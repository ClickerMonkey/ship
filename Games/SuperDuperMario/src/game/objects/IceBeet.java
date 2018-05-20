package game.objects;

import static game.GameConstants.BEET;
import static game.GameConstants.BEET_HEIGHT;
import static game.GameConstants.BEET_WIDTH;
import static game.GameConstants.TILE_WIDTH;
import static game.GameConstants.TILE_HEIGHT;
import net.philsprojects.game.ITiledEntity;
import net.philsprojects.game.TiledElement;
import game.Tiles;
import game.mario.Mario;

/**
 * The ice beet item that appears above a prizebox. Once Mario hits it, it changes his power state to Ice Power Behavior.
 * 
 * @author Philip Diffenderfer
 */
public class IceBeet extends Item
{

    public IceBeet(int x, int y)
    {
	super(BEET, (x * TILE_WIDTH) - (BEET_WIDTH - TILE_WIDTH) / 2f, y * TILE_HEIGHT, BEET_WIDTH, BEET_HEIGHT, Tiles.get(BEET));
	updateBounds();
    }

    @Override
    public boolean acceptsTileHit()
    {
	return false;
    }

    public void hitTile(TiledElement element, int x, int y, int hitType)
    {

    }

    public void hitEntity(ITiledEntity entity, int hitType)
    {
	if (entity instanceof Mario)
	{
	    _enabled = false;
	}
    }

    @Override
    public void initialize()
    {

    }

}
