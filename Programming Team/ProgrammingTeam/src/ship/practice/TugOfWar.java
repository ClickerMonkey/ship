package ship.practice;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Source:
 * http://online-judge.uva.es/p/v100/10032.html
 * 
 * This is the solution to Tug of war were N cases occur where
 * 	each case has M people with a weight per person. To find the
 * 	best combination of almost equal groups we start out with
 * 	randomly placed people and then while the difference in weight
 * 	between the 2 groups is greater then the lightest person we
 * 	grab the person in the heavier group closest to half the
 * 	difference and remove it from the heavier and add it to the
 * 	lightest. Eventually the solution will be met and in O(log n)
 *
 *Example Input:
 *	1
 *
 *	6
 *	12
 *	23
 *	32
 *	41
 *	56
 *	108
 *Output:
 *	132 140
 *
 *Example Input:
 *	1
 *
 *	3
 *	100
 *	90
 *	200
 *Output:
 *	190 200
 *
 * @author Philip Diffenderfer
 * 
 */
public class TugOfWar 
{
	public static void main(String[]args)
	{
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		
		// For each case there is a blank line, then a number
		//	for how many people 'n' followed by 'n' weights
		//	per person.
		for (int i = 0; i < cases; i++)
		{
			sc.nextLine();
			int people = sc.nextInt();
			
			// Split them up into 2 groups so they can swap
			ArrayList<Integer> ar1 = new ArrayList<Integer>();
			ArrayList<Integer> ar2 = new ArrayList<Integer>();
			int smallest = Integer.MAX_VALUE;
			
			// Read in the weights and divide them up, also
			//	keeping track of the smallest number.
			for (int j = 0; j < people; j++)
			{
				int weight = sc.nextInt();
				if (weight < smallest)
					smallest = weight;
				if (j < people / 2)
					ar1.add(weight);
				else
					ar2.add(weight);
			}
			
			// Find the initial total and absolute difference
			//	between the 2 groups.
			int total1 = total(ar1);
			int total2 = total(ar2);
			int diff = Math.abs(total1 - total2);
			// These are to use the groups with the most and
			//	least weight
			ArrayList<Integer> high, low;
			
			// While the difference in weight between the
			//	2 groups is greater then the smallest 
			//	person optimize the group
			while (diff > smallest)
			{
				// Sort out which one has the most and least
				high = (total1 > total2 ? ar1 : ar2);
				low = (total1 < total2 ? ar1 : ar2);
								
				// Find the one closest to half the difference
				//	by looping through the heavier group and
				// 	finding the person thats closest to half
				//	the difference between the weight of the 
				//	2 groups
				int minIndex = 0;
				int minValue = Integer.MAX_VALUE;
				int value;
				int half = (int)(diff * 0.5);
				
				for (int k = 0; k < high.size(); k++)
				{
					// The absolute difference
					value = Math.abs(high.get(k) - half);
					// If the difference is less then the current
					//	minimum value keep track of the index of
					//	the new closest person
					if (value < minValue)
					{
						minIndex = k;
						minValue = value;
					}
				}
				
				// Remove the closest from the high and put it in the low
				int closest = high.remove(minIndex);
				low.add(closest);
				
				// Recalculate the total weight for each group and its
				//	absolute difference.
				total1 = total(ar1);
				total2 = total(ar2);
				diff = Math.abs(total1 - total2);
			}
			
			// Print out the results from smallest to largest
			int min = Math.min(total1, total2);
			int max = Math.max(total1, total2);
			System.out.println(min + " " + max);
			
		}
		
	}
	
	/**
	 * Find the total of the array list of integers
	 */
	public static int total(ArrayList<Integer> ar)
	{
		int total = 0;
		for (int i = 0; i < ar.size(); i++)
			total += ar.get(i).intValue();
		return total;
	}
	
}
