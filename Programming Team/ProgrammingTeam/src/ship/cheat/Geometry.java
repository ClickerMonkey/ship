package ship.cheat;

import java.util.Arrays;


public class Geometry
{
	static final double EPSILON = 0.000001;
	static final int PLANE_FRONT = 0;
	static final int PLANE_BACK = 1;
	static final int PLANE_TOP = 2;
	static final int PLANE_OVERLAP = 3;
	
	boolean equal(double a, double b) {
		return Math.abs(a - b) < EPSILON;
	}
	
	double clamp(double x, double min, double max) {
		return (x < min ? min : (x > max ? max : x));
	}
	
	class Vector {
		double x, y;
		Vector() {
		}
		Vector(double x, double y) {
			set(x, y);
		}
		void set(double x, double y) {
			this.x = x;
			this.y = y;
		}
		public Rect getBounds() {
			return new Rect(x, y, x, y);
		}
		
		//=====================================================================
		// VECTOR MATH
		//=====================================================================
		
		// Normalizes this Vector (length is 1)
		void normalize() {
			if (x != 0.0 && y != 0.0) {
				double invlength = 1.0 / length();
				x *= invlength;
				y *= invlength;
			}
		}
		// Sets this vector as the unit vector from the origin pointing at vector v.
		// This will also return the distance between the origin and v.
		double unit(Vector origin, Vector v) {
			double dx = v.x - origin.x;
			double dy = v.y - origin.y;
			if (dx == 0.0 && dy == 0.0)
				return 0.0;
			double dist = Math.sqrt(dx * dx + dy * dy);
			double invdist = 1.0 / dist;
			x = dx * invdist;
			y = dy * invdist;
			return dist;
		}
		// Computes the dot product of this vector with the given vector v.
		double dot(Vector v) {
			return (v.x * x + v.y * y);
		}
		// Returns the euclidean distance this point is from the origin
		double length() {
			return Math.sqrt(x * x + y * y);
		}
		// Returns which side the given vector is from this vector. If they're pointing
		// in the same direction 0 is returned. If the given vector is on the left of
		// this vector then it returns 1, if its to the right it returns -1.
		int sign(Vector p) {
			double cross = (y * p.x - x * p.y); 
			return (cross==0 ? 0 : (cross<0 ? -1 : 1));
		}
		// Rotates this vector around the origin by the given angle
		void rotate(double radians) {
			double cos = Math.cos(radians);
			double sin = Math.sin(radians);
			double t = x;
			x = x * cos - y * sin;
			y = y * cos + t * sin;
		}
		// Rotates this vector around the origin given a normalized vector. The angle
		// between the normalized vector and the positive-x-axis is the angle of rotation
		void rotate(Vector normal) {
			double t = x;
			x = x * normal.x - y * normal.y;
			y = y * normal.x + t * normal.y;
		}
		// Reflect a vector/ray across a normal.
		void reflect(Vector normal) {
			double dot = 2 * dot(normal);
			x = x - (normal.x * dot);
			y = y - (normal.y * dot);
		}
	}

	class Line {
		double x1, y1, x2, y2;
		Line (double x1, double y1, double x2, double y2) {
			set(x1, y1, x2, y2);
		}
		Line(Line l) {
			set(l.x1, l.y1, l.x2, l.y2);
		}
		void set(double x1, double y1, double x2, double y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
		double delta(double x, double y) {
			double dx = x2 - x1;
			double dy = y2 - y1;
			double sq = dx * dx + dy * dy;
			return ((x - x1) * dx + (y - y1) * dy) / sq;
		}
		boolean onLine(Vector v) {
			double d = clamp(delta(v.x, v.y), 0.0, 1.0);
			double dx = v.x - (x1 + d * (x2 - x1));
			double dy = v.y - (y1 + d * (y2 - y1));
			return (dx * dx + dy * dy) <= (EPSILON * EPSILON);
		}
		double length() {
			return distance(getStart(), getEnd());
		}

		Vector getStart() {
			return new Vector(x1, y1);
		}
		Vector getEnd() {
			return new Vector(x2, y2);
		}
		Rect getBounds() {
			return new Rect(x1, y1, x2, y2);
		}
	}
	
