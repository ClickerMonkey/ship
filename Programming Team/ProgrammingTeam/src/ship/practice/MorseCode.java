package ship.practice;

import java.util.Scanner;


/**
 * Source:
 * NONE!
 * 
 * Converts a string to its morse code representation.
 * 
 * @author Philip Diffenderfer
 *
 */
public class MorseCode
{

	// Entry point into application which starts a MorseCode instance.
	public static void main(String[] args)
	{
		MorseCode mc = new MorseCode();
		
		// Create the mapping!
		mc.loadTable();

		// Get input from the user..
		Scanner input = new Scanner(System.in);

		// One line at a time while there exists input...
		while (input.hasNextLine())
		{
			// Gets the last line entered
			String line = input.nextLine();

			// Converts it and print it out.
			mc.displayMorse(line);
		}
	}

	// The map of characters to their morse code.
	private String[] table = new String[256];

	/**
	 * Initializes a new MorseCode class.
	 */
	public MorseCode()
	{
	}

	/**
	 * Lengthy process of mapping each character to its morse code equivalent.
	 */
	public void loadTable()
	{
		// Build the table and add a space to the end to avoid constant
		// concatenation of a space during conversion.
		table['A'] = table['a'] = ".- ";
		table['B'] = table['b'] = "-... ";
		table['C'] = table['c'] = "-.-. ";
		table['D'] = table['d'] = "-.. ";
		table['E'] = table['e'] = ". ";
		table['F'] = table['f'] = "..-. ";
		table['G'] = table['g'] = "--. ";
		table['H'] = table['h'] = ".... ";
		table['I'] = table['i'] = ".. ";
		table['J'] = table['j'] = ".--- ";
		table['K'] = table['k'] = "-.- ";
		table['L'] = table['l'] = ".-.. ";
		table['M'] = table['m'] = "-- ";
		table['N'] = table['n'] = "-. ";
		table['O'] = table['o'] = "--- ";
		table['P'] = table['p'] = ".--. ";
		table['Q'] = table['q'] = "--.- ";
		table['R'] = table['r'] = ".-. ";
		table['S'] = table['s'] = "... ";
		table['T'] = table['t'] = "- ";
		table['U'] = table['u'] = "..- ";
		table['V'] = table['v'] = "...- ";
		table['W'] = table['w'] = ".-- ";
		table['X'] = table['x'] = "-..- ";
		table['Y'] = table['y'] = "-.-- ";
		table['Z'] = table['z'] = "--.. ";
		table['0'] = "----- ";
		table['1'] = ".---- ";
		table['2'] = "..--- ";
		table['3'] = "...-- ";
		table['4'] = "....- ";
		table['5'] = "..... ";
		table['6'] = "-.... ";
		table['7'] = "--... ";
		table['8'] = "---.. ";
		table['9'] = "----. ";
		table['.'] = ".-.-.- ";
		table[','] = "--..-- ";
		table['?'] = "..--.. ";
		table['!'] = "..--. ";
		table[':'] = "---... ";
		table['\\'] = ".-..-. ";
		table['\''] = ".----. ";
		table['='] = "-...- ";
		table[' '] = "   ";
	}

	/**
	 * Converts a given text string to its equivalent morse code string.
	 * 
	 * @param line => The line to convert.
	 * @return => The converted line.
	 */
	public void displayMorse(String line)
	{
		// Convert line to a character array.
		char[] chars = line.toCharArray();

		// For each character get the conversion
		for (char c : chars)
		{
			String converted = table[c];

			// If the character was not specified then set the output to a question mark
			if (converted == null)
				converted = "? ";

			// Print out the conversion
			System.out.print(converted);

		}

		// Move the cursor to the next line.
		System.out.println();
	}

}
