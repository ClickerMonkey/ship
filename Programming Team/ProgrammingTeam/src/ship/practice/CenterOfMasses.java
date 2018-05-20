package ship.practice;

import java.util.Arrays;
import java.util.Scanner;


/**
 * Source:
 * http://online-judge.uva.es/p/v100/10002.html
 * 
 * @author Philip Diffenderfer	
 *
 */
public class CenterOfMasses
{

	//
	public static void main(String[] args) {
		new CenterOfMasses();
	}
	
	//
	CenterOfMasses() {
		Scanner in = new Scanner(System.in);
		
		// Number of points in polygon.
		int points = in.nextInt();
		
		// Stop input when there's a polygon with less then three points.
		while (points > 2) {
			
			// The collection of unordered points
			Polygon poly = new Polygon();
			
			// Add every point from input to the polygon
			for (int i = 0; i < points; i++) {
				poly.add(in.nextInt(), in.nextInt());
			}
			
			// Order the points in the polygon to make it non-intersecting (convex)
			poly.order();
			
			// The area is needed to calculate the center of mass
			double area = poly.area();
			
			// Calculate the center of mass
			double comx = 0.0;
			double comy = 0.0;
			for (int i = 0; i < poly.total; i++) {
				int x1 = poly.x[i];
				int y1 = poly.y[i];
				int x2 = poly.x[i+1];
				int y2 = poly.y[i+1];
				int cross = (x1 * y2 - x2 * y1);
				comx += (x1 + x2) * cross;
				comy += (y1 + y2) * cross;
			}

			comx /= 6.0 * area;
			comy /= 6.0 * area;
			
			System.out.format("%.3f %.3f\n", comx, comy);
			
			points = in.nextInt();
		}
	}
	
	// Returns x restricted between the given min and max. When using inverse trig
	// functions this must be done for [-1.0, 1.0] so the result returned isn't NaN.
	//		clamp(?, 0.0, 1.0)
	double clamp(double x, double min, double max) {
		return (x < min ? min : (x > max ? max : x));
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

	// A 2d Polygon
	class Polygon {
		int[] x;
		int[] y;
		int total;
		// Initializes a new Polygon
		Polygon() {
			x = new int[16];
			y = new int[16];
		}
		// Orders the points in the polygon clockwise starting at point 0.
		void order() {
			// Determine center
			double[] center = center();
			double cx = center[0];
			double cy = center[1];
			
			// Use point0 as start of polygon
			double minAngle = 0.0;
			int minIndex = -1;
			for (int i = 1; i < total - 1; i++) {
				// The point before (centered at origin
				double lastx = x[i - 1] - cx;
				double lasty = y[i - 1] - cy;
				
				// Start with no initial next point
				minIndex = -1;
				minAngle = 0.0;
				
				// Find the point closes (by inner angle) to point [i - 1]
				for (int j = i; j < total; j++) {
					double a = angle(lastx, lasty, x[j] - cx, y[j] - cy);
					if (minIndex == -1 || a < minAngle) {
						minAngle = a;
						minIndex = j;
					}
				}
				
				// Swap i with the next point
				if (i != minIndex) {
					swap(i, minIndex);	
				}
			}
		}
		// Swaps the points at index i and j.
		void swap(int i, int j) {
			int tx = x[i]; x[i] = x[j]; x[j] = tx;
			int ty = y[i]; y[i] = y[j]; y[j] = ty;
		}
		// Adds a point to the polygon connecting the previos point with a line.
		void add(int px, int py) {
			ensureCapacity();
			x[total] = px;
			y[total] = py;
			total++;
		}
		// 'Closes' the polygon by setting the very last point v[total] to the 
		// first point. Calling this twice has no effect. Any points added to the
		// polygon after its close will undo what close does.
		void close() {
			ensureCapacity();
			x[total] = x[0];
			y[total] = y[0];
		}
		// Ensures we have room for atleast one more point.
		void ensureCapacity() {
			if (total == x.length) {
				int newLength = total + (total >> 1);
				x = Arrays.copyOf(x, newLength);
				y = Arrays.copyOf(y, newLength);
			}
		}
		// Computes the area of the polygon
		double area() {
			close();					// Ensures the polygon has been closed
			int doublearea = 0;
			for (int i = 0; i < total; i++) {
				doublearea += (x[i] * y[i + 1]) - (x[i + 1] * y[i]);
			}
			return (doublearea * 0.5);	
		}
		// Returns the center of this polygon as an array {x,y}
		double[] center() {
			double[] c = new double[2];
			for (int i = 0; i < total; i++) {
				c[0] += x[i];
				c[1] += y[i];
			}
			c[0] /= total;
			c[1] /= total;
			return c;
		}
	}

	                  
	
}
