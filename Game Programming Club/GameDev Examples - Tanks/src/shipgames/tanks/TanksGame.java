package shipgames.tanks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import shipgames.GameScreen;
import shipgames.Vector;

@SuppressWarnings("serial")
public class TanksGame extends GameScreen implements KeyListener
{

	public static void main(String[] args)
	{
		showWindow(new TanksGame(), "Tanks Game");
	}
	
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;

	private static final float ROTATE_AMOUNT = 1f;
	private static final float FORCE_AMOUNT = 5f;
	
	private World world;
//	private float time;
	
	public TanksGame()
	{
		super(WIDTH, HEIGHT, true);

		addKeyListener(this);
		setBackground(new Color(100, 149, 237));
		setupMathCoordinateSystem();
		
		world = World.getInstance();
		world.initialize(WIDTH, HEIGHT, 200f, 20f, 100f);
		world.newLevel();
		world.startPlayer1();
		
	}
	
	@Override
	public void draw(Graphics2D gr)
	{
		world.draw(gr);
		
		// Draw the stats for the current tank
		Tank current = world.getCurrentPlayer();
		String force = String.format("Force <[Left] [Right]>: %.1f", current.getTurretForce());
		String angle = String.format("Angle <[Up] [Down]>: %.1f", current.getTurretAngle().angle());
		setupDefaultCoordinateSystem();
		gr.setColor(Color.black);
		gr.drawString(force, 20, 20);
		gr.drawString(angle, 20, 40);
		setupMathCoordinateSystem();
	}

	@Override
	public void update(float deltatime)
	{
		world.update(deltatime);
//		time += deltatime;
//		if (time > 3)
//		{
//			time -= 3f;
//			int x = land.random(0, WIDTH);
//			int radius = land.random(5, 50);
//			land.remove(x, radius);
//		}
	}

	public void keyTyped(KeyEvent e)
	{

	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		Tank player = world.getCurrentPlayer();
		Vector turret = player.getTurretAngle();
		
		switch (key)
		{
		case KeyEvent.VK_N:
			world.newLevel();
			break;
		case KeyEvent.VK_DOWN:
			turret.rotate(ROTATE_AMOUNT);
			break;
		case KeyEvent.VK_UP:
			turret.rotate(-ROTATE_AMOUNT);
			break;
		case KeyEvent.VK_LEFT:
			player.addForce(-FORCE_AMOUNT);
			break;
		case KeyEvent.VK_RIGHT:
			player.addForce(FORCE_AMOUNT);
			break;
		case KeyEvent.VK_SPACE:
			world.shoot();
			break;
		}
		
	}

	public void keyReleased(KeyEvent e)
	{
		
	}


}
