/************************************************\ 
 *               [BoundingBox]                  * 
 *            ______ ___    __   _    _____     * 
 *           / ____//   |  /  | / |  / ___/     *
 *          / /___ / /| | /   |/  | / __/       *
 *         / /_| // __  |/ /|  /| |/ /__        *
 *        /_____//_/  |_|_/ |_/ |_|\___/        *
 *    _____ __   _  ______ ______ __   _  _____ *
 *   / ___//  | / // ____//_  __//  | / // ___/ *
 *  / __/ / | |/ // /___   / /  / | |/ // __/   *
 * / /__ / /|   // /_| /__/ /_ / /|   // /__    *
 * \___//_/ |__//_____//_____//_/ |__/ \___/    *
 *                                              *
 \************************************************/

package net.philsprojects.game.util;

import static net.philsprojects.game.util.Math.max;
import static net.philsprojects.game.util.Math.min;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.philsprojects.game.Camera;
import net.philsprojects.game.IBinary;
import net.philsprojects.game.IClone;
import net.philsprojects.game.sprites.Quad;


public class BoundingBox implements IBinary, IClone<BoundingBox>
{

	// Left side offset in pixels of the bounding box from the y-axis, along the
	// x-axis.
	protected float _left = 0f;

	// Right side offset in pixels of the bounding box from the y-axis, along the
	// x-axis.
	protected float _right = 0f;

	// Top side offset in pixels of the bounding box from the x-axis, along the
	// y-axis.
	protected float _top = 0f;

	// Bottom side offset in pixels of the bounding box from the x-axis, along
	// the y-axis.
	protected float _bottom = 0f;

	/**
	 * 
	 */
	public BoundingBox()
	{
	}

