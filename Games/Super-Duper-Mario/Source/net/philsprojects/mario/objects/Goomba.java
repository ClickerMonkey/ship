package net.philsprojects.mario.objects;

import static net.philsprojects.game.Constants.*;
import static net.philsprojects.mario.GameConstants.*;
import net.philsprojects.game.ITiledEntity;
import net.philsprojects.game.SoundLibrary;
import net.philsprojects.game.TiledElement;
import net.philsprojects.game.util.BoundingBox;
import net.philsprojects.game.util.Math;
import net.philsprojects.game.util.Vector;
import net.philsprojects.mario.Level;
import net.philsprojects.mario.Tiles;
import net.philsprojects.mario.mario.Mario;

/**
 * An enemy that walks back and forth on a platform level.
 * 
 * @author Philip Diffenderfer
 */
public class Goomba extends Enemy
{

	private Vector _velocity = Vector.zero();
	private int _direction = FORWARD;

	public Goomba(int x, int y, int direction)
	{
		super(GOOMBA, x * TILE_WIDTH, y * TILE_HEIGHT, GOOMBA_WIDTH, GOOMBA_HEIGHT, 2);
		addAnimation(Tiles.get(GOOMBA_ANIM));
		addAnimation(Tiles.get(GOOMBA_DEAD));
		playAnimation(GOOMBA_ANIM);
		_velocity = new Vector(GOOMBA_SPEED * (_direction = direction), 0f);
		updateBounds();
	}

	public void update(float deltatime)
	{
		super.update(deltatime);
		if (_enabled)
		{
			Vector index = Level.getInstance().getIndex(_bounds);
			TiledElement infront = Level.getInstance().getTile((int)index.x + _direction, (int)index.y - 1);
			if ((infront != null && !infront.blocksTop()) || (infront == null))
			{
				BoundingBox box = Level.getInstance().getTileBounds((int)index.x + _direction, (int)index.y - 1);
				if (_direction == FORWARD && _bounds.getRight() >= box.getLeft())
				{
					_velocity.x = GOOMBA_SPEED * (_direction = BACKWARD);
					setRight(box.getLeft());
				}
				else if (_direction == BACKWARD && _bounds.getLeft() <= box.getRight())
				{
					_velocity.x = GOOMBA_SPEED * (_direction = FORWARD);
					setLeft(box.getRight());
				}
			}
			_location.add(_velocity.x * deltatime, _velocity.y * deltatime);
			_velocity.add(0f, GOOMBA_GRAVITY * deltatime);
			_velocity.y = Math.max(_velocity.y, GOOMBA_TERMINAL);
			updateBounds();
		}
	}

	public void hitEntity(ITiledEntity entity, int hitType)
	{
		if (entity.getGroupID() == GROUP_MARIO_PROJECTILE)
		{
			if (entity instanceof FireBall)
				new DeathEffect(_bounds, DeathEffect.DEATH_FIRE);
			else if (entity instanceof IceBall)
				new DeathEffect(_bounds, DeathEffect.DEATH_ICE);
			else
				new DeathEffect(_bounds, DeathEffect.DEATH_OTHER);

			Level.getInstance().addObject(new PointsPopup(_bounds, "100"));
			Mario.addPoints(100);

			_enabled = false;
		}
	}

	public void deathEvent()
	{	    
		Level.getInstance().addObject(new PointsPopup(_bounds, "100"));
		Mario.addPoints(100);
		new DeathEffect(_bounds, DeathEffect.DEATH_OTHER);
		SoundLibrary.getInstance().play(SOUND_BUMP);
	}

	public void hitTile(TiledElement element, int x, int y, int hitType)
	{
		if (hitType == HIT_LEFT || hitType == HIT_RIGHT)
		{
			_direction *= -1;
			_velocity.x = GOOMBA_SPEED * _direction;
		}
	}

}
