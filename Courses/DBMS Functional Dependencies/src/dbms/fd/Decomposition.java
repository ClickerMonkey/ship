package dbms.fd;

/**
 * A decomposition holds the original schema (R), a set of functional
 * dependencies (F), and the decomposed subschemas (subs).
 * 
 * @author Philip Diffenderfer
 * 
 */
public class Decomposition 
{

	// The original schema R
	public final Set R;
	
	// The set of functional dependencies on R
	public final FdSet F;
	
	// The subschemas/decomposition of R
	public final Set[] D;
	
	/**
	 * Instantiates a new Decomposition.
	 * 
	 * @param R => The original schema R.
	 * @param F => The set of functional dependencies on R.
	 * @param subschemas => The subchemas/projectsions of R.
	 */
	public Decomposition(Set R, FdSet F, Set[] subschemas)
	{
		this.R = R;
		this.F = F;
		this.D = subschemas;
	}
	
	/**
	 * Returns true if this decomposition is lossless.
	 */
	public boolean isLossless()
	{
		int colCount = R.size();
		int rowCount = D.length;
		int col, row;
	
		// A table of flags for whether [r][c] covers the attribute a[c]
		boolean table[][] = new boolean[rowCount][colCount];
		
		// Fill table with attributes
		for (row = 0; row < rowCount; row++) {
			for (col = 0; col < colCount; col++) {
				table[row][col] = D[row].contains(R.get(col));
			}
		}
		
		// Iteratively build the table until nothing more can be changed or
		// we determine the decomposition is lossless
		boolean changing;
		do {
			changing = false;

			// For every Fd in F...
			for (int i = 0; i < F.size(); i++) 
			{
				
				// Whether the j'th decomposition matches the X in the i'th Fd
				boolean match[] = new boolean[rowCount];
				int matchCount = 0;
				
				// Get the current Fd (and its X and Y sets)
				Fd fd = F.get(i);
				
				// For every row on the table...
				for (row = 0; row < rowCount; row++) 
				{
					int attrCount = fd.X.size();
					int matches = 0;
					// For every attribute in the current Fd.X
					for (int attr = 0; attr < attrCount; attr++) {
						int index = R.indexOf(fd.X.get(attr));
						if (table[row][index]) {
							matches++;
						}
					}
					// If they have the same number then copy over their Y's
					if (matches == attrCount) {
						match[row] = true;
						matchCount++;
					}
				}
				
				// Atleast 2 rows must have matching X attributes..
				if (matchCount > 1)
				{
					// For all rows that match the condition...
					for (row = 0; row < rowCount; row++) {
						// Mark the Y attributes as true
						for (int copy = 0; copy < fd.Y.size(); copy++) {
							int index = R.indexOf(fd.Y.get(copy));
							if (!table[row][index]) {
								changing = true;
							}
							table[row][index] = true;
						}	
					}	
				}
			}
			
			// If we're marked as changing...
			if (changing) {
				// Check for rows that are all marked.. 
				for (row = 0; row < rowCount; row++) {
					// Check every column, if one is not true then break.
					for (col = 0; col < colCount; col++) {
						if (!table[row][col]) {
							break;
						}
					}
					// All flags are true...
					if (col == colCount) {
						// Lossless decomposition!
						return true;
					}
				}
			}
		
			// Continue while the table is changing...
		} while (changing);
		
		// Not a lossless decomposition
		return false;
	}
	
	/**
	 * Returns whether the given Fd is preserved in this decomposition.
	 * 
	 * @param fd => The Fd to check for preservation.
	 */
	public boolean isPreserved(Fd fd) 
	{
		Set old = new Set();
		Set result = fd.X;
		while (old.size() != result.size()) {
			old = result;
			for (Set Ri : D) {
				Set I = Set.intersect(result, Ri);
				Set C = F.getClosure(I);
				Set T = Set.intersect(C, Ri);
				result = Set.union(result, T);
			}
		}
		return (fd.Y.isSubsetOf(result));
	}
	
	
}
