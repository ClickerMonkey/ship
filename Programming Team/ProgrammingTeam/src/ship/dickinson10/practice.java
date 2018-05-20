package ship.dickinson10;

import java.util.Scanner;

/**
 * @author Philip Diffenderfer
 */
public class practice {

	/**
5 9 7 6
4 12 20 40 12 6
-1

	 */
	public static void main(String[] args) {
		new practice();
	}
	
	practice() {
		Scanner in = new Scanner(System.in);
		
		while (in.hasNextLine()) {
			
			String line = in.nextLine();
			
			Scanner numbers = new Scanner(line);
			
			int sum = 0;
			int total = 0;
			while (numbers.hasNextInt()) {
				total++;
				sum += numbers.nextInt();
			}
			
			if (sum != -1) {
				System.out.format("Minimum total: %d\n", total);
				System.out.format("Maximum total: %d\n", sum);
			}
			else {
				break;
			}
		}
	}
	
}
