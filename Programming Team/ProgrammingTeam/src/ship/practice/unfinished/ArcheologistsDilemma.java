package ship.practice.unfinished;
import java.util.Scanner;



public class ArcheologistsDilemma
{

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		// While there's N to read...
		while (sc.hasNextInt())
		{
			// Read in next N and convert it to a string
			//	then calculate how many digits it has.
			int n = sc.nextInt();
			int original = n;
			String s = String.valueOf(n);
			// n = n * 10^digits
			n *= Math.pow(10, s.length());
			if (!solve(n, original))
				System.out.println("no power of 2");
		}
	}
	
	public static boolean solve(int n, int original)
	{
		n *= 10;
		double log2 = Math.log10(n) / Math.log10(2);
		long exp = (long)Math.ceil(log2);
		if (exp >= 63)
			return false;
		long num = (long)Math.pow(2, exp);
		String str = String.valueOf(num);
		if (str.startsWith(String.valueOf(original)))
		{
			System.out.println(exp);
			return true;
		}
		solve(n, original);
		return true;
	}
	
}
