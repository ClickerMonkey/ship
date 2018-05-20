package model3d;

import java.applet.Applet;
import java.awt.Graphics;


public abstract class CanvasApplet extends Applet implements Runnable 
{

	private boolean dirty;
	private boolean running;
	private Thread thread;
	private Canvas canvas;
	
	public abstract void initApplet(Canvas canvas);
	
	public abstract void drawApplet(Canvas canvas);
	
	public void dirty() {
		dirty = true;
	}
	
	public void init() {
		canvas = new Canvas(this);
	}
	
	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void stop() {
		if (thread != null) {
			running = false;
			thread = null;
		}
	}
	
	public void run() {
		dirty = true;
		running = true;
		initApplet(canvas);

		while (running) {
			drawApplet(canvas);
			if (dirty) {
				canvas.invalidate();
				repaint();
				dirty = false;
			}
			Thread.yield();
		}
	}
	
	public void update(Graphics g) {
		canvas.renderTo(g);
	}

}
