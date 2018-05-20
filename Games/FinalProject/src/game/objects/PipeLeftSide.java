package game.objects;

import static game.GameConstants.PIPE_LEFTSIDE;
import net.philsprojects.game.TiledElement;
import game.Tiles;

/**
 * The left side of a pipe.
 * 
 * @author Philip Diffenderfer
 */
public class PipeLeftSide extends TiledElement
{

    public PipeLeftSide()
    {
	super(Tiles.get(PIPE_LEFTSIDE), false, false, false, true, true, true, false, 1f);
    }

}
