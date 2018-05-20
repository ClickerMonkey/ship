package model.util;

public class Point {

	public double x, y, z;
	
	public Point() {
	}

	public Point(Point p) {
		this.x = p.x;
		this.y = p.y;
		this.z = p.z;
	}
	
	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void set(Point p) {
		this.x = p.x;
		this.y = p.y;
		this.z = p.z;
	}
	
	public void set(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double dot(Point a) {
		return (x * a.x) + (y * a.y) + (z * a.z);
	}
	
	public void cross(Point a, Point b) {
		x = a.y * b.z - b.y * a.z;
		y = b.x * a.z - a.x * b.z;
		z = a.x * b.y - b.x * a.y;
	}
	
	public void normal(Point a, Point b, Point c) {
		Point v1 = new Point(c.x - b.x, c.y - b.y, c.z - b.z);
		Point v2 = new Point(a.x - b.x, a.y - b.y, a.z - b.z);
		cross(v1, v2);
		normalize();
	}
	
	public void normalize() {
		double dot = (x * x) + (y * y) + (z * z);
		if (dot == 1.0) {
			return;
		}
		double invlength = 1.0 / Math.sqrt(dot);
		x *= invlength;
		y *= invlength;
		z *= invlength;
	}

	public void negate() {
		x = -x;
		y = -y;
		z = -z;
	}
	
	
	/**
	 * Returns the signed area of the given triangle. If the area returned is
	 * zero then the three points are collinear. If the area returned is 
	 * positive then the three points are clockwise, if it's negative then the
	 * points are counter-clockwise.
	 * 
	 * @param a The first point in the triangle.
	 * @param b The second point in the triangle.
	 * @param c The third point in the triangle.
	 * @return The signed area of the triangle the three points form.
	 */
	public static double area(Point a, Point b, Point c) {
		return ((a.x - c.x) * (b.y - c.y) - (a.y - c.y) * (b.x - c.x)) * 0.5;
	}
	
}
