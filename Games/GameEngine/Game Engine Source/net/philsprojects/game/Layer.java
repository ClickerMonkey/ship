package net.philsprojects.game;

import net.philsprojects.game.util.Iterator;
import net.philsprojects.game.util.LinkedList;

/**
 * A Layer of drawable and updatable objects.
 * 
 * @author Philip Diffenderfer
 */
public class Layer implements IDraw, IUpdate, IName, IClone<Layer>
{

	// Name of this Layer
	private String _name;
	// Visibility of the objects that can be drawn
	private boolean _visible = true;
	// If its enabled, all objects that can be updated, will be updated
	private boolean _enabled = true;

	private LinkedList<IDraw> _drawables = null;

	private LinkedList<IUpdate> _updatables = null;

	private Iterator<IDraw> _drawablesIterator;

	private Iterator<IUpdate> _updatablesIterator;


	/**
	 * Initializes this Layers entity list.
	 */
	public Layer(String name)
	{
		_name = name;
		_drawables = new LinkedList<IDraw>();
		_drawablesIterator = _drawables.getIterator();
		_updatables = new LinkedList<IUpdate>();
		_updatablesIterator = _updatables.getIterator();
	}

	/**
	 * Updates all the objects that can be updated in this layer.
	 * 
	 * @param deltatime => The time in seconds since the last update call.
	 */
	public void update(float deltatime)
	{
		if (!_enabled)
			return;

		_updatablesIterator.reset();
		while (_updatablesIterator.hasNext())
		{
			_updatablesIterator.getNext().update(deltatime);
		}
	}

	/**
	 * Draws all the objects that can be updated in this layer.
	 */
	public void draw()
	{
		if (!_visible)
			return;

		_drawablesIterator.reset();
		while (_drawablesIterator.hasNext())
		{
			_drawablesIterator.getNext().draw();
		}
	}

	/**
	 * Adds a drawable object to this layer.
	 * 
	 * @param drawable => The drawable object to add.
	 */
	public void add(IDraw drawable)
	{
		_drawables.add(drawable);
	}

	/**
	 * Adds an updatable object to this Layer.
	 * 
	 * @param updatable => The updatable object to add.
	 */
	public void add(IUpdate updatable)
	{
		_updatables.add(updatable);
	}

	/**
	 * Removes a drawable object from this layer.
	 * 
	 * @param drawable => The drawable object to remove.
	 * @return True if the drawable was removed, false if not.
	 */
	public boolean remove(IDraw drawable)
	{
		return (_drawables.remove(drawable) != null);
	}

	/**
	 * Removes a updatable object from this layer.
	 * 
	 * @param updatable => The updatable object to remove.
	 * @return True if the updatable was removed, false if not.
	 */
	public boolean remove(IUpdate updatable)
	{
		return (_updatables.remove(updatable) != null);
	}

	/**
	 * Gets the visibility of this layer.
	 */
	public boolean isVisible()
	{
		return _visible;
	}

	/**
	 * Sets the visibility of this layer.
	 *
	 * @param visible => If true, this layer will be visible on screen, if not it won't be.
	 */
	public void setVisible(boolean visible)
	{
		_visible = visible;
	}

	/**
	 * Returns whether this layer is enabled, the objects on this layer get an update or not.
	 */
	public boolean isEnabled()
	{
		return _enabled;
	}

	/**
	 * Returns the name of this Layer.
	 */
	public String getName()
	{
		return _name;
	}

	/**
	 * Gets a clone of this layer and all of ots objects.
	 */
	public Layer getClone()
	{
		return null;
	}


}
