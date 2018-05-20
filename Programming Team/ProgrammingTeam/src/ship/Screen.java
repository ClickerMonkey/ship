package ship;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class Screen extends JPanel implements ComponentListener
{

	private Timer timer;	
	private ScreenListener listener;
	private long lastSleepTime;
	private long updateTime = 0;
	
	//===========================================================================
	// Graphics
	private Graphics2D graphics;
//	private BufferedImage buffer;
//	private int[] clearRow;
	private Object antialiasingFlag = RenderingHints.VALUE_ANTIALIAS_ON;
	
	//===========================================================================
	// Concurrency
	private volatile boolean isRunning;
	private final ReentrantLock lock = new ReentrantLock();
	private final CountDownLatch finished = new CountDownLatch(1);
	
	private boolean[] keyDown = new boolean[256];
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @param listener
	 */
	public Screen(int width, int height) {
		
		Dimension d = new Dimension(width, height);
		setSize(d);
		setPreferredSize(d);
		setFocusable(true);
		
		addComponentListener(this);
		
//		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//		clearRow = new int[width];
		timer = new Timer();
	}
	
	public void lockSize() {
		setMaximumSize(getSize());
		setMinimumSize(getSize());
	}
	
	public void setListener(ScreenListener listener) {
		this.listener = listener;
	}

	/**
	 * Starts the game loop until the game is exited.
	 */
	public void start()
	{
		timer.start();
		isRunning = true;

		// While the window is visible
		while (isRunning)
		{
			// Update timer
			timer.update();
			
			// Update and draw
			listener.update(timer.getElapsed());
			
			paint(getGraphics());
			
			// Sleep until the next frame
			try {
				sleep(updateTime);
			} catch (Exception e) {
			}
		}
		
		// We're done the game loop
		finished.countDown();
	}
	
	/**
	 * Overrides the update method to enable double buffering.
	 */
	public final void update(Graphics g) {
		paint(g);
	}
	
	/**
	 * Overrides the Panel's paint method to render the game.
	 */
	public final void paint(Graphics g) {
//		lock.lock();
//		try  {
//			if (g == null || buffer == null)
//				return;
			
			// Get the graphics of the buffer
//			int width = getWidth();
//			int height = getHeight();
			
//			WritableRaster wr = buffer.getRaster();
//			while (--height >= 0) {
//				wr.setPixels(0, height, width, 1, clearRow);
//			}
			
//			graphics = (Graphics2D)buffer.getGraphics();
			graphics = (Graphics2D)g;
			// Clear the buffer with the background color
//			graphics.setColor(getBackground());
//			graphics.fillRect(0, 0, getWidth(), getHeight());
			// If antialising is turned on enable it.
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antialiasingFlag);

			// Draw the game objects to the buffers graphics.
			listener.draw(graphics);
//			graphics.dispose();
			// Draw the buffer to the main graphics
//			g.drawImage(buffer, 0, 0, null);
//		} catch (Exception e) {
//		} finally {
//			lock.unlock();
//		}
	}
	
	/**
	 * 
	 */
	public void exitGame() {
		isRunning = false;
		try {
			finished.await();
		} catch (Exception e) { }
	}
	
	/**
	 * Sets the frame rate of the screen in frames per second.
	 * 
	 * @param framesPerSecond
	 */
	public void setFrameRate(int framesPerSecond) {
		updateTime = 1000000L / framesPerSecond;
	}
	
	/**
	 * Sleep the delay time. This will not just sleep the given time but it 
	 * will make sure that the given time is at least the time between these
	 * sleep calls. This ensures that the update-able is being updated at a 
	 * fixed interval and the runtime of its update method should have no
	 * effect on its execution interval (unless its update method takes longer).
	 * 
	 * @param delay => The time between update invoking in milliseconds.
	 * 
	 * @throws InterruptedException => If the thread was interrupted during its
	 * 		sleeping time.
	 */
	private void sleep(long delayNanos) throws InterruptedException
	{
		long currentTime = System.nanoTime();
		long difference = currentTime - lastSleepTime;
		long remaining, sleepTime;

		// Continue until the time since the last sleep is equal to or
		// greater then the delay time.
		while (difference < delayNanos)
		{
			remaining = delayNanos - difference;
			// Of the remaining time sleep for 15/16ths of it (round up to 
			// next odd number to avoid sleeping 0 when remaining < 16ms).
			sleepTime = ((remaining >> 0x4) * 0xF) | 0x1;

			Thread.sleep(sleepTime);

			currentTime = System.nanoTime();
			difference = currentTime - lastSleepTime;
		}

		// Adjust the last time this runner slept.
		lastSleepTime = System.nanoTime();
	}
	
	
   /**
    * Invoked when the component's size changes.
    */
   public void componentResized(ComponentEvent e) {
   	lock.lock();
//		buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
//		clearRow = new int[getWidth()];
		lock.unlock();
   }
	public void componentHidden(ComponentEvent e) {
	}
	public void componentMoved(ComponentEvent e) {
	}
	public void componentShown(ComponentEvent e) {
	}

	
	public boolean isAntialiasingEnabled() {
		return (antialiasingFlag == RenderingHints.VALUE_ANTIALIAS_ON);
	}
	                                       
	
	public boolean isKeyDown(int code) {
		return keyDown[code & 0xFF];
	}
	
	public void keyPressed(KeyEvent e) {
		keyDown[e.getKeyCode() & 0xFF] = true;
	}
	public void keyReleased(KeyEvent e) {
		keyDown[e.getKeyCode() & 0xFF] = false;
	}
	public void keyTyped(KeyEvent e) {
	}
	
	public static void showWindow(final Screen screen, String title)
	{
		if (screen == null)
			return;
		
		JFrame window = new JFrame(title);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(screen);
//		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent e) {
				screen.exitGame();
			}
			public void windowActivated(WindowEvent e) { }
			public void windowClosed(WindowEvent e) { }
			public void windowDeactivated(WindowEvent e) { }
			public void windowDeiconified(WindowEvent e) { }
			public void windowIconified(WindowEvent e) { }
			public void windowOpened(WindowEvent e) { }
		});
		window.setVisible(true);
		screen.start();
	}
	
	
}
