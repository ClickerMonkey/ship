package axe;

import java.util.Random;

public class Rangef 
{
	private static Random rnd = new Random();
	public float min, max;
	public Rangef(float x) {
		this(x, x);
	}
	public Rangef(float min, float max) {
		this.min = min;
		this.max = max;
	}
	public float rnd() {
		return (max - min) * rnd.nextFloat() + min;
	}
}