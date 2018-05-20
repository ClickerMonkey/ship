package ship.dickinson09;

import java.util.HashMap;
import java.util.Scanner;


public class keys {

	public static void main(String[] args)
	{
		new keys();
	}
	
	public HashMap<Character, Character> k;
	
	public keys()
	{
		Scanner sc = new Scanner(System.in);
		
		buildMap();
		
		String line = "";
		char[] chars;
		char[] output;
		while (sc.hasNextLine())
		{
			line = sc.nextLine();
			chars = line.toCharArray();
			output = new char[chars.length];
			for (int i = 0; i < chars.length; i++)
			{
				if (k.containsKey(chars[i]))
					output[i] = k.get(chars[i]);
				else
					output[i] = chars[i];
			}
			System.out.println(output);
		}
	}
	
	public void buildMap()
	{
		k = new HashMap<Character, Character>(64);
//		k.put('A', null);
		k.put('B', 'V');
		k.put('C', 'X');
		k.put('D', 'S');
		k.put('E', 'W');
		k.put('F', 'D');
		k.put('G', 'F');
		k.put('H', 'G');
		k.put('I', 'U');
		k.put('J', 'H');
		k.put('K', 'J');
		k.put('L', 'K');
		k.put('M', 'N');
		k.put('N', 'B');
		k.put('O', 'I');
		k.put('P', 'O');
//		k.put('Q', null);
		k.put('R', 'E');
		k.put('S', 'A');
		k.put('T', 'R');
		k.put('U', 'Y');
		k.put('V', 'C');
		k.put('W', 'Q');
		k.put('X', 'Z');
		k.put('Y', 'T');
//		k.put('Z', null);
		k.put('[', 'P');
		k.put(']', '[');
		k.put('\\', ']');
		k.put(';', 'L');
		k.put('\'', ';');
		k.put(',', 'M');
		k.put('.', ',');
		k.put('/', '.');
		
		k.put('1', '`');
		k.put('2', '1');
		k.put('3', '2');
		k.put('4', '3');
		k.put('5', '4');
		k.put('6', '5');
		k.put('7', '6');
		k.put('8', '7');
		k.put('9', '8');
		k.put('0', '9');
		k.put('-', '0');
		k.put('=', '-');
//		k.put('`', null);
	}
	
}
