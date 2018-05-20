/************************************************\ 
 *              [SpriteTileFramed]              * 
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
import net.philsprojects.game.ISpriteTile;
import net.philsprojects.game.util.Helper;
import net.philsprojects.game.util.Rectangle;
import net.philsprojects.game.util.Vector;


public class SpriteTileFramed extends Animated implements ISpriteTile
{

	protected int _frame = 0;
	protected int _columns = 0;
	protected float _animationSpeed = 0f;
	protected float _time = 0f;
	protected String _name = null;
	protected String _texture = null;
	protected Rectangle _source = Rectangle.zero();
	protected Vector _offset = Vector.zero();
	protected int[] _frames = null;

	public SpriteTileFramed(String name, String texture, int type, int columns, int frameWidth, int frameHeight, float animationSpeed, int[] frames)
	{
		this(name, texture, type, columns, frameWidth, frameHeight, 0, 0, animationSpeed, frames);
	}

	public SpriteTileFramed(String name, String texture, int type, int columns, int frameWidth, int frameHeight, int offsetX, int offsetY, float animationSpeed, int[] frames)
	{
		super(type);
		_name = name;
		_texture = texture;
		_columns = columns;
		_animationSpeed = animationSpeed;
		_source.setSize(frameWidth, frameHeight);
		_offset.set(offsetX, offsetY);
		_enabled = true;
		_frames = frames;
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
			else if (getAnimationType() == ONCE_BACKWARD && _frame == _frames.length - 1)
				return false;
			updateSource();
		}
		return true;
	}

	protected void updateSource()
	{
		int actual = _frames[_frame];
		_source.setX((actual % _columns) * _source.getWidth() + _offset.x);
		_source.setY((actual / _columns) * _source.getHeight() + _offset.y);
	}

	protected void setFrame(int frame)
	{
		_frame = (frame >= 0 ? frame % _frames.length : _frames.length + (frame % _frames.length));
	}

	@Override
	protected boolean isAtEnd()
	{
		return _frame >= _frames.length;
	}

	@Override
	protected boolean isAtStart()
	{
		return _frame < 0;
	}

	@Override
	protected void resetToEnd()
	{
		_frame = _frames.length - 1;
		_time = 0f;
		updateSource();
	}

	@Override
	protected void resetToStart()
	{
		_frame = 0;
		_time = 0f;
		updateSource();
	}

	public float getAnimationSpeed()
	{
		return _animationSpeed;
	}

	public Rectangle getSource()
	{
		return _source;
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

	public int getFrameWidth()
	{
		return (int)_source.getWidth();
	}

	public int getFrameHeight()
	{
		return (int)_source.getHeight();
	}

	public int getTotalFrames()
	{
		return _frames.length;
	}

	public int getColumns()
	{
		return _columns;
	}

	public void dispose()
	{
		_name = null;
		_frames = null;
		_offset = null;
		_source = null;
		_texture = null;
	}

	public SpriteTileFramed getClone()
	{
		return new SpriteTileFramed(_name, _texture, getAnimationType(), _columns, (int)_source.getWidth(), (int)_source.getHeight(), (int)_offset.x, (int)_offset.y,
				_animationSpeed,  Helper.copy(_frames));
	}

	public SpriteTileFramed(DataInputStream reader) throws Exception
	{
		super(0);
		read(reader);
	}

	public void read(DataInputStream reader) throws Exception
	{
		_name = reader.readUTF();
		_texture = reader.readUTF();
		int type = reader.readInt();
		_columns = reader.readInt();
		int total = reader.readInt();
		_frames = new int[total];
		for (int i = 0; i < total; i++)
			_frames[i] = reader.readInt();
		_source.setWidth(reader.readInt());
		_source.setHeight(reader.readInt());
		_offset.read(reader);
		_animationSpeed = reader.readFloat();
		setType(type);
	}

	public void write(DataOutputStream writer) throws Exception
	{
		writer.writeUTF(_name);
		writer.writeUTF(_texture);
		writer.writeInt(getAnimationType());
		writer.writeInt(_columns);
		writer.writeInt(_frames.length);
		for (int i = 0; i < _frames.length; i++)
			writer.writeInt(_frames[i]);
		writer.writeInt((int)_source.getWidth());
		writer.writeInt((int)_source.getHeight());
		_offset.write(writer);
		writer.writeFloat(_animationSpeed);
	}

}
