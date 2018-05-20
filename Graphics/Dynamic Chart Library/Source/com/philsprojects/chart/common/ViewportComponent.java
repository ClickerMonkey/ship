package com.philsprojects.chart.common;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class ViewportComponent extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener, ComponentListener, CanvasListener
{

    // Additional canvas' that listen to the view port.
    private ArrayList<Canvas> canvases;

    // The last point the cursor was at.
    private Point lastPoint;

    // The current point the cursor is at.
    private Point currentPoint;

    // The point where the mouse was right clicked, used for zooming.
    private Point2D focusPoint;

    // Whether or not the right mouse button is down.
    private boolean rightDown = false;

    // Whether or not the left mouse button is down.
    private boolean leftDown = false;


    /**
     * Initializes a round robin database graph panel given the graph
     * it contains.
     * 
     * @param view => The round robin database graph to contain.
     */
    public ViewportComponent(int width, int height)
    {
	setPreferredSize(new Dimension(width, height));
	
	setFocusable(true);
	addMouseMotionListener(this);
	addMouseListener(this);
	addMouseWheelListener(this);

	canvases = new ArrayList<Canvas>();

	lastPoint = new Point();
	currentPoint = new Point();
	focusPoint = new Point2D.Double();
    }

    /**
     * Paints the component and all of the added canvases.
     */
    public void paint(Graphics g)
    {
	super.paint(g);

	synchronized (this)
	{
	    // If the panel tries to paint before any graphics object has been
	    // created then ignore it and return immediately.
	    if (g == null)
		return;

	    // Then draw all canvas from back to front in the order they 
	    // were added.
	    if (canvases != null)
	    {
		for (Canvas c : canvases)
		{
		    updateCanvas(c, g);
		}
	    }
	}
    }

    public void repaint()
    {
	paint(getGraphics());
    }

    public void updateCanvas(Canvas canvas, Graphics g)
    {
	synchronized (this)
	{
	    // If the panel tries to paint before any graphics object has been
	    // created then ignore it and return immediately.
	    if (g == null)
		return;

	    Graphics2D gr = (Graphics2D)g;

	    // Save the current clipping bounds to restore.
	    Rectangle clip = gr.getClipBounds();
	    
	    // If the clip is null then set it as the bounds of this component.
	    if (clip == null)
		clip = new Rectangle(0, 0, getWidth(), getHeight());

	    // Clip the area where the canvas lies.
	    gr.clip(canvas.getCanvasBounds());

	    // Draw the canvas
	    canvas.draw(gr);

	    // Restore the previous clipping bounds.
	    gr.clip(clip);

	}
    }

    /**
     * Updates all canvas' which listen to the given view port. This should be 
     * called when the view port has been changed and it requires redraws of
     * other canvases.
     * 
     * @param source => The given view port.
     */
    public void updateAllCanvas(Viewport source)
    {
	Graphics g = getGraphics();

	for (Canvas c : canvases)
	    if (c.listensTo(source))
		updateCanvas(c, g);
    }

    /**
     * Adds a canvas to the panel and draws it immediately.
     * 
     * @param canvas => The canvas to add to the panel.
     */
    public void addCanvas(Canvas canvas)
    {
	canvases.add(canvas);
	canvas.setListener(this);

	updateCanvas(canvas, getGraphics());
    }

    /**
     * Returns the view port, if any, under the given coordinates in this 
     * component's world space.
     * 
     * @param x => The x-coordinate in this component's world space.
     * @param y => The y-coordinate in this component's world space.
     * @return => The view port under the coordinates.
     */
    public Viewport getViewUnder(int x, int y)
    {
	for (Canvas c : canvases)
	{
	    if ((c instanceof Viewport) && c.getCanvasBounds().contains(x, y))
		return (Viewport)c;
	}

	return null;
    }


    /**
     * Handles translating and zooming using the mouse. Translating
     * is done using the left mouse button and dragging the mouse anywhere.
     * Zooming is done using the right mouse button and dragging
     * the mouse up and down to zoom in and out.
     */
    public void mouseDragged(MouseEvent e)
    {
	synchronized (this)
	{
	    int x = e.getX();
	    int y = e.getY();

	    // Get the first view port under the cursor.
	    Viewport view = getViewUnder(x, y);

	    // If there is no view port under the cursor then ignore it.
	    if (view == null)
		return;

	    lastPoint.setLocation(currentPoint);
	    currentPoint.setLocation(x, y);

	    if (leftDown)
	    {
		double dx = view.toWorldX(lastPoint.x) - view.toWorldX(currentPoint.x);
		double dy = view.toWorldY(lastPoint.y) - view.toWorldY(currentPoint.y);
		view.translateX(dx);
		view.translateY(dy);
	    }
	    else if (rightDown)
	    {
		double dy = (view.toWorldY(lastPoint.y) - view.toWorldY(currentPoint.y));
		view.zoom(1.0 + (Math.signum(dy) * 0.03), focusPoint.getX(), focusPoint.getY());
	    }

	    updateAllCanvas(view);
	}
    }

    /**
     * Handles updating the mouse position when the panel is clicked on.
     * If the panel is clicked by the right button then the focus point
     * is saved.
     */
    public void mousePressed(MouseEvent e)
    {
	synchronized (this)
	{
	    int x = e.getX();
	    int y = e.getY();

	    // Get the first view port under the cursor.
	    Viewport view = getViewUnder(x, y);

	    // If there is no view port under the cursor then ignore it.
	    if (view == null)
		return;

	    currentPoint.setLocation(x, y);
	    lastPoint.setLocation(currentPoint);

	    if (e.getButton() == MouseEvent.BUTTON1)
		leftDown = true;
	    if (e.getButton() == MouseEvent.BUTTON3)
	    {
		rightDown = true;
		focusPoint.setLocation(view.toWorldX(x), view.toWorldY(y));
	    }

	    updateAllCanvas(view);
	}
    }

    /**
     * Signals the release of either the right or left mouse button.
     */
    public void mouseReleased(MouseEvent e)
    {		
	synchronized (this)
	{
	    if (e.getButton() == MouseEvent.BUTTON1)
		leftDown = false;
	    if (e.getButton() == MouseEvent.BUTTON3)
		rightDown = false;
	}
    }

    /**
     * Handles zooming in and out of a graph using the mouse wheel. Scrolling
     * forwards zooms in at the current point, scrolling backwards zooms out
     * from the current point.
     */
    public void mouseWheelMoved(MouseWheelEvent e)
    {
	synchronized (this)
	{
	    // Get the first view port under the cursor.
	    Viewport view = getViewUnder(currentPoint.x, currentPoint.y);

	    // If there is no view port under the cursor then ignore it.
	    if (view == null)
		return;

	    double graphX = view.toWorldX(currentPoint.x);
	    double graphY = view.toWorldY(currentPoint.y);

	    if (e.getWheelRotation() < 0)
		view.zoom(1.1, graphX, graphY);
	    else if (e.getWheelRotation() > 0)
		view.zoom(0.9, graphX, graphY);

	    updateAllCanvas(view);
	}
    }

    /**
     * Handles setting the cursor of the graph as the mouse moves acrossed it.
     */
    public void mouseMoved(MouseEvent e)
    {
	synchronized (this)
	{
	    int x = e.getX();
	    int y = e.getY();

	    // Get the first view port under the cursor.
	    Viewport view = getViewUnder(x, y);

	    // If there is no view port under the cursor then ignore it.
	    if (view == null)
		return;

	    lastPoint.setLocation(currentPoint);
	    currentPoint.setLocation(x, y);
	}
    }

    public void componentResized(ComponentEvent e) 
    {
    }

    public void requestRedraw(Canvas canvas) 
    {
	if (canvas instanceof Viewport)
	    updateAllCanvas((Viewport)canvas);
	else
	    updateCanvas(canvas, getGraphics());
    }

    public void mouseClicked(MouseEvent e) { }

    public void mouseEntered(MouseEvent e) { }

    public void mouseExited(MouseEvent e) { }

    public void componentHidden(ComponentEvent e) { }

    public void componentMoved(ComponentEvent e) { }

    public void componentShown(ComponentEvent e) { }



}
