package dirty;
import java.awt.Graphics2D;


public abstract class Canvas {

	private final Rectangle bounds = new Rectangle();
	private boolean dirty;
	
	protected abstract void onDraw(Graphics2D gr);
	
	public void draw(Graphics2D gr, Rectangle region) {
		gr.setClip(region.left, region.top, region.right - region.left, region.bottom - region.top);
		onDraw(gr);
	}
	
	public Rectangle getBounds() {
		return bounds;
	}

	public boolean isDirty() {
		return dirty;
	}
	                             
	
}
