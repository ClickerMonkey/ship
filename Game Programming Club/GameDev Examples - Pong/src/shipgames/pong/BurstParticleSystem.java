package shipgames.pong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import shipgames.Util;
import shipgames.Vector;

/**
 * A Particle System where a certain number of particles are released in a 
 * certain amount of bursts every certain number of seconds. The particles in 
 * the system have an initial alpha, size, color, and their final alpha, size, 
 * and color. Every particles life span is based on a minimum and maximum 
 * lifetime in seconds. Every particles velocity is based on a minimum and 
 * maximum speed in pixels-per-second.
 * 
 * @author Philip Diffenderfer
 * 
 */
public class BurstParticleSystem
{

	// The number of bursts to release after start.
	private int bursts;
	// The current burst count the system is on.
	private int burstCount;
	// The number of particles to release every burst.
	private int particlesPerBurst;
	// The number of seconds between each burst.
	private float burstInterval;
	// The current time in seconds since the last burst.
	private float burstTime;
	// The gravity that affects the particles.
	private float gravity;
	// The minimum and maximum lifetime of the particles in seconds.
	private float minLifetime, maxLifetime;
	// The minimum and maximum velocity of the particles in pixels-per-second.
	private float minVelocity, maxVelocity;
	// The starting and ending size of the particles throughout their lifetime in pixels.
	private float startSize, endSize;
	// The starting and ending transparency of the particles throughout their lifetime.
	private float startAlpha, endAlpha;
	// The current position in space of this system.
	private Vector position;
	// The starting and ending color of the particles throughout their lifetime.
	private Color startColor, endColor;
	// The list of particles.
	private ArrayList<Particle> particles;
	
	/**
	 * Initializes an explosion class with all available options.
	 * 
	 * @param bursts => The number of explosions to release at "explode()"
	 * @param particlesPerBursts => The number of particles to release per explosion.
	 * @param burstInterval => The time in seconds between each explosion.
	 * @param gravity => The gravity affecting the particles.
	 * @param minLifetime => The minimum lifetime in seconds of a particle.
	 * @param maxLifeTime => The maxmimum lifetime in seconds of a particle.
	 * @param minVelocity => The minimum speed in pixels-per-second of a particle.
	 * @param maxVelocity => The maximum speed in pixels-per-second of a particle.
	 * @param startSize => The starting size in pixels of a particle.
	 * @param endSize => The ending size in pixels of a particle.
	 * @param startAlpha => The start transparency of a particle.
	 * @param endAlpha => The end transparency of a particle.
	 * @param startColor => The start color of a particle.
	 * @param endColor => The end color of a particle.
	 */
	public BurstParticleSystem(int bursts, int particlesPerBursts, float burstInterval, 
			float gravity, float minLifetime, float maxLifetime,  float minVelocity, 
			float maxVelocity, float startSize, float endSize, float startAlpha, 
			float endAlpha, Color startColor, Color endColor)
	{
		set(bursts, particlesPerBursts, burstInterval);
		setGravity(gravity);
		setLifetime(minLifetime, maxLifetime);
		setVelocity(minVelocity, maxVelocity);
		setSize(startSize, endSize);
		setAlpha(startAlpha, endAlpha);
		setColor(startColor, endColor);
		
		int size = (bursts != -1 ? bursts * particlesPerBursts : particlesPerBursts * 10);
		
		particles = new ArrayList<Particle>(size);
		position = new Vector();
	}
	
	/**
	 * Triggers this explosion. If its already explosion it clears the explosion number count
	 * and continues to update the old ones but also creates the new ones at the new system
	 * position.
	 * 
	 * @param x => The x coordinate to explode at.
	 * @param y => The y coordinate to explode at.
	 */
	public void start(float x, float y)
	{
		position.set(x, y);
		
		burstCount = 0;
		burstTime = burstInterval;
	}
	
	/**
	 * Stops anymore explosions from happening.
	 */
	public void stop()
	{
		burstCount = bursts;
	}
	
	/**
	 * Draws this Explosion's particles.
	 * 
	 * @param gr => The graphics object to draw to.
	 */
	public void draw(Graphics2D gr)
	{
		for (int i = 0; i < particles.size(); i++)
			particles.get(i).draw(gr);
	}
	
