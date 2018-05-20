package game.objects;

import static game.GameConstants.PIPE_RIGHTSIDE;
import net.philsprojects.game.TiledElement;
import game.Tiles;

/**
 * The right side of a pipe.
 * 
 * @author Philip Diffenderfer
 */
public class PipeRightSide extends TiledElement
{
    public PipeRightSide()
    {
	super(Tiles.get(PIPE_RIGHTSIDE), false, true, false, false, true, true, false, 1f);
    }
}
