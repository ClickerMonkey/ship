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
 *  Class: engine.util.Size                       *
 *                                                *
 \**************************************************/

package net.philsprojects.game.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.philsprojects.game.IBinary;
import net.philsprojects.game.IClone;


/**
 * A 2-Dimensional size with a width and height in pixels.
 * 
 * @author Philip Diffenderfer
 */
public class Size implements IBinary, IClone<Size>
{

	public float width = 0f;

	public float height = 0f;

	/**
	 * Initializes a new size with no width and height.
	 */
	public Size()
	{
	}

	/**
	 * Initializes a new size with a width and height.
	 * 
	 * @param width =>
	 *           The width in pixels of this size.
	 * @param height =>
	 *           The height in pixels of this size.
	 */
	public Size(float width, float height)
	{
		this.width = width;
		this.height = height;
	}

	/**
	 * Sets this size according to another.
	 * 
	 * @param size =>
	 *           The size to copy from.
	 */
	public void set(Size size)
	{
		width = size.width;
		height = size.height;
	}

	/**
	 * Sets the width and height of this size.
	 * 
	 * @param width =>
	 *           The new width in pixels of this size.
	 * @param height =>
	 *           The new height in pixels of this size.
	 */
	public void set(float width, float height)
	{
		this.width = width;
		this.height = height;
	}

	/**
	 * Gets a copy of this Size.
	 * 
	 * @return The reference to the new size instance.
	 */
	public Size getClone()
	{
		return new Size(width, height);
	}

	/**
	 * Gets a string representation of this size.
	 * 
	 * @return The reference to the new String created.
	 */
	@Override
	public String toString()
	{
		return String.format("{Width<%s> Height<%s>}", width, height);
	}

	/**
	 * Gets a size with a width and height of 0.
	 * 
	 * @return The reference to the new size created.
	 */
	public static Size zero()
	{
		return new Size(0f, 0f);
	}

	/**
	 * Gets a size with a width and height of 1.
	 * 
	 * @return The reference to the new size created.
	 */
	public static Size one()
	{
		return new Size(1f, 1f);
	}


	public Size(DataInputStream reader) throws Exception
	{
		read(reader);
	}

	public void read(DataInputStream reader) throws Exception
	{
		width = reader.readFloat();
		height = reader.readFloat();
	}

	public void write(DataOutputStream writer) throws Exception
	{
		writer.writeFloat(width);
		writer.writeFloat(height);
	}

}
