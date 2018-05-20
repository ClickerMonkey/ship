import java.util.Scanner;

public class P1_Raffles {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int x0, y0, x1, y1;
		while (true) {
			x0 = sc.nextInt();
			y0 = sc.nextInt();
			x1 = sc.nextInt();
			y1 = sc.nextInt();
			
			if (x0==0 && y0==0 && x1==0 && y1==0)
				break;

			double dx = x0 - x1;
			double dy = y0 - y1;
			double distance = dx * dx + dy * dy;
			
			if (distance <= 4.0 || distance >= 111513600.0)
				System.out.println("GOOD IDEA");
			else
				System.out.println("BAD IDEA");
		}
	}
}
