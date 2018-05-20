package ship.acm_10_25_08_shipred;

import java.util.ArrayList;
import java.util.Scanner;

/**

4
1.0 2 1 0.75 2 0.98
1.25 1 3 0.9
0.75 1 3 0.5
0.9 0
6
1.0  2  1 0.9  2 0.9
0.95 2  2 0.7  3 0.6
0.95 1  4 0.8
1.0  1  4 0.4
1.25 1  5 1.0
1.1  0
0
 */

public class SignalStrength
{

	public static void main(String[] args)
	{
		new SignalStrength();
	}
	
	public SignalStrength()
	{
		Scanner sc = new Scanner(System.in);
		Node nodes[];
		Node n;
		Path p;
		int network = 0, total;
		
		while ((total = sc.nextInt()) != 0)
		{
			network++;
			nodes = new Node[total];
			for (int i = 0; i < total; i++)
				nodes[i] = new Node();
			for (int i = 0; i < total; i++)
			{
				nodes[i].index = i;
				nodes[i].value = sc.nextDouble();
				// How many paths
				int paths = sc.nextInt();
				nodes[i].paths = new Path[paths];
				// Read in paths
				for (int j = 0; j < paths; j++)
					nodes[i].paths[j] = new Path(nodes[i], nodes[sc.nextInt()], sc.nextDouble());
			}
			
			nodes[0].maxValue = nodes[0].value;
			
			// Setup the previous'
			for (int i = 0; i < total; i++)
			{
				for (int j = 0; j < nodes[i].paths.length; j++)
				{
					p = nodes[i].paths[j];
					p.next.previous.add(p);
				}
			}
			
			if (nodes[total - 1].previous.size() == 0)
			{
				System.out.format("Network %d: %.2f\n", network, 0.0);
			}
			else
			{
				// Compute the max'
				for (int i = 1; i < total; i++)
				{
					n = nodes[i];
					for (int j = 0; j < n.previous.size(); j++)
					{
						p = n.previous.get(j);
						double value = n.value * p.value * p.previous.maxValue;
						if (value > n.maxValue)
							n.maxValue = value;
					}
				}
				
				System.out.format("Network %d: %.2f\n", network, nodes[total - 1].maxValue);
			}

		}
	}
	
	private class Node
	{
		public double value = 0.0;
		public int index = 0;
		public Path paths[];
		public ArrayList<Path> previous = new ArrayList<Path>();

		public double maxValue = 0.0;
	}
	
	private class Path
	{
		public double value;
		public Node next;
		public Node previous;
		
		public Path(Node p, Node n, double v)
		{
			value = v;
			previous = p;
			next = n;
		}
	}
	
}
