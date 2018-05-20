package graphing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


/**
 * A Componenet containing a Graph which is drawn to this Panel's
 * surface using double buffering.
 * 
 * @author Philip Diffenderfer
 */
public class GraphPanel extends JPanel
{

	private static final long serialVersionUID = 4356834422783028071L;
	
	/** The graph drawn in this Panel. */
	private Graph _graph;
	/** The double buffer for drawing the Graph. */
	private Image _buffer;
	/** The graphics object of the double buffer. */
	private Graphics2D _graphics;

	
	/**
	 * Initializes a GraphPanel with the initial graph contained
	 * in this GraphPanel.
	 * 
	 * @param graph => The graph contained in this GraphPanel.
	 */
	public GraphPanel(Graph graph)
	{
		setGraph(graph);
		setFocusable(true);
		setDoubleBuffered(true);
	}
	
	
	/**
	 * Overrides the paint method for JPanel and draws the graph.
	 */
	@Override
	public void paint(Graphics g) 
	{
		if (_buffer == null || g == null)
			return;
		
		_graphics = (Graphics2D)g;
		_graphics.setColor(this.getBackground());
		_graphics.fillRect(0, 0, getWidth(), getHeight());
		
		_graph.draw(_graphics);
		// _graphics.dispose();
		
		// g.drawImage(_buffer, 0, 0, this);
		// g.dispose();
	}
	
	/**
	 * Forces a repaint (redraw) of the graph.
	 */
	public void update(Graphics g)
	{
		paint(g);
	}
	
	
	/**
	 * Sets the graph contained in this GraphPanel.
	 * 
	 * @param graph => The graph contained in this GraphPanel.
	 */
	public void setGraph(Graph graph)
	{
		_graph = graph;
		
		int width = (int)_graph.getClippingBounds().getWidth();
		int height = (int)_graph.getClippingBounds().getHeight();
		setPreferredSize(new Dimension(width, height));
		
		_buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}
	
	/**
	 * Returns the graph contained in this GraphPanel.
	 */
	public Graph getGraph()
	{
		return _graph;
	}
	
}