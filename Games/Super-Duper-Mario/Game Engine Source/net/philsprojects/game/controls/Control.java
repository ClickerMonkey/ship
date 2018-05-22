package net.philsprojects.game.controls;

import net.philsprojects.game.*;
import net.philsprojects.game.util.*;

public abstract class Control implements IName, IDraw, IUpdate
{


	private String _name = null;
	private Control _parent = null;
	private Size _minSize = Size.zero();
	private Control _focused = null;
	private NameLinkedList<Control> _children = null;
	private BoundingBox _bounds = null;
	private MouseState _mouseState = new MouseState(Vector.zero(), Vector.zero(), false, false, false, false);
	protected ControlScheme _currentScheme = null;

	protected boolean _enabled = true;
	protected boolean _visible = true;
	protected boolean _canHaveFocus = true;
	protected boolean _isFocused = false;
	protected boolean _acceptsKey = true;
	protected boolean _acceptsMouse = true;
	protected boolean _isMouseOver = false;
	protected boolean _anchorTop = true;
	protected boolean _anchorBottom = false;
	protected boolean _anchorLeft = true;
	protected boolean _anchorRight = false;


	/**
	 * Initializes a control with its name, its parent, its bounds, and its minimum size.
	 * 
	 * @param name
	 * @param parent
	 * @param bounds
	 */
	public Control(String name, Control parent, BoundingBox bounds, float minWidth, float minHeight)
	{
		_name = name;
		_parent = parent;
		_bounds = bounds;
		_minSize.set(minWidth, minHeight);
	}

	/**
	 * Updates this control's current scheme. If it has children it then updates all of 
	 * the children on top then the focused control.
	 * 
	 * @param deltatime => The time in seconds since the last update.
	 */
	public void update(float deltatime)
	{
		if (!_enabled)
			return;
		// Update the current scheme
		if (_currentScheme != null)
			_currentScheme.update(deltatime);
		// Update all children
		if (_children != null)
		{
			Iterator<Control> i = _children.getIterator();
			Control current;	  
			while (i.hasNext())
			{
				current = i.getNext();
				if (_focused == null || _focused != current)
					current.update(deltatime);
			}
			if (_focused != null)
				_focused.update(deltatime);
		}
	}

	/**
	 * Draws this control based on its current scheme. If it has children then it draws
	 * all of the children on top then the focused control
	 */
	public void draw()
	{
		if (!_visible)
			return;
		// Draw the current scheme
		if (_currentScheme != null)
		{
			if (!_currentScheme.hasUniqueTexture())
			{
				_currentScheme.draw(_bounds);
			}
			else
			{
				final GraphicsLibrary g = GraphicsLibrary.getInstance();
				String current = g.getTexture();
				g.setTexture(_currentScheme.getUniqueTexture());
				_currentScheme.draw(_bounds);
				g.setTexture(current);
			}
		}
		// Draw all children
		if (_children != null)
		{
			Iterator<Control> i = _children.getIterator();
			Control current;
			while (i.hasNext())
			{
				current = i.getNext();
				if (_focused == null || _focused != current)
					current.draw();
			}
			if (_focused != null)
				_focused.draw();
		}
	}   

