package net.philsprojects.mario.objects;

import static net.philsprojects.game.Constants.HIT_BOTTOM;
import static net.philsprojects.mario.GameConstants.*;
import net.philsprojects.game.ITiledEntity;
import net.philsprojects.game.SoundLibrary;
import net.philsprojects.game.TiledElement;
import net.philsprojects.game.TiledElementInstance;
import net.philsprojects.game.sprites.Sprite;
import net.philsprojects.mario.Level;
import net.philsprojects.mario.Tiles;
import net.philsprojects.mario.mario.Mario;

public class PrizeBox extends TiledElement
{
	public static final int INDEX_HARDBLOCK = 15;

	public PrizeBox()
	{
		super(Tiles.get(PRIZEBOX_ANIM), true, true, true, true, true, true, true, 1f);
	}

	@Override
	public void hit(ITiledEntity entity, int x, int y, int hitType)
	{
		if (!(entity instanceof Mario) || hitType != HIT_BOTTOM)
			return;
		TiledElementInstance i = getInstance(x, y);
		if (i != null)
		{
			Level.getInstance().change(x, y, INDEX_HARDBLOCK);
			i.hit(entity);
		}
	}

	public static class Instance extends TiledElementInstance
	{

		private Object _item = null;

		public Instance(int x, int y, Object item)
		{
			super(x, y);
			_item = item;
		}

		@Override
		public void hit(ITiledEntity entity)
		{
			SoundLibrary.getInstance().play(SOUND_BUMP);
			if (_item instanceof Item)
			{
				Item i = (Item)_item;
				i.initialize();
			}
			if (_item instanceof ITiledEntity)
			{
				Level.getInstance().addEntity((ITiledEntity)_item);
			}
			else if (_item instanceof Sprite)
			{
				Level.getInstance().addObject((Sprite)_item);
			}
		}

	}

}
