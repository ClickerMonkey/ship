package net.philsprojects.game.controls;

import net.philsprojects.game.GraphicsLibrary;
import net.philsprojects.game.util.BoundingBox;
import net.philsprojects.game.util.Color;
import net.philsprojects.game.util.Rectangle;

public class SchemeStretchHorizontal implements ControlScheme
{

	private Rectangle[] _sources = null;
	private Color _shade = null;
	private String _texture = null;
	private boolean _uniqueTexture = false;

	public SchemeStretchHorizontal(SchemeStretchHorizontal base, Color shade)
	{
		_sources = base._sources;
		_shade = shade;
	}

	public SchemeStretchHorizontal(float left, float top, float right, float bottom, float topSideHeight, float bottomSideHeight, Color shade)
	{
		this(left, top, right, bottom, topSideHeight, bottomSideHeight, true, null, shade);
	}

	public SchemeStretchHorizontal(float left, float top, float right, float bottom, float topSideHeight, float bottomSideHeight, boolean sharesTexture, String texture)
	{
		this(left, top, right, bottom, topSideHeight, bottomSideHeight, sharesTexture, texture, Color.white());
	}

	public SchemeStretchHorizontal(float left, float top, float right, float bottom, float topSideHeight, float bottomSideHeight, boolean uniqueTexture, String texture, Color shade)
	{
		_sources = new Rectangle[3];
		_sources[0] = new Rectangle(left, top, right - left, topSideHeight);
		_sources[1] = new Rectangle(left, top + topSideHeight, right - left, (bottom - top) - (topSideHeight + bottomSideHeight));
		_sources[2] = new Rectangle(left, bottom - bottomSideHeight, right - left, bottomSideHeight);
		_uniqueTexture = uniqueTexture;
		_texture = texture;
		_shade = shade;
	}

	public void draw(BoundingBox bounds)
	{
		final GraphicsLibrary g = GraphicsLibrary.getInstance();
		g.setSource(_sources[0]);
		g.drawSprite(bounds.getLeft(), bounds.getTop() - _sources[0].getHeight(), bounds.getWidth(), _sources[0].getHeight(), _shade);
		g.setSource(_sources[1]);
		g.drawSprite(bounds.getLeft() , bounds.getBottom() - _sources[2].getHeight(), bounds.getWidth(), bounds.getHeight() - (_sources[0].getHeight() + _sources[2].getHeight()), _shade);
		g.setSource(_sources[2]);
		g.drawSprite(bounds.getLeft(), bounds.getBottom(), bounds.getWidth(), _sources[3].getHeight(), _shade);
	}

	public void update(float deltatime)
	{

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
