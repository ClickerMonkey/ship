package ccsce10;


import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/*
2
3 america flag nation
1 is
2 happy patriotic
4 flag kool taxes zooligists
2 is upset
3 american nasty young

 */
public class Problem3 {

	public static void main(String[] args) {
		new Problem3();
	}
	

	String[] sentence;
	int index;
	ArrayList<String> nouns;
	ArrayList<String> verbs;
	ArrayList<String> adjs;
	
	public Problem3() {
		Scanner input = new Scanner(System.in);
		
		int wordSets = input.nextInt();
		
		while (--wordSets >= 0) {
			
			ArrayList<String> words = new ArrayList<String>();

			nouns = getLine(input, words);
			verbs = getLine(input, words);
			adjs = getLine(input, words);
			
			Collections.sort(nouns);
			Collections.sort(verbs);
			Collections.sort(adjs);
			Collections.sort(words);
			
			sentence = new String[words.size()];
			sentence = words.toArray(sentence);
			index = 0;
			
			boolean valid = true;
			while (index < sentence.length && valid) {
				valid = isSentence();
			}
			if (valid) {
				System.out.println("correct");
			}
			else {
				System.out.println("wrong");
			}
		}
	}
	
	private ArrayList<String> getLine(Scanner input, ArrayList<String> total) {
		int count = input.nextInt();
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < count; i++) {
			String x = input.next();
			list.add(x);
			total.add(x);
		}
		input.nextLine();
		return list;
	}
	
	private boolean isSentence() {
		return isNounPhrase() && isVerb() && isNounPhrase();
	}
	
	private boolean isNounPhrase() {
		if (index < sentence.length && exists(nouns, sentence[index])) {
			index ++;
			return true;
		}
		if (index < sentence.length - 1 && exists(adjs, sentence[index]) && exists(nouns, sentence[index + 1])) {
			index += 2;
			return true;
		}
		return false;
	}
	
	private boolean isVerb() {
		if (index < sentence.length && exists(verbs, sentence[index])) {
			index++;
			return true;
		}
		return false;
	}
	
	private boolean exists(List<String> list, String word) {
		return Collections.binarySearch(list, word) >= 0;
	}
	
}
