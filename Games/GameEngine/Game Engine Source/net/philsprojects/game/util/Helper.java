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
 *  Class: engine.util.Helper                     *
 *                                                *
 \**************************************************/

package net.philsprojects.game.util;

import net.philsprojects.game.IClone;

public final class Helper
{

	private Helper()
	{

	}

	/**
	 * Returns a copy of those elements.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends IClone> T[] copy(T[] t)
	{
		T[] elements = (T[])new Object[t.length];
		for (int i = 0; i < t.length; i++)
			elements[i] = (T)t[i].getClone();
		return elements;
	}

	/**
	 * Returns a copy of the array of floats.
	 */
	public static float[] copy(float[] floats)
	{
		float[] copy = new float[floats.length];
		for (int i = 0; i < floats.length; i++)
			copy[i] = floats[i];
		return copy;
	}

	/**
	 * Returns a copy of the array of booleans.
	 */
	public static boolean[] copy(boolean[] bools)
	{
		boolean[] copy = new boolean[bools.length];
		for (int i = 0; i < bools.length; i++)
			copy[i] = bools[i];
		return copy;
	}

	/**
	 * Returns a copy of the array of ints.
	 */
	public static int[] copy(int[] ints)
	{
		int[] copy = new int[ints.length];
		for (int i = 0; i < ints.length; i++)
			copy[i] = ints[i];
		return copy;
	}

}
