package pfxEditor;

// 8 bytes
public class Range {

	private float min = 0.0f;
	private float max = 0.0f;

	public Range(float value) {
		min = max = value;
	}

	public Range(float maximum, float minimum) {
		max = maximum;
		min = minimum;
	}

	public float randomFloat() {
		return random(min, max);
	}

	public int randomInteger() {
		return random((int) min, (int) max);
	}

	public float getMin() {
		return min;
	}

	public float getMax() {
		return max;
	}

	public static int random(int min, int max) {
		if (min == max)
			return max;
		return (int) (java.lang.Math.random() * (max - min + 1) + min);
	}

	public static float random(float min, float max) {
		if (min == max)
			return max;
		return (float) java.lang.Math.random() * (max - min + 1) + min;
	}

}
