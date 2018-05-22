package net.philsprojects.game;

public abstract class TiledElementInstance
{
	protected int _x = 0;
	protected int _y = 0;

	public TiledElementInstance(int x, int y)
	{
		_x = x;
		_y = y;
	}

	public abstract void hit(ITiledEntity entity);

	public int getX()
	{
		return _x;
	}

	public int getY()
	{
		return _y;
	}

	public boolean equals(int x, int y)
	{
		return !(x != _x || y != _y);
	}

}
