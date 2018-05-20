package net.philsprojects.service;

/**
 * A generic handler of events.
 * 
 * @author Philip Diffenderfer
 *
 * @param <E>
 * 		The event type.
 */
public interface EventHandler<E> 
{

	/**
	 * Adds the event to the handler. The handler can choose to deny the event
	 * from being processed. Even if the handler accepts the event it may not 
	 * run if the application is being stopped.
	 * 
	 * @param event
	 * 		The event to add and process.
	 * @return
	 * 		True if the event was accepted, otherwise false.
	 */
	public boolean addEvent(E event);
	
}
