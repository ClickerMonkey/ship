package net.philsprojects.task;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of tasks that do not have a particular execution order.
 * 
 * @author Philip Diffenderfer
 *
 */
public class TaskSet extends TaskCollection 
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<?> onExecute() 
	{
		// Call all tasks asynchronously
		for (Task<?> task : list) {
			task.async(); 
		}
		
		List<Object> results = new ArrayList<Object>();
		// Now wait for each one to finish and add the results to a list.
		for (int i = list.size() - 1; i >= 0; i--) {
			list.get(i).join();
			results.add(list.get(i).getResult());
			// Remove this task once completed?
			if (cleanList) {
				list.remove(i);
			}
		}
		return results;
	}

}
