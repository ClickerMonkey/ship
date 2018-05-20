package net.philsprojects.game.controls;

import net.philsprojects.game.util.Iterator;
import net.philsprojects.game.util.Vector;

public class MouseEvent extends Event
{

	private Vector _location = Vector.zero();
	private boolean _leftDown = false;
	private boolean _rightDown = false;
	private int _type = 0;

	public void invoke(Control source, float x, float y, boolean leftDown, boolean rightDown, int type)
	{
		_location.set(x, y);
		_leftDown = leftDown;
		_rightDown = rightDown;
		_type = type;
		Iterator<EventListener> i = _listeners.getIterator();
		while (i.hasNext())
			i.getNext().<MouseEvent>controlEvent(source, this);
	}

	public float getX()
	{
		return _location.x;
	}

	public float getY()
	{
		return _location.y;
	}

	public Vector getLocation()
	{
		return _location;
	}

	public boolean isLeftDown()
	{
		return _leftDown;
	}

	public boolean isRightDown()
	{
		return _rightDown;
	}

	public int getType()
	{
		return _type;
	}


}
