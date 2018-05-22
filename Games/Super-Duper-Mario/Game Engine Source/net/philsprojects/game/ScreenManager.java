package net.philsprojects.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import net.philsprojects.game.util.NameHashTable;


public final class ScreenManager implements IDraw, IUpdate, KeyListener, MouseMotionListener, MouseListener
{

	private static ScreenManager _instance;

	public static ScreenManager getInstance()
	{
		return _instance;
	}

	public static void initialize(int maximumScreens, int width, int height)
	{
		_instance = new ScreenManager(maximumScreens);
		Screen.setSize(width, height);
	}

	private final int STATE_UPDATE = 0;
	private final int STATE_ENTRANCE = 1;
	private final int STATE_EXIT = 2;

	private NameHashTable<Screen> _screens;
	private Screen _current = null;
	private Screen _next = null;
	private float _time = 0f;
	private int _state = STATE_UPDATE;

	private ScreenManager(int maximumScreens)
	{
		_screens = new NameHashTable<Screen>(maximumScreens);
	}

	public boolean addScreen(Screen s)
	{
		return _screens.add(s);
	}

	public boolean setScreen(String name)
	{
		Screen s = _screens.get(name);
		if (s != null)
		{
			if (_current == null)
			{
				_current = s;
				_current.load();
				_state = STATE_ENTRANCE;
			}
			else
			{
				_state = STATE_EXIT;
				_next = s;
			}
			return true;
		}
		return false;
	}

	public void draw()
	{
		if (_current == null)
			return;

		if (_state == STATE_UPDATE)
			_current.draw();
		else if (_state == STATE_ENTRANCE)
			_current.drawEntrance();
		else if (_state == STATE_EXIT)
			_current.drawExit();
	}

	public void update(float deltatime)
	{
		if (_current == null)
			return;

		if (_state == STATE_UPDATE)
		{
			_current.update(deltatime);
		}
		else if (_state == STATE_ENTRANCE)
		{
			_current.updateEntrance(deltatime);
			_time += deltatime;
			if (_time > _current.getEntranceDuration())
			{
				_state = STATE_UPDATE;
				_time = 0f;
			}
		}
		else if (_state == STATE_EXIT)
		{
			_current.updateExit(deltatime);
			_time += deltatime;
			if (_time > _current.getExitDuration())
			{
				_current.dispose();
				_current = _next;
				_current.load();
				_state = STATE_ENTRANCE;
				_time = 0f;
			}
		}
	}

	public Screen getCurrent()
	{
		return _current;
	}

	public Screen getScreen(String name)
	{
		return _screens.get(name);
	}

	public void dispose()
	{
		if (_current != null)
			_current.dispose();
	}

	public boolean isVisible()
	{
		return true;
	}

	public void setVisible(boolean visible)
	{
	}

	public boolean isEnabled()
	{
		return true;
	}

	public void keyPressed(KeyEvent e)
	{
		if (_current != null)
			_current.keyPressed(e);
	}

	public void keyReleased(KeyEvent e)
	{
		if (_current != null)
			_current.keyReleased(e);
	}

	public void mouseMoved(MouseEvent e)
	{
		if (_current != null)
			_current.mouseMoved(e);
	}

	public void mousePressed(MouseEvent e)
	{
		if (_current != null)
			_current.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e)
	{
		if (_current != null)
			_current.mouseReleased(e);
	}

	public void keyTyped(KeyEvent e)
	{
	}

	public void mouseClicked(MouseEvent e)
	{
	}

	public void mouseDragged(MouseEvent e)
	{
	}

	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}
}
