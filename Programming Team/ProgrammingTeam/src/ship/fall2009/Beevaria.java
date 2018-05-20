package ship.fall2009;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Stack;

/**
 * Strategy:
 * 	For every prisoner generate a set of cell numbers that are visible from
 * 		their cell and not occupied by another prisoner.
 * 	Look at the intersections of the sets, the guards will lie on these
 * 	Mark each intersection with a list of prisoners which can see that cell
 * 	Sort the intersections from largest to smallest
 * 	If any of the intersections see all prisoners then return 1.
 * 	Find the first combination of intersections which cover all prisoners
 * 		More then 1 guard is required, start guard count at 2
 * 		Compare every possible group with size guard count
 * 			If the group (set) covers all prisoners then return guard count
 * 
 *
 * @author Philip Diffenderfer
 *
 */
public class Beevaria
{

	/* INPUT
3
0
5
9
10
12
-1
3
2
4
6
8
10
-1
3
0
1
2
-1
4
0
3
21
24
-1
4
1
6
11
16
26
-1
6
5
10
15
20
25
30
35
40
45
50
55
-1
25
6
324
56
45
90
1204
903
809
3
82
766
650
-1
25
6
324
56
45
90
1204
903
34
809
3
82
43
766
650
679
1002
1111
999
973
897
123
234
456
567
678
789
890
901
-1
7
0
9
11
20
40
43
45
60
80
-1
25
24
48
72
96
120
144
168
192
216
240
264
288
312
336
360
384
408
432
456
480
504
528
552
576
600
624
648
672
696
720
744
768
792
816
840
864
888
912
936
960
984
1008
1032
1056
1080
1104
1128
1152
1176
-1
-1

	 */

	public static void main(String[] args) {
		new Beevaria();
	}

	int size;
	int inner;	// = size - 1
	int row; 	// = size + inner
	int total;	// = row * size

	// The array of cells representing whether a cell is taken or not.
	boolean[] taken;
	// The array of prisoners and which cell they're in.
	int[] prisoners;
	// The number of prisoners
	int prisonerCount;

	Beevaria() {
		Scanner in = new Scanner(System.in);

		size = in.nextInt();
		int cases = 1;
		while (size != -1) {
			inner = size - 1;
			row = size + inner;
			total = row * size;

			taken = new boolean[total];
			prisoners = new int[total];
			prisonerCount = 0;

			// Read in the prisoners add them to the array and taking their cell.
			int cell = in.nextInt();
			while (cell != -1) {
				taken[cell] = true;
				prisoners[prisonerCount++] = cell;
				cell = in.nextInt();
			}

			// For each prisoner generate a set of a cells that are visible by
			// the prisoner and not taken.
			Set[] paths = new Set[prisonerCount];
			// For each prisoner...
			for (int i = 0; i < prisonerCount; i++) {
				paths[i] = getVisible(prisoners[i]);
			}

			int minimum = solve(paths);
			System.out.format("Prison %d, Guards needed: %d\n\n", cases, minimum);

			cases++;
			size = in.nextInt();
		}
	}	

	// Solves the Beevaria problem given a set per prisoner which contains which
	// empty cells the prisoner can see
	int solve(Set[] visible) {

		// Get each empty cell with which prisoners can see it
		Set[] cells = getGuardSets(visible); 

		// If cells returned null then its possible with one guard
		if (cells == null)
			return 1;

		// Order cells by how many prisoners they see
		sortBySize(cells);
		
		// Remove all subsets to drastically improve performance.
		int cellCount = removeSubsets(cells);
		
		// The least number of guards we require, worst case we position a guard 
		// so it can only see two prisoners (always possible).
		int leastGuards = (prisonerCount + 1) / 2;
		
		// For each starting cell...
		for (int i = 0; i < cellCount; i++) {
			// Start off with the current cell initially
			SetNode root = new SetNode(1, cells[i]);
			
			// Stack = DFS, this ensures a quick initial answer will be found for
			// difficult input, and from there the efficiency only improves. If we
			// were to use a queue for the difficult cases we would run out of 
			// memory since the entire current generation would be held in memory.
			Stack<SetNode> stack = new Stack<SetNode>();
			stack.push(root);

			// Continue until all possibilites have been exhausted.
			while (!stack.isEmpty()) {
				SetNode node = stack.pop();
				// If the set in the node sees all prisoners...
				if (node.set.n == prisonerCount) {
					if (node.depth < leastGuards) {
						leastGuards = node.depth;
					}
					break;
				}
				// If the set has not passed the leastGuards then branch it
				if (node.depth < leastGuards) {
					node.branch(cells, cellCount, stack);
				}
			}
		}
		
		return leastGuards;
	}

