package ship.dickinson09;

import java.util.Scanner;


public class pig 
{

	public static void main(String[] args)
	{
		new pig();
	}
	
	public pig()
	{
		Scanner sc = new Scanner(System.in);
		
		int score;
		
		while (sc.hasNextInt() && (score = sc.nextInt()) >= 0)
		{
			if (score == 0)
				System.out.println("0.625");
			else if (score == 1)
				System.out.println("0.000");
			else
			{
				int max = (int)Math.floor(score * 0.5);
				int perm[] = new int[max];
				int DONE = max * 6;
				int total = max * 2;
				
				// Setup the permutation
				for (int i = 0; i < max; i++)
					perm[i] = 2;
				// Run each permutation
				while (total != DONE)
				{
					total = 0;
					for (int i = 0; i < max; i++)
						total += perm[i];
					// Increase the last one
					
				}
			}
		}
	}
	
	// Returns -1 if it isn't, else how many nums are used to calculate the size
	public int isExact(int[] perm, int score)
	{
		int total = 0;
		for (int i = 0; i < perm.length; i++)
		{	
			total += perm[i];
			
			if (score == total)
				return i;
			if (total > score)
				return -1;
		}
		return -1;
	}
	
}
