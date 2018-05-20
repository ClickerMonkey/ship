package ship.weekly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.regex.Pattern;


public class WordFrequency
{

	public static void main(String[] args) {
		new WordFrequency();
	}
	
	WordFrequency() {
		Scanner in = new Scanner(System.in);
		// DELIMIT= [space]\t\n\r.?!:;")(-
		in.useDelimiter(Pattern.compile("[\\s,\\.?\\!:;\"\\)\\(-]+"));
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		while (in.hasNext()) {
			String word = in.next().toLowerCase();
			Integer count = map.get(word);
			if (count == null) {
				count = 0;
			}
			map.put(word, count + 1);
		}
		
		ArrayList<Node> nodes = new ArrayList<Node>(map.size());
		for (Entry<String, Integer> e : map.entrySet()) {
			nodes.add(new Node(e.getKey(), e.getValue()));
		}
		
		Collections.sort(nodes);
		
		for (int i = 0; i < 5; i++) {
			System.out.format("%s occurred %d times\n", 
					nodes.get(i).word, nodes.get(i).count);
		}
	}
	
	class Node implements Comparable<Node> {
		String word;
		int count;
		Node(String word, int count) {
			this.word = word;
			this.count = count;
		}
		public int compareTo(Node o) {
			int diff = o.count - count;
			return (diff != 0 ? diff : word.compareTo(o.word));
		}
	}
	
}
