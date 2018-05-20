package shipgames.tanks;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import shipgames.Vector;

public class Projectile
{

	private Vector location = new Vector();
	private Vector velocity = new Vector();
	private float radius;
	private float attack;
	
	public Projectile(Vector initialLocation, Vector initialVelocity, float force)
	{
		location.set(initialLocation);
		velocity.set(initialVelocity);
		velocity.multiply(force);
		radius = 2f;
		attack = 8f;
	}
	
	public void update(float deltatime)
	{
		// Increase the location by the velocity's direction and the current speed.
		location.add(velocity, deltatime);
		// Apply wind and gravity to the location
		velocity.y -= World.getInstance().getGravity() * deltatime;
	}
	
	public void draw(Graphics2D gr)
	{
		// Draw an ellipse centered on location.
		float left = location.x - radius;
		float top = location.y - radius;
		float size = radius * 2;
		gr.fill(new Ellipse2D.Float(left, top, size, size));
	}
	
	public boolean intersects(Tank t)
	{
		float dr = radius + Tank.BOUNDING_RADIUS;
		float dx = location.x - t.getX();
		float dy = location.y - t.getY();
		
		return (dx * dx + dy * dy <= dr * dr);
	}
	
	public boolean hit(Land land)
	{
		return land.intersects((int)location.x, (int)location.y);
	}
	
	public float getAttack()
	{
		return attack;
	}
	
	public float getX()
	{
		return location.x;
	}
	
	public float getY()
	{
		return location.y;
	}
	
	public float getRadius()
	{
		return radius;
	}
	
	public float getCraterSize()
	{
		return (radius * attack);
	}
	
}