	/**
	 * Resizes this control and all child controls according to this parent controls
	 * old size and its new size.
	 * 
	 * @param oldSize => The old size of the parent control.
	 * @param newSize => The new size of the parent control.
	 */
	public final void resize(Size oldSize, Size newSize)
	{
		// The difference in size
		float widthDif = newSize.width - oldSize.width;
		float heightDif = newSize.height - oldSize.height;
		// If no change in size has occurred return.
		if (widthDif == 0 && heightDif == 0)
			return;
		// The old size of this control
		Size old = _bounds.getSize();

		// If it's attached to the right side stretch the right
		if (_anchorRight)
			_bounds.setRight(_bounds.getRight() + widthDif);
		// If it's not attached to the left side then shift it
		if (!_anchorLeft)
			_bounds.setLeft(_bounds.getLeft() + widthDif);
		// If it's attached to the bottom side stretch the bottom
		if (_anchorBottom)
			_bounds.setBottom(_bounds.getBottom() + heightDif);
		// If it's not attached to the top side then shift it
		if (!_anchorTop)
			_bounds.setTop(_bounds.getTop() + heightDif);
		// If it's not attached to the right or left center its shifting.
		if (!_anchorRight && !_anchorLeft)
		{
			_bounds.setRight(_bounds.getRight() + widthDif / 2f);
			_bounds.setLeft(_bounds.getLeft() + widthDif / 2f);
		}
		// If it's not attached to the top or bottom center its shifting.
		if (!_anchorBottom && !_anchorTop)
		{
			_bounds.setBottom(_bounds.getBottom() + heightDif / 2f);
			_bounds.setTop(_bounds.getTop() + heightDif / 2f);
		}

		// Resize all children accordingly
		if (_children != null)
		{

			// The current size of this control
			Size current = _bounds.getSize();
			// Resize all the children with this controls old and new size.
			Iterator<Control> i = _children.getIterator();
			while (i.hasNext())
				i.getNext().resize(old, current);
		}
	}

	/**
	 * Relocates this control and all child controls according to this parent controls
	 * old location and its new location.
	 * 
	 * @param oldLocation => The old location of the parent control.
	 * @param newLocation => The new location of the parent control.
	 */
	public final void relocate(Vector oldLocation, Vector newLocation)
	{
		//The difference in location
		float xDif = newLocation.x - oldLocation.x;
		float yDif = newLocation.y - oldLocation.y;
		// If no change in location has occurred then return.
		if (xDif == 0 && yDif == 0)
			return;
		// The old location of this control
		Vector old = _bounds.getLocation();
		// Translate the bounds according to the size
		_bounds.translate(xDif, yDif);
		// Relocate all children accordingly
		if (_children != null)
		{
			// The new location of this control
			Vector current = _bounds.getLocation();
			// Relocate all children with this controls old and new location
			Iterator<Control> i = _children.getIterator();
			while (i.hasNext())
				i.getNext().relocate(old, current);
		}	
	}

