package net.philsprojects.mario.objects;

import static net.philsprojects.mario.GameConstants.*;
import net.philsprojects.game.ITiledEntity;
import net.philsprojects.game.sprites.AnimatedSprite;
import net.philsprojects.game.util.BoundingBox;

/**
 * The base class for any enemy, it handles bounds calculation and implements the default methods 
 *      from the ITiledEntity class.
 *      
 * @author Philip Diffenderfer
 */
public abstract class Enemy extends AnimatedSprite implements ITiledEntity
{

	protected float _boundsTopOffset = 2;
	protected float _boundsLeftOffset = 3;
	protected float _boundsRightOffset = 3;
	protected float _boundsBottomOffset = 2;

	protected BoundingBox _bounds = BoundingBox.zero();
	protected BoundingBox _lastBounds = BoundingBox.zero();

	public Enemy(String name, float x, float y, float width, float height, int animations)
	{
		super(name, x, y, width, height, animations);
	}

	public void kill()
	{
		_enabled = false;
		deathEvent();
	}

	public abstract void deathEvent();

	public boolean acceptsEntityHit()
	{
		return true;
	}

	public boolean acceptsTileHit()
	{
		return true;
	}

	public boolean correctsIntersection()
	{
		return false;
	}

	public String getGroupID()
	{
		return GROUP_ENEMY;
	}

	public BoundingBox getBounds()
	{
		return _bounds;
	}

	public BoundingBox getLastBounds()
	{
		return _lastBounds;
	}

	public boolean isUserDrawn()
	{
		return false;
	}

	public boolean removingEntity()
	{
		return true;
	}

	public void updateBounds()
	{
		_lastBounds = _bounds;
		_bounds = new BoundingBox(getX() + _boundsLeftOffset, getY() + getHeight() - _boundsTopOffset, getX() + getWidth() - _boundsRightOffset, getY() + _boundsBottomOffset);
	}

	public void setBottom(float bottom)
	{
		setY(bottom - _boundsBottomOffset);
		updateBounds();
	}

	public void setLeft(float left)
	{
		setX(left - _boundsLeftOffset);
		updateBounds();
	}

	public void setRight(float right)
	{
		setX(right - getWidth() + _boundsRightOffset);
		updateBounds();
	}

	public void setTop(float top)
	{
		setY(top - getHeight() - _boundsTopOffset);
		updateBounds();
	}

}
