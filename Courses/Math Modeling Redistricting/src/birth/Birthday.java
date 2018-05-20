package birth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/*
OUTPUT:

Problem #1
Probability (simulated) : 0.556
Actual Probability: 0.538

Problem #2
Probability: 0.870

Problem #3
1, 2
2, 2
3, 3
4, 4
5, 6
6, 8
7, 10
8, 13
9, 16
10, 19
11, 23
12, 27
13, 31
14, 36
15, 41
16, 46
17, 52
18, 58
19, 64
20, 71
21, 78
22, 85
23, 93
24, 101
25, 109
26, 118
27, 127
28, 136
29, 146
30, 156
31, 166
32, 177
33, 188
34, 199
35, 211
36, 223
37, 235
38, 248
39, 261
40, 274
41, 288
42, 302
43, 316
44, 331
45, 346
46, 361
47, 377
48, 393
49, 410
50, 426
51, 443
52, 461
53, 478
54, 496
55, 501
56, 501
57, 501
58, 501
59, 501
60, 501
61, 501
62, 501
63, 501
64, 501
65, 501
66, 501
67, 501
68, 501
69, 501
70, 501
71, 501
72, 501
73, 501
74, 501
75, 501
76, 501
77, 501
78, 501
79, 501
80, 501
81, 501
82, 501
83, 501
84, 501
85, 501
86, 501
87, 501
88, 501
89, 501
90, 501
91, 501
92, 501
93, 501
94, 501
95, 501
96, 501
97, 501
98, 501
99, 501



 */
public class Birthday 
{

	public static void main(String[] args) 
	{
		// Generate data with 1,000 trials.
		final int TRIALS = 1000;
		List<Integer> data = repeats(365, 24, TRIALS);
		// Count how many had repeats
		int total = 0;
		for (Integer r : data) {
			if (r > 0) {
				total++;
			}
		}
		
		// Problem #1
		System.out.println("Problem #1");
		System.out.format("Probability: %.3f\n", (double)total / TRIALS);
		System.out.format("Actual Probability: %.3f\n", probability(365, 24));
		System.out.println();
		
		// Problem #2
		// Print out the probability of 20 students picking 1 through 100
		// (inclusive) to have matching numbers.
		System.out.println("Problem #2");
		System.out.format("Probability: %.3f\n", probability(100, 20));
		System.out.println();
		
		// Problem #3
		System.out.println("Problem #3");
		int classSize, N;
		for (classSize = 1; classSize < 100; classSize++) {
			for (N = 2; N <= 500; N++) {
				if (probability(N, classSize) < 0.95) {
					break;
				}
			}
//			System.out.format("Class size of %d with N at %d\n", classSize, N);
			System.out.format("%d, %d\n", classSize, N);
		}
	}
	
	// Random number generator
	public static final Random rnd = new Random();
	
	// A list of repeats for each trial ran.
	public static List<Integer> repeats(int range, int points, int trials) 
	{
		// The list of counters for how many times a number repeated.
		List<Integer> repeats = new ArrayList<Integer>();
		for (int k = 0; k < trials; k++) 
		{
			// The set of integers that have been randomly generated in this trial
			HashSet<Integer> set = new HashSet<Integer>();
			// How many times a number was repeated
			int repeatTotal = 0;
			// For each point in the trial...
			for (int i = 0; i < points; i++) 
			{
				// Generate random number [0, range)
				int x = rnd.nextInt(range);	
				// If the number has been generated already...
				if (set.contains(x)) {
					repeatTotal++;
				}
				// Number has not been generated.
				else {
					set.add(x);
				}
			}
			// Add the number of repeats there were in this trial.
			repeats.add(repeatTotal);
		}
		return repeats;
	}
	
	// Probability of people sharing the same number (out of total)
	public static final double probability(int total, int people) 
	{
		double x = total;	
		double prob = 1f;		
		int person = 0;
		// For each person...
		while (person < people) {
			prob = prob * (x / total);
			x = x - 1;
			person = person + 1;
		}
		return 1f - prob;
	}
	
}
