package shipgames.ballspring;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import shipgames.GameScreen;

public class BallOnSpring extends GameScreen implements MouseMotionListener 
{


	public static void main(String[] args) {
		showWindow(new BallOnSpring(), "Testing");
	}

	// The starting X position of the spring end, the mouse x-coordinate
	private float s_x1 = 256;
	// The starting Y position of the spring end, the mouse y-coordinate
	private float s_y1 = 256;
	// The length of the spring (without gravity)
	private float s_length = 1.0f;
	// The springiness of the spring
	private float s_constant = 1.5f;

	// The gravity of the world
	private float gravity = 50.0f;

	// The mass of the ball on the end
	private float m_mass = 10f;
	// The inverse of the ball mass
	private float m_imass = 1f / m_mass;
	// The damping aka friction of the ball on the world
	private float m_damping = 0.995f;
	// The center x-coordinate of the ball and ending x-coordinate of the spring
	private float m_x = 256;
	// The center y-coordinate of the ball and ending y-coordinate of the spring
	private float m_y = 340;
	// The velocity of the ball on the x-axis
	private float m_vx = 0;
	// The velocity of the ball on the y-axis
	private float m_vy = 0;
	// The force of the ball (from the spring) on the x-axis
	private float m_fx = 0;
	// The force of the ball (from the spring) on the x-axis
	private float m_fy = 0;

	public BallOnSpring() 
	{
		super(512, 512, true);
		setBackground(Color.black);
		addMouseMotionListener(this);
	}

	@Override
	public void draw(Graphics2D gr) {
		// Draw Ball
		gr.setColor(Color.white);
		gr.draw(new Ellipse2D.Float(m_x - 20, m_y - 20, 40, 40));

		// Draw Spring
		gr.setColor(Color.green);
		gr.draw(new Line2D.Float(m_x, m_y, s_x1, s_y1));

		// Calculate mid-point and current length of spring
		float mx = (m_x + s_x1) * 0.5f;
		float my = (m_y + s_y1) * 0.5f;
		float dx = s_x1 - m_x;
		float dy = s_y1 - m_y;
		float distance = (float)Math.sqrt(dx * dx + dy * dy);

		// Draw Length of Spring
		gr.setColor(Color.blue);
		gr.drawString(String.valueOf(distance), mx, my);
	}

	@Override
	public void update(float deltatime) {

		m_fx = 0f;
		m_fy = 0f;

		float nx = s_x1 - m_x;
		float ny = s_y1 - m_y;
		float dot = (nx * nx) + (ny * ny);

		if (dot != 0.0)
		{	
			float distance = (float)Math.sqrt(dot);
			float invdist = 1f / distance;
			float force = (s_length - distance) * -s_constant;
			m_fx = (nx * invdist) * force;
			m_fy = (ny * invdist) * force;
		}
		m_fy += gravity;

		m_vx += m_fx * m_imass;
		m_vy += m_fy * m_imass;

		m_vx *= m_damping;
		m_vy *= m_damping;

		m_x += m_vx * deltatime;
		m_y += m_vy * deltatime;
	}

	public void mouseMoved(MouseEvent e) {
		s_x1 = e.getX();
		s_y1 = e.getY();
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
	}

}
