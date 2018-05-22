package net.philsprojects.mario.objects;

import static net.philsprojects.mario.GameConstants.*;
import net.philsprojects.game.IDraw;
import net.philsprojects.game.IName;
import net.philsprojects.game.IUpdate;
import net.philsprojects.game.effects.Range;
import net.philsprojects.game.sprites.Sprite;
import net.philsprojects.game.util.BoundingBox;
import net.philsprojects.game.util.Math;
import net.philsprojects.game.util.Vector;
import net.philsprojects.mario.Tiles;

/**
 * The Effect of a BrickBlock breaking into pieces once hit with a destructable entity.
 * 
 * @author Philip Diffenderfer
 */
public class BlockBreakEffect implements IDraw, IUpdate, IName
{

	private float _time = 0f;
	private Sprite[] _pieces;
	private Vector[] _velocity;

	public BlockBreakEffect(BoundingBox block, BoundingBox destroyer)
	{
		_pieces = new Sprite[4];
		_velocity = new Vector[4];
		_pieces[0] = new Sprite(BLOCK_PIECE1, block.getLeft(), block.getBottom(), TILE_WIDTH, TILE_HEIGHT, Tiles.get(BLOCK_PIECE1));
		_pieces[1] = new Sprite(BLOCK_PIECE2, block.getLeft(), block.getBottom(), TILE_WIDTH, TILE_HEIGHT, Tiles.get(BLOCK_PIECE2));
		_pieces[2] = new Sprite(BLOCK_PIECE3, block.getLeft(), block.getBottom(), TILE_WIDTH, TILE_HEIGHT, Tiles.get(BLOCK_PIECE3));
		_pieces[3] = new Sprite(BLOCK_PIECE4, block.getLeft(), block.getBottom(), TILE_WIDTH, TILE_HEIGHT, Tiles.get(BLOCK_PIECE4));

		float angle = Math.angle(destroyer.getCenterX(), destroyer.getCenterY(), block.getCenterX(), block.getCenterY());
		_velocity[0] = Math.angledVector(angle + Range.random(-30f, 30f), BLOCK_PIECE_SPEED);
		_velocity[1] = Math.angledVector(angle + Range.random(-30f, 30f), BLOCK_PIECE_SPEED);
		_velocity[2] = Math.angledVector(angle + Range.random(-30f, 30f), BLOCK_PIECE_SPEED);
		_velocity[3] = Math.angledVector(angle + Range.random(-30f, 30f), BLOCK_PIECE_SPEED);
	}

	public void draw()
	{
		for (int i = 0; i < 4; i++)
			_pieces[i].draw();
	}

	public void update(float deltatime)
	{
		float lifeDelta = _time / BLOCK_PIECE_LIFE;
		for (int i = 0; i < 4; i++)
		{
			_pieces[i].setAlpha(1f - lifeDelta / 2f);
			_pieces[i].getLocation().add(_velocity[i].x * deltatime, _velocity[i].y * deltatime);
			_pieces[i].setAngle(lifeDelta * -90);
			_velocity[i].add(0f, BLOCK_PIECE_GRAVITY * deltatime);
		}
		_time += deltatime;
		if (_time >= BLOCK_PIECE_LIFE)
		{
			_pieces = null;
			_velocity = null;
		}
	}

	public boolean isEnabled()
	{
		return (_time < BLOCK_PIECE_LIFE);
	}

	public boolean isVisible()
	{
		return true;
	}

	public void setVisible(boolean visible)
	{
	}

	public String getName()
	{
		return BLOCK_PIECE1;
	}

}
