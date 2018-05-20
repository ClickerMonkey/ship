package dirty;
import java.awt.Graphics2D;
import java.util.Arrays;


public class DirtyRectangles 
{

	public static final int DEFAULT_INCREASE = 16;
	
	private int increase = DEFAULT_INCREASE;
	private int total = 0;
	private Rectangle[] rects = null;

	//
	public DirtyRectangles(int initialCapacity) {
		rects = new Rectangle[initialCapacity];
		while (--initialCapacity >= 0) {
			rects[initialCapacity] = new Rectangle();
		}
	}
	

	// Redraws all dirty rectangles to the graphics object given an array of
	// canvas which support partial drawing. This will clear all dirty 
	// rectangles in the internal list as each rectangle is drawn to a canvas
	public void redraw(Graphics2D gr, Canvas[] canvas) {
		
		// Get a copy of the rectangles
		Canvas[] copy = Arrays.copyOf(canvas, canvas.length);
		
		// Overwrite all clean canvases
		int dirtyCount = 0;
		for (int i = 0; i < copy.length; i++) {
			if (copy[i].isDirty()) {
				dirtyCount++;
			}
			copy[i] = copy[i + dirtyCount];
		}
		
		Rectangle dirty, bounds;
		
		// Process every dirty rectangle 'removing' each one as its processed.
		while (--total >= 0) {
			
			// Get the current dirty rectangle
			dirty = rects[total];
			
			// Check for the rectangle on each canvas...
			for (int i = 0; i < dirtyCount; i++) {
		
				// Get the bounds of the current canvas...
				bounds = copy[i].getBounds();
				
				// If the canvas completely contains the dirty rectangle then
				// just redraw that rectangle on the canvas. The dirty rectangle
				// will not intersect any other canvas to skip checking any
				// additional canvases
				if (bounds.contains(dirty)) {
					copy[i].draw(gr, dirty);
					break;
				}
				// If the dirty rectangle partially covers the canvas then find
				// the area that covers the canvas (intersection) and redraw.
				else if (bounds.intersects(dirty)) {
					
					// Get the intersection
					Rectangle rect = new Rectangle();
					rect.set(bounds);
					rect.intersects(dirty);
					
					// Redraw the intersection
					copy[i].draw(gr, rect);
				}
			}
		}
	}
	
	// Adds the rectangle to be redrawn
	public void invalidate(Rectangle r) 
	{
		// Compare against all pre-existing rectangles
		for (int i = 0; i < total; i++) {
			// If the given rectangle intersects...
			if (r.intersects(rects[i])) {
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
	
}