	/**
	 * Used to update the status and trigger mouse events for this control.
	 * 
	 * @param x => The x-coordinate of the mouse.
	 * @param y => The y-coordinate of the mouse.
	 * @param leftDown => True if the left button on the mouse is down, false if it is not.
	 * @param rightDown => True if the right button on the mouse is down, false if it is not.
	 */
	public final boolean mouse(float x, float y, boolean leftDown, boolean rightDown)
	{
		boolean isInside = _bounds.contains(x, y);
		if (isInside && _parent != null)
			System.out.println("Inside!");
		// If the mouse is not, and was not over this control return false.
		if (!isInside && !_isMouseOver)
			return false;

		// Go through each child
		boolean onThisControl = true;
		if (_children != null)
		{
			Iterator<Control> i = _children.getIterator();
			while (i.hasNext() && onThisControl)
			{
				if (i.getNext().mouse(x, y, leftDown, rightDown))
					onThisControl = false;
			}
		}
		// If the mouse is on a child control and not directly on this one then return false
		if (!onThisControl)
			return false;
		// If it is on this control and not on a child control then...
		// If the location of the mouse has not changed... (PRESSES, HOVER)
		if (_mouseState.getCurrentPosition().x == x && _mouseState.getCurrentPosition().y == y)
		{
			// If the button states are the same and the location is the same its a mouse hover.
			if (leftDown == _mouseState.getCurrentLeftDown() && rightDown == _mouseState.getCurrentRightDown())
			{
				onMouseHover(_mouseState);
				return true;
			}

			//If the left button is pressed and was not previously pressed.. MOUSE_DOWN
			if (leftDown && !_mouseState.getCurrentLeftDown())
			{
				_mouseState.setCurrentLeftDown(true);
				onMouseDown(_mouseState);
			}
			//If the left button is not pressed but was previously pressed... MOUSE_UP
			else if (!leftDown && _mouseState.getCurrentLeftDown())
			{
				_mouseState.setCurrentLeftDown(false);
				onMouseUp(_mouseState);
			}

			//If the right button is pressed and was not previously pressed... MOUSE_DOWN
			if (rightDown && !_mouseState.getCurrentRightDown())
			{
				_mouseState.setCurrentRightDown(true);
				onMouseDown(_mouseState);
			}
			//If the right button is not pressed but was previously pressed... MOUSE_UP
			else if (!rightDown && _mouseState.getCurrentRightDown())
			{
				_mouseState.setCurrentRightDown(false);
				onMouseUp(_mouseState);
			}
		}
		// If the location of the mouse has changed... (MOVE, DRAG, ENTER, LEAVE)
		else
		{
			_mouseState.setCurrentPosition(x, y);
			// If the mouse is no longer over the control but was...
			if (!isInside && _isMouseOver)
			{
				_isMouseOver = false;
				onMouseLeave(_mouseState);
				return true;
			}
			// If the mouse is now inside and previously wasn't...
			else if (isInside && !_isMouseOver)
			{
				_isMouseOver = true;
				onMouseEnter(_mouseState);
				return true;
			}
			// If the mouse is inside and was previously inside...
			else
			{
				// If the button states have not changed...
				if (leftDown == _mouseState.getCurrentLeftDown() && rightDown == _mouseState.getCurrentRightDown())
				{
					// If either of the buttons is down...
					if (leftDown || rightDown)
					{
						onMouseDrag(_mouseState);
					}
					// If either of the buttons aren't down...
					else if (!leftDown && !rightDown)
					{
						onMouseMove(_mouseState);
					}
					return true;
				}
				// If a button state has changed and the location has changed.

				//If the left button is pressed and was not previously pressed.. MOUSE_DOWN
				if (leftDown && !_mouseState.getCurrentLeftDown())
				{
					_mouseState.setCurrentLeftDown(true);
					onMouseDown(_mouseState);
				}
				//If the left button is not pressed but was previously pressed... MOUSE_UP
				else if (!leftDown && _mouseState.getCurrentLeftDown())
				{
					_mouseState.setCurrentLeftDown(false);
					onMouseUp(_mouseState);
				}

				//If the right button is pressed and was not previously pressed... MOUSE_DOWN
				if (rightDown && !_mouseState.getCurrentRightDown())
				{
					_mouseState.setCurrentRightDown(true);
					onMouseDown(_mouseState);
				}
				//If the right button is not pressed but was previously pressed... MOUSE_UP
				else if (!rightDown && _mouseState.getCurrentRightDown())
				{
					_mouseState.setCurrentRightDown(false);
					onMouseUp(_mouseState);
				}
			}
		}
		return true;
	}


	public final boolean key()
	{
		if (_focused != null)
		{
			if (_focused.acceptsKey() && _focused.key())
				return true;
		}

		return true;
	}

	/**
	 * Lets the parent know that his control is now its focused control.
	 */
	public void requestFocus()
	{
		_isFocused = true;
		if (_parent != null)
		{
			if (_parent._focused != null)
			{
				_parent._focused._isFocused = false;
			}
			_parent._focused = this;
			_parent.requestFocus();
		}
	}

	/**
	 * Adds a child control to this control and sets it parent to this control.
	 * 
	 * @param child => The child control to add.
	 */
	public void addChild(Control child)
	{
		if (_children == null)
			_children = new NameLinkedList<Control>();
		child._parent = this;
		_children.add(child);
	}

	/**
	 * Removes a child control by its name.
	 * 
	 * @param name => The name of the child control to remove.
	 * @return The reference to the child control removed if any, otherwise null.
	 */
	public Control removeChild(String name)
	{
		return _children.remove(name);
	}

	/**
	 * Gets a child control by it name.
	 * 
	 * @param name => The name of the child control to return.
	 * @return The reference to the child control found if any, otherwise null.
	 */
	public Control getChild(String name)
	{
		return _children.get(name);
	}


	/**
	 * Adds a listener to this mouse event invoker. The listener is notified every time 
	 * a mouse event occurs on this control
	 * 
	 * @param listener => The mouse listener to add.
	 */
	public void addMouseListener(EventListener listener)
	{
		//_eventMouse.addListener(listener);
	}

