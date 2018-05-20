package ship.practice;

import java.util.Scanner;


public class HowBigIsIt
{

	public static void main(String[] args)
	{
		new HowBigIsIt();
	}
	
	public HowBigIsIt()
	{
		Scanner sc = new Scanner(System.in);
		
		int cases = sc.nextInt();
		double[] circles;
		for (int i = 0; i < cases; i++)
		{
			int count = sc.nextInt();
			circles = new double[count];
			for (int j = 0; j < count; j++)
				circles[j] = sc.nextDouble();
			
			// Solve problem
		}
	}
	
//	private double overlap(double r1, double r2)
//	{
//		double dy = r1 - r2;
//		double d = r1 + r2;
//		double dx = Math.sqrt(d * d - dy * dy);
//		return d - dx;
//	}
	
}
