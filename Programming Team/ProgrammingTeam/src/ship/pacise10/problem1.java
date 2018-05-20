package ship.pacise10;

import java.util.*;
public class problem1 {
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int A = in.nextInt();
		int B = in.nextInt();
		int caseNum = 1;
		while (A != 0 && B != 0) {

			int total = 0;
			for (int i = A; i <= B; i++) {
				int count = Integer.bitCount(i);
				if ((count & 1) == 1) {
					total++;
				}
			}

			System.out.format("Case %d: %d numbers\n", caseNum, total);

			A = in.nextInt();
			B = in.nextInt();
			caseNum++;
		}
	}

}
