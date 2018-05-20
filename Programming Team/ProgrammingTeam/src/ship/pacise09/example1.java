package ship.pacise09;

import java.util.Scanner;
public class example1
{
	public static void main(String[] args)
	{
		final double EPSILON = 0.0000001;
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext())
		{
			double s1 = sc.nextDouble();
			double s2 = sc.nextDouble();
			double s3 = sc.nextDouble();
			double g3 = Math.sqrt(s1 * s1 + s2 * s2);
			
			if (Math.abs(g3 - s3) < EPSILON)
				System.out.println("right");
			else if (g3 < s3)
				System.out.println("obtuse");
			else
				System.out.println("acute");
				
		}
	}
}