package ship.dickinson10;

import java.util.Scanner;

/**
 * Starts at every position in a given sequence and sums up subsequent values
 * until the desired sum is met or surpassed.
 * 
 * @author Andrew Marx
 */
public class sumthing {

/*
5

20
60
50
30
40

10
20
40
70
90
110
120
130
140
0


 */
	public static void main(String[] args) 
	{
		int seqLen, seq[], desiredSum, total;
		Scanner in = new Scanner(System.in);
		
		// Get the sequence of numbers.
		seqLen = in.nextInt();
		seq = new int[seqLen];
		for (int i=0; i<seqLen; i++)
			seq[i] = in.nextInt();
		
		boolean found;
		int foundAt ;
		// While a sum is desired (>0)
		while ((desiredSum = in.nextInt()) != 0)
		{
			found = false;
			foundAt = -1;
			// For each value in the sequence...
			for (int i=0; i<seqLen; i++)
			{
				// Current sum
				total = 0;
				// Start at the current position in the sequence, find the sum
				// of a set of letters while the current sum (total) is less then
				// the desired sum.
				for (int c=i; c<seqLen && total<desiredSum; c++)
				{
					total += seq[c];
				}
				// If we got our desired sum....
				if (total == desiredSum)
				{
					// FOUND! at the current position, stop searching.
					found = true;
					foundAt = i;
					break;
				}
			}
			// Print out its position and sum
			if (found)
				System.out.println("The section beginning with index " + foundAt + " has sum = "+desiredSum);
			// Sorry no section exists.
			else
				System.out.println("No section has sum = " + desiredSum);
		}
	}

}
