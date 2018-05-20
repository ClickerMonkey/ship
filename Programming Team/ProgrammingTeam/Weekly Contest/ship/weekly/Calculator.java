package ship.weekly;

import java.util.Scanner;


/**
 * Solves the Week-1 Problem-1 Calculator problem. This simplifies/collapses
 * operations of higher precedence into their equivalent value then preceeds to
 * handle the remaining equation (with additions and subtractions).
 * 
 * @author Philip Diffenderfer
 *
 */
public class Calculator
{

	// Entry point into application.
	public static void main(String[] args) {
		new Calculator();
	}
	
	// Read in every equation an solve it.
	Calculator() {
		Scanner in = new Scanner(System.in);

		// The number of equations.
		int cases = Integer.parseInt(in.nextLine());
		
		while (--cases >= 0) {
			
			// Get the equation without any whitespace characters.
			String line = stripSpaces(in.nextLine());
			
			// Solve the equation.
			int answer = solve(line);

			// Print it back out with the appropriate padding.
			System.out.format("%s = %d\n", padSpaces(line), answer);
		}
	}
	
	// Solves the given equation
	int solve(String line) {
		
		// Create a parser
		EqParser eq = new EqParser(line);
		
		// Collapse the equation, performing only high priority operations.
		int ans = collapse(eq);
		
		// While low priority [+-] operators exist in the equation...
		while (eq.hasNext()) {
			
			// Get the low priority operator
			char op = eq.nextOp();
			
			// Get the next operand applying any high priority operations
			int b = collapse(eq);

			// Perform the low priority operation
			ans = calculate(ans, op, b);
		}
		
		// Return the answer
		return ans;
	}
	
	// Returns the next operand performing any necessary high priority operations
	// until the next operator in the equation is low priority.
	int collapse(EqParser eq) {
		// Get the first operand...
		int a = eq.next();
		
		// While there is a high priority operator perform the operations
		// until a low priority is hit or the end of the equation is reached.
		while (eq.hasNext() && (eq.peekOp() == '*' || eq.peekOp() == '/')) {
			a = calculate(a, eq.nextOp(), eq.next());
		}
		
		// Return the collapsed value  
		return a;
	}
	
	// Performs the operation between a and b, the operation is either 
	// addition(+), subtraction(-), multiplication(*), or division(/).
	int calculate(int a, char op, int b) {
		switch (op) {
			case '+': return (a + b);
			case '-': return (a - b);
			case '*': return (a * b);
			case '/': return (a / b);
			default: throw new RuntimeException();
		}
	}

	// Strips the given string of all whitespace and returns the result.
	String stripSpaces(String s) {
		StringBuilder sb = new StringBuilder();
		for (char c : s.toCharArray()) {
			if (c > ' ') {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	// Pads the given string with spaces around the operators.
	String padSpaces(String s) {
		StringBuilder sb = new StringBuilder();
		for (char c : s.toCharArray()) {
			if (isOperator(c)) {
				sb.append(' ').append(c).append(' ');
			}
			else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	// Returns whether c is an acceptable operation character
	boolean isOperator(char c) {
		return (c == '+' || c == '-' || c == '*' || c == '/');
	}
	
	// A parser which iterates over a string and breaks it into operands and
	// operators.
	class EqParser {
		char[] eq;
		int pos;
		EqParser(String eq) {
			this.eq = eq.toCharArray();
		}
		// Returns the next operator from the equation. This can only be called
		// after a next() and only when hasNext() is true.
		char peekOp() {
			return eq[pos];
		}
		// Grabs the next operator from the equation. This must be called after
		// a next() and only when hasNext() is true.
		char nextOp() {
			return eq[pos++];
		}
		// Grabs the next integer from the equation. This must be called first or
		// after a nextOp is called.
		int next() {
			// Start after the current character, there's atleast one digit.
			int end = pos + 1;
			// Search to the next operator
			while (end < eq.length && !isOperator(eq[end])) {
				end++;
			}
			// Parse the integer
			int value = parseUInt(pos, end);
			// Update the position
			pos = end;
			// Returned the next operand.
			return value;
		}
		// Returns true if there are remaining operands or operators left in this
		// equation parser.
		boolean hasNext() {
			return (pos < eq.length);
		}
		// Parses the characters from start (inclusive) to end (exclusive) in the
		// character array as an unsigned integer. If there is an invalid character
		// (not 0-9) then -1 is returned.
		int parseUInt(int start, int end) {
			int power = 1, parsed = 0, digit;
			while (--end >= start) {
				digit = eq[end] - '0';
				if (digit < 0 || digit > 9) {
					return -1;
				}
				parsed += digit * power;
				power *= 10;
			}
			return parsed;
		}
	}
	
}
