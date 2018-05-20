package ship.practice;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class TheNecklace
{
	
	public static void main(String[] args) {
		new TheNecklace();
	}
	
	public TheNecklace() {
		Scanner input = new Scanner(System.in);
		// Number of cases
		int caseCount = input.nextInt();
		for (int i = 1; i <= caseCount; i++) {
			
			System.out.format("Case#%d:\n", i);
			
			// Number of beads in case
			int beadCount = input.nextInt();
			
			Bead[] beads = new Bead[beadCount];
			
			// Read in beads from input
			for (int k = 0; k < beadCount; k++){
				int c1 = input.nextInt();
				int c2 = input.nextInt();
				beads[k] = new Bead(c1, c2);
			}

			// Loop through every bead and adds its neigbors
			for (int k = 0; k < beadCount; k++) {
				// Current bead
				Bead a = beads[k];
				for (int j = 0; j < beadCount; j++) {
					if (k != j) {
						// Next bead
						Bead b = beads[j];
						// Are they related?
						if (a.has(b.c1) || a.has(b.c2)) {
							// Add b as a neighbor to a
							a.nodes.add(b);
						}
					}
				}
			}
			
			// Find a path through all of the beads
			Bead path = dfs(beads[0], beads);
			
			while (path != null) {
				// Print out path data
				System.out.format("%d %d\n", path.c1, path.c2);
				// BACKTRACK POWA!!!!
				path = path.parent;
			}
			
			System.out.println();
			// WEEEEE
		}
	}

	class Bead {
		boolean visited = false;
		int depth = 0;
		Bead parent;
		ArrayList<Bead> nodes = new ArrayList<Bead>();
		int c1, c2;
		Bead(int c1, int c2) {
			this.c1 = c1;
			this.c2 = c2;
		}
		boolean has(int color) {
			return (c1 == color || c2 == color);
		}
	}
	
	public Bead dfs(Bead start, Bead[] nodes) {
		Stack<Bead> S = new Stack<Bead>();
		S.push(start);
		start.depth = 1;
		Bead end = null;
		while (!S.isEmpty()) {
			Bead n = S.pop();
			if (n.visited) {
				continue;
			}
			if (n.depth == nodes.length) {
				return n;
			}
			
			end = n;
			for (Bead neighbor : end.nodes) {
				if (!neighbor.visited) {
					neighbor.parent = end;
					neighbor.depth = end.depth + 1;
					S.push(neighbor);
				}
			}
			end.visited = true;
		}
		return end;
	}
	
	
	
	
}
