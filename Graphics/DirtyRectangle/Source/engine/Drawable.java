package engine;

public interface Drawable {
	
	public void draw(Graphics gr);

	public boolean isVisible();
	public void setVisible(boolean visible);
	
	public boolean isDirty();
	public void clearDirty();
	public Rectangle getBounds();
	
}
