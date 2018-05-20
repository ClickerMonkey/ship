package chapter5;

import chapter2.sorting.*;

public class ShellSort 
{

	public static void main(String[] args)
	{	
		// pg 164 a & b
		doStability();
		// pg 164 c
		doComparison();
	}

	@SuppressWarnings("unchecked")
	public static void doComparison()
	{
		GeneratorInteger generator = new GeneratorInteger();
		
		DataWriter writer = new DataWriter();
		writer.initialize();
		
		Statistics<Integer> s = new Statistics<Integer>(generator, writer);
		
		// Sets all the sizes of data we want to test.
		s.setDatasizes(100, 1000); 
		// Set all the different generation methods we want to test.
		s.setMethods(GenerateMethod.Random, GenerateMethod.Sorted, GenerateMethod.Reverse);
		// Add all the different sorts we want to test
		s.setSorters(new SortShell<Integer>(), 
				 	 new SortInsertion<Integer>(), 
					 new SortBinaryInsertion<Integer>(),
					 new SortMerge<Integer>(), 
					 new SortQuick<Integer>());
		// Set how many times to run per data generation per sort.
		s.setRunsPerGeneration(1);
		
		System.out.println("Testing Comparisons...");
		
		// Start the testing!
		s.startRuns();
		
		// Finite'
		System.out.println("DONE");
	}
	
	public static void doStability()
	{
		Char[] data = Char.fromString("SHELLSORTISUSEFUL");
		Sort<Char> shell = new SortShell<Char>();
		
		System.out.println("Testing Stability...");
		System.out.println("Before Sort:");
		printChars(data);

		shell.sort(data);

		System.out.println("After Sort:");
		printChars(data);
		
		System.out.println("Stable: " + (isStable(data) ? "Yes" : "No"));
		
	}
	
	/**
	 * Checks if the sorted array of Chars has maintained stability.
	 */
	public static boolean isStable(Char[] sorted)
	{
		// If two Char have the same value but the first index is NOT less
		// then the second then this is not stable!
		for (int i = 0; i < sorted.length - 1; i++)
			if (sorted[i].compareTo(sorted[i + 1]) == 0 && sorted[i].index > sorted[i + 1].index)
				return false;
		
		return true;
	}
	
	/**
	 * Prints an array of characters to the screen.
	 */
	public static void printChars(Char[] chars)
	{
		int digits = (int)Math.floor(Math.log10(chars.length)) + 1;
		String format = "%" + digits + "s|";

		for (int i = 0; i < chars.length; i++)
			System.out.format(format, chars[i].value);
		System.out.println();
		for (int i = 0; i < chars.length; i++)
			System.out.format(format, chars[i].index);
		System.out.println();
	}
	
}
