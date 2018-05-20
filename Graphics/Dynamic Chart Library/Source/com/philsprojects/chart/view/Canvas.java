package com.philsprojects.chart.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.image.BufferedImage;


/**
 * 
 * 
 * @author Philip Diffenderfer
 * 
 */
public abstract class Canvas
{

	// The flag for turning antialiasing on or off.
	protected Object antialiasKey;

	// The clipping rectangle on the screen of this canvas.
	protected Shape clipping = null;

	// The clipping bounds on the screen of this canvas.
	protected Rectangle bounds = null;

	// The inverse of the bounds width in pixels.
	protected double invBoundsWidth = 0.0;

	// The inverse of the bounds height in pixels.
	protected double invBoundsHeight = 0.0;

	// The background color of the canvas.
	protected Color background;

	// The double buffer for drawing the canvas.
	private Image buffer;

	// The graphics object of the double buffer.
	private Graphics2D graphics;

	// A listener for canvas requests.
	private CanvasListener listener;

	/**
	 * Initializes a canvas given its drawing region and whether to use
	 * antialiasing or not.
	 * 
	 * @param offsetX =>
	 *                The x offset in pixels of the clipping rectangle.
	 * @param offsetY =>
	 *                The y offset in pixels of the clipping rectangle.
	 * @param width =>
	 *                The width in pixels of the clipping rectangle.
	 * @param height =>
	 *                The height in pixels of the clipping rectangle.
	 * @param antialiasing =>
	 *                True to enable smoother drawing with slow performance,
	 *                false to draw blocky with fast performance.
	 */
	public Canvas(boolean antialiasing, int offsetX, int offsetY, int width,
			int height)
	{
		setAntialiasing(antialiasing);
		setClippingRectangle(offsetX, offsetY, width, height);
	}

	/**
	 * Initializes a canvas given its drawing region and whether to use
	 * antialiasing or not.
	 * 
	 * @param bounds =>
	 *                The rectangular clipping region for the drawable.
	 * @param antialiasing =>
	 *                True to enable smoother drawing with slow performance,
	 *                false to draw blocky with fast performance.
	 */
	public Canvas(boolean antialiasing, Rectangle bounds)
	{
		setAntialiasing(antialiasing);
		setClipping(bounds);
	}

	/**
	 * Initializes a canvas given its drawing region and whether to use
	 * antialiasing or not.
	 * 
	 * @param clipping =>
	 *                The clipping region for the drawable.
	 * @param antialiasing =>
	 *                True to enable smoother drawing with slow performance,
	 *                false to draw blocky with fast performance.
	 */
	public Canvas(boolean antialiasing, Shape clipping)
	{
		setAntialiasing(antialiasing);
		setClipping(clipping);
	}

	/**
	 * Draws the canvas.
	 * 
	 * @param gr =>
	 *                The graphics object to draw on.
	 */
	public void draw(Graphics2D gr)
	{
		if (buffer == null || gr == null)
			return;

		// Gets the graphics from the buffer image.
		graphics = (Graphics2D) buffer.getGraphics();

		// Fills in the clipping bounds with the background color.
		graphics.setColor(background);
		graphics.fillRect(0, 0, bounds.width, bounds.height);

		// Sets whether antialiasing is on or off
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antialiasKey);

		// Call the inherited method for drawing.
		onDraw(graphics);
		graphics.dispose();

		// Restricts all drawing to inside the clipping region.
		gr.setClip(clipping);

