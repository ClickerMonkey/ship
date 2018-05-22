package net.philsprojects.game.controls;

import net.philsprojects.game.util.*;

public class MouseState
{

	private Vector _current;
	private Vector _last;
	private boolean _currentLeftDown;
	private boolean _currentRightDown;
	private boolean _lastLeftDown;
	private boolean _lastRightDown;

	public MouseState(Vector current, Vector previous, boolean currentLeftDown, boolean currentRightDown, boolean previousLeftDown, boolean previousRightDown)
	{
		set(current, previous, currentLeftDown, currentRightDown, previousLeftDown, previousRightDown);
	}

	public MouseState set(Vector current, Vector previous, boolean currentLeftDown, boolean currentRightDown, boolean previousLeftDown, boolean previousRightDown)
	{
		_current = current;
		_last = previous;
		_currentLeftDown = currentLeftDown;
		_currentRightDown = currentRightDown;
		_lastLeftDown = previousLeftDown;
		_lastRightDown = previousRightDown;
		return this;
	}

	public Vector getCurrentPosition()
	{
		return _current;
	}

	public Vector getLastPosition()
	{
		return _last;
	}

	public boolean getCurrentLeftDown()
	{
		return _currentLeftDown;
	}

	public boolean getCurrentRightDown()
	{
		return _currentRightDown;
	}

	public boolean getLastLeftDown()
	{
		return _lastLeftDown;
	}

	public boolean getLastRightDown()
	{
		return _lastRightDown;
	}

	protected void setCurrentPosition(float x, float y)
	{
		_last.set(_current);
		_current.set(x, y);
	}

	protected void setCurrentLeftDown(boolean down)
	{
		_lastLeftDown = _currentLeftDown;
		_currentLeftDown = down;
	}

	protected void setCurrentRightDown(boolean down)
	{
		_lastRightDown = _currentRightDown;
		_currentRightDown = down;
	}
}
