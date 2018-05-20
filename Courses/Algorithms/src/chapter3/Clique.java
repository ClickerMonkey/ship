package chapter3;

public class Clique
{

	public static void main(String[] args)
	{
		final boolean X = true;
		final boolean O = false;
		
		Clique c = new Clique();
		c.n = 6;
		c.graph = new boolean[][] {
				{O, X, O, O, X, O},
				{X, O, X, O, X, O},
				{O, X, O, X, O, O},
				{O, O, X, O, X, X},
				{X, X, O, X, O, O},
				{O, O, O, X, O, O}};
		
		System.out.println(c.cliqueExists(3));
	}
	
	public int n;
	public boolean[][] graph;
	
	public boolean cliqueExists(int k)
	{
		// Graphs will always have 1 or 2 size cliques
		if (n >= 2 && k <= 2)
			return true;
		
		// Initialize points
		int points[] = new int[k + 2];
		points[0] = -1;
		points[k + 1] = n + 1;
		// Fill the points with 1 through k
		for (int i = 1; i <= k; i++) 
			points[i] = i;
		// Used as the current pivot to increment from
		int current = 1;
		
		// If k = n then do the quick check.
		if (k == n)
			return isClique(points, k);

		// Finds all combinations of 'n CHOOSE k' without repitition.
		while (current != 0)
		{
			if (isClique(points, k))
				return true;

			current = k;
			// If the current pivot is overflowing then
			// back it up.
			while (points[current] == n - k + current) 
				current--;
			// Increase current to keep things rotating
			points[current]++;
			// Reset the array from after the current to <= k
			// so the i'th point is now the point before it plus one.
			for (int i = current + 1; i <= k; i++)
				points[i] = points[i - 1] + 1;
		} 		
		
		return false;
	}
	
	
	public boolean isClique(int[] points, int k)
	{
		// Check all the points against each other ONCE.
		int x, y;
		for (int i = 1; i < k; i++)
		{
			for (int j = i + 1; j <= k; j++)
			{
				x = points[i] - 1;
				y = points[j] - 1;
				if (!(graph[x][y] && graph[y][x]))
					return false;
			}
		}
		// It must be a clique
		return true;
	}
	
}
