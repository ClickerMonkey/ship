/**************************************************\ 
 *            ______ ___    __   _    _____       * 
 *           / ____//   |  /  | / |  / ___/       *
 *          / /___ / /| | /   |/  | / __/         *
 *         / /_| // __  |/ /|  /| |/ /__          *
 *        /_____//_/  |_|_/ |_/ |_|\___/          *
 *     _____ __   _  ______ ______ __   _  _____  *
 *    / ___//  | / // ____//_  __//  | / // ___/  *
 *   / __/ / | |/ // /___   / /  / | |/ // __/    *
 *  / /__ / /|   // /_| /__/ /_ / /|   // /__     *
 *  \___//_/ |__//_____//_____//_/ |__/ \___/     *
 *                                                *
 * Author: Philip Diffenderfer                    *
 *  Class: engine.util.Rectangle                  *
 *                                                *
 \**************************************************/

package net.philsprojects.game.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.philsprojects.game.IBinary;
import net.philsprojects.game.IClone;


/**
 * A Rectangle shape where the location is in the traditional upper-left corner.
 * This class is mainly used to keep track of source rectangles on textures.
 * 
 * @author Philip Diffenderfer
 */
public class Rectangle implements IBinary, IClone<Rectangle>
{

	private float _x = 0f;

	private float _y = 0f;

	private float _width = 0f;

	private float _height = 0f;

	/**
	 * Initializes an empty Rectangle.
	 */
	public Rectangle()
	{
	}

	/**
	 * Initializes a Rectangle with its location and size.
	 * 
	 * @param x =>
	 *           The x-coordinate in pixels of this rectangle.
	 * @param y =>
	 *           The y-coordinate in pixels of this rectangle.
	 * @param width =>
	 *           The width in pixels of this rectangle.
	 * @param height =>
	 *           The height in pixels of this rectangle.
	 */
	public Rectangle(float x, float y, float width, float height)
	{
		_x = x;
		_y = y;
		_width = width;
		_height = height;
	}

	/**
	 * Initializes a Rectangle with its location and size.
	 * 
	 * @param location =>
	 *           The location in pixels of this rectangle.
	 * @param size =>
	 *           The size in pixels of this rectangle.
	 */
	public Rectangle(Vector location, Size size)
	{
		_x = location.x;
		_y = location.y;
		_width = size.width;
		_height = size.height;
	}

	/**
	 * Sets the location of this rectangle.
	 * 
	 * @param location =>
	 *           The new location of this rectangle.
	 */
	public void setLocation(Vector location)
	{
		_x = location.x;
		_y = location.y;
	}

	/**
	 * Sets the location of this rectangle.
	 * 
	 * @param x =>
	 *           The new x-coordinate in pixels of this rectangle.
	 * @param y =>
	 *           The new y-coordinate in pixels of this rectangle.
	 */
	public void setLocation(float x, float y)
	{
		_x = x;
		_y = y;
	}

	/**
	 * Sets the size of this rectangle.
	 * 
	 * @param width =>
	 *           The new width in pixels of this rectangle.
	 * @param height =>
	 *           The new height in pixels of this rectangle.
	 */
	public void setSize(float width, float height)
	{
		_width = width;
		_height = height;
	}

	/**
	 * Sets the size of this rectangle.
	 * 
	 * @param size =>
	 *           The new size in pixels of this rectangle.
	 */
	public void setSize(Size size)
	{
		_width = size.width;
		_height = size.height;
	}

	/**
	 * Sets the x-coordinate of this rectangle.
	 * 
	 * @param x =>
	 *           The new x-coordinate in pixels of this rectangle.
	 */
	public void setX(float x)
	{
		_x = x;
	}

	/**
	 * Sets the y-coordinate of this rectangle.
	 * 
	 * @param y =>
	 *           The new y-coordinate in pixels of this rectangle.
	 */
	public void setY(float y)
	{
		_y = y;
	}

	/**
	 * Sets the width in pixels of this rectangle.
	 * 
	 * @param width =>
	 *           The new width in pixels of this rectangle.
	 */
	public void setWidth(float width)
	{
		_width = width;
	}

	/**
	 * Sets the height in pixels of this rectangle.
	 * 
	 * @param height =>
	 *           The new height in pixels of this rectangle.
	 */
	public void setHeight(float height)
	{
		_height = height;
	}

	/**
	 * Gets the location of this rectangle.
	 * 
	 * @return The reference to the new location instance.
	 */
	public Vector getLocation()
	{
		return new Vector(_x, _y);
	}

	/**
	 * Gets the size of this rectangle.
	 * 
	 * @return The reference to the new size instance.
	 */
	public Size getSize()
	{
		return new Size(_width, _height);
	}

	/**
	 * Gets the x-coordinate in pixels of this rectangle.
	 * 
	 * @return The x-coordinate in pixels of this rectangle.
	 */
	public float getX()
	{
		return _x;
	}

	/**
	 * Gets the y-coordinate in pixels of this rectangle.
	 * 
	 * @return The y-coordinate in pixels of this rectangle.
	 */
	public float getY()
	{
		return _y;
	}

	/**
	 * Gets the width in pixels of this rectangle.
	 * 
	 * @return The width in pixels of this rectangle.
	 */
	public float getWidth()
	{
		return _width;
	}

	/**
	 * Gets the height in pixels of this rectangle.
	 * 
	 * @return The height in pixels of this rectangle.
	 */
	public float getHeight()
	{
		return _height;
	}

	/**
	 * Gets the bounding box version of this rectangle.
	 * 
	 * @return The reference to the new bounding box calculated.
	 */
	public BoundingBox toBoundingBox()
	{
		return new BoundingBox(_x, _y + _height, _x + _width, _y);
	}

	/**
	 * Gets a copy of this rectangle.
	 * 
	 * @return The reference to the new rectangle instance.
	 */
	public Rectangle getClone()
	{
		return new Rectangle(_x, _y, _width, _height);
	}

	/**
	 * Gets a string representation of this rectangle.
	 * 
	 * @return The reference to the new String created.
	 */
	@Override
	public String toString()
	{
		return String.format("{X<%s> Y<%s> Width<%s> Height<%s>}", _x, _y, _width, _height);
	}

	/**
	 * Gets a Rectangle located at the origin and with no size.
	 * 
	 * @return The reference to the new rectangle created.
	 */
	public static Rectangle zero()
	{
		return new Rectangle();
	}

	/**
	 * Gets a Rectangle located at (1, 1) and with a size of (1, 1).
	 * 
	 * @return The reference to the new rectangle created.
	 */
	public static Rectangle one()
	{
		return new Rectangle(1f, 1f, 1f, 1f);
	}

	public Rectangle(DataInputStream reader) throws Exception
	{
		read(reader);
	}

	public void read(DataInputStream reader) throws Exception
	{
		_x = reader.readFloat();
		_y = reader.readFloat();
		_width = reader.readFloat();
		_height = reader.readFloat();
	}

	public void write(DataOutputStream writer) throws Exception
	{
		writer.writeFloat(_x);
		writer.writeFloat(_y);
		writer.writeFloat(_width);
		writer.writeFloat(_height);
	}

}
