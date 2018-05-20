package graphing;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.text.DecimalFormat;


/**
 * The Graph class provides any graphics capable component with
 * a graphing system. The graph has its own world position and
 * bounds. You can zoom in and out and translate across the world. 
 * 
 * @author Philip Diffenderfer
 */
public abstract class Graph
{
	
	/** The x-maximum (right) coordinate of the Graph. */
	protected double _right = 0.0;
	/** The x-minimum (left) coordinate of the Graph. */
	protected double _left = 0.0;
	/** The y-maximum (top) coordinate of the Graph. */
	protected double _top = 0.0;
	/** The y-minimum (bottom) coordinate of the Graph. */
	protected double _bottom = 0.0;
	
	/** The maximum right value this graph will show. */
	protected double _maxRight = 0.0;
	/** The maximum left value this graph will show. */
	protected double _maxLeft = 0.0;
	/** The maximum top value this graph will show. */
	protected double _maxTop = 0.0;
	/** The maximum bottom value this graph will show. */
	protected double _maxBottom = 0.0;

	/** The maximum width of the graph window. */
	protected double _maxWidth = 0.0;
	/** The minimum width of the graph window. */
	protected double _minWidth = 0.0;
	/** The maximum height of the graph window. */
	protected double _maxHeight = 0.0;
	/** The minimum height of the graph window. */
	protected double _minHeight = 0.0;
	
	/** The clipping rectangle and bounds on the screen of this Graph. */
	protected Shape _clipping = null;
	protected Rectangle _bounds = null;
		
	
	/**
	 * Initializes a Graph setting its clipping rectangle within the component.
	 * The clipping rectangle specifies the rectangular region in the component
	 * that is reserved for this graph to perform drawing functions.
	 * 
	 * @param offsetX => The x offset in pixels of the clipping rectangle.
	 * @param offsetY => The y offset in pixels of the clipping rectangle.
	 * @param width => The width in pixels of the clipping rectangle.
	 * @param height => The height in pixels of the clipping rectangle.
	 */
	public Graph(int offsetX, int offsetY, int width, int height)
	{
		this(offsetX, offsetY, width, height, 
				1.0, -1.0, 1.0, -1.0, 
				1.0, -1.0, 1.0, -1.0, 
				2.0, 2.0, 2.0, 2.0);
	}
	
	/**
	 * Initializes a Graph with a fixed window size and fixed position.
	 * This graph cannot zoom or translate from the current position.
	 * 
	 * @param offsetX => The x offset in pixels of the clipping rectangle.
	 * @param offsetY => The y offset in pixels of the clipping rectangle.
	 * @param width => The width in pixels of the clipping rectangle.
	 * @param height => The height in pixels of the clipping rectangle.
	 * @param right => The x-maximum displayed on this graph.
	 * @param left => The x-minimum displayed on this graph.
	 * @param top => The y-maximum displayed on this graph.
	 * @param bottom => The y-minimum displayed on this graph.
	 */
	public Graph(int offsetX, int offsetY, int width, int height, 
			double right, double left, double top, double bottom)
	{
		this(offsetX, offsetY, width, height, 
				right, left, top, bottom, 
				right, left, top, bottom, 
				right - left, right - left, top - bottom, top - bottom);
	}
	
	/**
	 * Initializes a Graph with a fixed window size. This graph cannot zoom
	 * but it can translate within its bounds.
	 * 
	 * @param offsetX => The x offset in pixels of the clipping rectangle.
	 * @param offsetY => The y offset in pixels of the clipping rectangle.
	 * @param width => The width in pixels of the clipping rectangle.
	 * @param height => The height in pixels of the clipping rectangle.
	 * @param right => The x-maximum displayed on this graph.
	 * @param left => The x-minimum displayed on this graph.
	 * @param top => The y-maximum displayed on this graph.
	 * @param bottom => The y-minimum displayed on this graph.
	 * @param maxRight => The maximum x value this graph will show.
	 * @param maxLeft => The minimum x value this graph will show.
	 * @param maxTop => The maximum y value this graph will show.
	 * @param maxBottom => The minimum y value this graph will show.
	 */
	public Graph(int offsetX, int offsetY, int width, int height, 
			double right, double left, double top, double bottom,
			double maxRight, double maxLeft, double maxTop, double maxBottom)
	{
		this(offsetX, offsetY, width, height, 
				right, left, top, bottom, 
				maxRight, maxLeft, maxTop, maxBottom, 
				right - left, right - left, top - bottom, top - bottom);
	}
	
