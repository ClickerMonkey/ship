package graphing;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.DecimalFormat;

/**
 * 
 * @author Philip Diffenderfer
 */
public class GraphController implements MouseMotionListener, MouseListener, KeyListener
{

	/** Input based on keyboard. */
	public static final int INPUT_KEY = 1;
	/** Input based on mouse. */
	public static final int INPUT_MOUSE = 2;
	
	/** The GraphPanel to listen to. */
	private GraphPanel _graphPanel;
	
	/** The last mouse position. */
	private Point _lastPoint;
	/** The current mouse position. */
	private Point _currentPoint;
	/** The starting mouse position when it was pressed. */
	private Point _initialPoint;
	
	/** The input type (Key/Mouse) used for translation. */
	private int _translateInputType = INPUT_MOUSE;
	/** The input value expected from the input type. */
	private int _translateInput = MouseEvent.BUTTON1;
	/** If the given input for translating has been triggered. */
	private boolean _translateOn = false;
	/** The scale (damping) for translation on the X-axis. */
	private double _translateScaleX = 1.0;
	/** The scale (damping) for translating on the Y-axis. */
	private double _translateScaleY = 1.0;
	
	/** The input type (Key, Mouse) used for zooming. */
	private int _zoomInputType = INPUT_MOUSE;
	/** The input value expected from the input type. */
	private int _zoomInput = MouseEvent.BUTTON3;
	/** If the given input for zooming has been triggered. */
	private boolean _zoomOn = false;
	/** The scale at which to zoom in and out. */
	private double _zoomScale = 1.05;
	
	/** The input type (Key, Mouse) used for displaying the values under the cursor. */
	private int _cursorInputType = INPUT_KEY;
	/** The input value expected for the input type. */
	private int _cursorInput = KeyEvent.VK_M;
	/** If the given input for displaying the values under the cursor has been triggered. */
	private boolean _cursorOn = false;

	
	/**
	 * Initializes a GraphController with its default values and no
	 * initial GraphPanel.
	 */
	public GraphController()
	{
	}
	
	/**
	 * Initializes a {@link GraphController with its default values 
	 * and a GraphPanel to listen to.
	 * 
	 * @param graphPanel => A GraphPanel to listen to.
	 */
	public GraphController(GraphPanel graphPanel)
	{
		setGraphPanel(graphPanel);
	}
	
	/**
	 * Sets the GraphPanel this GraphController is listening to. If this
	 * GraphController is already listening to anothet GraphPanel then it
	 * removes itself from the old one and starts listening to the new one.
	 * 
	 * @param graphPanel => The GraphPanel to listen to.
	 */
	public void setGraphPanel(GraphPanel graphPanel)
	{
		// If they're the same ignore
		if (_graphPanel == graphPanel)
			return;
		// If the controller was already set remove it as a listener
		// from this GraphPanel.
		if (_graphPanel != null)
		{
			_graphPanel.removeKeyListener(this);
			_graphPanel.removeMouseListener(this);
			_graphPanel.removeMouseMotionListener(this);
		}
		// Set the graph panel whether its null or not.
		_graphPanel = graphPanel;
		
		// If the controller is null exit, they could just want to disable it.
		if (_graphPanel != null)
		{
			_graphPanel.addKeyListener(this);
			_graphPanel.addMouseListener(this);
			_graphPanel.addMouseMotionListener(this);
		}
		
	}
	
	/**
	 * Draws the values under the cursor on the GraphPanel.
	 */
	public void drawCursor(int x, int y)
	{
		Graph g = _graphPanel.getGraph();
		double valueX = g.getGraphX(x);
		double valueY = g.getGraphY(y);

		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(1);
		df.setMaximumFractionDigits(1);
		
		int power = (int)Math.floor(Math.log10(g.getWidth()));
		if (power < 0)
			df.setMaximumFractionDigits(-power + 3);
		
		
		String s = String.format("(%s, %s)", df.format(valueX), df.format(valueY));
		Graphics gr = _graphPanel.getGraphics();
		gr.setColor(Color.black);
		gr.drawString(s, x, y);
	}
	
	/**
	 * Triggered everytime the mouse is dragged across the GraphPanel.
	 */
	public void mouseDragged(MouseEvent e)
	{
		_lastPoint = _currentPoint;
		_currentPoint = new Point(e.getX(), e.getY());
		
		if (_initialPoint == null)
			_initialPoint = new Point(e.getX(), e.getY());
		
		if (_lastPoint != null)
		{
			boolean graphChanged = false;
			Graph g = _graphPanel.getGraph();
			
			// If the translation is on by some input then calculate
			// the translation values and translate the graph.
			if (_translateOn)
			{
				// These are the values on the graphs scale based on the
				// mouse's current and last positions.
				double lastValueX, lastValueY, currValueX, currValueY;
				lastValueX = g.getGraphX(_lastPoint.x);
				currValueX = g.getGraphX(_currentPoint.x);
				lastValueY = g.getGraphY(_lastPoint.y);
				currValueY = g.getGraphY(_currentPoint.y);
				
				// Translate by the difference in values on the graph but
				// times it by some translation scale to dampen the movement.
				g.translateX((lastValueX - currValueX) * _translateScaleX);
				g.translateY((lastValueY - currValueY) * _translateScaleY);
				
				graphChanged = true;
			}
			// If the zoom is on by some input then calculate how
			// much to zoom and zoom on the graph.
			if (_zoomOn)
			{
				double currValueY, lastValueY, initValueX, initValueY;
				lastValueY = g.getGraphY(_lastPoint.y);
				currValueY = g.getGraphY(_currentPoint.y);
				
				initValueX = g.getGraphX(_initialPoint.x);
				initValueY = g.getGraphY(_initialPoint.y);
				
				if (lastValueY > currValueY)
					g.zoomIn(_zoomScale, initValueX, initValueY);
				else
					g.zoomOut(_zoomScale, initValueX, initValueY);
				
				graphChanged = true;
			}
			
// TODO dont draw on the surface, integrate into the graph.
//			// Include if the cursor values should be drawn.
//			graphChanged = _cursorOn || graphChanged;
			
			// Repaint the graph if some value has changed.
			if (graphChanged)
			{
				_graphPanel.repaint();
				// After the graph Panel has been drawn and if the cursor
				// is on then draw the values under it.
				if (_cursorOn)
				{
// TODO dont draw on the surface, integrate into the graph.
//					drawCursor(e.getX(), e.getY());
				}
			}
		}
	}
	
