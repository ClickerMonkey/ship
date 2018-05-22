package net.philsprojects.game.controls;

import net.philsprojects.game.GraphicsLibrary;
import net.philsprojects.game.util.BoundingBox;
import net.philsprojects.game.util.Color;
import net.philsprojects.game.util.Rectangle;

public class SchemeStretchNone implements ControlScheme
{
	//+-+-----+-+
	//|0|  1  |2|
	//+-+-----+-+
	//| |     | |
	//|3|  4  |5|
	//| |     | |
	//+-+-----+-+
	//|6|  7  |8| 
	//+-+-----+-+



	private Rectangle[] _sources = null;
	private Color _shade = null;
	private String _texture = null;
	private boolean _uniqueTexture = false;

	public SchemeStretchNone(SchemeStretchNone base, Color shade)
	{
		_sources = base._sources;
		_shade = shade;
	}

	public SchemeStretchNone(float left, float top, float right, float bottom, float leftSideWidth, float topSideHeight, float rightSideWidth, float bottomSideHeight, Color shade)
	{
		this(left, top, right, bottom, leftSideWidth, topSideHeight, rightSideWidth, bottomSideHeight, true, null, shade);
	}

	public SchemeStretchNone(float left, float top, float right, float bottom, float leftSideWidth, float topSideHeight, float rightSideWidth, float bottomSideHeight, boolean sharesTexture, String texture)
	{
		this(left, top, right, bottom, leftSideWidth, topSideHeight, rightSideWidth, bottomSideHeight, sharesTexture, texture, Color.white());
	}

	public SchemeStretchNone(float left, float top, float right, float bottom, float leftSideWidth, float topSideHeight, float rightSideWidth, float bottomSideHeight, boolean uniqueTexture, String texture, Color shade)
	{
		_sources = new Rectangle[9];
		_sources[0] = new Rectangle(left, top, leftSideWidth, topSideHeight);
		_sources[1] = new Rectangle(left + leftSideWidth, top, (right - left) - (leftSideWidth + rightSideWidth), topSideHeight);
		_sources[2] = new Rectangle(right - rightSideWidth, top, rightSideWidth, topSideHeight);
		_sources[3] = new Rectangle(left, topSideHeight, leftSideWidth, (bottom - top) - (topSideHeight + bottomSideHeight));
		_sources[4] = new Rectangle(left + leftSideWidth, topSideHeight, (right - left) - (leftSideWidth + rightSideWidth), (bottom - top) - (topSideHeight + bottomSideHeight));
		_sources[5] = new Rectangle(right - rightSideWidth, topSideHeight, rightSideWidth, (bottom - top) - (topSideHeight + bottomSideHeight));
		_sources[6] = new Rectangle(left, bottom, leftSideWidth, bottomSideHeight);
		_sources[7] = new Rectangle(left + leftSideWidth, bottom, (right - left) - (leftSideWidth + rightSideWidth), bottomSideHeight);
		_sources[8] = new Rectangle(right - rightSideWidth, bottom, rightSideWidth, bottomSideHeight);
		_uniqueTexture = uniqueTexture;
		_texture = texture;
		_shade = shade;
	}

	public void draw(BoundingBox bounds)
	{
		final GraphicsLibrary g = GraphicsLibrary.getInstance();
		float leftSide = _sources[0].getWidth();
		float rightSide = _sources[2].getWidth();
		float topSide = _sources[0].getHeight();
		float bottomSide = _sources[6].getHeight();
		float innerWidth = bounds.getWidth() - (leftSide + rightSide);
		float innerHeight = bounds.getHeight() - (topSide + bottomSide);
		g.setSource(_sources[0]);
		g.drawSprite(bounds.getLeft(), bounds.getTop() - topSide, leftSide, topSide, _shade);
		g.setSource(_sources[1]);
		g.drawSprite(bounds.getLeft() + leftSide, bounds.getTop() - topSide, innerWidth, topSide, _shade);
		g.setSource(_sources[2]);
		g.drawSprite(bounds.getRight() - rightSide, bounds.getTop() - topSide, rightSide, topSide, _shade);
		g.setSource(_sources[3]);
		g.drawSprite(bounds.getLeft(), bounds.getBottom() - bottomSide, leftSide, innerHeight, _shade);
		g.setSource(_sources[4]);
		g.drawSprite(bounds.getLeft() + leftSide, bounds.getBottom() - bottomSide, innerWidth, innerHeight, _shade);
		g.setSource(_sources[5]);
		g.drawSprite(bounds.getRight() - rightSide, bounds.getBottom() - bottomSide, rightSide, innerHeight, _shade);
		g.setSource(_sources[6]);
		g.drawSprite(bounds.getLeft(), bounds.getBottom(), leftSide, bottomSide, _shade);
		g.setSource(_sources[7]);
		g.drawSprite(bounds.getLeft() + leftSide, bounds.getBottom(), innerWidth, bottomSide, _shade);
		g.setSource(_sources[8]);
		g.drawSprite(bounds.getRight() - rightSide, bounds.getBottom(), rightSide, bottomSide, _shade);
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
