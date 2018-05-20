package finalproject;

/**
 * A plane defined by some line. A plane can evaluate some point to determine
 * the signed distance of the point, whether the point is in front or in back of
 * the plane. A plane can split a line up into two segments and can split a
 * polygon into two polygons.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Plane 
{
	
	/**
	 * This is the 'thickness' of the plane.
	 */
  	public static final double EPSILON = 0.0001;
	
  	
  	/**
  	 * A point or an entire line is infront of a Plane.
  	 */
	public static final int IN_FRONT = 0;
	
  	/**
  	 * A point or an entire line is behind of a Plane.
  	 */
	public static final int IN_BACK  = 1;
	
  	/**
  	 * A point or an entire line is directly on top of a Plane.
  	 */
	public static final int ON_TOP   = 2;
	
  	/**
  	 * A line crosses (intersects) the plane.
  	 */
	public static final int OVERLAP  = 4;
	
	
	// The A, B, and C component.
	private double a;
	private double b;
	private double c;
	
	// The temporary vector that lies on this plane from the last split.
	private Vector cross = new Vector();
	
	/**
	 * Initializes a plane based on a line.
	 * 
	 * @param l => The line to compute this planes components from.
	 */
	public Plane(Line l)
	{
		a = (l.getStart().y - l.getEnd().y);
		b = (l.getEnd().x - l.getStart().x);
		c = (-a * l.getStart().x) - (b * l.getStart().y);
	}
	
	/**
	 * Initializes a plane based on a plane.
	 * 
	 * @param p => The plane to copy from.
	 */
	public Plane(Plane p)
	{
		a = p.a;
		b = p.b;
		c = p.c;
	}
	
	/**
	 * Evaluates a point to this plane and determines if the point is in front
	 * of this plane, ibehind it, or on the plane.
	 * 
	 * @param v => The vector to evaluate against.
	 * @return Either IN_FRONT, IN_BACK, or ON_TOP.
	 */
	public int eval(Vector v)
	{
		// The signed distance v is from this plane
		double e = a * v.x + b * v.y + c;
		
		// Determine which side of the plane the vector is on, or if its on top.
		if (e > EPSILON)
			return IN_FRONT;
		else if (e < -EPSILON)
			return IN_BACK;
		else
			return ON_TOP;
	}
	
	/**
	 * Evaluates a line to this plane and determines if the line is in front
	 * of this plane, behind it, on the plane, or overlapping it.
	 * 
	 * @param l => The line to evaluate against.
	 * @return Either IN_FRONT, IN_BACK, or ON_TOP.
	 */
	public int eval(Line l)
	{
		int s = eval(l.getStart());
		int e = eval(l.getEnd());

		// The line is on top of this plane, or is completely on one side.
		if (s == e)
			return s;
		// The start point is on the plane but the end is not
		else if (s == ON_TOP)
			return e;
		// The end point is on the plane but the start is not
		else if (e == ON_TOP)
			return s;
		// The line crosses the plane 
		else
			return OVERLAP;
	}

	/**
	 * This will split the given line in half with this plane. If the line 
	 * doesn't even cross the plane then return null.
	 * 
	 * @param l => The line to split with this plane.
	 */
	public Line[] split(Line l)
	{
		Plane plane = new Plane(l);
		Line lines[] = new Line[2];
		
		cross.set(0, 0);
		
		double div = a * plane.b - b * plane.a;
		// This theoretically should never happen
		if (div == 0)
		{
			if (plane.a == 0)
				cross.x = l.getStart().x;
			if (plane.b == 0)
				cross.y = l.getStart().y;
			if (a == 0)
				cross.x = -b;
			if (b == 0)
				cross.y = c;
		}
		else
		{
			div = 1 / div;
			cross.x = (-c * plane.b + b * plane.c) * div;
			cross.y = (-a * plane.c + c * plane.a) * div;
		}
		
		int s = eval(l.getStart());
		int e = eval(l.getEnd());

		lines[IN_FRONT] = new Line(l);
		lines[IN_BACK] = new Line(l);
		
		// If the line crosses this plane...
		if (s == IN_FRONT && e == IN_BACK)
		{
			lines[IN_FRONT].setStart(l.getStart());
			lines[IN_FRONT].setEnd(cross);
       		lines[IN_BACK].setStart(cross);
      		lines[IN_BACK].setEnd(l.getEnd());
		}
		else if (s == IN_BACK && e == IN_FRONT)
		{
			lines[IN_BACK].setStart(l.getStart());
			lines[IN_BACK].setEnd(cross);
       		lines[IN_FRONT].setStart(cross);
      		lines[IN_FRONT].setEnd(l.getEnd());
		}
		// If the line doesn't cross the plane then it cannot be split.
		else
		{
			return null;
		}
		
		return lines;
	}
	
	/**
	 * Returns the point at which 2 planes intersect.
	 * 
	 * @param p The plane the intersection is on.
	 */
	public Vector intersection(Plane p)
	{
		double div = 1.0 / (a * p.b - b * p.a);
		double x = (-c * p.b + b * p.c) * div;
		double y = (-a * p.c + c * p.a) * div;
		
		return new Vector(x, y);
	}
	
	/**
	 * Returns the point at which a plane and line intersect.
	 * 
	 * @param l => The line the intersection is on.
	 */
	public Vector intersection(Line l)
	{
		return intersection(new Plane(l));
	}
	
	/**
	 * Returns two polygons split by a plane.
	 * 
	 * @param p => The polygon to split.
	 */
	public Polygon[] split(Polygon p)
	{
		int total = p.getSize();
		// The left and right polygons generated by the split
		Polygon front = new Polygon(total);
		Polygon back = new Polygon(total);
		
		// The two vectors making up the intersection (if any)
		Vector A, B;
		
		// The position of the current point on the polygon to this plane.
		int evalA, evalB;
		
		A = p.get(total - 1);
		evalA = eval(A);
		
		for (int i = 0; i < total; i++)
		{
			B = p.get(i);
			evalB = eval(B);
			
			if (evalB == IN_FRONT)
			{
				if (evalA == IN_BACK)
				{
					// The line between point A and point B
					Line line = new Line(A, B);
					Vector inter = intersection(line);
					// Add the intersection point to both of the polygons
					back.add(inter);
					front.add(inter);
				}
				front.add(B);
			}
			else if (evalB == IN_BACK)
			{
				if (evalA == IN_FRONT)
				{
					// The line between point A and point B
					Line line = new Line(A, B);
					Vector inter = intersection(line);
					// Add the intersection point to both of the polygons
					back.add(inter);
					front.add(inter);
				}
				back.add(B);
			}
			else
			{
				// The current point is on the plane so add it to both planes
				front.add(B);
				back.add(B);
			}
			// Set point A as point B
			A = B;
			evalA = evalB;
		}
		
		return new Polygon[] {front, back};
	}
	
	/**
	 * Sets the A component of this plane.
	 * 
	 * @param a => The new A component.
	 */
	public void setA(double a)
	{
		this.a = a;
	}
	
	/**
	 * Sets the B component of this plane.
	 * 
	 * @param b => The new B component.
	 */
	public void setB(double b)
	{
		this.b = b;
	}
	
	/**
	 * Sets the C component of this plane.
	 * 
	 * @param c => The new C component.
	 */
	public void setC(double c)
	{
		this.c = c;
	}
	
	/**
	 * Returns the A component of this plane.
	 */
	public double getA()
	{
		return a;
	}
	
	/**
	 * Returns the B component of this plane.
	 */
	public double getB()
	{
		return b;
	}
	
	/**
	 * Returns the C component of this plane.
	 */
	public double getC()
	{
		return c;
	}
	
	
}