	/**
	 * Initializes a graph setting all parameters available to describe the graph's
	 * bounds, restrictions, and current position.
	 * 
	 * @param offsetX => The x offset in pixels of the clipping rectangle.
	 * @param offsetY => The y offset in pixels of the clipping rectangle.
	 * @param width => The width in pixels of the clipping rectangle.
	 * @param height => The height in pixels of the clipping rectangle.
	 * @param right => The x-maximum displayed on this graph.
	 * @param left => The x-minimum displayed on this graph.
	 * @param top => The y-maximum displayed on this graph.
	 * @param bottom => The y-minimum displayed on this graph.
	 * @param maxRight => The maximum x value this graph will show.
	 * @param maxLeft => The minimum x value this graph will show.
	 * @param maxTop => The maximum y value this graph will show.
	 * @param maxBottom => The minimum y value this graph will show.
	 * @param maxWidth => The maximum width of the graph window. This restricts
	 * 		how much can be zoomed out in the x direction.
	 * @param minWidth => The minimum width of this graph window. This restricts
	 * 		how much can be zoomed in, in the x direction.
	 * @param maxHeight => The maximum height of the graph window. This restricts
	 * 		how much can be zoomed out in the y direction.
	 * @param minHeight => The minimum height of this graph window. This restricts
	 * 		how much can be zoomed in, in the y direction.
	 */
	public Graph(int offsetX, int offsetY, int width, int height, 
			double right, double left, double top, double bottom, 
			double maxRight, double maxLeft, double maxTop, double maxBottom, 
			double maxWidth, double minWidth, double maxHeight, double minHeight)
	{
		setClippingRectangle(offsetX, offsetY, width, height);
		setMaxBounds(maxRight, maxLeft, maxTop, maxBottom);
		setMaxSize(maxWidth, minWidth, maxHeight, minHeight);
		setBounds(right, left, top, bottom);
	}
	
	
	/**
	 * Prepares the graph for drawing. This clips the clipping region
	 * and turns on antialiasing.
	 * 
	 * @param gr => The graphics object to draw the Graph on.
	 */
	public void draw(Graphics2D gr)
	{
		//Smoothes all drawing
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//On draws graphics within the bounds
		gr.setClip(_clipping);
		//User draws graph
		drawGraph(gr);
	}
	
	
	/**
	 * Inherited classes implement their specific drawing.
	 * 
	 * @param gr => The graphics object to draw the Graph on.
	 */
	protected abstract void drawGraph(Graphics2D gr);
	
	
	/**
	 * Draws vertical lines across the graph that are separated by a width 
	 * on the graphs scale, not by pixels. Each line has a constant width 
	 * in pixels and a color.
	 * 
	 * @param gr => The graphics object to draw the grid lines on.
	 * @param width => Separation between lines on the graph.
	 * @param lineWidth => Width in pixels of the grid lines drawn.
	 * @param lineShade => Color of the grid lines drawn.
	 */
	protected void drawVerticalGridLines(Graphics2D gr, double width, float lineWidth, Color lineShade)
	{
		gr.setStroke(new BasicStroke(lineWidth));
		gr.setColor(lineShade);
		
		// Starting X-coordinate on the graph.
		double startX = Math.floor(_left / width) * width; 
		// Ending X-coordinate on the graph.
		double endX = Math.ceil(_right / width) * width;
		// Total number of lines to draw.
		int totalLines = (int)((endX - startX) / width);
		// The distance between each line, in pixels.
		double actualSize = getScreenX(width) - getScreenX(0);
		// Starting X-coordinate on the screen, in pixels.
		double x = getScreenX(startX) + actualSize;
		
		for (int i = 0; i < totalLines; i++)
		{
			gr.drawLine((int)x, _bounds.y, (int)x, _bounds.y + _bounds.height);
			x += actualSize;
		}

	}
	
