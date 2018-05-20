package net.philsprojects.task;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.philsprojects.BaseTest;
import net.philsprojects.task.TestTask.PowerTask;

public class TestTaskSet extends BaseTest 
{

	private TaskService executor;
	
	@Before
	public void testBefore() {
		executor = new TaskService();
		executor.start();
	}
	
	@After
	public void testAfter() {
		executor.stop();
	}
	
	@Test
	public void testSet()
	{
		TaskSet set = new TaskSet();
		set.setClean(false);
		
		assertEquals( 0, set.size() );
		
		set.add(new PowerTask(2, 20));
		set.add(new PowerTask(5, 3));
		set.add(new PowerTask(4, 3));
		
		assertEquals( 3, set.size() );
		
		List<?> results = set.sync();
		assertEquals( 3, results.size() );
		assertTrue( results.contains(new BigInteger("1048576")) );
		assertTrue( results.contains(new BigInteger("125")) );
		assertTrue( results.contains(new BigInteger("64")) );
		
		assertEquals( 3, set.size() );
		
		set.reset();
		set.setClean(true);
		
		set.async(new TaskListenerAdapter<List<?>>() {
			public void onTaskFinish(Task<List<?>> task) {
				List<?> results = task.getResult();
				assertTrue( results.contains(new BigInteger("1048576")) );
				assertTrue( results.contains(new BigInteger("125")) );
				assertTrue( results.contains(new BigInteger("64")) );
			}
		});
		
		set.join();

		assertEquals( 0, set.size() );
	}
	
}
