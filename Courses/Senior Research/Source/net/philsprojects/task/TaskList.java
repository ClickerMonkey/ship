package net.philsprojects.task;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of tasks that must be executed in order, one after another.
 * 
 * @author Philip Diffenderfer
 *
 */
public class TaskList extends TaskCollection
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<?> onExecute() 
	{
		List<Object> results = new ArrayList<Object>();
		for (int i = list.size() - 1; i >= 0; i--) {
			results.add(list.get(i).sync());
			// Remove this task once completed?
			if (cleanList) {
				list.remove(i);
			}
		}
		return results;
	}

}