	/**
	 * Triggered everytime the mouse is pressed on the GraphPanel.
	 */
	public void mousePressed(MouseEvent e) 
	{
		_lastPoint = null;
		_currentPoint = new Point(e.getX(), e.getY());
		
		// If the translation type for input is by mouse and
		// the button pressed matches that input turn translation on.
		if (_translateInputType == INPUT_MOUSE && _translateInput == e.getButton())
		{
			_translateOn = true;
		}
		// If the zoom type for input is by mouse and the button
		// pressed matches that input turn zoom on.
		if (_zoomInputType == INPUT_MOUSE && _zoomInput == e.getButton())
		{
			_zoomOn = true;
		}
		// If the cursor type for input is by mouse and the button pressed
		// matches that input turn displaying the value under the cursor on.
		if (_cursorInputType == INPUT_MOUSE && _cursorInput == e.getButton())
		{
			_cursorOn = true;
		}
	}
	
	/**
	 * Triggered everytime the mouse is released on the GraphPanel.
	 */
	public void mouseReleased(MouseEvent e) 
	{
		// If the translation type for input is by mouse and
		// the button released matches that input turn translation on.
		if (_translateInputType == INPUT_MOUSE && _translateInput == e.getButton())
		{
			_translateOn = false;
		}
		// If the zoom type for input is by mouse and the button
		// released matches that input turn zoom on.
		if (_zoomInputType == INPUT_MOUSE && _zoomInput == e.getButton())
		{
			_zoomOn = false;
		}
		// If the cursor type for input is by mouse and the button released
		// matches that input turn displaying the value under the cursor off.
		if (_cursorInputType == INPUT_MOUSE && _cursorInput == e.getButton())
		{
			_cursorOn = false;
		}
		// Clear the initial mouse position
		_initialPoint = null;
	}

	/**
	 * Triggered everytime the keyboard is pressed when the GraphPanel is focused.
	 */
	public void keyPressed(KeyEvent e)
	{
		boolean graphChanged = false;
		// If the translation type for input is by keyboard and
		// the key pressed matches that input turn translation on.
		if (_translateInputType == INPUT_KEY && _translateInput == e.getKeyCode())
		{
			_translateOn = true;
			graphChanged = true;
		}
		// If the zoom type for input is by keyboard and the key
		// pressed matches that input turn zoom on.
		if (_zoomInputType == INPUT_KEY && _zoomInput == e.getKeyCode())
		{
			_zoomOn = true;
			graphChanged = true;
		}
		// If the cursor type for input is by keyboard and the key pressed
		// matches that input turn displaying the value under the cursor on.
		if (_cursorInputType == INPUT_KEY && _cursorInput == e.getKeyCode())
		{
			_cursorOn = true;
			graphChanged = true;
		}
		// Repaint the graph if anything has changed.
		if (graphChanged)
		{
			_graphPanel.repaint();
		}
	}

	/**
	 * Triggered everytime the keyboard is released when the GraphPanel is focused.
	 */
	public void keyReleased(KeyEvent e)
	{
		boolean graphChanged = false;
		// If the translation type for input is by keyboard and the key
		// released matches that input turn translation on.
		if (_translateInputType == INPUT_KEY && _translateInput == e.getKeyCode())
		{
			_translateOn = false;
			graphChanged = true;
		}
		// If the zoom type for input is by keyboard and the key
		// released matches that input turn zoom on.
		if (_zoomInputType == INPUT_KEY && _zoomInput == e.getKeyCode())
		{
			_zoomOn = false;
			graphChanged = true;
		}
		// If the cursor type for input is by keyboard and the key released
		// matches that input turn displaying the value under the cursor off.
		if (_cursorInputType == INPUT_KEY && _cursorInput == e.getKeyCode())
		{
			_cursorOn = false;
			graphChanged = true;
		}
		// Repaint the graph if anything has changed.
		if (graphChanged)
		{
			_graphPanel.repaint();
		}
	}

	/**
	 * Triggered everytime the mouse moves across the GraphPanel.
	 */
	public void mouseMoved(MouseEvent e)
	{	
		// If the cursor is on repaint the graph and draw the values.
		if (_cursorOn)
		{
// TODO dont draw on the surface, integrate into the graph.
//			_graphPanel.repaint();
//			drawCursor(e.getX(), e.getY());
		}
	}
	
	public void keyTyped(KeyEvent e) 
	{ 
	}

	public void mouseClicked(MouseEvent e) 
	{
	}

	public void mouseEntered(MouseEvent e) 
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}

}