	/**
	 * Removes a listener from the mouse event invoker.
	 * 
	 * @param listener => The listener to remove.
	 */
	public void removeMouseListener(EventListener listener)
	{
		//_eventMouse.removeListener(listener);
	}



	/**
	 * Sets the size of this control and resizes its child controls accordingly.
	 * 
	 * @param width => The new width in pixels of this control.
	 * @param height => The new height in pixels of this control.
	 */
	public void setSize(float width, float height)
	{
		resize(_bounds.getSize(), new Size(width, height));
	}

	/**
	 * Sets the location of this control and relocates its child controls accordingly.
	 * 
	 * @param x => The new x-coordinate in pixels of this control.
	 * @param y => The new y-coordinate in pixels of this control.
	 */
	public void setLocation(float x, float y)
	{
		relocate(_bounds.getLocation(), new Vector(x, y));
	}

	/**
	 * Sets the x-coordinate of this control and relocates its child controls accordingly.
	 * 
	 * @param x => The new x-coordinate in pixels of this control.
	 */
	public void setX(float x)
	{
		setLocation(x, _bounds.getBottom());
	}

	/**
	 * Sets the y-coordinate of this control and relocates its child controls accordingly.
	 * 
	 * @param y => The new y-coordinate in pixels of this control.
	 */
	public void setY(float y)
	{
		setLocation(_bounds.getLeft(), y);
	}

	/**
	 * Sets the width of this control and resizes its child controls accordingly.
	 * 
	 * @param width => The new width in pixels of this control.
	 */
	public void setWidth(float width)
	{
		setSize(width, _bounds.getHeight());
	}

	/**
	 * Sets the height of this control and resizes its child controls accordingly.
	 * 
	 * @param height => The new height in pixels of this control.
	 */
	public void setHeight(float height)
	{
		setSize(_bounds.getWidth(), height);
	}

	/**
	 * 
	 * @param scheme
	 */
	public void setCurrentScheme(ControlScheme scheme)
	{
		_currentScheme = scheme;
	}

	/**
	 * Sets the visibility of this control and all child controls.
	 * 
	 * @param visible => True if this control should be seen, false if not.
	 */
	public void setVisible(boolean visible)
	{
		_visible = visible;
	}



	public Control getParent()
	{
		return _parent;
	}

	public NameLinkedList<Control> getChildren()
	{
		return _children;
	}

	public BoundingBox getBounds()
	{
		return _bounds;
	}

	public String getName()
	{
		return _name;
	}

	public Control getFocusedControl()
	{
		return _focused;
	}

	public ControlScheme getCurrentScheme()
	{
		return _currentScheme;
	}

	public Vector getMouseLocation()
	{
		return _mouseState.getCurrentPosition();
	}

	public Size getMinimumSize()
	{
		return _minSize;
	}

	public MouseState getMouseState()
	{
		return _mouseState;
	}

	public boolean isEnabled()
	{
		return _enabled;
	}

	public boolean isVisible()
	{
		return _visible;
	}

	public boolean acceptsKey()
	{
		return _acceptsKey;
	}

	public boolean acceptsMouse()
	{
		return _acceptsMouse;
	}

	public boolean anchorTop()
	{
		return _anchorTop;
	}

	public boolean anchorLeft()
	{
		return _anchorLeft;
	}

	public boolean anchorBottom()
	{
		return _anchorBottom;
	}

	public boolean canHaveFocus()
	{
		return _canHaveFocus;
	}

	public boolean isMouseOver()
	{
		return _isMouseOver;
	}

	public void onMouseEnter(MouseState state)
	{
	}

	public void onMouseLeave(MouseState state)
	{
	}

	public void onMouseDown(MouseState state)
	{
	}

	public void onMouseUp(MouseState state)
	{
	}

	public void onMouseHover(MouseState state)
	{
	}

	public void onMouseDrag(MouseState state)
	{
	}

	public void onMouseMove(MouseState state)
	{
	}

}