	class Plane {
		double a, b, c, invlength;
		Plane(Line l) {
			a = (l.y1 - l.y2);
			b = (l.x2 - l.x1);
			c = -(a * l.x1) - (b * l.y1);
			invlength = 1.0 / Math.sqrt(a * a + b * b);
		}
		// Signed distance between given point and plane.
		double distance(double x, double y) {
			return ((a * x) + (b * y) + c) * invlength;
		}
		// Evaluates the given point and returns its relative position to the plane.
		int eval(double x, double y) {
			double e = (a * x) + (b * y) + c;
			if (e > EPSILON)
				return PLANE_FRONT;
			else if (e < -EPSILON)
				return PLANE_BACK;
			return PLANE_TOP;
		}
		// Evaluates the given point and returns its relative position to the plane.
		int eval(Vector v) {
			return eval(v.x, v.y);
		}
		// Evaluates the given line and returns its relative position to the plane.
		int eval(Line l) {
			int s = eval(l.x1, l.y1);
			int e = eval(l.x2, l.y2);
			if (s == e)
				return s;
			else if (s == PLANE_TOP)
				return e;
			else if (e == PLANE_TOP)
				return s;
			return PLANE_OVERLAP;
		}
		Line[] split(Line l) {
			Plane p = new Plane(l);
			double cx = 0, cy = 0;
			double div = (a * p.b) - (b * p.a);
			if (equal(div, 0.0)) {
				if (equal(p.a, 0.0))
					cx = l.x1;
				if (equal(p.b, 0.0))
					cy = l.y1;
				if (equal(a, 0.0))
					cx = -b;
				if (equal(b, 0.0))
					cy = c;
			} else {
				cx = (-c * p.b + b * p.c) / div;
				cy = (-a * p.c + c * p.a) / div;
			}
			int s = eval(l.x1, l.y1);
			int e = eval(l.x2, l.y2);
			Line[] lines = new Line[] {new Line(l), new Line(l)};
			if (s == PLANE_FRONT && e == PLANE_BACK) {
				lines[0].x1 = cx;
				lines[0].y1 = cy;
			}
			return lines;
		}
	}
	
	// A 2d Circle {x, y, radius}
	class Circle {
		double x, y, radius;
		Circle(double x, double y, double radius) {
			set(x, y, radius);
		}
		void set(double x, double y, double radius) {
			this.x = x;
			this.y = y;
			this.radius = radius;
		}
		double area() {
			return Math.PI * radius * radius;
		}
		Rect getBounds() {
			return new Rect(x - radius, y + radius, x + radius, y - radius);
		}
	}
	
	// A 2d Rect {left, top, right, bottom}
	class Rect {
		double left, top, right, bottom;
		// Initializes a new rectangle
		Rect(double left, double top, double right, double bottom) {
			set(left, top, right, bottom);
		}
		// Sets the bounds of this rectangle
		void set(double x0, double y0, double x1, double y1) {
			this.left = Math.min(x0, x1);
			this.top = Math.max(y0, y1);
			this.right = Math.max(x0, x1);
			this.bottom = Math.min(y0, y1);
		}
		// Includes the given point into the bounds of this rectangle
		void include(Vector v) {
			left = Math.min(v.x, left);
			right = Math.max(v.x, right);
			top = Math.max(v.y, top);
			bottom = Math.min(v.y, bottom);
		}
		// Includes the given rectangle into the bounds of this rectangle
		void include(Rect r) {
			left = Math.min(r.left, left);
			right = Math.max(r.right, right);
			top = Math.max(r.top, top);
			bottom = Math.min(r.bottom, bottom);
		}
		// Returns the area of the rectangle
		double area() {
			return (right - left) * (top - bottom);
		}
		// Returns the left side of the rectangle as a line.
		Line getLeft() {
			return new Line(left, bottom, left, top);
		}
		// Returns the top side of the rectangle as a line.
		Line getTop() {
			return new Line(left, top, right, top);
		}
		// Returns the right side of the rectangle as a line.
		Line getRight() {
			return new Line(right, top, right, bottom);
		}
		// Returns the bottom side of the rectangle as a line.
		Line getBottom() {
			return new Line(right, bottom, left, bottom);
		}
	}
	