	/**
	 * Updates this explosion and releases any particles its needs to as well
	 * as updates the current particles, removing them as they die.
	 * 
	 * @param deltatime => The time in seconds since the last update call was made.
	 */
	public void update(float deltatime)
	{
		// If we still have explosions of particles to trigger...
		if (burstCount < bursts || bursts == -1)
		{
			burstTime += deltatime;
			// If the time since the last explosion has reached the interval
			// the release 'n' amount of particles.
			if (burstTime >= burstInterval)
			{
				// Release the number of particles
				for (int i = 0; i < particlesPerBurst; i++)
					particles.add(createParticle());
				// Increment the explosion count
				burstCount++;
				burstTime -= burstInterval;
			}
		}
		// If there are particles in the explosion then update them
		if (particles.size() != 0)
		{
			Particle p;
			for (int i = particles.size() - 1; i >= 0; i--)
			{
				p = particles.get(i);
				// Update the particles position.
				p.update(deltatime, gravity);
				// If the particle is dead then remove it.
				if (p.isDead())
				{
					particles.remove(i);
				}
				else
				{
					// Sets the particles size and color based on its current life and life span.
					float delta = p.lifeDelta();
					float t = 1f / 255f;
					p.size = getDelta(startSize, endSize, delta);
					p.alpha = getDelta(startAlpha, endAlpha, delta);
					p.red = getDelta(startColor.getRed(), endColor.getRed(), delta) * t;
					p.green = getDelta(startColor.getGreen(), endColor.getGreen(), delta) * t;
					p.blue = getDelta(startColor.getBlue(), endColor.getBlue(), delta) * t;
				}
			}
		}
		
	}
	
	/**
	 * Creates a new particles at the explosions coordinates and points
	 * it in a random direction at a velocity between the min and max velocities.
	 */
	public Particle createParticle()
	{
		float lifespan = Util.random(minLifetime, maxLifetime);
		Particle p = new Particle(lifespan);
		p.size = startSize;
		p.alpha = startAlpha;
		
		setInitialPosition(p);
		setInitialVelocity(p);
		
		return p;
	}
	
	/**
	 * Sets a particles position at creation.
	 */
	private void setInitialPosition(Particle p)
	{
		p.position.set(position);
	}

	/**
	 * Sets a particles velocity at creation.
	 */
	private void setInitialVelocity(Particle p)
	{
		float angle = Util.random(-(float)Math.PI, (float)Math.PI);
		float velocity = Util.random(minVelocity, maxVelocity);
		p.velocity.set((float)Math.cos(angle) * velocity, (float)Math.sin(angle) * velocity);
	}
	
	/**
	 * Gets a value between start and end at 'delta', where 0.0 is the 
	 * start value, 1.0 is the end value, and 0.5 is in the middle.
	 */
	private float getDelta(float start, float end, float delta)
	{
		return (end - start) * delta + start;
	}
	
	/**
	 * Sets the number of explosions to set off, the number of particles per explosion, 
	 * and the time in seconds between each explosion.
	 */
	public void set(int bursts, int particlesPerBurst, float burstInterval)
	{
		this.bursts = bursts;
		this.burstCount = bursts;
		this.particlesPerBurst = particlesPerBurst;
		this.burstInterval = burstInterval;
	}

	/**
	 * Sets the minimum and maximum lifetime for the particles created.
	 */
	public void setLifetime(float minLifetime, float maxLifetime)
	{
		this.minLifetime = minLifetime;
		this.maxLifetime = maxLifetime;
	}
	
	/**
	 * Sets the starting and ending size for the particles.
	 */
	public void setSize(float startSize, float endSize)
	{
		this.startSize = startSize;
		this.endSize = endSize;
	}
	
	/**
	 * Sets the minimum and maximum velocity for the particles created.
	 */
	public void setVelocity(float minVelocity, float maxVelocity)
	{
		this.minVelocity = minVelocity;
		this.maxVelocity = maxVelocity;
	}
	
	/**
	 * Sets the starting and ending transparency for the particles.
	 */
	public void setAlpha(float startAlpha, float endAlpha)
	{
		this.startAlpha = startAlpha;
		this.endAlpha = endAlpha;
	}
	
	/**
	 * Sets the starting and ending colors for the particles.
	 */
	public void setColor(Color startColor, Color endColor)
	{
		this.startColor = startColor;
		this.endColor = endColor;
	}
	
