package chapter2.sorting;

import java.io.File;
import java.io.PrintStream;


public class DataWriter 
{

	// Used to separate all results by tabs to be written to a CSV file.
	private static String FORMAT = 
		"%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n";
	
	// The heading of the data
	private static String[] HEADING_TOP = 
	{ "Sorting", "Array", "Total", "Data", "Duration", "Duration", "Duration", "Swap", "Swap", "Swap", "Comparison", "Comparison", "Comparison" };
	
	// Right below the heading
	private static String[] HEADING_BOTTOM = 
	{ "Method", "Type", "Runs", "Size", "Avg. (ns)", "High (ns)", "Low (ns)", "Avg.", "High", "Low", "Avg.", "High", "Low" };
	
	// The last method used, keeps away from repitition.
	private static String lastMethod = "";
	// The last generation type used, keeps away from repititon.
	private static String lastType = "";
	// The last number of runs tested.
	private static int lastRuns = 0;
	// The output data file.
	private File output;
	// The writer to output to (A file or System.out)
	private PrintStream writer;
	
	
	/**
	 * Initializes the Data writer by trying to create an
	 * output file then filling that output file with headings.
	 */
	public void initialize()
	{
		// Get the next data file
		output = getNextDataFile();
				
		// Try to create the writer, if failed write to System.out
		try 
		{
			output.createNewFile();
			writer = new PrintStream(output);
		}
		catch (Exception e) 
		{
			System.out.println("ERROR: Cannot create output file! Writing to System.out");
			
			writer = System.out;
		}
		finally
		{
			System.out.println("Output file: " + output.getAbsolutePath());
		}
		
		// Write the headers
		writer.format(FORMAT, (Object[])HEADING_TOP);
		writer.format(FORMAT, (Object[])HEADING_BOTTOM);
		writer.println();
	}
	
	/**
	 * Gets the next available file to write to.
	 * @return
	 */
	protected File getNextDataFile()
	{
		File data;
		int current = 0;
		
		do
		{
			current++;
			data = new File("Data" + current + ".csv");
			
		} while (data.exists());
		
		return data;
	}
	
	/**
	 * Writes data to the output (whether its a file or System.out)
	 * 
	 * @param sort => The Sort used.
	 * @param method => The method of data generation.
	 * @param runs => The number of total runs executed.
	 * @param dataSize => The number of data elements per run.
	 * @param avgDuration => The average duration in nano seconds.
	 * @param highDuration => The highest duration in nano seconds.
	 * @param lowDuration => The lowest duration in nano seconds.
	 * @param avgSwaps => The average number of swaps.
	 * @param highSwaps => The highest number of swaps.
	 * @param lowSwaps => The lowest number of swaps.
	 * @param avgComparisons => The average number of comparisons.
	 * @param highComparisons => The highest number of comparisons.
	 * @param lowComparisons => The lowest number of comparisons.
	 */
	@SuppressWarnings("unchecked")
	public void writeData(Sort sort, GenerateMethod method, 
			int runs, int dataSize,
			long avgDuration, long highDuration, long lowDuration, 
			double avgSwaps, double highSwaps, double lowSwaps,
			double avgComparisons, double highComparisons, double lowComparisons)
	{
		String name = sort.getClass().getSimpleName();
		String meth = method.toString();
		
		writer.format(FORMAT, 
				// If the current method is equal to the last method write nothing!
				(name.equals(lastMethod) ? "" : (lastMethod = name)),
				// If the current type is equal to the last type write nothing.
				(meth.equals(lastType) ? "" : (lastType = meth)),
				// If the runs are the same write nothing.
				(runs == lastRuns ? "" : (lastRuns = runs)),
				dataSize,
				avgDuration, highDuration, lowDuration,
				(int)avgSwaps, (int)highSwaps, (int)lowSwaps,
				(int)avgComparisons, (int)highComparisons, (int)lowComparisons);
	}
	
}
