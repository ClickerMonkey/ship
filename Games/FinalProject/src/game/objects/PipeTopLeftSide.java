package game.objects;

import static game.GameConstants.PIPE_TOPLEFTSIDE;
import net.philsprojects.game.TiledElement;
import game.Tiles;

/**
 * The top left opening of a pipe.
 * 
 * @author Philip Diffenderfer
 */
public class PipeTopLeftSide extends TiledElement
{
    public PipeTopLeftSide()
    {
	super(Tiles.get(PIPE_TOPLEFTSIDE), true, false, false, true, true, true, false, 1f);
    }
}
