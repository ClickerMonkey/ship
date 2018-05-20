/************************************************\ 
 *            [SpriteTileJumping]               * 
 *            ______ ___    __   _    _____     * 
 *           / ____//   |  /  | / |  / ___/     *
 *          / /___ / /| | /   |/  | / __/       *
 *         / /_| // __  |/ /|  /| |/ /__        *
 *        /_____//_/  |_|_/ |_/ |_|\___/        *
 *    _____ __   _  ______ ______ __   _  _____ *
 *   / ___//  | / // ____//_  __//  | / // ___/ *
 *  / __/ / | |/ // /___   / /  / | |/ // __/   *
 * / /__ / /|   // /_| /__/ /_ / /|   // /__    *
 * \___//_/ |__//_____//_____//_/ |__/ \___/    *
 *                                              *
\************************************************/

package net.philsprojects.game.sprites;

import static net.philsprojects.game.Constants.ONCE_BACKWARD;
import static net.philsprojects.game.Constants.ONCE_FORWARD;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.philsprojects.game.Animated;
import net.philsprojects.game.Constants;
import net.philsprojects.game.ISpriteTile;
import net.philsprojects.game.util.Rectangle;
import net.philsprojects.game.util.Vector;


public class SpriteTileJumping extends Animated implements ISpriteTile
{

	protected int _totalFrames = 0;
	protected int _frame = 0;
	protected float _time = 0.0f;
	protected float _animationSpeed = 0.0f;

	protected String _name = null;
	protected String _texture = null;

	protected Vector[] _points = null;
	protected Rectangle _source = Rectangle.zero();

	public SpriteTileJumping(String name, String texture, int type, int frameWidth, int frameHeight, float animationSpeed, Vector... points)
	{
		super(type);
		_name = name;
		_texture = texture;
		_totalFrames = points.length;
		_source.setSize(frameWidth, frameHeight);
		_animationSpeed = animationSpeed;
		_points = points;
		_enabled = true;
		reset();
	}

	@Override
	protected boolean updateAnimation(float deltatime)
	{
		_time += deltatime;
		if (_time >= _animationSpeed)
		{
			_time -= _animationSpeed;
			setFrame(_frame + getDirection());
			if (getAnimationType() == ONCE_FORWARD && _frame == 0)
				return false;
			else if (getAnimationType() == ONCE_BACKWARD && _frame == _totalFrames - 1)
				return false;
			_source.setLocation(_points[_frame]);
		}
		return true;
	}

	protected void setFrame(int frame)
	{
		_frame = (frame >= 0 ? frame % _totalFrames : _totalFrames + (frame % _totalFrames));
	}

	@Override
	protected boolean isAtEnd()
	{
		return _frame >= _totalFrames;
	}

	@Override
	protected boolean isAtStart()
	{
		return _frame < 0;
	}

	@Override
	protected void resetToEnd()
	{
		if (getAnimationType() == Constants.PINGPONG)
			_frame = _totalFrames;
		else
			_frame = _totalFrames - 1;
		_source.setLocation(_points[_frame]);
	}

	@Override
	protected void resetToStart()
	{
		_frame = 0;	
		_source.setLocation(_points[_frame]);
	}

	public Rectangle getSource()
	{
		return _source;
	}

	public Vector getCurrent()
	{
		return _source.getLocation();
	}

	public float getAnimationSpeed()
	{
		return _animationSpeed;
	}

	public void setAnimationSpeed(float speed)
	{
		_animationSpeed = speed;
	}

	public String getName()
	{
		return _name;
	}

	public String getTexture()
	{
		return _texture;
	}

	public void setTexture(String texture)
	{
		_texture = texture;
	}

	public void dispose()
	{
		_name = null;
		_points = null;
		_source = null;
		_texture = null;
	}

	public SpriteTileJumping getClone()
	{
		return new SpriteTileJumping(_name, _texture, getAnimationType(), (int)_source.getWidth(), (int)_source.getHeight(), _animationSpeed, _points);
	}

	public SpriteTileJumping(DataInputStream reader) throws Exception
	{
		super(0);
		read(reader);
	}

	public void read(DataInputStream reader) throws Exception
	{
		_name = reader.readUTF();
		_texture = reader.readUTF();
		_totalFrames = reader.readInt();
		int type = reader.readInt();
		int total = reader.readInt();
		_points = new Vector[total];
		for (int i = 0; i < total; i++)
			_points[i] = new Vector(reader);
		_source.setWidth(reader.readInt());
		_source.setHeight(reader.readInt());
		_animationSpeed = reader.readFloat();
		setType(type);
	}

	public void write(DataOutputStream writer) throws Exception
	{
		writer.writeUTF(_name);
		writer.writeUTF(_texture);
		writer.writeInt(_totalFrames);
		writer.writeInt(getAnimationType());
		writer.writeInt(_points.length);
		for (int i = 0; i < _points.length; i++)
			_points[i].write(writer);
		writer.writeInt((int)_source.getWidth());
		writer.writeInt((int)_source.getHeight());
		writer.writeFloat(_animationSpeed);
	}

}