		// Draw the buffer to the graphics object.
		gr.drawImage(buffer, bounds.x, bounds.y, null);
	}

	/**
	 * Performs the drawing operation for the inherited canvas.
	 * 
	 * @param gr =>
	 *                The graphics object to draw on.
	 */
	protected abstract void onDraw(Graphics2D gr);

	/**
	 * Returns true if the implemented canvas should be redrawn then the given
	 * viewport is modified.
	 *  
	 * @param view => The viewport that has been modified.
	 */
	protected abstract boolean listensTo(Viewport view);
	
	/**
	 * Creates the double buffer based on the clipping bounds size.
	 */
	protected void createDoubleBuffer()
	{
		buffer = new BufferedImage(bounds.width, bounds.height,
				BufferedImage.TYPE_INT_RGB);
	}

	/**
	 * Requests to the Canvas listener for the redrawing of this canvas.
	 */
	public void requestRedraw()
	{
		if (listener != null)
			listener.requestRedraw(this);
	}

	/**
	 * Handles resizing of the canvas given the new size.
	 * 
	 * @param newWidth =>
	 *                The new width of canvas in pixels.
	 * @param newHeight =>
	 *                The new height of the canvas in pixels.
	 */
	public void onResize(int newWidth, int newHeight)
	{
		bounds.width = (newWidth - bounds.x);
		bounds.height = (newHeight - bounds.y);

		createDoubleBuffer();
	}

	/**
	 * Sets the width and height in pixels of this canvas and the location of
	 * this canvas relative to the component that contains it.
	 * 
	 * @param offsetX =>
	 *                The x offset in pixels of the clipping rectangle.
	 * @param offsetY =>
	 *                The y offset in pixels of the clipping rectangle.
	 * @param width =>
	 *                The width in pixels of the clipping rectangle.
	 * @param height =>
	 *                The height in pixels of the clipping rectangle.
	 */
	public final void setClippingRectangle(int offsetX, int offsetY, int width,
			int height)
	{
		this.bounds = new Rectangle(offsetX, offsetY, width, height);
		this.clipping = bounds;
		this.invBoundsWidth = 1.0 / width;
		this.invBoundsHeight = 1.0 / height;

		createDoubleBuffer();
	}

	/**
	 * Sets the region (shape) of this canvas. The inside of the shape is where
	 * the canvas is rendered.
	 * 
	 * @param clipping =>
	 *                The clipping shape.
	 */
	public final void setClipping(Shape clipping)
	{
		this.clipping = clipping;
		this.bounds = clipping.getBounds();
		this.invBoundsWidth = 1.0 / bounds.width;
		this.invBoundsHeight = 1.0 / bounds.height;

		createDoubleBuffer();
	}

	/**
	 * Sets whether smoother drawing (antialiasing) is used when drawing.
	 * 
	 * @param antialiasing =>
	 *                True to enable smoother drawing with slow performance,
	 *                false to draw blocky with fast performance.
	 */
	public final void setAntialiasing(boolean antialiasing)
	{
		this.antialiasKey = (antialiasing ? RenderingHints.VALUE_ANTIALIAS_ON
				: RenderingHints.VALUE_ANTIALIAS_OFF);
	}

	/**
	 * Sets the background color of the canvas.
	 * 
	 * @param background =>
	 *                The new background color of the canvas.
	 */
	public final void setBackground(Color background)
	{
		this.background = background;
	}

	/**
	 * Sets the listener of canvas requests.
	 * 
	 * @param listener =>
	 *                The new listener of this canvas.
	 */
	public final void setListener(CanvasListener listener)
	{
		this.listener = listener;
	}

	/**
	 * Returns the graphics object of the double buffer.
	 */
	public final Graphics2D getGraphics()
	{
		return graphics;
	}

	/**
	 * Returns the background color of the canvas.
	 */
	public final Color getBackground()
	{
		return background;
	}

	/**
	 * Returns the clipping region of the canvas.
	 */
	public Shape getClippingShape()
	{
		return clipping;
	}

	/**
	 * Returns the rectangular bounds of the canvas.
	 */
	public Rectangle getCanvasBounds()
	{
		return bounds;
	}

	/**
	 * Returns the width of the canvas in pixels.
	 */
	public int getCanvasWidth()
	{
		return bounds.width;
	}

	/**
	 * Returns the height of the canvas in pixels.
	 */
	public int getCanvasHeight()
	{
		return bounds.height;
	}

	/**
	 * Returns the offset of the canvas in the container on the x-axis.
	 */
	public int getCanvasX()
	{
		return bounds.x;
	}

	/**
	 * Returns the offset of the canvas in the container on the y-axis.
	 */
	public int getCanvasY()
	{
		return bounds.y;
	}

	/**
	 * Returns the offset of the right side of the canvas on the y-axis.
	 */
	public int getCanvasRight()
	{
		return bounds.x + bounds.width;
	}

	/**
	 * Returns the offset of the bottom side of the canvas on the x-axis.
	 */
	public int getCanvasBottom()
	{
		return bounds.y + bounds.height;
	}

}
