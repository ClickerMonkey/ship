package engine;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class TestScene extends JFrame implements WindowFocusListener, WindowListener, WindowStateListener
{

	public static void main(String[] args) {
		new TestScene().start();
	}
	
	class Ball extends Sprite {
		float x, y, vx, vy;
		public Ball(int cx, int cy, int width, int height, float angle, float speed) {
			setSize(width, height);
			setCenter(cx, cy);
			x = cx;
			y = cy;
			vx = (float)Math.cos(angle) * speed;
			vy = (float)Math.sin(angle) * speed;
		}
		@Override
		public void onUpdate(TimeUnit elapsed) {
			x += vx * elapsed.seconds;
			y += vy * elapsed.seconds;
			if (x < 0) {
				vx = Math.abs(vx);
				x = 0;
			}
			if (x > 500) { 
				vx = -Math.abs(vx);
				x = 500;
			}
			if (y < 0) {
				vy = Math.abs(vy);
				y = 0;
			}
			if (y > 400) {
				vy = -Math.abs(vy);
				y = 400;
			}
			setCenter((int)x, (int)y);
		}
	}
	
	class Background extends Sprite {
		private Color backcolor;
		public Background(int x, int y, int width, int height, Color color) {
			setBounds(x, y, x + width, y + height);
			backcolor = color;
		}
		@Override
		public void draw(Graphics gr) {
//			Rectangle bounds = getBounds();
//			int x = bounds.left;
//			int y = bounds.top;
//			int w = bounds.width();
//			int h = bounds.height();
			gr.getInternal().setColor(backcolor);
			gr.getInternal().fill(gr.getInternal().getClip());
		}
	}
	
	private Scene scene;
	private Graphics graphics;
	private Sprite sprite;
	private Background back;
	private boolean running = true;
	
	public TestScene() {
		setSize(500, 400);
		setTitle("Test Scene");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowFocusListener(this);
		addWindowListener(this);
		addWindowStateListener(this);
		setVisible(true);
	}
	
	public void start()
	{
		scene = new Scene(this);
		graphics = new Graphics(this);

		back = new Background(0, 0, 500, 400, Color.gray);
		scene.addSprite(back);
		
		sprite = new Ball(100, 100, 50, 50, 40f, 50f);
		sprite.setTileSource(createTile("Button.png", 0, 0, 250, 250));
		scene.addSprite(sprite);
		
		scene.start();
		scene.invalidate();
		while (running) {
			scene.update();
			scene.draw(graphics);
			try {
				Thread.yield();
			}
			catch (Exception e) {
			}
		}
	}
	
	private Tile createTile(String filename, int x, int y, int width, int height)
	{
		BufferedImage texture = null;
		try {
			texture = ImageIO.read(new File(filename));
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return new Tile(texture, x, y, width, height);
	}

	@Override
	public void windowGainedFocus(WindowEvent e) {
		if (scene != null) {
			scene.invalidate();	
		}
	}

	@Override
	public void windowLostFocus(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.exit(0);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		running = false;
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		if (scene != null) {
			scene.invalidate();	
		}
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
		if (scene != null) {
			scene.invalidate();	
		}
	}

	@Override
	public void windowStateChanged(WindowEvent e) {
		if (scene != null) {
			scene.invalidate();	
		}
	}

	
	
}
