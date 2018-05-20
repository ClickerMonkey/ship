package game.objects;

import static game.GameConstants.STAR;
import static game.GameConstants.STAR_GRAVITY;
import static game.GameConstants.STAR_HEIGHT;
import static game.GameConstants.STAR_SPEED;
import static game.GameConstants.STAR_TERMINAL;
import static game.GameConstants.STAR_WIDTH;
import static game.GameConstants.TILE_WIDTH;
import static game.GameConstants.TILE_HEIGHT;
import static net.philsprojects.game.Constants.HIT_LEFT;
import static net.philsprojects.game.Constants.HIT_RIGHT;
import static net.philsprojects.game.Constants.LOOP_FORWARD;
import net.philsprojects.game.ChangerColor;
import net.philsprojects.game.ITiledEntity;
import net.philsprojects.game.TiledElement;
import net.philsprojects.game.util.Color;
import net.philsprojects.game.util.Math;
import net.philsprojects.game.util.Vector;
import game.Tiles;
import game.mario.Mario;

/**
 * The item which puts Mario in his special star state where he just needs to run into whatever to kill it.
 * 
 * @author Philip Diffenderfer
 */
public class Star extends Item
{

    private StarEffect _effect = null;
    private ChangerColor _star = null;
    private Vector _velocity = Vector.zero();

    public Star(int x, int y, int direction)
    {
	super(STAR, (x * TILE_WIDTH) - (STAR_WIDTH - TILE_WIDTH) / 2f, y * TILE_HEIGHT, STAR_WIDTH, STAR_HEIGHT, Tiles.get(STAR));
	_velocity = new Vector(STAR_SPEED * direction, 0f);
	updateBounds();
    }

    public void initialize()
    {
	_effect = new StarEffect();
	_effect.setLocation(getX(), getY());
	_effect.update(0f);
	
	_star = new ChangerColor("StarColor", LOOP_FORWARD, 0.4f, new Color[] { Color.red(), Color.orange(), Color.yellow(), Color.green(), Color.blue(), Color.purple() });
	_star.update(0f);
    }
    
    public void hitEntity(ITiledEntity entity, int hitType)
    {
	if (entity instanceof Mario)
	{
	    _enabled = false;
	}
    }

    public void hitTile(TiledElement element, int x, int y, int hitType)
    {
	if (hitType == HIT_LEFT)
	{
	    _velocity.x = STAR_SPEED;
	}
	else if (hitType == HIT_RIGHT)
	{
	    _velocity.x = -STAR_SPEED;
	}
    }

    public void update(float deltatime)
    {
	super.update(deltatime);
	if (_enabled)
	{
	    //Physics
	    _location.add(_velocity.x * deltatime, _velocity.y * deltatime);
	    _velocity.add(0f, STAR_GRAVITY * deltatime);
	    _velocity.y = Math.max(_velocity.y, STAR_TERMINAL);
	    updateBounds();
	    //The Color changer
	    _star.update(deltatime);
	    _shade = _star.getColor();
	    //The Special Effects
	    _effect.setParticleColors(_star.getColor(), _star.getColor());
	    _effect.setLocation(getX() + STAR_WIDTH / 2f, getY() + STAR_HEIGHT / 2f);
	}
    }
    
    @Override
    public void draw()
    {
	_effect.draw();
	super.draw();
    }

    public boolean removingEntity()
    {
	_effect.stopCreating();
	return true;
    }

}
