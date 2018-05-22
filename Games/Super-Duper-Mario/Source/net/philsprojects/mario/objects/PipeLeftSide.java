package net.philsprojects.mario.objects;

import static net.philsprojects.mario.GameConstants.PIPE_LEFTSIDE;
import net.philsprojects.game.TiledElement;
import net.philsprojects.mario.Tiles;

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
