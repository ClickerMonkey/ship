package ship.practice;
import java.util.Scanner;

/**
 * Source:
 * http://uva.onlinejudge.org/external/101/10180.html
 * 
 * The problem:
 * 	There's a perfectly round tree trunk centered at the
 * 	origin with a given radius. There are 2 people at different 
 * 	coordinates outside of the trunk and they want the shortest 
 * 	rope length so they both are holding an end.
 * 
 * Case1:
 * 	From person1 to person2 a direct line crosses the trunk 
 * 	so the rope must wrap around the tree in the shortest direction.
 * Case2:
 * 	From person1 to person2 if they hold a rope and it doesn't 
 * 	cross the circle then the shortest length of the rope
 * 	is just the distance between the 2 people
 * 
 * Equations:
 * 	Sine rule
 *       A/sin(a) = B/sin(b)		b=invsin( (sin(a)*B) / A)
 *		Pythagorean's Theorem
 *			A*A + B*B = C*C			C=sqrt(A*A + B*B)
 */
public class RopeCrisis
{
	
	public static void main(String[] args) {
		new RopeCrisis();
	}
	
	//
	RopeCrisis()
	{	
		Scanner sc = new Scanner(System.in);
		
		int cases = sc.nextInt();
		
		while (--cases >= 0) {
			double radius = sc.nextDouble();
			double p1x = sc.nextDouble();
			double p1y = sc.nextDouble();
			double p2x = sc.nextDouble();
			double p2y = sc.nextDouble();
			
			double minLength = minRopeLength(radius, p1x, p1y, p2x, p2y);
			
			System.out.format("%.3f\n", minLength);
		}
	}

	// Solves the minRopeLength problem.
	double minRopeLength(double radius, double x1, double y1, double x2, double y2) {
		// Calculate the length of the tangents
		double t1 = tangentLength(x1, y1, radius);
		double t2 = tangentLength(x2, y2, radius);
		
		// Calculate the distances from the center of the circle to the people
		double d1 = distance(x1, y1);
		double d2 = distance(x2, y2);
		
		// Calculate the angle between the center of the circle and the two points.
		double between = angle(x1, y1, x2, y2);
		// If between > 180deg then use the smaller angle on the other side
		if (between > Math.PI) {
			between = (Math.PI * 2.0) - between;
		}
		
		// Calculates the angle between the person, circle, and tangent point
		double a1 = angleInRightTriangle(t1, d1);
		double a2 = angleInRightTriangle(t2, d2);
		
		// If the sum of the tangent angles is greater then the angle between then
		// the rope does not intersect the circle, if they're equal then the rope
		// is tangent to the circle
		if (a1 + a2 >= between) {
			return distance(x2 - x1, y2 - y1);
		}
		
		// Calculate the angle of the arc between the tangent points, and its length
		double arcAngle = between - (a1 + a2);
		double arcLength = arcAngle * radius;
		
		// Return the sum of the arc length and the two tangents.
		return arcLength + t1 + t2;
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
	
	// Returns the distance from the given point to the origin.
	//		distance(x2-x1, y2-y1)
	double distance(double dx, double dy) {
		return Math.sqrt(dx * dx + dy * dy);
	}
	
}
