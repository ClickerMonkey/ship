package net.philsprojects.util;

import static org.junit.Assert.*;

import org.junit.Test;

import net.philsprojects.BaseTest;

public class TestNonNullRef extends BaseTest 
{

	@Test
	public void testNonNull()
	{
		NonNullRef<String> ref = new NonNullRef<String>();
		assertFalse( ref.has() );
		
		ref.set("Hello World");
		assertTrue( ref.has() );
		
		assertEquals( "Hello World", ref.get() );
	}
	
	@Test
	public void testNull()
	{
		final NonNullRef<String> ref = new NonNullRef<String>();
		assertFalse( ref.has() );
		
		GroupTask.initialize(1);
		GroupTask.add(new Runnable() {
			public void run() {
				System.out.println("Before Get");
				assertEquals( "Hello World", ref.get() );
			}
		});
		GroupTask.begin();
		
		sleep(500);
		System.out.println("Before Set");
		ref.set("Hello World");
		
		GroupTask.finish();
	}
	
}
