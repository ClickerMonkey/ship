package net.philsprojects.game.controls;

import net.philsprojects.game.GraphicsLibrary;
import net.philsprojects.game.ISpriteTile;
import net.philsprojects.game.util.BoundingBox;
import net.philsprojects.game.util.Color;
import net.philsprojects.game.util.Rectangle;

public class SchemeStretch implements ControlScheme
{

	private ISpriteTile _tile = null;
	private Rectangle[] _sources = null;
	private Color _shade = null;
	private String _texture = null;
	private boolean _uniqueTexture = false;

	public SchemeStretch(SchemeStretch base, Color shade)
	{
		_sources = base._sources;
		_shade = shade;
	}

	public SchemeStretch(ISpriteTile tile)
	{
		this(tile, false, null, Color.white());
	}

	public SchemeStretch(ISpriteTile tile, Color shade)
	{
		this(tile, false, null, shade);
	}

	public SchemeStretch(ISpriteTile tile, boolean uniqueTexture, String texture, Color shade)
	{
		_tile = tile;
		_sources = new Rectangle[1];
		_sources[0] = _tile.getSource();
		_uniqueTexture = uniqueTexture;
		_texture = texture;
		_shade = shade;
	}

	public void draw(BoundingBox bounds)
	{
		final GraphicsLibrary g = GraphicsLibrary.getInstance();
		g.setSource(_sources[0]);
		g.drawSprite(bounds.getLeft(), bounds.getBottom(), bounds.getWidth(), bounds.getHeight(), _shade);
	}

	public void update(float deltatime)
	{
		_sources[0] = _tile.getSource();
		_tile.update(deltatime);
	}

	public ISpriteTile getTile()
	{
		return _tile;
	}

	public void setTile(ISpriteTile tile)
	{
		_tile = tile;
	}

	public String getUniqueTexture()
	{
		return _texture;
	}

	public boolean hasUniqueTexture()
	{
		return _uniqueTexture;
	}

}
