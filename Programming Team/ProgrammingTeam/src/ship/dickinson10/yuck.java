package ship.dickinson10;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Philip Diffenderfer, Keith Porter
 */
public class yuck {

	/*
	 TEST INPUT
3
5
3.5
8
9
10
13.5
5
6
8
3
7

	 */
	
	public static void main(String[] args) {
		new yuck();
	}
	
	// The minimum distance between two restaurants
	int k;		
	// Array list of possible restaurant locations
	ArrayList<Rest> rests = new ArrayList<Rest>();  
	
	yuck() {
		Scanner in = new Scanner(System.in);
		
		// Minimum distance units between two restaurants
		k = in.nextInt();				
		// Number of restaurants in input
		int restCount = in.nextInt();	
		
		for (int i = 0; i < restCount; i++) {
			// Add empty restaurants to list
			rests.add(new Rest());				
		}
		
		// Read in locations along road
		for (int i = 0; i < restCount; i++) {
			rests.get(i).address = in.nextDouble();
		}
		// Read in expected profit from restaurant.
		for (int i = 0; i < restCount; i++) {
			rests.get(i).profit = in.nextInt();
		}
		
		// Expected profit counter
		int profit = 0;									
		
		/*
		 *  Iterate through the list of restaurants
		 *	Select the best restaurant to open.
		 *  Add the expected profit to the counter
		 *  Remove selected restaurant from the list.
		 *  Also remove all of its neighbors who are too close (within K units exclusive)
		 */
		while (!rests.isEmpty()) {
			// Calulates the net gain by opening a restaurant for ALL restaurants in the list
			calcNet();					
			//	Get the index of the restaurant with the highest net gain
			int maxIndex = getMax();
			
			// Increment profit
			profit += rests.get(maxIndex).profit;  
			
			//Remove the restaurant and it's neighbors who are no longer eligible.
			removeSurrounding(maxIndex);			
		}
		
		//Formatted output.
		System.out.format("Maximum profit: $%d\n", profit);		
	}
	
	
	/**
	 * Calculates the "value" of the restaurant.
	 * The 'cost' of establishing a restaurant at a given location is the sum of
	 * the profits of the restaurants that can not be opened because they are too
	 * close to the restaurant.  
	 * The overall value of opening a restaurant is the profit it is expected to
	 * provide, minus the 'cost' of establishing it
	 */
	void calcNet() {
		// For each restaurant
		for (Rest r : rests)  {		
			// Start with the profit it provides
			r.net = r.profit;
			// All neighbors atleast k units away (exclusive) you must subtract 
			// their profits from the current restaurants net profits.
			for (Rest r2 : rests) {
				if (r == r2) continue;
				if (r.within(r2, k)) {
					r.net -= r2.profit;
				}
			}
		}
	}

	/**
	 * Find the restaurant with the maximum 'net' value
	 */
	int getMax() {
		int max = 0;
		for (int i = 1; i < rests.size(); i++) {
			if (rests.get(i).net > rests.get(max).net) {
				max = i;
			}
		}
		return max;
	}
	
	/**
	 * Remove the restaurant at maxIndex, and all its neighbors that are within k
	 */
	void removeSurrounding(int maxIndex) {
		Rest r = rests.get(maxIndex);
		// Iterate from end of array list towards front. Starting at back must be
		// done if you're deleting from the list while you're traversing it.
		for (int i = rests.size() - 1; i >= 0; i--) {  
			if (r.within(rests.get(i), k)) {
				rests.remove(i);
			}
		}
	}
	
	/**
	 * Hold data for restaurant and calculate whether or not another restaurant 
	 * is too close to establish.
	 */
	class Rest {
		double address;
		int profit;
		int net; 
		// Cannot open a restaurant within k units *EXCLUSIVE*
		boolean within(Rest r, int k) {
			return Math.abs(address - r.address) < k;
		}
	}
	
}
