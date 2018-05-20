package dbms.fd;
import java.util.Arrays;

/**
 * Builds a Set of attributes.
 * 
 * @author Philip Diffenderfer
 *
 */
public class SetBuilder 
{

	/**
	 * Default capacity of a FdSetBuilder.
	 */
	public static int DEFAULT_CAPACITY = 16;
	
	// Number of attributes currently in the set.
	private int size;
	
	// The array of attributes in this builer.
	private String[] e;
	
	/**
	 * Instantiates a new Set builder.
	 */
	public SetBuilder()
	{
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * Instantiates a new Set builder.
	 * 
	 * @param initialCapacity => The initial number of attributes this builder 
	 * 		can hold before its internal array needs to be resized.
	 */
	public SetBuilder(int initialCapacity) 
	{
		this.e = new String[initialCapacity];
	}
	
	/**
	 * Adds the given attribute to the set.
	 * 
	 * @param attribute => The attribute to add.
	 */
	public void add(String attribute) 
	{
		ensureCapacity(1);
		e[size++] = attribute;
	}
	
	/**
	 * Adds all of the attributes in the given set to this builder.
	 * 
	 * @param set => The Set to add.
	 */
	public void add(Set set) 
	{
		int total = set.size();
		ensureCapacity(total);
		for (int i = 0; i < total; i++) {
			e[size++] = set.get(i);
		}
	}
	
	/**
	 * Removes ands returns the attribute at the given index.
	 * 
	 * @param i => The index of the attribute [ 0, size )
	 */
	public String remove(int i)
	{
		if (i < 0 || i >= size) {
			return null;
		}
		String removed = e[i];
		e[i] = e[--size];
		return removed;
	}

	/**
	 * Returns the number of attributes currently in this builder.
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
		return (i < 0 || i >= size ? null : e[i]);
	}

	/**
	 * Returns the Set this builder has built thus far.
	 */
	public Set getSet() 
	{
		return new Set(e, size);
	}
	
	/**
	 * Returns the Set this builder has built thus far then clears the builder 
	 * of all attributes.
	 */
	public Set takeSet() 
	{
		Set result = getSet();
		clear();
		return result;
	}

	/**
	 * Removes all attributes from this builder.
	 */
	public void clear() 
	{
		size = 0;
	}

	
	/**
	 * Ensures this builder can add a given number of attributes to its 
	 * internal array.
	 * 
	 * @param add => The number of attributes about to be added.
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
