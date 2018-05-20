package ship.dickinson10;

import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Philip Diffenderfer
 */
public class vanity {

	/*
5
1-888-FLY-ASAP
1-800-SKI-TRIP
1-877-YES-HOME
1-888-Tax-Firm
1-800-4UR-Pets

	 */
	
	public static void main(String[] args) {
		new vanity();
	}
	
	vanity() {
		// A huge frickin mapping for a-z, 0-9, and '-' (took 60 secs to make lol)
		HashMap<Character, Character> map = new HashMap<Character, Character>();
		map.put('a', '2');
		map.put('b', '2');
		map.put('c', '2');
		map.put('d', '3');
		map.put('e', '3');
		map.put('f', '3');
		map.put('g', '4');
		map.put('h', '4');
		map.put('i', '4');
		map.put('j', '5');
		map.put('k', '5');
		map.put('l', '5');
		map.put('m', '6');
		map.put('n', '6');
		map.put('o', '6');
		map.put('p', '7');
		map.put('q', '7');
		map.put('r', '7');
		map.put('s', '7');
		map.put('t', '8');
		map.put('u', '8');
		map.put('v', '8');
		map.put('w', '9');
		map.put('x', '9');
		map.put('y', '9');
		map.put('z', '9');
		map.put('-', '-');
		map.put('1', '1');
		map.put('2', '2');
		map.put('3', '3');
		map.put('4', '4');
		map.put('5', '5');
		map.put('6', '6');
		map.put('7', '7');
		map.put('8', '8');
		map.put('9', '9');
		map.put('0', '0');
		
		Scanner in = new Scanner(System.in);
		
		int caseCount = in.nextInt();
		in.nextLine();
		
		while (--caseCount >= 0) {
			
			// Get the line (in lower case)
			String line = in.nextLine().toLowerCase();
			char[] chars = line.toCharArray();
			
			// Map each char in the input to its actual char.
			for (char c : chars) {
				System.out.print(map.get(c));
			}
			System.out.println();
		}
	}
	
}
