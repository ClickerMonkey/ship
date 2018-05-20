import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;


public class PhoneWords2
{

	public static void main(String[] args) throws IOException {
		new PhoneWords();
	}
	
	String sentence;
	int[] digits;
	int[] offsets;
	ArrayList<char[]> words = new ArrayList<char[]>(); 
	
	private char map[][] = {
			{}, // 0
			{}, // 1
			{'a', 'b', 'c'}, // 2
			{'d', 'e', 'f'}, // 3
			{'g', 'h', 'i'}, // 4
			{'j', 'k', 'l'}, // 5
			{'m', 'n', 'o'}, // 6
			{'p', 'q', 'r', 's'}, // 7
			{'t', 'u', 'v'}, // 8
			{'w', 'x', 'y', 'z'}, // 9
	};
	
	public PhoneWords2()	throws IOException {
		this.loadWords();
		this.getInput();
		this.solve();
	}
	
	private void loadWords() throws IOException {
		File dict = new File("basic-words");
		Scanner input = new Scanner(dict);
		while (input.hasNextLine()) {
			words.add(input.nextLine().toLowerCase().toCharArray());
		}
		offsets = new int[26];
		for (int i = words.size() - 1; i >= 0; i--) {
			offsets[words.get(i)[0] - 'a'] = i;
		}
	}
	
	private void getInput() {
		Scanner input = new Scanner(System.in);
		sentence = input.nextLine();
		digits = new int[sentence.length()];
		for (int i = 0; i < sentence.length(); i++) {
			digits[i] = sentence.charAt(i) - '0';
		}
	}
	
	
	private class Node {
		Node prev;
		Node start;
		char letter;
		int index;
		Node (char letter, int index, Node prev) {
			this.letter = letter;
			this.index = index;
			this.prev = prev;
		}
	}
	
	private void solve() {
		Stack<Node> stack = new Stack<Node>();
		
		char[] letters = map[digits[0]];
		for (char c : letters) {
			Node n = new Node(c, 0, null);
			n.start = n;
			stack.push(n);
		}
		
		while (!stack.isEmpty()) {
			Node n = stack.pop();
			if (n.index < digits.length) {
				letters = map[digits[n.index + 1]];
				for (char c : letters) {
					Node w = new Node(c, n.index + 1, n);
					w.start = n.start;
					stack.push(w);
				}
			}
		}
	}
	
	private void findWord(Node end) {
		Node start = end.start;
		Node current = end;
		int length = end.index - start.index;
		char[] word = new char[length];
		for (int i = length - 1; i >= 0; i--) {
			word[i] = current.letter;
			current = current.prev;
		}
		
	}
	
	private void search(char[] word) {
		 int charIndex = 0;
		 
	}
	
	
	
}
