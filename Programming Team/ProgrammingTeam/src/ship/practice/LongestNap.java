package ship.practice;
import java.util.Scanner;

/**
 * Source:
 * http://uva.onlinejudge.org/external/101/10191.html
 * 
 * @author Philip Diffenderfer
 *
 */
public class LongestNap
{

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		
		// Keep track of what day.
		int currentDay = 1;
		
		// While we still have an integer to grab.
		while (sc.hasNextInt())
		{
			// Grab how many lines of classes
			int count = sc.nextInt();
			
			// Initialize the size of the array with 2 extra slots at the start and end.
			double times[] = new double[count * 2 + 2];
			
			// The first time is always 10:00
			times[0] = 10.0;
			// The last time is always 18:00
			times[count * 2 + 1] = 18.0;
			
			for (int i = 0; i < count; i++)
			{
				// Get the double representation of the current times being read.
				times[i * 2 + 1] = toTime(sc.next());
				times[i * 2 + 2] = toTime(sc.next());
				// Read the rest of the line ignoring what appointment is going on.
				sc.nextLine();
			}
			
			double maxDifference = 0.0;
			double maxTime = 0.0;
			
			// Loop through the times and calculate the difference in time.
			// With the max time keep track of the start time.
			for (int i = 0; i <= count; i++)
			{
				double diff = times[i * 2 + 1] - times[i * 2];
				if (diff > maxDifference)
				{
					maxDifference = diff;
					maxTime = times[i * 2];
				}
			}
			
			// Calculate the starting hour and minutes based on the time in double.
			int startHour = (int)Math.floor(maxTime);
			int startMins = (int)((maxTime - startHour) * 60);
			
			// Calculate the duration of the longest nap in hours and minutes
			int diffHour = (int)Math.floor(maxDifference);
			int diffMins = (int)((maxDifference - diffHour) * 60);
			
			// Format the start time with leading zeros
			String startTime = String.format("%02d:%02d", startHour, startMins);
			
			// Determine the output for longest nap depending if its more then an hour.
			String diffTime = "";
			if (diffHour > 0 && diffMins == 0)
				diffTime = diffHour + " hours.";
			else if (diffHour > 0)
				diffTime = diffHour + " hours and " + diffMins + " minutes.";
			else
				diffTime = diffMins + " minutes.";
			// Show the output
			System.out.print("Day #" + currentDay + ": the longest nap starts at " + startTime + " and will last for " + diffTime + "\n");
			currentDay++;
		}
	}

	/**
	 * Converts a string time to the double counterpart.
	 */
	public static double toTime(String time)
	{
		// The first 2 digits is the hour number.
		int hour = Integer.parseInt(time.substring(0, 2));
		// The second pair of digits after the colon is the minute number.
		int minute = Integer.parseInt(time.substring(3, 5));
		// Return the hour plus the minutes grabbed divided by 60.
		return hour + (minute / 60.0);
	}

	/**
	 * Solves the problem using int's instead of double
	 */
	public static void solveOther()
	{
		Scanner sc = new Scanner(System.in);
		int day = 1;
		while (sc.hasNextInt())
		{
			int count = sc.nextInt();
			int maxDif = 0;
			int maxDifTime = 0;
			int times[] = new int[count * 2 + 2];
			times[0] = 600;
			times[count * 2 - 1] = 1080;
			// This loop grabs the times while calculating the max difference in
			// time.
			for (int i = 0; i <= count; i++)
			{
				// We only want 'count' times
				if (i < count)
				{
					String time1 = sc.next();
					String time2 = sc.next();
					times[i * 2 + 1] = Integer.parseInt(time1.substring(0, 2)) * 60 + Integer.parseInt(time1.substring(3, 5));
					times[i * 2 + 2] = Integer.parseInt(time2.substring(0, 2)) * 60 + Integer.parseInt(time2.substring(3, 5));
				}
				int dif = times[i * 2 + 1] - times[i * 2];
				if (dif > maxDif)
				{
					maxDif = dif;
					maxDifTime = times[i * 2];
				}
			}
			// Format the start time with leading zeros
			String startTime = String.format("%02d:%02d", maxDifTime / 60, maxDifTime % 60);
			// Calculate the hours and minutes.
			int diffHour = maxDif / 60;
			int diffMins = maxDif % 60;
			// Determine the output for longest nap depending if its more then an
			// hour.
			String diffTime = "";
			if (diffHour > 0 && diffMins == 0)
				diffTime = diffHour + " hours.";
			else if (diffHour > 0)
				diffTime = diffHour + " hours and " + diffMins + " minutes.";
			else
				diffTime = diffMins + " minutes.";
			// Show the output
			System.out.print("Day #" + day + ": the longest nap starts at " + startTime + " and will last for " + diffTime + "\n");
			day++;
		}
	}

}
