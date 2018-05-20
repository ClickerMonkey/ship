package ship.practice;

import java.util.Arrays;
import java.util.Scanner;


public class Pairsumonious
{

	public static void main(String[] args) {
		new Pairsumonious();
	}
	
	public Pairsumonious() {
		Scanner input = new Scanner(System.in);
		
		while (input.hasNextLine()) {
			
			int n = input.nextInt();
			int total = sums(n);
			int sum[] = new int[total];
			for (int i = 0; i < total; i++) {
				sum[i] = input.nextInt();
			}
			
			int[] solution = solve(n, toSet(sum));
			
			if (solution == null) {
				System.out.println("No Solution.");
			}
			else {
				display(solution);
			}
		}
	}

	private int[] solve(int n, int[] nums) {
		int solution[] = new int[n + 1];
		boolean solved = false;
		for (int j = 0; !solved && j < nums[0]; j++) {
			
			solution[0] = j;
			for (int y = 1; y < nums.length; y++) {
				solution[y] = other(solution[y - 1], nums[y]);
			}
			solution[n] = other(solution[n - 1], nums[0]);

			solved = (solution[0] == solution[n]);
		}
		return (solved ? solution : null);
	}
	
	private void display(int[] solution) {
		for (int k = 0; k < solution.length; k++) {
			if (k > 0) System.out.print(" ");
			System.out.print(solution[k]);
		}
		System.out.println();	
	}
	
	private int sums(int x) {
		return x * (x - 1) / 2;
	}
	private int other(int x, int n) {
		return n - x;
	}
	private int index(int x, int n) {
		return (x < n / 2 ? x - 1 : n - x - 1);
	}
	
	// Given an arry of numbers, this will remove all duplicates and sort it
	// in descording order.
	private int[] toSet(int[] list) { 
		Arrays.sort(list);
		int n = list.length;
		int k = 0;
		int unique = 0;
		while (k < n && unique < n) {
			list[unique] = list[k];
			while (k < n && list[unique] == list[k]) {
				k++;
			}
			unique++;
		}
		int[] result = new int[unique];
		for (int i = 0; i < unique; i++) {
			result[unique - i - 1] = list[i];
		}
		return result;
	}
	
}
