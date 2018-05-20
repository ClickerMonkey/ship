package game.objects;

import static game.GameConstants.HARDBLOCK;
import net.philsprojects.game.TiledElement;
import game.Tiles;

/**
 * The hardest block in the game, where its not breakable and it doesnt interact.
 * 
 * @author Philip Diffenderfer
 */
public class HardBlock extends TiledElement
{

    public HardBlock()
    {
	super(Tiles.get(HARDBLOCK), true, true, true, true, true, true, false, 1f);
    }

}