	/**
	 * Sets the gravity that affects all the particles.
	 */
	public void setGravity(float gravity)
	{
		this.gravity = gravity;
	}
	
	/**
	 * Sets the position of the particle system.
	 */
	public void setPosition(float x, float y)
	{
		position.set(x, y);
	}
	
	/**
	 * Returns whether this explosion has any active particles.
	 */
	public boolean isActive()
	{
		return (particles.size() != 0);
	}
	
	/**
	 * The number of bursts to release after start.
	 */
	public int getTotalBursts()
	{
		return bursts;
	}
	
	/**
	 * The current burst count the system is on.
	 */
	public int getCurrentBurst()
	{
		return burstCount;
	}
	
	/**
	 * The number of seconds between each burst.
	 */
	public float getBurstInterval()
	{
		return burstInterval;
	}
	
	/**
	 * The current time in seconds since the last burst.
	 */
	public float getBurstTime()
	{
		return burstTime;
	}
	
	/**
	 * The number of particles to release every burst.
	 */	
	public int getParticlesPerBurst()
	{
		return particlesPerBurst;
	}

	/**
	 * The gravity that affects the particles.
	 */
	public float getGravity()
	{
		return gravity;
	}
	
	/**
	 * The maximum lifetime of the particles in seconds.
	 */	
	public float getMaxLifetime()
	{
		return maxLifetime;
	}

	/**
	 * The minimum lifetime of the particles in seconds.
	 */	
	public float getMinLifetime()
	{
		return minLifetime;
	}

	/**
	 * The maximum velocity of the particles in pixels-per-second.
	 */	
	public float getMaxVelocity()
	{
		return maxVelocity;
	}

	/**
	 * The minimum velocity of the particles in pixels-per-second.
	 */	
	public float getMinVelocity()
	{
		return minVelocity;
	}

	/**
	 * The starting size of the particles throughout their lifetime in pixels.
	 */	
	public float getStartSize()
	{
		return startSize;
	}

	/**
	 * The ending size of the particles throughout their lifetime in pixels.
	 */	
	public float getEndSize()
	{
		return endSize;
	}

	/**
	 * The starting transparency of the particles throughout their lifetime.
	 */	
	public float getStartAlpha()
	{
		return startAlpha;
	}

	/**
	 * The ending transparency of the particles throughout their lifetime.
	 */	
	public float getEndAlpha()
	{
		return endAlpha;
	}

	/**
	 * The starting color of the particles throughout their lifetime.
	 */	
	public Color getStartColor()
	{
		return startColor;
	}

	/**
	 * The ending color of the particles throughout their lifetime.
	 */	
	public Color getEndColor()
	{
		return endColor;
	}
	
	
	/**
	 * A particle in any explosion that has a lifetime, position, velocity,
	 * size, alpha, and red, green, and blue components.
	 */
	private class Particle
	{
		private final float lifespan;
		private float time;
		private float size;
		private float alpha, red, green, blue;
		private Vector position;
		private Vector velocity;
		
		/**
		 * Initializes a particle with its life span in seconds.
		 * 
		 * @param life => How long this particle will last in seconds.
		 */
		public Particle(float life)
		{
			lifespan = life;
			time = 0f;
			
			position = new Vector();
			velocity = new Vector();
		}
		
		/**
		 * Updates a particles position based on its velocity and the gravity affecting it.
		 */
		public void update(float deltatime, float gravity)
		{
			position.add(velocity, deltatime);
			position.y += gravity * deltatime;
			
			time += deltatime;
		}
		
		/**
		 * This method draws each particle.
		 */
		public void draw(Graphics2D gr)
		{
			float radius = size * 0.5f;
			gr.setColor(new Color(red, green, blue, alpha));
			gr.fill(new Ellipse2D.Double(position.x - radius, position.y - radius, size, size));
		}
		
		/**
		 * Returns a value that is between 0.0 and 1.0 where 0.0 is a new born
		 * particle and 1.0 is a particle that is dead (met its life span).
		 */
		public float lifeDelta()
		{
			return (time / lifespan);
		}

		/**
		 * Returns true if this particle is dead.
		 */
		public boolean isDead()
		{
			return (time >= lifespan);
		}
		
	}
	
}