package graphing.graphs;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;


public class FunctionGraph extends NormalGraph
{

	private FunctionGraphListener _listener;
	
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
	public FunctionGraph(int offsetX, int offsetY, int width, int height, 
			double right, double left, double top, double bottom, 
			double maxRight, double maxLeft, double maxTop, double maxBottom, 
			double maxWidth, double minWidth, double maxHeight, double minHeight)
	{
		super(offsetX, offsetY, width, height, 
			right, left, top, bottom, 
			maxRight, maxLeft, maxTop, maxBottom, 
			maxWidth, minWidth, maxHeight, minHeight);
	}
	
	@Override
	public void drawGraph(Graphics2D gr)
	{
		super.drawGraph(gr);
		
		if (_listener == null)
			return;

		gr.setColor(Color.red);
		gr.setStroke(new BasicStroke(2));
		
		// The list of points calculated from F(x)
		GeneralPath path = new GeneralPath();
		
		double dx, x;
		// The value on the graph per pixel
		dx = getWidth() / getClippingBounds().getWidth();
		x = _left;
		
		path.moveTo(getScreenX(x), getScreenY(_listener.F(x)));
		while (x <= _right)
		{
			x += dx;
			path.lineTo(getScreenX(x), getScreenY(_listener.F(x)));
		}
		gr.draw(path);
	}
	
	public void setListener(FunctionGraphListener listener)
	{
		_listener = listener;
	}

}
