package dbms.fd;
import java.util.Arrays;

/**
 * An immutable set of attributes (as strings). There are no duplicate
 * attributes in a set, and all attributes are ordered.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Set 
{

	// The number of unique elements in this Set.
	private final int size;

	// The elements in this Set. The first n elements are the unique elements.
	private final String[] e;
	
	/**
	 * Instantiates a new Set of attributes (as a parameter array). If the
	 * attributes given have duplicates they are removed, and the final state
	 * of the set is ordered and only contains unique attributes.
	 * 
	 * @param attributes => The set of attributes.
	 */
	public Set(String ... attributes) 
	{
		e = attributes;
		Arrays.sort(e);
		size = removeDuplicates();
	}
	
	/**
	 * Instantiates a new Set of attributes (as an array of strings and the
	 * number of attrbutes to take from that array, starting at 0). If the
	 * attributes given have duplicates they are removed, and the final state
	 * of the set is ordered and only contains unique attributes.
	 * 
	 * @param attributes => The source array of attributes.
	 * @param count => The number of attributes to use from the source array.
	 */
	public Set(String[] attributes, int count) 
	{
		this(Arrays.copyOf(attributes, count));
	}
	
	/**
	 * Removes any duplicates from this set of elements and returns how many
	 * unique elements exist in the set now.
	 */
	private int removeDuplicates() 
	{
		int unique = 0; 
		int last = e.length - 1;
		for (int i = 0; i < e.length; i++) {
			// This is a unique number, keep it.
			e[unique++] = e[i];
			// If the next is a duplicate then skip it.
			while ((i < last) && e[i].equals(e[i + 1])) {
				i++;
			}
		}
		return unique;
	}

	/**
	 * Returns the number of unique elements in this Set.
	 */
	public int size() 
	{
		return size;
	}

	/**
	 * Returns the attribute at the given index.
	 * 
	 * @param i => The index of the attribute [ 0, size )
	 */
	public String get(int i) 
	{
		return (i < 0 || i >= e.length ? null : e[i]);
	}

	/**
	 * Returns the index of the given attribute in this Set. If the given
	 * attribute doesn't exist in this Set then -1 is returned.
	 * 
	 * @param attribute => The attribute to search for.
	 * @return => The index of that element.
	 */
	public int indexOf(String attribute)
	{
		int min = 0;
		int max = size - 1;
		int mid;
		while (min <= max) 
		{
			mid = ((max + min) >> 1);
			int cmp = e[mid].compareTo(attribute);
			
			if (cmp == 0) {
				return mid;
			}
			else if (cmp < 0) {
				min = mid + 1;
			}
			else {
				max = mid - 1;
			}
		}
		return -1;
	}
	
	/**
	 * Returns true if the given attribute exists in this Set.
	 * 
	 * @param attribute => The attribute to test for existence.
	 */
	public boolean contains(String attribute) 
	{
		return indexOf(attribute) != -1;
	}
	
	/**
	 * Returns whether this Set is a subset of the given Set.
	 * 
	 * @param a => The set to test against.
	 */
	public boolean isSubsetOf(Set a) 
	{
		return isSubset(a, this);
	}
	
	/**
	 * Converts this set into its String representation.
	 */
	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		if (size >= 1) {	
			sb.append(e[0]);
			for (int i = 1; i < size; i++) {
				sb.append(",");
				sb.append(e[i]);
			}
		}
		sb.append("}");
		return sb.toString();
	}
	
	/**
	 * Returns wether the given object is equivalent to this Set. The object
	 * must be of type Set and must contain the exact attributes for this to be
	 * true.
	 */
	@Override
	public boolean equals(Object o)
	{
		// Same set reference: equal
		if (o == this) {
			return true;
		}
		// Must be of type Set 
		if (o instanceof Set) {
			Set s = (Set)o;
			// Unequal size: unequal
			if (size != s.size) {
				return false;
			}
			// Check every element, when there's an inequality: unequal
			for (int i = 0; i < size; i++) {
				if (!e[i].equals(s.e[i])) {
					return false;
				}
			}
			// Equal!
			return true;
		}
		// Invalid data type
		return false;
	}
	
	/**
	 * Prints this Sets string representation out to Standard Output.
	 */
	public void print()
	{
		System.out.println(this);
	}
	
	
	/**
	 * Performs grouping on any number of sets. This performs in O(M*N) where M
	 * is the number of sets and N is the size of the largest set.
	 * Sets used in Examples:
	 *		A = {1,2,3,4,8,9,10}
	 *		B = {1,3,4,5,6,9,13}
	 *		C = {1,2,3,5,7,8,11}
	 *		D = {1,2,4,5,6,7,12}
	 *
	 * ===Inclusive grouping===
	 * Returns a set which contains all numbers that are contained in atleast n 
	 * sets. If n is 1 then this performs a union of the given sets, if n is the
	 * number of sets then this does a strict intersection of all the sets.
	 *	For Example:
	 *		group(true, 4, A, B, C, D) = {1}
	 *		group(true, 3, A, B, C, D) = {1,2,3,4,5}
	 *		group(true, 2, A, B, C, D) = {1,2,3,4,5,6,7,8,9}
	 *		group(true, 1, A, B, C, D) = {1,2,3,4,5,6,7,8,9,10,11,12,13}
	 * 	group(true, 2, A, B) = {1,3,4,9}
	 *
	 * ===Exclusive grouping===
	 * Returns a set which contains all numbers that are not in anymore then n
	 * sets. If n is 1 this returns an empty set, if n is 2 then this performs a 
	 * complement of the given sets, if n is the number of sets then this does 
	 * not include the strict intersection.
	 * For Example:
	 *		group(false, 4, A, B, C, D) = {2,3,4,5,6,7,8,9,10,11,12,13}
	 *		group(false, 3, A, B, C, D) = {6,7,8,9,10,11,12,13}
	 *		group(false, 2, A, B, C, D) = {10,11,12,13}
	 *		group(false, 1, A, B, C, D) = {}
	 * 	group(false, 2, A, B) = {1,3,4,9}
	 * 
	 * @param inclusive => Whether the grouping is to be inclusive.
	 * @param n => The grouping threshold.
	 * @param sets => The sets to group.
	 * @return => The resulting grouped set.
	 */
	public static Set group(boolean inclusive, int n, Set ... sets) 
	{
		// Phil's Set Theory Grouping Algorithm
		// 		Given a list of ordered sets, this will group equal elements
		// and depending on inclusive or exclusiveness and the group size 
		// threshold (n), the groupings may be added to the resulting Set.
		
		// Remove empty/null sets
		int count = removeEmptySets(sets);
		
		// The pointer to j'th set where the current number is.
		int[] index = new int[count];
		// The maximum possible size of the grouped set
		int maxSize = 0;
		// The number of sets that have not been completely searched through.
		int fullSets = count;

		// Pre-compute and store values for quick retrieval.
		for (int i = 0; i < count; i++) {
			index[i] = sets[i].size - 1;
			maxSize += sets[i].size;
		}

		// With the initial size being maxSize the builder will never have to
		// resize its internal array
		SetBuilder result = new SetBuilder(maxSize);

		// While not all sets have been exhausted...
		while (fullSets > 0) {

			//=====================================================================
			// Find maximum number in the sets (only looking at the last number in the sets)
			//=====================================================================
			// The index of the set with the minimum first number.
			int max = 0;		
			// The index of the maximum number in the set.
			int maxIndex = -1;
			
			for (int j = 0; j < count; j++) {
				// If the next number in the j'th set is less then the next number
				// in the min'th set then reset the min set.
				if (index[j] >= 0 && (maxIndex == -1 || sets[j].e[index[j]].compareTo(sets[max].e[maxIndex]) > 0)) {
					max = j;
					maxIndex = index[j];
				}
			}

			//=====================================================================
			// Count how many sets have max, for every set that has the max 
			// decrement their positions.
			//=====================================================================
			int total = 0;
			for (int j = 0; j < count; j++) {
				// If the next number in the j'th set is equal to the min...
				if (index[j] >= 0 && sets[j].e[index[j]].compareTo(sets[max].e[maxIndex]) == 0) {
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
				result.add(sets[max].e[maxIndex]);
			}
		}

		return result.getSet();
	}
	
	/**
	 * Removes all null or empty sets from the given array and returns how many
	 * non-empty sets exist in the array starting at index 0.
	 * 
	 * @param sets => The array of sets to remove empty/null sets from.
	 */
	public static int removeEmptySets(Set[] sets) 
	{
		// The initial count of full sets.
		int count = sets.length;
		int k = 0;
		while (k < count) {
			// If it's null or empty then copy over it.
			if (sets[k] == null || sets[k].size == 0) {
				sets[k] = sets[--count];
			} else {
				k++;
			}
		}
		return count;
	}
	
	/**
	 * Returns the union of the given sets.
	 * 		union({1,2,3}, {2,3,4}) = {1,2,3,4}
	 * 
	 * @param sets => The sets to union.
	 * @return => The resulting unioned set.
	 */
	public static Set union(Set ... sets) 
	{
		return group(true, 1, sets);
	}
	
	/**
	 * Returns the intersection of the given sets.
	 * 		intersect({1,2,3}, {2,3,4}) = {2,3}
	 * 
	 * @param sets => The sets to intersect.
	 * @return => The resulting intersection set.
	 */
	public static Set intersect(Set ... sets) 
	{
		return group(true, sets.length, sets);
	}
	
	/**
	 * Returns the symmetric difference of the given sets.
	 * 		difference({1,2,3}, {2,3,4}) = {1,4}
	 * 
	 * @param sets => The sets to find the difference of.
	 * @return => The resulting difference set.
	 */
	public static Set difference(Set ... sets) 
	{
		return group(false, sets.length, sets);
	}
	
	/**
	 * Returns the relative complement (set difference) of the two sets.
	 * 		complement({1,2,3}, {2,3,4}) = {1}
	 * 		complement({2,3,4}, {1,2,3}) = {4}
	 * 
	 * @param a => The first set.
	 * @param b => The second set.
	 * @return => The resulting complement set.
	 */
	public static Set complement(Set a, Set b) 
	{
		return difference(union(a, b), b);
	}
	
	/**
	 * Returns whether b is a subset of a. (all elements in b exist in a)
	 * 		isSubset({1,2,3,4,5}, {2,5}) = true
	 * 		isSubset({1,2,3,4,5}, {2,6}) = false
	 * 
	 * @param a => The first set.
	 * @param b => The set to test as a subset of the first set.
	 */
	public static boolean isSubset(Set a, Set b) 
	{
		// If b has more elements then a it cannot be a subset.
		if (b.size > a.size) {
			return false;
		}
		int k = 0;
		// Check that every element of b exists in a
		for (int i = 0; i < b.size; i++) {
			// Skip elements in a that are less then the current element in b
			while (k < a.size && a.e[k].compareTo(b.e[i]) < 0) {
				k++;
			}
			// If a has been exhausted or the current elements aren't equal...
			if (k == a.size || !b.e[i].equals(a.e[k])) {
				return false;
			}
			// Move k to the next element in a
			k++;
		}
		// Valid subset!
		return true;
	}
	
	/**
	 * Given an array of Sets this removes all subsets in the array and returns
	 * the number of unique sets that exist (n) in the array [ 0, n ).
	 * 
	 * @param sets => The array of sets to remove from.
	 * @return => The number of unique sets no in the array.
	 */
	public static int removeSubsets(Set[] sets)
	{
		// Mark all sets that are a subset of another cell
		boolean[] isSubset = new boolean[sets.length];
		for (int i = 0; i < sets.length; i++) {
			for (int j = i + 1; j < sets.length; j++) {
				if (isSubset[j]) continue;
				isSubset[j] = isSubset(sets[i], sets[j]); 
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
	
	/**
	 * Parses a set of attributes from a String. The format of the string may
	 * be: "[{]attr1,attr2,attr3...attrn[}]". Where are attributes are comma
	 * delimited and the braces may or may not be included. Any white space
	 * around the attributes is considered to be part of the attribute itself.
	 * 
	 * @param s => The string to parse from.
	 * @return => The parsed Set.
	 */
	public static Set fromString(String s) 
	{
		s = s.trim();
		int start = (s.startsWith("}") ? 1 : 0);
		int end = (s.endsWith("}") ? 1 : 0);
		s = s.substring(start, s.length() - end);
		return new Set(s.split(","));
	}
	
}
