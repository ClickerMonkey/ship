package net.philsprojects.task;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of tasks when executed will return a list of results.
 * 
 * @author Philip Diffenderfer
 *
 */
public abstract class TaskCollection extends Task<List<? extends Object>> 
{

	// The internal list of tasks.
	protected List<Task<? extends Object>> list;
	
	// Whether tasks should be removed from the list once they finish.
	protected volatile boolean cleanList = true;
	
	// Whether this task is currently running.
	private volatile boolean running = false;
	
	
	/**
	 * Instantiates a new TaskCollection.
	 */
	public TaskCollection() 
	{
		list = new ArrayList<Task<?>>();
	} 
	
	/**
	 * Executes this collections tasks.
	 * 
	 * @return
	 * 		The results of the tasks.
	 */
	protected abstract List<?> onExecute();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final List<?> execute()
	{
		running = true;
		try {
			return onExecute();	
		}
		finally {
			running = false;	
		}
	}
	
	/**
	 * Sets this collection to cleaning mode. In cleaning mode once a task has
	 * completed it will be removed from the collection.
	 * 
	 * @param cleanList
	 * 		Whether this collection should be in cleaning mode.
	 */
	public void setClean(boolean cleanList)
	{
		this.cleanList = cleanList;
	}
	
	/**
	 * Returns whether this collection is in cleaning mode. In cleaning mode
	 * once a task has completed it will be removed from the collection.
	 * 
	 * @return
	 * 		True if this collection is in cleaning mode.
	 */
	public boolean isClean()
	{
		return cleanList;
	}
	
	/**
	 * Adds the given task to this collection. If this collection is currently
	 * executing, the given task will not be added. 
	 * 
	 * @param task
	 * 		The task to add.
	 * @return
	 * 		True if the task was added, false if the task is currently running.
	 */
	public boolean add(Task<?> task) 
	{
		return (!running && list.add(task));
	}
	
	/**
	 * Removes the given task from this collection. If this collection is 
	 * currently executing, the given task will not be removed.
	 * 
	 * @param task
	 * 		The task to remove.
	 * @return
	 * 		True if the task was removed, false if the task is currently running.
	 */
	public boolean remove(Task<?> task) 
	{
		return (!running && list.remove(task));
	}
	
	/**
	 * Removes the task at the given index from this collection. If this
	 * collection is currently executing, the given task will not be removed.
	 * 
	 * @param index
	 * 		The index of the task to remove.
	 * @return
	 * 		True if the task was removed, false if the task is currently running.
	 */
	public Task<?> remove(int index) 
	{
		return (running ? null : list.remove(index));
	}
	
	/**
	 * Returns the task at the given index from this collection. If this
	 * collection is currently executing, null will be returned.
	 * 
	 * @param index
	 * 		The index of the task to get.
	 * @return
	 * 		The task at the given index, or null if this collection is running.
	 */
	public Task<?> getTask(int index) 
	{
		return (running ? null : list.get(index));
	}
	
	/**
	 * Returns the size of the task collection. If this collection is currently
	 * executing then -1 will be returned.
	 * 
	 * @return
	 * 		The number of tasks in this collection, or -1 if this collection is
	 * 		running.
	 */
	public int size() 
	{
		return (running ? - 1 : list.size());
	}
	
}
