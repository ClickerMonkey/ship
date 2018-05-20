package ccsce10;


import java.util.*;

/*
3
3
4 110 0 111 1 010 0 100 1
4 001 0 000 1 010 0 011 1
7 011 1 100 0 000 1 010 1 110 1 101 0 111 1

 */
public class Problem5 {

	public static void main(String[] args) {
		new Problem5();
	}
	
	public Problem5() {
		Scanner input = new Scanner(System.in);
		
		int caseCount = input.nextInt();
		int setSize = input.nextInt();
		
		while (--caseCount >= 0) {
			
			int patternCount = input.nextInt();
			
			int[][] patterns = new int[patternCount][setSize];
			int[] outputs = new int[patternCount];
			
			for (int i = 0; i < patternCount; i++) {
				String text = input.next();
				for (int k = 0; k < setSize; k++) {
					patterns[i][k] = text.charAt(k) - '0';
				}
				outputs[i] = input.nextInt();
			}
			
			boolean learned = false;
			int[] weights = new int[setSize];
			for (int i = 0; i < 100000 && !learned; i++) {
				int[] output = new int[patternCount];
				
				for (int k = 0; k < patternCount; k++) {
					output[k] = 0;
					for (int j = 0; j < setSize; j++) {
						output[k] += weights[j] * (patterns[k][j] * 2 - 1);
					}
					if (output[k] >= 0) {
						output[k] = 1;
					}
					else {
						output[k] = 0;
					}
//					if (output[k] != outputs[k]) {
						int e = outputs[k] - output[k];
						for (int j = 0; j < setSize; j++) {
							weights[j] += e * patterns[k][j];
						}
//					}
				}
				
				if (Arrays.equals(output, outputs)) {
					learned = true;
				}
			}
			if (learned) {
				System.out.println("can learn");
			}
			else {
				System.out.println("cannot learn");
			}
		}
	}
	
}
