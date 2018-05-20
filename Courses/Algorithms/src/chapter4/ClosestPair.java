package chapter4;

import java.util.ArrayList;
import java.util.Scanner;


public class ClosestPair
{
	
	public static String INPUT =
		"726 291\n" + 
		"263 305\n" + 
		"340 124\n" + 
		"835 263\n" + 
		"293 290\n" + 
		"451 944\n" + 
		"497 9\n" + 
		"780 554\n" + 
		"744 876\n" + 
		"741 132\n" + 
		"148 455\n" + 
		"543 278\n" + 
		"183 806\n" + 
		"633 421\n" + 
		"339 379\n" + 
		"288 222\n" + 
		"584 820\n" + 
		"975 123\n" + 
		"746 338\n" + 
		"183 27\n" + 
		"410 239\n" + 
		"177 323\n" + 
		"149 111\n" + 
		"992 243\n" + 
		"716 944\n" + 
		"40 591\n" + 
		"346 139\n" + 
		"898 464\n" + 
		"454 984\n" + 
		"216 453\n" + 
		"834 665\n" + // CLOSEST POINT 1
		"168 204\n" + 
		"926 287\n" + 
		"568 905\n" + 
		"839 709\n" + // CLOSEST POINT 2
		"583 447\n" + 
		"599 223\n" + 
		"949 543\n" + 
		"529 215\n" + 
		"762 45\n" + 
		"741 9\n" + 
		"171 84\n" + 
		"496 16\n" + 
		"782 388\n" + 
		"570 182\n" + 
		"181 357\n" + 
		"824 447\n" + 
		"727 26\n" + 
		"901 639\n" + 
		"718 37";
	
	
	/**
	 * This will run the divide and conquer algorithm and compare
	 * its efficiency to the brute force algorithm. (solve method)
	 * This will also run X amount of tests against N amount of random
	 * points and check that the results from the divide and conquer
	 * algorithm match the brute forces algorithm's to prove that
	 * it works properly!
	 */
	public static void main(String[] args)
	{
		// This tests data sets with 1000 points 10 times each.
		testAlgorithms(1000, 100, 0.0, 1000.0);

		// This test the list of points from above.
//		solve(new Scanner(INPUT));
	}
	
	/**
	 * This will test the divide and conquer algorithm against
	 * the brute force. The data set will be 'maxPoints' large and
	 * 'maxTests' will be run to compare the results from each
	 * algorithm.
	 * 
	 * @param maxPoints => Maximum number of points per test.
	 * @param maxTests => Mazimum number of tests.
	 * @param min => The min x/y axis bounds where tests will be generated.
	 * @param max => the max x/y axis bounds where tests will be generated.
	 */
	public static void testAlgorithms(int maxPoints, int maxTests, double min, double max)
	{
		// Create and initialize each point at 0;
		Point[] points = new Point[maxPoints];
		for (int i = 0; i < maxPoints; i++)
			points[i] = new Point();

		// Create the closest pair solver
		ClosestPair closest = new ClosestPair();
		closest.setPoints(points);
		
		int times = 0;
		double distanceDAC = 0.0;
		long totalDAC = 0;
		long minDAC = Long.MAX_VALUE;
		long maxDAC = Long.MIN_VALUE;
		double distanceBF = 0.0;
		long comparisonsBF = 0;
		
		while (distanceDAC == distanceBF && times < maxTests)
		{
			// Reset all points to random areas.
			for (int i = 0; i < maxPoints; i++)
				points[i].random(min, max);

			// Solve using divide and conquer
			distanceDAC = closest.solveDivideAndConquer();
			totalDAC += closest.totalComparisons;
			minDAC = Math.min(minDAC, closest.totalComparisons);
			maxDAC = Math.max(maxDAC, closest.totalComparisons);
			
			// Solve using brute force
			distanceBF = closest.solveBruteForce();
			comparisonsBF = closest.totalComparisons;

			times++;
		}
		
		// If both methods produced different results... problem!
		if (times != maxTests)
		{
			System.out.println("Difference between methods!!!");
			System.out.println("Found on iteration " + times);
			System.out.println("\tBrute Force yielded " + distanceBF);
			System.out.println("\tDivide and Conquer yielded " + distanceDAC);
		}
		else
		{
			// Print out success
			System.out.format("Successful execution a total of %d time(s) with %d points each.\n", maxTests, maxPoints);
			// Results of brute force algorithm
			System.out.println("\tBrute Force:");
			System.out.println("\t\tComparisons: " + comparisonsBF);
			// Results of divide and conquer algorithm
			System.out.println("\tDivide and Conquer:");
			System.out.println("\t\tComparisons Max: " + maxDAC);
			System.out.println("\t\tComparisons Min: " + minDAC);
			
			long comparisonAvg = (long)((double)totalDAC / times);
			System.out.println("\t\tComparisons Avg: " + comparisonAvg);
			// Determine efficiency
			double efficiency = (double)comparisonsBF / (double)comparisonAvg;
			
			if (efficiency < 1.0)
				System.out.format("Brute Force is on average %.1f times more efficient then Divide and Conquer.\n", 1.0 / efficiency);
			else
				System.out.format("Divide and Conquer is on average %.1f times more efficient then brute force.\n", efficiency);
			 
		}
	}
	
