package net.philsprojects.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import net.philsprojects.BaseTest;
import net.philsprojects.util.BlockableQueue;

@Deprecated
public class TestServicePool extends BaseTest
{
	
	public class TestService<E> extends AbstractService<E> {
		public List<E> events = new ArrayList<E>();
		public TestService() {
		}
		public TestService(BlockableQueue<E> source) {
			super(source);
		}
		protected void onEvent(E event) {
			events.add(event);
		}
		protected void onExecute() {
		}
		protected void onPause() {
		}
		protected void onResume() {
		}
		protected void onStart() {
		}
		protected void onStop() {
		}
	}
	
	public class TestServiceFactory<E> implements ServiceFactory<E> {
		public Service<E> newService() {
			return new TestService<E>();
		}
		public Service<E> newService(BlockableQueue<E> queue) {
			return new TestService<E>(queue);
		}
	}
	
	
	@Test
	public void testAccessors() 
	{
		final TestServiceFactory<String> factory = new TestServiceFactory<String>();
		final ServicePool<String> pool = new ServicePool<String>(factory);
		
		assertEquals( 0, pool.getServiceCount() );
		assertSame( factory, pool.getFactory() );
		assertEquals( 0, pool.getWaitingEvents() );
		assertFalse( pool.isAccepting() );
		assertTrue( pool.isUnderflow() );
		assertTrue( pool.isStopped() );
		assertFalse( pool.isRunning() );
	}
	
	@Test
	public void testStartAndStop()
	{
		final TestServiceFactory<String> factory = new TestServiceFactory<String>();
		final ServicePool<String> pool = new ServicePool<String>(factory);
		
		pool.start();
		
		assertEquals( pool.getMinCapacity(), pool.getServiceCount() );
		assertTrue( pool.isAccepting() );
		assertFalse( pool.isUnderflow() );
		assertFalse( pool.isOverflow() );
		assertFalse( pool.isStopped() );
		assertTrue( pool.isRunning() );
		
		pool.stop();
		
		assertEquals( 0, pool.getServiceCount() );
		assertFalse( pool.isAccepting() );
		assertTrue( pool.isUnderflow() );
		assertTrue( pool.isStopped() );
		assertFalse( pool.isRunning() );
	}
	
	
	@Test
	public void testAllocateDeallocate() 
	{
		final TestServiceFactory<String> factory = new TestServiceFactory<String>();
		final ServicePool<String> pool = new ServicePool<String>(factory);
		
		pool.start();

		assertEquals( pool.getMinCapacity(), pool.getServiceCount() );
		
		int gap = pool.getMaxCapacity() - pool.getMinCapacity();
		
		assertEquals( gap, pool.allocate(gap) );
		assertEquals( pool.getMaxCapacity(), pool.getServiceCount() );

		assertEquals( gap, pool.deallocate(gap) );
		assertEquals( pool.getMinCapacity(), pool.getServiceCount() );
		
		pool.stop();
	}
	
	@Test
	public void testListener()
	{
		final AtomicInteger deallocates = new AtomicInteger();
		final AtomicInteger allocates = new AtomicInteger();
		final TestServiceFactory<String> factory = new TestServiceFactory<String>();
		final ServicePool<String> pool = new ServicePool<String>(factory);
		
		pool.getListeners().add(new ServicePoolListener<String>() {
			public void onServiceDestroyBatch(ServicePool<String> pool, int serviceCount) {
				deallocates.addAndGet(serviceCount);
			}
			public void onServiceDestroy(ServicePool<String> pool, Service<String> service) {
			}
			public void onServiceCreateBatch(ServicePool<String> pool, int serviceCount) {
				allocates.addAndGet(serviceCount);
			}
			public void onServiceCreate(ServicePool<String> pool, Service<String> service) {
			}
		});
		
		pool.start();
		
		assertEquals( allocates.get(), pool.getMinCapacity() );
	}
	
	@Test
	public void testEventDistribution()
	{
		final HashSet<Service<Integer>> services = new HashSet<Service<Integer>>();
		final TestServiceFactory<Integer> factory = new TestServiceFactory<Integer>();
		final ServicePool<Integer> pool = new ServicePool<Integer>(factory);
		final int EVENTS = 10000;
		
		pool.setMaxCapacity(20);
		pool.setMinCapacity(10);
		pool.setAllocateSize(2);
		pool.setAllocateThreshold(1000);
		pool.setDeallocateSize(1);
		pool.setEventThreshold(2);
		
		pool.getListeners().add(new ServicePoolListenerAdapter<Integer>() {
			public void onServiceCreate(ServicePool<Integer> pool, Service<Integer> service) {
				services.add(service);
			}
			public void onServiceDestroy(ServicePool<Integer> pool, Service<Integer> service) {
				services.remove(service);
			}
		});
		
		pool.start();
		
		// Start timing
		watch.start("Adding events... ");
		
		// Start hitting the services hard.
		for (int i = 0; i < EVENTS; i++) {
			assertTrue( pool.addEvent(i) );
		}
		
		// Wait 500ms for all events to be processed
		// Wait for all events to be processed
		while (pool.getWaitingEvents() > 0) {
			// do a whole lot of nothing
		}
		
		// Stop timing
		watch.stop("%.6f\n");

		// Check to see if any extra services needed to be allocated.
		System.out.format("Services [%d]\n", pool.getServiceCount());
		
		// Pause so the last event can be added to the service's list.
		sleep(500);
		
		// How many times each event was called. 
		List<Integer> events = new ArrayList<Integer>(EVENTS);
		for (int i = 0; i < EVENTS; i++) {
			events.add(0);
		}
		
		// Look at the distribution of all events, and display.
		System.out.print("[");
		for (Service<Integer> service : services) {
			TestService<Integer> ts = (TestService<Integer>)service;
			for (Integer x : ts.events) {
				events.set(x, events.get(x) + 1);
			}
			System.out.format("%d, ", ts.events.size());
		}
		System.out.println("]");
		
		// Stop all services now.
		pool.stop(true);
		
		// Show both have been emptied.
		assertEquals( 0, pool.getServiceCount() );
		assertEquals( 0, services.size() );
		
		// Show that all events executed exactly called once.
		for (int i = 0; i < EVENTS; i++) {
			assertEquals( 1, events.get(i).intValue() );
		}
	}
	
