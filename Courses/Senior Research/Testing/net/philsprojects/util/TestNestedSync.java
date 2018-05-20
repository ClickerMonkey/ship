package net.philsprojects.util;

import java.util.concurrent.atomic.AtomicBoolean;

import net.philsprojects.BaseTest;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestNestedSync extends BaseTest 
{

	@Test
	public void testNested() 
	{
		final AtomicBoolean flag = new AtomicBoolean(false);
		final Object lock = new Object();
		
		GroupTask.initialize(2);
		
		GroupTask.add(new Runnable() {
			public void run() {
				synchronized (lock) {
					synchronized (lock) {
						try {
							lock.wait();
							flag.set(true);	
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				assertTrue(flag.get());
			}
		});
		
		GroupTask.add(new Runnable() {
			public void run() {
				while (flag.get() == false) {
					synchronized (lock) {
						lock.notifyAll();
					}
					sleep(1);
				}
				assertTrue(flag.get());
			}
		});
		
		GroupTask.execute();
		
		assertTrue(true);
	}
	
	
}
