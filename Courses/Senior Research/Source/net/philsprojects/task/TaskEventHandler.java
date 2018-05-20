package net.philsprojects.task;

import net.philsprojects.service.EventHandler;

/**
 * An event handler that can process any type of task.
 * 
 * @author Philip Diffenderfer
 *
 */
public interface TaskEventHandler extends EventHandler<Task<?>> 
{

}
