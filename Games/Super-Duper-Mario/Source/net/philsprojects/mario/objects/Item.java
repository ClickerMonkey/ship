package net.philsprojects.mario.objects;

import static net.philsprojects.mario.GameConstants.GROUP_ITEM;
import net.philsprojects.game.ISpriteTile;
import net.philsprojects.game.ITiledEntity;
import net.philsprojects.game.sprites.Sprite;
import net.philsprojects.game.util.BoundingBox;

/**
 * The base class for any item that Mario can pick up, it handles bounds calculation and implements 
 *      the default methods from the ITiledEntity class.
 *      
 * @author Philip Diffenderfer
 */
public abstract class Item extends Sprite implements ITiledEntity
{

	private float _boundsTopOffset = 2;
	private float _boundsLeftOffset = 4;
	private float _boundsRightOffset = 4;
	private float _boundsBottomOffset = 2;

	private BoundingBox _bounds = BoundingBox.zero();
	private BoundingBox _lastBounds = BoundingBox.zero();

	public Item(String name, float x, float y, float width, float height, ISpriteTile tile)
	{
		super(name, x, y, width, height, tile);
	}

	public abstract void initialize();

	public boolean acceptsEntityHit()
	{
		return true;
	}

	public boolean acceptsTileHit()
	{
		return true;
	}

	public String getGroupID()
	{
		return GROUP_ITEM;
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

	public void updateBounds()
	{
		_lastBounds = _bounds;
		_bounds = new BoundingBox(getX() + _boundsLeftOffset, getY() + getHeight() - _boundsTopOffset, getX() + getWidth() - _boundsRightOffset, getY() + _boundsBottomOffset);
	}

	public boolean isUserDrawn()
	{
		return false;
	}

	@Override
	public BoundingBox getBounds()
	{
		return _bounds;
	}

	public BoundingBox getLastBounds()
	{
		return _lastBounds;
	}

	public boolean removingEntity()
	{
		return true;
	}

	public boolean correctsIntersection()
	{
		return false;
	}
}
