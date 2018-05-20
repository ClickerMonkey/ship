package dbms.fd;

/**
 * A Functional Dependency expresses a constraint on values of a set of 
 * attributes imposed by another set. Y is functionally dependent on X, 
 * denoted X -> Y. Represents "common sense" rules on data, referred to as 
 * semantic constraints.
 * 
 * @author Philip Diffenderfer
 */
public class Fd 
{
	
	// The independent set of attributes
	public Set X;
	
	// The dependent set of attributes
	public Set Y;
	
	/**
	 * Instantiates a new Functional Dependency given an independent set and
	 * dependent set of attributes.
	 * 
	 * @param X => The independent set of attributes.
	 * @param Y => The dependent set of attributes.
	 */
	public Fd(Set X, Set Y) 
	{
		this.X = X;
		this.Y = Y;
	}
	
	/**
	 * A functional dependency is trivial if Y is a subset of X.
	 * 
	 * @return True if this funcional dependency is trivial.
	 */
	public boolean isTrivial() 
	{
		return Y.isSubsetOf(X); 
	}
	
	/**
	 * Returns the string representation of this functional dependency.
	 */
	@Override
	public String toString()
	{
		return X + " -> " + Y;
	}
	
	/**
	 * Parses the given String into a Functional Dependency. The expected format
	 * of the string is "set->set". Any white space around the sets or arrow is
	 * allowed. For the format of the sets see Set.fromString(String).
	 * 
	 * @param s => The string to parse.
	 * @return => The parsed functional dependency.
	 */
	public static Fd fromString(String s)
	{
		String sets[] = s.split("->");
		return new Fd(Set.fromString(sets[0]), Set.fromString(sets[1]));
	}
	
}
