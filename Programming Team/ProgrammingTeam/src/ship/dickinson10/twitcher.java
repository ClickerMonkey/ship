package ship.dickinson10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Solves the twitcher problem. For every person build their followers. For 
 * every follower of a, check their depth in b. Take the minimum depth and 
 * that's the solution. If all depths were -1 then no solution existed.
 * 
 * @author Philip Diffenderfer, Andrew Marx
 */
public class twitcher {

	/*
5
a
b
a b
b

a
b
a c
b c
c

a
b
b a
a

a
b
a c
b d
c
d c

a
b
a c
b d x y
c
d z q c
x m
y r
z
q
m
r

	 */
	
	public static void main(String[] args) {
		new twitcher();
	}

	// A set of all nodes for this case
	ArrayList<Node> nodes;
	// A mapping of nodes by their names for this case.
	HashMap<String, Node> nodeMap = new HashMap<String, Node>();
	
	twitcher() {
		Scanner in = new Scanner(System.in);
		
		int caseCount = in.nextInt();
		in.nextLine();
		
		while (--caseCount >= 0) {

			// Reset both lists
			nodes = new ArrayList<Node>();
			nodeMap = new HashMap<String, Node>();
			
			// Get a and b.
			Node a = new Node();
			Node b = new Node();
			
			a.name = in.nextLine().trim();
			b.name = in.nextLine().trim();
			
			nodes.add(a);
			nodeMap.put(a.name, a);
			nodes.add(b);
			nodeMap.put(b.name, b);
			
			// Get the next line (starts with person, and then their followers).
			String line = in.nextLine();
			
			// While an empty line hasn't been read...
			while (line.length() != 0) {
				// Go through each name..
				Scanner names = new Scanner(line);

				// The first name is the node.
				String nodeName = names.next();
				// Get the node based on its name
				Node node = nodeMap.get(nodeName);
				// If the node doesn't exist yet, create it and add and map it.
				if (node == null) {
					node = new Node();
					node.name = nodeName;
					nodes.add(node);
					nodeMap.put(node.name, node);
				}
				
				// Get each follower in the line and add the names to the nodes list
				while (names.hasNext()) {
					String childName = names.next();
					node.childrenName.add(childName);
				}
				
				// Next line of person and followers.
				line = in.nextLine();
			}
			
			// Add nodes to children list based on their name.
			buildChildren();
			
			// A minimum depth of A BILLION DOLLARS!!!
			int minDepth = Integer.MAX_VALUE;
			
			// For every child of a...
			for (Node child : a.childrenNode) {
				
				// Get the depth of its child in b.
				int childDepth = depth(b, child);
				
				// If the child exists in b, and it beats the current minimum depth
				// then save it as the min.
				if (childDepth != -1 && childDepth < minDepth) {
					minDepth = childDepth;
				}
			}
			
			// No solution found.
			if (minDepth == Integer.MAX_VALUE) {
				System.out.println(-1);
			}
			// Solution found.
			else {
				System.out.println(minDepth);
			}
		}
		
	}

	/**
	 * For every node this builds their list of child nodes based on their list
	 * of child names (now that all nodes have been mapped by name).
	 */
	void buildChildren() {
		for (Node n : nodes) {
			for (String childName : n.childrenName) {
				Node child = nodeMap.get(childName);
				n.childrenNode.add(child);
			}
		}
	}
	
	/**
	 * Returns the depth of the end node from the start node (-1 if doesn't exist).
	 */
	int depth(Node start, Node end) {
		// Add the starting node to the queue
		Queue<Node> queue = new LinkedList<Node>();
		queue.offer(start);
		// Its depth is 0
		start.depth = 0;
		// While it has children that haven't been searched yet...
		while (!queue.isEmpty()) {
			// Take next node
			Node curr = queue.poll();
			// If its the end then return its depth.
			if (curr == end) {
				return curr.depth;
			}
			// For each child of the current node sets its depth and enqueue it.
			for (Node child : curr.childrenNode) {
				child.depth = curr.depth + 1;
				queue.offer(child);
			}
		}
		// Not found.
		return -1;
	}
	
	// A user and their depth in the follower tree.
	class Node {
		String name;
		int depth;
		
		ArrayList<String> childrenName;
		ArrayList<Node> childrenNode;
		
		Node() {
			childrenName = new ArrayList<String>();
			childrenNode = new ArrayList<Node>();
		}
	}
	
}
