package ship.practice.unfinished;

import java.util.Scanner;

/**
 * a b c d e f g h i
 * 0 1 2 3 4 5 6 7 8
EXAMPLE INPUT

   (1)-8-(2)-7-(3)
 4 /|   2/ \    |\ 9
  / |   /   \   | \
(0) 1 (8)    4 14 (4)
  \ |7/ \ 6   \ | /
 8 \|/   \     \|/ 10
   (7)-1-(6)-2-(5)

9
2 1 4 7 8
3 0 4 2 8 7 1
4 1 8 8 2 5 4 3 7
3 2 7 5 14 4 9
2 3 9 5 10
4 6 2 2 4 3 14 4 10
3 7 1 5 2 8 6
4 0 8 1 1 8 7 6 1
3 2 2 6 6 7 7 

 */
public class MinimumSpanningTree
{
	//9079
	private static int[][] graph;
	
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		
		// INPUT
		int total = input.nextInt();
		graph = new int[total][total];
		
		//neighbors [neighbor_index edge_value]...
		for (int i = 0; i < total; i++)
		{
			int neighbors = input.nextInt();
			for (int j = 0; j < neighbors; j++)
			{
				int neighbor = input.nextInt();
				int cost = input.nextInt();
				graph[i][neighbor] = cost;
			}
		}
		
		// ALGORITHM (array)
		boolean visited[] = new boolean[total];
		int totalVisited = total;
		int keys[] = new int[total];
		int pi[] = new int[total];
		
		for (int i = 0; i < total; i++)
			keys[i] = Integer.MAX_VALUE;		
		keys[0] = 0;
		pi[0] = -1;
		
		while (totalVisited != 0)
		{
			// Find min of 'Q', u
			int uValue = Integer.MAX_VALUE;
			int u = -1;
			for (int i = 0; i < total; i++)
			{
				if (!visited[i] && keys[i] < uValue)
				{
					uValue = keys[i];
					u = i;
				}
			}
			if (u == -1)
				break;
			visited[u] = true;
			totalVisited++;
			
			// For each neighbor
			for (int v = 0; v < total; v++)
			{
				// If v exists in 'Q', and the weight at edge [u,v] < keys[v]
				if (!visited[v] && graph[u][v] != 0 && graph[u][v] < keys[v])
				{
					pi[v] = u;
					keys[v] = graph[u][v];
				} 
			}
		}
		
		for (int i = 0; i < total; i++)
			System.out.println(pi[i] + " => " + keys[i]);
		
	}
	
}
