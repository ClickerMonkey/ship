import java.util.Scanner;

public class Program {
	public static void main(String[] args) {
		new Program();
	}
	
	public Program() {
		Scanner sc = new Scanner(System.in);
		Game24 game = new Game24();
		System.out.println("Welcome to Phil's 24-Game Solver and Creator!!!");
		System.out.println("This will solve ANY 24 card with any amount of digits.");
		System.out.println("Type in the corresponding number for your choice.");
		System.out.println();
		while (true) {
			System.out.println("Options: Solve[1] Create[2] Exit[3]");
			System.out.print("  Choice: ");
			int choice = sc.nextInt();
			if (choice == 1) {
				System.out.print("    Numbers: (# # # #): ");
				game.solve(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
			} else if (choice == 2) {
				System.out.print("    Number of Digits: ");
				int digits = sc.nextInt();
				System.out.print("    Difficulty (1-3): ");
				int dots = sc.nextInt();
				int[] n = game.create(dots, digits);
				System.out.println("      \\" + n[0] + "/");
				System.out.println("      " + n[1] + "X" + n[2]);
				System.out.println("      /" + n[3] + "\\");
			} else if (choice == 3) {
				break;
			} else {
				System.out.println("Not an option!");
			}
		}
		System.out.println("Thank you come again!");
	}
}
