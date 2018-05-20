package game.objects;

import static game.GameConstants.*;
import static net.philsprojects.game.Constants.*;
import net.philsprojects.game.ITiledEntity;
import net.philsprojects.game.SoundLibrary;
import net.philsprojects.game.TiledElement;
import net.philsprojects.game.util.BoundingBox;
import net.philsprojects.game.util.Math;
import net.philsprojects.game.util.Vector;
import game.Level;

/**
 * An ice ball that Mario can throw at enemies when he has the Ice Power Behavior. It will last its set
 *      lifetime in seconds or it will only survive 2 wall hits.
 *      
 * @author Philip Diffenderfer
 */
public class IceBall implements ITiledEntity
{

    private Vector _location = Vector.zero();
    private Vector _velocity = Vector.zero();
    private IceBallEffect _effect = null;
    private boolean _enabled = true;
    private float _time = 0f;
    private int _wallHits = 0;
    private BoundingBox _bounds = BoundingBox.zero();
    private BoundingBox _lastBounds = BoundingBox.zero();

    public IceBall(float x, float y, int direction, boolean up)
    {
	_effect = new IceBallEffect();
	_location.set(x, y);
	_effect.setEmitterOffset(x, y);
	_velocity = new Vector(ICE_SPEED * direction, up ? ICE_JUMP : ICE_GRAVITY * 0.5f);
	_bounds = _lastBounds = new BoundingBox(_location.x - ICE_HALFSIZE, _location.y + ICE_HALFSIZE, _location.x + ICE_HALFSIZE, _location.y - ICE_HALFSIZE);
    }

    public boolean acceptsEntityHit()
    {
	return true;
    }

    public boolean acceptsTileHit()
    {
	return true;
    }

    public BoundingBox getBounds()
    {
	return _bounds;
    }
    public BoundingBox getLastBounds()
    {
	return _lastBounds;
    }

    public String getGroupID()
    {
	return GROUP_MARIO_PROJECTILE;
    }

    public void hitEntity(ITiledEntity entity, int hitType)
    {
	if (entity.getGroupID() == GROUP_ENEMY)
	{
	    _enabled = false;
	    _effect.stopCreating();
	    SoundLibrary.getInstance().play(SOUND_BALL);
	}
	else if (entity.getGroupID() == GROUP_PLATFORM && hitType == HIT_BOTTOM)
	{
	    _velocity.y = ICE_JUMP;
	    Level.getInstance().addObject(new IceBallPatch(this, hitType));
	}
    }

    public void hitTile(TiledElement element, int x, int y, int hitType)
    {
	if (hitType == HIT_RIGHT || hitType == HIT_LEFT)
	{
	    _velocity.x *= -1;
	    _wallHits++;
	}
	else if (hitType == HIT_BOTTOM)
	{
	    _velocity.y = ICE_JUMP;
	} 
	else if (hitType == HIT_TOP)
	{
	    _velocity.y = 0;
	}
	if (element instanceof Spike && (hitType == HIT_BOTTOM || hitType == HIT_TOP))
	{
	    _enabled = false;
	    _effect.stopCreating();
	}
	else if (hitType == HIT_TOP || hitType == HIT_BOTTOM)
	    Level.getInstance().addObject(new IceBallPatch(this, hitType));
    }

    public void setBottom(float bottom)
    {
	_location.y = bottom + ICE_HALFSIZE + 1;
	updateBounds();
    }

    public void setLeft(float left)
    {
	_location.x = left + ICE_HALFSIZE;
	updateBounds();
    }

    public void setRight(float right)
    {
	_location.x = right - ICE_HALFSIZE;
	updateBounds();
    }

    public void setTop(float top)
    {
	_location.y = top - ICE_HALFSIZE - 1;
	updateBounds();
    }

    public boolean isEnabled()
    {
	return _enabled;
    }

    public void update(float deltatime)
    {
	if (_enabled)
	{
	    _time += deltatime;
	    if (_time >= ICE_LIFETIME || _wallHits > 1)
	    {
		_enabled = false;
		_effect.stopCreating();
	    }
	    float normal = deltatime / ICE_TIMEFOLD;
	    for (int i = 0; i < ICE_TIMEFOLD; i++)
	    {
		Level.getInstance().handleEntityTileCollisions(this);
		_location.add(_velocity.x * normal, _velocity.y * normal);
		_velocity.add(0f, ICE_GRAVITY * normal);
		_velocity.y = Math.max(_velocity.y, ICE_TERMINAL);
		updateBounds();
	    }
	    _effect.setEmitterOffset(_location);
	}
    }
    
    private void updateBounds()
    {
	_lastBounds = _bounds;
	_bounds = new BoundingBox(_location.x - ICE_HALFSIZE, _location.y + ICE_HALFSIZE, _location.x + ICE_HALFSIZE, _location.y - ICE_HALFSIZE);
    }

    public void draw()
    {

    }

    public boolean isVisible()
    {
	return true;
    }

    public void setVisible(boolean visible)
    {
    }

    public boolean isUserDrawn()
    {
	return true;
    }

    public boolean removingEntity()
    {
	_effect.stopCreating();
	return true;
    }
    
    public boolean correctsIntersection()
    {
	return false;
    }
    
}
