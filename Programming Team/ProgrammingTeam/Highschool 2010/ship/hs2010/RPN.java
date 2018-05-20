package ship.hs2010;

import java.util.Scanner;
import java.util.Stack;


public class RPN
{

	public static void main(String[] args) {
		new RPN();
	}
	
	public RPN() {
		Scanner input = new Scanner(System.in);
		
		int caseCount = input.nextInt();
		input.nextLine();
		
		while (--caseCount >= 0) {
			
			String line = input.nextLine();
			Scanner items = new Scanner(line);
			Stack<Integer> stack = new Stack<Integer>();
			
			while (items.hasNext()) {
				String item = items.next();
				
				if (isOperator(item)) {
					Integer a = stack.pop();
					Integer b = stack.pop();
					Integer result = performOperation(a, item, b);
					stack.push(result);
				}
				else {
					stack.push(Integer.parseInt(item));
				}
			}
			
			System.out.println(stack.pop());
		}
	}
	
	private Integer performOperation(Integer a, String op, Integer b) {
		if (op.equals("*")) {
			return a * b;
		}
		else if (op.equals("/")) {
			return a / b;
		}
		else if (op.equals("+")) {
			return a + b;
		}
		else if (op.equals("-")) {
			return a - b;
		}
		return null;
	}
	
	private boolean isOperator(String item) {
		return item.equals("*") || item.equals("/") || 
				 item.equals("-") || item.equals("+");
	}
	
}
