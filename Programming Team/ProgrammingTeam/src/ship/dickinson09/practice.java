package ship.dickinson09;

// The Incredicoders - Shippensburg University - team08 - rm120
import java.util.Scanner;
public class practice {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		for (int l = sc.nextInt() - 1; l >= 0; l--)
			System.out.println("Practice Makes Perfect".substring(sc.nextInt(), sc.nextInt() + 1));
	}
}
