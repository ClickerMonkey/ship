package pfxEditor;
/**
 * Keeps track of values over a certain amount of time. An array of times and
 * values is passed through where the first time must be 0.0 and the last time
 * will be the whole duration of the curve. The first value is the initial value
 * at time 0.0 and the last value will be reached at the maximum time in
 * seconds.
 * 
 * @author Philip Diffenderfer
 */
public class Curve implements ICurve {

	private float[] _info;
	private int _depth = 0;
	private float _duration = 0.0f;

	/**
	 * Creates a curve that uses a certain time step <code>timeInterval</code>
	 * between the values that are generated.
	 */
	public Curve(float timeInterval, float times[], float values[]) {
		this((int) (times[times.length - 1] / timeInterval) + 1, times, values);
	}

	/**
	 * Creates a curve that sets up how many values are going to be used to
	 * determine how many values are generated.
	 */
	public Curve(int curveDepth, float times[], float values[]) {
		_depth = curveDepth;

		_info = new float[_depth];
		_duration = times[times.length - 1];

		float interval = _duration / (_depth - 1);
		float time = -interval;
		float delta = 0.0f;
		float dif = 0.0f;
		int current = 0;

		for (int i = 0; i < _depth; i++) {
			time += interval;

			dif = times[current + 1] - times[current];

			delta = (time - times[current]) / dif;

			_info[i] = (values[current + 1] - values[current]) * delta
					+ values[current];

			if (time >= times[current + 1])
				current++;
		}
	}

	/**
	 * Gets the value along the curve at a certain time in seconds.
	 */
	public float getValue(float time) {
		if (time >= _duration)
			return _info[_info.length - 1];
		if (time <= 0.0f)
			return _info[0];
		return _info[(int) ((time / _duration) * (_depth - 1))];
	}

	/**
	 * Returns the full duration of this curve in seconds.
	 */
	public float getDuration() {
		return _duration;
	}

	/**
	 * Returns how many values are stored away.
	 */
	public int getDepth() {
		return _depth;
	}

}
