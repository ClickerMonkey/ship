package engine;
import java.util.Arrays;


public class RectangleList 
{

	public static final int DEFAULT_INCREASE = 16;
	
	private int increase = DEFAULT_INCREASE;
	private int total = 0;
	private Rectangle[] rects = null;

	//
	public RectangleList(int initialCapacity) {
		rects = new Rectangle[initialCapacity];
		while (--initialCapacity >= 0) {
			rects[initialCapacity] = new Rectangle();
		}
	}

	// Returns and removes the last dirty rectangle added
	public Rectangle popDirty() {
		return (total == 0 ? null : rects[--total]);
	}
	
	// Adds the rectangle to be redrawn
	public void invalidate(Rectangle r) 
	{
		// Compare against all pre-existing rectangles
		for (int i = 0; i < total; i++) {
			// If the given rectangle intersects...
			if (rects[i].intersects(r)) {
				// Intersect, join them and return
				rects[i].union(r);
				return;
			}
		}
		
		// The given rectangle does not intersect any pre-existing ones, add
		// it to the list of rectangles.
		ensureCapacity(1);
		add(r);
	}
	
	// Adds the rectangle to the list
	private void add(Rectangle r) {
		rects[total++].set(r);
	}
	
	// Ensures that the list has enough room to add x more rectangles
	private void ensureCapacity(int x) 
	{
		// Get the current capacity of the array
		int capacity = rects.length;
		
		// If we need more space to add x rectangles then...
		if (total + x > capacity) {
			// Determine the new capacity size (it will always fit atleast x
			// more rectangles
			int newcapacity = Math.max(capacity + increase, capacity + x);
			
			// Resize the rectangle array
			rects = Arrays.copyOf(rects, newcapacity);
			
			// Instantiate each rectangle added
			while (--newcapacity >= capacity) {
				rects[newcapacity] = new Rectangle();
			}
		}
	}
	
	// Returns true if there are any dirty rectangles
	public boolean hasDirty() {
		return (total == 0);
	}
	
}