	/**
	 * Draws horizontal lines down the graph that are separated by a width
	 * on the graphs scale, not by pixels. Each line has a constant width
	 * in pixels and a color.
	 * 
	 * @param gr => The graphics object to draw the grid lines on.
	 * @param width => Separation between lines on the graph.
	 * @param lineWidth => Width in pixels of the grid lines drawn.
	 * @param lineShade => Color of the grid lines drawn.
	 */
	protected void drawHorizontalGridLines(Graphics2D gr, double width, float lineWidth, Color lineShade)
	{
		gr.setStroke(new BasicStroke(lineWidth));
		gr.setColor(lineShade);

		// Starting X-coordinate on the graph.
		double startY = Math.floor(_bottom / width) * width;
		// Ending X-coordinate on the graph.
		double endY = Math.ceil(_top / width) * width;
		// Total number of lines to draw.
		int totalLines = (int)((endY - startY) / width);
		// The distance between each line, in pixels.
		double actualSize = getScreenY(width) - getScreenY(0);
		// Starting X-coordinate on the screen, in pixels.
		double y = getScreenY(startY) + actualSize;
		
		for (int i = 0; i < totalLines; i++)
		{
			gr.drawLine(_bounds.x, (int)y, _bounds.x + _bounds.width, (int)y);
			y += actualSize;
		}
	}
	
	/**
	 * Draws a grid of horizontal and vertical lines that are separated by 
	 * widths on the graphs scale, not by pixels.  Each line has a constant 
	 * width in pixels and a color.
	 * 
	 * @param gr => The graphics object to draw the grid lines on.
	 * @param width => Separation between vertical lines on the graph.
	 * @param height => Separation between horizontal lines on the graph.
	 * @param lineWidth => Width in pixels of the grid lines drawn.
	 * @param lineShade => Color of the grid lines drawn.
	 */
	protected void drawGridLines(Graphics2D gr, double width, double height, float lineWidth, Color lineShade)
	{
		drawVerticalGridLines(gr, width, lineWidth, lineShade);
		drawHorizontalGridLines(gr, height, lineWidth, lineShade);
	}
	
	/**
	 * Returns the total number of vertical lines displayed on the graph
	 * given a grid size.
	 * 
	 * @param width => The given grid size to calculate from.
	 */
	protected int getVerticalCount(double width)
	{
		// Starting X-coordinate on the graph.
		double startX = Math.floor(_left / width) * width; 
		// Ending X-coordinate on the graph.
		double endX = Math.ceil(_right / width) * width;
		// Total number of lines to draw.
		 return (int)((endX - startX) / width);		
	}
	
	/**
	 * Returns the total number of horizontal lines displayed on the graph
	 * given a grid size.
	 * 
	 * @param height => The given grid size to calculate from.
	 */
	protected int getHorizontalCount(double height)
	{
		// Starting X-coordinate on the graph.
		double startY = Math.floor(_bottom / height) * height;
		// Ending X-coordinate on the graph.
		double endY = Math.ceil(_top / height) * height;
		// Total number of lines to draw.
		return (int)((endY - startY) / height);	
	}
	
	/**
	 * Draws all the vertical values on the graph separated by widths on the graphs
	 * scale, not by pixels. The values have a specificed font and fore color.
	 * 
	 * @param gr => The graphics object to draw the grid values on.
	 * @param width => Separation between the values on the graph.
	 * @param font => The font of the value to draw.
	 * @param foreColor => The color of the font to draw.
	 */
	protected void drawVerticalGridValues(Graphics2D gr, double width, Font font, Color foreColor)
	{
		gr.setFont(font);
		gr.setColor(foreColor);
		
		int height = gr.getFontMetrics(font).getHeight();
		
		// Starting X-coordinate on the graph.
		double startX = Math.floor(_left / width) * width; 
		// Ending X-coordinate on the graph.
		double endX = Math.ceil(_right / width) * width;
		// Total number of lines to draw.
		int totalLines = (int)((endX - startX) / width) + 1;
		// The distance between each line, in pixels.
		double actualSize = getScreenX(width) - getScreenX(0);
		// Starting X-coordinate on the screen, in pixels.
		double x = getScreenX(startX);
		double dx = startX;
		
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(1);
		df.setMaximumFractionDigits(6);
		
		for (int i = 0; i < totalLines; i++)
		{
			gr.drawString(df.format(dx), _bounds.x + (int)x + 2, _bounds.y + height);
			dx += width;
			x += actualSize;
		}
	}
	
