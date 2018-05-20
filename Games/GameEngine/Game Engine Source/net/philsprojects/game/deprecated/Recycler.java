package net.philsprojects.game.deprecated;

import net.philsprojects.game.util.LinkedList;

/**
 * 
 * 
 * @author Philip Diffenderfer
 */
@SuppressWarnings("unchecked")
public class Recycler
{

	// If a list of recyclable types doesn't exist, this is returned.
	private static final int DOESNT_EXIST = -1;

	// The class type passed in to create a new object.
	private static final Class[] CLASS_TYPES = { Object[].class };

	// The array of LinkedLists that keep track of different recyclable types.
	private static LinkedList<IRecycle>[] _recycled = new LinkedList[513];

	// The total amount of non-null objects passed through the recycle()
	// function.
	private static long _totalThrown = 0;

	// The total amount of objects removed from the Recycler and returned to use.
	private static long _totalRecycled = 0;

	// The total amount of times the get() function was called.
	private static long _totalRequested = 0;

	/**
	 * Gets an item from the <code>Recycler</code> that has the same type as the
	 * <code>item</code> passed through. This item passed through is then used to
	 * generate another of that type if none exists in the <code>Recycler</code>.
	 * If it does exist in the <code>Recycler</code> then the
	 * <code>Recycler</code> returns and removes the first object of that type
	 * and sets it according to the <code>args</code> arguments.
	 * 
	 * @param item
	 *           => The Item used as a base for generating and getting recycled
	 *           objects.
	 * @param args
	 *           => The array of objects used to initialize a new object or set a
	 *           recycled one.
	 */
	public static <T extends IRecycle> T get(Class type, Object... args)
	{
		if (type == null || args == null)
			return null;
		// Gets the index
		int index = getIndex(type, false);
		// Add one to the total amount of requested objects.
		_totalRequested++;
		// If the index of the list of item types is out of range OR the list of
		// these types of
		// elements is empty then return a new instance.
		if (index == DOESNT_EXIST || _recycled[index].getSize() == 0)
		{
			try
			{
				return (T)type.getConstructor(CLASS_TYPES).newInstance(new Object[] { args });
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}

		// Add one to the total number of recycled objects.
		_totalRecycled++;
		// If an item in the list exists, remove it and set its values
		// according to the arguments.
		return (T)_recycled[index].removeFirst().set(args);
	}

	/**
	 * Adds an item to the <code>Recycler</code>.
	 * 
	 * @param item
	 *           => The item to add.
	 */
	public static void recycle(IRecycle item)
	{
		if (item == null)
			return;
		// Gets the index
		int index = getIndex(item.getClass(), true);
		// If an item of this type is not in the Recycler, add its type and
		// add it to the list of recycled objects.
		if (_recycled[index] == null)
			_recycled[index] = new LinkedList<IRecycle>();
		// Add the recyclable item to the correct array index.
		_recycled[index].add(item);
		// Add one to the total amount of trash thrown away.
		_totalThrown++;
	}

	/**
	 * 
	 */
	public static void clear()
	{
		for (int i = 0; i < 513; i++)
		{
			if (_recycled[i] != null)
			{
				_recycled[i].clear();
				_recycled[i] = null;
			}
		}
		_totalThrown = _totalRecycled = _totalRequested = 0;
	}

	/**
	 * Returns the index at the next empty position in the array, if not
	 * nullAcceptable though it returns ENTRY_DOES_NOT_EXIST.
	 * 
	 * @param item
	 *           => The Item to try to find a type of
	 * @param nullAcceptable
	 *           =>
	 */
	private static int getIndex(Class type, boolean nullAcceptable)
	{
		int index = getHash(type.getCanonicalName());
		int checked = 0;
		while (checked < _recycled.length && _recycled[index] != null && _recycled[index].getSize() != 0 && !_recycled[index].get(0).getClass().equals(type))
		{
			index = (index + 1) % _recycled.length;
			checked++;
		}
		if (_recycled[index] == null && !nullAcceptable)
			return DOESNT_EXIST;
		return index;
	}

	/**
	 * Returns an index within the bounds of the recycled array of LinkedLists on
	 * where its best to place it.
	 * 
	 * @param canonicalName
	 *           => The name of the class (*.getClass().getCanonicalName()).
	 */
	private static int getHash(String canonicalName)
	{
		// Add all of the character values together and mod it by the total arrays
		// of recycled lists.
		int total = canonicalName.charAt(0);
		for (int i = 1; i < canonicalName.length(); i++)
			total += canonicalName.charAt(i);
		return total % _recycled.length;
	}

	/**
	 * Returns the total amount of non-null objects passed through the
	 * <code>recycle(IRecycle item)</code> function.
	 */
	public static long getTotalThrown()
	{
		return _totalThrown;
	}

	/**
	 * Returns the total amount of objects removed from the <code>Recycler</code>
	 * and returned to use.
	 */
	public static long getTotalRecycled()
	{
		return _totalRecycled;
	}

	/**
	 * Returns the total amount of times the
	 * <code>get(IRecycle item, Object[] args)</code> function was called.
	 */
	public static long getTotalRequested()
	{
		return _totalRequested;
	}

}
