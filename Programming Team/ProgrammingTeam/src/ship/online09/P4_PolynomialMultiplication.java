import java.util.Scanner;


public class P4_PolynomialMultiplication {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		while (n != -1) {
			System.out.println(scored(0, n));
			n = sc.nextInt();
		}
	}
	
	private static int scored(int total, int score)
	{
		if (total == score) {
			return 1;
		} else if (total < score) {
			int n = 0;
			n += scored(total + 2, score);
			n += scored(total + 3, score);
			n += scored(total + 6, score);
			n += scored(total + 7, score);
			n += scored(total + 8, score);
			return n;
		} else {
			return 0;
		}
	}
	
}
