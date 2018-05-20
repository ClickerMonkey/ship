import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Scanner;


public class Validator {

	public static void main(String[] args) throws Exception {
		new Validator().run();
	}
	
	public void run() throws Exception {
		Scanner input = new Scanner(
				new BufferedInputStream(
						new FileInputStream("pacities.out.csv")));
		
		String line = null;
		int lineCount = 0;
		while (input.hasNextLine()) {
			line = input.nextLine();
			lineCount++;
			if (count(line.toCharArray(), ',') != 3) {
				System.out.println("PROBLEM: " + line);
			}
		}
		System.out.println("DONE (" + lineCount + ")");
		
		input.close();
	}
	
	private int count(char[] haystack, char needle) {
		int total = 0;
		for (int i = 0; i < haystack.length; i++) {
			if (haystack[i] == needle) {
				total++;
			}
		}
		return total;
	}
	
}
