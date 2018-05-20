package graphing.graphs;
import graphing.Graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class NormalGraph extends Graph
{
	
	private Color _backgroundShade;
	private Color _axisShade;
	private Color _gridShade;
	private double _gridSize;
	private Font _valueFont;
	private Color _valueForeColor;
	
	
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
	 * 		how much can be zoomed in in the x direction.
	 * @param maxHeight => The maximum height of the graph window. This restricts
	 * 		how much can be zoomed out in the y direction.
	 * @param minHeight => The minimum height of this graph window. This restricts
	 * 		how much can be zoomed in in the y direction.
	 */
	public NormalGraph(int offsetX, int offsetY, int width, int height, 
			double right, double left, double top, double bottom, 
			double maxRight, double maxLeft, double maxTop, double maxBottom, 
			double maxWidth, double minWidth, double maxHeight, double minHeight)
	{
		super(offsetX, offsetY, width, height, 
			right, left, top, bottom, 
			maxRight, maxLeft, maxTop, maxBottom, 
			maxWidth, minWidth, maxHeight, minHeight);
	}
	
	public void setVisuals(double gridWidth, Font valueFont, Color valueForeColor, Color background, Color axis, Color grid)
	{
		_valueFont = valueFont;
		_valueForeColor = valueForeColor;
		_gridSize = gridWidth;
		_backgroundShade = background;
		_axisShade = axis;
		_gridShade = grid;
	}
	
	@Override
	protected void drawGraph(Graphics2D gr)
	{
		//Background
		gr.setColor(_backgroundShade);
		gr.fill(_clipping);
		
		//Draw Grids
		int power = (int)Math.floor(Math.log10(getWidth()));
		double gridExtra = Math.pow(10.0, power + 1);
		double gridLarge = Math.pow(10.0, power);
		double gridSmall = Math.pow(10.0, power - 1);
		double delta = (getWidth() - gridLarge) / (gridExtra - gridLarge);

		Color half = deltaColor(0.5, _gridShade, _backgroundShade);
		Color end = deltaColor(0.025, _backgroundShade, _gridShade);
		
		drawGridLines(gr, gridSmall, gridSmall, 1.0f, deltaColor(delta, half, end));
		drawGridLines(gr, gridLarge, gridLarge, 1.5f, deltaColor(delta, _gridShade, half));
		drawGridLines(gr, gridExtra, gridExtra, 2.5f, _gridShade);
		
		// Determine what interval you want to draw your values at.
		double width = gridLarge;
		double height = gridLarge;
		
		int maxWide = (int)Math.ceil(getWidth() / gridLarge);
		int maxHigh = (int)Math.ceil(getHeight() / gridLarge);
		
		if (maxWide <= 2)
			width *= 0.2;
		else if (maxWide <= 5)
			width *= 0.5;
		
		if (maxHigh <= 2)
			height *= 0.2;
		else if (maxHigh <= 5)
			height *= 0.5;
		
		drawGridValues(gr, width, height, _valueFont, _valueForeColor);

		// Set Axis drawing values.
		gr.setStroke(new BasicStroke(3));
		gr.setColor(_axisShade);

		Rectangle b = _bounds;
		
		// Check if the X-Axis is on the screen and if so draw it.
		int y = (int)getScreenY(0.0);
		if (_clipping.contains(b.x + (b.width >> 1), y))
			gr.drawLine(b.x, y, b.x + b.height, y);
		
		// Check if the Y-Axis is on the screen and if so draw it.
		int x = (int)getScreenX(0.0);
		if (_clipping.contains(x, b.y + (b.height >> 1))) 
			gr.drawLine(x, b.y, x, b.y + b.height);
	}
	
	private Color deltaColor(double delta, Color start, Color end)
	{
		int r = (int)((end.getRed() - start.getRed()) * delta + start.getRed());
		int g = (int)((end.getGreen() - start.getGreen()) * delta + start.getGreen());
		int b = (int)((end.getBlue() - start.getBlue()) * delta + start.getBlue());
		return new Color(r, g, b);
	}
	
	public double getGridSize() 
	{
		return _gridSize;
	}
	
	public Color getBackgroundColor() 
	{
		return _backgroundShade;
	}
	
	public Color getAxisColor()
	{
		return _axisShade;
	}
	
	public Color getGridColor()
	{
		return _gridShade;
	}

}
