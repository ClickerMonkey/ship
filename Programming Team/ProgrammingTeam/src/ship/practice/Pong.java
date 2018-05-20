package ship.practice;

import java.util.Scanner;


/**
 * Source:
 * ME!
 * 
 * Little Tommy likes to play the video game Pong, as a matter of fact he's the
 * best at it. Hes become bored with the game and is paying you a fair amount
 * to write the AI for the game to be perfect. Everytime Tommy hits the ball 
 * with his bar your code must calculate the ending position of the ball on the 
 * opposing side as a y-coordinate so the AI bar knows exactly where to go.
 * He also likes to vary the width and height of his screen to increase the
 * dificulty.
 * 
 * The ball tommy plays with has a radius of 0 and the bars in the game have 
 * widths of 0 as well. When the ball bounces off the top or bottom of the 
 * screen the bounce is reflective, ie the outgoing angle is reflected off the
 * normal of the wall from the incoming angle. Tommy always plays on the left
 * side so the angle of the ball is exclusively between 90 degs and -90 degs.
 * 
 * Given the width and height of a pong game screen, the position of the pong 
 * ball on the y-axis, and the angle of the ball in degrees, what is the
 * ending y-location of the ball once it intersects the opponents side (right)?
 * 
 * Input:
 * 	w h y theta
 * 
 * w = Width of screen as double
 * h = Height of screen as double
 * y = Y-coordinate of ball on left wall as double
 * theta = Angle in degrees at which the ball is angled (90,-90) exclusively.
 * 
 * Solution:
 * 	Treat the path of the ball as a wave and remove all complete waves to
 * 	determine the remaining section of the wave.
 * 
 * @author Philip Diffenderfer
 *
 */
public class Pong
{

	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		
		double w = input.nextDouble();
		double h = input.nextDouble();
		double y = input.nextDouble();
		double theta = -input.nextDouble();
		
		// Convert the degree to radians
		double radian = theta * Math.PI / 180.0;
		// Precompute the sine and cosine of the given angle.
		double cos = Math.cos(radian);
		double sin = Math.sin(radian);
		
		// The width of a single wave
		double wavelength = (h * cos * 2) / sin;
		
		// The number of FULL waves in this example.
		double waves = Math.floor(w / wavelength);
		
		// The amount of extra (non-full) wave that is left over
		double remaining = w - (waves * wavelength);
		
		// The normalized value of the given y (dependent on the angle of direction)
		double hdelta = (theta < 0 ? (h / y) : (y / h));
		
		// The normalized value of the length of the remaining wave
		double wavedelta = remaining / wavelength;
		// The top is 0.5 the way through the wave
		double top = hdelta * 0.5;
		// The bottom is 0.5 a wave below the top
		double bottom = top + 0.5;
		
		double delta = -1f;
		// Consider all situations, a ball bouncing up from the bottom, a ball
		// bouncing down from the top, and a ball that hasn't bounced at all!
		if (wavedelta >= 0.0 && wavedelta < top)
			delta = (top - wavedelta) * 2.0;
		else if (wavedelta >= top && wavedelta < bottom)
			delta = (wavedelta - top) * 2.0;
		else if (wavedelta >= bottom)
			delta = 1.0 - ((wavedelta - bottom) * 2.0);
		
		// Multiply the ending position by the height of the screen.
		double z = delta * h;
		
		System.out.println(z);
	}
	
}
