package ship.weekly;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


//Frequency class
public class Word 
{
	static ArrayList<Wurd> dictionary = new ArrayList<Wurd>();

	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		while(input.hasNextLine())
		{
			String line = input.nextLine();
			line = line.replaceAll("[.!?:,;—()-]", " ").toLowerCase();
			Scanner input2 = new Scanner(line);
			while(input2.hasNext())
			{
				String wordString = input2.next();
				Wurd tempWord = get(wordString);
				if(tempWord == null)
					add(wordString);
				else
					tempWord.count++;
			}
		}

		// Sort dictionary based on count
		Collections.sort(dictionary);

		// Print results
		for(int i=0; i < 5 && i < dictionary.size(); i++)
		{
			Wurd tempWord = dictionary.get(i);
			System.out.println(tempWord);
		}
		System.out.println();

	}

	// Return reference to word if found, otherwise return null
	public static Wurd get(String wordString)
	{
		Wurd returnWord = null;
		for(int i=0; i < dictionary.size(); i++)
		{
			Wurd tempWord = dictionary.get(i);
			if(tempWord.word.equals(wordString))
				returnWord = tempWord;
		}
		return returnWord;
	}

	// Add word to dictionary
	public static void add(String wordString)
	{
		Wurd word = new Wurd(wordString, 1);
		dictionary.add(word);
	}


	//"Word to your mother" - Vanilla Ice, 1990
	static class Wurd implements Comparable<Wurd>
	{
		int count;
		String word;

		public Wurd(String word, int count)
		{
			this.word = word;
			this.count = count;
		}

		// Largest count values first, then alphabetical by word
		public int compareTo(Wurd otherWord)
		{
			int returnValue = 0;
			if(count > otherWord.count)
				returnValue = -1;
			if(count < otherWord.count)
				returnValue = 1;
			if(count == otherWord.count)
			{
				if(word.compareTo(otherWord.word) < 0)
					returnValue = -1;
				if(word.compareTo(otherWord.word) > 0)
					returnValue = 1;
			}
			return returnValue;
		}

		public String toString()
		{
			return word + " occurred " + count + " times";
		}
	}

}
// Frequency class
