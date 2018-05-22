package net.philsprojects.game;

import net.philsprojects.game.util.Color;
import net.philsprojects.game.util.Curve;
import net.philsprojects.game.util.SmoothCurve;

public class ChangerColor extends Animated implements IUpdate, IName, IClone<ChangerColor>
{

	protected String _name = null;
	protected Color _shade = Color.white();
	protected ICurve _curveR = null;
	protected ICurve _curveG = null;
	protected ICurve _curveB = null;
	protected float _time = 0.0f;

	public ChangerColor(String name, int type)
	{
		super(type);
		_name = name;
	}

	public ChangerColor(String name, int type, int steps, float[] times, Color[] values)
	{
		super(type);
		_name = name;
		set(steps, times, values);
	}

	public ChangerColor(String name, int type, float duration, Color[] values)
	{
		super(type);
		_name = name;
		set(duration, values);
	}

	public void set(int steps, float[] times, Color[] values)
	{
		int count = values.length;
		float[] r = new float[count];
		float[] g = new float[count];
		float[] b = new float[count];
		for (int i = 0; i < count; i++)
		{
			r[i] = values[i].getR();
			g[i] = values[i].getG();
			b[i] = values[i].getB();
		}
		_curveR = new Curve(steps, times, r);
		_curveG = new Curve(steps, times, g);
		_curveB = new Curve(steps, times, b);
		reset();
	}

	public void set(float duration, Color[] values)
	{
		int count = values.length;
		float[] r = new float[count];
		float[] g = new float[count];
		float[] b = new float[count];
		for (int i = 0; i < count; i++)
		{
			r[i] = values[i].getR();
			g[i] = values[i].getG();
			b[i] = values[i].getB();
		}
		_curveR = new SmoothCurve(duration, r);
		_curveG = new SmoothCurve(duration, g);
		_curveB = new SmoothCurve(duration, b);
		reset();
	}

	public void start()
	{
		if (_curveR == null || _curveG == null || _curveB == null)
			return;
		_enabled = true;
	}

	@Override
	protected boolean updateAnimation(float deltatime)
	{
		_time += deltatime * getDirection();
		updateShade();
		return true;
	}

	protected void updateShade()
	{
		_shade.setR(_curveR.getValue(_time));
		_shade.setG(_curveG.getValue(_time));
		_shade.setB(_curveB.getValue(_time));
	}

	@Override
	protected void resetToEnd()
	{
		if (getAnimationType() == Constants.PINGPONG)
			_time = _curveR.getDuration() - (_time - _curveR.getDuration());
		else
			_time = _curveR.getDuration();
		updateShade();
	}

	@Override
	protected void resetToStart()
	{
		if (getAnimationType() == Constants.PINGPONG)
			_time = -_time;
		else
			_time = 0.0f;
		updateShade();
	}

	@Override
	protected boolean isAtEnd()
	{
		return (_time > _curveR.getDuration());
	}

	@Override
	protected boolean isAtStart()
	{
		return (_time < 0f);
	}

	public Color getColor()
	{
		return _shade;
	}

	public String getName()
	{
		return _name;
	}

	public ChangerColor getClone()
	{
		ChangerColor clone = new ChangerColor(_name + "#", getAnimationType());
		clone._curveR = _curveR;
		clone._curveG = _curveG;
		clone._curveB = _curveB;
		clone.reset();
		return clone;
	}

}
