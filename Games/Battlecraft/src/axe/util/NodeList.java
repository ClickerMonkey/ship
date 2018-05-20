package axe.util;

import axe.Order;

public class NodeList<X> 
{

	protected Order order = Order.BackToFront;
	protected final Node<X> head = new Node<X>();
	
	public Node<X> add(X x) {
		return new Node<X>(x, head);
	}
	
	public Node<X> first() {
		return head.next();
	}
	
	public Node<X> last() {
		Node<X> c = head;
		while (c.next() != null) {
			c = c.next();
		}
		return (c == head ? null : c);
	}
	
	public void clear() {
		traverse(Order.BackToFront, new NodeTraverse<X>() {
			public void onTraverse(Node<X> node, X x) {
				node.remove();
			}
		});
	}
	
	public void traverse(NodeTraverse<X> traverse) {
		traverse(order, traverse);
	}
	
	public void traverse(Order order, NodeTraverse<X> traverse) 
	{
		Node<X> current, next;
		switch (order) {
		case BackToFront:
			current = first();
			while (current != null) {
				next = current.next();
				traverse.onTraverse(current, current.x);
				current = next;
			}
			break;
		case FrontToBack:
			current = last();
			while (current != null && current != head) {
				next = current.prev();
				traverse.onTraverse(current, current.x);
				current = next;
			}
			break;
		}
	}
	
	public Order order() {
		return order;
	}
	
	public void order(Order order) {
		this.order = order;
	}
	
}
