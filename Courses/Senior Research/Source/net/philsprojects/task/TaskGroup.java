package net.philsprojects.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

/**
 * A collection of tasks that must start at the exact same time (execute
 * concurrently).
 * 
 * @author Philip Diffenderfer
 *
 */
public class TaskGroup extends TaskCollection 
{

	/**
	 * A runner which waits for a barrier then executes another runner.
	 * 
	 * @author Philip Diffenderfer
	 *
	 */
	private class SyncRunner implements Runnable 
	{
		
		// The barrier to wait for.
		private final CyclicBarrier barrier;
		
		// The runner to execute.
		private final Runnable runner;
		
		/**
		 * Instantiates a new SyncRunner.
		 * 
		 * @param barrier
		 * 		The barrier to wait for.
		 * @param runner
		 * 		The runner to execute.
		 */
		public SyncRunner(CyclicBarrier barrier, Runnable runner) 
		{
			this.barrier = barrier;
			this.runner = runner;
		}
		
		/**
		 * {@inheritDoc}
		 */
		public void run() 
		{
			try {
				barrier.await();	
				runner.run();
			}
			catch (Exception e) {
			}
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<?> onExecute() 
	{
		// A barrier to make all tasks wait for each other to start.
		CyclicBarrier barrier = new CyclicBarrier(list.size());
		
		// Create new threads and start them. They will block until they're all
		// started and blocking on the barrier.
		for (Task<?> task : list) 
		{
			new Thread(new SyncRunner(barrier, task)).start();
		}
		
		// Finally wait for each task to finish, and if the list should be 
		// cleaned then remove the task from the list.
		List<Object> results = new ArrayList<Object>();
		for (int i = list.size() - 1; i >= 0; i--) 
		{
			results.add(list.get(i).sync());
			// Remove this task once completed?
			if (cleanList) {
				list.remove(i);
			}
		}
		return results;
	}

}
