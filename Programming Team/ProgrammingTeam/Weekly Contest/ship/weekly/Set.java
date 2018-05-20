package ship.weekly;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeSet;



public class Set 
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		for(int counter=1; input.hasNextLine(); counter++)
		{
			Queue<Sett> sets = new LinkedList<Sett>();
			Queue<Character> operations = new LinkedList<Character>();
			Sett tempSet = new Sett();
			String line = input.nextLine();
			String[] words = line.split(" ");

			// Blank line terminates input
			if(line.equals(""))
				break;

			// Add data to the appropriate location
			for(int i=0; i < words.length; i++)
			{
				Character c = words[i].charAt(0);
				if(c == '+' || c == '-' || c == '–')
				{
					operations.add(c);
					sets.add(tempSet);
					tempSet = new Sett();
				}
				else
					tempSet.add(c);
			}
			sets.add(tempSet); // Last set

			// Start with solution as the first set
			Sett solution = null;
			if(sets.peek() != null)
				solution = sets.remove();

			while(sets.peek() != null && operations.peek() != null)
			{
				Character currentOperation = operations.remove();
				Sett currentSet = sets.remove();
				switch(currentOperation)
				{
					case '+':
						solution = solution.union(currentSet);
						break;
					case '-':
					case '–':
						solution = solution.intersection(currentSet);
						break;
				}
			}
			System.out.println("Case#" + counter + ": " + solution);
		}
	}


	// Class for holding and performing set operations
	static class Sett
	{
		TreeSet<Character> elements = new TreeSet<Character>();

		// Add a character to the list of elements
		public void add(Character c)
		{
			elements.add(c);
		}

		// Union between two sets
		public Sett union(Sett other)
		{
			Sett solution = new Sett();
			solution.elements.addAll(elements);
			solution.elements.addAll(other.elements);
			return solution;
		}

		// Intersection between two sets
		public Sett intersection(Sett other)
		{
			Sett solution = new Sett();
			solution.elements.addAll(elements);
			solution.elements.retainAll(other.elements);
			return solution;
		}

		// Pretty output
		public String toString()
		{
			String returnString = "{";
			Iterator<Character> itr = elements.iterator();

			while(itr.hasNext())
			{
				returnString += itr.next();
				if(itr.hasNext())
					returnString += ", ";
			}

			returnString += "}";
			return returnString;
		}
	}

}