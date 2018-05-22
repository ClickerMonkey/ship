package net.philsprojects.mario.objects;

import static net.philsprojects.game.Constants.*;
import static net.philsprojects.mario.GameConstants.*;
import net.philsprojects.game.ITiledEntity;
import net.philsprojects.game.TiledElement;
import net.philsprojects.game.util.Math;
import net.philsprojects.game.util.Vector;
import net.philsprojects.mario.Tiles;
import net.philsprojects.mario.mario.Mario;

/**
 * The mushroom which enlarges Mario to the next largest state unless hes at the maximum size state.
 * 
 * @author Philip Diffenderfer
 */
public class RedMushroom extends Item
{

	private Vector _velocity = Vector.zero();

	public RedMushroom(int x, int y, int direction)
	{
		super(MUSHROOM_RED, (x * TILE_WIDTH) - (MUSHROOM_RED_WIDTH - TILE_WIDTH) / 2f, y * TILE_HEIGHT, MUSHROOM_RED_WIDTH, MUSHROOM_RED_HEIGHT, Tiles.get(MUSHROOM_RED));
		_velocity = new Vector(MUSHROOM_RED_SPEED * direction, 0f);
		updateBounds();
	}

	public void hitEntity(ITiledEntity entity, int hitType)
	{
		if (entity instanceof Mario)
		{
			_enabled = false;
		}
	}

	public void hitTile(TiledElement element, int x, int y, int hitType)
	{
		if (hitType == HIT_LEFT)
		{
			_velocity.x = MUSHROOM_RED_SPEED;
		}
		else if (hitType == HIT_RIGHT)
		{
			_velocity.x = -MUSHROOM_RED_SPEED;
		}
	}

	public void update(float deltatime)
	{
		super.update(deltatime);
		if (_enabled)
		{
			_location.add(_velocity.x * deltatime, _velocity.y * deltatime);
			_velocity.add(0f, MUSHROOM_RED_GRAVITY * deltatime);
			_velocity.y = Math.max(_velocity.y, MUSHROOM_RED_TERMINAL);
			updateBounds();
		}
	}

	@Override
	public void initialize()
	{

	}


}