	/**
	 * Draws all the horizontal values on the graph separated by widths on the graphs
	 * scale, not by pixels. The values have a specificed font and fore color.
	 * 
	 * @param gr => The graphics object to draw the grid values on.
	 * @param width => Separation between the values on the graph.
	 * @param font => The font of the value to draw.
	 * @param foreColor => The color of the font to draw.
	 */
	protected void drawHorizontalGridValues(Graphics2D gr, double width, Font font, Color foreColor)
	{
		gr.setFont(font);
		gr.setColor(foreColor);

		// Starting Y-coordinate on the graph.
		double startY = Math.floor(_bottom / width) * width;
		// Ending Y-coordinate on the graph.
		double endY = Math.ceil(_top / width) * width;
		// Total number of lines to draw.
		int totalLines = (int)((endY - startY) / width) + 1;
		// The distance between each line, in pixels.
		double actualSize = getScreenY(width) - getScreenY(0);
		// Starting X-coordinate on the screen, in pixels.
		double y = getScreenY(startY);
		double dy = startY;

		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(1);
		df.setMaximumFractionDigits(6);

		for (int i = 0; i < totalLines; i++)
		{
			gr.drawString(df.format(dy), _bounds.x + 2, _bounds.y + (int)y - 2);
			dy += width;
			y += actualSize;
		}
	}
	
	/**
	 * Draws all the values on the graph separated by widths on the graphs
	 * scale, not by pixels. The values have a specificed font and fore color.
	 * 
	 * @param gr => The graphics object to draw the grid values on.
	 * @param width => Separation vertically between the values on the graph.
	 * @param height => Separation horizontally between the values on the graph.
	 * @param font => The font of the value to draw.
	 * @param foreColor => The color of the font to draw.
	 */
	protected void drawGridValues(Graphics2D gr, double width, double height, Font font, Color foreColor)
	{
		drawVerticalGridValues(gr, width, font, foreColor);
		drawHorizontalGridValues(gr, height, font, foreColor);
	}

	
	/** 
	 * Zooms out on the focus point by zoomFactor.
	 * 
	 * @param zoomFactor => A value to zoom out on the graph, 1.0 will just center the
	 * 		graph on the focus point, while a 2.0 will enlarge the 
	 * 		maximums and minimums making the size of the graph twice as large.
	 * @param x => An X value to focus the graph on when zoomed out.
	 * @param y => An Y value to focus the graph on when zoomed out.
	 */
	public final void zoomOut(double zoomFactor, double x, double y)
	{
		if (zoomFactor == 0.0)
			return;
		double halfLeft, halfRight, halfBottom, halfTop;
		halfRight = (_right  - x) / zoomFactor;
		halfLeft = (x - _left) / zoomFactor;
		halfTop = (_top - y) / zoomFactor;
		halfBottom = (y - _bottom) / zoomFactor;

		_right = x + halfRight;
		_left = x - halfLeft;
		_top = y + halfTop;
		_bottom = y - halfBottom;
		correctBounds(x, y);
	}
	
	/** 
	 * Zooms out on the center of the graph by zoomFactor.
	 * 
	 * @param zoomFactor => A value to zoom out on the graph, 1.0 will do nothing, 
	 * 		while a 2.0 will enlarge the maximums and minimums making the size of
	 * 		the graph twice as large.
	 */
	public final void zoomOut(double zoomFactor)
	{
		//Use the center of the graph as the focus point
		zoomOut(zoomFactor, getCenterX(), getCenterY());
	}
	
