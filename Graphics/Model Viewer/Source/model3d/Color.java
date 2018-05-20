package model3d;

public class Color {

	public int r, g, b, a;

	public void set(int r, int g, int b) {
	}
	
	public void set(int r, int g, int b, int a) {
		this.r = clampi(r, 0, 255);
		this.r = clampi(g, 0, 255);
		this.r = clampi(b, 0, 255);
		this.r = clampi(a, 0, 255);
	}
	
	public void lerp(Color x, Color y, float dt) {
		dt = clampf(dt, 0, 1);
		r = (int)((y.r - x.r) * dt + x.r);
		g = (int)((y.g - x.g) * dt + x.g);
		b = (int)((y.b - x.b) * dt + x.b);
		a = (int)((y.a - x.a) * dt + x.a);
	}
	
	private int clampi(int v, int min, int max) {
		return (v < min ? min : (v > max ? max : v));
	}
	
	private float clampf(float v, float min, float max) {
		return (v < min ? min : (v > max ? max : v));
	}
	
}
