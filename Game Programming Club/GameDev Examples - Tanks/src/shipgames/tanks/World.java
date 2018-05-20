package shipgames.tanks;

import java.awt.Color;
import java.awt.Graphics2D;

import shipgames.Util;
import shipgames.Vector;

public final class World
{

	private static World instance = new World();
	
	public static World getInstance()
	{
		return instance;
	}
	
	
	// This is to make sure the world only gets initialized once.
	private boolean initialized = false;
	

	private float initialForce;
	private float initialHealth;
	private float gravity;
	
	private Land land;
	
	private Tank player1;
	private Tank player2;
	private Tank currentPlayer;
	private Tank enemyPlayer;
	
	private Projectile projectile;
	
	private Vector wind;
//	// The time in seconds since the wind was last updated.
//	private float windTime;
//	// The time in seconds to update the wind
//	private float windChange;
	
	
	private World()
	{
	}
	
	public void initialize(int width, int height, float initForce, float initHealth, float initGravity)
	{
		if (initialized)
			return;
		
		land = new Land(width, height);
		
		player1 = new Tank();
		player2 = new Tank();
		
		initialForce = initForce;
		initialHealth = initHealth;
		gravity = initGravity;
		
		wind = new Vector();
		
		initialized = true;
	}
	
	public void newLevel()
	{
		if (!initialized) return;
		
		// Generate a new landscape.
		land.generate();
		
		// Used to determine where to place the tanks.
		int quarter = land.getWidth() / 4;

		// This is to keep player 1 from going off the left
		int minLeft = Tank.BOUNDING_RADIUS;
		// This is to keep player 2 from going off the right
		int maxRight = land.getWidth() - Tank.BOUNDING_RADIUS;
		
		// Player 1 will start on the first quarter of the screen.
		int player1Start = Util.random(minLeft, quarter);
		// Player 2 will start on the last quarter of the screen.
		int player2Start = Util.random(quarter * 3, maxRight);

		// Initialize player 1's tank on his half where the center of the tank
		// is resting on the land. The initial angle points towards player 2 at 45.
		player1.initialize(player1Start, land.getY(player1Start), 45, initialForce, initialHealth);
 		
		// Initialize player 2's tank on his half where the center of the tank
		// is resting on the land. The initial angle points towards player 1 at 45.
		player2.initialize(player2Start, land.getY(player2Start), 135, initialForce, initialHealth);
	}
	
	public void draw(Graphics2D gr)
	{
		if (!initialized) return;
		
		gr.setColor(Color.red);
		player1.draw(gr);
		gr.setColor(Color.blue);
		player2.draw(gr);

		if (projectile != null)
		{
			gr.setColor(Color.black);
			projectile.draw(gr);	
		}
		
		gr.setColor(new Color(0, 180, 0));
		land.draw(gr);
	}
	
	public void update(float deltatime)
	{
		if (!initialized) return;
		
		// If the land beneath the tank has been removed then
		// shift the tanks down.
		player1.updatePosition(land);
		player2.updatePosition(land);
		
		// If there is a projectile in the air...
		if (projectile != null)
		{
			// Update its position based on its velocity,
			// gravity, and the wind.
			projectile.update(deltatime);
			
			// If the projectile hit the enemy player...
			if (projectile.intersects(enemyPlayer))
			{
				// Subtract the projectiles attack from the
				// enemy players health. If they're dead, flag
				// the end of game.
				enemyPlayer.health -= projectile.getAttack();
				
				if (enemyPlayer.health <= 0)
					playerLost();

				// No more projectile...
				projectile = null;
			}
			
			// If the projectile hit the land..
			else if (projectile.hit(land))
			{
				// Remove the chunk of land
				int x = (int)projectile.getX();
				int y = (int)projectile.getY();
				int crater = (int)projectile.getCraterSize();
				land.remove(x, y, crater);
				// No more projectile...
				projectile = null;
			}
			
			// If the projectile went past one of the sides
			else if (projectile.getX() < 0 || projectile.getX() > land.getWidth())
				projectile = null;
		}
		
		// Update the winds direction and force periodically.
//		windTime += deltatime;
//		if (windTime >= windChange)
//		{
//			windTime -= windChange;
//			windChange = (float)Math.random() * 3f;
//		}
		
	}
	
	public void playerLost()
	{
		switchTurns();
	}
	
	
	public float getGravity()
	{
		return gravity;
	}
	
	public Vector getWind()
	{
		return wind;
	}
	
	public Vector getTotalForces()
	{
		return new Vector(wind.x, wind.y + gravity);
	}
	
	public void switchTurns()
	{
		Tank current = currentPlayer;
		currentPlayer = enemyPlayer;
		enemyPlayer = current;
	}
	
	public void startPlayer1()
	{
		currentPlayer = player1;
		enemyPlayer = player2;
	}
	
	public void startPlayer2()
	{
		currentPlayer = player2;
		enemyPlayer = player1;
	}
	
	public Tank getCurrentPlayer()
	{
		return currentPlayer;
	}
	
	public Tank getEnemyPlayer()
	{
		return enemyPlayer;
	}

	public void shoot()
	{
		if (projectile != null)
			return;
		
		projectile = currentPlayer.createProjectile();
	}
	
}
