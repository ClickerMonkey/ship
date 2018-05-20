/************************************************\ 
 *             [SpriteTileAnimated]             * 
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
import net.philsprojects.game.util.Rectangle;
import net.philsprojects.game.util.Vector;


public class SpriteTileAnimated extends Animated implements ISpriteTile
{

	protected int _frame = 0;
	protected int _totalFrames = 0;
	protected int _columns = 0;
	protected float _animationSpeed = 0f;
	protected float _time = 0f;
	protected String _name = null;
	protected String _texture = null;
	protected Rectangle _source = Rectangle.zero();
	protected Vector _offset = Vector.zero();

	public SpriteTileAnimated(String name, String texture, int type, int columns, int totalFrames, int frameWidth, int frameHeight, float animationSpeed)
	{
		this(name, texture, type, columns, totalFrames, frameWidth, frameHeight, 0, 0, animationSpeed);
	}

	public SpriteTileAnimated(String name, String texture, int type, int columns, int totalFrames, int frameWidth, int frameHeight, int offsetX, int offsetY, float animationSpeed)
	{
		super(type);
		_name = name;
		_texture = texture;
		_columns = columns;
		_totalFrames = totalFrames;
		_animationSpeed = animationSpeed;
		_source.setSize(frameWidth, frameHeight);
		_offset.set(offsetX, offsetY);
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
			updateSource();
		}
		return true;
	}

	protected void updateSource()
	{
		_source.setX((_frame % _columns) * _source.getWidth() + _offset.x);
		_source.setY((_frame / _columns) * _source.getHeight() + _offset.y);
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
		_frame = _totalFrames - 1;
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
		return _totalFrames;
	}

	public int getColumns()
	{
		return _columns;
	}

	public void dispose()
	{
		_name = null;
		_texture = null;
		_offset = null;
		_source = null;
	}

	public SpriteTileAnimated getClone()
	{
		return new SpriteTileAnimated(_name, _texture, getAnimationType(), _columns, _totalFrames, (int)_source.getWidth(), (int)_source.getHeight(), (int)_offset.x, (int)_offset.y,
				_animationSpeed);
	}


	public SpriteTileAnimated(DataInputStream reader) throws Exception
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
		_totalFrames = reader.readInt();
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
		writer.writeInt(_totalFrames);
		writer.writeInt((int)_source.getWidth());
		writer.writeInt((int)_source.getHeight());
		_offset.write(writer);
		writer.writeFloat(_animationSpeed);
	}

}
