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
 *  Class: engine.util.Math                       *
 *                                                *
 \**************************************************/

package net.philsprojects.game.util;

/**
 * A Library of functions slightly different from the
 * <code>java.lang.Math</code> class where all trigonometric functions are now
 * degree based and not radian.
 * 
 * @author Philip Diffenderfer
 */
public final class Math
{

	public static float PI = 3.1415926535f;

	/**
	 * A private constructor to ensure only one instance.
	 */
	private Math()
	{

	}

	/**
	 * Returns the square root of a number.
	 */
	public static float sqrt(float number)
	{
		return (float)java.lang.Math.sqrt(number);
	}

	/**
	 * Returns the sin of an angle in degrees.
	 */
	public static float sin(float degree)
	{
		return (float)java.lang.StrictMath.sin(toRadian(degree));
	}

	/**
	 * Returns the cos of an angle in degrees.
	 */
	public static float cos(float degree)
	{
		return (float)java.lang.StrictMath.cos(toRadian(degree));
	}

	/**
	 * Returns the distance between (x1, y1) and (y1, y2).
	 */
	public static float distance(float x1, float y1, float x2, float y2)
	{
		return (float)java.lang.StrictMath.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	/**
	 * Returns the distance between the two vectors.
	 */
	public static float distance(Vector v1, Vector v2)
	{
		return distance(v1.x, v1.y, v2.x, v2.y);
	}

	/**
	 * Returns the distance between the vector and the origin.
	 */
	public static float distance(Vector v)
	{
		return distance(0, 0, v.x, v.y);
	}

	/**
	 * Returns the distance without taking the square root between (x1, y1) and
	 * (y1, y2).
	 */
	public static float distanceSquared(float x1, float y1, float x2, float y2)
	{
		return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
	}

	/**
	 * Returns the distance without taking the square root between the two
	 * vectors.
	 */
	public static float distanceSquared(Vector v1, Vector v2)
	{
		return distanceSquared(v1.x, v1.y, v2.x, v2.y);
	}

	/**
	 * Returns the distance without taking the square root between the vector and
	 * the origin.
	 */
	public static float distanceSquared(Vector v)
	{
		return distanceSquared(0, 0, v.x, v.y);
	}

	/**
	 * Returns the angle in degrees between (x1, y1) and (x2, y2).
	 */
	public static float angle(float x1, float y1, float x2, float y2)
	{
		return 180 - toDegree((float)java.lang.StrictMath.atan2(y2 - y1, x1 - x2));
	}

	/**
	 * Returns the angle in degrees between the two vectors.
	 */
	public static float angle(Vector v1, Vector v2)
	{
		return angle(v1.x, v1.y, v2.x, v2.y);
	}

	/**
	 * Returns the angle in degrees between the vector and the origin.
	 */
	public static float angle(Vector v)
	{
		return angle(0, 0, v.x, v.y);
	}

	/**
	 * Returns a vector halfway between (x1, y1) and (x2, y2).
	 */
	public static Vector midVector(float x1, float y1, float x2, float y2)
	{
		return new Vector((x1 + x2) / 2, (y1 + y2) / 2);
	}

	/**
	 * Returns a vector halfway between the two vectors.
	 */
	public static Vector midVector(Vector v1, Vector v2)
	{
		return midVector(v1.x, v1.y, v2.x, v2.y);
	}

	/**
	 * Returns which of v1 and v2 is closest to v.
	 */
	public static Vector closestToVector(Vector v, Vector v1, Vector v2)
	{
		return (distanceSquared(v, v1) < distanceSquared(v, v2)) ? v1 : v2;
	}

	/**
	 * Returns which of v1 and v2 is farthest from v.
	 */
	public static Vector farthestFromVector(Vector v, Vector v1, Vector v2)
	{
		return (distanceSquared(v, v1) > distanceSquared(v, v2)) ? v1 : v2;
	}

	/**
	 * Returns a vector <code>distance</code> away from the origin at the
	 * <code>angle</code> in degrees.
	 */
	public static Vector angledVector(float angle, float distance)
	{
		return new Vector(cos(angle) * distance, sin(angle) * distance);
	}

	/**
	 * Returns the average vector of the array of vectors.
	 */
	public static Vector averageVector(Vector[] vectors)
	{
		Vector average = Vector.zero();
		for (Vector v : vectors)
			average = Vector.add(average, v);
		return Vector.divide(average, vectors.length);
	}

	/**
	 * Returns a vector that is rotated around the origin (0, 0) by
	 * <code>angle</code> degrees.
	 */
	public static Vector rotateVector(Vector vector, float angle)
	{
		return angledVector(angle(vector) + angle, distance(vector));
	}

	/**
	 * Returns a vector that is rotated around the <code>origin</code> by
	 * <code>angle</code> degrees.
	 */
	public static Vector rotateVector(Vector origin, Vector vector, float angle)
	{
		return Vector.add(origin, angledVector(angle(origin, vector) + angle, distance(origin, vector)));
	}

	/**
	 * Returns an array of vectors rotated around the <code>origin</code> by
	 * <code>angle</code> degrees.
	 */
	public static Vector[] rotateVectors(Vector origin, Vector[] vectors, float angle)
	{
		for (int i = 0; i < vectors.length; i++)
			vectors[i] = rotateVector(origin, vectors[i], angle);
		return vectors;
	}

	/**
	 * Converts the radian to degrees.
	 */
	public static float toDegree(float radian)
	{
		return radian * 180 / PI;
	}

	/**
	 * Converts the degree to radians.
	 */
	public static float toRadian(float degree)
	{
		return degree / 180 * PI;
	}

	/**
	 * Returns the minimum of the two floats.
	 */
	public static float min(float a, float b)
	{
		return (a < b) ? a : b;
	}

	/**
	 * Returns the minimum of the two integers.
	 */
	public static int min(int a, int b)
	{
		return (a < b) ? a : b;
	}

	/**
	 * Returns the maximum of the two floats.
	 */
	public static float max(float a, float b)
	{
		return (a > b) ? a : b;
	}

	/**
	 * Returns the maximum of the two integers.
	 */
	public static int max(int a, int b)
	{
		return (a > b) ? a : b;
	}

	/**
	 * Returns the absolute value of <code>a</code>.
	 */
	public static float abs(float a)
	{
		return (a < 0.0f) ? -a : a;
	}

	/**
	 * Returns the absolute value of <code>a</code>.
	 */
	public static int abs(int a)
	{
		return (a < 0.0f) ? -a : a;
	}

	/**
	 * Returns the whole number part of a number.
	 */
	public static float floor(float a)
	{
		return (float)java.lang.StrictMath.floor(a);
	}

	/**
	 * Returns the decimal part of a number.
	 */
	public static float remainder(float a)
	{
		return a - floor(a);
	}

	/**
	 * If <code>a</code> is a whole number, <code>a</code> is returned. If
	 * not then the next highest integer is returned.
	 */
	public static float ceil(float a)
	{
		return (float)java.lang.StrictMath.ceil(a);
	}

	/**
	 * Returns the closest integer to <code>a</code>.
	 */
	public static int round(float a)
	{
		return java.lang.StrictMath.round(a);
	}

	/**
	 * Returns an integer clipped between the minimum and maximum allowed values.
	 */
	public static int cut(int a, int min, int max)
	{
		if (a < min)
			return min;
		if (a > max)
			return max;
		return a;
	}

	/**
	 * Returns a float clipped between the minimum and maximum allowed values.
	 */
	public static float cut(float a, float min, float max)
	{
		if (a < min)
			return min;
		if (a > max)
			return max;
		return a;
	}

	/**
	 * Returns the remainder part of <code>a / mod</code>.
	 */
	public static float mod(float a, float mod)
	{
		while (a >= mod)
			a -= mod;
		while (a < 0)
			a += mod;
		return a;
	}

}
