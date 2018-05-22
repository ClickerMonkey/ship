package net.philsprojects.mario.objects;

import static net.philsprojects.game.Constants.HIT_BOTTOM;
import static net.philsprojects.mario.GameConstants.*;
import net.philsprojects.game.ITiledEntity;
import net.philsprojects.game.SoundLibrary;
import net.philsprojects.game.TiledElement;
import net.philsprojects.game.TiledElementInstance;
import net.philsprojects.mario.Level;
import net.philsprojects.mario.Tiles;
import net.philsprojects.mario.mario.Mario;

/**
 * The breakable blocks that Mario or a shell can destroy. They can have instances that contain a certain amount of coins.
 * 
 * @author Philip Diffenderfer
 */
public class BrickBlock extends TiledElement
{

	public BrickBlock()
	{
		super(Tiles.get(BRICKBLOCK), true, true, true, true, true, true, true, 1f);
	}

	@Override
	public void hit(ITiledEntity entity, int x, int y, int hitType)
	{
		if (!(entity instanceof Mario) || hitType != HIT_BOTTOM)
			return;
		Instance i = (Instance)getInstance(x, y);
		if (i != null)
		{
			i.hit(entity);
			if (i.isEmpty())
			{
				Level.getInstance().change(x, y, 0);
				SoundLibrary.getInstance().play(SOUND_BLOCKBREAK);
				Level.getInstance().addObject(new BlockBreakEffect(Level.getInstance().getTileBounds(x, y) , entity.getBounds()));
			}
			else
				SoundLibrary.getInstance().play(SOUND_BUMP);
		}
		else
		{
			Level.getInstance().change(x, y, 0);
			SoundLibrary.getInstance().play(SOUND_BLOCKBREAK);
			Level.getInstance().addObject(new BlockBreakEffect(Level.getInstance().getTileBounds(x, y) , entity.getBounds()));
		}
	}

	public static class Instance extends TiledElementInstance
	{

		private int _coins = 0;

		public Instance(int x, int y, int coins)
		{
			super(x, y);
			_coins = coins;
		}

		@Override
		public void hit(ITiledEntity entity)
		{
			if (_coins > 0)
			{
				Level.getInstance().addObject(new CoinPopup(_x, _y + 1));
				Mario.addCoin();
				_coins--;
				SoundLibrary.getInstance().play(SOUND_COIN);
			}
		}

		public boolean isEmpty()
		{
			return (_coins <= 0);
		}

	}

}