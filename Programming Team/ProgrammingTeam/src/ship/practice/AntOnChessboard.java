package ship.practice;
import java.util.Scanner;

/**
 * Source:
 * http://uva.onlinejudge.org/external/101/10161.html
 * 
 * @author Philip Diffenderfer
 *
 */
public class AntOnChessboard {

	
	public static void main(String[] args) {
		new AntOnChessboard();
	}

	//
	public AntOnChessboard() {
		Scanner sc = new Scanner(System.in);
		
		int tile = sc.nextInt();
		
		while (tile != 0) {
			// Print solution
			System.out.println(solve(tile));
			
			tile = sc.nextInt();
		}
	}

	// Determines the column and row the ant is on the chessboard given the
	// index of its square
	String solve(int n)
	{
		// The width of the 'row'
		int wid = (int)Math.ceil(Math.sqrt(n));
		// The power of the 'row' and the greatest number
		int pow = wid * wid;
		// The smallest number on the row 
		int low = (wid - 1) * (wid - 1) + 1;
		// The corner number
		int cor = pow - (wid - 1);
		// If n is greater then the corner number return the width, else calculate the actual value
		int greater = (n >= cor ? wid : n - low + 1);
		// If n is less then the corner number return the width, else calculate the actual value 
		int lesser = (n <= cor ? wid : pow - n + 1);
		// If the power is even its on the bottom row
		int x = (pow % 2 == 0) ? greater : lesser;
		// If the power is odd its on the left column
		int y = (pow % 2 == 1) ? greater : lesser;
		return x + " " + y;
	}

}