	/** 
	 * Zooms in on the focus point by zoomFactor.
	 * 
	 * @param zoomFactor => A value to zoom in on the graph, 1.0 will just center the
	 * 		graph on the focus point, while a 2.0 will shrink the 
	 * 		maximums and minimums making the size of the graph half as larger.
	 * @param x => An X value to focus the graph on when zoomed in.
	 * @param y => An Y value to focus the graph on when zoomed in.
	 */
	public final void zoomIn(double zoomFactor, double x, double y)
	{
		if (zoomFactor == 0.0)
			return;
		double halfLeft, halfRight, halfBottom, halfTop;
		halfRight = (_right  - x) * zoomFactor;
		halfLeft = (x - _left) * zoomFactor;
		halfTop = (_top - y) * zoomFactor;
		halfBottom = (y - _bottom) * zoomFactor;

		_right = x + halfRight;
		_left = x - halfLeft;
		_top = y + halfTop;
		_bottom = y - halfBottom;
		correctBounds(x, y);
	}
	
	/** 
	 * Zooms in on the center of the graph by zoomFactor.
	 * 
	 * @param zoomFactor => A value to zoom in on the graph, 1.0 will do nothing, 
	 * 		while a 2.0 will shrink the maximums and minimums making the size of
	 *      the graph half as larger.
	 */
	public final void zoomIn(double zoomFactor)
	{
		//Use the center of the graph as the focus point
		zoomIn(zoomFactor, getCenterX(), getCenterY());
	}
	
	/** 
	 * Translates the left and right by dx, moving the graph values on the X-Axis. 
	 * 
	 * @param dx => The number to translate the graph by horizontally.
	 */
	public final void translateX(double dx)
	{
		_right += dx;
		_left += dx;
		correctBounds();
	}
	
	/** 
	 * Translates the top and bottom by dy, moving the graph values on the Y-Axis. 
	 * 
	 * @param dy => The number to translate the graph by vertically.
	 */
	public final void translateY(double dy)
	{
		_top += dy;
		_bottom += dy;
		correctBounds();
	}
	
	/**
	 * Translates the the graph values on both Axis.
	 * 
	 * @param dx => The number to translate the graph by horizontally.
	 * @param dy => The number to translate the graph by vertically.
	 */
	public final void translate(double dx, double dy)
	{
		_right += dx;
		_left += dx;
		_top += dy;
		_bottom += dy;
		correctBounds();
	}
	
	/**
	 * Centers the graph at a position maintaining the same size.
	 * 
	 * @param x => The center X value of the graph. 
	 * @param y => The center Y value of the graph.
	 */
	public final void center(double x, double y)
	{
		double halfWidth = getWidth() * 0.5;
		double halfHeight = getHeight() * 0.5;

		_right = x + halfWidth;
		_left = x - halfWidth;
		_top = y + halfHeight;
		_bottom = y - halfHeight;
		correctBounds();
	}
	
	
	/**
	 * This corrects any bounds alignment with the max left, right, top,
	 * and bottom. If the graph window is larger then the max its squeezed
	 * into the max bounds. If the size is outside the bound restrictons
	 * then center it on a the graph's center values.
	 */
	public final void correctBounds()
	{
		correctBounds(getCenterX(), getCenterY());
	}
	
