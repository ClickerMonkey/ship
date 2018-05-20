package graphing.testing;
import graphing.GraphPanel;
import graphing.graphs.NormalGraph;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;


public class NormalGraphWindow extends JFrame implements MouseMotionListener, MouseListener, KeyListener,  MouseWheelListener
{
	
	public static void main(String[] args)
	{
		new NormalGraphWindow();
	}
	
	private static final long serialVersionUID = 7450984307104141622L;
	
	private GraphPanel _panel;
	private NormalGraph _graph;
	private Point _lastPoint;
	private Point _currentPoint;
	private boolean _rightDown = false;
	private boolean _leftDown = false;
	
	public NormalGraphWindow()
	{
		_graph = new NormalGraph(0, 0, 512, 512, 5.0, -5.0, 5.0, -5.0, 200.0, -200.0, 200.0, -200.0, 100.0, 0.1, 100.0, 0.1);
		_graph.setVisuals(1.0, new Font("Tahoma", Font.PLAIN, 12), Color.darkGray, Color.white, Color.blue, Color.lightGray);
		
		_panel = new GraphPanel(_graph);
		_panel.addMouseMotionListener(this);
		_panel.addMouseListener(this);
		_panel.addKeyListener(this);
		_panel.addMouseWheelListener(this);
		
		setTitle("Normal Graph");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(_panel);
		pack();
		setVisible(true);
	}
	
	public void mouseDragged(MouseEvent e)
	{
		_lastPoint = _currentPoint;
		_currentPoint = new Point(e.getX(), e.getY());
		if (_lastPoint != null && _currentPoint != null)
		{
			if (_leftDown)
			{
				double dx = _graph.getGraphX(_lastPoint.x) - _graph.getGraphX(_currentPoint.x);
				double dy = _graph.getGraphY(_lastPoint.y) - _graph.getGraphY(_currentPoint.y);
				_graph.translateX(dx);
				_graph.translateY(dy);
			}
			else if (_rightDown)
			{
				double y = _graph.getGraphY(_currentPoint.y);
				double dy = (_graph.getGraphY(_lastPoint.y) - y);
				_graph.zoomIn(1.0 + (Math.signum(dy) * 0.03));
			}
			
			_panel.repaint();
		}
	}
	
	public void mousePressed(MouseEvent e)
	{
		_lastPoint = null;
		_currentPoint = new Point(e.getX(), e.getY());

		if (e.getButton() == MouseEvent.BUTTON1)
			_leftDown = true;
		if (e.getButton() == MouseEvent.BUTTON3)
			_rightDown = true;
	}

	public void mouseReleased(MouseEvent e)
	{		
		if (e.getButton() == MouseEvent.BUTTON1)
			_leftDown = false;
		if (e.getButton() == MouseEvent.BUTTON3)
			_rightDown = false;
	}
	
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		if (e.getWheelRotation() < 0)
			_graph.zoomIn(1.02);
		else if (e.getWheelRotation() > 0)
			_graph.zoomOut(1.02);
		
		_panel.repaint();
	}

	public void mouseMoved(MouseEvent e)
	{
		_lastPoint = _currentPoint;
		_currentPoint = new Point(e.getX(), e.getY());
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

	public void keyPressed(KeyEvent e) 
	{
	}

	public void keyReleased(KeyEvent e) 
	{
	}

	public void keyTyped(KeyEvent e) 
	{
	}

	
}
