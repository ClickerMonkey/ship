package game.objects;

import static game.GameConstants.*;
import net.philsprojects.game.ITiledEntity;
import net.philsprojects.game.TiledElement;
import game.Tiles;
import game.mario.Mario;

/**
 * The flower item that appears above a prizebox. Once Mario hits it, it changes his power state to Fire Power Behavior.
 * 
 * @author Philip Diffenderfer
 */
public class Flower extends Item
{
    
    public Flower(int x, int y)
    {
	super(FLOWER, (x * TILE_WIDTH) - (FLOWER_WIDTH - TILE_WIDTH) / 2f, y * TILE_HEIGHT, FLOWER_WIDTH, FLOWER_HEIGHT, Tiles.get(FLOWER));
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
