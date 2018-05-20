package gerry;

public class Viewport 
{
	
	private final float left;
	private final float top;
	private final float scalex;
	private final float scaley;
	public final int width;
	public final int height;
	
	private Viewport(Box bounds, int width, int height) {
		this.left = bounds.l;
		this.top = bounds.t;
		this.scalex = width / bounds.width();
		this.scaley = height / bounds.height();
		this.width = width;
		this.height = height;
	}
	
	public Pixel convert(float x, float y) {
		Pixel p = new Pixel();
		p.x = (int)((x - left) * scalex);
		p.y = (int)((top - y) * scaley);
		return p;
	}
	
	public float convertx(float x) {
		return (x - left) * scalex;
	}
	
	public float converty(float y) {
		return (top - y) * scaley;
	}
	
	public static Viewport fromWidth(Box bounds, int width) {
		return new Viewport(bounds, width, (int)(width * (bounds.height() / bounds.width())));
	}
	
	public static Viewport fromHeight(Box bounds, int height) {
		return new Viewport(bounds, (int)(height * (bounds.width() / bounds.height())), height);
	}
	
}