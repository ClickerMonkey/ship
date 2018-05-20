package chapter6;

import chapter2.sorting.DataWriter;
import chapter2.sorting.GenerateMethod;
import chapter2.sorting.GeneratorInteger;
import chapter2.sorting.SortHeap;
import chapter2.sorting.Statistics;

public class Heap 
{

	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		GeneratorInteger generator = new GeneratorInteger();
		
		DataWriter writer = new DataWriter();
		writer.initialize();
		
		Statistics<Integer> s = new Statistics<Integer>(generator, writer);
		
		// Sets all the sizes of data we want to test.
		s.setDatasizes(100, 1000, 10000); //50, 75, 100, 125, 150, 200, 250, 500);
		// Set all the different generation methods we want to test.
		s.setMethods(GenerateMethod.Random, GenerateMethod.Sorted, GenerateMethod.Reverse);
		// Add all the different sorts we want to test
		s.setSorters(new SortHeap<Integer>());
		// Set how many times to run per data generation per sort.
		s.setRunsPerGeneration(10);
		
		System.out.println("Testing...");
		
		// Start the testing!
		s.startRuns();
		
		// Finite'
		System.out.println("DONE");
	}
	

}
