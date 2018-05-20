package pfxEditor;

public class SmoothCurve implements ICurve {

	public float _timeInterval = 0.0f;
	public float _duration = 0.0f;
	public float[] _values = null;

	public SmoothCurve(float timeInterval, float duration, float[] values) {
		_timeInterval = timeInterval;
		_duration = duration;
		_values = values;
	}

	public SmoothCurve(float duration, float[] values) {
		_timeInterval = duration / (values.length - 1);
		_duration = duration;
		_values = values;
	}

	public float getValue(float time) {
		if (time >= _duration)
			return _values[_values.length - 1];
		if (time <= 0.0f)
			return _values[0];
		int index = (int) Math.floor(time / _duration * (_values.length - 1));
		float delta = (time - (index * _timeInterval)) / _timeInterval;
		return (_values[index + 1] - _values[index]) * delta + _values[index];
	}

	public float getTimeInterval() {
		return _timeInterval;
	}

	public float getDuration() {
		return _duration;
	}

	public float[] getValues() {
		return _values;
	}

	public SmoothCurve getClone() {
		return new SmoothCurve(_timeInterval, _duration, _values);
	}

}