	/**
	 * This corrects any bounds alignment with the max left, right, top,
	 * and bottom. If the graph window is larger then the max its squeezed
	 * into the max bounds. If the size is outside the bound restrictons
	 * then center it on a desired point.
	 * 
	 * @param desiredX => The desired x value of focus when correcting bounds.
	 * @param desiredY => The desired y value of focus when correcting bounds.
	 */
	public final void correctBounds(double desiredX, double desiredY)
	{
		double width = getWidth();
		double height = getHeight();
		
		// Correct the width and height of the window based on
		// the ratio adjustment (widthScale, heightScale).
		double widthScale = 1.0;
		widthScale = Math.max(widthScale, _minWidth / width);
		widthScale = Math.min(widthScale, _maxWidth / width);
		double heightScale = 1.0;
		heightScale = Math.max(heightScale, _minHeight / height);
		heightScale = Math.min(heightScale, _maxHeight / height);
		// Calculate the new adjusted bounds of the graph window.
		double diffRight, diffLeft, diffTop, diffBottom;
		diffRight = (_right - desiredX) * widthScale;
		diffLeft = (desiredX - _left) * widthScale;
		diffTop = (_top - desiredY) * heightScale;
		diffBottom = (desiredY - _bottom) * heightScale;
		// Set the new actual bounds.
		_right = desiredX + diffRight;
		_left = desiredX - diffLeft;
		_top = desiredY + diffTop;
		_bottom = desiredY - diffBottom;
		
		// Update the width and height
		width = getWidth();
		height = getHeight();
		
		// If the left and the right are both outside of the max
		// bounds then set them to the max's.
		if (width > getMaxBoundsWidth())
		{
			_left = _maxLeft;
			_right = _maxRight;
		}
		// If only the left is outside of the max bounds then shift it
		// over so its on the max left and it keeps the same width.
		else if (_left < _maxLeft)
		{
			_left = _maxLeft;
			_right = _maxLeft + width;
		}
		// If only the right is outside of the max bounds then shift it
		// over so its on the max right and it keeps the same width.
		else if (_right > _maxRight)
		{
			_right = _maxRight;
			_left = _maxRight - width;
		}

		// If the top and the bottom are both outside of the max
		// bounds then set them to the max's.
		if (height > getMaxBoundsHeight())
		{
			_bottom = _maxBottom;
			_top = _maxTop;
		}
		// If only the bottom is outside of the max bounds then shift it
		// over so its on the max bottom and it keeps the same height.
		else if (_bottom < _maxBottom)
		{
			_bottom = _maxBottom;
			_top = _maxBottom + height;
		}
		// If only the top is outside of the max bounds then shift it
		// over so its on the max top and it keeps the same height.
		else if (_top > _maxTop)
		{
			_top = _maxTop;
			_bottom = _maxTop - height;
		}
	}
	
	
	/**
	 * Sets the current display window bounds of the graph.
	 * 
	 * @param right => The value on the right of the graph window.
	 * @param left => The value on the left of the graph window.
	 * @param top => The value on the top of the graph window.
	 * @param bottom => The value of the bottom of the graph window.
	 */
	public final void setBounds(double right, double left, double top, double bottom)
	{
		_right = right;
		_left = left;
		_top = top;
		_bottom = bottom;
		correctBounds();
	}
	
	/**
	 * Sets the maximum display window bounds that can be seen on this graph.
	 * 
	 * @param maxRight => The maximum right value this graph will show.
	 * @param maxLeft => The maximum left value this graph will show.
	 * @param maxTop => The maximum top value this graph will show.
	 * @param maxBottom => The maximum bottom value this graph will show.
	 */
	public final void setMaxBounds(double maxRight, double maxLeft, double maxTop, double maxBottom)
	{
		_maxRight = maxRight;
		_maxLeft = maxLeft;
		_maxTop = maxTop;
		_maxBottom = maxBottom;
		correctBounds();
	}
	
	/**
	 * Sets the maximum and minimum display window width and height for this graph.
	 * 
	 * @param maxWidth => The maximum width of the graph window allowed.
	 * @param minWidth => The minimum width of the graph window allowed.
	 * @param maxHeight => The maximum height of the graph window allowed.
	 * @param minHeight => The minimum height of the graph window allowed.
	 */
	public final void setMaxSize(double maxWidth,  double minWidth, double maxHeight, double minHeight)
	{
		_maxWidth = maxWidth;
		_minWidth = minWidth;
		_maxHeight = maxHeight;
		_minHeight = minHeight;
		correctBounds();
	}

	/** 
	 * Sets the width and height in pixels of this graph and the loocation
	 * of this graph relative to the component that contains it.
	 * 
	 * @param offsetX => The x offset in pixels of the clipping rectangle.
	 * @param offsetY => The y offset in pixels of the clipping rectangle.
	 * @param width => The width in pixels of the clipping rectangle.
	 * @param height => The height in pixels of the clipping rectangle.
	 */
	public final void setClippingRectangle(int offsetX, int offsetY, int width, int height)
	{
		setClipping(new Rectangle(offsetX, offsetY, width, height));
	}
	
