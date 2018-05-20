package axe.util;

import java.util.Arrays;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import axe.TimeUnit;
import axe.Timer;
import axe.input.KeyEvent;
import axe.input.KeyListener;
import axe.input.MouseEvent;
import axe.input.MouseListener;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;


public class Screen {

	private int width;
	private int height;
	private Timer timer;
	private TimeUnit elapsed;

	private ScreenListener[] screenListener;

	private KeyEvent keyEvent;
	private KeyListener[] keyListener;

	private MouseEvent mouseEvent;
	private MouseListener[] mouseListener;

	public Screen(int width, int height, boolean fullscreen, int samples) {
		DisplayMode mode = new DisplayMode(width, height);

		try {
			Display.setDisplayMode(mode);
			Display.setFullscreen(fullscreen);
			Display.setVSyncEnabled(false);
			
			if (samples > 0) {
				boolean created = false;
				int total = 1 << samples;
				while (!created) {
					try {
						Display.create(new PixelFormat(8, 16, 0, total));
						created = true;
					}
					catch (LWJGLException e) {
						total >>= 1;
						samples--;
					}
				}
				System.out.println("Samples chosen: " + samples);	
			}
			else {
				Display.create();
			}
			

			Keyboard.create();
			Keyboard.enableRepeatEvents(false);

			Mouse.create();
			Mouse.setGrabbed(true);

		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		this.width = width;
		this.height = height;
		this.timer = new Timer();

		this.screenListener = new ScreenListener[0];
		this.keyListener = new KeyListener[0];
		this.mouseListener = new MouseListener[0];

		this.keyEvent = new KeyEvent();
		this.mouseEvent = new MouseEvent();
	}

	public void setup2D() {
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_DEPTH_TEST);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluOrtho2D(0, width, 0, height);
		
		glClearAccum(0f, 0f, 0f, 1f);
		glClear(GL_ACCUM_BUFFER_BIT);
		
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glViewport(0, 0, width, height);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	}
	
	public void setup3D() {
		glEnable(GL_TEXTURE_2D); // Enable Texture Mapping
		glShadeModel(GL_SMOOTH); // Enable Smooth Shading
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Black Background
		glClearDepth(1.0); // Depth Buffer Setup
		glEnable(GL_DEPTH_TEST); // Enables Depth Testing
		glDepthFunc(GL_LEQUAL); // The Type Of Depth Testing To Do

		glMatrixMode(GL_PROJECTION); // Select The Projection Matrix
		glLoadIdentity(); // Reset The Projection Matrix

		// Calculate The Aspect Ratio Of The Window
		gluPerspective(45.0f, (float)width / (float)height, 0.1f, 100.0f);
		glMatrixMode(GL_MODELVIEW); // Select The Modelview Matrix

		// Really Nice Perspective Calculations
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		
//		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
//		glClearDepth(1.0);				
//
//		glViewport(0, 0, width, height);		
//		glMatrixMode(GL_PROJECTION);
//		glLoadIdentity();		
//
//		gluPerspective(45.0f,(float)width/(float)height,1.0f,200.0f);
//		glMatrixMode(GL_MODELVIEW);
//		
//		glEnable(GL_DEPTH_TEST);
//		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
//		glShadeModel(GL_SMOOTH);
	}

	public void start() {
		timer.start();

		elapsed = timer.elapsed();

		for (ScreenListener sl : screenListener) {
			sl.onStart();
		}
		
		Display.update();
		while (!Display.isCloseRequested()) {

			timer.update();

			handleKeyboard();
			handleMouse();

			if (handleWindow()) {
				continue;
			}

			for (ScreenListener sl : screenListener) {
				sl.onUpdate(elapsed);
			}
			for (ScreenListener sl : screenListener) {
				sl.onDraw();
			}

			Display.update();
			//			Thread.yield();
		}
	}
	
	public TimeUnit getElapsed() {
		return elapsed;
	}

	public void addKeyListener(KeyListener listener) {
		int size = keyListener.length;
		keyListener = Arrays.copyOf(keyListener, size + 1);
		keyListener[size] = listener;
	}

	public void addMouseListener(MouseListener listener) {
		int size = mouseListener.length;
		mouseListener = Arrays.copyOf(mouseListener, size + 1);
		mouseListener[size] = listener;
	}

	public void addScreenListener(ScreenListener listener) {
		int size = screenListener.length;
		screenListener = Arrays.copyOf(screenListener, size + 1);
		screenListener[size] = listener;
	}

	private boolean handleWindow() {

		// If the window is visible then return
		if (Display.isVisible()) {
			return false;
		}
		// If the window has been visual distorted then redraw...
		if (Display.isDirty()) {
			for (ScreenListener sl : screenListener) {
				sl.onDraw();
			}
		}
		// Sleep 100ms to avoid CPU hogging.
		try {
			Thread.sleep(100);
		} catch (InterruptedException ie) {
		}
		// The window has been handled
		return true;
	}

	private void handleKeyboard() {

		if (keyListener.length == 0) {
			return;
		}

		while (Keyboard.next()) {
			keyEvent.update();

			if (keyEvent.state) {
				for (KeyListener kl : keyListener) {
					kl.onKeyDown(keyEvent);
				}
			} else {
				for (KeyListener kl : keyListener) {
					kl.onKeyUp(keyEvent);
				}
			}
		}
	}

	private void handleMouse() {

		if (mouseListener.length == 0) {
			return;
		}

		boolean wasInWindow = mouseEvent.inWindow;
		boolean isInWindow = Mouse.isInsideWindow();
		// Has the inWindow state changed?
		if (wasInWindow != isInWindow) {
			// If it was in the window and now it's not, it exited.
			if (wasInWindow && !isInWindow) {
				for (MouseListener ls : mouseListener) {
					ls.onMouseExit(mouseEvent);
				}
			}
			// If it was not in the window and now it its, its entered.
			if (!wasInWindow && isInWindow) {
				for (MouseListener ls : mouseListener) {
					ls.onMouseEnter(mouseEvent);
				}
			}

			// Update the mouse events state
			mouseEvent.inWindow = isInWindow;
			return;
		}

		int events = 0;

		while (Mouse.next()) {
			mouseEvent.update();
			events++;

			// Has the button changed?
			if (mouseEvent.button != -1) {
				// Button down?
				if (mouseEvent.buttonState) {
					for (MouseListener ls : mouseListener) {
						ls.onMouseDown(mouseEvent);
					}
				} else {
					for (MouseListener ls : mouseListener) {
						ls.onMouseUp(mouseEvent);
					}
				}
			}
			// Change in location?
			if (mouseEvent.dx != 0 || mouseEvent.dy != 0) {
				if (mouseEvent.hasButtonDown()) {
					for (MouseListener ls : mouseListener) {
						ls.onMouseDrag(mouseEvent);
					}
				} else {
					for (MouseListener ls : mouseListener) {
						ls.onMouseMove(mouseEvent);
					}
				}
			}
			// Change in wheel?
			if (mouseEvent.wheel != 0) {
				for (MouseListener ls : mouseListener) {
					ls.onMouseWheel(mouseEvent);
				}
			}
		}

		if (events == 0 && isInWindow) {
			for (MouseListener ls : mouseListener) {
				ls.onMouseHover(mouseEvent);
			}
		}
	}

	public void close() {
		for (ScreenListener sl : screenListener) {
			sl.onClose();
		}
		Display.destroy();
		System.exit(0);
	}

	public int width() {
		return width;
	}

	public int height() {
		return height;
	}

}