	// A 2d Polygon
	class Polygon {
		Vector[] v;
		int total;
		// Initializes a new Polygon
		Polygon() {
			v = new Vector[16];
		}
		// Adds a point to the polygon connecting the previos point with a line.
		void add(Vector point) {
			ensureCapacity();
			v[total++] = point;
		}
		// 'Closes' the polygon by setting the very last point v[total] to the 
		// first point. Calling this twice has no effect. Any points added to the
		// polygon after its close will undo what close does.
		void close() {
			ensureCapacity();
			v[total] = v[0];
		}
		// Ensures we have room for atleast one more point.
		void ensureCapacity() {
			if (total == v.length) {
				v = Arrays.copyOf(v, total << 1);
			}
		}
		// Swaps the points at index i and j.
		void swap(int i, int j) {
			Vector t = v[i]; 
			v[i] = v[j]; 
			v[j] = t;
		}
		// Computes the area of
		double area() {
			close();					// Ensures the polygon has been closed
			double total = 0.0;
			for (int i = 0; i < total; i++) {
				total += (v[i].x * v[i + 1].y) - (v[i + 1].x * v[i].y);
			}
			return total * 0.5;	
		}
		// Computes the bounding rectangle of the polygon.
		Rect getBounds() {
			Rect bounds = new Rect(v[0].x, v[0].y, v[1].x, v[1].y);
			for (int i = 2; i < total; i++) {
				bounds.include(v[i]);
			}
			return bounds;
		}
	}
	
	// Intersection between two line segments. Returns null if no intersection 
	// exists.
	Vector intersection(Line a, Line b) {
		double x21 = a.x2 - a.x1;
		double y21 = a.x2 - a.x1;
		double x13 = a.x1 - b.x1;
		double y13 = a.x1 - b.x1;
		double x43 = b.x2 - b.x1;
		double y43 = b.x2 - b.x1;
		
		double denom = (y43 * x21) - (x43 * y21);
		if (equal(denom, 0.0))	// Parallel or coincident
			return null;
		
		double ua = ((x43 * y13) - (y43 * x13)) / denom;
		if (ua < 0.0 || ua > 1.0)	// Intersection point not on a
			return null;
		
		double ub = ((x21 * y13) - (y21 * x13)) / denom;
		if (ub < 0.0 || ub > 1.0)	// Intersection point not on b
			return null;
		
		// Intersectionline exists.
		return new Vector(a.x1 + (x21 * ua), a.y1 + (y21 * ua));
	}
	
	// Returns the shortest/closest line between two lines. If they intersect then
	// both points of the line are equal, if they're parallel or coincident then
	// null is returned.
	Line closestLine(Line a, Line b) {
		double x21 = a.x2 - a.x1;
		double y21 = a.x2 - a.x1;
		double x13 = a.x1 - b.x1;
		double y13 = a.x1 - b.x1;
		double x43 = b.x2 - b.x1;
		double y43 = b.x2 - b.x1;
		
		double denom = (y43 * x21) - (x43 * y21);
		if (equal(denom, 0.0))	// Parallel or coincident
			return null;
		
		double ua = clamp(((x43 * y13) - (y43 * x13)) / denom, 0.0, 1.0);
		double ub = clamp(((x21 * y13) - (y21 * x13)) / denom, 0.0, 1.0);
		
		Line closest = new Line(0.0, 0.0, 0.0, 0.0);
		closest.x1 = a.x1 + (x21 * ua);
		closest.y1 = a.y1 + (y21 * ua);
		closest.x2 = b.x1 + (x43 * ub);
		closest.y2 = b.y1 + (y43 * ub);
		return closest;
	}
	
	// Intersection between plane and line segment. Returns null if no 
	// intersection exists.
	Vector intersection(Plane p, Line l) {
		double dot1 = (p.a * l.x1 + p.b * l.y1);
		double dot2 = (p.a * l.x2 + p.b * l.y2);
		double t = (dot1 + p.c) / (dot1 - dot2);
		if (t < 0.0 || t > 1.0)
			return null;
		double x21 = l.x2 - l.x1;
		double y21 = l.y2 - l.y1;
		return new Vector(l.x1 + (x21 * t), l.y1 + (y21 * t));
	}
 
	// Intersection between two planes. If the planes are parallel or coincident 
	// then null is returned.
	Vector intersection(Plane a, Plane b) {
		// TODO
		return null;
	}
	
	// Returns the intersection line between a plane and a circle. Returns null 
	// if no intersection exists.
	Line intersection(Plane p, Circle c) {
		double d = p.distance(c.x, c.y);
		if (Math.abs(d) >= c.radius)
			return null;
		// TODO
		return null;
	}
	
