package net.philsprojects.mario.objects;

import static net.philsprojects.mario.GameConstants.HARDBLOCK;
import net.philsprojects.game.TiledElement;
import net.philsprojects.mario.Tiles;

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
