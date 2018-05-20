package net.philsprojects.game.controls;

import net.philsprojects.game.util.BoundingBox;
import net.philsprojects.game.util.Size;

public class Button extends Control
{

	public static Size MINIMUM_SIZE = Size.zero();

	protected ControlScheme _mouseOver = null;
	protected ControlScheme _mouseDown = null;
	protected ControlScheme _normal = null;

	public Button(String name, float x, float y, float width, float height)
	{
		super(name, null, new BoundingBox(x, y + height, x + width, y), MINIMUM_SIZE.width, MINIMUM_SIZE.height);
	}

	public void setMouseOverScheme(ControlScheme scheme)
	{
		_mouseOver = scheme;
	}

	public void setMouseDownScheme(ControlScheme scheme)
	{
		_mouseDown = scheme;
	}

	public void setNormalScheme(ControlScheme scheme)
	{
		_normal = scheme;
		_currentScheme = scheme;
	}

	@Override
	public void onMouseEnter(MouseState state)
	{
		_currentScheme = _mouseOver;
		System.out.println("Mouse Enter");
	}

	@Override
	public void onMouseLeave(MouseState state)
	{
		_currentScheme = _normal;
	}

	@Override
	public void onMouseDown(MouseState state)
	{
		_currentScheme = _mouseDown;
	}

	@Override
	public void onMouseUp(MouseState state)
	{
		_currentScheme = _normal;
	}

}
