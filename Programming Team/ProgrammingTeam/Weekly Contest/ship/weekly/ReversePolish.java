package ship.weekly;

import java.util.Scanner;
import java.util.Stack;


public class ReversePolish
{

	public static void main(String[] args) {
		new ReversePolish();
	}
	
	
	public ReversePolish()
	{
		Scanner input = new Scanner(System.in);
		
		int caseCount = input.nextInt();
		input.nextLine();
		
		for (int i = 1; i <= caseCount; i++) {
			
			String line = input.nextLine();
			String[] ops = line.split(" ");
			Stack<Fraction> stack = new Stack<Fraction>();

			for (int j = 0; j < ops.length; j++) {
				if (isOperation(ops[j])) {
					Fraction f1 = stack.pop();
					Fraction f2 = stack.pop();
					perform(f1, f2, ops[j]);
					stack.push(f1);
				} else {
					stack.push(new Fraction(ops[j]));
				}
			}
			
			Fraction result = stack.pop();
			
			System.out.format("Case%d: %s\n", i, result);
		}
	}
	
	private void perform(Fraction f1, Fraction f2, String op) {
		char c = op.charAt(0);
		switch (c) {
			case '+': 	f1.add(f2); 	break;
			case '-': 	f1.sub(f2); 	break;
			case '*': 	f1.mul(f2); 	break;
			case '/': 	f1.div(f2); 	break;
		}
	}
	
	private boolean isOperation(String op) {
		if (op.length() != 1) {
			return false;
		}
		char c = op.charAt(0);
		return (c == '*' || c == '+' || c == '-' || c == '/');
	}
	
	public class Fraction {
		public int num, den;
		// Initializes a new fraction
		public Fraction(int num, int den) {
			set(num, den);
		}
		// Initializes a new fraction given a string
		public Fraction(String s) {
			int slash = s.indexOf('|');
			if (slash == -1) {
				set(Integer.parseInt(s), 1);
			} else {
				String nums = s.substring(0, slash);
				String dens = s.substring(slash + 1);
				set(Integer.parseInt(nums), Integer.parseInt(dens));
			}
		}
		// Sets the numerator and denominator of the fraction and simplifies
		public void set(int numerator, int denominator) {
			num = numerator;
			den = denominator;
			simplify();
		}
		// Sets this fraction from another fraction
		public void set(Fraction f) {
			set(f.num, f.den);
		}
		// Adds the given fraction to this fraction
		public void add(Fraction f) {
			set((num * f.den) + (den * f.num), (den * f.den));
		}
		// Subtracts the given fraction from this fraction
		public void sub(Fraction f) {
			set((num * f.den) - (den * f.num), (den * f.den));
		}
		// Multiplies this fraction by the given fraction
		public void mul(Fraction f) {
			set((num * f.num), (den * f.den));
		}
		// Divides this fraction by the given fraction
		public void div(Fraction f) {
			set((num * f.den), (den * f.num));
		}
		// Simplifies this fraction and corrects any signs
		public void simplify() {
			int gcd = (int)gcd(num, den);
			num /= gcd;
			den /= gcd;
			// Maintain signed numerator and positive denoninator
			if (den < 0 || (num < 0 && den < 0)) {
				den = -den;
				num = -num;
			}
		}
		// Returns the Greatest-Common-Denominator of the two integers.
		private long gcd(long a, long b) {
			if (a < 0) a = -a;
			if (b < 0) b = -b;
			while (b > 0) {
				long t = b;
				b = a % b;
				a = t;
			}
			return a;
		}
		// String representation of this fraction
		public String toString() {
			if (den == 1) {
				return Integer.toString(num);
			}
			return Integer.toString(num) + "|" + Integer.toString(den);
		}
	}
	
}