	// Returns the intersection line between two circles. If the circles don't 
	// intersect or a circle is contained in another then null is returned.
	Line intersection(Circle a, Circle b) {
		double dx = b.x - a.x;
		double dy = b.y - a.y;
		double sq = dx * dx + dy * dy;					// Distance squared between circles
		double rsum = a.radius + b.radius;				// Sum of circles radii
		if (sq > rsum * rsum) {								// Circles do not intersect
			return null;
		}
		double rsub = Math.abs(a.radius - b.radius);	// Difference between circles radius
		if (sq < rsub * rsub) {								// Circles are contained in each other
			return null;
		}
		double d = Math.sqrt(sq);							// Distance between circles
		double invd = 1.0 / d;								// Inverse distance
		double rasq = a.radius * a.radius;				// Circle a radius squared
		double rbsq = b.radius * b.radius;				// Circle b radius squared
		double u = (rasq - rbsq + sq) * 0.5 * invd;	// Distance from circle a to plane of intersection
		double px = a.x + (dx * u * invd);				// Point on plane of intersection on the line (a-b)
		double py = a.y + (dy * u * invd);				
		double h = Math.sqrt(rasq - (u * u));			// Distance from line (a-b) to the intersection points
		double rx = -dy * h * invd;						// Offset from line (a-b) on the intersection plane to the points
		double ry = dx * h * invd;

		// Return the line of intersection
		return new Line(px + rx, py + ry, px - rx, py - ry);
	}

	// Returns the intersection rectangle between two rectangles. If the
	Rect intersection(Rect a, Rect b) {
		double left = Math.max(a.left, b.left);			// Left side of intersection box
		double right = Math.min(a.right, b.right);		// Right side of intersection box
		double top = Math.min(a.top, b.top);				// Top side of intersection box
		double bottom = Math.max(a.bottom, b.bottom);	// Bottom side of intersection box
		if (left > right || bottom > top)					// Signals no intersection
			return null;
		return new Rect(left, top, right, bottom);		// Intersecting box
	}
	
	// Returns the intersection line between a circle and a line.
	Line intersection(Line l, Circle c) {
		double dx = l.x2 - l.x1;
		double dy = l.y2 - l.y1;
		double rsq = c.radius * c.radius;
		double cdot = c.x * c.x + c.y * c.y;
		double cldot = c.x * l.x1 + c.y * l.y1;
		double ldot = l.x1 * l.x1 + l.y1 * l.y1;
		
		double a = dx * dx + dy * dy;
		double b = 2 * (dx * (l.x1 - c.x) + dy * (l.y1 - c.y));
		double d = cdot + ldot - (2 * cldot) - rsq;
		double det = b * b - 4 * a * d;
		if (equal(det, 0.0) || det < 0) {
			return null;
		}
		double sqrt = Math.sqrt(det);
		double den = 1.0 / (2 * a);
		double ua = clamp((-b + sqrt) * den, 0.0, 1.0);
		double ub = clamp((-b - sqrt) * den, 0.0, 1.0);
		
		Line inter = new Line(0.0, 0.0, 0.0, 0.0);
		inter.x1 = l.x1 + (dx * ua);
		inter.y1 = l.y1 + (dy * ua);
		inter.x2 = l.x1 + (dx * ub);
		inter.y2 = l.y1 + (dy * ub);
		return inter;
	}
	
	
	// Plane-Circle intersect test
	boolean intersects(Plane p, Circle c) {
		return Math.abs(p.distance(c.x, c.y)) < c.radius;		// Change to <= to include edge
	}

	// Circle-Point intersect test
	boolean contains(Circle c, Vector v) {
		double dx = v.x - c.x;
		double dy = v.y - c.y;
		return (dx * dx + dy * dy) < (c.radius * c.radius);	// Change to <= to include edge
	}
	
	// Rect-Line intersect test
	boolean intersects(Rect r, Line l) {
		// If either point of the line is inside the rect then intersection.
		if (contains(r, l.getStart()) || contains(r, l.getEnd()))
			return true;
		boolean intersects = // Intersection with any of the sides?
			(intersection(r.getLeft(), l) != null) ||		// Left side and l intersect
			(intersection(r.getTop(), l) != null) ||		// Top side and l intersect
			(intersection(r.getRight(), l) != null) ||	// Right side and l intersect
			(intersection(r.getBottom(), l) != null);		// Bottom side and l intersect
		return intersects;	
	}

