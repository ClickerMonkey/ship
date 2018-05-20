package chapter3;

import java.awt.geom.Point2D;

public class HamiltonCircuit
{

	public static void main(String[] args)
	{
		new HamiltonCircuit(10);
	}
	
	public int N;
	public Point2D.Double[] points;
	public double[][] graph;
	// Statistics
	public int distanceCalculations = 0;
	
	private class Path
	{
		private boolean[] visited;
//		private int[] path;
		private double distance;
		private int index;
		private int depth;
		
		public Path(int myIndex, int parentsIndex, int parentsDepth, double parentsDistance, boolean[] parentsVisited)
		{
			// Setup the visited points in this path already.
			visited = new boolean[N];
			for (int j = 0; j < N; j++)
				visited[j] = parentsVisited[j];
			// Set this point in the path index based on the parents
			index = myIndex;
			// Increase the depth
			depth = parentsDepth + 1;
			// Set this point in this path as true
			visited[index] = true;
			// Calculate the distance from this index to the parents index
			double distance = graph[parentsIndex][myIndex];
			// If the distance hasn't been calculated yet do it
			if (distance == -1)
			{
				double dx = points[parentsIndex].x - points[myIndex].x;
				double dy = points[parentsIndex].y - points[myIndex].y;
				distance = Math.sqrt(dx * dx + dy * dy);
				graph[parentsIndex][myIndex] = distance;
				graph[myIndex][parentsIndex] = distance;
				
				distanceCalculations++;
			}
			// Add the distance on
			distance += parentsDistance;
		}
		
		public void branch()
		{
//			Path p;
			for (int i = 0; i < N; i++)
			{
				if (visited[i]) continue;
				
				new Path(i, index, depth, distance, visited);
			}
		}
		
	}
	
	public HamiltonCircuit(int n)
	{
		N = n;
		// Create N random points between -100 and 100
		points = new Point2D.Double[N];
		for (int i = 0; i < N; i++)
			points[i] = new Point2D.Double(Math.random() * 200 - 100, Math.random() * 200 - 100);
		// Create the graph, fill it with -1, so we can calculate as we go.
		graph = new double[N][N];
		for (int y = 0; y < N; y++)
			for (int x = 0; x < N; x++)
				graph[y][x] = -1;
		// Create the first point
		Path p = new Path(0, 0, 0, 0.0, new boolean[N]);
		// Solve it!
		p.branch();
	}
	
}
