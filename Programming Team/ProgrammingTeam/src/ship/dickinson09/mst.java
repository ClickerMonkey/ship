package ship.dickinson09;

import java.util.Scanner;
import java.util.Stack;

// The Incredicoders - Shippensburg University - team08 - rm120
public class mst { // Minimum Spanning Tree
	
	public static void main(String[] args) { 
		new mst();
	}
	
	public double[][] matrix;
	public boolean[] visited;
	public int n;
	
	public mst() {
		Scanner sc = new Scanner(System.in);
		
		n = sc.nextInt();
		
		matrix = new double[n][n];
		visited = new boolean[n];
		
		int edges = sc.nextInt();
		for (int e = 0; e < edges; e++)
		{
			int start = sc.nextInt();
			int end = sc.nextInt();
			double weight = sc.nextDouble();
			matrix[start][end] = weight;
		}
		
		Stack<Integer> nodes = new Stack<Integer>();
		for (int i = 0; i < n; i++)
			nodes.add(i);
		
		while (!nodes.empty())
		{
			int source = nodes.pop();
			if (!visited[source])
				nodes.push(closest(source));
		}
	}
	
	public int closest(int source) {
		int min = -1;
		for (int i = 0; i < n; i++)
			if (matrix[source][i] != 0 && (min == -1 || matrix[source][i] < matrix[source][min]))
				min = i;

		System.out.format("%d => $d: %.2f\n", source, min, matrix[source][min]);
		
		return min;
	}
}
