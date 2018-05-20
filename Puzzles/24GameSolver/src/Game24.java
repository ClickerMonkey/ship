import java.util.ArrayList;

public class Game24 {

	public static final char PLUS = '+';
	public static final char MINUS = '-';
	public static final char TIMES = '*';
	public static final char DIVIDE = '/';
	public static final char STARTING = 's';
	
	public ArrayList<Node[]> solutions = new ArrayList<Node[]>();
	
	public Game24() { }

	public void solve(int a, int b, int c, int d) {
		solutions.clear();
		new Node(null, a, STARTING, 0, new int[] {b, c, d});
		new Node(null, b, STARTING, 0, new int[] {a, c, d});
		new Node(null, c, STARTING, 0, new int[] {b, a, d});
		new Node(null, d, STARTING, 0, new int[] {b, c, a});
		
		clearSimilarSolutions();
		
		Node[] n;
		for (int i = 0; i < solutions.size(); i++)
		{
			n = solutions.get(i);
			System.out.println("((" + n[0].value + 
					   " " + n[1].operation + " " + n[1].value + ")" +
					   " " + n[2].operation + " " + n[2].value + ")" +
					   " " + n[3].operation + " " + n[3].value);
		}
		
	}
	
	@SuppressWarnings("unused")
	public int[] create(int difficulty, int digits) {
		final int[] diff = new int[] {Integer.MAX_VALUE, 3, 1};
		final char[] ops = new char[] {PLUS, MINUS, TIMES, DIVIDE};
		int max = digits * 10 - 1, min = 2, value;
		int a, b, c, d, num;
		do {
			value = 24;
			a = b = c = d = 0;
			//Do a & b
			
			//Do c
			
			//Do d
			
		} while (solutions.size() > diff[difficulty]);
		return new int[] {a, b, c, d};
	}
	
	public void clearSimilarSolutions() {
		Node[] a, b;
		for (int i = solutions.size() - 1; i >= 0; i--) {
			a =  solutions.get(i);
			for (int j = i - 1; j >= 0; j--) {
				b = solutions.get(j);
				if (a[1].total == b[1].total && a[2].total == b[2].total) {
					solutions.remove(i); break;
				}
			}
		}
	}
	
	//
	public static int[] minus(int[] n, int a) {
		int[] m = new int[n.length - 1];
		int i = 0;
		while (true) {
			if (n[i] == a) {
				for (int j = i + 1; j < n.length; j++)
					m[j-1] = n[j];
				break;
			} else {
				m[i] = n[i];
			}
			i++;
		}
		return m;
	}

	//
	public static boolean isLegal(int a, int b, char operation) {
		// Adding and multiplication is always legal
		if (operation == PLUS || operation == TIMES) return true;
		// Subtraction is legal if answer is not negative
		if (operation == MINUS && a - b >= 0) return true;
		// Cannot divide by 0
		if (b == 0) return false;
		// Division is legal if 'b' is a factor of 'a'
		return (a % b == 0);
	}
	
	//
	public class Node {
		public Node parent;
		public int value;
		public char operation;
		public int level = 0;
		public int total = 0;
		public int[] nums = null;

		public Node(Node Parent, int Value, char Operation, int Level, int[] Remaining) {
			parent = Parent;
			value = Value;
			operation = Operation;
			level = Level + 1;
			nums = Remaining;
			if (parent != null) {
				switch (operation) {
				case PLUS:
					total = parent.total + value;
					break;
				case MINUS:
					total = parent.total - value;
					break;
				case TIMES:
					total = parent.total * value;
					break;
				case DIVIDE:
					total = parent.total / value;
					break;
				}
			} else {
				total = value;
			}
			expand();
		}

		public void expand() {
			if (level == 4) {
				check();
			} else {
				// Plus
				for (int i = 0; i < nums.length; i++)
					if (isLegal(total, nums[i], PLUS))
						new Node(this, nums[i], PLUS, level, minus(nums, nums[i]));
				// Minus
				for (int i = 0; i < nums.length; i++)
					if (isLegal(total, nums[i], MINUS))
						new Node(this, nums[i], MINUS, level, minus(nums, nums[i]));
				// Multiply
				for (int i = 0; i < nums.length; i++)
					if (isLegal(total, nums[i], TIMES))
						new Node(this, nums[i], TIMES, level, minus(nums, nums[i]));
				// Divide
				for (int i = 0; i < nums.length; i++)
					if (isLegal(total, nums[i], DIVIDE))
						new Node(this, nums[i], DIVIDE, level, minus(nums, nums[i]));
			}
		}

		public void check() {
			if (total != 24) return;
			solutions.add(new Node[] {this.parent.parent.parent, 
									  this.parent.parent, 
									  this.parent, 
									  this});
		}
	}

}