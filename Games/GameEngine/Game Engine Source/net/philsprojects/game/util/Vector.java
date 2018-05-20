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
 *  Class: engine.util.Vector                     *
 *                                                *
 \**************************************************/

package net.philsprojects.game.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.philsprojects.game.IBinary;
import net.philsprojects.game.IClone;


/**
 * A 2-Dimensional point with an x and y coordinate.
 * 
 * @author Philip Diffenderfer
 */
public class Vector implements IBinary, IClone<Vector>
{

	public float x = 0f;

	public float y = 0f;

	/**
	 * Initializes a vector at the origin.
	 */
	public Vector()
	{
	}

	/**
	 * Initializes a vector with its x and y values.
	 * 
	 * @param x =>
	 *           The x-coordinate of this vector.
	 * @param y =>
	 *           The y-coordinate of this vector.
	 */
	public Vector(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Adds a vectors x and y coordinates to this vectors coordinates.
	 * 
	 * @param v =>
	 *           The vector to add x and y coordinates from.
	 */
	public void add(Vector v)
	{
		x += v.x;
		y += v.y;
	}

	/**
	 * Adds a value to this vectors coordinates.
	 * 
	 * @param v =>
	 *           The value to add.
	 */
	public void add(float v)
	{
		x += v;
		y += v;
	}

	/**
	 * Adds x and y coordinates to this vectors coordinates.
	 * 
	 * @param x =>
	 *           The x-coordinate to add to this vectors x-coordinate.
	 * @param x =>
	 *           The y-coordinate to add to this vectors y-coordinate.
	 */
	public void add(float x, float y)
	{
		this.x += x;
		this.y += y;
	}

	/**
	 * Multiplies a vectors x and y coordinates to this vectors coordinates.
	 * 
	 * @param v =>
	 *           The vector to multiply x and y coordinates from.
	 */
	public void multiply(Vector v)
	{
		x *= v.x;
		y *= v.y;
	}

	/**
	 * Multiplies a value to this vectors coordinates.
	 * 
	 * @param v =>
	 *           The value to multiply.
	 */
	public void multiply(float v)
	{
		x *= v;
		y *= v;
	}

	/**
	 * Subtracts a vectors x and y coordinates from this vectors coordinates.
	 * 
	 * @param v =>
	 *           The vector to subtract x and y coordinates from.
	 */
	public void subtract(Vector v)
	{
		x -= v.x;
		y -= v.y;
	}

	/**
	 * Subtracts a value from this vectors coordinates.
	 * 
	 * @param v =>
	 *           The value to subtract.
	 */
	public void subtract(float v)
	{
		x -= v;
		y -= v;
	}

	/**
	 * Divides a vectors x and y coordinates to this vectors coordinates.
	 * 
	 * @param v =>
	 *           The vector to divide x and y coordinates from.
	 */
	public void divide(Vector v)
	{
		x /= (v.x == 0 ? 1f : v.x);
		y /= (v.y == 0 ? 1f : v.y);
	}

	/**
	 * Divides a value from this vectors coordinates.
	 * 
	 * @param v =>
	 *           The value to divide.
	 */
	public void divide(float v)
	{
		if (v == 0)
			return;
		x /= v;
		y /= v;
	}

	/**
	 * Sets this vectors coordinates based on an x and a y.
	 * 
	 * @param x =>
	 *           The new x-coordinate of this vector.
	 * @param y =>
	 *           The new y-coordinate of this vector.
	 */
	public void set(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets this vectors coordinates based on another vector.
	 * 
	 * @param v =>
	 *           The new values of this vector.
	 */
	public void set(Vector v)
	{
		x = v.x;
		y = v.y;
	}

	/**
	 * Gets a copy of this vector.
	 * 
	 * @return The reference to a new vector instance.
	 */
	public Vector getClone()
	{
		return new Vector(x, y);
	}

	/**
	 * Gets a string representation of this vector.
	 * 
	 * @return The reference to the new String created.
	 */
	@Override
	public String toString()
	{
		return String.format("{X<%s>, Y<%s>}", x, y);
	}

	/**
	 * Gets a vector with an x and y of 0.
	 * 
	 * @return The reference to the new vector created.
	 */
	public static Vector zero()
	{
		return new Vector(0f, 0f);
	}

	/**
	 * Gets a vector with an x and y of 1.
	 * 
	 * @return The reference to the new vector created.
	 */
	public static Vector one()
	{
		return new Vector(1f, 1f);
	}

	/**
	 * Returns a vector calculated by subtracting v2 from v1.
	 * 
	 * @param v1 =>
	 *           The vector to subtract from.
	 * @param v2 =>
	 *           The vector being subtracted.
	 * @return The reference to the new vector created.
	 */
	public static Vector subtract(Vector v1, Vector v2)
	{
		return new Vector(v1.x - v2.x, v1.y - v2.y);
	}

	/**
	 * Returns a vector calculated by adding v1 and v2.
	 * 
	 * @param v1 =>
	 *           The first vector to add from.
	 * @param v2 =>
	 *           The second vector to add from.
	 * @return The reference to the new vector created.
	 */
	public static Vector add(Vector v1, Vector v2)
	{
		return new Vector(v1.x + v2.x, v1.y + v2.y);
	}

	/**
	 * Returns a vector calculated by adding value to v.
	 * 
	 * @param v =>
	 *           The vector to add to.
	 * @param value =>
	 *           The value to add.
	 * @return The reference to the new vector created.
	 */
	public static Vector add(Vector v, float value)
	{
		return new Vector(v.x + value, v.y + value);
	}

	/**
	 * Returns a vector calculated by multiplying v1 and v2.
	 * 
	 * @param v1 =>
	 *           The first vector to multiply from.
	 * @param v2 =>
	 *           The second vector to multiply from.
	 * @return The reference to the new vector created.
	 */
	public static Vector multiply(Vector v1, Vector v2)
	{
		return new Vector(v1.x * v2.x, v1.y * v2.y);
	}

	/**
	 * Returns a vector calculated by multiplying v by value.
	 * 
	 * @param v =>
	 *           The vector to multiply from.
	 * @param value =>
	 *           The value to multiply by.
	 * @return The reference to the new vector created.
	 */
	public static Vector multiply(Vector v, float value)
	{
		return new Vector(v.x * value, v.y * value);
	}

	/**
	 * Returns a vector calculated by dividing v1 by v2.
	 * 
	 * @param v1 =>
	 *           The vector to divide from.
	 * @param v2 =>
	 *           The vector to divide by.
	 * @return The reference to the new vector created.
	 */
	public static Vector divide(Vector v1, Vector v2)
	{
		return new Vector(v2.x != 0f ? v1.x / v2.x : 0f, v2.y != 0f ? v1.y / v2.y : 0f);
	}

	/**
	 * Returns a vector calculated by dividing v by value.
	 * 
	 * @param v =>
	 *           The vector to divide from.
	 * @param value =>
	 *           The value to divide by.
	 * @return The reference to the new vector created.
	 */
	public static Vector divide(Vector v, float value)
	{
		return (value == 0f ? null : new Vector(v.x / value, v.y / value));
	}

	/**
	 * Returns the distance between two vectors.
	 * 
	 * @param v1 =>
	 *           The first vector.
	 * @param v2 =>
	 *           The second vector.
	 * @return The distance between the two vectors.
	 */
	public static float distance(Vector v1, Vector v2)
	{
		return Math.distance(v1, v2);
	}

	/**
	 * Returns the non-squared distance between the two vectors.
	 * 
	 * @param v1 =>
	 *           The first vector.
	 * @param v2 =>
	 *           The second vector.
	 * @return The non-squared distance between the two vectors.
	 */
	public static float distanceSquared(Vector v1, Vector v2)
	{
		return Math.distanceSquared(v1, v2);
	}

	/**
	 * Returns a vector between v1 and v2 determined by delta.
	 * 
	 * @param v1 =>
	 *           The first vector.
	 * @param v2 =>
	 *           The second vector.
	 * @param delta =>
	 *           A value from 0 to 1 where 0 is at v1, 1 is at v2, and 0.5 is
	 *           right in the middle.
	 * @return The reference to the new vector created.
	 */
	public static Vector delta(Vector v1, Vector v2, float delta)
	{
		return add(multiply(subtract(v2, v1), delta), v1);
	}

	// <==========================================> //
	// <=== IBinary Interface ====================> //
	// <==========================================> //

	public Vector(DataInputStream reader) throws Exception
	{
		read(reader);
	}

	public void read(DataInputStream reader) throws Exception
	{
		x = reader.readFloat();
		y = reader.readFloat();
	}

	public void write(DataOutputStream writer) throws Exception
	{
		writer.writeFloat(x);
		writer.writeFloat(y);
	}

}
