package net.philsprojects.util;

import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicInteger;

import net.philsprojects.BaseTest;

import org.junit.Test;

public class TestSignal extends BaseTest 
{

	@Test
	public void testSerial()
	{
		Signal sig = new Signal();
		
		assertEquals( 0, sig.send() );
		assertEquals( 1, sig.receive() );
		
		assertFalse( sig.recieved() );
		
		assertEquals( 0, sig.send() );
		assertTrue( sig.recieved() );
		assertFalse( sig.recieved() );
		
		assertEquals( 0, sig.send() );
		assertEquals( 1, sig.send() );
		assertEquals( 2, sig.receive() );
		assertEquals( 0, sig.receive() );
	}
	
	@Test
	public void testConcurrent()
	{
		final int SENDERS = 6;
		final int SENDS_PER_SENDER = 100;
		final int TOTAL_SENDS = SENDERS * SENDS_PER_SENDER;
		
		final AtomicInteger sends = new AtomicInteger();
		final AtomicInteger receives = new AtomicInteger();
		final Signal signal = new Signal();
		
		GroupTask.initialize(SENDERS + 1);
		
		Runnable sender = new Runnable() {
			public void run() {
				for (int i = 0; i < SENDS_PER_SENDER; i++) {
					signal.send();
					sends.incrementAndGet();	
				}
			}
		};
		Runnable reciever = new Runnable() {
			public void run() {
				while (receives.get() < TOTAL_SENDS) {
					receives.getAndAdd(signal.receive());	
				}
			}
		};

		GroupTask.add(sender, SENDERS);	
		GroupTask.add(reciever);
		
		GroupTask.execute();

		assertEquals( sends.get(), receives.get() );
		assertEquals( TOTAL_SENDS, receives.get() );
	}
	
}
