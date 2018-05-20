package shipgames.tanks;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import shipgames.Vector;

public class Tank
{

	public static int BOUNDING_RADIUS = 10;
	public static int TURRET_LENGTH = 10;
	
	private Vector location;
	private Vector turretAngle;
	private float turretForce;
	
	public float health;
	
	public Tank()
	{
		location = new Vector();
		turretAngle = new Vector();
	}
	
	public void initialize(int x, int y, float angle, float force, float health)
	{
		location.set(x, y);
		turretAngle.set(angle);
		turretForce = force;
	}
	
	public void draw(Graphics2D gr)
	{
		// Draw the main body.
		float left = location.x - BOUNDING_RADIUS;
		float top = location.y - BOUNDING_RADIUS;
		float size = BOUNDING_RADIUS * 2;
		
		gr.fill(new Ellipse2D.Float(left, top, size, size));
		
		// Draw the turret.
		Vector origin = getTurretOrigin();
		Vector end = getTurretEnd();
		gr.draw(new Line2D.Float(origin.x, origin.y, end.x, end.y));
	}
	
	public Projectile createProjectile()
	{
		return new Projectile(getTurretEnd(), turretAngle, turretForce);
	}
	
	public void addForce(float force)
	{
		turretForce += force;
	}
	
	public void updatePosition(Land l)
	{
		location.y = l.getY((int)location.x);
	}
	
	public Vector getTurretEnd()
	{
		Vector end = new Vector(turretAngle);
		end.multiply(BOUNDING_RADIUS + TURRET_LENGTH);
		end.add(location);
		return end;
	}
	
	public Vector getTurretOrigin()
	{
		Vector origin = new Vector(turretAngle);
		origin.multiply(BOUNDING_RADIUS);
		origin.add(location);
		return origin;
	}
	
	public Vector getTurretAngle()
	{
		return turretAngle;
	}
	
	public float getTurretForce()
	{
		return turretForce;
	}
	
	public float getX()
	{
		return location.x;
	}
	
	public float getY()
	{
		return location.y;
	}
	
	
	
}
