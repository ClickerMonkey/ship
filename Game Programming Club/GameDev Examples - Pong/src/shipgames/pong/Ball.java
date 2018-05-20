package shipgames.pong;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Ball extends shipgames.pongbasic.Ball
{

	public Ball(float x, float y, float radius, float angle, float speed, float maxSpeed, Color color)
	{
		super(x, y, radius, angle, speed, maxSpeed, color);
	}
	
	/**
	 * Draws this balls tail and then itself with gradients.
	 */
	public void draw(Graphics2D gr)
	{
		
		Ellipse2D s = new Ellipse2D.Double(center.x - radius, center.y - radius, radius * 2, radius * 2);
		Color transparent = new Color(255, 127, 0, 0);
		
		gr.setPaint(null);
		gr.setColor(color);
		gr.fill(s);
		gr.setPaint(new GradientPaint(
				(float)(center.x - radius * 0.25), 
				(float)(center.y - radius * 0.25), transparent, 
				(center.x + radius), 
				(center.y + radius), Color.red));
		gr.fill(s);
		float inner = radius * 0.9f;
		s = new Ellipse2D.Double(center.x - inner, center.y - inner, inner * 2, inner * 2);
		gr.setPaint(new GradientPaint(
				(center.x - inner), 
				(center.y - inner), Color.white, 
				(center.x - inner), 
				center.y, transparent));
		gr.fill(s);
	}
	
}
