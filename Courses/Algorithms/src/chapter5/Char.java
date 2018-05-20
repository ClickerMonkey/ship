package chapter5;

public class Char implements Comparable<Char>
{

	// The value of this char
	public char value;
	// The original index in the array
	public int index;
	
	// Initialize
	public Char(char value, int index)
	{
		this.value = value;
		this.index = index;
	}
	
	// Compares two Char's
	public int compareTo(Char c)
	{
		return (value - c.value);
	}
	
	//
	public static Char[] fromString(String s)
	{
		Char[] result = new Char[s.length()];
		for (int i = 0; i < s.length(); i++)
			result[i] = new Char(s.charAt(i), i);
		return result;
	}
	
}
