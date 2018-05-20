package ship.practice;

import java.util.Scanner;


/**
 * Source:
 * http://online-judge.uva.es/p/v100/10000.html
 * 
 * Solution:
 * 	Find the longest path from start to every other node by using a depth
 * 	first search. This could be greatly improved by using a Floyd-Warshall
 * 	alternative that finds the matrix of maximum paths vs. minimum paths.
 * 
 * @author Philip Diffenderfer
 *
 */
public class LongestPaths
{

	/* INPUT
2
1
1 2
0 0
5
3
1 2
3 5
3 1
2 4
4 5
0 0
5
5
5 1
5 2
5 3
5 4
4 1
4 2
0 0
0

	 */
	/* OUTPUT
Case 1: The longest path from 1 has length 1, finishing at 2.

Case 2: The longest path from 3 has length 4, finishing at 5.

Case 3: The longest path from 5 has length 2, finishing at 1.

	 */
	
	public static void main(String[] args) {
		new LongestPaths();
	}
	
	// An adjancency matrix storing the path length between 'p' and 'q'.
	boolean[][] canVisit;
	// The number of friends to visit
	int friends;
	
	LongestPaths() {
		Scanner in = new Scanner(System.in);
		
		friends = in.nextInt();
		int cases = 1;
		
		while (friends != 0) {
			
			canVisit = new boolean[friends][friends];
			
			// Build the adjacency matrix
			int start = in.nextInt() - 1;
			int p = in.nextInt() - 1;
			int q = in.nextInt() - 1;
			while (!(p == -1 && q == -1)) {
				
				canVisit[p][q] = true;
				
				p = in.nextInt() - 1;
				q = in.nextInt() - 1;
			}
			
			// Keep track of the end point and length of the longest path.
			int longestIndex = -1;
			int longest = 0;
			// Get the length from start to every other node.
			for (int i = 0; i < friends; i++) {
				// Skip itself
				if (i == start) continue;
				// Determine longest path (nodes visited - 1)
				int pathLength = longestPath(start, i) - 1;
				// Is it longer?
				if (pathLength > longest) {
					longest = pathLength;
					longestIndex = i;
				}			
			}
			
			// Output
			System.out.format("Case %d: The longest path from %d has length %d, finishing at %d.\n\n", 
					cases, start + 1, longest, longestIndex + 1);

			friends = in.nextInt();
			cases++;
		}

	}
	
	// Recusively find the longest path from start to end using depth first search.
	int longestPath(int start, int end) {
		// If the start is the end then its a length of
		if (start == end)
			return 1;
		// Look at all neighbors
		int longestPath = 0;
		for (int i = 0; i < friends; i++) {
			// If we can move from start to i...
			if (canVisit[start][i]) {
				// Calculate the longest path from start to i...
				int length = longestPath(i, end);
				// If the length of the path is the longest so far save it.
				if (length > longestPath) {
					longestPath = length;
				}
			}
		}
		return longestPath + 1;
	}
	
}
