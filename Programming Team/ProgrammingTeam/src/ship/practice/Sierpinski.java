package ship.practice;

import java.util.Scanner;


public class Sierpinski
{
	
	public static void main(String[] args) {
		new Sierpinski();
	}

	double areaScale = Math.sqrt(3.0) * 0.25;
	
	Sierpinski() {
		Scanner in = new Scanner(System.in);
		
		int cases = in.nextInt();
		
		while (--cases >= 0) {
			int iterations = in.nextInt();
			double base = in.nextDouble();
			
			double area = triangleArea(base);
			
			while (--iterations >= 0) {
				area *= 0.75;
			}
			
			System.out.format("%.6f\n", area);
		}
	}
	
	double triangleArea(double base) {
		return (base * base) * areaScale;
	}
	
}