	// Circle-Rect intersect test
	boolean intersects(Circle c, Rect r) {
		// Difference between closest point on edge of rectangle and the center of the circle
		double dx = c.x - Math.max(r.left, Math.min(r.right, c.x));	
		double dy = c.y - Math.max(r.bottom, Math.min(r.top, c.y));
		return (dx * dx + dy * dy) < (c.radius * c.radius);	// Change to <= to include edge
	}

	// Rect-Point intersect test
	boolean contains(Rect r, Vector v) {
		return !(v.x <= r.left ||	// Change to < to include edge
				v.x >= r.right ||		// Change to > to include edge
				v.y >= r.top ||		// Change to > to include edge
				v.y <= r.bottom);		// Change to < to include edge 
	}

	// Polygon-Point intersect test
	boolean contains(Polygon p, Vector v) {
		Vector start, end;
		boolean contains = false;
		for (int i = 0; i < p.total; i++) {
			start = p.v[i]; 
			end = p.v[i + 1];
			boolean inRange = (start.y > v.y) != (end.y > v.y);
			boolean rayCross = rayIntersects(v.x, v.y, start.x, start.y, end.x, end.y); 
			if (inRange && rayCross)
				contains = !contains;
		}
		return contains;
	}
	
	// Returns true if the horizontal ray starting at point {rayx, rayy} 
	// intersects the given line at {x1, y1} to {x2, y2}
	boolean rayIntersects(double rayx, double rayy, double x1, double y1, double x2, double y2) {
		double dx = x1 - x2;					// Width of line bounds
		double dy = y1 - y2;					// Height of line bounds
		double py = rayy - y2;				// Distance point is from line end
		double px = (dx * py) / dy + x2;	// The projected x coordinate from the ray to the line
		return (rayx < px);					// Change to <= to include line edge
	}

	
	// Returns the area of a triangle
	double triangleArea(Vector a, Vector b, Vector c) {
		return 0.5 * Math.abs((b.x-a.x)*(c.y-a.y)-(c.x-a.x)*(b.y-a.y));
	}
	
	// Returns true if the point c is on the left side of the line between a and b
	boolean ccw(Vector a, Vector b, Vector c) {
		double k = (a.x*b.y + c.x*a.y + b.x*c.y - c.x*b.y - b.x*a.y - a.x*c.y);
		return k <= 0.0;
	}
	
	// Returns true if the points a, b, and c are in a line
	boolean collinear(Vector a, Vector b, Vector c) {
		double bx = b.x - a.x;
		double by = b.y - a.y;
		double cx = c.x - a.x;
		double cy = c.y - a.y;
		if ( by == 0 || cy == 0 ) { // horizontal
			return ( by == cy );
		}
		if ( bx == 0 || cx == 0 ) { // vertical
			return ( bx == cx );
		}
		return equal(bx / by, cx / cy);
	}
	
	// Returns a delta (0.0->1.0) to a point on the plane (start-end) perpendicular
	// to the given point p. To calculate the point on the plane its:
	// {(ex-sx)*delta+sx,(ey-sy)*delta+sy). If the delta is > 1.0 or < 0.0 its not
	// on the plane.
	double planeDelta(Vector start, Vector end, Vector p) {
		double dx = end.x - start.x;
		double dy = end.y - start.y;
		double sq = dx * dx + dy * dy;
		return (dy * (p.y - start.y) + dx * (p.x - start.x)) / sq;
	}
	
	// Returns the signed distance of a given point p to the given plane (start-end)
	// The distance returned is negative when its on the right side of the plane.
	double planeSignedDistance(Vector start, Vector end, Vector p) {
		double dx = end.x - start.x;
		double dy = end.y - start.y;
		double length = Math.sqrt(dx * dx + dy * dy);
		return (dy * (start.x - p.x) - dx * (start.y - p.y)) / length;
	}
	
	// Returns true if the given point p is in the triangle {a, b, c}
	boolean pointInOnTriangle(Vector p, Vector a, Vector b, Vector c) {
		return !(ccw(a, b, p) || ccw(b, c, p) || ccw(c, a, p));
	}
	
