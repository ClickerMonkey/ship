package ship.dickinson10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Solves the lottery problem. Builds the tree, then starting at the root it
 * determines the amount each child gets depending on the share of the parent.
 * 
 * @author Philip Diffenderfer, Keith Porter
 */
public class lottery {

	/*
01234 6 2880 4
01002 9 01234
13579 7 43210
31416 4 01234
43210 5 01234
	
	 */
	public static void main(String[] args) {
		new lottery();
	}
	
	// The winning node
	Node root;
	// All of the nodes in order from input
	ArrayList<Node> nodes = new ArrayList<Node>();
	// A mapping of nodes by their names (id)
	HashMap<String, Node> nodeMap = new HashMap<String, Node>();
	
	
	lottery() {
		Scanner in = new Scanner(System.in);
		
		// A single case, starting with a root node.
		Node root = new Node();
		// Its id, its point, and the winning share
		root.id = in.next();
		root.poin = in.nextInt();
		root.share = in.nextInt();
		// First node, also map it.
		nodes.add(root);
		nodeMap.put(root.id, root);
		
		// How many descendants there are to follow (total)
		int descs = in.nextInt();
	
		nodes.ensureCapacity(descs);
		
		// Read in each descendant
		while (--descs >= 0) {
			// Gets the childs name, its parents name, and its poin(?)
			Node child = new Node();
			child.id = in.next();
			child.poin = in.nextInt();
			child.pid = in.next();
			
			// Add it to the list, and map it by its name
			nodes.add(child);
			nodeMap.put(child.id, child);
		}
		
		// Using names from input, set references
		buildParents();
		buildChildren();
		
		// Solve starting at the root node.
		root.solve();
		
		// In the same order as parsed, print out each nodes share.
		for (Node node : nodes) {
			node.print();
		}
	}
	
	/**
	 * Make sure all nodes (who have their parent names) have a reference to 
	 * their parent node.
	 */
	void buildParents() {
		for (Node n : nodes) {
			if (n.pid != null) {
				n.parent = nodeMap.get(n.pid);
			}
		}
	}
	
	/**
	 * Makes sure all nodes exist in their parents child list (if they have a parent).
	 */
	void buildChildren() {
		for (Node n : nodes) {
			if (n.parent != null) {
				n.parent.children.add(n);
			}
		}
	}
	
	/**
	 * The winner or descendant. Has a unique id, parent id, poin(?), an initial
	 * share, and its final winnings (mine). 
	 */
	class Node {
		String id;
		String pid;
		int poin;
		int share;
		int mine;
		Node parent;
		ArrayList<Node> children;
		
		Node() {
			children = new ArrayList<Node>();
		}
		// Gives this node and its children their appropriate amounts.
		void solve() {
			// Total poin(?) of this node and all children
			int total = poin;
			for (Node child : children) {
				total += child.poin;
			}

			// How much of it is mine?
			mine = poin * share / total;
			
			// How much does each child get to divide up to its children?
			for (Node child : children) {
				child.share = child.poin * share / total;
			}
			
			// Make sure all children divy up their share.
			for (Node child : children) {
				child.solve();
			}
		}
		// A node in awesome form.
		void print() {
			System.out.format("%s %d\n", id, mine);
		}
	}
	
}
