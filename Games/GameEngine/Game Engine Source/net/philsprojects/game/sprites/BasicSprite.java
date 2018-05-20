package net.philsprojects.game.sprites;

import net.philsprojects.game.*;
import net.philsprojects.game.util.*;

public class BasicSprite implements IName, IUpdate, IDraw, ITexture
{

	// The name of this sprite.
	protected String _name = null;

	// The size of this sprite in pixels
	protected Size _size = Size.zero();

	// Location of the sprite in the world.
	protected Vector _location = Vector.zero();

	// The tile or animation.
	protected ISpriteTile _tile = SpriteTileStatic.zero();

	// Whether this sprite updates the tile.
	protected boolean _enabled = true;

	// Whether this sprite is drawn.
	protected boolean _visible = true;


	public BasicSprite(String name, float x, float y, float width, float height, ISpriteTile tile)
	{
		_name = name;
		_location.set(x, y);
		_size.set(width, height);
		_tile = tile;
	}

	/**
	 * 
	 * @param deltatime =>
	 */
	public void update(float deltatime)
	{
		if (_enabled)
		{
			_tile.update(deltatime);
			_enabled = _tile.isEnabled();
		}
	}

	public void draw()
	{
		if (!_visible)
			return;
		final GraphicsLibrary g = GraphicsLibrary.getInstance();
		// Setup the sprite, set its textures, set the source rectangle, flip it,
		// rotate it, and draw it.
		g.setupSprite();
		g.setTexture(_tile.getTexture());
		g.setSource(_tile.getSource());
		g.drawSprite(_location.x, _location.y, _size.width, _size.height);
		g.clearSource();
	}

	public void setVisible(boolean visible)
	{
		_visible = visible;
	}

	public void setX(float x)
	{
		_location.x = x;
	}

	public void setY(float y)
	{
		_location.y = y;
	}

	public void setLocation(float x, float y)
	{
		_location.set(x, y);
	}

	public void setLocation(Vector location)
	{
		_location.set(location);
	}

	public void setWidth(float width)
	{
		_size.width = width;
	}

	public void setHeight(float height)
	{
		_size.height = height;
	}

	public void setSize(float width, float height)
	{
		_size.set(width, height);
	}

	public void setSize(Size size)
	{
		_size.set(size);
	}

	public void setTexture(String texture)
	{
		_tile.setTexture(texture);
	}

	public void setTile(ISpriteTile tile)
	{
		_tile = tile;
	}

	public BoundingBox getBounds()
	{
		return new BoundingBox(_location, _size);
	}

	public String getName()
	{
		return _name;
	}

	public String getTexture()
	{
		return _tile.getTexture();
	}

	public float getX()
	{
		return _location.x;
	}

	public float getY()
	{
		return _location.y;
	}

	public Vector getLocation()
	{
		return _location;
	}

	public float getWidth()
	{
		return _size.width;
	}

	public float getHeight()
	{
		return _size.height;
	}

	public Size getSize()
	{
		return _size;
	}

	public ISpriteTile getTile()
	{
		return _tile;
	}

	public boolean isEnabled()
	{
		return _enabled;
	}

	public boolean isVisible()
	{
		return _visible;
	}

	public BasicSprite getClone()
	{
		return new BasicSprite(_name + "#", _location.x, _location.y, _size.width, _size.height, _tile.getClone());
	}

}
