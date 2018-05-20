package net.philsprojects.net.nio;

import static org.junit.Assert.*;

import org.junit.Test;

import net.philsprojects.BaseTest;

public class TestNioSelector extends BaseTest
{

	@Test
	public void testDefault()
	{
		NioSelector selector = new NioSelector();
		assertEquals( 0, selector.getAcceptors().getResourceCount() );
		assertEquals( 0, selector.getWorkers().getResourceCount() );
		
		selector.start();
		
		assertEquals( 0, selector.getAcceptors().getResourceCount() );
		assertEquals( 1, selector.getWorkers().getResourceCount() );
		
		selector.stop();
		
		assertEquals( 0, selector.getAcceptors().getResourceCount() );
		assertEquals( 0, selector.getWorkers().getResourceCount() );
	}
	
	@Test
	public void testWorkers()
	{
		NioSelector selector = new NioSelector();
		selector.getWorkers().setCapacity(3);
		selector.start();

		assertEquals( 0, selector.getAcceptors().getResourceCount() );
		assertEquals( 3, selector.getWorkers().getResourceCount() );
		
		selector.stop();
		
		assertEquals( 0, selector.getAcceptors().getResourceCount() );
		assertEquals( 0, selector.getWorkers().getResourceCount() );
	}
	
	@Test
	public void testAcceptors()
	{
		NioSelector selector = new NioSelector();
		selector.getWorkers().setCapacity(0);
		selector.getAcceptors().setCapacity(2);
		selector.start();

		assertEquals( 2, selector.getAcceptors().getResourceCount() );
		assertEquals( 0, selector.getWorkers().getResourceCount() );
		
		selector.stop();
		
		assertEquals( 0, selector.getAcceptors().getResourceCount() );
		assertEquals( 0, selector.getWorkers().getResourceCount() );
	}
	
}
