package net.philsprojects.game.controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.GLCanvas;

public class Keyboard implements KeyListener
{

	private static Keyboard _instance = new Keyboard();

	public static Keyboard getInstance()
	{
		return _instance;
	}

	private boolean[] _isKeyDown = null;
	private boolean _shiftKeyDown = false;
	private boolean _ctrlKeyDown = false;
	private boolean _altKeyDown = false;

	private Keyboard() 
	{
		_isKeyDown = new boolean[256];
	}    

	public boolean isKeyDown(int key)
	{
		return _isKeyDown[key];
	}

	public boolean isShiftDown()
	{
		return _shiftKeyDown;
	}

	public boolean isCtrlDown()
	{
		return _ctrlKeyDown;
	}

	public boolean isAltDown()
	{
		return _altKeyDown;
	}

	public void registerWithCanvas(GLCanvas canvas)
	{
		canvas.addKeyListener(this);
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
