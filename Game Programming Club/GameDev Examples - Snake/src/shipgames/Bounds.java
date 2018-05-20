package shipgames;

/**
 * A mixture between a rectangle and a bounding box. This enables less 
 * calculations between the two that normally occur. Most functions are size
 * preservering (the width and height are kept). This Bounds class has an x
 * coordinate (left), y coordinate (top), bottom, right, width, and height.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Bounds 
{

	// The x value of the left side
	private float left;
	// The y value of the top side
	private float top;
	// The x value of the right side
	private float right;
	// The y value of the bottom side
	private float bottom;
	// The width of the bounds
	private float width;
	// The heigth of the bounds
	private float height;
	
	/**
	 * Initializes a bounds located at the origin with no size.
	 */
	public Bounds()
	{
		set(0f, 0f, 0f, 0f);
	}
	
	/**
	 * Initializes a bounds with a location and size.
	 * 
	 * @param x => The x value for the left side.
	 * @param y => The y value for the top side. 
	 * @param width => The width of the bounds.
	 * @param height => The height of the bounds.
	 */
	public Bounds(float x, float y, float width, float height)
	{
		set(x, y, width, height);
	}
	
	/**
	 * Initializes a bounds with a location and size.
	 * 
	 * @param location => The location (top left) of the bounds.
	 * @param size => The size of the bounds.
	 */
	public Bounds(Vector location, Vector size)
	{
		set(location.x, location.y, size.x, size.y);
	}
	
	/**
	 * Sets this bounds location and size.
	 * 
	 * @param x => The x value for the left side.
	 * @param y => The y value for the top side. 
	 * @param width => The width of the bounds.
	 * @param height => The height of the bounds.
	 */
	public void set(float x, float y, float width, float height)
	{
		this.right = (this.left = x) + width;
		this.bottom = (this.top = y) + height;
		
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Sets this bounds location and size.
	 * 
	 * @param location => The location (top left) of the bounds.
	 * @param size => The size of the bounds.
	 */
	public void set(Vector location, Vector size)
	{
		set(location.x, location.y, size.x, size.y);
	}
	
	/**
	 * Sets the bounds based on the left, top, right, and bottom side values.
	 * 
	 * @param left => The x value of the left side.
	 * @param top => The y value of the top side.
	 * @param right => The x value of the right side.
	 * @param bottom => The y value of the bottom side.
	 */
	public void setLTRB(float left, float top, float right, float bottom)
	{
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		
		this.width = right - left;
		this.height = bottom - top;
	}
	
	/**
	 * Updates the size (width and height) based on the positions of the left,
	 * right, top, and bottoms sides. This method should be called after one or
	 * several calls to include().
	 */
	public void updateSize()
	{
		width = right - left;
		height = bottom - top;
	}
	
	/**
	 * Includes a vector into this bound. If the vector is outside the bounds
	 * then the left, right, top, and bottom are adjusted to include it.
	 * 
	 * @param v => The vector to include.
	 */
	public void include(Vector v)
	{
		include(v.x, v.y);
	}
	
	/**
	 * Includes a vector into this bound. If the vector is outside the bounds
	 * then the left, right, top, and bottom are adjusted to include it.
	 * 
	 * @param x => The x value of the vector to include.
	 * @param y => The y value of the vector to include.
	 */
	public void include(float x, float y)
	{
		if (x < left)
			left = x;
		if (x > right)
			right = x;
		if (y < top)
			top = y;
		if (y > bottom)
			bottom = y;
	}
	
	/**
	 * Returns true if the vector is inside or on the edge of this bounds.
	 * 
	 * @param v => The vector to test for containment.
	 */
	public boolean contains(Vector v)
	{
		return contains(v.x, v.y);
	}
	
	/**
	 * Returns true if the vector is inside or on the edge of this bounds.
	 * 
	 * @param x => The x value of the vector to test for containment.
	 * @param y => The y value of the vector to test for containment.
	 */
	public boolean contains(float x, float y)
	{
		return !(x < left || x > right || y < top || y > bottom);
	}
	
	/**
	 * Returns true if the bounds is inside or on the edge of this bounds.
	 * 
	 * @param b => The bounds to test for containment.
	 */
	public boolean contains(Bounds b)
	{
		return !(b.left < left || b.right > right || b.top < top || b.bottom > bottom);
	}
	
	/**
	 * Returns true if the bounds and this bounds intersect (have overlapping edges)
	 * If the bounds is on the edge of this bounds then this still returns false.
	 * 
	 * @param b => The bounds to test for intersection. 
	 */
	public boolean intersects(Bounds b)
	{
		return !(b.left >= right || b.right <= left || b.top >= bottom || b.bottom <= top);
	}
	
	/**
	 * Sets the x value (left side) while adjusting the right side according to
	 * the new left side and the width.
	 * 
	 * @param x => The x value of the left side.
	 */
	public void setX(float x)
	{
		left = x;
		right = x + width;
	}
	
	/**
	 * Sets the y value (top side) while adjusting the bottom side according to
	 * the new top side and the height.
	 * 
	 * @param y => The y value of the top side.
	 */
	public void setY(float y)
	{
		top = y;
		bottom = y + height;
	}
	
	/**
	 * Sets the width of this bounds while adjusting the right side according to
	 * the left side and the new width.
	 * 
	 * @param value => The new width of this bounds.
	 */
	public void setWidth(float value)
	{
		width = value;
		right = left + value;
	}
	
	/**
	 * Sets the height of this bounds while adjusting the bottom side according to
	 * the top side and the new height.
	 * 
	 * @param value => The new height of this bounds.
	 */
	public void setHeight(float value)
	{
		height = value;
		bottom = top + value;
	}
	
	/**
	 * Sets the left side of this bounds without affecting the right side and
	 * without correcting the width. After all manipulation of the bounds is
	 * done then updateSize() should be called to correct the width.
	 * 
	 * @param value => The left side of this bounds.
	 */
	public void setLeft(float value)
	{
		left = value;
	}
	
	/**
	 * Sets the top side of this bounds without affecting the bottom side and
	 * without correcting the height. After all manipulation of the bounds is
	 * done then updateSize() should be called to correct the height.
	 * 
	 * @param value => The top side of this bounds.
	 */
	public void setTop(float value)
	{
		top = value;
	}
	
	/**
	 * Sets the right side of this bounds without affecting the left side and
	 * without correcting the width. After all manipulation of the bounds is
	 * done then updateSize() should be called to correct the width.
	 * 
	 * @param value => The right side of this bounds.
	 */
	public void setRight(float value)
	{
		right = value;
	}
	
	/**
	 * Sets the right side while adjusting the left side according to the width.
	 * 
	 * @param value => The right side of this bounds.
	 */
	public void moveRight(float value)
	{
		right = value;
		left = right - width;
	}
	
	/**
	 * Sets the bottom side of this bounds without affecting the top side and
	 * without correcting the height. After all manipulation of the bounds is
	 * done then updateSize() should be called to correct the height.
	 * 
	 * @param value => The bottom side of this bounds.
	 */
	public void setBottom(float value)
	{
		bottom = value;
	}
	
	/**
	 * Sets the bottom side while adjusting the top side according to the height.
	 * 
	 * @param value => The bottom side of this bounds.
	 */
	public void moveBottom(float value)
	{
		bottom = value;
		top = bottom - height;
	}
	
	/**
	 * Returns the x value (left side) of this bounds.
	 */
	public float getX()
	{
		return left;
	}
	
	/**
	 * Returns the y value (top side) of this bounds.
	 */
	public float getY()
	{
		return top;
	}
	
	/**
	 * Returns the width of this bounds.
	 */
	public float getWidth()
	{
		return width;
	}
	
	/**
	 * Returns the height of this bounds.
	 */
	public float getHeight()
	{
		return height;
	}

	/**
	 * Returns the right side of this bounds.
	 */
	public float getRight()
	{
		return right;
	}
	
	/**
	 * Returns the left side of this bounds.
	 */
	public float getBottom()
	{
		return bottom;
	}
	
}
