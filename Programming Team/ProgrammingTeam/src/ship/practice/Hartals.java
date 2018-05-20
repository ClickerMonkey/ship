package ship.practice;
import java.util.Scanner;

/**
 * Source:
 * http://uva.onlinejudge.org/external/100/10050.html
 * 
 * @author Philip Diffenderfer
 *
 */
public class Hartals {

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		// Read each case
		for (int i = 0; i < cases; i++)
		{
			// First number in the case is the time period in days measured.
			int days = sc.nextInt();
			// Second number is the number of political parties in bangladesh.
			int parties = sc.nextInt();
			int total = 0;
			// For every party calculate how many days arent on a friday or saturday.
			for (int j = 0; j < parties; j++)
			{
				// The time interval in days
				int hartal = sc.nextInt();
				int m = 0;
				// Start off with the first day, increase by the time interval, 
				//		and loop until we're up to the total time period (days)
				for (int n = hartal; n <= days; n += hartal)
				{
					// This is which number week
					m = (int)Math.floor(n / 7.0);
					// This is the index for friday and saturday
					int fri = m * 7 + 5;
					int sat = fri + 1;
					// If its not a saturday or friday increase the total hartal days.
					if (n != sat && n != fri)
						total++;
				}
			}
			// Print out the results for the case.
			System.out.println(total);
		}
	}

}
