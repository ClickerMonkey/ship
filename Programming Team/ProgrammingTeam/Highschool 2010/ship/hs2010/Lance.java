package ship.hs2010;

import java.util.Scanner;


public class Lance
{

	private static final String FORMAT = "Lance traveled %.2f miles in %.1f seconds at a velocity of %.1f mph.\n";
	
	public static void main(String[] args) {
		new Lance();
	}
	
	public Lance() {
		Scanner input = new Scanner(System.in);
		
		int caseCount = input.nextInt();
		
		while (--caseCount >= 0) {
			
			double time = input.nextDouble();
			int pointCount = input.nextInt() + 1;
			double x[] = new double[pointCount];
			double y[] = new double[pointCount];
			
			x[0] = 0.0;
			y[0] = 0.0;
			
			for (int i = 1; i < pointCount; i++) {
				x[i] = input.nextDouble();
				y[i] = input.nextDouble();
			}
			
			double miles = 0.0;
			
			for (int i = 1; i < pointCount; i++) {
				miles += distance(x[i - 1], y[i - 1], x[i], y[i]);
			}
			
			System.out.format(FORMAT, miles, time, miles / (time / 3600.0));
		}
	}
	
	private double distance(double x1, double y1, double x2, double y2) {
		double dx = x2 - x1;
		double dy = y2 - y1;
		return Math.sqrt(dx * dx + dy * dy);
	}
	
}