	// A node which contains how many guards are on it (depth) and the set of 
	// prisoners it covers (set of prisoner indices)
	class SetNode {
		int depth;
		Set set;
		// Initializes a new set node.
		SetNode(int depth, Set set) {
			this.depth = depth;
			this.set = set;
		}
		// Given an array of sets (and the total) this will add all sets in the array
		// that have the maximum difference in elements from this node to the stack.
		void branch(Set[] sets, int totalSets, Stack<SetNode> stack) {
			ArrayList<Set> children = new ArrayList<Set>();
			int maxDifference = 0;
			// Look at every cell except this one, be greedy about it.
			for (int j = 0; j < totalSets; j++) {
				int current = difference(set, sets[j]);
				if (current > maxDifference) {
					maxDifference = current;
					children.clear();
				}
				if (current == maxDifference) {
					children.add(sets[j]);
				}
			}
			// Add each child to the queue.
			for (Set child : children) {
				// Increase the depth (guard count) and union the child and parent.
				stack.push(new SetNode(depth + 1, union(child, set)));
			}
		}
		
	}

	// Returns an array of all the cells a guard can stand on. Each set contains
	// the indices of the prisoners which the guard can see from that cell.
	Set[] getGuardSets(Set[] prisonerVisible) {

		// Set of all possible cells a guard can occupy
		Set complete = union(prisonerVisible);

		// The array of sets of prisoners visible
		Set[] cells = new Set[complete.n];

		SetBuilder sb = new SetBuilder();
		// Fill in each cell....
		for (int i = 0; i < cells.length; i++) {

			// Get the index of the current cell.
			int cellIndex = complete.set[i];
			for (int j = 0; j < prisonerCount; j++) {
				// If the prisoner can see the current cell, add its index.
				if (prisonerVisible[j].contains(cellIndex))
					sb.add(j);
			}

			// If a cell is visible by all prisoners then this requires 1 guard.
			if (sb.size() == prisonerCount)
				return null;

			// Create the set and clear for the next one.
			cells[i] = sb.getSet();
			sb.clear();
		}

		return cells;
	}

	// Returns the set of all visible untaken cells from the given cell. 
	Set getVisible(int cell) {
		SetBuilder sb = new SetBuilder();
		int x;

		x = cell;	// Moving Up+Left
		while (!isTop(x) && hasLeft(x)) {
			x = downNeg(x);
			if (!taken[x]) sb.add(x);
		}
		x = cell;	// Moving Down+Right
		while (!isBottom(x) && hasRight(x)) {
			x = downPos(x);
			if (!taken[x]) sb.add(x);
		}
		x = cell;	// Moving Up+Right
		while (!isTop(x) && hasRight(x)) {
			x = upPos(x);
			if (!taken[x]) sb.add(x);
		}
		x = cell;	// Moving Down+Left
		while (!isBottom(x) && hasLeft(x)) {
			x = upNeg(x);
			if (!taken[x]) sb.add(x);
		}
		x = cell;	// Moving Down
		while (hasBottom(x)) {
			x = colPos(x);
			if (!taken[x]) sb.add(x);
		}
		x = cell;	// Moving Up
		while (hasTop(x)) {
			x = colNeg(x);
			if (!taken[x]) sb.add(x);
		}

		return sb.getSet();
	}

	
	// Returns true if the given cell has a cell below it.
	boolean hasBottom(int pos) {
		return pos < total - row;
	}
	// Returns true if the given cell is on the bottom-most row.
	boolean isBottom(int pos) {
		return pos >= total - inner;
	}
	// Returns true if the given cell has a cell above it.
	boolean hasTop(int pos) {
		return pos > row;
	}
	// Returns true if the given cell is on the top-most row.
	boolean isTop(int pos) {
		return pos <= inner;
	}
	// Returns true if the given cell has a cell to the left of it.
	boolean hasLeft(int pos) {
		return pos % row != 0;
	}
	// Returns true if the given cell has a cell to the right of it.
	boolean hasRight(int pos) {
		return (pos - inner) % row != 0;
	}

	// Returns the index of the cell below c
	int colPos(int c) {
		return c + row;
	}
	// Returns the index of the cell above c
	int colNeg(int c) {
		return c - row;
	}
	// Returns the index of the cell below and to the right of c
	int downPos(int c) {
		return c + size;
	}
	// Returns the index of the cell above and to the left of c
	int downNeg(int c) {
		return c - size;
	}
	// Returns the index of the cell above and to the right of c
	int upPos(int c) {
		return c - inner;
	}
	// Returns the index of the cell below and to the left of c
	int upNeg(int c) {
		return c + inner;
	}