	// Returns the euclidean distance between the given points.
	double distance(Vector a, Vector b) {
		double dx = a.x - b.x;
		double dy = a.y - b.y;
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	// Computes angle between abc and returns it in radians.
	double angle(Vector a, Vector b, Vector c) {
		return angle(a.x - b.x, a.y - b.y, c.x - b.x, c.y - b.y);
	}
	

	// Computes angle between {x1,y1}-{0,0}-{x2,y2} and returns it in radians.
	//		angle(ax-bx, ay-by, cx-bx, cy-by)
	double angle(double x1, double y1, double x2, double y2) {
		// Compute the unit vectors pointing from b to a and c,
		// then use the dot product to compute the inner angle.
		double d1 = 1.0 / Math.sqrt(x1 * x1 + y1 * y1);	// Inverse distance of p1 from origin
		double d2 = 1.0 / Math.sqrt(x2 * x2 + y2 * y2);	// Inverse distance of p2 from origin
		double nx1 = x1 * d1;									// Normalized point 1
		double ny1 = y1 * d1;
		double nx2 = x2 * d2;									// Normalized point 2
		double ny2 = y2 * d2;
		double dot = (nx1 * nx2 + ny1 * ny2);				// Dot product
		double inner = Math.acos(clamp(dot, -1.0, 1.0));// Inner angle in radius
		return inner; 
	}
	
	// Returns the angle in radians of the angle 'opposite' from the side of 
	// length opposite, the length of the hypotenuse must ge given.
	double angleInRightTriangle(double opposite, double hyp) {
		return Math.asin(clamp(opposite / hyp, -1.0, 1.0));
	}
	
	// Given a location of a point, this returns the length of the tangent from
	// a circle at the origin with the given radius.
	//		tangentLength(px-cx, py-cy, cr)
	double tangentLength(double x, double y, double radius) {
		return Math.sqrt((x * x + y * y) - radius * radius);
	}
	
	//====================================================================
	// CONVEX HULL
	//====================================================================
	
	// Breaks down a polygon to a convex hull which contains all points in the
	// given polygon. The ordering of the points in the polygon is clockwise
	// where the first point is the bottom+left most point.
	void convexHull(Polygon p) {
		if (p.total <= 3)									// Anything with less then 4 sides...
			return;											// Don't modify it.
		
		int lowest = getLowestPoint(p);				// Find the bottom+left most point
		p.swap(0, lowest);								// Make the first vector that point
		p.close();											// Make the last vector that point
		
		int start = 0;
		while (start < p.total) {
			int next = getNextPoint(p, start++);	// Get next point in the hull
			if (next == p.total) {						// If the next is the first point in the hull...
				p.total = start;							// Reset the number of points in the polygon
				break;
			}
			p.swap(start, next);							// Swap the current to the next point in the hull
		}
	}
	
	// Returns the index of the left-most and bottom-most point in a polygon.
	// Gets the leftmost and lowest point from all the points.
	int getLowestPoint(Polygon p) {
		// The left+bottom most by default.
		int lowest = 0;
		for (int i = 1; i < p.total; i++) {
			Vector low = p.v[lowest];
			Vector cur = p.v[i];
			// If 'i' is more left (or same x and more bottom) then 'lowest' then update it.
			if (cur.x < low.x || (low.x == cur.x && cur.y < low.y))
				lowest = i;
		}
		return lowest;
	}
	
	// Gets the next point in the polygon from start which is the point most
	// counter-clockwise from the point at the given index.
	// Given the index of a point this will return the index of the next point
	// on the convex hull going clockwise.
	int getNextPoint(Polygon p, int start) {
		// The most counter-clockwise by default
		int end = start + 1;										
		for (int next = end + 1; next <= p.total; next++) {
			// If 'next' is more counter-clockwise then 'end'...
			if (!ccw(p.v[start], p.v[end], p.v[next])) {
				end = next;	
			}
		}
		return end;
	}
	
	
	public Geometry() {
		Polygon p = new Polygon();
		p.add(new Vector(-5, 0));
		p.add(new Vector(10, 0));
		p.add(new Vector(0, 10)); //2
		p.add(new Vector(0, 5));
		p.add(new Vector(0, 0));
		p.add(new Vector(-2, 5));
		p.add(new Vector(3, 6));
		p.add(new Vector(10, 3)); //3
		p.add(new Vector(-10, 7)); //1
		p.add(new Vector(-10, 0)); //0
		p.close();
		
		convexHull(p);
		
		for (int i = 0; i < p.total; i++) {
			System.out.format("(%f, %f)\n", p.v[i].x, p.v[i].y);
		}	
	}
	
	public static void main(String[] args) {
		new Geometry();
	}
	
}
