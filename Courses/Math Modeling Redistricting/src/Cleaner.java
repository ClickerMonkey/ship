import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Scanner;


public class Cleaner {

	public static void main(String[] args) throws Exception {
		new Cleaner().run();
	}
	
	public void run() throws Exception {
		Scanner input = new Scanner(
				new BufferedInputStream(
						new FileInputStream("pacities.out.csv")));
		PrintStream output = new  PrintStream("pacities.clean.csv");
		
		String line = null;
		while (input.hasNextLine()) {
			line = input.nextLine();
			line = line.replace("\"", "");
			output.println(line);
		}
		
		input.close();
		output.close();
	}
	
}