	/**
	 * Sets the region (shape) of this graph. The inside of the shape is where the graph is drawn.
	 * 
	 * @param clipping => The clipping shape.
	 */
	public final void setClipping(Shape clipping)
	{
		_clipping = clipping;
		_bounds = clipping.getBounds();
	}

	/**
	 * Sets the x-maximum display on this graph.
	 * 
	 * @param right => The value on the graph.
	 */
	public final void setRight(double right)
	{
		_right = right;
		correctBounds();
	}
	
	/**
	 * Sets the x-minimum display on this graph.
	 * 
	 * @param left => The value on the graph.
	 */
	public final void setLeft(double left)
	{
		_left = left;
		correctBounds();
	}

	/**
	 * Sets the y-maximum display on this graph.
	 * 
	 * @param top => The value on the graph.
	 */
	public final void setTop(double top)
	{
		_top = top;
		correctBounds();
	}
	
	/**
	 * Sets the y-minimum display on this graph.
	 * 
	 * @param bottom => The value on the graph.
	 */
	public final void setBottom(double bottom) 
	{
		_bottom = bottom;
		correctBounds();
	}
	 
	/**
	 * Returns the maximum x value this graph will show.
	 * 
	 * @param maxRight => The value on the graph.
	 */
	public final void setMaxRight(double maxRight)
	{
		_maxRight = maxRight;
		correctBounds();
	}
	
	/**
	 * Returns the minimum x value this graph will show.
	 * 
	 * @param maxLeft => The value on the graph.
	 */
	public final void setMaxLeft(double maxLeft)
	{
		_maxLeft = maxLeft;
		correctBounds();
	}

	/**
	 * Returns the maximum y value this graph will show.
	 * 
	 * @param maxTop => The value on the graph.
	 */
	public final void setMaxTop(double maxTop)
	{
		_maxTop = maxTop;
		correctBounds();
	}
	
	/**
	 * Returns the minimum y value this graph will show.
	 * 
	 * @param maxBottom => The value on the graph.
	 */
	public final void setMaxBottom(double maxBottom) 
	{
		_maxBottom = maxBottom;
		correctBounds();
	}
	
	/**
	 * Sets the maximum width of the graph window. This 
	 * restricts how much can be zoomed out in the x direction.
	 * 
	 * @param maxWidth => The value on the graph.
	 */
	public final void setMaxWidth(double maxWidth)
	{
		_maxWidth = maxWidth;
		correctBounds();
	}
	
	/**
	 * Sets the minimum width of the graph window. This 
	 * restricts how much can be zoomed in, in the x direction.
	 * 
	 * @param minWidth => The value on the graph.
	 */
	public final void setMinWidth(double minWidth)
	{
		_minWidth = minWidth;
		correctBounds();
	}
	
	/**
	 * Sets the maximum height of the graph window. This 
	 * restricts how much can be zoomed out in the y direction.
	 * 
	 * @param maxWidth => The value on the graph.
	 */
	public final void setMaxHeight(double maxHeight)
	{
		_maxHeight = maxHeight;
		correctBounds();
	}
	
	/**
	 * Sets the minimum height of the graph window. This 
	 * restricts how much can be zoomed in, in the y direction.
	 * 
	 * @param maxWidth => The value on the graph.
	 */
	public final void setMinHeight(double minHeight)
	{
		_minHeight = minHeight;
		correctBounds();
	}
	
	
	/** 
	 * Translates an X value on the graph to the equivalent X-coordinate on the screen.
	 * 
	 * @param x => The value on the graph to calculate the screen coordinate.
	 */
	public final double getScreenX(double x)
	{
		double delta = (x - _left) / getWidth();
		return (delta * _bounds.width) + _bounds.x;
	}
	
