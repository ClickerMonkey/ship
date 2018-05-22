package net.philsprojects.game.animation;

import net.philsprojects.game.*;

import static net.philsprojects.game.Constants.*;


public class Animation extends Animated implements IName, IClone<Animation>
{

	protected Skeleton _skeleton = null;
	protected Frame[] _frames = null;
	protected String _name = null;
	protected float _interval = 0f;
	protected int _current = 0;
	protected float _time = 0f;


	protected Animation(int type)
	{
		super(type);
	}


	@Override
	protected boolean updateAnimation(float deltatime)
	{
		// Every time the frame interval is reached increase the current animation frame.
		_time += deltatime;
		if (_time >= _interval)
		{
			_time -= _interval;
			_current += getDirection();
		}
		// Get the delta between the current frame and the next.
		float delta = _time / _interval;
		// If we're going backwards then flip the delta
		if (getDirection() == BACKWARD)
			delta = 1 - delta;
		// Set the skeleton's angles and distances between the 2 frames.
		Frame.setSkeleton(_frames[_current], _frames[_current + 1], delta, _skeleton);
		return true;
	}

	@Override
	protected void resetToEnd()
	{
		_current = _frames.length - 2;
	}

	@Override
	protected void resetToStart()
	{
		_current = 0;
	}

	@Override
	protected boolean isAtEnd()
	{
		return (_current > _frames.length - 1);
	}

	@Override
	protected boolean isAtStart()
	{
		return (_current < 0);
	}

	public float getDuration()
	{
		return (_frames.length - 1) * _interval;
	}

	public float getInterval()
	{
		return _interval;
	}

	public Skeleton getSkeleton()
	{
		return _skeleton;
	}

	public int getStartingFrame()
	{
		return (getDirection() == FORWARD ? _current : _current + 1);
	}

	public int getEndingFrame()
	{
		return (getDirection() == BACKWARD ? _current : _current + 1);
	}

	public String getName()
	{
		return _name;
	}

	public Animation getClone()
	{
		Animation clone = new Animation(getAnimationType());
		clone._current = _current;
		clone._frames = net.philsprojects.game.util.Helper.<Frame>copy(_frames);
		clone._interval = _interval;
		clone._name = _name + "#";
		clone._skeleton = _skeleton;
		return clone;
	}

}
