package net.philsprojects.game;

import static net.philsprojects.game.util.Math.angle;
import net.philsprojects.game.util.Vector;

public class PathBezier extends Animated implements IPath
{

	public static int CALCULATE_LENGTH_STEPS = 20;

	private float _totalTime = 0.0f;
	private float _time = 0.0f;
	private float _length = 0.0f;
	private String _name = "";
	private Vector _location = Vector.zero();
	private Vector _lastLocation = Vector.zero();
	private Vector[] _points = null;

	public PathBezier(String name, int type)
	{
		super(type);
		_name = name;
	}

	public PathBezier(String name, int type, Vector v1, Vector v2, Vector v3, Vector v4)
	{
		super(type);
		_name = name;
		set(v1, v2, v3, v4);
	}

	public void set(Vector v1, Vector v2, Vector v3, Vector v4)
	{
		_points = new Vector[] { v1, v2, v3, v4 };
		recalculateLength();
		reset();
	}

	public void start()
	{
		if (_totalTime == 0.0f)
			return;
		_enabled = true;
	}

	public void startDuration(float duration)
	{
		if (duration == 0.0f)
			return;
		_totalTime = duration;
		_time = 0f;
		_enabled = true;
	}

	public void startSpeed(float speed)
	{
		if (speed == 0.0f)
			return;
		_totalTime = _length / speed;
		_time = 0f;
		_enabled = true;
	}

	@Override
	protected boolean updateAnimation(float deltatime)
	{
		float d = _time / _totalTime;
		_lastLocation.set(_location);
		_location = pointOnBezier(d);
		_time += deltatime * getDirection();
		return true;
	}

	private Vector pointOnBezier(float d)
	{
		float nd = 1 - d;
		return new Vector(_points[0].x * nd * nd * nd + 3 * _points[1].x * d * nd * nd + 3 * _points[2].x * d * d * nd + _points[3].x * d * d * d, _points[0].y * nd * nd *
				nd + 3 * _points[1].y * d * nd * nd + 3 * _points[2].y * d * d * nd + _points[3].y * d * d * d);

	}

	@Override
	protected void resetToEnd()
	{
		_time = _totalTime;
	}

	@Override
	protected void resetToStart()
	{
		_time = 0.0f;
	}

	@Override
	protected boolean isAtEnd()
	{
		return (_time >= _totalTime);
	}

	@Override
	protected boolean isAtStart()
	{
		return (_time <= 0.0f);
	}

	private void recalculateLength()
	{
		_length = 0.0f;
		Vector current = _points[0];
		Vector last = null;
		for (int i = 0; i < CALCULATE_LENGTH_STEPS; i++)
		{
			last = current;
			current = pointOnBezier(i / CALCULATE_LENGTH_STEPS);
			_length += Vector.distance(last, current);
		}
	}

	public String getName()
	{
		return _name;
	}

	public Vector getLocation()
	{
		return _location;
	}

	public float getAngle()
	{
		return angle(_lastLocation, _location);
	}

	public float getLength()
	{
		return _length;
	}

	public float getSpeed()
	{
		return _length / _totalTime;
	}

	public float getDuration()
	{
		return _totalTime;
	}

	public boolean isMoving()
	{
		return _enabled;
	}

	public PathBezier getClone()
	{
		return new PathBezier(_name + "#", getAnimationType(), _points[0].getClone(), _points[1].getClone(), _points[2].getClone(), _points[3].getClone());
	}

}