	/** 
	 * Translates a Y value on the graph to the equivalent Y-coordinate on the screen.
	 * 
	 * @param y => The value on the graph to calculate the screen coordinate.
	 */
	public final double getScreenY(double y)
	{
		double delta = (y - _bottom) / getHeight();
		return (_bounds.height - (delta * _bounds.height)) + _bounds.y;
	}
	
	/**
	 * Translates a screen X-coordinate to the equivalen graph X value.
	 * 
	 * @param x => An x-coordinate of a pixel on the screen over the graph.
	 */
	public final double getGraphX(int x)
	{
		double delta = (double)(x - _bounds.x) / (double)_bounds.width;
		return getWidth() * delta + _left;
	}
	
	/**
	 * Translates a screen Y-coordinate to the equivalent graph Y value.
	 * 
	 * @param y => A y-coordinate of a pixel on the screen over the graph.
	 */
	public final double getGraphY(int y)
	{
		double delta = 1.0 - (double)(y - _bounds.y) / (double)_bounds.height;
		return getHeight() * delta + _bottom;
	}
	
	/**
	 * Returns the Shape where the Graph is drawn in.
	 */
	public final Shape getClipping()
	{
		return _clipping;
	}

	/**
	 * Returns the clipping Shape's bounds.
	 */
	public final Rectangle getClippingBounds()
	{
		return _bounds;
	}
	
	/**
	 * Returns the x-maximum displayed on this graph.
	 */
	public final double getRight()
	{
		return _right;
	}
	
	 /**
	  * Returns the x-minimum displayed on this graph.
	  */
	public final double getLeft()
	{
		return _left;
	}
	
	/**
	 * Returns the y-maximum displayed on this graph.
	 */
	public final double getTop()
	{
		return _top;
	}
	
	/**
	 * Returns the y-minimum displayed on this graph.
	 */
	public final double getBottom()
	{
		return _bottom;
	}
	
	/**
	 * Returns the height of the graph window (difference between top and bottom).
	 */
	public final double getHeight()
	{
		return (_top - _bottom);
	}

	/**
	 * Returns the width of the graph window (difference between right and left).
	 */
	public final double getWidth()
	{
		return (_right - _left);
	}
	
	/**
	 * Returns the x value at the center on the graph.
	 */
	public final double getCenterX()
	{
		return (_right + _left) * 0.5;
	}

	/**
	 * Returns the y value at the center on the graph.
	 */
	public final double getCenterY()
	{
		return (_top + _bottom) * 0.5;
	}
	
	/**
	 * Returns the minimum x value this graph will show.
	 */
	public final double getMaxLeft()
	{
		return _maxLeft;
	}
		
	/**
	 * Returns the maximum x value this graph will show.
	 */
	public final double getMaxRight()
	{
		return _maxRight;
	}
	
	/**
	 * Returns the maximum y value this graph will show.
	 */
	public final double getMaxTop()
	{
		return _maxTop;
	}
	
	/**
	 * Returns the minimum y value this graph will show.
	 */
	public final double getMaxBottom()
	{
		return _maxBottom;
	}
	
	/**
	 * Returns the width of the bounds the graph will show in.
	 */
	public final double getMaxBoundsWidth()
	{
		return (_maxRight - _maxLeft);
	}
	
	/**
	 * Returns the height of the bounds the graph will show in.
	 */
	public final double getMaxBoundsHeight()
	{
		return (_maxTop - _maxBottom);
	}	
	
	/**
	 * Returns the maximum width of the graph window. This 
	 * restricts how much can be zoomed out in the x direction.
	 */
	public final double getMaxWidth()
	{
		return _maxWidth;
	}
	
	/**
	 * Returns the minimum width of the graph window. This 
	 * restricts how much can be zoomed in, in the x direction.
	 */
	public final double getMinWidth()
	{
		return _minWidth;
	}
	
	/**
	 * Returns the maximum height of the graph window. This 
	 * restricts how much can be zoomed out in the y direction.
	 */
	public final double getMaxHeight()
	{
		return _maxHeight;
	}
	
	/**
	 * Returns the minimum height of the graph window. This 
	 * restricts how much can be zoomed in, in the y direction.
	 */
	public final double getMinHeight()
	{
		return _minHeight;
	}


}
