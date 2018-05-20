package net.philsprojects.service;

/**
 * An interrupt passed to a service by pausing or stopping it.
 * 
 * @author Philip Diffenderfer
 *
 */
public enum ServiceInterrupt
{
	
	/**
	 * Interrupts a service as quickly as possible by ignoring all unhandled 
	 * events and not performing an execute in the current servicing iteration.
	 */
	Immediate(false, false),
	
	/**
	 * Interrupts a service by handling all events but not performing an 
	 * execute in the current servicing iteration.
	 */
	Execute(true, false),
	
	/**
	 * Interrupts a service by ignoring all events but still performs the 
	 * execute in the current servicing iteration.
	 */
	Actions(false, true),
	
	/**
	 * Interrupts a service once all events have been handled and execution has
	 * occurred in the current servicing iteration.
	 */
	None(true, true);
	
	
	
	/**
	 * Whether the interrupt will allow events in the current servicing 
	 * iteration to be handled.
	 */
	public final boolean runEvents;
	
	/**
	 * Whether the interrupt will allow execution to occur in the current 
	 * servicing iteration.
	 */
	public final boolean runExecute;
	
	
	/**
	 * Instantiates a ServiceInterrupt enumeration.
	 * 
	 * @param runEvents
	 * 		Whether the events should be handled during the interrupt.
	 * @param runExecute
	 * 		Whether the service executes during the interrupt.
	 */
	private ServiceInterrupt(boolean runEvents, boolean runExecute) 
	{
		this.runEvents = runEvents;
		this.runExecute = runExecute;
	}
	
}