	//===========================================================================
	// Deprecated
	//===========================================================================
	//	// Column = |
	//	boolean inColumn(int a, int b) {
	//		return Math.abs(a - b) % row == 0;
	//	}
	//	// Down = \
	//	boolean inDown(int a, int b) {
	//		return Math.abs(a - b) % size == 0;
	//	}
	//	// Up = /
	//	boolean inUp(int a, int b) {
	//		return Math.abs(a - b) % inner == 0;
	//	}
	//	// Column OR Down OR Up
	//	boolean sees(int a, int b) {
	//		return inColumn(a, b) || inDown(a, b) || inUp(a, b);
	//	}


	//===========================================================================
	// A set is an immutable group of unique numbers ordered from smallest to largest.
	//===========================================================================
	class Set {
		// The ordered set of numbers.
		final int[] set;
		// The count of numbers in this set.
		int n;
		// Initializes a new set of numbers.
		Set(int ... set) {
			Arrays.sort(this.set = set);
			n = set.length;
		}
		// Returns the number of possible subsets this set contains. If the size
		// of this set is greater then 63 then the value returned is inaccurate.
		long subsetCount() {
			return (1L << n);
		}
		// Returns true if this set contains the given integer.
		boolean contains(int x) {
			// Perform a binary search for x
			int min = 0, max = n - 1, mid;
			while (min <= max) {
				mid = ((max + min) >> 1);
				if (set[mid] == x)
					return true;
				if (set[mid] < x)
					min = mid + 1;
				else
					max = mid - 1;
			}
			return false;
		}		
		// Prints the string representation of this Set.
		void print() {
			if (n == 0) {
				System.out.println("{ }");
			} else {
				System.out.print("{ " + set[0]);
				for (int i = 1; i < n; i++)
					System.out.print(" " + set[i]);
				System.out.println(" }");
			}
		}
	}

	//===========================================================================
	// Builds a set by providing number appending to the set.
	//===========================================================================
	class SetBuilder {
		// The set of numbers being built.
		int[] set;
		// The count of numbers currently in the set.
		int n;
		// Initializes a new empty Set Builder.
		SetBuilder() {
			this(16);
		}
		SetBuilder(int n) {
			set = new int[Math.max(n, 2)];
		}
		// Adds the given number to the set.
		void add(int x) {
			ensureCapacity();
			set[n++] = x;
		}
		// Clears the set of all numbers.
		void clear() {
			n = 0;
		}
		// Ensures the set has space to add one more number.
		void ensureCapacity() {
			if (n == set.length) {
				set = Arrays.copyOf(set, n + (n >> 1));	// Resize by 150%
			}
		}
		// Returns the count of numbers in the set.
		int size() {
			return n;
		}
		// Returns the builders created Set.
		Set getSet() {
			return new Set(Arrays.copyOf(set, n));
		}
	}

