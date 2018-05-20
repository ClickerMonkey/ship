package net.philsprojects.game.controls;

import net.philsprojects.game.util.LinkedList;

public abstract class Event
{

	protected LinkedList<EventListener> _listeners;

	protected Event()
	{
		_listeners = new LinkedList<EventListener>();
	}

	public void addListener(EventListener listener)
	{
		_listeners.add(listener);
	}

	public void removeListener(EventListener listener)
	{
		_listeners.remove(listener);
	}


}
