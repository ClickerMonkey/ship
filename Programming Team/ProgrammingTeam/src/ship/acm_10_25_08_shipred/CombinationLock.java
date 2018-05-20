package ship.acm_10_25_08_shipred;

import java.util.Scanner;

public class CombinationLock
{
	public static int solve(int N, int T1, int T2, int T3)
	{
		int r1 = T1 - T2;
		if (r1 < 0)
			r1 += N;
		int r2 = T3 - T2;
		if (r2 < 0)
			r2 += N;
		return 6 * N - 1 - r1 - r2;
	}

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		boolean happy = true;
		do
		{
			int N = sc.nextInt();
			int T1 = sc.nextInt();
			int T2 = sc.nextInt();
			int T3 = sc.nextInt();
			happy = (N + T1 + T2 + T3 > 0);
			if (happy) System.out.println(solve(N,T1,T2,T3));
		} while (happy);
	}
}
