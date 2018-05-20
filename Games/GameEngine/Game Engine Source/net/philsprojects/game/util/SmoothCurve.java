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
 *  Class: engine.util.SmoothCurve                *
 *                                                *
 \**************************************************/

package net.philsprojects.game.util;

import net.philsprojects.game.ICurve;

/**
 * Keeps track of values over a certain amount of time. An array of values is
 * passed through and the first value is the initial value at time 0.0 and the
 * last value will be reached at the maximum time in seconds.
 * 
 * @author Philip Diffenderfer
 */
public class SmoothCurve implements ICurve
{

	public float _timeInterval = 0f;

	public float _duration = 0f;

	public float[] _values = null;

	/**
	 * Initializes a curve where the values are separated evenly over a total
	 * duration in seconds.
	 * 
	 * @param duration =>
	 *           The total time of the curve.
	 * @param values =>
	 *           The values along the curve.
	 */
	public SmoothCurve(float duration, float[] values)
	{
		_timeInterval = duration / (values.length - 1);
		_duration = duration;
		_values = values;
	}

	/**
	 * Gets a value along the curve at a certain time.
	 * 
	 * @param time =>
	 *           The time along the curve larger then or equal to 0 and less then
	 *           the total duration.
	 */
	public float getValue(float time)
	{
		if (time >= _duration)
			return _values[_values.length - 1];
		if (time <= 0.0f)
			return _values[0];
		// The index of the value to the left of this time.
		int index = (int)(time / _duration * (_values.length - 1));
		// The percent in between this time is, between the 2 values.
		float delta = (time - (index * _timeInterval)) / _timeInterval;
		// Returns the value between the (index) and (index + 1) by the delta.
		return (_values[index + 1] - _values[index]) * delta + _values[index];
	}

	/**
	 * Gets the time interval in between each value.
	 * 
	 * @return The time interval in seconds between each value.
	 */
	public float getTimeInterval()
	{
		return _timeInterval;
	}

	/**
	 * Gets the total duration of the curve.
	 * 
	 * @return The total duration in seconds of the curve.
	 */
	public float getDuration()
	{
		return _duration;
	}

	/**
	 * Gets the values along this curve.
	 * 
	 * @return The reference to the array of values in this curve.
	 */
	public float[] getValues()
	{
		return _values;
	}

	/**
	 * Scales all the values of this curve.
	 * 
	 * @param scale =>
	 *           The scale to multiply each value by.
	 */
	public void scale(float scale)
	{
		for (int i = 0; i < _values.length; i++)
			_values[i] = _values[i] * scale;
	}

	/**
	 * Gets a copy of this SmoothCurve.
	 * 
	 * @return The reference to a new SmoothCurve with all the same values.
	 */
	public SmoothCurve getClone()
	{
		return new SmoothCurve(_duration, Helper.copy(_values));
	}

}
