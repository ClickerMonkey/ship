package dirty;


public class Rectangle 
{

	public int left;
	public int top;
	public int right;
	public int bottom;
	
	//
	public Rectangle() {
		this(0, 0, 0, 0);
	}
	
	//
	public Rectangle(int l, int t, int r, int b) {
		left = l;
		top = t;
		right = r;
		bottom = b;
	}
	
	// Sets this rectangle according to another rectangle
	public void set(Rectangle r) {
		left = r.left;
		top = r.top;
		right = r.right;
		bottom = r.bottom;
	}
	
	// Returns true if this rectangle is equivalent to the given recctangle
	public boolean equals(Rectangle r) {
		return !(left != r.left || right != r.right || top != r.top || bottom != r.bottom);
	}
	
	// Returns true if this rectangle contains the given rectangle
	public boolean contains(Rectangle r) {
		return !(r.left < left || r.right > right || r.top < top || r.bottom > bottom);
	}
	
	// Sets this rectangle as the union of the two given rectangles
	public void union(Rectangle a, Rectangle b) {
		left = Math.min(a.left, b.left);
		top = Math.min(a.top, b.top);
		right = Math.max(a.right, b.right);
		bottom = Math.max(a.bottom, b.bottom);
	}
	
	// Unions this rectangle with a given rectangle
	public void union(Rectangle r) {
		if (r.left < left) {
			left = r.left;
		}
		if (r.right > right) {
			right = r.right;
		}
		if (r.top < top) {
			top = r.top;
		}
		if (r.bottom > bottom) {
			bottom = r.bottom;
		}
	}
	
	// Returns true if this rectangle intersects the given rectangle
	public boolean intersects(Rectangle r) {
		return !(right < r.left || left > r.right || top > r.bottom || bottom < r.top);
	}
	
	// Sets this rectangle as the intersection of the two given rectangles
	public void intersect(Rectangle a, Rectangle b) {
		left = Math.max(a.left, b.left);
		top = Math.max(a.top, b.top);
		right = Math.min(a.right, b.right);
		bottom = Math.min(a.bottom, b.bottom);
	}
	
	// Sets this rectangle as the intersection of itself and the given rectangle
	public void intersect(Rectangle r) {
		if (r.left > left) {
			left = r.left;
		}
		if (r.top > top) {
			top = r.top;
		}
		if (r.right < right) {
			right = r.right;
		}
		if (r.bottom < bottom) {
			bottom = r.bottom;
		}
	}

	//=================================
	// COMPLEMENT 'a complement b', 'a has complement b'
	//=================================
	//	    a
	//	+------+
	//  |      |  b
	//  |  +---+---+
	//  |  |   | X |
	//  +--+---+---|
	//     | Y | Z |
	//     +---+---+
	//
	// X = complementX
	// Z = complementZ
	// Y = complementY
	
	/*

	// Returns whether the complement of this rectangle and the given has an X
	// complement rectangle.
	public boolean hasComplementX(Rectangle r) {
		return (r.right > right || r.left < left);
	}
	
	// Returns whether the complement of this rectangle and the given has an X
	// complement rectangle.
	public boolean hasComplementZ(Rectangle r) {
		return hasComplementX(r) && hasComplementY(r);
	}
	
	// Returns whether the complement of this rectangle and the given has an X
	// complement rectangle.
	public boolean hasComplementY(Rectangle r) {
		return (r.bottom > bottom || r.top < top);
	}
	
	// Sets this rectangle as the complement X of the two given rectangles
	public void complementX(Rectangle a, Rectangle b) {
		left = Math.max(a.right + 1, b.left);
		top = Math.min(a.top, b.top);
		right = Math.min(a.left - 1, b.right);
		bottom = Math.max(a.bottom, b.top);
	}
	
	// Sets this rectangle as the complement Z of the two given rectangles
	public void complementZ(Rectangle a, Rectangle b) {
		left = Math.max(a.right + 1, b.left);
		top = Math.max(a.bottom + 1, b.top);
		right = Math.min(a.left - 1, b.right);
		bottom = Math.min(a.top - 1, b.bottom);
	}

	// Sets this rectangle as the complement Y of the two given rectangles
	public void complementY(Rectangle a, Rectangle b) {
		left = Math.max(a.left, b.left);
		top = Math.max(a.bottom + 1, b.top);
		right = Math.max(a.right, b.left);
		bottom = Math.min(a.top - 1, b.bottom);
	}
	
	*/
}
