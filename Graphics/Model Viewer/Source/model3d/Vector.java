package model3d;

public class Vector 
{

	public float x, y, z;
	
	public Vector() {
	}
	
	public Vector(Vector v) {
		set(v);
	}
	
	public Vector(Vector a, Vector b) {
		set(a.x - b.x, a.y - b.y, a.z - b.z);
	}
	
	public Vector(float x, float y, float z) {
		set(x, y, z);
	}
	
	public void set(Vector v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float dot(Vector a) {
		return (x * a.x) + (y * a.y) + (z * a.z);
	}
	
	public void cross(Vector a, Vector b) {
		x = a.y * b.z - b.y * a.z;
		y = b.x * a.z - a.x * b.z;
		z = a.x * b.y - b.x * a.y;
	}
	
	public void subtract(Vector a) {
		x -= a.x;
		y -= a.y;
		z -= a.z;
	}

	public void normalize() {
		float dot = (x * x) + (y * y) + (z * z);
		if (dot != 1.0f) {
			float inv = 1.0f / (float)Math.sqrt(dot);
			x *= inv;
			y *= inv;
			z *= inv;
		}
	}
	
	public static Vector getNormal(Vector p1, Vector p2, Vector p3) {
		Vector v1 = new Vector(p1, p2);
		Vector v2 = new Vector(p3, p2);
		Vector normal = new Vector();
		normal.cross(v1, v2);
		normal.normalize();
		return normal;
	}

}