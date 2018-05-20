package net.philsprojects.task;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.philsprojects.BaseTest;
import net.philsprojects.task.TestTask.PowerTask;

public class TestTaskList extends BaseTest 
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
	public void testList()
	{
		TaskList list = new TaskList();
		list.setClean(false);
		
		assertEquals( 0, list.size() );
		
		list.add(new PowerTask(2, 20));
		list.add(new PowerTask(5, 3));
		list.add(new PowerTask(4, 3));
		
		assertEquals( 3, list.size() );
		
		List<?> results = list.sync();
		assertEquals( 3, results.size() );
		assertTrue( results.contains(new BigInteger("1048576")) );
		assertTrue( results.contains(new BigInteger("125")) );
		assertTrue( results.contains(new BigInteger("64")) );
		
		assertEquals( 3, list.size() );
		
		list.reset();
		list.setClean(true);
		
		list.async(new TaskListenerAdapter<List<?>>() {
			public void onTaskFinish(Task<List<?>> task) {
				List<?> results = task.getResult();
				assertTrue( results.contains(new BigInteger("1048576")) );
				assertTrue( results.contains(new BigInteger("125")) );
				assertTrue( results.contains(new BigInteger("64")) );
			}
		});
		
		list.join();

		assertEquals( 0, list.size() );
	}
	
}
