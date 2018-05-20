package net.philsprojects.task;

/**
 * A clone of a task used for forking. This task merely calls the parent tasks
 * execute method.
 * 
 * @author Philip Diffenderfer
 *
 * @param <R>
 * 		The result type.
 */
public class TaskFork<R> extends Task<R> 
{

	// The parent task to invoke.
	private final Task<R> parent;
	
	/**
	 * Instantiates a new TaskClone given the parent task.
	 * 
	 * @param parent
	 * 		The parent of this task.
	 */
	public TaskFork(Task<R> parent) 
	{
		this.parent = parent;
		this.setHandler(parent.getHandler());
		this.setTimeout(parent.getTimeout());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public R execute() 
	{
		return parent.execute();
	}
	
	/**
	 * Returns the parent of this forked task.
	 * 
	 * @return
	 * 		The reference to the parent task.
	 */
	public Task<R> getParent()
	{
		return parent;
	}

}
