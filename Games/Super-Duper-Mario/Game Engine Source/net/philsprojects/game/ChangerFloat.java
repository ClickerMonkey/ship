package net.philsprojects.game;

import net.philsprojects.game.util.Curve;
import net.philsprojects.game.util.SmoothCurve;

public class ChangerFloat extends Animated implements IUpdate, IName, IClone<ChangerFloat>
{

	protected String _name = null;
	protected ICurve _curve = null;
	protected float _time = 0.0f;

	public ChangerFloat(String name, int type)
	{
		super(type);
		_name = name;
	}

	public ChangerFloat(String name, int type, int steps, float[] times, float[] values)
	{
		super(type);
		_name = name;
		set(steps, times, values);
	}

	public ChangerFloat(String name, int type, float duration, float[] values)
	{
		super(type);
		_name = name;
		set(duration, values);
	}

	public ChangerFloat(String name, int type, ICurve curve)
	{
		super(type);
		_name = name;
		_curve = curve;
		reset();
	}

	public void set(int steps, float[] times, float[] values)
	{
		_curve = new Curve(steps, times, values);
		reset();
	}

	public void set(float duration, float[] values)
	{
		_curve = new SmoothCurve(duration, values);
		reset();
	}

	public void start()
	{
		if (_curve == null)
			return;
		_enabled = true;
	}

	@Override
	protected boolean updateAnimation(float deltatime)
	{
		_time += deltatime * getDirection();
		return true;
	}

	@Override
	protected void resetToEnd()
	{
		if (getAnimationType() == Constants.PINGPONG)
			_time = _curve.getDuration() - (_time - _curve.getDuration());
		else
			_time = _curve.getDuration();
	}

	@Override
	protected void resetToStart()
	{
		if (getAnimationType() == Constants.PINGPONG)
			_time = -_time;
		else
			_time = 0f;
	}

	@Override
	protected boolean isAtEnd()
	{
		return (_time > _curve.getDuration());
	}

	@Override
	protected boolean isAtStart()
	{
		return (_time < 0f);
	}

	public float getValue()
	{
		return _curve.getValue(_time);
	}

	public float getDuration()
	{
		return _curve.getDuration();
	}

	public String getName()
	{
		return _name;
	}

	public ChangerFloat getClone()
	{
		return new ChangerFloat(_name + "#", getAnimationType(), _curve);
	}

}
