package net.philsprojects.game;

import static net.philsprojects.game.Constants.BACKWARD;
import static net.philsprojects.game.Constants.FORWARD;
import static net.philsprojects.game.Constants.ONCE_BACKWARD;
import static net.philsprojects.game.Constants.ONCE_FORWARD;
import static net.philsprojects.game.Constants.PINGPONG;
import static net.philsprojects.game.util.Math.angle;
import static net.philsprojects.game.util.Math.distance;
import net.philsprojects.game.util.Vector;

public class PathSpline extends Animated implements IPath
{
	private int _currentLine = 0;
	private float _currentLength = 0.0f;
	private float _speed = 0.0f;
	private float _totalLength = 0.0f;
	private String _name = "";
	private Vector _location = Vector.zero();
	private Vector _prevLocation = Vector.zero();
	private Vector _nextLocation = Vector.zero();
	private Vector[] _points = null;
	private float[] _lengths = null;

	protected PathSpline(String name, int type)
	{
		super(type);
		_name = name;
	}

	protected PathSpline(String name, int type, Vector[] vectors)
	{
		super(type);
		_name = name;
		set(vectors);
	}

	public void set(Vector[] vectors)
	{
		_points = vectors;
		_lengths = new float[vectors.length];
		_lengths[0] = 0.0f;
		recalculateLength();
		reset();
	}

	public void start()
	{
		if (_points == null || _points.length < 2)
			return;
		_enabled = true;
	}

	public void startDuration(float duration)
	{
		if (_points == null || _points.length < 2)
			return;
		_speed = _totalLength / duration;
		_enabled = true;
	}

	public void startSpeed(float speed)
	{
		if (_points == null || _points.length < 2 || speed <= 0f)
			return;
		_speed = speed;
		_enabled = true;
	}

	@Override
	protected boolean updateAnimation(float deltatime)
	{
		_currentLength += _speed * deltatime * getDirection();
		if (getDirection() == FORWARD && _currentLength >= _lengths[_currentLine + 1] && _currentLine + 2 < _points.length)
		{
			_currentLine++;
			_prevLocation = _points[_currentLine];
			_nextLocation = _points[_currentLine + 1];
		}
		if (getDirection() == BACKWARD && _currentLength <= _lengths[_currentLine] && _currentLine > 0)
		{
			_currentLine--;
			_prevLocation = _points[_currentLine];
			_nextLocation = _points[_currentLine + 1];
		}
		float delta = 1.0f - (_lengths[_currentLine + 1] - _currentLength) / (_lengths[_currentLine + 1] - _lengths[_currentLine]);
		_location = Vector.delta(_prevLocation, _nextLocation, delta);
		if (isAtEnd() && getAnimationType() == ONCE_FORWARD)
			_location = _points[_points.length - 1];
		if (isAtStart() && getAnimationType() == ONCE_BACKWARD)
			_location = _points[0];
		return true;
	}

	@Override
	protected boolean isAtEnd()
	{
		return (_currentLength > _totalLength);
	}

	@Override
	protected boolean isAtStart()
	{
		return (_currentLength < 0.0f);
	}

	@Override
	protected void resetToEnd()
	{
		_currentLine = _points.length - 2;
		_prevLocation = _points[_currentLine];
		_nextLocation = _points[_currentLine + 1];
		if (getAnimationType() == PINGPONG)
		{
			_currentLength = _totalLength - (_currentLength - _totalLength);
			float delta = 1.0f - (_lengths[_currentLine + 1] - _currentLength) / (_lengths[_currentLine + 1] - _lengths[_currentLine]);
			_location = Vector.delta(_prevLocation, _nextLocation, delta);

		}
		else
		{
			_currentLength = _totalLength;
			_location = _nextLocation;
		}
	}

	@Override
	protected void resetToStart()
	{
		_currentLine = 0;
		_prevLocation = _points[0];
		_nextLocation = _points[1];
		if (getAnimationType() == PINGPONG)
		{
			_currentLength = -_currentLength;
			float delta = 1.0f - (_lengths[_currentLine + 1] - _currentLength) / (_lengths[_currentLine + 1] - _lengths[_currentLine]);
			_location = Vector.delta(_prevLocation, _nextLocation, delta);

		}
		else
		{
			_currentLength = 0f;
			_location = _prevLocation;
		}
	}

	private void recalculateLength()
	{
		_totalLength = 0.0f;
		float d = 0.0f;
		for (int i = 0; i < _points.length - 1; i++)
		{
			d = distance(_points[i], _points[i + 1]);
			_totalLength += d;
			_lengths[i + 1] = _totalLength;
		}
	}

	public Vector getLocation()
	{
		return _location;
	}

	public float getLength()
	{
		return _totalLength;
	}

	public float getAngle()
	{
		return angle(_location, _nextLocation);
	}

	public float getSpeed()
	{
		return _speed;
	}

	public boolean isMoving()
	{
		return _enabled;
	}

	public String getName()
	{
		return _name;
	}

	public PathSpline getClone()
	{
		return new PathSpline(_name + "#", getAnimationType(), net.philsprojects.game.util.Helper.<Vector>copy(_points));
	}

}
