package ccsce10;


import java.util.*;


/*

5
apple applet argyle addle car
3
a _ _ l e
a _ _ _ _ _
_ d _

 */
public class Problem1 {

	public static void main(String[] args) {
		new Problem1();
	}
	
	public Problem1() {
		Scanner input = new Scanner(System.in);
		
		int wordCount = input.nextInt();
		
		input.nextLine();
		char[][] words = new char[wordCount][];
		for (int i = 0; i < wordCount; i++) {
			words[i] = input.next().toCharArray();
		}
		Arrays.sort(words, new Comparator<char[]>() {
			public int compare(char[] arg0, char[] arg1) {
				return new String(arg0).compareTo(new String(arg1));
			}
		});
		
		int problemCount = input.nextInt();
		input.nextLine();
		
		while (--problemCount >= 0) {
			String line = input.nextLine();
			
			String[] letters = line.split(" ");
			char[] chars = new char[letters.length];
			for (int i = 0; i < chars.length; i++) {
				chars[i] = letters[i].charAt(0);
			}
			
			int total = 0;
			for (int i = 0; i < words.length; i++) {
				if (matches(words[i], chars)) {
					total++;
				}
			}
			System.out.println(total);
		}
	}
	
	public boolean matches(char[] word, char[] regex) {
		if (word.length != regex.length) return false;
		for (int i = 0; i < word.length; i++) {
			if (regex[i] != '_' && regex[i] != word[i]) {
				return false;
			}
		}
		return true;
	}
	
}
