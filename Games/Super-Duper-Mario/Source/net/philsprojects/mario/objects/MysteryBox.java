package net.philsprojects.mario.objects;

import static net.philsprojects.game.Constants.HIT_BOTTOM;
import static net.philsprojects.mario.GameConstants.*;
import net.philsprojects.game.ITiledEntity;
import net.philsprojects.game.SoundLibrary;
import net.philsprojects.game.TiledElement;
import net.philsprojects.game.TiledElementInstance;
import net.philsprojects.game.util.Vector;
import net.philsprojects.mario.Level;
import net.philsprojects.mario.Tiles;

/**
 * A box that must keep track of instances and each instance changes certain tiles on the map to a new value.
 * 
 * @author Philip Diffenderfer
 */
public class MysteryBox extends TiledElement
{

	public MysteryBox()
	{
		super(Tiles.get(MYSTERYBLOCK), true, true, true, true, true, true, 1f);
	}

	@Override
	public void hit(ITiledEntity entity, int x, int y, int hitType)
	{
		if (hitType == HIT_BOTTOM)
		{
			Instance i = (Instance)this.getInstance(x, y);
			if (i != null)
				i.hit(entity);
		}
	}

	@Override
	public boolean tracksInstances()
	{
		return true;
	}

	public static class Instance extends TiledElementInstance
	{

		private int _turnInto = 0;
		private Vector[] _boxes = null;

		public Instance(int x, int y, int turnInto, Vector[] boxes)
		{
			super(x, y);
			_boxes = boxes;
			_turnInto = turnInto;
		}

		@Override
		public void hit(ITiledEntity entity)
		{
			SoundLibrary.getInstance().play(SOUND_BUMP);
			if (_boxes != null)
			{
				for (int i = 0; i < _boxes.length; i++)
					Level.getInstance().change((int)_boxes[i].x, (int)_boxes[i].y, _turnInto);
				_boxes = null;
			}
		}

	}

}
