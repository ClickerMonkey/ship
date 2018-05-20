import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/*
 696263473543258626345878277483328843247767846333863463673465357
 4355684373
 4355696753
 */

public class PhoneWords
{

	private static ArrayList<String> dictionary;

	private static ArrayList<String> solutions = new ArrayList<String>();

	private static int countLetters(String s)
	{
		int result = 0;
		for (int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			if (c != ' ')
			{
				result++;
			}
		}
		return result;
	}

	// Check to see if a word exists in the dictionary
	public static void getWords(ArrayList<Integer> numberSequence, int begin)
	{

		String dictionaryWord = "";
		String match = "";
		ArrayList<Integer> toRemove = new ArrayList<Integer>();

		for (int i = 0; i < dictionary.size(); i++)
		{
			if (match.length() == dictionaryWord.length() && match.length() > 0)
			{
				for (int j = 0; j < solutions.size(); j++)
				{
					String solution = solutions.get(j);
					if (countLetters(solution) == begin)
					{
						solution += " " + match;
						solutions.add(solution);
						toRemove.add(j);
					}
				}
				if (begin == 0)
				{
					solutions.add(match);
				}
			}
			dictionaryWord = dictionary.get(i).toLowerCase().trim();
			match = "";
			if (numberSequence.size() - begin < dictionaryWord.length())
				continue;

			for (int j = 0; j < numberSequence.size() && j < dictionaryWord.length(); j++)
			{
				int numValue = numberSequence.get(j + begin);
				if (numValue == 2 && ((int)dictionaryWord.charAt(j) < 97 || (int)dictionaryWord.charAt(j) > 99))
				{
					match = "";
					break;
				}
				if (numValue == 3 && ((int)dictionaryWord.charAt(j) < 100 || (int)dictionaryWord.charAt(j) > 102))
				{
					match = "";
					break;
				}
				if (numValue == 4 && ((int)dictionaryWord.charAt(j) < 103 || (int)dictionaryWord.charAt(j) > 105))
				{
					match = "";
					break;
				}
				if (numValue == 5 && ((int)dictionaryWord.charAt(j) < 106 || (int)dictionaryWord.charAt(j) > 108))
				{
					match = "";
					break;
				}
				if (numValue == 6 && ((int)dictionaryWord.charAt(j) < 109 || (int)dictionaryWord.charAt(j) > 111))
				{
					match = "";
					break;
				}
				if (numValue == 7 && ((int)dictionaryWord.charAt(j) < 112 || (int)dictionaryWord.charAt(j) > 115))
				{
					match = "";
					break;
				}
				if (numValue == 8 && ((int)dictionaryWord.charAt(j) < 116 || (int)dictionaryWord.charAt(j) > 118))
				{
					match = "";
					break;
				}
				if (numValue == 9 && ((int)dictionaryWord.charAt(j) < 119 || (int)dictionaryWord.charAt(j) > 121))
				{
					match = "";
					break;
				}
				match += dictionaryWord.charAt(j);
			}
		}

		for (int i = 0; i < toRemove.size(); i++)
		{
			int removeMe = toRemove.get(i);
			solutions.remove(removeMe);
		}
	}

	// Convert
	public static void main(String[] args)
	{

		dictionary = new ArrayList<String>();
		File dictionaryFile = new File("basic-words");
		// File dictionaryFile = new File("/home/david/testwords");

		// Populate dictionary from dictionary file
		try
		{
			Scanner dictInput = new Scanner(dictionaryFile);
			while (dictInput.hasNextLine())
			{
				dictionary.add(dictInput.nextLine());
			}
		}

		// Handle file errors
		catch (Exception e)
		{
			System.err.println(e);
		}

		// Read in sequence of numbers from keyboard
		Scanner input = new Scanner(System.in);
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		String blah = input.nextLine();
		for (int i = 0; i < blah.length(); i++)
		{
			numbers.add(Character.getNumericValue(blah.charAt(i)));
		}

		for (int i = 0; i < numbers.size(); i++)
		{
			getWords(numbers, i);
		}

		// Print solutions
		for (int i = 0; i < solutions.size(); i++)
		{
			String solution = solutions.get(i);
			if(countLetters(solution) == numbers.size()) {
			System.out.println(solutions.get(i));
			}
		}
	}
}
