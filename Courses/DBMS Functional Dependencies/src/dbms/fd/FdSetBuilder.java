package dbms.fd;
import java.util.Arrays;

/**
 * Builds an FdSet.
 * 
 * @author Philip Diffenderfer
 *
 */
public class FdSetBuilder 
{

	/**
	 * Default capacity of a FdSetBuilder.
	 */
	public static int DEFAULT_CAPACITY = 16;
	
	// Number of Fd's currently in the set.
	private int size;
	
	// The array of Fd's in this builder.
	private Fd[] e;
	
	/**
	 * Instantiates a new FdSet builder.
	 */
	public FdSetBuilder()
	{
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * Instantiates a new FdSet builder.
	 * 
	 * @param initialCapacity => The initial number of Fd's this builder can
	 * 		hold before its internal array needs to be resized.
	 */
	public FdSetBuilder(int initialCapacity) 
	{
		this.e = new Fd[initialCapacity];
	}
	
	/**
	 * Adds the given Fd to the set.
	 * 
	 * @param fd => The Fd to add.
	 */
	public void add(Fd fd) 
	{
		ensureCapacity(1);
		e[size++] = fd;
	}
	
	/**
	 * Adds all of the Fd's in the given FdSet to the set.
	 * 
	 * @param set => The FdSet to add.
	 */
	public void add(FdSet set) 
	{
		int total = set.size();
		ensureCapacity(total);
		for (int i = 0; i < total; i++) {
			e[size++] = set.get(i);
		}
	}
	
	/**
	 * Removes ands returns the Fd at the given index.
	 * 
	 * @param i => The index of the Fd [ 0, size )
	 */
	public Fd remove(int i)
	{
		if (i < 0 || i >= size) {
			return null;
		}
		Fd removed = e[i];
		e[i] = e[--size];
		return removed;
	}

	/**
	 * Returns the number of Fd's currently in this builder.
	 */
	public int size() 
	{
		return size;
	}
	
	/**
	 * Returns the Fd at the given index.
	 * 
	 * @param i => The index of the Fd [ 0, size )
	 */
	public Fd get(int i) 
	{
		return (i < 0 || i >= size ? null : e[i]);
	}

	/**
	 * Returns the FdSet this builder has built thus far.
	 */
	public FdSet getSet() 
	{
		return new FdSet(e, size);
	}

	/**
	 * Returns the FdSet this builder has built thus far then clears the
	 * builder of all Fd's.
	 */
	public FdSet takeSet() 
	{
		FdSet result = getSet();
		clear();
		return result;
	}
	
	/**
	 * Removes all Fd's from this builder.
	 */
	public void clear() 
	{
		size = 0;
	}
	
	/**
	 * Ensures this builder can add a given number of Fd's to its internal array.
	 * 
	 * @param add => The number of Fd's about to be added.
	 */
	public void ensureCapacity(int add) 
	{
		int desired = size + add;
		if (desired > e.length) {
			int next = e.length + (e.length / 2);
			int capacity = Math.max(desired, next);
			e = Arrays.copyOf(e, capacity);
		}
	}
	
}
