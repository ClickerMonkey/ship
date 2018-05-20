package axe.util;

public interface NodeTraverse<X> {
	public void onTraverse(Node<X> node, X x);
}