	/**
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public BoundingBox(float left, float top, float right, float bottom)
	{
		_left = left;
		_top = top;
		_right = right;
		_bottom = bottom;
	}

	/**
	 * 
	 * @param location
	 * @param size
	 */
	public BoundingBox(Vector location, Size size)
	{
		_left = location.x;
		_top = location.y + size.height;
		_right = location.x + size.width;
		_bottom = location.y;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 */
	public final void clear(float x, float y)
	{
		_left = _right = x;
		_top = _bottom = y;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 */
	public final void include(float x, float y)
	{
		if (x < _left)
			_left = x;
		if (x > _right)
			_right = x;
		if (y > _top)
			_top = y;
		if (y < _bottom)
			_bottom = y;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param size
	 */
	public final void include(float x, float y, float size)
	{
		if (x < _left)
			_left = x;
		if (x + size > _right)
			_right = x + size;
		if (y + size > _top)
			_top = y + size;
		if (y < _bottom)
			_bottom = y;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public final void include(float x, float y, float width, float height)
	{
		if (x < _left)
			_left = x;
		if (x + width > _right)
			_right = x + width;
		if (y + height > _top)
			_top = y + height;
		if (y < _bottom)
			_bottom = y;
	}

	/**
	 * 
	 * @param box
	 */
	public final void include(BoundingBox box)
	{
		if (box._left < _left)
			_left = box._left;
		if (box._right > _right)
			_right = box._right;
		if (box._top > _top)
			_top = box._top;
		if (box._bottom < _bottom)
			_bottom = box._bottom;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 */
	public final boolean contains(float x, float y)
	{
		return !(x <= _left || x >= _right || y >= _top || y <= _bottom);
	}

	/**
	 * 
	 * @param s
	 */
	public final boolean contains(Quad s)
	{
		return contains(s.getBoundLeft(), s.getBoundRight(), s.getBoundTop(), s.getBoundBottom());
	}

	/**
	 * 
	 * @param left
	 * @param right
	 * @param top
	 * @param bottom
	 */
	public final boolean contains(float left, float right, float top, float bottom)
	{
		return !(left <= _left || right >= _right || top <= _top || bottom >= _bottom);
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public final boolean intersects(Quad s)
	{
		return intersects(s.getBoundLeft(), s.getBoundRight(), s.getBoundTop(), s.getBoundTop());
	}

	/**
	 * 
	 * @param box
	 * @return
	 */
	public final boolean intersects(BoundingBox box)
	{
		return !(box._left > _right || box._right < _left || box._top < _bottom || box._bottom > _top);
	}

	/**
	 * 
	 * @param left
	 * @param right
	 * @param top
	 * @param bottom
	 * @return
	 */
	public final boolean intersects(float left, float right, float top, float bottom)
	{
		return !(left > _right || right < _left || top < _bottom || bottom > _top);
	}

	/**
	 * 
	 * @param box
	 * @return
	 */
	public final BoundingBox intersection(BoundingBox box)
	{
		return intersection(box.getLeft(), box.getRight(), box.getTop(), box.getBottom());
	}

	/**
	 * 
	 * @param left
	 * @param right
	 * @param top
	 * @param bottom
	 * @return
	 */
	public final BoundingBox intersection(float left, float right, float top, float bottom)
	{
		if (intersects(left, right, top, bottom)) {
			return new BoundingBox(max(_left, left), min(_top, top), min(_right, right), max(_bottom, bottom));
		}
		return BoundingBox.zero();
	}

	/**
	 * 
	 */
	public final boolean isOnScreen()
	{
		return Camera.getInstance().intersects((int)_left, (int)_right, (int)_top, (int)_bottom);
	}

	/**
	 * 
	 */
	public final BoundingBox getActualBox()
	{
		final Camera c = Camera.getInstance();
		return new BoundingBox(_left - c.getX(), _top - c.getY(), _right - c.getX(), _bottom - c.getY());
	}

	/**
	 * Returns true if left, right, top, and bottom all are 0, otherwise false.
	 */
	public final boolean isEmpty()
	{
		return !(_left != 0f || _top != 0f || _right != 0f || _bottom != 0f);
	}

	/**
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public final void set(float left, float top, float right, float bottom)
	{
		_left = left;
		_top = top;
		_right = right;
		_bottom = bottom;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 */
	public final void translate(float x, float y)
	{
		_left += x;
		_right += x;
		_top += y;
		_bottom += y;
	}

	/**
	 * 
	 * @param v
	 */
	public final void translate(Vector v)
	{
		translate(v.x, v.y);
	}

	/**
	 * 
	 * @param left
	 */
	public void setLeft(float left)
	{
		_left = left;
	}

	/**
	 * 
	 * @param top
	 */
	public void setTop(float top)
	{
		_top = top;
	}

	/**
	 * 
	 * @param right
	 */
	public void setRight(float right)
	{
		_right = right;
	}

	/**
	 * 
	 * @param bottom
	 */
	public void setBottom(float bottom)
	{
		_bottom = bottom;
	}

	/**
	 * 
	 * @return
	 */
	public float getLeft()
	{
		return _left;
	}

	/**
	 * 
	 * @return
	 */
	public float getTop()
	{
		return _top;
	}

	/**
	 * 
	 * @return
	 */
	public float getRight()
	{
		return _right;
	}

	/**
	 * 
	 * @return
	 */
	public float getBottom()
	{
		return _bottom;
	}

	/**
	 * 
	 * @return
	 */
	public float getWidth()
	{
		return _right - _left;
	}

	/**
	 * 
	 * @return
	 */
	public float getHeight()
	{
		return _top - _bottom;
	}

	/**
	 * 
	 * @return
	 */
	public float getCenterX()
	{
		return (_right + _left) / 2f;
	}

	/**
	 * 
	 * @return
	 */
	public float getCenterY()
	{
		return (_top + _bottom) / 2f;
	}

	public Size getSize()
	{
		return new Size(_right - _left, _bottom - _top);
	}

	public Vector getLocation()
	{
		return new Vector(_left, _bottom);
	}

	/**
	 * 
	 * @return
	 */
	public Vector getCenter()
	{
		return new Vector((_right + _left) / 2f, (_top + _bottom) / 2f);
	}

	public Rectangle toRectangle()
	{
		return new Rectangle(_left, _bottom, _right - _left, _top - _bottom);
	}

	@Override
	public String toString()
	{
		return String.format("{Left<%s> Top<%s> Right<%s> Bottom<%s>}", _left, _top, _right, _bottom);
	}


	public BoundingBox getClone()
	{
		return new BoundingBox(_left, _top, _right, _bottom);
	}

	public static BoundingBox zero()
	{
		return new BoundingBox(0f, 0f, 0f, 0f);
	}

	public static BoundingBox one()
	{
		return new BoundingBox(1f, 1f, 1f, 1f);
	}


	public void read(DataInputStream reader) throws Exception
	{
		_left = reader.readFloat();
		_top = reader.readFloat();
		_right = reader.readFloat();
		_bottom = reader.readFloat();
	}

	public void write(DataOutputStream writer) throws Exception
	{
		writer.writeFloat(_left);
		writer.writeFloat(_top);
		writer.writeFloat(_right);
		writer.writeFloat(_bottom);
	}


}
