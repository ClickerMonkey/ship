package net.philsprojects.game;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class Screen implements IName, IUpdate, IDraw
{

	protected static int _width;
	protected static int _height;

	public static void setSize(int width, int height)
	{
		_width = width;
		_height = height;
	}

	private String _name = null;

	public Screen(String name)
	{
		_name = name;
		ScreenManager.getInstance().addScreen(this);
	}

	public final String getName()
	{
		return _name;
	}

	public final boolean isVisible()
	{
		return (ScreenManager.getInstance().getCurrent() == this);
	}

	public final boolean isEnabled()
	{
		return true;
	}

	public void setVisible(boolean visible)
	{
	}


	public abstract void load();

	public abstract void dispose();

	public abstract void mouseMoved(MouseEvent e);

	public abstract void keyPressed(KeyEvent e);

	public abstract void keyReleased(KeyEvent e);

	public abstract void mousePressed(MouseEvent e);

	public abstract void mouseReleased(MouseEvent e);

	public abstract float getEntranceDuration();

	public abstract float getExitDuration();

	public abstract void updateEntrance(float deltatime);

	public abstract void updateExit(float deltatime);

	public abstract void drawEntrance();

	public abstract void drawExit();
}
