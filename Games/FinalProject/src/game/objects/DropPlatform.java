package game.objects;

import static game.GameConstants.*;
import static net.philsprojects.game.Constants.*;
import net.philsprojects.game.ITiledEntity;
import net.philsprojects.game.TiledElement;
import net.philsprojects.game.sprites.Sprite;
import net.philsprojects.game.util.BoundingBox;
import net.philsprojects.game.util.Math;
import net.philsprojects.game.util.Vector;
import game.Tiles;
import game.mario.Mario;

/**
 * A platform that Mario can stand on for so long until it starts falling.
 * 
 * @author Philip Diffenderfer
 */
public class DropPlatform extends Sprite implements ITiledEntity
{
    private static float _boundsTopOffset = 2;
    private static float _boundsLeftOffset = 3;
    private static float _boundsRightOffset = 3;
    private static float _boundsBottomOffset = 2;

    private BoundingBox _bounds = BoundingBox.zero();
    private BoundingBox _lastBounds = BoundingBox.zero();

    private Vector _velocity = Vector.zero();
    private boolean _marioOn = false;
    private boolean _falling = false;
    private float _time = 0f;

    public DropPlatform(int x, int y)
    {
	super(DROPPLATFORM, x * TILE_WIDTH, y * TILE_HEIGHT, DROPPLATFORM_WIDTH, DROPPLATFORM_HEIGHT, Tiles.get(PLATFORM));
	updateBounds();
    }

    @Override
    public void update(float deltatime)
    {
	super.update(deltatime);
	if (_marioOn)
	{
	    _time += deltatime;
	    if (_time >= DROPPLATFORM_WAIT)
	    {
		_falling = true;
	    }
	    _marioOn = false;
	}
	else
	{
	    _time = 0f;
	}
	if (_falling)
	{
	    _location.add(_velocity.x * deltatime, _velocity.y * deltatime);
	    _velocity.add(0f, DROPPLATFORM_GRAVITY * deltatime);
	    _velocity.y = Math.max(_velocity.y, DROPPLATFORM_TERMINAL);
	    updateBounds();
	}
    }
    
    public void updateBounds()
    {
	_lastBounds = _bounds;
	_bounds = new BoundingBox(getX() + _boundsLeftOffset, getY() + getHeight() - _boundsTopOffset, getX() + getWidth() - _boundsRightOffset, getY() + _boundsBottomOffset);
    }
    
    public void hitEntity(ITiledEntity entity, int hitType)
    {
	if (entity instanceof Mario && hitType == HIT_TOP)
	{
	    _marioOn = true;
	    Mario m = (Mario)entity;
	    m.fall(false);
	}
    }

    public boolean acceptsEntityHit()
    {
	return true;
    }

    public boolean acceptsTileHit()
    {
	return false;
    }

    public boolean correctsIntersection()
    {
	return true;
    }

    public String getGroupID()
    {
	return GROUP_PLATFORM;
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


    public void hitTile(TiledElement element, int x, int y, int hitType)
    {
    }

    public boolean isUserDrawn()
    {
	return false;
    }

    public boolean removingEntity()
    {
	return true;
    }

    public void setBottom(float bottom)
    {
    }

    public void setLeft(float left)
    {
    }

    public void setRight(float right)
    {
    }

    public void setTop(float top)
    {
    }

}
