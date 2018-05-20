package pfxEditor;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;

import com.sun.opengl.util.Animator;

public abstract class Scene extends Frame implements GLEventListener,
		KeyListener, MouseMotionListener, MouseListener {

	private TextureLibrary _textures;
	private Timer _timer;
	private Animator _animator = null;
	private boolean[] _keyDown = null;
	private Vector _mouseLocation = Vector.zero();

	public Scene() {
		// Set the size according to the size desired by the inherited applet
		setSize(this.requestSceneWidth(), this.requestSceneHeight());
		setLayout(new BorderLayout());
		// The window to display openGL graphics
		final GLCanvas canvas = new GLCanvas();
		// Add all the listeners
		canvas.addKeyListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addMouseListener(this);
		canvas.addGLEventListener(this);
		// Set the size to the size of the applet
		canvas.setSize(getSize());
		// Add the openGL canvas to the applet
		add(canvas, BorderLayout.CENTER);
		// Set the animator and pass in the openGL canvas
		_animator = new Animator(canvas);
		// Set how fast this can run
		_animator.setRunAsFastAsPossible(false);
		// Initialize the texture library with a set number of textures
		// that can be uploaded
		TextureLibrary.initialize(this.requestMaximumTextures());
		// Sets the instance of the texture library
		_textures = TextureLibrary.getInstance();
		// Sets the timer and how many times the frame rate is updated
		_timer = new Timer(this.requestUpdatesPerSecond());
		//Start running the animator
		_animator.start();
	}

	public void display(GLAutoDrawable drawable) {
		// Updates the timer
		_timer.update();
		// Sends the time in seconds since last update call and requests
		// all game data updating
		this.update(_timer.getDeltatime());
		// Clears the openGL buffer to ready it for drawing
		final GL gl = drawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		// Sends the graphics library and requests all game data drawing
		this.draw(gl);
	}

	/**
	 * 
	 */
	public final void init(GLAutoDrawable drawable) {
		// initialize the graphics library with the texture library and
		// background color.
		final GL gl = drawable.getGL();
		final Color background = this.requestBackgroundColor();
		//*******************************************************************
		gl.glShadeModel(GL.GL_SMOOTH);
		gl.glClearColor(background.getR(), background.getG(),
				background.getB(), 0f);
		gl.glClearDepth(1.0);
		//*******************************************************************
		// Enable Z-buffer
		gl.glEnable(GL.GL_DEPTH_TEST);
		// Enable Blending
		gl.glEnable(GL.GL_BLEND);
		//*******************************************************************
		gl.glEnable(GL.GL_ALPHA); //*****************************************
		//*******************************************************************
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		//*******************************************************************
		// Get nicest perspective and point smoothing.
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		gl.glHint(GL.GL_POINT_SMOOTH_HINT, GL.GL_NICEST);
		// Enable 2D textures.
		gl.glEnable(GL.GL_TEXTURE_2D);
		//*******************************************************************
		// Load the game data and textures sending the texture library
		// and the size of the display screen as well as requesting the
		// background color.
		this.load(_textures, drawable.getWidth(), drawable.getHeight());
		// Start the timer
		_timer.start();
	}

	/**
	 * 
	 */
	public final void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		// Setup the view port to the desired size
		final GL gl = drawable.getGL();
		final float k = (float)height / (float)width;
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glViewport(0, 0, drawable.getWidth(), drawable.getHeight());
		gl.glLoadIdentity();
		gl.glFrustum(-.5f, .5f, -.5f * k, .5f * k, 1.0f, 500.0f);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		
//		gl.glMatrixMode(GL.GL_PROJECTION);
//		gl.glLoadIdentity();
//		gl.glViewport(0, 0, width, height);
//		// Setup a 2D environment
//		glu.gluOrtho2D(x, x + width, y, y + height);
//		gl.glMatrixMode(GL.GL_MODELVIEW);
//		gl.glLoadIdentity();
	}
	
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {

	}

	/**
	 * 
	 */
	public final void mouseMoved(MouseEvent e) {
		_mouseLocation.set(e.getX(), e.getY(), 0f);
		// Notifies listener that the mouse has been moved
		this.mouseMove(e);
	}

	/**
	 * 
	 */
	public final void keyPressed(KeyEvent e) {
		if (e.getKeyCode() < 256)
			;
		_keyDown[e.getKeyCode()] = true;
		// Notifies listener that a key has been pressed
		this.keyDown(e);
	}

	/**
	 * 
	 */
	public final void keyReleased(KeyEvent e) {
		if (e.getKeyCode() < 256)
			;
		_keyDown[e.getKeyCode()] = false;
		// Notifies listener that a key has been released
		this.keyUp(e);
	}

	/**
	 * 
	 */
	public final void mousePressed(MouseEvent e) {
		_mouseLocation.set(e.getX(), e.getY(), 0f);
		// Notifies listener that a mouse key was pressed
		this.mouseDown(e);
	}

	/**
	 * 
	 */
	public final void mouseReleased(MouseEvent e) {
		_mouseLocation.set(e.getX(), e.getY(), 0f);
		// Notifies listener that a mouse key was released
		this.mouseUp(e);
	}

	public void keyTyped(KeyEvent e) {

	}

	public void mouseDragged(MouseEvent e) {

	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	/**
	 * 
	 */
	public abstract void draw(GL gl);

	/**
	 * 
	 */
	public abstract void load(TextureLibrary textures, int width, int height);

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
	public abstract int requestMaximumTextures();

	/**
	 * 
	 */
	public abstract int requestUpdatesPerSecond();

	/**
	 * 
	 */
	public abstract int requestSceneWidth();

	/**
	 * 
	 */
	public abstract int requestSceneHeight();

	/**
	 * 
	 */
	public abstract Color requestBackgroundColor();

}
