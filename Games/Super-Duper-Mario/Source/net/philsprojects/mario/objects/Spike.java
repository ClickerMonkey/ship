package net.philsprojects.mario.objects;

import static net.philsprojects.game.Constants.*;
import static net.philsprojects.mario.GameConstants.*;
import net.philsprojects.game.ITiledEntity;
import net.philsprojects.game.TiledElement;
import net.philsprojects.mario.Tiles;
import net.philsprojects.mario.mario.Mario;

/**
 * Pointy spikes that injure Mario when he lands on them.
 * 
 * @author Philip Diffenderfer
 */
public class Spike extends TiledElement
{

	public Spike()
	{
		super(Tiles.get(SPIKE), true, true, true, true, true, true, 1f);
	}

	@Override
	public void hit(ITiledEntity entity, int x, int y, int hitType)
	{
		if (hitType == HIT_TOP && entity instanceof Mario)
		{
			final Mario m = (Mario)entity;
			//float overlap = Math.abs(m.getBounds().getCenterX() - Level.getInstance().getTileBounds(x, y).getCenterX());
			//if (overlap <= 10)
			m.takeHit();
		}
	}

}
