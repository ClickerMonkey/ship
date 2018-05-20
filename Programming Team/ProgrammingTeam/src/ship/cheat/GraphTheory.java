package ship.cheat;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;


public class GraphTheory
{
	public static void main(String[] args) {
		new GraphTheory();
	}
	GraphTheory() {
		newGraph(5);
		addEdge(0, 1, 5);
		addEdge(1, 2, 4);
		addEdge(0, 2, 8);
		addEdge(1, 1, 2);
		addEdge(2, 3, 2);
		addEdge(1, 3, 12);
		
		dijkstras(0, 3);
		printCost();
		printPath(3);
		
		if (bfs(3, 0)) {
			printPath(0);
		}
	}
	
	void printCost() {
		for (int i = 0; i < 5; i++) {
			System.out.format("Cost to move from 0 to %d: %d\n", i, cost[i]);
		}
	}
	
	void printPath(int target) {
		Stack<Integer> reverse = new Stack<Integer>();
		for (int i = target; i >= 0; i = parent[i]) {
			reverse.add(i);
		}
		while (reverse.size() > 0) {
			System.out.print(reverse.pop());
			System.out.print(" => ");
		}
		System.out.println();	
	}
	
	
	//===========================================================================
	// ADJACENCY MATRIX
	//		Use an adjacency matrix when the number of possible nodes is
	// somewhere around 500 and below.
	//===========================================================================
	
	int nodeCount;		// The number of nodes in the graph
	int edge[][];		// The weight of the edge between x and y (0 = no edge)
	int parent[];		// The parent node of node i. Used for backtracking

	//===========================================================================
	// Initializes the adjaceny matrix
	void newGraph(int n) {
		this.nodeCount = n;
		this.edge = new int[n][n];
		this.parent = new int[n];
	}
	//===========================================================================

	//===========================================================================
	// Adds a directional edge to the graph from the node at the start index
	// to the node at the end index - of the given weight.
	void addEdge(int start, int end, int weight) {
		edge[start][end] = weight;
	}
	//===========================================================================

	//===========================================================================
	// Performs a breadth-first-search to move from start to target. If the path
	// is found then true is returned and the path from start to target is
	// stored backwards in parent.
	boolean bfs(int start, int target) {
		boolean visited[] = new boolean[nodeCount];
		Queue<Integer> queue = new ArrayDeque<Integer>(nodeCount + 2);
		queue.offer(start);
		parent[start] = -1;
		while (queue.size() > 0) {
			int u = queue.poll();
			if (u == target) return true;
			for (int v = 0; v < nodeCount; v++) {
				if (!visited[v]) {
					queue.offer(v);
					visited[v] = true;
					parent[v] = u;
				}
			}
		}
		return false;
	}
	//===========================================================================
	

	//===========================================================================
	// Performs a depth-first-search to move from start to target. If the path
	// is found then true is returned and the path from start to target is
	// stored backwards in parent.
	boolean dfs(int start, int target) {
		boolean visited[] = new boolean[nodeCount];
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(start);
		parent[start] = -1;
		while (stack.size() > 0) {
			int u = stack.pop();
			if (u == target) return true;
			for (int v = 0; v < nodeCount; v++) {
				if (!visited[v]) {
					stack.push(v);
					visited[v] = true;
					parent[v] = u;
				}
			}
		}
		return false;
	}
	//===========================================================================
	

	//===========================================================================
	// Given a search algorith has been performed (bfs, dfs, dijkstras) this
	// shows how to traverse backwards from the target to the source.
	void backtrack(int target) {
		// Traverse nodes
		for (int i = target; i >= 0; i = parent[i]) {
			//.. do whatever with node[i]
		}
		// Traverse edges
		for (int i = target; parent[i] >= 0; i = parent[i]) {
			//.. do whatever with edge[ parent[i] ][i]
		}
	}
	//===========================================================================
	

	//===========================================================================
	// Dijkstras Algorithm finds the shortest path between two nodes. The cost
	// to move from node x to node y is stored in edge[x][y]. This will solve
	// the shortest path problem from every node to the source. To travers
	// the path from the target node to the source you need to backtrack using
	// the parent array. If you are at node i then parent[i] is the node before
	// i in the path from source to target.
	
	int cost[];					// The minimum cost for moving from source to i.
	
	void dijkstras(int source, int target) {
		cost = new int[nodeCount];
		boolean visited[] = new boolean[nodeCount];
		for (int i = 0; i < nodeCount; i++) {
			cost[i] = Integer.MAX_VALUE;
			parent[i] = -1;
		}
		cost[source] = 0;
		for (int i = 0; i < nodeCount; i++) {
			int minIndex = -1;
			for (int j = 0; j < nodeCount; j++) {
				if (!visited[j] && (minIndex == -1 || (cost[j] < cost[minIndex]))) {
					minIndex = j;
				}
			}
			if (cost[minIndex] == Integer.MAX_VALUE) {
				break;
			}
			visited[minIndex] = true;
			for (int j = 0; j < nodeCount; j++) {
				if (edge[minIndex][j] != 0) {
					int newCost = cost[minIndex] + edge[minIndex][j];
					if (newCost < cost[j]) {
						cost[j] = newCost;
						parent[j] = minIndex;
					}
				}
			}
		}
	}
	//===========================================================================
	
}
