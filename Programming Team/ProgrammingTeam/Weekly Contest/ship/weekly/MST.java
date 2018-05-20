package ship.weekly;

import java.util.PriorityQueue;
import java.util.Scanner;

/*

10
21
0 1 6.0
0 2 3.0
0 4 9.0
1 2 4.0
1 3 2.0
1 5 9.0
2 3 2.0
2 4 9.0
2 6 9.0
3 5 9.0
3 6 8.0
4 6 8.0
4 9 18.0
5 6 7.0
5 7 4.0
5 8 5.0
6 8 9.0
6 9 10.0
7 8 1.0
7 9 4.0
8 9 3.0
0

 */

public class MST
{

	public static void main(String[] args) {
		new MST();
	}
	
	int nodeCount;
	int edgeCount;
	boolean[] connected;
	boolean[] visited;
	PriorityQueue<Edge> edges;
	
	MST() {
		Scanner in = new Scanner(System.in);
		
		int cases = 1;
		nodeCount = in.nextInt();
		
		while (nodeCount > 0) {

			visited = new boolean[nodeCount];
			connected = new boolean[nodeCount];
			edges = new PriorityQueue<Edge>();
			
			edgeCount = in.nextInt();
			
			while (--edgeCount >= 0) {
				int start = in.nextInt();
				int end = in.nextInt();
				double weight = in.nextDouble();
				
				edges.add(new Edge(start, end, weight));
			}
	
			double min = solve();
			
			System.out.format("Minimum cable length for case %d: %.2f\n", cases, min);
			
			nodeCount = in.nextInt();
			cases++;
		}
	}
	
	double solve() {
		
		double sum = 0.0;
		int visitedCount = 0;
		int connectCount = 1;
		
		connected[0] = true;
		
		// While edges exist and not all nodes have been visited...
		while (!edges.isEmpty() && connectCount < nodeCount) {

			// Grab the edge with the least weight on the queue.
			Edge e = edges.poll();
			
			// If both ends of the edge can be reached from 0, then its already
			// in the tree.
			if (connected[e.a] && connected[e.b]) {
				continue;
			}
			
			// If the first node has not been visited, visit it.
			if (!visited[e.a]) {
				visitedCount++;
				visited[e.a] = true;
			}

			// If the second node has not been visited, visit it.
			if (!visited[e.b]) {
				visitedCount++;
				visited[e.b] = true;
			}
			
			// If a is not connected to 0 but b is, connect it.
			if (!connected[e.a] && connected[e.b]) {
				connectCount++;
				connected[e.a] = true;
			}
			// If b is not connected to 0 but a is, connect it.
			if (!connected[e.b] && connected[e.a]) {
				connectCount++;
				connected[e.b] = true;
			}
			
			// Increase the sum by the weight of the chosen edge.
			sum += e.weight;
		}
		
		return sum;
	}
	
	class Edge implements Comparable<Edge> {
		int a;
		int b;
		double weight;
		Edge(int start, int end, double weight) {
			this.a = start;
			this.b = end;
			this.weight = weight;
		}
		public int compareTo(Edge e) {
			return (int)Math.signum(weight - e.weight);
		}
	}
	
}
