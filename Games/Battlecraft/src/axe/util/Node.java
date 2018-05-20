package axe.util;

public class Node<X> 
{
	
	public final X x;
	private Node<X> next, prev;
	
	public Node() {
		this.x = null;
	}
	
	public Node(X x) {
		this.x = x;
	}
	
	public Node(X x, Node<X> after) {
		this.x = x;
		this.insert(after);
	}
	
	public void remove() {
		if (next != null) {
			next.prev = prev;
		}
		if (prev != null) {
			prev.next = next;
		}
		prev = null;
		next = null;
	}
	
	public void insert(Node<X> after) {
		remove();
		final Node<X> before = after.next;
		if (before != null) {
			before.prev = this;
			next = after.next;
		}
		after.next = this;
		prev = after;
	}
	
	public void toFront() {
		swap(Integer.MIN_VALUE);
	}
	
	public void toBack() {
		swap(Integer.MAX_VALUE);
	}
	
	public void swap(int direction) {
		if (direction < 0) {
			while (++direction <= 0 && prev.prev != null) {
				prev.next = next;
				next.prev = prev;
				next = prev;
				prev = prev.prev;
			}
		}
		if (direction > 0) {
			while (--direction >= 0 && next != null) {
				prev.next = next;
				next.prev = prev;
				prev = next;
				next = next.next;
			}
		}
	}
	
	public Node<X> next() {
		return next;
	}
	
	public Node<X> prev() {
		return prev;
	}
	
}