	/**
	 * Given the Scanner for input pairs of "x y" doubles
	 * are read from the input stream until EOF is reached.
	 * As soon as every point is added it solves the closest
	 * point problem using divide and conquer.
	 */
	public static void solve(Scanner input)
	{
		ArrayList<Point> points = new ArrayList<Point>();
		double x, y;
		// Read in all the points where each coordinate is
		// delimited by a space.
		while (input.hasNextDouble())
		{
			x = input.nextDouble();
			if (!input.hasNextDouble())
				break;
			y = input.nextDouble();

			points.add(new Point(x, y));
		}
		
		// Create the closest pair solver, get the points from the input,
		// solve it using the divide and conquer algorithm
		ClosestPair solver = new ClosestPair();
		Point[] source = new Point[points.size()];
		source = points.toArray(source);
		solver.setPoints(source);
		// Solve it and get the min distance
		double distance = solver.solveDivideAndConquer();
		// Grab the closest pair.
		Point[] closest = solver.getClosestPair();
		
		System.out.println("The closest points found were:");
		System.out.println("\t" + closest[0]);
		System.out.println("\t" + closest[1]);
		System.out.println("They were separated by " + distance);
	}
	
	// The array of points to calculate the closest pair.
	private Point[] points;
	private Point[] closest;
	
	// Statistics
	private int totalRecursions;
	private int totalComparisons;
	
	
	/**
	 * Initializes a ClosestPair solver.
	 */
	public ClosestPair()
	{
	}
	
	/**
	 * Sets the points to solve the problem against.
	 */
	public void setPoints(Point[] p)
	{  
		points = p;
	}
	
	/**
	 * This solves using the brute force method and then
	 * returns the minimum distance found. This will also
	 * calculate how many total comparisons (distance
	 * calculations) took place.
	 */
	public double solveBruteForce()
	{
		int total = points.length;
		double distanceSq = Double.MAX_VALUE;
		
		
		for (int i = 0; i < total - 1; i++)
			for (int j = i + 1; j < total; j++)
				distanceSq = Math.min(distanceSq, points[j].distance(points[i]));
		
		// Sequence: x(1)=0,x(2)=1,x(3)=3,x(4)=6,x(5)=10,x(6)=15
		// Formula = N(N-1)/2
		totalComparisons = total * (total  - 1) / 2;
		
		return Math.sqrt(distanceSq);
	}
	
	/**
	 * This solves using the divide and conquer version
	 * and returns the minimum distance found. This will
	 * also calculate how many total comparisons (distance
	 * calculations) took place as well as how many times
	 * the solution broke down into 1 or 0 sized subsets.
	 */
	public double solveDivideAndConquer()
	{
		// Initialize Data
		totalRecursions = 0;
		totalComparisons = 0;

		// Sort points by x values
		Point space[] = new Point[points.length];
		mergesort(space, 0, points.length);
		
		// Create the list of points
		PointList list = new PointList(points);
		// Get the minimum distance (squared) between
		// all the points.
		PointList solution = divideAndConquer(list);
		
		return Math.sqrt(solution.getDelta());
	}
	
