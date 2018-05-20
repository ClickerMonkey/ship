package ship.practice;
import java.util.Scanner;

/**
 * Source:
 * http://online-judge.uva.es/p/v100/10038.html
 * 
 * @author Philip Diffenderfer
 *
 */
public class JollyJumpers
{

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		while (sc.hasNextLine())
			System.out.println(isJollyJumper(sc.nextLine()));
	}

	/**
	 * Returns whether an input line is 'Jolly' or 'Not jolly'.
	 * 
	 * @param line =>
	 *           A line of space delimited numbers taken from the input.
	 * @return 'Jolly' if the sequence fits the definition, 'Not jolly' if it
	 *         doesn't.
	 */
	public static String isJollyJumper(String line)
	{
		String nums[] = line.split(" ");
		int n = Integer.parseInt(nums[0]);
		boolean sequence[] = new boolean[n - 1];
		// Find the absolute difference between successive elements and mark it as
		// existing (true)
		for (int i = 0; i < n - 1; i++)
		{
			int diff = Math.abs(Integer.parseInt(nums[i + 2]) - Integer.parseInt(nums[i + 1]));
			
			if (diff > n - 1 || diff == 0)
				return "Not jolly";
			
			sequence[diff - 1] = true;
		}
		// If any difference is not true, then there's a missing difference,
		// return false
		for (int i = 0; i < n - 1; i++)
			if (!sequence[i])
				return "Not jolly";
		// Every number from 1 to n - 1 was covered, return true.
		return "Jolly";
	}

}
