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
 *  Class: engine.util.Curve                      *
 *                                                *
 \**************************************************/

package net.philsprojects.game.util;

import net.philsprojects.game.ICurve;

/**
 * Keeps track of values over a certain amount of time. An array of times and
 * values is passed through where the first time must be 0.0 and the last time
 * will be the whole duration of the curve. The first value is the initial value
 * at time 0.0 and the last value will be reached at the maximum time in
 * seconds.
 * 
 * @author Philip Diffenderfer 
 */
public class Curve implements ICurve
{

	private float[] _info;

	private float _duration = 0f;

	private float _durationInv = 0f;

	/**
	 * Initializes a curve that has a certain duration in seconds and the values
	 * that are generated.
	 * 
	 * @param duration =>
	 *           The total time of the Curve in seconds.
	 * @param info =>
	 *           The values along the curve.
	 */
	public Curve(float duration, float[] info)
	{
		_info = info;
		_duration = duration;
		_durationInv = 1f / _duration;
	}

	/**
	 * Initializes a curve that uses a certain time step between the values that
	 * are generated.
	 * 
	 * @param timeInterval =>
	 *           The amount of seconds between each value generated.
	 * @param times =>
	 *           The times along the curve where a certain value is reached.
	 * @param values =>
	 *           The values along the curve where each one matches a time.
	 */
	public Curve(float timeInterval, float times[], float values[])
	{
		this((int)(times[times.length - 1] / timeInterval) + 1, times, values);
	}

	/**
	 * Initializes a curve that sets up how many values are going to be used to
	 * determine how many values are generated.
	 * 
	 * @param curveDepth =>
	 *           The number of values to generate along the curve.
	 * @param times =>
	 *           The times along the curve where a certain value is reached.
	 * @param values =>
	 *           The values along the curve where each one matches a time.
	 */
	public Curve(int curveDepth, float times[], float values[])
	{
		_info = new float[curveDepth];
		_duration = times[times.length - 1];
		_durationInv = 1f / _duration;

		float interval = _duration / (curveDepth - 1);
		float time = -interval;
		float delta = 0.0f;
		float dif = 0.0f;
		int current = 0;

		for (int i = 0; i < curveDepth; i++)
		{
			time += interval;

			dif = times[current + 1] - times[current];

			delta = (time - times[current]) / dif;

			_info[i] = (values[current + 1] - values[current]) * delta + values[current];

			if (time >= times[current + 1])
				current++;
		}
	}

	/**
	 * Gets the value along the curve at a certain time in seconds.
	 * 
	 * @param time =>
	 *           The time along the curve. Between 0 and the curves duration.
	 */
	public float getValue(float time)
	{
		int max = _info.length - 1;
		if (time >= _duration)
			return _info[max];
		if (time <= 0.0f)
			return _info[0];
		return _info[(int)(time * _durationInv * max)];
	}

	/**
	 * Returns the full duration of this curve in seconds.
	 */
	public float getDuration()
	{
		return _duration;
	}

	/**
	 * Returns how many values are stored away.
	 */
	public int getDepth()
	{
		return _info.length;
	}

	/**
	 * Scales each value in the curve by a certain value.
	 * 
	 * @param scale =>
	 *           The value to scale.
	 */
	public void scale(float scale)
	{
		for (int i = 0; i < _info.length; i++)
			_info[i] = _info[i] * scale;
	}

	/**
	 * Clones the curve and returns a copy with all the same values.
	 */
	public Curve getClone()
	{
		return new Curve(_duration, Helper.copy(_info));
	}


}
