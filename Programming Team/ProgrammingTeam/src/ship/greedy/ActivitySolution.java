package ship.greedy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class ActivitySolution
{

	public static void main(String[] args)
	{
		new ActivitySolution();
	}
	
	public String INPUT = 
		"1 4\n" +
		"3 5\n" +
		"0 6\n" +
		"5 7\n" +
		"3 8\n" +
		"5 9\n" +
		"6 10\n" +
		"8 11\n" +
		"8 12\n" +
		"2 13\n" +
		"12 14\n";
		
	public ActivitySolution()
	{
		Scanner sc = new Scanner(INPUT);
		ArrayList<Activity> activities = new ArrayList<Activity>();
		
		int start;
		while (sc.hasNextInt())
		{
			start = sc.nextInt();
			if (sc.hasNextInt())
				activities.add(new Activity(start, sc.nextInt()));
			else
				break;
		}
		
		Collections.sort(activities);
		
		Activity last = activities.get(0);
		Activity current;
		
		System.out.print(last.toString() + ", ");
		
		for (int i = 1; i < activities.size(); i++) 
		{
			current = activities.get(i);
			if (current.start >= last.finish)
				System.out.print((last = current).toString() + ", ");
		}
	}
	
	private class Activity implements Comparable<Activity>
	{
		public int start;
		public int finish;
		
		public Activity(int starttime, int finishtime)
		{
			start = starttime;
			finish = finishtime;
		}
		
		public int compareTo(Activity o)
		{
			if (finish == o.finish)
				return start - o.start;
			else
				return finish - o.finish;
		}
		
		public String toString()
		{
			return String.format("(%d -> %d)", start, finish);
		}
	}
	
}
