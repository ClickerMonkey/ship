package ship.dickinson10;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Almost solves the 'stars' problem. Two galaxies are given as a set of points.
 * Each galaxy has an outer boundary of stars (convex hull). You must see if the
 * galaxies collide (any star in one galaxy exists in another, a star directly
 * on the boundary counts as well). We have a problem with stars on edges and
 * also a special input case like so:
 *    +----+
 *    |    |
 * +----------+
 * |  |    |  |
 * +----------+
 *    |    |
 *    +----+
 * Didn't have enough time to solve, 5 more minutes was all we needed!
 *    
 * @author Philip Diffenderfer
 */
public class stars {

	/*
4
1.5,1.0
1.0,2.0
2.5,2.5
2.5,1.0
3
3.0,3.0
3.0,4.0
4.0,3.0


6
151.0,200.0
151.5,202.5
152.0,202.0
152.0,201.0
153.0,202.0
153.0,200.5
4
152.5,201.0
152.5,203.5
154.5,202.0
154.0,201.5

===P1===
4
8,2
4,6
9,11
13,7

===P2===
4
10,4
10,2
13,2
13,4

===P3===
4
3,2
3,4
6,4
6,2

===P4===
3
4,9
4,12
7,9

===P5===
4
11,9
13,8
16,11
15,12

	 */

	public static void main(String[] args) {
		new stars();
	}
	
	stars() {
		// User whitespace and commas as delimiters
		Scanner in = new Scanner(System.in);
		in.useDelimiter("[,\\s]");
		
		// Read in the first galaxy
		Polygon p1 = new Polygon(in.nextInt());
		for (int i = 0; i < p1.total; i++) {
			p1.points[i] = new Point();
			p1.points[i].x = in.nextDouble();
			p1.points[i].y = in.nextDouble();
		}
		
		// Read in the second galaxy
		Polygon p2 = new Polygon(in.nextInt());
		for (int i = 0; i < p2.total; i++) {
			p2.points[i] = new Point();
			p2.points[i].x = in.nextDouble();
			p2.points[i].y = in.nextDouble();
		}

		// Hullify both galaxies. All edge points are added to their respective
		// edgeNodes list.
		p1.hullify();
		p2.hullify();
		
		// Do they intersect
		boolean intersects = false;
		
		// If any edge point in p1 exists in p2 (not accurate intersection)
		for (Point edge : p1.edgePoints) {
			if (p2.contains(edge)) {
				intersects = true;
				break;
			}
		}
		
		// If the first intersection failed...
		if (!intersects) {
			// If any edge point in p2 exists in p1...
			for (Point edge : p2.edgePoints) {
				if (p1.contains(edge)) {
					intersects = true;
					break;
				}
			}
		}
		
		// Do the boundaries intersect?
		if (intersects) {
			System.out.println("The galaxies have collided.");
		}
		else {
			System.out.println("The galaxies have not collided.");
		}
	}

// A single star
class Point {
	double x, y;
}

// A galaxy of stars
class Polygon {
	int total;
	Point[] points;
	ArrayList<Point> edgePoints;
	// A galaxy has a predetermined set of stars
	Polygon(int total) {
		this.total = total;
		this.points = new Point[total + 1];
		this.edgePoints = new ArrayList<Point>();
	}
	// Builds the convex hull of this galaxy. Adds all boundary points into
	// edgePoints. This is presumably correct.
	void hullify() {
		if (total <= 3) {
			close();
			return;
		}
		// Start with the lowest star in the galaxy.
		int lowest = lowest();
		// Make sure its the first point
		swap(0, lowest);
		// Wrap the first point and last point together
		close();
		// Add the lowest point to the edge
		edgePoints.add(points[0]);
		int start = 0;
		// While not all stars have been traversed.
		while (start < total) {
			// The index of the next boundary star after the current (start)
			int next = getNext(start);
			// If the index is the first boundary point then return
			if (next == total) {
				break;
			}
			// Add this point as a boundary
			edgePoints.add(points[next]);
			// Swap it out so its not examined as another boundary point
			swap(start, next);
			// Next please!
			start++;
		}
	}
	// Swap the stars at i and j.
	void swap(int i, int j) {
		Point temp = points[i];
		points[i] = points[j];
		points[j] = temp;
	}
	// Return the index of the lowest (and leftmost) star
	int lowest() {
		int lowest = 0;
		for (int i = 1; i < total; i++) {
			Point low = points[lowest];
			Point cur = points[i];
			if (cur.x < low.x || (low.x == cur.x && cur.y < low.y)) {
				lowest = i;
			}
		}
		return lowest;
	}
	// Given the index of a star, find the next star in the convex hull (this
	// is the star which is the most clockwise from the given star)
	int getNext(int start) {
		// The index of the next star
		int end = start + 1;
		// For every point remaining in the polygon (including first)
		for (int next = end + 1; next <= total; next++) {
			// If this configuration proves to be more clockwise then
			// the current point (end) then use it.
			if (!ccw(points[start], points[end], points[next])) {
				end = next;
			}
		}
		return end;
	}
	// Returns true if hands a->b and c->b are counter-clockwise.
	boolean ccw(Point a, Point b, Point c) {
		double k = (a.x*b.y + c.x*a.y + b.x*c.y - c.x*b.y - b.x*a.y - a.x*c.y);
		return (k == 0.0);
	}
	// Returns true if this galaxy contains the given star.
	boolean contains(Point p) {
		boolean contains = false;
		for (int i = 0; i < total; i++) {
			Point s = points[i];
			Point e = points[i + 1];
//				if (s == null) System.out.println("s:" + i);
//				if (e == null) System.out.println("e:" + (i + 1));
			boolean inRange = (s.y < p.y) != (e.y > p.y);
			boolean rayCross = rayIntersects(p.x, p.y, s.x, s.y, e.x, e.y);
			if (inRange && rayCross) {
				contains = !contains;
			}
			// If the star is straight up on the current line then return true.
			if (onLine(s, e, p)) {
				return true;
			}
		}
		return contains;
	}
	// Typical ray intersection for polygon containment algorithm.
	boolean rayIntersects(double rayx, double rayy, double x1, double y1, double x2, double y2) {
		double dx = x1 - x2;
		double dy = y1 - y2;
		double py = rayy - y2;
		double px = (dx * py) / dy + x2;
		return (rayx < px);
	} 
	// Returns true if the point p lies on the line segment {start,end}
	boolean onLine(Point start, Point end, Point p) { 
		double dx = end.x - start.x;
		double dy = end.y - start.y;
		double sq = dx * dx + dy * dy;
		double delta = ((dy * (p.y - start.y)) - dx * (p.x - start.x)) / sq;
		if (delta > 1.0) delta = 1.0;
		if (delta < 0.0) delta = 0.0;
		// The point on the line closest to p.
		double px = dx * delta + start.x;
		double py = dy * delta + start.y;
		// The distance between the point on the line closest to p and p.
		return Math.abs(distance(px, py, p.x, p.y)) < 0.000001;
	}
	// Distance between two points.
	double distance(double x1, double y1, double x2, double y2) {
		double dx = x2 - x1;
		double dy = y2 - y1;
		return Math.sqrt(dx * dx + dy * dy);
	}
	// Close this galaxy up.
	void close() {
		points[total] = points[0];
	}
}

}