	/**
	 * Uses the divide and conquer algorithm to get the closest pair.
	 */
	public Point[] getClosestPair()
	{
		return closest;
	}
	
	/**
	 * Performs the divide and conquer algorithm for solving
	 * the closest pair problem given a list of points. This
	 * returns the distance (squared).
	 */
	public PointList divideAndConquer(PointList list)
	{
		// If zero/one point is contained in this subset then
		// return a value that forces the checking of this point
		if (list.getSize() <= 1)
		{
			// Increment total recursions statistics
			totalRecursions++;
			// Set the lists delta to max possible value
			list.setDelta(Double.MAX_VALUE);
		}
		// Handle 2 or more points
		else
		{
			// Split it down the middle
			int middle = (int)Math.ceil(list.getSize() / 2);
			// Create the two separate 'lists' splitting them down the middle
			// and calling the divide and conquer function recursively.
			PointList left  = list.split(0, middle - 1 );
			PointList right = list.split(middle, list.getSize() - 1);
			
			double leftDelta = divideAndConquer(left).getDelta();
			double rightDelta = divideAndConquer(right).getDelta();
			
			// Set the delta for the current list
			list.setDelta(Math.min(leftDelta, rightDelta));
			
			// Determine the average x of the list
			double average = list.get(middle - 1).x;
			
			Point current;
			// Merge the left and right side now based on the average
			for (int i = 0; i < list.getSize(); i++)
			{
				current = list.get(i);
				// If the current point is within the delta threshold
				// then test it against the left or right side
				if (Math.abs(current.x - average) <= list.getDelta())
				{
					// If current is on the left side, check it against the right
					if (i < middle)
						compareSide(list, current, right);
					// If current is on the right side, check it against the left
					else // (i >= middle)
						compareSide(list, current, left);
				}
			}
		}
		return list;
	}
	
	/**
	 * Given a PointList 'set' where all points are contained
	 * we test the Point 'current' against being within a specified
	 * area of any point in the PointList 'subset'.
	 */
	public void compareSide(PointList set, Point current, PointList subset)
	{
		// Iterate through this sides points and compare them
		// to the current points bounding boxes. If one is contained
		// in the current points box then calculate the distance
		// and update the delta and the closest points in this sub problem.
		Point next;
		for (int j = 0; j < subset.getSize(); j++)
		{
			next = subset.get(j);
			// If they are one in the same then continue to test against others.
			if (next.isEqual(current))
				continue;
			
			// If the next point is in currents's "bounding box".
			if (inBoundingBox(current, next, set.getDelta()))
			{
				double dist = current.distance(next);
				
				if (dist < set.getDelta())
				{
					set.setDelta(dist);
					set.setClosestPair(current, next);
					// Set the closest for the solution
					closest = set.getClosestPair();
				}
				totalComparisons++;
			}
		}
	}
	
	/**
	 * Determines if points 'a' and 'b' are within atleast
	 * 'delta' of each other.
	 */
	private boolean inBoundingBox(Point a, Point b, double delta)
	{
		return (Math.abs(a.x - b.x) <= delta && Math.abs(a.y - b.y) <= delta);
	}
	
	/**
	 * Performs a merge sort on the points provided
	 * in this closest pair solver.
	 */
	private void mergesort(Point[] space, int low, int high)
	{
		if (high - low <= 1)
			return;
		
		int middle = low + ((high - low) >> 1);
		mergesort(space, low, middle);
		mergesort(space, middle, high);
		
		merge(space, low, middle, high);
	}
	
	/**
	 * Performs the merge (sorting) of the left and right sides.
	 */
	private void merge(Point[] space, int low, int middle, int high)
	{
		int i = low, j = middle;
		
		for (int k = low; k < high; k++) 
		{
			if (i == middle)
				space[k] = points[j++];
			else if (j == high)
				space[k] = points[i++];
			else if (points[j].x < points[i].x)
				space[k] = points[j++];
			else
				space[k] = points[i++];
		}
		
		for (int k = low; k < high; k++)
			points[k] = space[k];
	}
	
}
