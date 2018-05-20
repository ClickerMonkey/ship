package gerry;

public class Box 
{
	
	public float r, l, t, b;
	
	public Box(City c) {
		this(c.x, c.y);
	}
	
	public Box(float x, float y) {
		l = r = x;
		t = b = y;
	}
	
	public void include(City c) {
		include(c.x, c.y);
	}

	public void include(float x, float y) {
		if (y > t) t = y;
		if (y < b) b = y;
		if (x < l) l = x;
		if (x > r) r = x;
	}
	
	public float width() {
		return (r - l);
	}
	
	public float height() {
		return (t - b);
	}
	
	public float dx(float x) {
		return (x - l) / (r - l);
	}
	
	public float dy(float y) {
		return (t - y) / (t - b);
	}
	
	public String toString() {
		return String.format("[L:%.2f T:%.2f R:%.2f B:%.2f]", l, t, r, b);
	}
	
}