package net.philsprojects.game.controls;

import net.philsprojects.game.GraphicsLibrary;
import net.philsprojects.game.util.BoundingBox;
import net.philsprojects.game.util.Color;
import net.philsprojects.game.util.Rectangle;

public class SchemeStretchVertical implements ControlScheme
{

	private Rectangle[] _sources = null;
	private Color _shade = null;
	private String _texture = null;
	private boolean _uniqueTexture = false;

	public SchemeStretchVertical(SchemeStretchVertical base, Color shade)
	{
		_sources = base._sources;
		_shade = shade;
	}

	public SchemeStretchVertical(float left, float top, float right, float bottom, float leftSideWidth, float rightSideWidth)
	{
		this(left, top, right, bottom, leftSideWidth, rightSideWidth, true, null);
	}

	/**
	 * 
	 *          Top<br>
	 *       _ ______ _<br>
	 *      | |      | |<br>
	 * Left | |      | | Right<br>
	 *      |_|______|_|<br>
	 *       ^ Bottom ^<br>
	 *       |        |<br>
	 * leftSideWidth  rightSideWidth<br>
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @param leftSideWidth
	 * @param rightSideWidth
	 */
	public SchemeStretchVertical(float left, float top, float right, float bottom, float leftSideWidth, float rightSideWidth, boolean uniqueTexture, String texture)
	{
		_sources = new Rectangle[3];
		_sources[0] = new Rectangle(left, top, leftSideWidth, bottom - top);
		_sources[1] = new Rectangle(left + leftSideWidth, top, (right - left) - (leftSideWidth + rightSideWidth), bottom - top);
		_sources[2] = new Rectangle(right - rightSideWidth, top, rightSideWidth, bottom - top);
		_uniqueTexture = uniqueTexture;
		_texture = texture;
	}

	public void draw(BoundingBox bounds)    
	{
		final GraphicsLibrary g = GraphicsLibrary.getInstance();
		g.setSource(_sources[0]);
		g.drawSprite(bounds.getLeft(), bounds.getBottom(), _sources[0].getWidth(), bounds.getHeight(), _shade);
		g.setSource(_sources[1]);
		g.drawSprite(bounds.getLeft() + _sources[0].getWidth() , bounds.getBottom(), bounds.getWidth() - (_sources[0].getWidth() + _sources[2].getWidth()), bounds.getHeight(), _shade);
		g.setSource(_sources[2]);
		g.drawSprite(bounds.getRight() - _sources[2].getWidth(), bounds.getBottom(), _sources[2].getWidth(), bounds.getHeight(), _shade);
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
