package ship.practice;

import java.util.*;

/**
 * Source:
 * http://online-judge.uva.es/p/v100/10003.html
 * 
 * Solution:
 * 	Work backwards starting with all the pieces already cut. To uncut a piece
 * 	find the pair of adjacent cuts with the shortest length of all the cuts.
 * 	With each pair of cuts combine it to a single cut and add it back into
 * 	the list of cuts, until there is only one cut (board) left. The minimal
 * 	cost is the sum of the lengths of all the cuts combined.
 * 
 * @author Philip Diffenderfer
 *
 */
public class CuttingSticks 
{

	public static void main(String[] args) {
		new CuttingSticks();
	}
	
	// The list of board lengths
	ArrayList<Integer> boards;
	
	CuttingSticks() {
		Scanner in = new Scanner(System.in);
		
		// The length of the board
		int length = in.nextInt();
		
		while (length != 0) {
			
			// How many cuts to the board?
			int totalCuts = in.nextInt();
			
			// The position of each cut (and the start and end of the board)
			int[] cuts = new int[totalCuts + 2];
			
			for (int i = 1; i <= totalCuts; i++) {
				cuts[i] = in.nextInt();
			}
			cuts[0] = 0;
			cuts[totalCuts + 1] = length;
			
			int cost = getMinimumCost(length, cuts);
			System.out.format("The minimum cost is %d.\n", cost);
			
			length = in.nextInt();
		}
	}
	
	// Solves a boards minimum cost at a certain length with certain cuts
	public int getMinimumCost(int length, int[] cuts) {
		// Initialize empty array of boards
		boards = new ArrayList<Integer>();
		
		// Add each board cut to the list.
		for (int i = 1; i < cuts.length; i++) {
			boards.add(cuts[i] - cuts[i - 1]);
		}
		
		int cost = 0;
		
		// While there are boards we can combine...
		while (boards.size() != 1) {
			
			// The first index of the pair of boards with the smallest combined length
			int minIndex = indexOfShortestPair();
			// Remove both boards from the list and get the length.
			int minLength = boards.remove(minIndex) + boards.remove(minIndex);
			
			// Insert the combined cuts as a single board back into the array.
			boards.add(minIndex, minLength);
			
			// Add the length of the board to the cost.
			cost += minLength;
		}
		
		// The minimal cost computed!
		return cost;
	}
	
	// Returns the index of the shortest pair of adjacent boards from the list
	// of board lengths. The index returned is the index of the left board.
	int indexOfShortestPair() {
		
		int index = -1;			// The index of the shortest pair.
		int shortest = 100000;	// The length of the shortest pair.

		// Start with the last boards and work towards the first
		for (int i = boards.size() - 1; i > 0; i--) {
			
			// The length of this pair.
			int length = boards.get(i) + boards.get(i - 1);
			
			// If the pair length is shorter then the current minimum...
			if (length < shortest) {
				// Update the index of the shortest pair
				shortest = length; 
				index = i - 1;
			}
		}
		// Return the index of the shortest pair
		return index;
	}
}