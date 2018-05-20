package finalproject;

/**
 * A vector is a geometric object that has both magnitude (length) and direction. 
 * A vector can also be represented as a point in 2-dimensional space. Vector's 
 * are a primary part of physics used to describe velocity and acceleration of an
 * entity as well as forces affecting the entity. A vector may be normalized which
 * then it is called a normal vector. Normal vectors are used in physics for 
 * collision resolution, specifically an entity colliding with another entity and 
 * a normal vector easily determines the resulting velocities directions based on 
 * the surfaces intersecting.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Vector 
{
	
	// The x component of this vector.
	public double x;
	
	// The y component of this vector.
	public double y;
	
	/**
	 * Initializes a vector at the origin.
	 */
	public Vector()
	{
		x = y = 0f;
	}
	
	/**
	 * Initializes a vector with its x and y values.
	 * 
	 * @param x => The x value of this vector.
	 * @param y => The y value of this vector.
	 */
	public Vector(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	
	/**
	 * Initializes a vector based on another vector.
	 * 
	 * @param v => A vector with x and y values.
	 */
	public Vector(Vector v)
	{
		x = v.x;
		y = v.y;
	}
	
	/**
	 * Sets this vector.
	 * 
	 * @param x => The x value of this vector.
	 * @param y => The y value of this vector.
	 */
	public void set(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Sets this vector based on another vector.
	 * 
	 * @param v => The vector to copy coordinates from.
	 */
	public void set(Vector v)
	{
		this.x = v.x;
		this.y = v.y;
	}
	
	/**
	 * Subtracts a vector from this vector.
	 * 
	 * @param v => The vector to subtract.
	 */
	public void subtract(Vector v)
	{
		this.x -= v.x;
		this.y -= v.y;
	}
	
	/**
	 * Multiplies this vector by some scalar.
	 * 
	 * @param scalar => The amount to multiply this vector by.
	 */
	public void multiply(double scalar)
	{
		this.x *= scalar;
		this.y *= scalar;
	}
	
	/**
	 * Turns this vector into the tangent on if itself.
	 */
	public void tangent()
	{
		double z = x;
		x = -y;
		y = z;
	}
	
	/**
	 * Normalizes this vector making its length exactly 1.
	 */
	public void normalize()
	{
		double d = length();
		// If the vector is pointing nowhere or it already has
		// a length of 1.0 then exit.
		if (d == 0.0 || d == 1.0)
			return;
		// Invert the distance, this is to only divide once.
		d = 1f / d;
		x *= d;
		y *= d;
	}
	
	/**
	 * Returns the distance from this vector to the origin. 
	 */
	public double length()
	{
		return Math.sqrt(x * x + y * y);
	}
	
	/**
	 * Returns true if these two vectors have the same values.
	 * 
	 * @param v => The vector to test against.
	 */
	public boolean isEqual(Vector v)
	{
		return (x == v.x && y == v.y);
	}
	
}
