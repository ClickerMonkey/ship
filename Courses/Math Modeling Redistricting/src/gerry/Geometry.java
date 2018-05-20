package gerry;

public class Geometry 
{

	public static float distanceSq(float x1, float y1, float x2, float y2) 
	{
		float dx = x1 - x2;
		float dy = y1 - y2;
		return (dx * dx + dy * dy);
	}
	
	public static float distanceSq(Node a, Node b) 
	{
		return distanceSq(a.x(), a.y(), b.x(), b.y());
	}
	
	public static boolean cross(Pixel p0, Pixel p1, Pixel p2, Pixel p3) 
	{
		return cross(p0.x, p0.y, p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
	}
	
	public static boolean cross(int x0, int y0, int x1, int y1, int x2, int y2, int x3, int y3) 
	{
		if (x0 == x2 && y0 == y2) return false;
		if (x1 == x2 && y1 == y2) return false;
		if (x0 == x3 && y0 == y3) return false;
		if (x1 == x3 && y1 == y3) return false;
		
		int x21 = x1 - x0;
		int y21 = y1 - y0;
		int x13 = x0 - x2;
		int y13 = y0 - y2;
		int x43 = x3 - x2;
		int y43 = y3 - y2;
		
		int denom = (y43 * x21) - (x43 * y21);
		if (denom == 0)	// Parallel or coincident
			return false;

		int ds = Integer.signum(denom);
		int d = denom * ds;
		
		int da = ((x43 * y13) - (y43 * x13)) * ds;
		if (da < 0 || da > d)	// Intersection point not on a
			return false;
		
		int db = (((x21 * y13) - (y21 * x13))) * ds;
		if (db < 0 || db > d)	// Intersection point not on b
			return false;
		
		return true;
	}
	
	public static boolean cross(Edge v, Edge w) 
	{
		return cross(v.a.x(), v.a.y(), v.b.x(), v.b.y(), w.a.x(), w.a.y(), w.b.x(), w.b.y());
	}
	
	public static boolean cross(float x0, float y0, float x1, float y1, float x2, float y2, float x3, float y3) 
	{
		if (x0 == x2 && y0 == y2) return false;
		if (x1 == x2 && y1 == y2) return false;
		if (x0 == x3 && y0 == y3) return false;
		if (x1 == x3 && y1 == y3) return false;
		
		float x21 = x1 - x0;
		float y21 = y1 - y0;
		float x13 = x0 - x2;
		float y13 = y0 - y2;
		float x43 = x3 - x2;
		float y43 = y3 - y2;
		
		float denom = (y43 * x21) - (x43 * y21);
		if (denom == 0)	// Parallel or coincident
			return false;

		float ds = Math.signum(denom);
		float d = denom * ds;
		
		float da = ((x43 * y13) - (y43 * x13)) * ds;
		if (da < 0 || da > d)	// Intersection point not on a
			return false;
		
		float db = (((x21 * y13) - (y21 * x13))) * ds;
		if (db < 0 || db > d)	// Intersection point not on b
			return false;
		
		return true;
	}
	
}
