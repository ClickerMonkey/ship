package ship.acm_10_25_08_shipred;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StemsSell
{
	
	public static void main(String[] args)
	{
		new StemsSell();
	}

	private Pattern regexExp = Pattern.compile("(.+) => (.+)");
	private Pattern regexStar = Pattern.compile("[a-z]+");
	private Pattern regexV = Pattern.compile("[aeiou]");
	private Pattern regexC = Pattern.compile("[bcdfghj-np-tv-z]");
	private Pattern regexDigit = Pattern.compile("[1-9]");
	
	public StemsSell()
	{
		Scanner sc = new Scanner(System.in);
		String line;
		
		while (!(line = sc.nextLine()).equals("***"))
		{
			ArrayList<String> patterns = new ArrayList<String>();
			ArrayList<String> replaces = new ArrayList<String>();
			
			while (true)
			{
				String s[] = line.split(" => ");
				if (s.length != 2)
					break;
				// parse pattern
				String pattern = "";
				char[] chars = s[0].toCharArray();
				for (int i = s[0].length() - 1; i >= 0; i--)
				{
					
				}

				patterns.add(pattern);
				
				replaces.add(s[1]);
				line = sc.nextLine();
			}
			
			// Reached an empty line
			while ((line = sc.nextLine()).length() != 0 || !line.equals("***"))
			{
				// Process line, run each pattern
				String words[] = line.split(" ");
				
			}
		
			
		}
		
	}
	
}
