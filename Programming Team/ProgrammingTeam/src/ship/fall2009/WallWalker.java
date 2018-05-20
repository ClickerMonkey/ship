package ship.fall2009;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Strategy:
 * 	Point the robot in any direction along an axis and place it at the origin
 * 	For each distance move the robot in the current direction and add that
 * 		robot position to the polygon
 * 	Change the direction of the robot based on R or L, if its X then stop.
 * 
 * 	Use the Polygon.area() method to compute its area.
 * 
 * @author Philip Diffender
 *
 */
public class WallWalker
{

	/* INPUT
20 L 20 L 10 R 40 L 10 L 60 X
15 L 10 R 5 R 20 L 40 L 15 L 20 R 15 L 40 L 20 X
X

	 */

	public static void main(String[] args) {
		new WallWalker();
	}

	WallWalker() {
		Scanner in = new Scanner(System.in);

		int cases = 1;
		String line = in.nextLine();
		while (!line.equals("X")) {

			Scanner scan = new Scanner(line);

			// Start off going on positive x-axis
			int xdir = 1;
			int ydir = 0;

			// Starting point (origin)
			int x = 0;
			int y = 0;

			Polygon p = new Polygon();
			p.add(x, y);

			// Read in the first distance and movement
			int distance = scan.nextInt();
			char move = scan.next().charAt(0);

			// While the movement isn't cancelled...
			while (move != 'X') {

				// Change current position by direction and distance travele
				x += xdir * distance;
				y += ydir * distance;

				// Add corner to polygon
				p.add(x, y);

				// Change direction based on move.
				int olddirx = xdir;
				switch(move) {
					case 'L':	// Turn Left
						xdir = -ydir;
						ydir = olddirx;
						break;
					case 'R':	// Turn Right
						xdir = ydir;
						ydir = -olddirx;
						break;
				}

				// Read in next distance and movement.
				distance = scan.nextInt();
				move = scan.next().charAt(0);
			}

			// Compute the area of the room
			int area = p.area();

			System.out.format("Case %d: %d sq. ft.\n\n", cases, area);
			
			line = in.nextLine();
			cases++;
		}
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
		// Computes the area of
		int area() {
			close();					// Ensures the polygon has been closed
			int doublearea = 0;
			for (int i = 0; i < total; i++) {
				doublearea += (x[i] * y[i + 1]) - (x[i + 1] * y[i]);
			}
			return (doublearea >> 1);	
		}
	}

}
