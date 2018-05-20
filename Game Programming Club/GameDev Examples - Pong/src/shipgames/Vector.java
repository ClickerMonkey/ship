package shipgames;

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
	
	/**
	 * The x component of this vector.
	 */
	public float x;
	
	/**
	 * The y component of this vector.
	 */
	public float y;
	
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
	public Vector(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Initializes a vector based on its angle in degrees.
	 * 
	 * @param angle => The angle in degrees.
	 */
	public Vector(float angle)
	{
		angle = angle * (float)Math.PI / 180f;
		x = (float)Math.cos(angle);
		y = (float)Math.sin(angle);
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
	public void set(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Sets this vector as a normalized vector pointing
	 * in some direction given by an angle in degrees.
	 * 
	 * @param degrees => The angle in degrees to point to.
	 */
	public void set(float degrees)
	{
		degrees = (float)(degrees * Math.PI / 180.0);
		x = (float)StrictMath.cos(degrees);
		y = (float)StrictMath.sin(degrees);
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
	 * Adds x and y values to this vector.
	 * 
	 * @param x => The amount to add to x.
	 * @param y => The amount to add to y.
	 */
	public void add(float x, float y)
	{
		this.x += x;
		this.y += y;
	}
	
	/**
	 * Adds a vector to this vector.
	 * 
	 * @param v => The vector to add to this vector.
	 */
	public void add(Vector v)
	{
		this.x += v.x;
		this.y += v.y;
	}
	
	/**
	 * Adds a vector multiplied by some scalar to this vector.
	 * 
	 * @param v => The vector to add and be scaled.
	 * @param scalar => The amount to scale the vector being added.
	 */
	public void add(Vector v, float scalar)
	{
		this.x += v.x * scalar;
		this.y += v.y * scalar;
	}
	
	/**
	 * Adds a vector multiplied by some scalar to this vector.
	 * 
	 * @param x => The x value to add and be scaled.
	 * @param y => The y value to add and be scaled.
	 * @param scalar => The amount to scale the vector being added.
	 */
	public void add(float x, float y, float scalar)
	{
		this.x += x * scalar;
		this.y += y * scalar;
	}
	
	/**
	 * Subtracts x and y values from this vector.
	 * 
	 * @param x => The amount to subtract from x.
	 * @param y => The amount to subtract from y.
	 */
	public void subtract(float x, float y)
	{
		this.x -= x;
		this.y -= y;
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
	public void multiply(float scalar)
	{
		this.x *= scalar;
		this.y *= scalar;
	}
	
	/**
	 * Multiplies this vector by x and y scalars.
	 * 
	 * @param x => The scalar to multiply x by.
	 * @param y => The scalar to multiply y by.
	 */
	public void multiply(float x, float y)
	{
		this.x *= x;
		this.y *= y;
	}
	
	/**
	 * Multiplies this vector by another vector.
	 * 
	 * @param v => The vector to multiply by.
	 */
	public void multiply(Vector v)
	{
		this.x *= v.x;
		this.y *= v.y;
	}
	
	/**
	 * Divides this vector by some scalar.
	 * 
	 * @param scalar => The amount to divide this vector by.
	 */
	public void divide(float scalar)
	{
		if (scalar == 0.0)
			return;
		// Invert scalar to only divide once
		scalar = 1f / scalar;
		this.x *= scalar;
		this.y *= scalar;
	}
	
	/**
	 * Divides this vector by some vector.
	 * 
	 * @param v => The vector to divide by.
	 */
	public void divide(Vector v)
	{
		if (v.x != 0.0)
			this.x /= v.x;
		if (v.y != 0.0)
			this.y /= v.y;
	}
	
	/**
	 * Negates this vector's values. (Positive value becomes
	 * negative, negative becomes positive)
	 */
	public void negate()
	{
		x = -x;
		y = -y;
	}
	
	/**
	 * Turns this vector into the tangent on if itself.
	 */
	public void tangent()
	{
		float z = x;
		x = -y;
		y = z;
	}
	
	/**
	 * Normalizes this vector making its length exactly 1.
	 */
	public void normalize()
	{
		float d = length();
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
	 * Reflects this vector across a normal.
	 * 
	 * @param normalX => The normal x value.
	 * @param normalY => The normal y value.
	 */
	public void reflect(float normalX, float normalY)
	{
		float dot = 2 * dot(normalX, normalY);
		x = (dot * normalX) - x;
		y = (dot * normalY) - y;
	}
	
	/**
	 * Reflects this vector across a normal.
	 * 
	 * @param normal => The normal to reflect across.
	 */
	public void reflect(Vector normal)
	{
		reflect(normal.x, normal.y);
	}
	
	/**
	 * Rotates this vector by adding the direction of some normal
	 * to this vector's direction (angle).
	 * 
	 * @param normalX => The normal x value.
	 * @param normalY => The normal y value.
	 */
	public void rotate(float normalX, float normalY)
	{
		float xx = x, yy = y;
		x = xx * normalX - yy * normalY;
		y = yy * normalX + xx * normalY;
	}
	
	/**
	 * Rotates this vector by adding the direction of some normal
	 * to this vector's direction (angle).
	 * 
	 * @param normal => The normal to rotate by.
	 */
	public void rotate(Vector normal)
	{
		rotate(normal.x, normal.y);
	}
	
	/**
	 * Rotates this vector by some angle in degrees. When this vector
	 * is rotated its magnitude is constant but its direction adds some
	 * value in degrees.
	 * 
	 * @param degrees => The angle in degrees to rotate by.
	 */
	public void rotate(float degrees)
	{
		degrees = (float)(degrees * Math.PI / 180.0);
		float cos = (float)StrictMath.cos(degrees);
		float sin = (float)StrictMath.sin(degrees);
		float xx = x, yy = y;

		x = xx * cos - yy * sin;
		y = yy * cos + xx * sin;
	}
	
	/**
	 * Rotates this vector by some angle in radians. When this vector
	 * is rotated its magnitude is constant but its direction adds some
	 * value in radians.
	 * 
	 * @param radians => The angle in radians to rotate by.
	 */
	public void rotateRadians(float radians)
	{
		float cos = (float)StrictMath.cos(radians);
		float sin = (float)StrictMath.sin(radians);
		float xx = x, yy = y;
		
		x = xx * cos - yy * sin;
		y = yy * cos + xx * sin;
	}
	
	/**
	 * This will return a projected y value given a x value. The
	 * given x value is an imaginary x component to a point along
	 * this vectors direction and this function returns the y that
	 * completes the point along this vector with the given x.
	 * If this vector has a zero x component this will throw an
	 * error since there can be no projected y.
	 * 
	 * @param x => The x value for a point on this vector. 
	 */
	public float projectX(float x)
	{
		return (x / this.x) * this.y;
	}
	
	/**
	 * This will return a projected x value given a y value. The
	 * given y value is an imaginary y component to a point along
	 * this vectors direction and this function returns the x that
	 * completes the point along this vector with the given y.
	 * If this vector has a zero y component this will throw an
	 * error since there can be no projected x.
	 * 
	 * @param y => The y value for a point on this vector.
	 */
	public float projectY(float y)
	{
		return (y / this.y) * this.x;
	}
	
	/**
	 * Mirrors this vector across the x-axis negating 
	 * the y component.
	 */
	public void mirrorX()
	{
		y = -y;
	}
	
	/**
	 * Mirrors this vector across the y-axis negating
	 * the x component.
	 */
	public void mirrorY()
	{
		x = -x;
	}
	
	/**
	 * Reflects this vector into the second quadrant of a graph
	 * so the coordinates are both positive.
	 */
	public void absolute()
	{
		x = Math.abs(x);
		y = Math.abs(y);
	}

	/**
	 * Returns the 'sign' of one vector compared to this one. This
	 * will return -1 if the given vector is on the right side of this
	 * vector and it will return 1 if the given vector is on the left
	 * side of this vector.
	 * 
	 * @param v => The vector to check against.
	 */
	public int sign(Vector v)
	{
		return (y * v.x > x * v.y ? -1 : 1);
	}

	/**
	 * Returns the angle of this vector in degrees from the origin.
	 */
	public float angle()
	{
		return (float)(StrictMath.atan2(-y, -x) * 180 / Math.PI + 180.0);
	}
	
	/**
	 * Returns the angle of this vector in degrees from the origin.
	 */
	public float angleRadians()
	{
		return (float)StrictMath.atan2(y, x);
	}
	
	/**
	 * Returns the dot product of this vector and an x and y.
	 * 
	 * @param x => The x value to find the dot product against.
	 * @param y => The y value to find the dot product against.
	 */
	public float dot(float x, float y)
	{
		return (x * this.x + y * this.y);
	}

	/**
	 * Returns the dot product of this vector and another.
	 * 
	 * @param v => The vector to find the dot product against.
	 */
	public float dot(Vector v)
	{
		return (v.x * x + v.y * y);
	}

	/**
	 * Finds the cross product of this vector and an x and y.
	 * 
	 * @param x => The x value to find the cross product against.
	 * @param y => The y value to find the cross product against.
	 */
	public float cross(float x, float y)
	{
		return (this.x * y - this.y * x);
	}

	/**
	 * Finds the cross product of this vector and another.
	 * 
	 * @param v => The vector to find the cross product against.
	 */
	public float cross(Vector v)
	{
		return (x * v.y - y * v.x); 
	}

	/**
	 * Returns true if this vector is normalized and false if not.
	 */
	public boolean isNormal()
	{
		return (distanceSq() == 1.0);
	}

	/**
	 * Returns the distance squared from this vector to the origin.
	 */
	public float distanceSq()
	{
		return (x * x + y * y);
	}

	/**
	 * Returns the distance squared from this vector to another.
	 * 
	 * @param x => The x value of the vector.
	 * @param y => The y value of the vector.
	 */
	public float distanceSq(float x, float y)
	{
		float dx = (this.x - x);
		float dy = (this.y - y);
		return (dx * dx + dy * dy);
	}

	/**
	 * Returns the distance squared from this vector to another.
	 * 
	 * @param v => The vector to find the distance to.
	 */
	public float distanceSq(Vector v)
	{
		return distanceSq(v.x, v.y);
	}
	
	/**
	 * Returns the distance from this vector to the origin. 
	 */
	public float length()
	{
		return (float)Math.sqrt(x * x + y * y);
	}

	/**
	 * Clips the length of this vector based on a min and max
	 * allowable length.
	 * 
	 * @param minLength => The minimum length of this vector.
	 * @param maxLength => The maximum length of this vector.
	 */
	public float clipLength(float minLength, float maxLength)
	{
		float length = length();
		if (length == 0f)
			return 0f;
		float lengthInv = 1f / length;
		
		if (length < minLength)
			length = minLength;
		if (length > maxLength)
			length = maxLength;
		
		x *= lengthInv * length;
		y *= lengthInv * length;
	
		return length;
	}
	
	/**
	 * Returns the distance from this vector to another.
	 * 
	 * @param x => The x value of the vector.
	 * @param y => The y value of the vector.
	 */
	public float distance(float x, float y)
	{
		float dx = (this.x - x);
		float dy = (this.y - y);
		return (float)Math.sqrt(dx * dx + dy * dy);
	}

	/**
	 * Returns the distance from this vector to another.
	 * 
	 * @param v => The vector to find the distance to.
	 */
	public float distance(Vector v)
	{
		return distance(v.x, v.y);
	}

	/**
	 * Returns the signed distance from this vector to a line.
	 *  
	 * @param start => The start (origin) vector of the line.
	 * @param end => The end vector of the line.
	 */
	public float distanceSigned(Vector start, Vector end)
	{
		float distance = start.distance(end);
		if (distance == 0f)
			return distance(start);
		float distanceInv = 1f / distance;
		float a = (end.y - start.y) * (start.x - x);
		float b = (end.x - start.x) * (start.y - y);
		return (a - b) * distanceInv;		
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

	/**
	 * Returns true if these two vectors do not have the same values.
	 * 
	 * @param v => The vector to test against.
	 */
	public boolean isNotEqual(Vector v)
	{
		return (x != v.x || y != v.y);
	}
	
	/**
	 * Returns a string in the format (x, y) where each value has 2 decimal
	 * place precision.
	 */
	public String toString()
	{
		return String.format("(%.2f, %.2f)", x, y);
	}
	
	/**
	 * Returns the distance between 2 vectors.
	 * 
	 * @param a => The first vector.
	 * @param b => The second vector.
	 */
	public static float distance(Vector a, Vector b)
	{
		float dx = a.x - b.x;
		float dy = a.y - b.y;
		return (float)Math.sqrt(dx * dx + dy * dy);
	}
	
	/**
	 * Returns the distance squared between 2 vectors.
	 * 
	 * @param a => The first vector.
	 * @param b => The second vector.
	 */
	public static float distanceSq(Vector a, Vector b)
	{
		float dx = a.x - b.x;
		float dy = a.y - b.y;
		return dx * dx + dy * dy;
	}
	
}
