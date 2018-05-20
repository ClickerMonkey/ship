package net.philsprojects.game;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import net.philsprojects.game.controls.ControlSystem;
import net.philsprojects.game.util.Color;
import net.philsprojects.game.util.Rectangle;
import net.philsprojects.game.util.Timer;
import net.philsprojects.game.util.Vector;

import com.sun.opengl.util.Animator;


public abstract class SceneApplet extends Applet implements GLEventListener, KeyListener, MouseMotionListener, MouseListener
{

	private static final long serialVersionUID = 59858228611045855L;

	private Timer _timer;
	private boolean[] _keyDown = null;
	private Vector _mouseLocation = Vector.zero();
	private Animator _animator = null;
	private boolean _disposed = false;

	@Override
	public final void init()
	{
		// Set the size according to the size desired by the inherited window
		setSize(this.requestAppletWidth(), this.requestAppletHeight());
		setLayout(new BorderLayout());
		// The window to display openGL graphics
		final GLCanvas canvas = new GLCanvas();
		// Initialize the screen manager with a set number of maximum screens.
		ScreenManager.initialize(this.requestMaximumScreens(), this.requestAppletWidth(), this.requestAppletHeight());
		final ScreenManager m = ScreenManager.getInstance();
		final ControlSystem c = ControlSystem.getInstance();
		// Add all the listeners
		canvas.addKeyListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addMouseListener(this);
		canvas.addGLEventListener(this);
		canvas.addMouseListener(m);
		canvas.addMouseMotionListener(m);
		canvas.addKeyListener(m);
		canvas.addMouseListener(c);
		canvas.addMouseMotionListener(c);
		canvas.addKeyListener(c);
		// Set the size to the size of the window
		canvas.setSize(getSize());
		// Add the openGL canvas to the window
		add(canvas, BorderLayout.CENTER);
		// Set the animator and pass in the openGL canvas
		_animator = new Animator(canvas);
		// Set how fast this can run
		_animator.setRunAsFastAsPossible(true);
		// Initialize main classes of application
		initializeApplet();
	}

	@Override
	public final void start()
	{
		_animator.start();
	}

	@Override
	public final void stop()
	{
		_animator.stop();
		this.dispose();
	}

	/**
	 * 
	 */
	private void initializeApplet()
	{
		// Initialize the texture library with a set number of maximum textures.
		TextureLibrary.initialize(this.requestMaximumTextures());
		// Initialize the sound library with a set number of maximum sounds.
		SoundLibrary.initialize(this.requestMaximumSounds());
		// Initialize the starting camera location and size
		Camera.initialize(this.requestCameraBounds());
		// Initialize the tile library with a set of maximum tiles.
		TileLibrary.initialize(this.requestMaximumTiles());
		// Sets the timer and how many times the frame rate is updated
		_timer = new Timer(this.requestUpdatesPerSecond());
		// Sets up all keys as being down
		_keyDown = new boolean[256];
	}

	/**
	 * 
	 */
	public final void display(GLAutoDrawable drawable)
	{
		if (_disposed)
			return;
		// Updates the timer
		_timer.update();
		// Sends the time in seconds since last update call and requests
		// all game data updating
		this.update(_timer.getDeltatime());
		// Clears the openGL buffer to ready it for drawing
		drawable.getGL().glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		// Sends the graphics library and requests all game data drawing
		this.draw(GraphicsLibrary.getInstance());
		// Attempt to force garbage collection
	}

	/**
	 * 
	 */
	public final void displayChanged(GLAutoDrawable drawable, boolean arg1, boolean arg2)
	{
	}

	/**
	 * 
	 */
	public final void init(GLAutoDrawable drawable)
	{
		final GraphicsLibrary g = GraphicsLibrary.getInstance();
		final TextureLibrary t = TextureLibrary.getInstance();
		final SoundLibrary s = SoundLibrary.getInstance();
		final ScreenManager m = ScreenManager.getInstance();
		final TileLibrary l = TileLibrary.getInstance();
		final Camera c = Camera.getInstance();
		// initialize the graphics library with the texture library and
		// background color.
		g.initialize(drawable.getGL(), t, this.requestBackgroundColor());
		// Load the game data and textures sending the texture library
		// and the size of the display screen as well as requesting the
		// background color.
		this.load(t, s, m, l, g);
		// Any camera Observers will be set
		c.update();
		// Start by default the graphics library on sprite drawing
		g.setupSprite();
		// Start the timer
		_timer.start();
	}

