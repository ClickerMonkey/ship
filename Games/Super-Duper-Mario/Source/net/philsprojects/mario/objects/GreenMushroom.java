package net.philsprojects.mario.objects;

import static net.philsprojects.game.Constants.HIT_LEFT;
import static net.philsprojects.game.Constants.HIT_RIGHT;
import static net.philsprojects.mario.GameConstants.MUSHROOM_GREEN;
import static net.philsprojects.mario.GameConstants.MUSHROOM_GREEN_GRAVITY;
import static net.philsprojects.mario.GameConstants.MUSHROOM_GREEN_HEIGHT;
import static net.philsprojects.mario.GameConstants.MUSHROOM_GREEN_SPEED;
import static net.philsprojects.mario.GameConstants.MUSHROOM_GREEN_TERMINAL;
import static net.philsprojects.mario.GameConstants.MUSHROOM_GREEN_WIDTH;
import static net.philsprojects.mario.GameConstants.SOUND_1UP;
import static net.philsprojects.mario.GameConstants.TILE_HEIGHT;
import static net.philsprojects.mario.GameConstants.TILE_WIDTH;
import net.philsprojects.game.ITiledEntity;
import net.philsprojects.game.SoundLibrary;
import net.philsprojects.game.TiledElement;
import net.philsprojects.game.util.Math;
import net.philsprojects.game.util.Vector;
import net.philsprojects.mario.Tiles;
import net.philsprojects.mario.mario.Mario;

/**
 * An Item that gives Mario an extra life if he hits it.
 * 
 * @author Philip Diffenderfer
 */
public class GreenMushroom extends Item
{

	private Vector _velocity = Vector.zero();

	public GreenMushroom(int x, int y, int direction)
	{
		super(MUSHROOM_GREEN, (x * TILE_WIDTH) - (MUSHROOM_GREEN_WIDTH - TILE_WIDTH) / 2f, y * TILE_HEIGHT, MUSHROOM_GREEN_WIDTH, MUSHROOM_GREEN_HEIGHT, Tiles.get(MUSHROOM_GREEN));
		_velocity = new Vector(MUSHROOM_GREEN_SPEED * direction, 0f);
		updateBounds();
	}

	public void hitEntity(ITiledEntity entity, int hitType)
	{
		if (entity instanceof Mario)
		{
			_enabled = false;
			SoundLibrary.getInstance().play(SOUND_1UP);
		}
	}

	public void hitTile(TiledElement element, int x, int y, int hitType)
	{
		if (hitType == HIT_LEFT)
		{
			_velocity.x = MUSHROOM_GREEN_SPEED;
		}
		else if (hitType == HIT_RIGHT)
		{
			_velocity.x = -MUSHROOM_GREEN_SPEED;
		}
	}

	public void update(float deltatime)
	{
		super.update(deltatime);
		if (_enabled)
		{
			_location.add(_velocity.x * deltatime, _velocity.y * deltatime);
			_velocity.add(0f, MUSHROOM_GREEN_GRAVITY * deltatime);
			_velocity.y = Math.max(_velocity.y, MUSHROOM_GREEN_TERMINAL);
			updateBounds();
		}
	}

	@Override
	public void initialize()
	{

	}

}
