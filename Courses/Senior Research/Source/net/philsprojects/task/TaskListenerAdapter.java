package net.philsprojects.task;

/**
 * An adapter which implements all TaskListener methods. This can be used to
 * only listen to specific task events.
 * 
 * <h1>Example Usage</h1>
 * <pre>
 * Task t = new ...
 * t.async(new TastListenerAdapter() {
 *	public void onTaskFinish(Task t) {
 * 		// task has finished (success, error, timed out, cancelled)
 *	}
 * });
 * </pre>
 * 
 * @author Philip Diffenderfer
 *
 * @param <R>
 * 		The result type.
 */
public class TaskListenerAdapter<R> implements TaskListener<R> 
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onTaskCancel(Task<R> source) 
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onTaskError(Task<R> source, Throwable error) 
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onTaskFinish(Task<R> source) 
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onTaskSuccess(Task<R> source, R result) 
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onTaskTimeout(Task<R> source) 
	{
	}

}
