package game.objects;

import static game.GameConstants.*;
import static net.philsprojects.game.Constants.*;
import net.philsprojects.game.sprites.Sprite;
import net.philsprojects.game.util.Math;
import game.Tiles;

/**
 * Occurs whenever an IceBall hits the ground or ceiling. It represents the ground freezing and it only lasts 
 *     for a certain amount of seconds until it disappears.
 * 
 * @author Philip Diffenderfer
 */
public class IceBallPatch extends Sprite
{

    private float _time = 0.25f;
    
    public IceBallPatch(IceBall source, int hitType)
    {
	super(ICE_PATCH, 0, 0, 0, 0, Tiles.get(ICE_PATCH));
	setSize(ICE_PATCH_WIDTH, ICE_PATCH_HEIGHT);
	if (hitType == HIT_BOTTOM)
	{
	    setLocation(source.getBounds().getCenterX() - ICE_PATCH_WIDTH / 2f, source.getBounds().getBottom() - ICE_PATCH_HEIGHT);
	}
	else
	{
	    setFlip(FLIP_Y);
	    setLocation(source.getBounds().getCenterX() - ICE_PATCH_WIDTH / 2f, source.getBounds().getTop());
	}
    }

    public void update(float deltatime)
    {
	super.update(deltatime);
	if (_enabled)
	{
	    _time += deltatime;
	    if (_time >= ICE_PATCH_DURATION)
		_enabled = false;
	    setAlpha(Math.cut(1f - _time / ICE_PATCH_DURATION, 0f, 1f));
	}
    }
    
}
