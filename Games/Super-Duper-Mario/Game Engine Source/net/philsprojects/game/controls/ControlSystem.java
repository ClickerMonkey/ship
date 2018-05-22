package net.philsprojects.game.controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import net.philsprojects.game.GraphicsLibrary;
import net.philsprojects.game.util.BoundingBox;
import net.philsprojects.game.util.Vector;


public final class ControlSystem extends Control  implements KeyListener, MouseMotionListener, MouseListener
{
	private static ControlSystem _instance = new ControlSystem();

	public static ControlSystem getInstance()
	{
		return _instance;
	}

	private String _texture = null;
	private Vector _mouse = Vector.zero();
	private boolean _mouseLeftDown = false;
	private boolean _mouseRightDown = false;

	private ControlSystem()
	{
		super("engine.controls.ControlSystem", null, BoundingBox.zero(), 0, 0);
	}

	@Override
	public void draw()
	{
		final GraphicsLibrary g = GraphicsLibrary.getInstance();
		if (_texture != null)
			g.setTexture(_texture);
		super.draw();
	}

	@Override
	public void update(float deltatime)
	{
		this.mouse(_mouse.x, _mouse.y, _mouseLeftDown, _mouseRightDown);
		super.update(deltatime);
	}

	public void setTexture(String texture)
	{
		_texture = texture;
	}

	public String getTexture() 
	{
		return _texture;
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

	public void mouseDragged(MouseEvent e)
	{
	}

	public void mouseClicked(MouseEvent e)
	{
	}

	public void mouseMoved(MouseEvent e)
	{
		_mouse.set(e.getX(), getBounds().getHeight() - e.getY());
	}

	public void mouseEntered(MouseEvent e)
	{
		_mouse.set(e.getX(), getBounds().getHeight() - e.getY());
	}

	public void mouseExited(MouseEvent e)
	{
		_mouse.set(e.getX(), getBounds().getHeight() - e.getY());
	}

	public void mousePressed(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			_mouseLeftDown = true;
		}
		else if (e.getButton() == MouseEvent.BUTTON3)
		{
			_mouseRightDown = true;
		}
	}

	public void mouseReleased(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			_mouseLeftDown = false;
		}
		else if (e.getButton() == MouseEvent.BUTTON3)
		{
			_mouseRightDown = false;
		}
	}    


}
