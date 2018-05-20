package ship.weekly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;


public class NumberThing
{

	public static void main(String[] args) {
		new NumberThing();
	}
	
	/*
213 234 123 764 654 366 734
123 234 345 456 567 678 901
12 21 123 234 546 126 46 364
1234z 23
1234 5555

Case#1: 123, 213, 234, 734, 764, 654
Case#2: 123, 234, 345, 456, 567, 678
Case#3: 12, 126, 21, 123, 234, 364, 46, 546

	 */
	
	int maxLength;
	ArrayList<Node> longest;
	
	NumberThing() 
	{
		Scanner in = new Scanner(System.in);
		
		String line = in.nextLine();
		Pattern accept = Pattern.compile("[ 0-9]+");
		int caseNum = 1;
		
		while (accept.matcher(line).matches()) { 

			longest = new ArrayList<Node>();
			maxLength = 0;
			
			// Build the initial queue of numbers
			LinkedList<Node> queue = new LinkedList<Node>();
			Scanner parser = new Scanner(line);
			while (parser.hasNext()) {
				queue.add(new Node(parser.next()));
			}
			// Sort the queue into ascending
			Collections.sort(queue);
			
			// Pop off one at a time using each item as the root
			int count = queue.size();
			while (--count >= 0) {
				// Take the next item, and initialize it as the root
				Node n = queue.poll();
				n.parent = null;
				n.depth = 1;
				// Search using this as the root and give the remaining items
				search(n, queue);
				// Add the root back in
				queue.offer(n);
			}
			
			// Display solution
			System.out.format("Case#%d: ", caseNum);
			for (int i = 0; i < longest.size(); i++) {
				if (i > 0) {
					System.out.print(", ");
				}
				System.out.print(longest.get(i).number);
			}
			System.out.println();
			
			// Next input case
			line = in.nextLine();
			caseNum++;
		}
	}
	
	// Given a parent and a list of possible children... 
	void search(Node parent, LinkedList<Node> queue)
	{
		// Sort the children into ascending order
		Collections.sort(queue);
		
		// If the current path is larger then the previous...
		if (parent.depth > maxLength) {
			// Clear the existing path
			longest.clear();
			// Save the current path to longest (backtracking)
			savePath(parent);
			// Set the new max length
			maxLength = parent.depth;
		}
		
		// For each possible child..
		int count = queue.size();
		while (--count >= 0) {
			// Take the next item from the queue
			Node n = queue.poll();
			// If it's a possible child initialize it and continue dfs
			if (n.matches(parent)) {
				n.parent = parent;
				n.depth = parent.depth + 1;
				search(n, queue);
			}
			// Add it back in so following paths can use it
			queue.offer(n);
		}		
	}
	
	// Given a node this will add all nodes from root to the given node to the
	// longest list. Obviously uses recursion for backtracking.
	void savePath(Node n) 
	{
		if (n.parent != null) {
			savePath(n.parent);
		}
		longest.add(n);
	}
	
	class Node implements Comparable<Node> 
	{
		// The parent of this node (or null if this node is the root)
		Node parent;
		// The depth of this node
		int depth;
		// This nodes number
		final String number;
		// The count of each digit in the number.
		final int count[] = new int[10];
		
		// Initializes a new node
		Node(String number) 
		{
			this.number = number;
			for (char c : number.toCharArray()) {
				count[c - '0']++;
			}
		}
		
		// Returns true if the node has atleast two similar digits
		boolean matches(Node n) 
		{
			int total = 0;
			for (int i = 0; i < 10; i++) {
				total += Math.min(count[i], n.count[i]);
			}
			return (total >= 2);
		}
		
		// Sorts node based on given number
		public int compareTo(Node o) 
		{
			return number.compareTo(o.number);
		}
	}
	
}
