/************************************************\ 
 *              [SpriteTileStatic]              * 
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

import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.philsprojects.game.ISpriteTile;
import net.philsprojects.game.util.Rectangle;


public class SpriteTileStatic implements ISpriteTile
{

	protected Rectangle _source = null;
	protected String _name = null;
	protected String _texture = null;

	public SpriteTileStatic(String name, String texture, Rectangle source)
	{
		_name = name;
		_texture = texture;
		_source = source;
	}

	public SpriteTileStatic(String name, String texture, int x, int y, int width, int height)
	{
		_name = name;
		_texture = texture;
		_source = new Rectangle(x, y, width, height);
	}

	public boolean doNextFrame()
	{
		return true;
	}

	public float getAnimationSpeed()
	{
		return 0.0f;
	}

	public Rectangle getSource()
	{
		return _source;
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

	public boolean isEnabled()
	{
		return true;
	}

	public void update(float deltatime)
	{
	}

	public void reset()
	{
	}

	public void setAnimationSpeed(float speed)
	{
	}

	public void dispose()
	{
		_name = null;
		_source = null;
		_texture = null;
	}

	public static ISpriteTile zero()
	{
		return new SpriteTileStatic("", "", null);
	}

	public SpriteTileStatic getClone()
	{
		return new SpriteTileStatic(_name, _texture, _source);
	}

	public SpriteTileStatic(DataInputStream reader) throws Exception
	{
		read(reader);
	}

	public void read(DataInputStream reader) throws Exception
	{
		_name = reader.readUTF();
		_texture = reader.readUTF();
		_source.setX(reader.readInt());
		_source.setY(reader.readInt());
		_source.setWidth(reader.readInt());
		_source.setHeight(reader.readInt());
	}

	public void write(DataOutputStream writer) throws Exception
	{
		writer.writeUTF(_name);
		writer.writeUTF(_texture);
		writer.writeInt((int)_source.getX());
		writer.writeInt((int)_source.getY());
		writer.writeInt((int)_source.getWidth());
		writer.writeInt((int)_source.getHeight());
	}

}
