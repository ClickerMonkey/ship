package ship.pacise09;

import java.util.Scanner;


public class Crossword
{

	public static void main(String[] args) {
		new Crossword();
	}
	
	// 12
	// 21
	// 
	// 123
	// 132
	// 213
	// 231
	// 312
	// 321
	//
	// 1234
	// 1243
	// 1324
	// 1342
	// 1423
	// 1432
	// 2134
	// 2143
	// 2314
	// 2341
	
	// 12345
	// 12354
	// 12435
	// 12453
	// 12534
	// 12543
	// 13245
	// 13254
	// 13425
	// 13452
	
	
	boolean solved;
	
	Crossword() {
		Scanner in = new Scanner(System.in);
		
		while (in.hasNextLine()) {
			
			String[] w = {
					in.nextLine(),
					in.nextLine(),
					in.nextLine(),
					in.nextLine()	
			};
			
			solved = false;
			solve(w[0], w[1], w[2], w[3]);
			solve(w[0], w[1], w[3], w[2]);
			solve(w[0], w[3], w[2], w[1]);
			solve(w[0], w[1], w[2], w[3]);
			solve(w[0], w[1], w[2], w[3]);
			solve(w[0], w[1], w[2], w[3]);
			solve(w[0], w[1], w[2], w[3]);
			solve(w[0], w[1], w[2], w[3]);
		}
	}
	
	public void solve(String w1, String w2, String w3, String w4) {
		
	}
	
}
