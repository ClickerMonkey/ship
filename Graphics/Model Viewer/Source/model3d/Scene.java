package model3d;

public interface Scene {

	/**
	 * Draws a pixel at a.
	 */
	public void draw(Vector a);
	
	/**
	 * Draws a line from a to b.
	 */
	public void draw(Vector a, Vector b);
	
	/**
	 * Draws a triangle between a, b, and c.
	 */
	public void draw(Vector a, Vector b, Vector c);
	
	/**
	 * Draws a quad between a, b, c, and d.
	 */
	public void draw(Vector a, Vector b, Vector c, Vector d);
	
	/**
	 * Draws a polygon with the given vectors.
	 */
	public void draw(Vector[] v);
	
}
