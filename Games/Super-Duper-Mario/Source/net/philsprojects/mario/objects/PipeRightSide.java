package net.philsprojects.mario.objects;

import static net.philsprojects.mario.GameConstants.PIPE_RIGHTSIDE;
import net.philsprojects.game.TiledElement;
import net.philsprojects.mario.Tiles;

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
