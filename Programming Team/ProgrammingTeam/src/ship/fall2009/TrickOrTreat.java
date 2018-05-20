package ship.fall2009;

import java.util.Arrays;
import java.util.Scanner;

public class TrickOrTreat
{
	
	/* INPUT
6 L 6 L 6 L 6 X
5 L 1 R 1 L 5 L 6 L 6 X
2 L 2 L 1 R 4 L 1 L 6 X
3 L 2 R 1 R 4 L 8 L 3 L 4 R 3 L 8 L 4 X
X

	 */
	public static void main(String[] args) {
		new TrickOrTreat();
	}

	TrickOrTreat() {
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

				// Change current position by direction and distance traveled (x2)
				x += xdir * (distance * 2);
				y += ydir * (distance * 2);

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

			// Get the bounds (LTRB) of the polygon
			int left = p.getMin(p.x);
			int right = p.getMax(p.x);
			int top = p.getMax(p.y);
			int bottom = p.getMin(p.y);
			
			// Calculate dimensions of floor layout in characters
			int width = right - left + 1;
			int height = top - bottom + 1;
			
			// Translate so all points on the polygon are non-negative.
			p.translate(-left, -bottom);
			
			// Create the floor plans
			char[][] map = new char[height][width];
			
			// Fill it with spaces.
			for (int row = 0; row < height; row++) {
				for (int col = 0; col < width; col++) {
					map[row][col] = ' ';
				}
			}
			
			// Do every wall in the polygon
			for (int i = 0; i < p.total; i++) {
				// Start and end points in the wall
				int sx = p.x[i];
				int sy = p.y[i];
				int ex = p.x[i+1];
				int ey = p.y[i+1];
				
				// Direction unit vector the robot traveled from start to end
				int dirx = sign(ex - sx);
				int diry = sign(ey - sy);
				
				// The wall character to use (horizontal or vertical)
				char wall = (dirx == 0 ? '|' : '-');
				
				// While we haven't met the end point...
				while (!(sx == ex && sy == ey)) {
					// Set the wall and move to the next point on the wall
					map[sy][sx] = wall;
					sx += dirx;
					sy += diry;
				}
			}
			
			// Do every corner in the polygon
			for (int i = 0; i < p.total; i++) {
				map[p.y[i]][p.x[i]] = '+';
			}

			// Print out the floor plans
			System.out.format("Case %d:\n", cases);
			for (int row = 0; row < height; row++) {
				System.out.println(map[row]);
			}
			System.out.println();
			
		
			line = in.nextLine();
			cases++;
		}
	}

	// Returns the sign of a (-1,1) or 0 if a is 0.
	int sign(int a) {
		return (a < 0 ? -1 : (a > 0 ? 1 : 0));
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
		// Gets the minimum value in the array.
		int getMin(int[] arr) {
			int min = arr[0];
			for (int i = 1; i < total; i++)
				min = Math.min(min, arr[i]);
			return min;
		}
		// Gets the leftmost edge of the polygon.
		int getMax(int[] arr) {
			int max = arr[0];
			for (int i = 1; i < total; i++)
				max = Math.max(max, arr[i]);
			return max;
		}
		// Translates the polygon by tx and ty
		void translate(int tx, int ty) {
			close();
			for (int i = 0; i <= total; i++) {
				x[i] += tx;
				y[i] += ty;
			}
		}
	}

}