	/**
	 * 
	 */
	public final void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
	{
		// Setup the view port to the desired size
		final GL gl = drawable.getGL();
		final GLU glu = new GLU();
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glViewport(0, 0, width, height);
		// Setup a 2D environment
		glu.gluOrtho2D(x, x + width, y, y + height);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	/**
	 * 
	 */
	public final void mouseMoved(MouseEvent e)
	{
		_mouseLocation.set(e.getX(), e.getY());
		// Notifies listener that the mouse has been moved
		this.mouseMove(e);
	}

	/**
	 * 
	 */
	public final void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() < 256)
			;
		_keyDown[e.getKeyCode()] = true;
		// Notifies listener that a key has been pressed
		this.keyDown(e);
	}

	/**
	 * 
	 */
	public final void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() < 256)
			;
		_keyDown[e.getKeyCode()] = false;
		// Notifies listener that a key has been released
		this.keyUp(e);
	}

	/**
	 * 
	 */
	public final void mousePressed(MouseEvent e)
	{
		_mouseLocation.set(e.getX(), e.getY());
		// Notifies listener that a mouse key was pressed
		this.mouseDown(e);
	}

	/**
	 * 
	 */
	public final void mouseReleased(MouseEvent e)
	{
		_mouseLocation.set(e.getX(), e.getY());
		// Notifies listener that a mouse key was released
		this.mouseUp(e);
	}

	/**
	 * 
	 */
	public final Timer getTimer()
	{
		return _timer;
	}

	/**
	 * 
	 */
	public final boolean isKeyDown(int key)
	{
		if (key < 256)
			return _keyDown[key];
		return false;
	}

	/**
	 * 
	 */
	public final Vector getMouseLocation()
	{
		return _mouseLocation;
	}

	public abstract void dispose();


	/**
	 * 
	 */
	public abstract void draw(GraphicsLibrary graphics);

	/**
	 * 
	 */
	public abstract void load(TextureLibrary textures, SoundLibrary sounds, ScreenManager screens, TileLibrary tiles, GraphicsLibrary graphics);

	/**
	 * 
	 */
	public abstract void update(float deltatime);

	/**
	 * 
	 */
	public abstract void keyDown(KeyEvent key);

	/**
	 * 
	 */
	public abstract void keyUp(KeyEvent key);

	/**
	 * 
	 */
	public abstract void mouseMove(MouseEvent mouse);

	/**
	 * 
	 */
	public abstract void mouseDown(MouseEvent mouse);

	/**
	 * 
	 */
	public abstract void mouseUp(MouseEvent mouse);

	/**
	 * 
	 */
	public abstract int requestMaximumSounds();

	/**
	 * 
	 */
	public abstract int requestMaximumTextures();

	/**
	 * 
	 */
	public abstract int requestMaximumScreens();

	/**
	 * 
	 */
	public abstract int requestMaximumTiles();

	/**
	 * 
	 */
	public abstract int requestUpdatesPerSecond();

	/**
	 * 
	 */
	public abstract int requestAppletWidth();

	/**
	 * 
	 */
	public abstract int requestAppletHeight();

	/**
	 * 
	 */
	public abstract Rectangle requestCameraBounds();

	/**
	 * 
	 */
	public abstract Color requestBackgroundColor();


	public final void mouseDragged(MouseEvent e)
	{
	}

	public final void keyTyped(KeyEvent e)
	{
	}

	public final void mouseClicked(MouseEvent e)
	{
	}

	public final void mouseEntered(MouseEvent e)
	{
	}

	public final void mouseExited(MouseEvent e)
	{
	}

}
