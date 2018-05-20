package ship.practice;
import java.util.Scanner;


/**
 * You are given an M x N matrix where each element has a positive whole number.
 * You start from the left of the matrix on any of the elements and every move you do
 * you go to the next column but you may go up a row or down a row, or stay at the same.
 * Your objective is to get from the left to the right of the matrix in the least costing
 * path where the path is a combination of the value for the elements you travel on.
 * 
 * @author Phil Diffenderfer
 */
public class Walker
{

	public static void main(String[] args)
	{
		new Walker();
	}
	
	public Walker()
	{
		Scanner sc = new Scanner(System.in);
		
		int rows = sc.nextInt();
		int columns = sc.nextInt();
		int[][] e = new int[rows][columns];
		
		// Grab the input matrix
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < columns; c++)
				e[r][c] = sc.nextInt();
		
		// Initialize and fill in the counter array
		int[] lengths = new int[rows];
		for (int i = 0; i < rows; i++)
			lengths[i] = e[i][columns - 1];
		
		// Loop from column 0 to columns - 1
		for (int c = columns - 1; c > 0; c--)
		{
			for (int r = 0; r < rows; r++)
			{
				// For each row compute the next 3 possible values, upper
				// same, and lower. Find the minimum and save the decision
				// into the current element and add the minimum's value into
				// the counter array
				int upper, same, lower;
				upper = (r == 0 ? rows - 1 : r - 1);
				same = r;
				lower = (r + 1) % rows;
				
				int min_v, min;
				int upper_v = e[upper][c - 1];
				int same_v = e[same][c - 1];
				int lower_v = e[lower][c - 1];
				min_v = 	Math.min(same_v, Math.min(upper_v, lower_v));
				min = (min_v == upper_v ? upper : (min_v == same_v ? same : lower));
				
				lengths[r] += min_v;
				e[r][c] = min;
			}
		}
		
		// Find the lowest total in the counter array
		int rowMin = Integer.MAX_VALUE;
		for (int i = 0; i < rows; i++)
			if (lengths[i] < rowMin)
				rowMin = i;

		// Print out the path used to find the minimum
		System.out.print((rowMin + 1) + " ");
		for (int i = 0; i < columns - 1; i++)
			System.out.print((e[rowMin][i] + 1) + " ");
		
		// The minimum path found
		System.out.println(lengths[rowMin]);
	}

}
