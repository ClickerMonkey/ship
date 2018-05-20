package graph;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Stack;

/**
4
110 111
111 210
111 220
220 320
 *
 */
public class Prerequisites
{
	
	public static Hashtable<Integer, Node> nodes;
	public static Stack<Node> traversed;
	
	/**
	 * 
	 */
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		
		nodes = new Hashtable<Integer, Node>();
		traversed = new Stack<Node>();
		
		int total = sc.nextInt();
		for (int i = 0; i < total; i++)
		{
			int first = sc.nextInt();
			int second = sc.nextInt();
			// Handle the first class code first
			if (!nodes.containsKey(first))
				nodes.put(first, new Node(first, null));
			
			if (nodes.containsKey(second))
				nodes.get(second).previous = nodes.get(first);
			else
				nodes.put(second, new Node(second, nodes.get(first)));
		}
		
		Node n;
		Enumeration<Node> iter = nodes.elements();
		// Depth First Search
		while (iter.hasMoreElements())
		{
			n = iter.nextElement();
			if (!n.visited)
			{
				depthFirst(n);
				n.visited = true;
				traversed.push(n);
			}
		}
		
		while (!traversed.isEmpty())
			System.out.println(traversed.pop().code);
		
	}

	/**
	 * Performs a depth first search on a node.
	 */
	public static void depthFirst(Node n)
	{
		if (n.previous != null)
			if (!n.previous.visited)
				depthFirst(n.previous);
	}	
	
	private static class Node
	{
		public int code;
		public Node previous;
		public boolean visited = false;
		
		public Node(int code, Node previous)
		{
			this.code = code;
			this.previous = previous;
		}
	}

}
