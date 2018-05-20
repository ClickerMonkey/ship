package engine;

public class Sprite implements Updatable, Drawable {

	private TileSource source;
	
	private Rectangle dirty = new Rectangle();
	private Rectangle bounds = new Rectangle();
	
	private boolean enabled = true;
	private boolean visible = true;
	private boolean isDirty = false;
	
	public Sprite() {
		
	}
	
	public final void update(TimeUnit elapsed) {
		dirty.set(bounds);
		
		onUpdate(elapsed);
		
		if (!dirty.equals(bounds)) {
			isDirty = true;
		}
	}
	
	protected void onUpdate(TimeUnit elapsed) {
		
	}

	public void draw(Graphics gr) {
		if (source != null) {
			gr.setTile(source);
			gr.draw(bounds);	
		}
	}

	public void setTileSource(TileSource source) {
		this.source = source;
	}
	
	public void setBounds(int left, int top, int right, int bottom)
	{
		bounds.left = left;
		bounds.top = top;
		bounds.right = right;
		bounds.bottom = bottom;
	}
	
	public void setSize(int width, int height)
	{
		bounds.right = bounds.left + width;
		bounds.bottom = bounds.top + height;
	}
	
	public void setCenter(int x, int y) {
		int width = bounds.width() >> 1;
		int height = bounds.height() >> 1;
		bounds.left = x - width;
		bounds.top = y - height;
		bounds.right = x + width;
		bounds.bottom = y + height;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void clearDirty() {
		isDirty = false;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public boolean isDirty() {
		return isDirty;
	}
	
}
