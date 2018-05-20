import java.util.Scanner;

public class ASCIIFont 
{

	public String _name;
	public int _characterHeight;
	public int _spaceWidth;
	public String _availableLetters;
	public ASCIILetter[] _letters;
	
	private ASCIIFont()
	{
		
	}
	
	public String[] getString(String word)
	{
		String[] lines = new String[_characterHeight];
		for (int i = 0; i < _characterHeight; i++)
			lines[i] = "";
		int index = -1;
		for (int i = 0; i < word.length(); i++)
		{
			index = _availableLetters.indexOf(word.charAt(i));
			if (index != -1)
				for (int j = 0; j < _characterHeight; j++)
					lines[j] += _letters[index]._lines[j];
			else
				for (int j = 0; j < _characterHeight; j++)
					lines[j] += " ";
		}
		return lines;
	}
	
	public static ASCIIFont fromFile(String filepath)
	{
		ASCIIFont f = new ASCIIFont();
		Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream(filepath));
		f._name = in.nextLine();
		f._characterHeight = Integer.parseInt(in.nextLine());
		char spaceFiller = in.nextLine().charAt(0);
		f._spaceWidth = Integer.parseInt(in.nextLine());
		f._availableLetters = in.nextLine();
		int letters = f._availableLetters.length();
		f._letters = new ASCIILetter[letters];
		for (int i = 0; i < letters; i++)
		{
			f._letters[i] = new ASCIILetter(f._characterHeight);
			f._letters[i]._letter = f._availableLetters.charAt(i);
			for (int j = 0; j < f._characterHeight; j++)
			{
				f._letters[i]._lines[j] = in.nextLine().trim().replace(spaceFiller, ' ');
			}
		}
		return f;
	}
	
}
