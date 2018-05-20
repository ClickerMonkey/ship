import java.util.Scanner;


public class P2_Memoization {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		while (n != 0) {
			long total = 0;
			while (--n >= 0) {
				String s = sc.next();
				for (char c : s.toCharArray())
					total += c - '0';
			}
			System.out.println(total);
			n = sc.nextInt();
		}
	}
}
