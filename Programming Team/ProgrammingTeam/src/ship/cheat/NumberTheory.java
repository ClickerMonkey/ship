package ship.cheat;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class NumberTheory
{


	// Returns the greatest common denominator between a and b.
	static long gcd(long a, long b) {
		if (a < 0) a = -a;
		if (b < 0) b = -b;
		while (b > 0) {
			long t = b;
			b = a % b;
			a = t;
		}
		return a;
	}
	// Returns the lowest common multiple between a and b.
	long lcm(long a, long b) {
		return (a * b) / gcd(a, b);
	}
	// Returns the lowest prime factor of n. If n is prime itself is returned.
	int factor(int n) {
		if ((n & 1) == 0)							// If it's even then return 2
			return 2;
		int max = (int)Math.sqrt(n);			// Calculate its greatest possible factor
		for (int a = 3; a <= max; a += 2) {
			if (n % a == 0)						// If n is divisible by the current factor, return it.
				return a;
		}
		return n;									// It's a prime number
	}
	// Adds all prime factors of n to the given list primes.
	void primeFactorization(int n, List<Integer> primes) {
		while (n > 1) {			// Break it down until it's its own prime factor
			int r = factor(n);	// Compute the lowest prime factor
			primes.add(r);			// Add it to the list
			n /= r;					// Divide n by its lowest prime factor
		}
	}

	// Calculates the factorial of the given integer n.
	// F(n) = n! = n*(n-1)*(n-2)*...*(1)
	long factorial(long n) {
		long f = n;
		while (--n > 1)
			f *= n;
		return f;
	}
	// Calculates the permutation of the given integer n and m. Ordered collection of distinct elements.
	// P(n,m) = n! / (n - m)!
	long permutate(long n, long m) {
		long p = n;
		while (--m >= 1)
			p *= --n;
		return p;
	}
	// Calculates the combination of the given integer n and m. Un-ordered collection of distinct elements.
	// C(n,m) = n! / m!(n - m)!
	long choose(long n, long m) {
		long num = 1, den = 1, gcd;
		if (m > (n >> 1))
			m = n - m;
		while (m >= 1) {
			num *= n--;
			den *= m--;
			// This reduces overflow, if dealing with small numbers remove it
			gcd = gcd(num, den);
			num /= gcd;
			den /= gcd;
		}
		return num;	//If removed gcd then replace with (num / den)
	}

	// Given an array of values the array is rolled to the next permutation.
	void nextPermutation(int[] values) {
		int i = values.length - 1;
		int j = values.length;
		while (values[i - 1] >= values[i]) i--;
		while (values[j - 1] <= values[i - 1]) j--;

		swap(values, i - 1, j - 1);
		i++; j = values.length;

		while (i < j) {
			swap(values, (i++) - 1, --j);
		}
	}
	// Swaps to integers in a given array.
	void swap(int[] values, int i, int j) {
		int t = values[i];
		values[i] = values[j];
		values[j] = t;
	}

	// a^n (mod p) = base^power (% mod)
	long fastExponentiation(long base, long power, long mod) {
		long prod = 1;
		long term = base % mod;
		while(power > 0) {
			if((power & 1) == 1) 
				prod = (prod * term) % mod;
			power >>= 1;
		term = (term * term) % mod;
		}
		return prod;
	}

	// Returns all the factors of the given integer.
	//		getFactors(12) = {1,2,3,4,6,12}
	//		getFactors(7) = {1,7}
	//		getFactors(256) = {1,2,4,8,16,32,64,128,256}
	Set getFactors(int x) {
		SetBuilder sb = new SetBuilder();
		sb.add(1);			// 1 is always a factor of x
		sb.add(x);			// x is always a factor of itself.
		for (int i = 2; i * i <= x; i++) {
			if (x % i == 0) {		// If x is divisible by i, its divisible
				sb.add(i);			// Add i to the set
				sb.add(x / i);		// And add
			}
		}
		return sb.getSet();
	}

	//===========================================================================
	// A set is an immutable group of unique numbers ordered from smallest to 
	// largest. The values in the set must be ordered for all set operations to
	// function properly, so don't manually change/re-order these!
	//===========================================================================
	class Set {
		// The ordered set of numbers.
		final int[] set;
		// The count of numbers in this set.
		int n;
		// Initializes a new set of numbers. The numbers passed in will be ordered
		// and any duplicates will automatically be removed.
		Set(int ... set) {
			// Sort the given set
			Arrays.sort(this.set = set);
			n = set.length;
			// Remove all duplicated numbers
			if (n > 1) {
				n = removeDuplicates();
			}
		}
		// Removes all duplicates from the set and returns how many unique
		// numbers in the set exist.
		int removeDuplicates() {
			int j = 0, last = n - 1;
			for (int i = 0; i < n; i++) {
				// This is a unique number, keep it.
				set[j++] = set[i];
				// If the next is a duplicate then skip it.
				while ((i < last) && set[i] == set[i + 1])
					i++;
			}
			return j;
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
	// A generator of subsets for a given parent Set. The sizes of the subsets
	// generated can be specifed to be within a given bounds inclusively.
	//===========================================================================
	class Subset extends Set {

		Set parent;					// The parent set of this subset.
		int subsetSize;			// The size of the current sub set
		int subsetMin;				// The minimum size allowed for a subset
		int subsetMax;				// The maximum size allowed for a subset
		boolean[] subsetMask;	// The mask used to generate subsets
		boolean rolled;

		// Initializes a subset given its parent and the min and max size of 
		// subsets to generate.
		Subset(Set parent, int min, int max) {
			// Make this subset a copy of the parent set
			super(Arrays.copyOf(parent.set, parent.n));
			this.parent = parent;
			this.subsetMin = min;
			this.subsetMax = max;
			this.subsetSize = n;
			this.subsetMask = new boolean[n];
			this.findNextValidSubset();			// Find the first valid subset
		}
		// Increments this subset to the next subset in the parent set.
		void next() {
			updateSubset();			// Set this subset based on the mask
			rollMask();					// The actual 'next' operation
			findNextValidSubset();	// Get the next subset with the desired size.
		}
		// Returns true if this subset can generate another unique subset of the parent.
		boolean hasNext() {
			return (subsetSize != 0);
		}
		//========================================================================
		// Private Methods
		//========================================================================
		// Updates the values and size of this subset based on the current mask
		void updateSubset() {
			// Restrict the count of numbers in this subset by its current size.
			n = subsetSize;
			// For each marked number in the parent mask add it to this subset.
			for (int i = 0, j = 0; i < parent.n; i++) {
				if (!subsetMask[i]) {
					set[j++] = parent.set[i];
				}
			}
		}
		// Rolls the mask until a valid subset is met (within the subset size bounds)
		void findNextValidSubset() {
			// While this subset is not within the subset size bounds...
			while (hasNext() && (subsetSize < subsetMin || subsetSize > subsetMax)) {
				rollMask();
			}
		}
		// Rolls the subset mask to the next one and updates the subsetSize.
		void rollMask() {
			int i = set.length - 1;
			while (i >= 0 && subsetMask[i]) {
				subsetMask[i--] = false;
				subsetSize++;
			}
			if (i >= 0) {
				subsetMask[i] = true;
				subsetSize--;
			}
			rolled = true;
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

	// Returns the intersection of the given sets.
	//		intersect({1,2,3}, {2,3,4}) = {2,3}
	Set intersect(Set ... sets) {
		return group(true, sets.length, sets);
	}

	// Returns the symmetric difference of the given sets.
	//		difference({1,2,3}, {2,3,4}) = {1,4}
	Set difference(Set ... sets) {
		return group(false, sets.length, sets);
	}

	// Returns the relative complement (set difference) of the two sets.
	// 	complement({1,2,3}, {2,3,4}) = {1}
	//		complement({2,3,4}, {1,2,3}) = {4}
	Set complement(Set a, Set b) {
		return difference(union(a, b), b);
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
	int findDifference(Set a, Set b) {
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

	// Given an array of Sets this removes all subsets in the array and returns
	// the number of unique sets that exist (n) in the array [0,n-1].
	int removeSubsets(Set[] sets) {
		// Mark all sets that are a subset of another cell
		boolean[] isSubset = new boolean[sets.length];
		for (int i = 0; i < sets.length; i++) {
			for (int j = i + 1; j < sets.length; j++) {
				if (!isSubset[j] && isSubset(sets[i], sets[j])) {
					isSubset[j] = true;
				}
			}
		}
		// Remove all marked sets
		int unique = 0;
		for (int i = 0; i < sets.length; i++) {
			// This is a unique set, keep it.
			sets[unique++] = sets[i];
			// Skip all following 'subset' sets.
			while ((i < sets.length - 1) && isSubset[i + 1])
				i++;
		}
		return unique;
	}

	// Sorts from largest to smallest.
	void sortBySize(Set[] sets) { 
		Arrays.sort(sets, new Comparator<Set>() {
			public int compare(Set o1, Set o2) {
				return (o2.n - o1.n);
			}
		});
	}

	public NumberTheory() {
		Set A = new Set(1, 2, 3, 4, 8, 9, 10);
		Set B = new Set(1, 3, 4, 5, 6, 9, 13);
		Set C = new Set(1, 2, 3, 5, 7, 8, 11);
		Set D = new Set(1, 2, 4, 5, 6, 7, 12);

		Set inc4 = group(true, 4, A, B, C, D);
		Set inc3 = group(true, 3, A, B, C, D);
		Set inc2 = group(true, 2, A, B, C, D);
		Set inc1 = group(true, 1, A, B, C, D);

		Set exc4 = group(false, 4, A, B, C, D);
		Set exc3 = group(false, 3, A, B, C, D);
		Set exc2 = group(false, 2, A, B, C, D);
		Set exc1 = group(false, 1, A, B, C, D);

		System.out.print("A = "); A.print();
		System.out.print("B = "); B.print();
		System.out.print("C = "); C.print();
		System.out.print("D = "); D.print();

		System.out.println("Inclusive grouping:");
		System.out.print("group(4,A,B,C,D) = "); inc4.print();
		System.out.print("group(3,A,B,C,D) = "); inc3.print();
		System.out.print("group(2,A,B,C,D) = "); inc2.print();
		System.out.print("group(1,A,B,C,D) = "); inc1.print();
		System.out.println();

		System.out.println("Exclusive grouping:");
		System.out.print("group(4,A,B,C,D) = "); exc4.print();
		System.out.print("group(3,A,B,C,D) = "); exc3.print();
		System.out.print("group(2,A,B,C,D) = "); exc2.print();
		System.out.print("group(1,A,B,C,D) = "); exc1.print();
		System.out.println();

		Set X = new Set(1, 2, 3);
		Set Y = new Set(2, 3, 4);
		System.out.print("X = "); X.print();
		System.out.print("Y = "); Y.print();
		System.out.print("X union Y = "); union(X, Y).print();
		System.out.print("X intersect Y = "); intersect(X, Y).print();
		System.out.print("X difference Y = "); difference(X, Y).print();
		System.out.print("X complement Y = "); complement(X, Y).print();
		System.out.print("Y complement X = "); complement(Y, X).print();
		System.out.println();

		Set E = new Set(1, 2, 3, 4, 89, 120, 5, 6, 10);
		Set R = new Set(1, 4, 5);
		Set T = new Set(1, 2, 3, 4, 5, 6, 7);
		System.out.print("E = "); E.print();
		System.out.print("R = "); R.print();
		System.out.print("T = "); T.print();
		System.out.println("R is subset of E = " + isSubset(E, R));
		System.out.println("T is subset of E = " + isSubset(E, T));
		System.out.println("R is subset of T = " + isSubset(T, R));
		System.out.println();

		Set P = new Set(1, 2, 3, 4);
		Set L = new Set();
		Subset O = new Subset(P, 2, 2);		// Only generate subsets of 2
		System.out.print("P = "); P.print();
		System.out.println("Subsets of P:");
		while (O.hasNext()) {
			O.next();
			O.print();
			L = union(L, O);
		}
		System.out.println("Union of all subsets of size 2 = "); L.print();


		Set V = new Set(1, 2, 3);
		Subset Vs = new Subset(V, 0, 3);
		System.out.print("V = "); V.print();
		System.out.println("All subsets of V:");
		while (Vs.hasNext()) {
			Vs.next();
			Vs.print();
		}

		Set DUP1 = new Set(1, 1, 2, 2, 3, 3, 3, 4, 5, 6, 7, 7, 7);
		System.out.print("DUP1 = "); DUP1.print();
		Set DUP2 = new Set(1, 1, 1, 1, 1, 1);
		System.out.print("DUP2 = "); DUP2.print();
	}

	public static void main(String[] args) {
		new NumberTheory();
	}

}
