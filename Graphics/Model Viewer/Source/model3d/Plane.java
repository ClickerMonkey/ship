package model3d;

public class Plane 
{
	public static final float EPSILON = 0.0000001f;

	public final float a, b, c, d;

	public Plane(Vector p1, Vector p2, Vector p3) {
		this(p2, Vector.getNormal(p1, p2, p3));
	}
	
	public Plane(Vector p, Vector normal) {
		a = normal.x;
		b = normal.y;
		c = normal.z;
		d = -normal.dot(p);
	}
	
	public float distance(Vector v) {
		return (a * v.x) + (b * v.y) + (c * v.z) + d;
	}
	
	public boolean isOn(Vector v) {
		return Math.abs(distance(v)) < EPSILON;
	}
	
	public boolean isBelow(Vector v) {
		return distance(v) < 0;
	}
	
	public boolean isAbove(Vector v) {
		return distance(v) > 0;
	}

	
	
}