	@Test
	public void testMaxEvents()
	{
		final TestServiceFactory<Integer> factory = new TestServiceFactory<Integer>();
		final ServicePool<Integer> pool = new ServicePool<Integer>(factory);
		
		pool.setMaxCapacity(0); // Tricksy!
		pool.setMinCapacity(0); // Tricksy!
		pool.setMaxEvents(5);

		pool.start();

		assertTrue( pool.addEvent(0) );
		assertTrue( pool.addEvent(1) );
		assertTrue( pool.addEvent(2) );
		assertTrue( pool.addEvent(3) );
		assertTrue( pool.addEvent(4) );

		assertFalse( pool.addEvent(5) );
		
		pool.stop();
	}
	
	@Test
	public void testEventThreshold()
	{
		final AtomicInteger allocates = new AtomicInteger();
		final TestServiceFactory<Integer> factory = new TestServiceFactory<Integer>();
		final ServicePool<Integer> pool = new ServicePool<Integer>(factory);

		pool.setMaxCapacity(6);
		pool.setMinCapacity(2);
		pool.setAllocateSize(2);
		pool.setEventThreshold(2);
		
		pool.getListeners().add(new ServicePoolListenerAdapter<Integer>() {
			public void onServiceCreate(ServicePool<Integer> pool, Service<Integer> service) {
				try {
//					// First it has to be running (it can allocate when start is called)
//					if (pool.isRunning()) {
//						// Ensure its only adding service if event count is beyond
//						assertTrue( pool.getWaitingEvents() >= pool.getEventThreshold() );
//					}	
				}
				finally {
					// Track each service added (max of 6).
					allocates.incrementAndGet();	
				}
			}
		});
		
		// Start pool of services.
		pool.start();

		// Add a bunch of events, this should trigger growing.
		for (int i = 0; i < 10000; i++) {
			pool.addEvent(i);
		}
		
		pool.stop();
		
		assertEquals( pool.getMaxCapacity(), allocates.get() );
	}
	
	@Test
	public void testAllocateSize()
	{
		final TestServiceFactory<Integer> factory = new TestServiceFactory<Integer>();
		final ServicePool<Integer> pool = new ServicePool<Integer>(factory);

		pool.setMaxCapacity(6);
		pool.setMinCapacity(2);
		pool.setAllocateSize(2);
		pool.setEventThreshold(1);
		
		pool.getListeners().add(new ServicePoolListenerAdapter<Integer>() {
			public void onServiceCreateBatch(ServicePool<Integer> pool, int serviceCount) {
				assertEquals( 2, serviceCount );
			}
		});
		
		pool.start();

		// Add a bunch of events, this should trigger growing.
		for (int i = 0; i < 10000; i++) {
			pool.addEvent(i);
		}
		
		pool.stop();
	}
	
	@Test
	public void testDeallocateSize()
	{
		final AtomicInteger deallocates = new AtomicInteger();
		final TestServiceFactory<Integer> factory = new TestServiceFactory<Integer>();
		final ServicePool<Integer> pool = new ServicePool<Integer>(factory);

		pool.setMaxCapacity(4);
		pool.setMinCapacity(2);
		pool.setAllocateSize(2);
		pool.setDeallocateSize(2);
		pool.setEventThreshold(1);
		pool.setAllocateThreshold(1000);
		
		pool.getListeners().add(new ServicePoolListenerAdapter<Integer>() {
			public void onServiceDestroyBatch(ServicePool<Integer> pool, int serviceCount) {
				assertEquals( 2, serviceCount );
				deallocates.incrementAndGet();
			}
		});
		
		pool.start();

		// Add a bunch of events, this should trigger growing.
		for (int i = 0; i < 10000; i++) {
			pool.addEvent(i);
		}
		
		// Wait for them all to be processed
		while (pool.getWaitingEvents() > 0) {
			// do nothing!
		}
		
		// Wait the threshold
		sleep( pool.getAllocateThreshold() );
		
		assertEquals( pool.getMaxCapacity(), pool.getServiceCount() );
		
		// Try adding again
		assertTrue( pool.addEvent(10000) );
		
		assertEquals( 1, deallocates.get() );
		assertEquals( pool.getMinCapacity(), pool.getServiceCount() );
		
		pool.stop();

		assertEquals( 2, deallocates.get() );
		assertEquals( 0, pool.getServiceCount() );
	}
	
	
}