	//===========================================================================
	// Performs grouping on any number of sets. This performs in O(M*N) where M
	// is the number of sets and N is the size of the largest set.
	// Sets used in Examples:
	//		A = {1,2,3,4,8,9,10}
	//		B = {1,3,4,5,6,9,13}
	//		C = {1,2,3,5,7,8,11}
	//		D = {1,2,4,5,6,7,12}
	//
	// ===Inclusive grouping===
	// Returns a set which contains all numbers that are contained in atleast n 
	// sets. If n is 1 then this performs a union of the given sets, if n is the
	// number of sets then this does a strict intersection of all the sets.
	//	For Example:
	//		group(true, 4, A, B, C, D) = {1}
	//		group(true, 3, A, B, C, D) = {1,2,3,4,5}
	//		group(true, 2, A, B, C, D) = {1,2,3,4,5,6,7,8,9}
	//		group(true, 1, A, B, C, D) = {1,2,3,4,5,6,7,8,9,10,11,12,13}
	// 	group(true, 2, A, B) = {1,3,4,9}
	//
	// ===Exclusive grouping===
	// Returns a set which contains all numbers that are not in anymore then n
	// sets. If n is 1 this returns an empty set, if n is 2 then this performs a 
	// complement of the given sets, if n is the number of sets then this does 
	// not include the strict intersection.
	// For Example:
	//		group(false, 4, A, B, C, D) = {2,3,4,5,6,7,8,9,10,11,12,13}
	//		group(false, 3, A, B, C, D) = {6,7,8,9,10,11,12,13}
	//		group(false, 2, A, B, C, D) = {10,11,12,13}
	//		group(false, 1, A, B, C, D) = {}
	// 	group(false, 2, A, B) = {1,3,4,9}
	//===========================================================================
	Set group(boolean inclusive, int n, Set ... sets) {

		/*
		 * Grouping is a generic way to combine sets in certain ways.
		 * The basic algorith for grouping is:
		 * 1	While all sets are not empty
		 * 2		Find the highest remaining number in the sets
		 * 3		Count how many sets contain this high number
		 * 4		Remove this number from every set that contains it. 
		 * 5		Based on the count, inclusiveness, and n determine if this number
		 * 			should be added to the result
		 */

		// Remove empty/null sets
		int count = removeEmptySets(sets);

		// A copy of each sets numbers.
		int[][] values = new int[count][];
		// The pointer to j'th set where the current number is.
		int[] index = new int[count];
		// The maximum possible size of the grouped set
		int maxSize = 0;
		// The number of sets that have not been completely searched through.
		int fullSets = count;

		// Pre compute and store values for quick retrieval.
		for (int i = 0; i < count; i++) {
			values[i] = sets[i].set;
			index[i] = sets[i].n - 1;
			maxSize += sets[i].n;
		}

		// With the initial size being maxSize the builder will never have to
		// resize its internal array
		SetBuilder result = new SetBuilder(maxSize);

		// While not all sets have been exhausted...
		while (fullSets > 0) {

			//=====================================================================
			// Find maximum number in the sets (only looking at the last number in the sets)
			int max = 0;			// The index of the set with the minimum first number.
			int maxIndex = -1;	// The index of the maximum number in the set.
			for (int j = 0; j < count; j++) {
				// If the next number in the j'th set is less then the next number
				// in the min'th set then reset the min set.
				if (index[j] >= 0 && (maxIndex == -1 || values[j][index[j]] > values[max][maxIndex])) {
					maxIndex = index[max = j];
				}
			}

			//=====================================================================
			// Count how many sets have max, for every set that has the max 
			// decrement their positions.
			int total = 0;
			for (int j = 0; j < count; j++) {
				// If the next number in the j'th set is equal to the min...
				if (index[j] >= 0 && values[j][index[j]] == values[max][maxIndex]) {
					// Increment matches
					total++;
					// Change the index
					index[j]--;
					// If this set has been exhausted then decrement full sets
					if (index[j] < 0) {
						fullSets--;
					}
				}
			}

			//=====================================================================
			// If its inclusive use the >= n rule
			// If its not inclusive use the < n rule
			if ((inclusive && total >= n) || (!inclusive && total < n)) {
				result.add(values[max][maxIndex]);
			}
		}

		return result.getSet();
	}
	
	// Removes all null or empty sets from the given array and returns how many
	// non-empty sets exist in the array starting at index 0.
	int removeEmptySets(Set[] sets) {
		// The initial count of full sets.
		int count = sets.length;
		int k = 0;
		while (k < count) {
			// If it's null or empty then copy over it.
			if (sets[k] == null || sets[k].n == 0) {
				sets[k] = sets[--count];
			} else {
				k++;
			}
		}
		return count;
	}

	// Returns the union of the given sets.
	//		union({1,2,3}, {2,3,4}) = {1,2,3,4}
	Set union(Set ... sets) {
		return group(true, 1, sets);
	}

	// Returns whether b is a subset of a. (all elements in b exist in a)
	//		isSubset({1,2,3,4,5}, {2,5}) = true
	//		isSubset({1,2,3,4,5}, {2,6}) = false
	boolean isSubset(Set a, Set b) {
		for (int i = 0; i < b.n; i++) {
			if (!a.contains(b.set[i]))
				return false;
		}
		return true;
	}

	// Returns the number of elements that are different between the two sets
	int difference(Set a, Set b) {
		int[] s1 = a.set;
		int[] s2 = b.set;
		int n1 = a.n - 1;
		int n2 = b.n - 1;
		int diff = 0;
		while (n1 >= 0 && n2 >= 0) {
			int d = s1[n1] - s2[n2];
			if (d != 0) diff++;
			if (d > 0) n2++;
			if (d < 0) n1++;
			n1--;
			n2--;
		}
		return diff + (n1 + n2 + 2);
	}
	
	// Sorts from largest to smallest.
	void sortBySize(Set[] sets) { 
		Arrays.sort(sets, new Comparator<Set>() {
			public int compare(Set o1, Set o2) {
				return (o2.n - o1.n);
			}
		});
	}
	
	// Given an array of Sets this removes all subsets in the array and returns
	// the number unique sets exist.
	int removeSubsets(Set[] sets) {
		// Mark all cells that are a subset of another cell
		boolean[] isSubset = new boolean[sets.length];
		for (int i = 0; i < sets.length; i++) {
			for (int j = i + 1; j < sets.length; j++) {
				isSubset[j] |= isSubset(sets[i], sets[j]);
			}
		}
		// Remove all marked cells
		int unique = 0;
		for (int i = 0; i < sets.length; i++) {
			// This is a unique number, keep it.
			sets[unique++] = sets[i];
			// If the next is a subset then skip it.
			while ((i < sets.length - 1) && isSubset[i + 1])
				i++;
		}
		return unique;
	}
	
	
}
