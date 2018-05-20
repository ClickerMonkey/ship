package axe;

import java.util.Random;

public class Rangei 
{
	private static Random rnd = new Random();
	public int min, max;
	public Rangei(int x) {
		this(x, x);
	}
	public Rangei(int min, int max) {
		this.min = min;
		this.max = max;
	}
	public int rnd() {
		return rnd.nextInt(max - min + 1) + min;
	}
}