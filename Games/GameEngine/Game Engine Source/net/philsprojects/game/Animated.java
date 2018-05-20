package net.philsprojects.game;

import static net.philsprojects.game.Constants.BACKWARD;
import static net.philsprojects.game.Constants.FORWARD;
import static net.philsprojects.game.Constants.LOOP_BACKWARD;
import static net.philsprojects.game.Constants.LOOP_FORWARD;
import static net.philsprojects.game.Constants.ONCE_BACKWARD;
import static net.philsprojects.game.Constants.ONCE_FORWARD;
import static net.philsprojects.game.Constants.PINGPONG;

public abstract class Animated implements IUpdate
{

	private int _type = LOOP_FORWARD;
	private int _motion = FORWARD;

	protected boolean _enabled = false;

	protected Animated(int type)
	{
		_type = type;
	}

	public final void update(float deltatime)
	{
		if (_enabled)
		{
			if (updateAnimation(deltatime))
				_enabled = doAnimation();
			else
				_enabled = false;
		}
	}

	private boolean doAnimation()
	{
		switch (_type)
		{
			case ONCE_FORWARD:
				if (isAtEnd())
					return false;
				return true;
			case ONCE_BACKWARD:
				if (isAtStart())
					return false;
				return true;
			case LOOP_FORWARD:
				if (isAtEnd())
					resetToStart();
				return true;
			case LOOP_BACKWARD:
				if (isAtStart())
					resetToEnd();
				return true;
			case PINGPONG:
				switch (_motion)
				{
					case FORWARD:
						if (isAtEnd())
						{
							resetToEnd();
							_motion = BACKWARD;
						}
						return true;
					case BACKWARD:
						if (isAtStart())
						{
							resetToStart();
							_motion = FORWARD;
						}
						return true;
				}
		}
		return true;
	}

	public final void reset()
	{
		if (_type == LOOP_BACKWARD || _type == ONCE_BACKWARD)
		{
			resetToEnd();
			_motion = BACKWARD;
		}
		else
		{
			resetToStart();
			_motion = FORWARD;
		}
		_enabled = true;
	}

	public final boolean isEnabled()
	{
		return _enabled;
	}

	public final int getAnimationType()
	{
		return _type;
	}

	public final int getDirection()
	{
		return _motion;
	}

	public final void setType(int type)
	{
		_type = type;
		reset();
	}

	protected abstract void resetToEnd();

	protected abstract void resetToStart();

	protected abstract boolean isAtEnd();

	protected abstract boolean isAtStart();

	protected abstract boolean updateAnimation(float deltatime);

}
