package dbms.fd;
import java.util.Arrays;

/**
 * A set of functional dependencies.
 * 
 * @author Philip Diffenderfer
 *
 */
public class FdSet 
{

	// The immutable list of Fd's
	public final Fd[] fds;

	/**
	 * Instantiates a new set of functional dependencies.
	 * 
	 * @param fds => An array of functional dependencies.
	 */
	public FdSet(Fd ... fds) 
	{
		this.fds = fds;
	}

	/**
	 * Instantiates a new set of functional dependencies. (as an array of 
	 * Fd's and the number of attrbutes to take from that array, starting at 0).
	 * 
	 * @param fds => The source array of Fd's.
	 * @param count => The number of Fd's to take from the source array.
	 */
	public FdSet(Fd[] fds, int count)
	{
		this(Arrays.copyOf(fds, count));
	}

	/**
	 * Returns the total number of Fd in this set.
	 */
	public int size()
	{
		return fds.length;
	}

	/**
	 * Returns the Fd at the given index.
	 * 
	 * @param i => The index of the Fd.
	 */
	public Fd get(int i)
	{
		return (i < 0 || i >= fds.length ? null : fds[i]);
	}

	/**
	 * Returns the String representation of this set of Fd's.
	 */
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("F = {\n");
		for (Fd fd : fds) {
			sb.append("\t");
			sb.append(fd);
			sb.append("\n");
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * For a set of attributes X this will return the closure of X with respect
	 * to this set of functional Dependencies. The notation for the closure of
	 * X is X+.
	 */
	public Set getClosure(Set X) 
	{
		SetBuilder sb = new SetBuilder();

		// Reflexifivity rule (If Y is a subset of X, then X->Y)
		sb.add(X);

		Set closure, newest = sb.getSet();
		// Transitivity rule (If X->Y and Y->Z, then X->Z)
		do {
			// Get the current X+ 
			closure = newest;

			// For every Fd in this set...
			for (Fd fd : fds) {
				// If the dependant of Fd is a subset of X+
				if (fd.X.isSubsetOf(closure)) {
					// Union Y to X+
					sb.add(fd.Y);
				}
			}

			// Get most up-to-date set
			newest = sb.getSet();

			// Loop while X+ is changing...
		} while (newest.size() != closure.size());

		// Remove the first element (X).
		sb.remove(0);

		// Return X+
		return sb.getSet();
	}

	/**
	 * Returns whether the given Fd (X->Y) exists in the closure of this set. 
	 * This will return true if Y is a subset of X+ with-respect-to this set. 
	 * 
	 * @param fd => The Fd to test for closure in this set.
	 */
	public boolean inClosure(Fd fd)
	{
		return fd.Y.isSubsetOf(getClosure(fd.X));
	}

	/**
	 * Returns true if the given set of Fd's is equavilent to this set.
	 * 
	 * @param G => The set of Fd's to test against.
	 */
	public boolean isEqual(FdSet G)
	{
		return isEqual(this, G);
	}

	/**
	 * Returns the minimal cover of this set of Fd's.
	 */
	public FdSet getMinimalCover()
	{
		return getMinimalCover(this);
	}

	/**
	 * Returns true if F covers G (G is a subset of F+).
	 * 
	 * @param F => The first set.
	 * @param G => The second set.
	 */
	public static boolean covers(FdSet F, FdSet G)
	{
		// For all Fd's in G...
		for (Fd fd : G.fds) {
			// The closure of X with-respect-to F must contain Y
			if (!F.inClosure(fd)) {
				return false;
			}
		}
		// F covers G
		return true;	
	}

	/**
	 * Returns whether F and G are equivalent (F covers G, G covers F).
	 * 
	 * @param F => The first set.
	 * @param G => The second set.
	 */
	public static boolean isEqual(FdSet F, FdSet G)
	{
		return covers(F, G) && covers(G, F);
	}

	/**
	 * Computes the minimal cover of a given set of Fd's F.
	 * 
	 * @param F => The set of Fd's to compute the minimal cover of.
	 */
	public static FdSet getMinimalCover(FdSet F)
	{
		FdSetBuilder G = new FdSetBuilder();

		// G is initially set to F
		G.add(F);

		// STEP 1 = Break apart dependent attributes
		for (int i = G.size() - 1; i >= 0; i--) 
		{
			// Remove current fd
			Fd fd = G.remove(i);
			// Break down dependent attributes in fd. If the current fd has only
			// one attribute then the fd is essentially added back in.
			for (int k = 0; k < fd.Y.size(); k++) {
				G.add(new Fd(fd.X, new Set(fd.Y.get(k))));
			}
		}

		// STEP 2 = Remove extraneous attributes
		FdSet Gset = G.getSet();
		for (int i = G.size() - 1; i >= 0; i--)
		{
			// Get the current fd
			Fd fd = G.get(i);
			// If the fd has multiple attributes in X check each one.
			int total = fd.X.size();
			if (total == 1) { 
				continue;
			}
			
			// For each attribute in X...
			for (int k = 0; k < total; k++) 
			{
				String attribute = fd.X.get(k);
				// Compute the set of attributes without attribute k.
				Set diff = Set.difference(fd.X, new Set(attribute));
				// Compute the closure of the new set with G.
				Set closure = Gset.getClosure(diff);

				// If the attribute k exists in the closure, its extraneous
				if (closure.contains(attribute)) 
				{
					// Set X as the set without the extraneous attribute.
					fd.X = diff;
				}
			}
		}

		// STEP 3 = Delete redundant functional dependencies
		// Compute the closure for each Fd in G. Remove the Fd from G and compute
		// the closure again, if they are not equal, re add the Fd into G.
		for (int i = G.size() - 1; i >= 0; i--) 
		{
			// Get the current Fd in the set we're examining
			Fd current = G.get(i);

			// Compute the closure of X with-respect-to G before its removed.
			Set before = G.getSet().getClosure(current.X);
			// Remove X
			G.remove(i);
			// Compute the closure of X with-respect-to G after its removed.
			Set after = G.getSet().getClosure(current.X);

			// If the closure before and after are not equal then this Fd is 
			// required, add it back into G.
			if (!before.equals(after)) {
				G.add(current);
			}
		}

		// Return the minimal set of Fds
		return G.getSet();
	}

}
