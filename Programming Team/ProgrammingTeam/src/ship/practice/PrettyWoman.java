package ship.practice;
import java.util.Scanner;

public class PrettyWoman
{

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext())
		{
			// Store the 2 words in an array
			String w[] = new String[] { sc.next(), sc.next() };
			// Store the length of the words in an array
			int l[] = new int[] { w[0].length(), w[1].length() };
			int alpha[] = new int[26];
			// For every char in the string increment the alpha array based on the
			// char.
			for (int i = 0; i < l[0]; i++)
				if (i < l[0])
					alpha[(w[0].charAt(i) - 'a')] += 1;
			for (int i = 0; i < l[1]; i++)
				if (i < l[1])
					alpha[(w[1].charAt(i) - 'a')] += 1000;
			// For every letter in the alphabet if its been incremented by both
			// words then output the char
			for (int i = 0; i < 26; i++)
				if (alpha[i] % 1000 >= 1 && (alpha[i] / 1000) >= 1)
					System.out.print((char)(i + 'a'));
			System.out.println();
		}
	}
}
