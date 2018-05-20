package shipgames.pongbasic;

import shipgames.Vector;

public class Collisions
{


	public static boolean handleCollision(Bar bar, Ball ball, float ballSpeedIncrease, boolean enableSpinOff)
	{
		// If the bar and ball don't intersect then exit.
		if (!intersects(bar, ball))
			return false;
		
		///////////////////////////////////////////////////////////////
		// Determine where on the bar it hit. Use a simple solution.
		///////////////////////////////////////////////////////////////
		//   :   :
		// 3 : 2 : 3
		//...+---+....
		//   | B |
		// 1 | A | 1
		//   | R | 
		//...+---+....
		// 3 : 2 : 3
		//   :   :
		//
		// BAR) If the ball's center is in the bar then apply the solution
		// 		to the ball's last position.
		// 1) If the ball's center is in this section then its hit the side.
		// 2) If the ball's center is in this section then its hit the top.
		// 3) If the ball's center is in this section then its hit a corner.

		Vector center = ball.center;
		
		// Determine if the center of the ball is inside or on the edge of the bar.
		boolean inside = (center.x >= bar.getLeft() &&
						 center.x <= bar.getRight() &&
						 center.y >= bar.getTop() &&
						 center.y <= bar.getBottom());
		
		// If it is inside, use the last position to determine where it hit.
		if (inside)
			center = ball.previous;
		
		// The y is between the top and bottom
		boolean inSection1 = (center.y >= bar.getTop() && 
							  center.y <= bar.getBottom());
		// The x is between the left and right
		boolean inSection2 = (center.x >= bar.getLeft() && 
							  center.x <= bar.getRight());
		// The ball is not in either section 1 or 2
		boolean inSection3 = !(inSection1 || inSection2);

		// If it hit the side or the corner, bounce horizontally.
		if (inSection1 || inSection3)
			ball.getVelocity().mirrorY();
		// If it hit the top/bottom or the corner, bounce vertically.
		if (inSection2 || inSection3)
			ball.getVelocity().mirrorX();

		// If the ball hit a side then calculate its spin off angle depending
		// on where on the bar it hit.
		if (inSection1 && enableSpinOff)
		{
			float diff = bar.getY() - ball.getY();
			float angle = (diff / bar.getHalfHeight()) * 30f;
			ball.getVelocity().rotate(angle);
		}
		
		// If the ball is moving to vertically correct
		Vector velocity = ball.getVelocity();
		if (Math.abs(velocity.x) < 0.2)
		{
			velocity.x = (float)(0.2 * Math.signum(velocity.x));
			velocity.normalize();
		}
		
		// Move the ball out of the bar with the new velocity.
		final float MOVE_TIME = 0.005f;
		while (intersects(bar, ball))
			ball.update(MOVE_TIME);
				
		// Since the ball hit it increase the speed
		ball.addSpeed(ballSpeedIncrease);
		
		return true;
	}
	
	/**
	 * Returns true if the ball intersects with the bar.
	 * 
	 * @param bar => The bar to test with.
	 * @param ball => The ball to test with.
	 */
	public static boolean intersects(Bar bar, Ball ball)
	{
		// Find a point on the bounds or within the rectangle
		// that is the closest possible point.
		float closestX = ball.getX();
		float closestY = ball.getY();

		// Clamp the closest point based on the rectangle's bounds.
		if (closestX > bar.getRight())
			closestX = bar.getRight();
		if (closestX < bar.getLeft())
			closestX = bar.getLeft();
		if (closestY < bar.getTop())
			closestY = bar.getTop();
		if (closestY > bar.getBottom())
			closestY = bar.getBottom();
		
		// The difference between the closest point and the circle
		float dx = closestX - ball.getX();
		float dy = closestY - ball.getY();
		
		// If the difference is zero, the circle is inside the rectangle.
		if (dx == 0f && dy == 0f)
			return true;
		
		// If the distance from the closest point on the bar to the circle's 
		// center is less than the circle's radius then there is an edge of
		// intersection. If the distance is equal to the radius then there was
		// an intersection at one point.
		return (dx * dx + dy * dy) <= ball.getRadius() * ball.getRadius();
	}
	
}
