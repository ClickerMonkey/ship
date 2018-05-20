package ship.practice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Source:
 * http://acmicpc-live-archive.uva.es/nuevoportal/data/p2110.pdf
 * 
 * @author Philip Diffenderfer
 *
 */
public class TheIsHaveIt
{

	public static void main(String[] args)
	{
		new TheIsHaveIt();
	}

	// The set of verbs available in first-person
	private HashSet<String> verbsFirst;
	// The set of verbs available in third-person
	private HashSet<String> verbsThird;
	// The lines in the current case
	private ArrayList<String> lines;
	// The words in each line in the current case
	private ArrayList<String[]> words;
	
	// Read the input and delegate to the command to analyze it.
	public TheIsHaveIt()
	{
		Scanner sc = new Scanner(System.in);
		
		int verbs = sc.nextInt();

		verbsFirst = new HashSet<String>(verbs);
		verbsThird = new HashSet<String>(verbs);
		
		for (int i = 0; i < verbs; i++)
		{
			verbsFirst.add(sc.next());
			verbsThird.add(sc.next());
		}
		
		// Eliminate the new line after the last verb
		sc.nextLine();
		
		int index = 1;
		String line = sc.nextLine();

		while (!line.equals("END OF DATA"))
		{
			lines = new ArrayList<String>();
			words = new ArrayList<String[]>();
			while (!line.equals("END"))
			{
				lines.add(line);
				words.add(line.split("[ \t]"));
				line = sc.nextLine();
			}
			analyze(index);
			index++;
			
			line = sc.nextLine();
		}
	}
	
	public void analyze(int index)
	{
		System.out.format("STUDENT %d\n", index);

		// The array of words for the current line
		String[] w;
		// The buffer used for placing ? and *
		StringBuffer bottom;
		
		int firsts;
		int thirds;
		
		for (int line = 0; line < lines.size(); line++)
		{
			w = words.get(line);
			// We know the max of the buffer
			bottom = new StringBuffer(lines.get(line).length() + 2);
			
			firsts = 0;
			thirds = 0;
			
			// Analyze each word
			for (int word = 0; word < w.length; word++)
			{
				// Add empty spaces to the bottom
				for (int s = 0; s < w[word].length() + 1; s++)
					bottom.append(' ');
				
				// If the word ends with , ignore it!
				if (w[word].endsWith(","))
					continue;
				
				// If this word is an I
				if (w[word].equals("I"))
				{
					String verb;
					// If there is a verb after the I grab it
					if (word < w.length - 1)
						verb = w[word + 1];
					// Grab the verb on the next line
					else if (line < lines.size() - 1)
						verb = words.get(line + 1)[0];
					// If there is not a verb, and no line below then exit
					else
						continue;
					
					// Remove any other punctuation after the word
					if (verb.endsWith(".") || verb.endsWith("!") ||
						 verb.endsWith(",") || verb.endsWith("?"))
						verb = verb.substring(0, verb.length() - 2);
					
					// Check if the verb exists
					boolean isFirst = verbsFirst.contains(verb);
					boolean isThird = verbsThird.contains(verb);
					
					if (isFirst || isThird)
					{
						// Remove the last space on bottom so we can put our char
						bottom.deleteCharAt(bottom.length() - 2);
						
						// If the verb is the same in both tenses or is in third person...
						if ((isFirst && isThird) || isThird)
						{
							bottom.append('?');
							thirds++;
						}
						// Else it must be in first person!
						else if (isFirst)
						{
							bottom.append('*');
							firsts++;
						}
					}
				}
			}
			
			System.out.format("%3d%3d  %s\n", firsts, thirds, lines.get(line));
			System.out.format("       %s\n", bottom.toString());
			
		}
		// Extra line
		System.out.println();
	}
	
}
