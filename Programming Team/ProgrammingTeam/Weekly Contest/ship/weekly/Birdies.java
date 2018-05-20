package ship.weekly;

import java.util.Scanner;


public class Birdies
{

	public static void main(String[] args) {
		new Birdies();
	}
	
	public Birdies() {
		Scanner input = new Scanner(System.in);
		
		int caseCount = input.nextInt();
		
		for (int caseNumber = 1; caseNumber <= caseCount; caseNumber++) 
		{
			
			// Parse weapon from input 
			Weapon w = new Weapon(input);
			
			int birdCount = input.nextInt();
			int minBird = -1;
			double minTime = 0.0;
			
			for (int i = 0; i < birdCount; i++) 
			{
				// Get the birdie from input and calculate the time of intersection.
				double time = getIntersectTime(new Bird(input), w);
				
				// If it can hit the bird, and its faster then the last...
				if (time > 0.0 && (minBird == -1 || time < minTime)) {
					minBird = i;
					minTime = time;
				}
			}
			
			System.out.format("Case#%d: ", caseNumber);
			if (minBird == -1) {
				System.out.println("No birds could be shot!");
			}
			else {
				System.out.format("Bird #%d chosen and shot in %.2f seconds.\n", minBird + 1, minTime);
			}
		}
	}
	
	/**
	 * Calculates the intersection time of the weapon shot and a birdie. If the
	 * weapon cannot kill the birdie then -1.0 is returned.
	 * 
	 * @param target => The birdie to hit.
	 * @param weapon => The weapon to use.
	 * @return
	 */
	private double getIntersectTime(Bird target, Weapon weapon) 
	{
		// Boils down to solving quadratic in the form ax^2 + bx + c = 0
		double dx = target.x - weapon.x;
		double dy = target.y - weapon.y;
	
		// Calculate a, b, and c
		double a = sqr(target.velx) + sqr(target.vely) - sqr(weapon.speed);
		double b = 2.0 * (target.velx * dx + target.vely * dy);
		double c = sqr(dx) + sqr(dy);
		
		// Determines if solutions exist.
		double discriminant = sqr(b) - 4 * a * c;
		
		// Can't ever get to target in time
		if (discriminant < 0.0) {
			return -1.0;
		}
	
		// Two possible solutions for x
		double t1 = (-b + Math.sqrt(discriminant)) / (2 * a);
		double t2 = (-b - Math.sqrt(discriminant)) / (2 * a);
		
		// Possible??
		if (t1 < 0.0 && t2 < 0.0) {
			return -1.0;
		}
		
		// Choose smaller positive value
		return (t1 < 0 ? t2 : (t2 < 0 ? t1 : Math.min(t1, t2)));
	}

	// Squares a number of course
	private double sqr(double a) {
		return a * a;
	}
	
	// A birdie
	class Bird {
		double x, y, velx, vely, direction, velocity;
		Bird(Scanner input) {
			x = input.nextDouble();
			y = input.nextDouble();
			direction = Math.toRadians(input.nextDouble());
			velocity = input.nextDouble();
			velx = Math.cos(direction) * velocity;
			vely = Math.sin(direction) * velocity;
		}
	}
	
	// A nasty weaponz
	class Weapon {
		double x, y, speed;
		Weapon(Scanner input) {
			x = input.nextDouble();
			y = input.nextDouble();
			speed = input.nextDouble();
		}
	}
	
}
