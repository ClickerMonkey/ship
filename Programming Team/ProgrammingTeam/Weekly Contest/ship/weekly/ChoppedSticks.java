package ship.weekly;

import java.util.Scanner;


/**
 * Solves the Chopped sticks problem.
 * 
 * @author Philip Diffenderfer
 *
 */
public class ChoppedSticks
{
	
	// Entry point into application
	public static void main(String[] args) 
	{
		new ChoppedSticks();
	}
	
	/**
	 * Prompts for input and solves each case.
	 */
	public ChoppedSticks() 
	{
		Scanner input = new Scanner(System.in);
		
		int caseCount = input.nextInt();
		
		// Read in each case
		for (int caseNumber = 1; caseNumber <= caseCount; caseNumber++) {
			
			// Read in all sticks for case
			int stickCount = input.nextInt();
			Stick sticks[] = new Stick[stickCount];
			while (--stickCount >= 0) {
				sticks[stickCount] = new Stick(input);
			}
			
			// Read in cutter for case
			Cutter cutter = new Cutter(input);
			
			// Compute total length of sticks contained in cutter.
			double total = 0.0;
			for (Stick s : sticks) {
				// Returns false if the line doesn't intersect with the cutter at all.
				// True is returned when the given stick has been clipped in the cutter.
				if (chopStick(s, cutter)) {
					total += s.length();
				}
			}
			
			// Output for the current case
			System.out.format("Case%d: Length of cut sticks: %.2f\n", caseNumber, total);
		}
	}
	
	/**
	 * Chops the given stick to be completely contained in the given Cutter. If
	 * the stick is completely inside the cutter then it is unchanged and true is
	 * returned. If the stick crosses over any of the blades of the cutter its 
	 * clipped to only the inside portion. If the stick is completely outside the
	 * cuttern then false is returned immediately.
	 * 
	 * @param s The stick to chop.
	 * @param c The cutter which is doing the chopping.
	 * @return True if atleast a portion of the stick lies in the cutter, false
	 * 	if the stick was not chopped by the cutter at all.
	 */
	public boolean chopStick(Stick s, Cutter c) 
	{
		// My version of the Cohen-Sutherland algorithm (faster of course)
		
		// Compute out codes of start point
		boolean code1_left = s.x1 < c.left;
		boolean code1_right = s.x1 > c.right;
		boolean code1_top = s.y1 > c.top;
		boolean code1_bottom = s.y1 < c.bottom;
		boolean code1 = code1_left | code1_right | code1_top | code1_bottom;

		// Compute out codes of end point
		boolean code2_left = s.x2 < c.left;
		boolean code2_right = s.x2 > c.right;
		boolean code2_top = s.y2 > c.top;
		boolean code2_bottom = s.y2 < c.bottom;
		boolean code2 = code2_left | code2_right | code2_top | code2_bottom;
		
		// Trivially Accept
		if (!(code1 | code2)) {
			return true;
		}
		
		// Trivially Reject
		if ((code1_left & code2_left) |
			(code1_right & code2_right) |
			(code1_top & code2_top) |
			(code1_bottom & code2_bottom)) {
			return false;
		}

		// If the first point is outside the clip rectangle
		if (code1) {
			// If its above the top side of the clip rectangle
			if (code1_top) {
				s.x1 += (c.top - s.y1) * (s.x2 - s.x1) / (s.y2 - s.y1);
				s.y1 = c.top;
			}
			// If its below the bottom side of the clip rectangle
			else if (code1_bottom) {
				s.x1 += (c.bottom - s.y1) * (s.x2 - s.x1) / (s.y2 - s.y1);
				s.y1 = c.bottom;
			}

			// If its after the right side of the clip rectangle
			if (s.x1 > c.right) {
				s.y1 += (c.right - s.x1) * (s.y2 - s.y1) / (s.x2 - s.x1);
				s.x1 = c.right;
			}
			// If its before the left side of the clip rectangle
			else if (s.x1 < c.left) {
				s.y1 += (c.left - s.x1) * (s.y2 - s.y1) / (s.x2 - s.x1);
				s.x1 = c.left;
			}	
		}
		
		// If the second point is outside the clip rectangle
		if (code2){
			// If its above the top side of the clip rectangle
			if (code2_top) {
				s.x2 += (c.top - s.y2) * (s.x2 - s.x1) / (s.y2 - s.y1);
				s.y2 = c.top;
			}
			// If its below the bottom side of the clip rectangle
			else if (code2_bottom) {
				s.x2 += (c.bottom - s.y2) * (s.x2 - s.x1) / (s.y2 - s.y1);
				s.y2 = c.bottom;
			}
			
			// If its after the right side of the clip rectangle
			if (s.x2 > c.right) {
				s.y2 += (c.right - s.x2) * (s.y2 - s.y1) / (s.x2 - s.x1);
				s.x2 = c.right;
			}
			// If its before the left side of the clip rectangle
			else if (s.x2 < c.left) {
				s.y2 += (c.left - s.x2) * (s.y2 - s.y1) / (s.x2 - s.x1);
				s.x2 = c.left;
			}
		}
		
		return true;
	}

	// A cutter of course
	class Cutter {
		double left, top, right, bottom;
		// Parse cutter from input
		Cutter(Scanner input) {
			left = input.nextDouble();
			top = input.nextDouble();
			right = input.nextDouble();
			bottom = input.nextDouble();
		}
	}
	
	// A stick of course
	class Stick {
		double x1, y1, x2, y2;
		// Parse stick from input
		Stick(Scanner input) {
			x1 = input.nextDouble();
			y1 = input.nextDouble();
			x2 = input.nextDouble();
			y2 = input.nextDouble();
		}
		// Length of the stick
		double length() {
			double dx = x2 - x1;
			double dy = y2 - y1;
			return Math.sqrt(dx * dx + dy * dy);
		}
	}
	
}
