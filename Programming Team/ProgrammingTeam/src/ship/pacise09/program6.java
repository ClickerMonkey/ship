package ship.pacise09;

import java.util.Scanner;
public class program6
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		while (sc.hasNextInt())
		{
			int A = sc.nextInt();
			int B = sc.nextInt();
			int C = sc.nextInt();
			System.out.println((int)Math.ceil((double)(B * C) / (double)(A * A * 2)));
		}
	}
}