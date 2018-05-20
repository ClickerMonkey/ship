package ccsce10;

import java.util.Scanner;

/*
3
2
1 4
1
5
6
2 5 7 8 32 5

 */
public class Practice {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		for (int i = in.nextInt(); i > 0; i--) {
			int sum = 0;
			for (int n = in.nextInt(); n > 0; n--) {
				sum += in.nextInt();
			}
			System.out.println(sum);
		}
//		new Practice();
	}
	
	int index = 0;
	int setSize = 6;
	int subsetSize = 4;
	int subsetMax = setSize - subsetSize;
	int[] subset = new int[subsetSize];
	
	public Practice() {
		this.init();	

		for (int i = 0; i < 6; i++) {
			this.print();
			this.next();
		}
	}
	
	public void init() {
		for (int i = 0; i < subset.length; i++) {
			subset[i] = i;
		}
	}
	
	public void next() {
		int i = subsetSize - 1;
		while (i >= 0 && subset[i] == setSize - i) {
			i--;
		}
		subset[i]++;
		int j = i + 1;
		while (j < subsetSize) {
			subset[j] = ++j;
		}
	}
	
	public void print() {
		for (int i = 0; i < subsetSize; i++) {
			if (i > 0) System.out.print(" ");
			System.out.print(subset[i]);
		}
		System.out.println();
	}
}
