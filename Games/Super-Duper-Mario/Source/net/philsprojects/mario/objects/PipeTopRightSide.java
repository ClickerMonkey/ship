package net.philsprojects.mario.objects;

import static net.philsprojects.mario.GameConstants.PIPE_TOPRIGHTSIDE;
import net.philsprojects.game.TiledElement;
import net.philsprojects.mario.Tiles;

/**
 * The top right opening of a pipe.
 * 
 * @author Philip Diffenderfer
 */
public class PipeTopRightSide extends TiledElement
{
	public PipeTopRightSide()
	{
		super(Tiles.get(PIPE_TOPRIGHTSIDE), true, true, false, false, true, true, false, 1f);
	}
}
