package ship.cheat;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class CheatSheet
{

	//===========================================================================
	// Number Theory
	//===========================================================================

	public int primeCount;
	public long[] primes;
	// Creates a prime table with a maximum of primeMax primes. If the table is not
	// large enough for determining the primality of a given integer then it will
	// result in an IndexOutOfBoundsException. The largest prime on the table required
	// to determine the primality of n is sqrt(n).
	public void createPrimeTable(int primeMax) {
		primes = new long[primeMax];
		primes[0] = 2; 
		primes[1] = 3;
		primeCount = 2;
	}
	// Determines the primality of the given integer n. This builds up a table of
	// primes if any are needed. If the table possibly contains the integer n in
	// list of primes then a binary search is done on the table. If the table does
	// not contain n but does contain >=sqrt(n) then a simple primality test is done.
	// If the table does not contain enough data to determine the primality of n 
	// then it is filled until it does.
	public boolean isPrime(long n) {
		if (n == 1) return false;					// 1 is not prime
		if (n == 2) return true;					// 2 is prime
		if ((n & 1) == 0) return false;			// Any even number is not prime
		int x = primeCount - 1;						// The index of the last prime generated
		if (n <= primes[x]) {						// If the prime has already been generated...
			int index = findPrime(n);				// Do a binary search for the prime..
			return (primes[index] == n);			// Was the prime found a match?
		}
		if (n < primes[x] * primes[x]) {			// If the primality can be determined with the table...
			return isPrimeReady(n);					// Return its primality
		}
		buildUpPrimes(n);								// Build the table up
		return isPrimeReady(n);						// Return its primality
	}
	// Does a binary search of the given integer n and returns the index to the closest number in the prime table.
	public int findPrime(long n) {
		int mid, min = 0, max = primeCount - 1;
		do {
			mid = (min + max) >> 1;
		if (n > primes[mid])		
			min = mid + 1;
		else
			max = mid - 1;
		} while ((primes[mid] != n) && (min < max));
		return mid;
	}
	// Determines the primality of n with only primes on the table.
	public boolean isPrimeReady(long n) {
		int i = 0;
		while (n >= primes[i] * primes[i]) {
			if (n % primes[i] == 0)
				return false;
			i++;
		}
		return true;
	}
	// Determines the next prime after the prime at the given index.
	public long nextPrime(int index) {
		long prime = primes[index] + 2;
		while (!isPrimeReady(prime))
			prime += 2;
		return prime;
	}
	// Builds up the list of primes until the table contains enough to determine the primality of n.
	private void buildUpPrimes(long n) {
		int last = primeCount - 1;
		while (n >= primes[last] * primes[last]) {
			primes[primeCount++] = nextPrime(last++);
		}
	}



	//	public static void main(String[] args) {
	//		CheatSheet cs = new CheatSheet();
	//		
	//		// Stores up to 1,000,000 primes.
	//		cs.createPrimeTable(1000000);
	//		// Builds up all primes up to the given integer. 
	//		cs.buildUpPrimes(100000);
	//		// Returns the primality of the given integer.
	//		if (cs.isPrime(98245165375L)) {
	//			//...
	//		}
	//	}


	//	public static void main(String[] args) {
	//		CheatSheet cs = new CheatSheet();
	//		cs.createMatrix(3, 4);
	//		cs.matrix[0][0].set(2,1);
	//		cs.matrix[1][0].set(-3,1);
	//		cs.matrix[2][0].set(-2,1);
	//		cs.matrix[0][1].set(1,1);
	//		cs.matrix[1][1].set(-1,1);
	//		cs.matrix[2][1].set(1,1);
	//		cs.matrix[0][2].set(-1,1);
	//		cs.matrix[1][2].set(2,1);
	//		cs.matrix[2][2].set(2,1);
	//		cs.matrix[0][3].set(8,1);
	//		cs.matrix[1][3].set(-11,1);
	//		cs.matrix[2][3].set(-3,1);
	//		cs.rref();
	//		for (int r = 0; r < 3; r++) {
	//			System.out.print("[");
	//			for (int c = 0; c < 4; c++) {
	//				System.out.format(" %2d/%2d ", cs.matrix[r][c].num, cs.matrix[r][c].den);
	//			}
	//			System.out.println("]");
	//		}
	//	}

	//===========================================================================
	// Computational Geometry
	//===========================================================================



	
	
	public class Point {
		public double x, y;
		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
		// Normalizes this Vector (length is 1)
		public void normalize() {
			if (x != 0.0 && y != 0.0) {
				double invlength = 1.0 / length();
				x *= invlength;
				y *= invlength;
			}
		}
		// Returns the euclidean distance this point is from the origin
		public double length() {
			return Math.sqrt(x * x + y * y);
		}
		// Returns which side the given vector is from this vector. If they're pointing
		// in the same direction 0 is returned. If the given vector is on the left of
		// this vector then it returns 1, if its to the right it returns -1.
		public int sign(Point p) {
			double cross = (y * p.x - x * p.y); 
			return (cross==0 ? 0 : (cross<0 ? -1 : 1));
		}
		// Rotates this vector around the origin by the given angle
		public void rotate(double radians) {
			double cos = Math.cos(radians);
			double sin = Math.sin(radians);
			double t = x;
			x = x * cos - y * sin;
			y = y * cos + t * sin;
		}
		// Rotates this vector around the origin given a normalized vector. The angle
		// between the normalized vector and the positive-x-axis is the angle of rotation
		public void rotate(Point normal) {
			double t = x;
			x = x * normal.x - y * normal.y;
			y = y * normal.x + t * normal.y;
		}
	}

	//	public static void main(String[] args) {
	//		CheatSheet cs = new CheatSheet();
	//		cs.createPoints(10);
	//		cs.setPoint(0, -5, 0);
	//		cs.setPoint(1, 10, 0);
	//		cs.setPoint(2, 0, 10);
	//		cs.setPoint(3, 0, 5);
	//		cs.setPoint(4, 0, 0);
	//		cs.setPoint(5, -2, 5);
	//		cs.setPoint(6, 3, 6);
	//		cs.setPoint(7, 10, 3);
	//		cs.setPoint(8, -10, 7);
	//		cs.setPoint(9, -10, 0);
	//		int total = cs.convexHull();
	//		System.out.println(total);
	//		for (int i = 0; i < total; i++) {
	//			System.out.format("(%f, %f)\n", cs.points[i].x, cs.points[i].y);
	//		}
	//	}


	// Convex Hull
	public int pointCount;
	public Point[] points;
	// Creates the array of points given the number of total points.
	public void createPoints(int pointCount) {
		this.pointCount = pointCount;
		this.points = new Point[pointCount + 1];
	}
	// Sets the point at the specified index.
	public void setPoint(int i, double x, double y) {
		points[i] = new Point(x, y);
	}
	// Swaps the points at the given indices.
	private void swapPoints(int i, int j) {
		Point t = points[i];
		points[i] = points[j];
		points[j] = t;
	}
	// Finds the points that make up the convex hull and returns the number of
	// points on its edges. The points {0, hull edges} are the respective edges
	// on the convex hull.
	public int convexHull() {
		// For a polygon 3 points or smaller
		if (pointCount <= 3)								// For polygons of 3 or less points
			return pointCount;
		int lowest = getLowestPoint();				// Find the leftmost lowest point
		swapPoints(0, lowest);							// Make 0th point the leftmost lowest point
		points[pointCount] = points[0];				// Copy the leftmost to the last point

		for (int start = 0; start < pointCount; start++) {
			int next = getNextPoint(start);			// Find the next point in the hull
			if (next == pointCount)						// If we reached our start point...
				return (start + 1);						// Return the number of points in the hull.
			swapPoints(start + 1, next);				// Swap the points.
		}
		return -1;											// Never going to happen.
	}
	// Gets the leftmost and lowest point from all the points.
	private int getLowestPoint() {
		int lowest = 0;
		for (int i = 1; i < pointCount; i++) {
			double dx = points[lowest].x - points[i].x;
			double dy = points[lowest].y - points[i].y;
			if (dx > 0.0 || (dx == 0.0 && dy > 0.0))
				lowest = i;
		}
		return lowest;
	}
	// Given the index of a point this will return the index of the next point
	// on the convex hull going clockwise.
	private int getNextPoint(int start) {
		int end = start + 1;
		for (int next = end + 1; next <= pointCount; next++) {
			if (!ccw(points[start], points[end], points[next])) {
				end = next;
			}
		}
		return end;
	}





	//===========================================================================
	// Graph Theory
	//===========================================================================

	public int nodeCount;
	public int edge[][];
	public int flow[][];
	public int parent[];

	// Prepares a graph for a given number of nodes.
	public void createGraph(int nodeCount) {
		this.nodeCount = nodeCount;
		this.edge = new int[nodeCount][nodeCount];
		this.flow = new int[nodeCount][nodeCount];
		this.parent = new int[nodeCount];
	}
	// Adds a weight to a given edge.
	public void addEdge(int start, int end, int weight) {
		edge[start][end] = weight;
	}
	//Breadth-First-Search of Graph using a Queue.
	public boolean breadthFirstSearch(int start, int target) {
		boolean[] visited = new boolean[nodeCount];
		Queue<Integer> queue = new ArrayDeque<Integer>(nodeCount + 2);
		queue.offer(start);				// Start searching on the first node
		parent[start] = -1;				// Clear its parent.
		while (!queue.isEmpty()) { 	// While nodes exist in the Q
			int u = queue.poll();		// Remove and traverse the next node
			if (u == target)				// If we've reached the target, return success
				return true;
			for (int v = 0; v < nodeCount; v++) {		// Search all nodes...
				// Must be an unvisited node, must have flow left...
				if (!visited[v] && (edge[u][v] > flow[u][v])) {
					queue.offer(v);		// Add it as a possibility for traversal
					visited[v] = true;	// Mark it as visited
					parent[v] = u;			// Save the parent of this node for backtracking
				}
			}
		}
		return false; // The target node has not been reached.
	}
	//Breadth-First-Search of Graph using a Queue.
	public boolean depthFirstSearch(int start, int target) {
		boolean[] visited = new boolean[nodeCount];
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(start);				// Start searching on the first node
		parent[start] = -1;				// Clear its parent.
		while (!stack.isEmpty()) { 	// While nodes exist in the Q
			int u = stack.pop();			// Remove and traverse the next node
			if (u == target)				// If we've reached the target, return success
				return true;
			for (int v = 0; v < nodeCount; v++) {		// Search all nodes...
				// Must be an unvisited node, must have flow left...
				if (!visited[v] && (edge[u][v] > flow[u][v])) {
					stack.push(v);			// Add it as a possibility for traversal
					visited[v] = true;	// Mark it as visited
					parent[v] = u;			// Save the parent of this node for backtracking
				}
			}
		}
		return false; // The target node has not been reached.
	}

	public static void main(String[] args) {
		CheatSheet cs = new CheatSheet();
		cs.createGraph(6);
		cs.addEdge(0, 1, 160);
		cs.addEdge(0, 2, 130);
		cs.addEdge(1, 2, 100);
		cs.addEdge(2, 1, 40);
		cs.addEdge(1, 3, 120);
		cs.addEdge(3, 2, 90);
		cs.addEdge(2, 4, 140);
		cs.addEdge(4, 3, 70);
		cs.addEdge(3, 5, 200);
		cs.addEdge(4, 5, 40);
		if (cs.depthFirstSearch(0, 5)) {
			for (int u = 5; u >= 0; u = cs.parent[u]) {
				System.out.println(u);
			}
		}
	}

	public void backtrackPath(int target) {
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
	// Network Flow
	//===========================================================================

	//Ford-Fulkerson Max-Flow Min-Cut Theorem
	public int maxFlow(int source, int sink) {
		int maxFlow = 0;
		// Initialize the flow matrix (how much has flowed on each edge).
		flow = new int[nodeCount][nodeCount];
		// While there's a path with flow between the source and the sink...
		while (breadthFirstSearch(source,  sink)) {
			// Backtrack and find minimum cut.
			int minCut = Integer.MAX_VALUE; 
			for (int u = sink; parent[u] >= 0; u = parent[u]) {
				minCut = Math.min(minCut, edge[ parent[u] ][u] - flow[ parent[u] ][u]);
			}
			// Backtrack and adjust total flow of all edges.
			for (int u = sink; parent[u] >= 0; u = parent[u]) {
				flow[ parent[u] ][u] += minCut;
			}
			// Add how much flowed in this path.
			maxFlow += minCut;
		}
		return maxFlow;
	}

	//	public static void main(String[] args) {
	//		CheatSheet cs = new CheatSheet();
	//		cs.createGraph(6);
	//		cs.addEdge(0, 1, 160);
	//		cs.addEdge(0, 2, 130);
	//		cs.addEdge(1, 2, 100);
	//		cs.addEdge(2, 1, 40);
	//		cs.addEdge(1, 3, 120);
	//		cs.addEdge(3, 2, 90);
	//		cs.addEdge(2, 4, 140);
	//		cs.addEdge(4, 3, 70);
	//		cs.addEdge(3, 5, 200);
	//		cs.addEdge(4, 5, 40);
	//		cs.shortestPath(0, 5);
	//		cs.print(0, 5);
	//	}

	//===========================================================================
	// Shortest Path
	//===========================================================================

	// Dijkstra's Shortest Path Algorithm
	public void shortestPath(int source, int target) {
		// The distance cost for each node.
		int[] dist = new int[nodeCount];
		// The array to keep track of visited nodes.
		boolean[] visited = new boolean[nodeCount];
		// Clear all parents and distances
		for (int i = 0; i < nodeCount; i++) {
			dist[i] = Integer.MAX_VALUE;
			parent[i] = -1;
		}
		// No cost for the source node.
		dist[source] = 0;
		// Test all nodes until target is found.
		for (int i = 0; i < nodeCount; i++) {
			// Find the index of the shortest unvisited edge
			int minIndex = -1;
			for (int j = 0; j < nodeCount; j++) {
				if (!visited[j] && (minIndex == -1 || (dist[j] < dist[minIndex])))
					minIndex = j;
			}
			// If the minimum is infinity then exit
			if (dist[minIndex] == Integer.MAX_VALUE)
				break;
			// This node has been visited.
			visited[minIndex] = true;
			// Traverse all neighboring nodes connected to minIndex.
			for (int j = 0; j < nodeCount; j++) {
				// The edge must exist...
				if (edge[minIndex][j] != 0) {
					// Calculate the travel distance if we go to this edge.
					int newDistance = dist[minIndex] + edge[minIndex][j];
					// If the new distance is less then track the movement.
					if (newDistance < dist[j]) {
						dist[j] = newDistance;
						parent[j] = minIndex;
					}
				}
			}
		}
	}
	public void print(int start, int target) {
		int current = target;
		do {
			System.out.println(current);
			current = parent[current];
		} while (current != start);
		System.out.println(start);
	}
	
	
	
	int edges[][];		// edge[i][j] = length between direct edge between i and j
	int dist[][];		// dist[i][j] = length of shortest path between i and j
	int path[][];		// path[i][j] = on shortest path from i to j, path[i][j] is the last node before j
	int n;
	
	void solveFloydWarshall() {
		int i, j, k;
		// Copy edge weight over and set each path pointing to itself
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++) {
				dist[i][j] = edges[i][j];
				path[i][j] = i;
			}
		}
		// All diagonal distances is 0
		for (i = 0; i < n; i++) {
			dist[i][i] = 0;	
		}
		// Perform Floyd Warshall Algorithm
		for (k = 0; k < n; k++) {			// length of the current paths
			for (i = 0; i < n; i++) {		// start at node i
				for (j = 0; j < n; j++) {	// point to node j
					int cost = dist[i][k] + dist[k][j]; 
					if (cost < dist[i][j]) {
						dist[i][j] = cost;			// reduce i-->j to smaller one i->k->j
						path[i][j] = path[k][j];	// update path for i-->j
					}
				}
			}
		}
	}
	
	void printPath(int i, int j) {
		if (i != j) printPath(i, path[i][j]);
		System.out.println(j);
	}

	boolean edgeE[][];	// edgeE[i][j] = there exists an edge from i to j
	boolean exists[][];	// exists[i][j] = there exists a path from i to j
	
	void transitiveClosure() {
		int i, j, k;
		// Copy over edges
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++) {
				exists[i][j] = edgeE[i][j];
			}
		}
		// Every node can reach itself
		for (i = 0; i < n; i++) {
			edgeE[i][i] = true;
		}
		// Perform Floyd Warshall Algorithm
		for (k = 0; k < n; k++) {			// length of the current paths
			for (i = 0; i < n; i++) {		// start at node i
				for (j = 0; j < n; j++) {	// point to node j
					exists[i][j] = exists[i][j] || (exists[i][k] && exists[k][j]);
				}
			}
		}
	}
	
	
	
	
}
