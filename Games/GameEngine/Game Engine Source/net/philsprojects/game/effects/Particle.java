package net.philsprojects.game.effects;

// 40 bytes
public class Particle implements IParticle
{

	public final static int FULL = 1;
	public final static int ADDITIVE = 2;
	public final static int NEGATIVE = 3;
	public final static int NORMAL = 4;

	private float _x = 0f;
	private float _y = 0f;
	private float _initialAngle = 0.0f;
	private float _time = 0f;
	private float _life = 0f;
	private float _velocityX = 0f;
	private float _velocityY = 0f;

	public Particle()
	{

	}

	public void update(float deltatime)
	{
		_time += deltatime;
		_x += _velocityX * deltatime;
		_y += _velocityY * deltatime;
	}

	public final void draw()
	{
	}

	public final boolean isDead()
	{
		return (_time >= _life);
	}

	public float lifeDelta()
	{
		return _time / _life;
	}

	public float getX()
	{
		return _x;
	}

	public float getY()
	{
		return _y;
	}

	public boolean isCustomDraw()
	{
		return false;
	}

	public boolean isVisible()
	{
		return true;
	}

	public void toggleVisibility()
	{
	}

	public boolean isEnabled()
	{
		return true;
	}

	public float getInitialAngle()
	{
		return _initialAngle;
	}

	public float getVelocityX()
	{
		return _velocityX;
	}

	public float getVelocityY()
	{
		return _velocityY;
	}

	public float getLifeTime()
	{
		return _life;
	}

	public float getLife()
	{
		return _time;
	}

	public void setInitialAngle(float angle)
	{
		_initialAngle = angle;
	}

	public void setLifeTime(float lifetime)
	{
		_life = lifetime;
		_time = 0f;
	}

	public void setVisible(boolean visible)
	{
	}

	public void setVelocityX(float x)
	{
		_velocityX = x;
	}

	public void setVelocityY(float y)
	{
		_velocityY = y;
	}

	public void setX(float x)
	{
		_x = x;	
	}

	public void setY(float y)
	{
		_y = y;
	}

}
