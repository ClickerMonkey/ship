package ship.practice;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Map.Entry;


public class WordCounter
{

	public static void main(String[] args)
	{
		try
		{
			new WordCounter();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public WordCounter() throws FileNotFoundException
	{
		File file = new File("src/ship/practice/input.txt");
		Scanner sc = new Scanner(file);
		
		// Total number of words in the current paragraph.
		int total = 0;
		// The table of hashed words
		Hashtable<String, Integer> words = new Hashtable<String, Integer>();
		
		String line;
		
		while (sc.hasNextLine())
		{
			line = sc.nextLine();
			
			if (line.equals("")) 
			{
				print(words, total);
				total = 0;
				words.clear();
			}
			
			String[] s = line.toLowerCase().split("[ ,;-?.!\"'\\)\\(\\[\\]]");
			total += s.length;
			for (int i = 0; i < s.length; i++)
			{
				if (s[i].length() == 0)
					continue;
				if (words.containsKey(s[i]))
					words.put(s[i], words.get(s[i]) + 1);
				else
					words.put(s[i], 1);
			}
		}
		print(words, total);
	}
	
	public void print(Hashtable<String, Integer> words, int total)
	{
		Iterator<Entry<String, Integer>> set = words.entrySet().iterator();
		Entry<String, Integer> next;
		while (set.hasNext())
		{
			next = set.next();
			System.out.format("%-20s: %-3s %.2f%%\n", next.getKey(), next.getValue() , (double)next.getValue().intValue() / (double)total * 100.0);
		}
		System.out.println();
	}
	
}
