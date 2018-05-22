package net.philsprojects.game;

import net.philsprojects.game.sprites.Quad;
import net.philsprojects.game.util.BoundingBox;
import net.philsprojects.game.util.Iterator;
import net.philsprojects.game.util.LinkedList;
import net.philsprojects.game.util.Rectangle;
import net.philsprojects.game.util.Vector;

/**
 * @author Philip Diffenderfer
 */
public final class Camera
{

	// \\
	private static Camera _instance;

	/**
	 * 
	 */
	public static Camera getInstance()
	{
		return _instance;
	}

	/**
	 * @param bounds
	 */
	public static void initialize(Rectangle bounds)
	{
		initialize((int)bounds.getX(), (int)bounds.getY(), (int)bounds.getWidth(), (int)bounds.getHeight());
	}

	/**
	 * @param bounds
	 */
	public static void initialize(int x, int y, int width, int height)
	{
		_instance = new Camera(x, y, width, height);
	}

	// Location and Size of Camera's view on the world.\\
	private float _actualX = 0.0f;
	private float _actualY = 0.0f;
	private int _x = 0;
	private int _y = 0;
	private int _width = 0;
	private int _height = 0;
	private BoundingBox _bounds = null;

	// All CameraObservers that are affected when the camera changes location and/or size.\\
	private LinkedList<ICameraObserver> _observers;

	/**
	 * 
	 */
	private Camera(int x, int y, int width, int height)
	{
		_observers = new LinkedList<ICameraObserver>();
		_actualX = _x = x;
		_actualY = _y = y;
		_width = width;
		_height = height;
	}

	/**
	 * 
	 */
	public final void update()
	{
		notifyObservers();
	}

	/**
	 * @param deltaX
	 * @param deltaY
	 */
	public final void translate(float deltaX, float deltaY)
	{
		// if (deltaX != 0.0f && deltaY != 0.0f) {
		_actualX += deltaX;
		_actualY += deltaY;
		// System.out.println(actualX+","+actualY);
		_x = (int)_actualX;
		_y = (int)_actualY;
		correctCamera();
		notifyObservers();
		// }
	}

	/**
	 *
	 */

	public final boolean contains(int testX, int testY)
	{
		return !(testX <= _x || testX >= _x + _width || testY <= _y || testY >= _y + _height);
	}

	public final boolean contains(Quad s)
	{
		return contains(s.getBoundLeft(), s.getBoundRight(), s.getBoundTop(), s.getBoundBottom());
	}

	public final boolean contains(float left, float right, float top, float bottom)
	{
		return !(left <= _x || right >= _x + _width || top <= _y || bottom >= _y + _height);
	}

	public final boolean intersects(Quad s)
	{
		return intersects(s.getBoundLeft(), s.getBoundRight(), s.getBoundTop(), s.getBoundTop());
	}

	public final boolean intersects(float left, float right, float top, float bottom)
	{
		return !(left > _x + _width || right < _x || top < _y || bottom > _y + _height);
	}

	/**
	 * @param observer
	 */
	public void addObserver(ICameraObserver observer)
	{
		// If this observer already exists as a listener, return.
		Iterator<ICameraObserver> iter = _observers.getIterator();
		while (iter.hasNext())
			if (iter.getNext() == observer)
				return;
		// Add unique observer
		_observers.add(observer);
	}

	/**
	 * 
	 */
	private void notifyObservers()
	{
		Iterator<ICameraObserver> iter = _observers.getIterator();
		while (iter.hasNext())
			iter.getNext().cameraChanged(_x, _y, _width, _height);
	}

	/**
	 * 
	 */
	public void clearObservers()
	{
		_observers.clear();
	}

	/**
	 * @param newX
	 * @param newY
	 */
	public void setLocation(int newX, int newY)
	{
		if (_x != newX || _y != newY)
		{
			_actualX = _x = newX;
			_actualY = _y = newY;
			correctCamera();
			notifyObservers();
		}
	}

	/**
	 * @param newWidth
	 * @param newHeight
	 */
	public void setSize(int newWidth, int newHeight)
	{
		if (_width != newWidth && _height != newHeight)
		{
			_width = newWidth;
			_height = newHeight;
			correctCamera();
			notifyObservers();
		}
	}

	public void correctCamera()
	{
		if (_bounds == null)
			return;
		if (_actualX < _bounds.getLeft())
			_actualX = _bounds.getLeft();
		else if (_actualX + _width > _bounds.getRight())
			_actualX = _bounds.getRight() - _width;
		if (_actualY < _bounds.getBottom())
			_actualY = _bounds.getBottom();
		else if (_actualY + _height > _bounds.getTop())
			_actualY = _bounds.getTop() - _height;
		_x = (int)_actualX;
		_y = (int)_actualY;
	}

	/**
	 * @return
	 */
	public int getX()
	{
		return _x;
	}

	/**
	 * @return
	 */
	public int getY()
	{
		return _y;
	}

	/**
	 * @return
	 */
	public int getWidth()
	{
		return _width;
	}

	/**
	 * @return
	 */
	public int getHeight()
	{
		return _height;
	}

	/**
	 * @return
	 */
	public int getTop()
	{
		return _y + _height;
	}

	/**
	 * @return
	 */
	public int getRight()
	{
		return _x + _width;
	}

	public Vector getLocation()
	{
		return new Vector(_x, _y);
	}

	public int getObserverCount()
	{
		return _observers.getSize();
	}

	public BoundingBox getBounds()
	{
		return _bounds;
	}

	public void setBounds(float left, float top, float right, float bottom)
	{
		_bounds = new BoundingBox(left, top, right, bottom);
	}

	public void setBounds(BoundingBox bounds)
	{
		_bounds = bounds;
	}

	/**
	 * 
	 */
	@Override
	public String toString()
	{
		return String.format("X<%s> Y<%s> Width<%s> Height<%s>", _x, _y, _width, _height);
	}

}
