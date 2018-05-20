package axe.g2f;

public class Rect2i 
{
	public int x, y, w, h;
	
	public Rect2i() {
	}
	
	public Rect2i(Rect2i r) {
		set(r);
	}
	
	public Rect2i(int x, int y) {
		set(x, y);
	}
	
	public Rect2i(int x, int y, int width, int height) {
		set(x, y, width, height);
	}

	public void set(Rect2i r) {
		set(r.x, r.y, r.w, r.h);
	}
	
	public void set(int x, int y) {
		set(x, y, 0, 0);
	}
	
	public void set(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.w = width;
		this.h = height;
	}
	
	public void clear() {
		x = y = w = h = 0;
	}
	
}
