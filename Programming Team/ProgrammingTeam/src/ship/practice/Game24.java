package ship.practice;

import java.util.Scanner;
import java.util.Stack;


public class Game24
{

	public static void main(String[] args) {
		new Game24();
	}
	
	Game24() {
		Scanner in = new Scanner(System.in);
		
		
	}	
	
	void solve() {
		
	}
	
	// Any operation that can be used in the Game24
	enum Operation {
		Add() {
			boolean canPerform(int a, int b) {
				return true;
			}
			int getResult(int a, int b) {
				return a + b;
			}
		},
		Substract() {
			boolean canPerform(int a, int b) {
				return (a >= b);
			}
			int getResult(int a, int b) {
				return a - b;
			}
		},
		Multiply() {
			boolean canPerform(int a, int b) {
				return true;
			}
			int getResult(int a, int b) {
				return a * b;
			}
		},
		Divide() {
			boolean canPerform(int a, int b) {
				return (a % b == 0);
			}
			int getResult(int a, int b) {
				return a / b;
			}
		};
		
		// Returns true if the given operation can be performed on these two numbers.
		abstract boolean canPerform(int a, int b);
		// Returns the result of the given operation of these two numbers.
		abstract int getResult(int a, int b);
	}
	
	class Node {
		Node parent;
		Operation operation;
		int operand;
		int result;
		int depth;
		// Is this node has don
		boolean isFinalState() {
			return (depth == 4 && result == 24);
		}
		void branch(Stack<Node> stack) {
			
		}
	}
	
}
