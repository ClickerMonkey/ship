/**************************************************\ 
 *            ______ ___    __   _    _____       * 
 *           / ____//   |  /  | / |  / ___/       *
 *          / /___ / /| | /   |/  | / __/         *
 *         / /_| // __  |/ /|  /| |/ /__          *
 *        /_____//_/  |_|_/ |_/ |_|\___/          *
 *     _____ __   _  ______ ______ __   _  _____  *
 *    / ___//  | / // ____//_  __//  | / // ___/  *
 *   / __/ / | |/ // /___   / /  / | |/ // __/    *
 *  / /__ / /|   // /_| /__/ /_ / /|   // /__     *
 *  \___//_/ |__//_____//_____//_/ |__/ \___/     *
 *                                                *
 * Author: Philip Diffenderfer                    *
 *  Class: engine.sprites.Sprite                  *
 *                                                *
\**************************************************/

package net.philsprojects.game.sprites;

import static net.philsprojects.game.Constants.*;
import static net.philsprojects.game.util.Vector.*;
import net.philsprojects.game.*;
import net.philsprojects.game.util.*;

/**
 * 
 * @author Philip Diffenderfer
 */
public class Sprite extends BasicSprite implements ISocket
{

	// The shade of the sprite.
	protected Color _shade = Color.white();

	// The offset in location of the sprite.
	protected Vector _locationOffset = Vector.zero();

	// How much this sprite resists camera movement.
	protected Vector _resistFactor = Vector.one();

	// The flipping for the texture on the screen.
	protected int _flip = FLIP_NONE;

	// The rotating for the texture on the screen.
	protected int _rotate = ROTATE_NONE;

	// The angle or orientation of the sprite in degrees.
	protected float _angle = 0.0f;

	// If it respects the camera then it moves according to the camera, else its
	// normal screen coordinates.
	protected boolean _respectToCamera = true;


	public Sprite(String name, float x, float y, float width, float height, ISpriteTile tile)
	{
		this(name, x, y, width, height, tile, 0f, true);
	}

	public Sprite(String name, float x, float y, float width, float height, ISpriteTile tile, float angle)
	{
		this(name, x, y, width, height, tile, angle, true);
	}

	public Sprite(String name, float x, float y, float width, float height, ISpriteTile tile, boolean respectToCamera)
	{
		this(name, x, y, width, height, tile, 0f, respectToCamera);
	}

	public Sprite(String name, float x, float y, float width, float height, ISpriteTile tile, float angle, boolean respectToCamera)
	{
		super(name, x, y, width, height, tile);
		_angle = angle;
		_respectToCamera = respectToCamera;
	}

	/**
	 * 
	 * @param s
	 */
	public Sprite(Sprite s)
	{
		this(s._name + "#", s._location.x, s._location.y, s._size.width, s._size.height, s._tile.getClone(), s._angle, s._respectToCamera);
		_resistFactor = s._resistFactor.getClone();
		_locationOffset = s._locationOffset.getClone();
		_shade = s._shade.getClone();
		_visible = s._visible;
		_enabled = s._enabled;
	}

	/**
	 * 
	 * @param deltatime =>
	 */
	@Override
	public void update(float deltatime)
	{
		if (isEnabled())
		{
			getTile().update(deltatime);
			_enabled = getTile().isEnabled();
		}
	}

	@Override
	public void draw()
	{
		if (!isVisible() || getTile() == null)
			return;
		final GraphicsLibrary g = GraphicsLibrary.getInstance();

		// Get the sprite's actual location in the world or on the screen.
		Vector actual;
		if (isRespectToCamera())
			actual = multiply(subtract(getLocation(), Camera.getInstance().getLocation()), getResistFactor());
		else
			actual = getLocation();
		// Setup the sprite, set its textures, set the source rectangle, flip it,
		// rotate it, and draw it.
		g.setupSprite();
		g.setTexture(getTile().getTexture());
		g.setSource(getTile().getSource());
		g.flipSprite(getFlip());
		g.rotateSprite(getRotate());
		g.drawSprite(actual.x, actual.y, getSize().width, getSize().height, getAngle(), -getLocationOffset().x, -getLocationOffset().y, getShade());
		g.clearSource();
	}

	public void setResistFactor(float x, float y)
	{
		_resistFactor.set(x, y);
	}

	public void setLocationOffset(float x, float y)
	{
		_locationOffset.set(x, y);
	}

	public void setShade(float r, float g, float b)
	{
		_shade.setR(r);
		_shade.setG(g);
		_shade.setB(b);
	}

	public void setShade(Color shade)
	{
		_shade = shade;
	}

	public void setAlpha(float a)
	{
		_shade.setA(a);
	}

	public void setAngle(float angle)
	{
		_angle = angle;
	}

	public void setFlip(int flip)
	{
		_flip = flip;
	}

	public void setRotate(int rotate)
	{
		_rotate = rotate;
	}

	public void setRespectToCamera(boolean respectToCamera)
	{
		_respectToCamera = respectToCamera;
	}

	public void toggleFlipX()
	{
		if (_flip == FLIP_NONE)
			_flip = FLIP_X;
		else if (_flip == FLIP_X)
			_flip = FLIP_NONE;
		else if (_flip == FLIP_XY)
			_flip = FLIP_Y;
		else if (_flip == FLIP_Y)
			_flip = FLIP_XY;
	}

	public void toggleFlipY()
	{
		if (_flip == FLIP_NONE)
			_flip = FLIP_Y;
		else if (_flip == FLIP_Y)
			_flip = FLIP_NONE;
		else if (_flip == FLIP_XY)
			_flip = FLIP_X;
		else if (_flip == FLIP_X)
			_flip = FLIP_XY;
	}

	@Override
	public BoundingBox getBounds()
	{
		return new BoundingBox(subtract(getLocation(), getLocationOffset()), getSize());
	}

	public Vector getActualLocation()
	{
		return add(getLocation(), getLocationOffset());
	}

	@Override
	public Vector getLocation()
	{
		return _location;
	}

	public Color getShade()
	{
		return _shade;
	}

	public float getAngle()
	{
		return _angle;
	}

	public int getFlip()
	{
		return _flip;
	}

	public int getRotate()
	{
		return _rotate;
	}

	public float getAlpha()
	{
		return _shade.getA();
	}

	public Vector getLocationOffset()
	{
		return _locationOffset;
	}

	public Vector getResistFactor()
	{
		return _resistFactor;
	}

	public boolean isRespectToCamera()
	{
		return _respectToCamera;
	}

	public Sprite getClone()
	{
		return new Sprite(this);
	}

}
