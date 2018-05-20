package axe.util;

/**
 * A 2-dimensional point in space. With vector arithmetic you can add,
 * multiply, subtract, divide, and scale. Calculations for a vector
 * include: negation, normal, rotate, reflect, absolute, angle, 
 * cross product, dot product, distance, magnitude, mirror, and tangent.
 * 
 * @author Philip Diffenderfer
 */
public class Vector 
{

	// VARIABLES
	
	public float x;
	public float y;
	
	// CONSTRUCTORS
	
	public Vector() {
		x = y = 0f;
	}
	
	public Vector(float angle) {
		this.angle(angle, 1f);
	}
	
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector(Vector v) {
		x = v.x;
		y = v.y;
	}
	
	//////////////////////////////////////////
	// ACCESSIBILITY METHODS
	//////////////////////////////////////////
	
	public void clear() {
		x = y = 0f;
	}
	
	public void set(Vector v) {
		x = v.x;
		y = v.y;
	}
	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	//////////////////////////////////////////
	// IMMEDIATE VECTOR MATH
	//////////////////////////////////////////

	public void add(float s) {
		x += s;
		y += s;
	}

	public void add(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public void add(Vector v) {
		x += v.x;
		y += v.y;
	}

	public void add(Vector v, float scale) {
		x += v.x * scale;
		y += v.y * scale;
	}
	
	public void sub(float s) {
		x -= s;
		y -= s;
	}
	
	public void sub(float x, float y) {
		this.x -= x;
		this.y -= y;
	}
	
	public void sub(Vector v) {
		x -= v.x;
		y -= v.y;
	}
	
	public void mul(float s) {
		x *= s;
		y *= s;
	}
	
	public void mul(float x, float y) {
		this.x *= x;
		this.y *= y;
	}
	
	public void mul(Vector v) {
		x *= v.x;
		y *= v.y;
	}
	
	public void div(Vector v) {
		if (v.x != 0.0)
			x /= v.x;
		if (v.y != 0.0)	
			y /= v.y;
	}
	
	public void div(float s) {
		if (s != 0) {
			s = 1 / s;
			x *= s;
			y *= s;
		}
	}
	
	public void neg() {
		x = -x;
		y = -y;
	}
	
	public void tangent() {
		float ny = x;
		x = -y;
		y = ny;
	}
	
	public void normal(Vector origin, Vector point) {
		x = point.x - origin.x;
		y = point.y - origin.y;
		normal();
	}
	
	public void normal() {
		float sq = x * x + y * y;
		if (sq != 0.0 && sq != 1.0) {
			float scale = 1 / (float)StrictMath.sqrt(sq);
			x *= scale;
			y *= scale;
		}
	}

	public void reflect(Vector n) { 
		float dot = 2 * dot(n);
		x = n.x * dot - x;
		y = n.y * dot - y;
	}
	
	public void rotate(Vector n) {
		float ox = x, oy = y;
		x = ox * n.x + oy * n.y;
		y = oy * n.x - ox * n.y;
	}
	
	public void max(float magnitude) {
		float msq = magnitude * magnitude;
		float dsq = x * x + y * y;
		if (dsq > msq && dsq > 0.0) {
			float scale = magnitude / (float)StrictMath.sqrt(dsq);
			x *= scale;
			y *= scale;
		}
	}
	
	public void min(float magnitude) {
		float msq = magnitude * magnitude;
		float dsq = x * x + y * y;
		if (dsq < msq && dsq > 0.0) {
			float scale = magnitude / (float)StrictMath.sqrt(dsq);
			x *= scale;
			y *= scale;
		}
	}

	public float length(float newLength) {
		float sq = x * x + y * y;
		if (sq == 0f) {
			return 0f;
		}
		float length = (float)StrictMath.sqrt(sq);
		float scale = newLength / length;
		x *= scale;
		y *= scale;
		return length;
	}
	
	public void clamp(float min, float max) {
		float sq = x * x + y * y;
		if (sq != 0f) {
			float scale = 1f;
			if (sq < min * min) {
				scale = min / (float)StrictMath.sqrt(sq);
			}
			if (sq > max * max) {
				scale = max / (float)StrictMath.sqrt(sq);
			}
			x *= scale;
			y *= scale;	
		}
	}
	
	public void mirrorx() {
		y = -y;
	}	
	
	public void mirrory() {
		x = -x;
	}
	
	public void abs() {
		x = StrictMath.abs(x);
		y = StrictMath.abs(y);
	}
	
	public void direct(Vector origin, Vector target) {
		x = target.x - origin.x;
		y = target.y - origin.y;
	}
	
	public void swap(Vector v) {
		float xx = x;
		float yy = y;
		x = v.x;
		y = v.y;
		v.x = xx;
		v.y = yy;
	}
	
	public Vector copy() {
		return new Vector(x, y);
	}
	
	//////////////////////////////////////////
	//	
	//////////////////////////////////////////

	public float angle()
	{
		return Math.angle(x, y);
	}
	
	public void angle(float angle) {
		this.angle(angle, 1f);
	}
	
	public void angle(float angle, float scale) {
		x = (float)StrictMath.cos(angle) * scale;
		y = (float)StrictMath.sin(angle) * scale;
	}
	
	public float dot()
	{
		return x * x + y * y;
	}
	
	public float dot(Vector v)
	{
		return v.x * x + v.y * y;
	}
	
	public float cross(Vector v)
	{
		return x * v.y - y * v.x; 
	}
	
	public float length()
	{
		return (float)StrictMath.sqrt(x * x + y * y);
	}
	
	public boolean isNormal()
	{
		return dot() == 1.0;
	}
	
	public boolean isZero(float epsilon)
	{
		return (StrictMath.abs(x) < epsilon && StrictMath.abs(y) < epsilon);
	}
	
	//////////////////////////////////////////
	
	@Override
	public String toString()
	{
		return String.format("{%f, %f} (%f)", x, y, length());
	}
	
	public final static Vector XAXIS = new Vector(1, 0);
	public final static Vector YAXIS = new Vector(0, 1);

	public static Vector normal(Vector v) {
		Vector w = new Vector(v);
		w.normal();
		return w;
	}
	
	public static Vector sub(Vector v, Vector w) {
		Vector u = new Vector(v);
		u.sub(w);
		return u;
	}
	
	public static Vector add(Vector v, Vector w) {
		Vector u = new Vector(v);
		u.add(w);
		return u;
	}
	
	public static float distance(Vector v, Vector w) {
		float dx = v.x - w.x;
		float dy = v.y - w.y;
		return (float)StrictMath.sqrt(dx * dx + dy * dy);
	}
	
	public static float distanceSq(Vector v, Vector w) {
		float dx = v.x - w.x;
		float dy = v.y - w.y;
		return (dx * dx + dy * dy);
	}
	
	
}
