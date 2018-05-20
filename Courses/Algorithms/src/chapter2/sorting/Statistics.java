package chapter2.sorting;


public class Statistics<E extends Comparable<E>>
{

	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{	
		GeneratorInteger generator = new GeneratorInteger();
		
		DataWriter writer = new DataWriter();
		writer.initialize();
		
		Statistics<Integer> s = new Statistics<Integer>(generator, writer);
		
		// Sets all the sizes of data we want to test.
		s.setDatasizes(500); //50, 75, 100, 125, 150, 200, 250, 500);
		// Set all the different generation methods we want to test.
		s.setMethods(GenerateMethod.Random); //, GenerateMethod.Sorted, GenerateMethod.Reverse);
		// Add all the different sorts we want to test
		s.setSorters(new SortBubble<Integer>(), 
					 new SortSelection<Integer>(), 
					 new SortInsertion<Integer>(), 
					 new SortBinaryInsertion<Integer>(), 
					 new SortShell<Integer>(), 
					 new SortHeap<Integer>(), 
					 new SortMerge<Integer>(), 
					 new SortQuick<Integer>());
		// Set how many times to run per data generation per sort.
		s.setRunsPerGeneration(10);
		
		System.out.println("Testing...");
		
		// Start the testing!
		s.startRuns();
		
		// Finite'
		System.out.println("DONE");
	}
	
	private DataWriter writer;
	private Generator<E> generator;
	private int runsPerGeneration;
	
	private int[] sizes;
	private Sort<E>[] sorters;
	private GenerateMethod[] methods;
	
	/**
	 * Initialize a Statistics class with a data generator and
	 * a writer to output the results to.
	 * 
	 * @param generator => The data (array) generator.
	 * @param writer => The output writer.
	 */
	public Statistics(Generator<E> generator, DataWriter writer)
	{
		this.generator = generator;
		this.writer = writer;
	}
	
	/**
	 * Sets all the different data sizes to test at.
	 */
	public void setDatasizes(int ... sizes)
	{
		this.sizes = sizes;
	}

	/**
	 * Sets all the different sorters to test each generation
	 * method and data sizes.
	 */
	public void setSorters(Sort<E> ... sorters)
	{
		this.sorters = sorters;
	}

	/**
	 * Sets all the different generation methods for the data.
	 */
	public void setMethods(GenerateMethod ... methods)
	{
		this.methods = methods;
	}

	/**
	 * Sets how many times to run per generation per sort.
	 */
	public void setRunsPerGeneration(int runs)
	{
		runsPerGeneration = runs;
	}

	/**
	 * Executes the testing of all the different data sizes,
	 * data generation types, and the different sorting methods.
	 */
	public void startRuns()
	{
		if (runsPerGeneration == 0 ||
			sizes == null ||
			sorters == null ||
			methods == null)
			return;
		
		for (int i = 0; i < sorters.length; i++)
			for (int j = 0; j < methods.length; j++)
				for (int k = 0; k < sizes.length; k++)
					testSort(sorters[i], methods[j], sizes[k]);
		
	}
	
	/**
	 * Tests a specific sort using a generation method and
	 * the size of the data to generate. This will do so
	 * many runs per method for this sort then calculate
	 * the statistics for these methods and print them out
	 * to the data writer.
	 */
	private void testSort(Sort<E> sort, GenerateMethod method, int size)
	{
		// Keep track of the total duration in nanoseconds
		long totalDuration = 0, lowDuration = Long.MAX_VALUE, highDuration = 0;
		double totalSwaps = 0.0, lowSwaps = Double.MAX_VALUE, highSwaps = 0.0;
		double totalComparisons = 0.0, lowComparisons = Double.MAX_VALUE, highComparisons = 0.0;
		
		for (int run = 0; run < runsPerGeneration; run++)
		{
			// Do some garbage collection from previous runs.
			System.gc();
			// Generate the array based on its size and the method used.
			generator.generate(size, method);
			// Sort the array with the current sorter
			sort.sort(generator.getData());
			
			// Tally up all the execution data
			totalDuration += sort.getDuration();
			lowDuration = Math.min(lowDuration, sort.getDuration());
			highDuration = Math.max(highDuration, sort.getDuration());
			
			totalSwaps += sort.getSwaps();
			lowSwaps = Math.min(lowSwaps, sort.getSwaps());
			highSwaps = Math.max(highSwaps, sort.getSwaps());
			
			totalComparisons += sort.getComparisons();
			lowComparisons = Math.min(lowComparisons, sort.getComparisons());
			highComparisons = Math.max(highComparisons, sort.getComparisons());
		}
		
		// Find the averages for all the test ran in nanoseconds
		long avgDuration = (long)((double)totalDuration / (double)runsPerGeneration);
		double avgSwaps = totalSwaps / (double)runsPerGeneration;
		double avgComparisons = totalComparisons / (double)runsPerGeneration;
		
		// Write the statistics to an output
		writer.writeData(sort, method, runsPerGeneration, size, avgDuration, highDuration, lowDuration, avgSwaps, highSwaps, lowSwaps, avgComparisons, highComparisons, lowComparisons);
	}
	
}
