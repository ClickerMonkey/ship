package chapter1;

import java.awt.Point;
import java.util.Scanner;

public class Problems
{

	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		Problems solver = new Problems();
		Point solution;
		int a, b, c, gcd;
		
		// Extended GCD
		System.out.print("a: ");
		a = input.nextInt();
		System.out.print("b: ");
		b = input.nextInt();
		
		gcd = solver.gcd(a, b);
		solution = solver.extendedGCD(a, b);
		
		System.out.format("x: %d\ny: %d\ngcd: %d\n", solution.x, solution.y, gcd);
		
		// Diophantine
		System.out.print("a: ");
		a = input.nextInt();
		System.out.print("b: ");
		b = input.nextInt();
		System.out.print("c: ");
		c = input.nextInt();
		
		solution = solver.diophantine(a, b, c);
		
		System.out.format("x: %d\ny: %d\n", solution.x, solution.y);
	}
	
	public int gcd(int a, int b)
	{
		while (b != 0)
			b = a % (a = b);
		return a;
	}

	public Point extendedGCD(int a, int b)
	{
		int q, x, y, prevX, prevY;
		x = prevY = q = 0;
		y = prevX = 1;
		
		// Loop until b is zero where a is the gcd.
		while (b != 0)
		{	
			// Determine the quotient
			q = a / b;
			// Break down to solve for the gcd
			b = a % (a = b);
			// Use the quotient to determine
			// the current x,y and the previous
			x = prevX - (q * (prevX = x));
			y = prevY - (q * (prevY = y));
		}
		
		return new Point(prevX, prevY);
	}
	
	public Point diophantine(int a, int b, int c)
	{
		int gcd = gcd(a, b);
		// If c is not divisible by the gcd then there
		// is no solution!
		if (c % gcd != 0)
			return null;
		// Get the solution for just a and b
		Point solution = extendedGCD(a, b);
		// Find the scale to balance the equation to equal c
		int scale = c / gcd;
		// Return the solution scaled by the balance amount.
		return new Point(solution.x * scale, solution.y * scale);
	}
	
}
