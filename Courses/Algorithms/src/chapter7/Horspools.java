package chapter7;

public class Horspools
{

	public static void main(String[] args)
	{
		Horspools h = new Horspools();
		
		char[] pattern = "barber".toCharArray();
		char[] text = "jim_saw_me_in_a_barbershop".toCharArray();
		
		int match = h.horspoolMatching(pattern, text);
		
		System.out.println((match == -1 ? "Pattern not found in text." : "Pattern found in text at position " + match));
	}
	
	// Execute Horspool's algorithm
	public int horspoolMatching(char[] pattern, char[] text)
	{
		// Convert both to lowercase
		pattern = toLower(pattern);
		text = toLower(text);
		// Generate the shift table
		int table[] = shiftTable(pattern);
		
		int m = pattern.length;
		int n = text.length;
		int i = m - 1;
		int k = 0;
		
		while (i < n)
		{
			k = 0;
			while (k < m && pattern[m - 1 - k] == text[i - k])
				k++;

			if (k == m)
				return i - m + 1;
			else if (isAlpha(text[i]))
				i += table[text[i] - 'a'];
			else
				i += pattern.length;			
		}	
		
		return -1;
	}
	
	// Converts the char array to lowercase
	public char[] toLower(char[] c)
	{
		for (int i = 0; i < c.length; i++)
			c[i] = Character.toLowerCase(c[i]);
		return c;
	}
	
	// Generates the shift table given the pattern
	public int[] shiftTable(char[] pattern)
	{
		int[] table = new int[26];
		int index = 0;
		int position = 0;
		
		for (int i = pattern.length - 2; i >= 0; i--)
		{
			index++;			
			position = pattern[i] - 'a';
			
			if (isAlpha(pattern[i]) && table[position] == 0)
				table[position] = index;
		}

		for (int i = 0; i < 26; i++)
			if (table[i] == 0)
				table[i] = pattern.length;
		
		return table;
	}
	
	// Determines if c is a letter between A and Z
	public boolean isAlpha(char c)
	{
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
	}
	
}
