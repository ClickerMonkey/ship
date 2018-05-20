package dcl.test;

import org.junit.Test;

import dcl.Order;

public class TestOrder 
{
	
	@Test
	public void testBackToFront()
	{
		Order order = Order.BackToFront;

		System.out.println(order);
		
		int length = 10;
		int step = order.getStep();
		int start = order.getStart(length);
		int end = order.getEnd(length);
		
		for (int i = start; i != end; i += step) {
			System.out.println(i);
		}
	}
	
	@Test
	public void testFrontToBack()
	{
		Order order = Order.FrontToBack;

		System.out.println(order);
		
		int length = 10;
		int step = order.getStep();
		int start = order.getStart(length);
		int end = order.getEnd(length);
		
		for (int i = start; i != end; i += step) {
			System.out.println(i);
		}
	}
	
}
