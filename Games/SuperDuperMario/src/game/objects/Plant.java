package game.objects;

import static game.GameConstants.*;
import net.philsprojects.game.ITiledEntity;
import net.philsprojects.game.TiledElement;
import net.philsprojects.game.util.Math;
import game.Level;
import game.Tiles;
import game.mario.Mario;

/**
 * The plant enemy that hides in pipes or can hide behind anything. It pops up every so many 
 *      seconds and stays there for so many seconds until lowering again.
 *      
 * @author Philip Diffenderfer
 */
public class Plant extends Enemy
{

    private static final int HIDING = 0;
    private static final int RISING = 1;
    private static final int VISIBLE = 2;
    private static final int LOWERING = 3;

    private int _mode = HIDING;
    private float _time = 0f;
    private float _restBottom = 0f;
    private float _visibleBottom = 0f;

    public Plant(int x, int y, int pipeHeight)
    {
	super(PLANT, x * TILE_WIDTH + (TILE_WIDTH * 2 - PLANT_WIDTH) / 2f, y * TILE_HEIGHT, PLANT_WIDTH, PLANT_HEIGHT, 3);
	addAnimation(Tiles.get(PLANT_CLOSED));
	addAnimation(Tiles.get(PLANT_OPEN));
	addAnimation(Tiles.get(PLANT_CHOMP));
	playAnimation(PLANT_CLOSED);
	_restBottom = y * TILE_HEIGHT;
	_visibleBottom = (y + pipeHeight) * TILE_HEIGHT;
	_boundsTopOffset = 6;
	_boundsLeftOffset = 15;
	_boundsRightOffset = 15;
	_boundsBottomOffset = 0;
	updateBounds();
    }

    public void update(float deltatime)
    {
	super.update(deltatime);
	if (_enabled)
	{
	    _time += deltatime;
	    if (_mode == HIDING)
	    {
		playAnimation(PLANT_OPEN);
		if (_time >= PLANT_HIDDEN_DURATION)
		{
		    _time -= PLANT_HIDDEN_DURATION;
		    _mode = RISING;
		}
	    }
	    else if (_mode == RISING)
	    {
		playAnimation(PLANT_OPEN);
		float delta = Math.cut(_time / PLANT_APPEAR_TIME, 0f, 1f);
		setY((_visibleBottom - _restBottom) * delta + _restBottom);
		if (_time >= PLANT_APPEAR_TIME)
		{
		    _time -= PLANT_APPEAR_TIME;
		    _mode = VISIBLE;
		}
		updateBounds();
	    }
	    else if (_mode == VISIBLE)
	    {
		playAnimation(PLANT_CHOMP);
		if (_time >= PLANT_VISIBLE_DURATION)
		{
		    _time -= PLANT_VISIBLE_DURATION;
		    _mode = LOWERING;
		}
	    }
	    else if (_mode == LOWERING)
	    {
		playAnimation(PLANT_CLOSED);
		float delta = Math.cut(_time / PLANT_APPEAR_TIME, 0f, 1f);
		setY((_restBottom - _visibleBottom) * delta + _visibleBottom);
		if (_time >= PLANT_APPEAR_TIME)
		{
		    _time -= PLANT_APPEAR_TIME;
		    _mode = HIDING;
		}
		updateBounds();
	    }
	}
    }


    public void hitEntity(ITiledEntity entity, int hitType)
    {
	if (entity.getGroupID() == GROUP_MARIO_PROJECTILE)
	{
	    if (entity instanceof FireBall)
		new DeathEffect(_bounds, DeathEffect.DEATH_FIRE);
	    else if (entity instanceof IceBall)
		new DeathEffect(_bounds, DeathEffect.DEATH_ICE);
	    else
		new DeathEffect(_bounds, DeathEffect.DEATH_OTHER);
	    
	    Level.getInstance().addObject(new PointsPopup(_bounds, "200"));
	    Mario.addPoints(200);
	    
	    _enabled = false;
	}
    }
    
    public void deathEvent()
    {
	new DeathEffect(_bounds, DeathEffect.DEATH_OTHER);
    }

    public void hitTile(TiledElement element, int x, int y, int hitType)
    {

    }

    public boolean isUserDrawn()
    {
	return true;
    }

    @Override
    public boolean acceptsTileHit()
    {
	return false;
    }
    
    @Override
    public void setBottom(float bottom)
    {
    }

    @Override
    public void setLeft(float left)
    {
    }

    @Override
    public void setRight(float right)
    {
    }

    @Override
    public void setTop(float top)
    {
    }

    @Override
    public boolean correctsIntersection()
    {
	return false;
    }

}
