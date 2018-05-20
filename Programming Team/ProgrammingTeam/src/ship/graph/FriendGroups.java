package ship.graph;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;


/**

Carol Greg
Greg Alexa
Carol Logan
Sarah Natasha
Phil Sarah
Elizabeth Henry
Alexa Edward
DONE

 * @author Phil
 *
 */
public class FriendGroups
{


	// The graph of relations
	public static boolean graph[][];
	// The list of visited
	public static boolean visited[];
	
	/**
	 * 
	 */
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		
		// A table of people and their index on the graph.
		Hashtable<String, Integer> people = new Hashtable<String, Integer>();
		// A list of each relation
		ArrayList<Relation> relation = new ArrayList<Relation>();
		
		String friend1, friend2;
		
		// Read in input.
		while (sc.hasNext())
		{
			friend1 = sc.next();
			friend2 = sc.next();
			
			int f1, f2;

			// If the person was added already get their index on the graph.
			if (!people.containsKey(friend1))
			{
				people.put(friend1, people.size());
			}
			
			// If the person was added already get their index on the graph.
			if (people.contains(friend2))
			{
				f2 = people.get(friend2);
			}
			// If the person was not added yet then calculate their index
			// and then add them in using that index.
			else
			{
				f2 = people.size();
				people.put(friend2, f2);
			}
			
			
			// Add their relationship to the list
			relation.add(new Relation(friend1, friend2));
		}
		
		int size = people.size();
		
		// Fill the graph for each relation.
		graph = new boolean[size][size];
		
		for (int i = 0; i < relation.size(); i++)
		{
			Relation r = relation.get(i);
			// Get the index of friend 1 in people
			int f1 = people.get(r.friend1);
			// Get the index of friend 2 in people
			int f2 = people.get(r.friend2);
			// At thoses indices mark them as related (true).
			graph[f1][f2] = true;
			graph[f2][f1] = true;
		}
		
		// Initialize visited
		visited = new boolean[size];
		
		int groups = 0;
		
		for (int i = 0; i < people.size(); i++)
		{
			// Each time a new unvisited person is visited
			// its the start of a new group.
			if (!visited[i])
			{
				groups++;
				depthFirst(i);
			}
		}
		
		// Print out the number of groups
		System.out.println(groups);
	}
	
	/**
	 * Does a depth first search on this person so all
	 * people related are visited.
	 * 
	 * @param person => The index of the person to visit.
	 */
	public static void depthFirst(int person)
	{		
		// This person is now visited!
		visited[person] = true;
		
		for (int i = 0; i < graph.length; i++)
		{
			// If the person was not visited and the person
			// is in my group then visit them.
			if (!visited[i] && graph[i][person])
			{
				depthFirst(i);
			}
		}
	}
	
	
	
	private static class Relation
	{
		String friend1;
		String friend2;
		
		public Relation(String f1, String f2)
		{
			friend1 = f1;
			friend2 = f2;
		}
	}
	
	

}
