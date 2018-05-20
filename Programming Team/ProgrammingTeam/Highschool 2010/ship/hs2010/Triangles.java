package ship.hs2010;

import java.util.Arrays;
import java.util.Scanner;

public class Triangles
{

	public static void main(String[] args) {
		new Triangles();
	}
	
	public Triangles() {
		Scanner input = new Scanner(System.in);
		
		int caseCount = input.nextInt();
		
		while (--caseCount >= 0) {

			double[] sides = new double[3];
			sides[0] = input.nextDouble();
			sides[1] = input.nextDouble();
			sides[2] = input.nextDouble();
			
			// Sort sides in ascending order
			Arrays.sort(sides);

			double a = sides[0];
			double b = sides[1];
			double c = sides[2];
			
			// Check for invalid triangle
			if (c >= a + b) {
				System.out.println("invalid");
			}
			// Check for right trangle
			else if ((a * a) + (b * b) == (c * c)) {
				System.out.println("right");
			}
			// Check for obtuse
			else if ((a * a) + (b * b) > (c * c)) {
				System.out.println("obtuse");
			}
			// Must be acute
			else {
				System.out.println("acute");
			}
		}
	}
	
}
