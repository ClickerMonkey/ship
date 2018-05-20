package wars;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.input.Keyboard;

import axe.TimeUnit;
import axe.input.KeyEvent;
import axe.input.KeyListener;
import axe.input.MouseEvent;
import axe.input.MouseListener;
import axe.util.Screen;
import axe.util.ScreenListener;
import axe.util.Vector;


public class GamePick implements ScreenListener, MouseListener, KeyListener
{
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	private static final float ASPECT = (float)WIDTH / (float)HEIGHT;
	
	public static void main(String[] args) {
		Screen screen = new Screen(WIDTH, HEIGHT, false, 1);

		GamePick game = new GamePick(screen);

		screen.addScreenListener(game);
		screen.addKeyListener(game);
		screen.addMouseListener(game);
		screen.start();
	}

	
	private final Vec3f center = new Vec3f(-0.5f, 1.5f, -6);
	private final Screen screen;
	private final Vector mouse;
	private float angle;
	private boolean mouseover[] = new boolean[4];
	
	private Camera3f camera;
	
	private Quad towards, away;
	private Quad floorQuad;
	private Grid floor;
	
	public GamePick(Screen screen) {
		this.screen = screen;
		this.mouse = new Vector(WIDTH/2, HEIGHT/2);
	}

	@Override
	public void onStart() {
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		glShadeModel(GL_SMOOTH);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

		glLightModeli(GL_LIGHT_MODEL_LOCAL_VIEWER, GL_TRUE);
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);

		glEnable(GL_NORMALIZE);
		
		FloatBuffer ambient = floats(0.2f, 0.2f, 0.2f, 1.0f);
		FloatBuffer diffuse = floats(0.8f, 0.8f, 0.8f, 1.0f);
		FloatBuffer specular = floats(1.0f, 1.0f, 1.0f, 1.0f);
		FloatBuffer position = floats(0.0f, 0.0f, 0.0f, 1.0f);
		glLight(GL_LIGHT0, GL_AMBIENT, ambient);
		glLight(GL_LIGHT0, GL_DIFFUSE, diffuse);
		glLight(GL_LIGHT0, GL_SPECULAR, specular);
		glLight(GL_LIGHT0, GL_POSITION, position);
		
		init(WIDTH, HEIGHT);
		
		top = new Quad(Vec3f.mul(Vec3f.UP, 0.5f), Vec3f.UP).gridify(5);
		bottom = new Quad(Vec3f.mul(Vec3f.DOWN, 0.5f), Vec3f.DOWN).gridify(5);
		right = new Quad(Vec3f.mul(Vec3f.RIGHT, 0.5f), Vec3f.RIGHT).gridify(5);
		left = new Quad(Vec3f.mul(Vec3f.LEFT, 0.5f), Vec3f.LEFT).gridify(5);
		front = new Quad(Vec3f.mul(Vec3f.NEAR, 0.5f), Vec3f.NEAR).gridify(5);
		back = new Quad(Vec3f.mul(Vec3f.FAR, 0.5f), Vec3f.FAR).gridify(5);
		
		towards = new Quad(new Vec3f(), center);
		away = new Quad(new Vec3f(), center);
		
		floorQuad = new Quad(new Vec3f(0, -3, -10), Vec3f.UP);
		floorQuad.setScale(5);
		floor = floorQuad.gridify(12);
		
		camera = new Camera3f();
		camera.focus.set(0, 0, 0);
		camera.distance.set(7);
		camera.yaw.set(Scalarf.PI * 1.5f);
		camera.pitch.set(Scalarf.PI);
		camera.update();
	}

	@Override
	public void onDraw() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		init3d(WIDTH, HEIGHT);
		camera.bind();
		
		glEnable(GL_LIGHTING);
		select(mouse.x, mouse.y);
		render();
		
		glBegin(GL_QUADS); {
			glColor(1f, 1f, 0f, 1f);
			towards.draw();
			glColor(0f, 1f, 1f, 1f);
			away.draw();
			glColor(0.3f, 0.3f, 0.3f, 1f);
			floor.draw();
		} glEnd();

		glDisable(GL_LIGHTING);
		init2d(WIDTH, HEIGHT);
		glPushMatrix(); {
			glTranslatef(mouse.x, mouse.y, 0);
			glScalef(2f, 2f, 1f);
			glColor4f(1f, 1f, 1f, 1f);
			glBegin(GL_QUADS); {
				glVertex2f(-0.5f, 0.5f);
				glVertex2f(0.5f, 0.5f);
				glVertex2f(0.5f, -0.5f);
				glVertex2f(-0.5f, -0.5f);
			} glEnd();
		} glPopMatrix();
	}
	
	public void render() {
		supercube(center.x, center.y, center.z, -angle * 0.5f, 2);
		supercube(0, 0, -7, angle, 1);
		supercube(1, -3, -10, angle * 2f, 3);
	}
	
	public void floor(int w, int h, float y) {
		glColor(0.8f, 0.8f, 1f, 1f);
		glBegin(GL_QUADS);
		for (int z = -h; z <= h; z++) {
			for (int x = -w; x <= w; x++) {
				glNormal3f(0.0f, 1.0f, 0.0f);
				glVertex3f(x-0.45f, y, z+0.45f);
				glVertex3f(x+0.45f, y, z+0.45f);
				glVertex3f(x+0.45f, y, z-0.45f);
				glVertex3f(x-0.45f, y, z-0.45f);
			}
		}
		glEnd();
	}
	
	private void supercube(float x, float y, float z, float angle, int name) {
		glLoadName(name);
		float size = (mouseover[name] ? 1.2f : 1.0f);
		
//		glEnable(GL_CULL_FACE);
//		glCullFace(GL_FRONT);
//		glPushMatrix(); {
//			cube(x, y, z, angle, scale, 1f);
//		} glPopMatrix();
//		glCullFace(GL_BACK);
		glPushMatrix(); {
			cube(x, y, z, angle, size, 1f);
		} glPopMatrix();
//		glDisable(GL_CULL_FACE);
	}
	
	public void select(float x, float y) {
		for (int i = 0; i < mouseover.length; i++) {
			mouseover[i] = false;
		}
		
		int[] viewport = new int[4];
		
		IntBuffer buffer = ByteBuffer.allocateDirect(256).order(ByteOrder.nativeOrder()).asIntBuffer();
		
		IntBuffer view = ByteBuffer.allocateDirect(64).order(ByteOrder.nativeOrder()).asIntBuffer();
		view.order();
		glGetInteger(GL_VIEWPORT, view);
		view.get(viewport);
		view.flip();
		
		if (x < viewport[0] || x > viewport[2]) {
			return;
		}
		if (y < viewport[1] || y > viewport[3]) {
			return;
		}
		
		glSelectBuffer(buffer);
		glRenderMode(GL_SELECT);
		glInitNames();
		glPushName(0);
		glMatrixMode(GL_PROJECTION);
		glPushMatrix(); {
			glLoadIdentity();
			gluPickMatrix(x, y, 1, 1, view);
			gluPerspective(60.0f, (float)(viewport[2]-viewport[0])/(float)(viewport[3]-viewport[1]), 0.0001f, 1000.0f);

			glMatrixMode(GL_MODELVIEW);
			camera.bind();
			render();
			
			glMatrixMode(GL_PROJECTION);
		} glPopMatrix();
		int hits = glRenderMode(GL_RENDER);
		if (hits > 0) {
			int choose = buffer.get(3);
			int depth = buffer.get(1);
			
			for (int i = 1; i < hits; i++) {
				int j = (i << 2);
				if (buffer.get(j + 1) < depth) {
					choose = buffer.get(j + 3);
					depth = buffer.get(j + 1);
				}
			}
			
			if (choose > 0 && choose <= mouseover.length) {
				mouseover[choose] = true;	
			}
		}
		glMatrixMode(GL_MODELVIEW);
	}
	
	public void init(int w, int h) {
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glViewport(0, 0, w, h);
		glClearColor(0, 0, 0, 1);
	}

	private FloatBuffer floats(float ... vals) {
		return (FloatBuffer)ByteBuffer.allocateDirect(vals.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer().put(vals).flip();
	}
	
	FloatBuffer lightColor = floats(1.0f, 1.0f, 1.0f, 1.0f); 
	FloatBuffer currentColor = floats(0.0f, 0.0f, 0.0f, 1.0f);
	private void glColor(float red, float green, float blue, float alpha) {
		currentColor.put(0, red).put(1, green).put(2, blue).put(3, alpha);
		glMaterial(GL_FRONT, GL_AMBIENT, currentColor);
		glMaterial(GL_FRONT, GL_DIFFUSE, currentColor);
		glMaterial(GL_FRONT, GL_SPECULAR, lightColor);
		glMaterialf(GL_FRONT, GL_SHININESS, 60.0f);
	}
	
	public void init3d(int w, int h) {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(60.0f, ASPECT, 0.0001f, 1000.0f);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
	
	public void init2d(int w, int h) {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluOrtho2D(0, w, 0, h);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}

	@Override
	public void onUpdate(TimeUnit elapsed) {
		angle += elapsed.seconds * 40;

		towards.setNormal(center);
		Vec3f other = new Vec3f(center);
		other.neg();
		away.setNormal(other);
		
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			center.y += elapsed.seconds;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			center.y -= elapsed.seconds;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			center.x += elapsed.seconds;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			center.x -= elapsed.seconds;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			center.z += elapsed.seconds;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			center.z -= elapsed.seconds;
		}
	}

	private Drawable top, bottom, front, back, right, left;
	
	private void cube(float x, float y, float z, float angle, float size, float alpha) {
		
		glTranslatef(x, y, z);             // Move Right 1.5 Units And Into The Screen 6.0
		glRotatef(angle,1.0f,1.0f,1.0f);           // Rotate The Quad On The X axis ( NEW )
		glScalef(size, size, size);
		glColor(0.5f,0.5f,1.0f, alpha);                 // Set The Color To Blue One Time Only
		glBegin(GL_QUADS); {                       // Draw A Quad
			
			glColor(0.0f,1.0f,0.0f, alpha);             // Set The Color To Green
			top.draw();
//			glFace(0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f);
//			glNormal3f( 0.0f, 1.0f, 0.0f);
//			glVertex3f( 1.0f, 1.0f,-1.0f);         // Top Right Of The Quad (Top)
//			glVertex3f(-1.0f, 1.0f,-1.0f);         // Top Left Of The Quad (Top)
//			glVertex3f(-1.0f, 1.0f, 1.0f);         // Bottom Left Of The Quad (Top)
//			glVertex3f( 1.0f, 1.0f, 1.0f);         // Bottom Right Of The Quad (Top)
			
			glColor(1.0f,0.5f,0.0f, alpha);             // Set The Color To Orange
			bottom.draw();
//			glFace(0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f);
//			glNormal3f( 0.0f,-1.0f, 0.0f);
//			glVertex3f( 1.0f,-1.0f, 1.0f);         // Top Right Of The Quad (Bottom)
//			glVertex3f(-1.0f,-1.0f, 1.0f);         // Top Left Of The Quad (Bottom)
//			glVertex3f(-1.0f,-1.0f,-1.0f);         // Bottom Left Of The Quad (Bottom)
//			glVertex3f( 1.0f,-1.0f,-1.0f);         // Bottom Right Of The Quad (Bottom)
			
			glColor(1.0f,0.0f,0.0f, alpha);             // Set The Color To Red
			front.draw();
//			glFace(0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f);
//			glNormal3f( 0.0f, 0.0f, 1.0f);
//			glVertex3f( 1.0f, 1.0f, 1.0f);         // Top Right Of The Quad (Front)
//			glVertex3f(-1.0f, 1.0f, 1.0f);         // Top Left Of The Quad (Front)
//			glVertex3f(-1.0f,-1.0f, 1.0f);         // Bottom Left Of The Quad (Front)
//			glVertex3f( 1.0f,-1.0f, 1.0f);         // Bottom Right Of The Quad (Front)
			
			glColor(1.0f,1.0f,0.0f, alpha);             // Set The Color To Yellow
			back.draw();
//			glFace(0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f);
//			glNormal3f( 0.0f, 0.0f,-1.0f);
//			glVertex3f( 1.0f,-1.0f,-1.0f);         // Bottom Left Of The Quad (Back)
//			glVertex3f(-1.0f,-1.0f,-1.0f);         // Bottom Right Of The Quad (Back)
//			glVertex3f(-1.0f, 1.0f,-1.0f);         // Top Right Of The Quad (Back)
//			glVertex3f( 1.0f, 1.0f,-1.0f);         // Top Left Of The Quad (Back)
			
			glColor(0.0f,0.0f,1.0f, alpha);             // Set The Color To Blue
			left.draw();
//			glFace(-1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f);
//			glNormal3f(-1.0f, 0.0f, 0.0f);
//			glVertex3f(-1.0f, 1.0f, 1.0f);         // Top Right Of The Quad (Left)
//			glVertex3f(-1.0f, 1.0f,-1.0f);         // Top Left Of The Quad (Left)
//			glVertex3f(-1.0f,-1.0f,-1.0f);         // Bottom Left Of The Quad (Left)
//			glVertex3f(-1.0f,-1.0f, 1.0f);         // Bottom Right Of The Quad (Left)
			
			glColor(1.0f,0.0f,1.0f, alpha);             // Set The Color To Violet
			right.draw();
//			glFace(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);
//			glNormal3f( 1.0f, 0.0f, 0.0f);
//			glVertex3f( 1.0f, 1.0f,-1.0f);         // Top Right Of The Quad (Right)
//			glVertex3f( 1.0f, 1.0f, 1.0f);         // Top Left Of The Quad (Right)
//			glVertex3f( 1.0f,-1.0f, 1.0f);         // Bottom Left Of The Quad (Right)
//			glVertex3f( 1.0f,-1.0f,-1.0f);         // Bottom Right Of The Quad (Right)
		} glEnd();                                 // Done Drawing The Quad
	}

	
//	private void glFace(float nx, float ny, float nz, float ox, float oy, float oz) {
//		float[] n = {nx, ny, nz};
//		float[] r = {-nz + ny, 0, nx};
//		float[] u = new float[3];
//		norm(r);
//		cross(n, r, u);
//		cross(n, u, r);
//		
//		glNormal3f(nx, ny, nz);
////		float rx = -nz + ny, rz = nx;
////		float ux = ny*rz, uy = nz*rx - rz*nx, uz = -rx*ny;
//		glVertex3f(ox + u[0] + r[0], oy + u[1] + r[1], oz + u[2] + r[2]); //0
//		glVertex3f(ox + u[0] - r[0], oy + u[1] - r[1], oz + u[2] - r[2]); //1
//		glVertex3f(ox +-u[0] - r[0], oy +-u[1] - r[1], oz +-u[2] - r[2]); //2
//		glVertex3f(ox +-u[0] + r[0], oy +-u[1] + r[1], oz +-u[2] + r[2]); //3
//		//y1*z2 - y2*z1 , z1*x2 - z2*x1 , x1*y2 - x2*y1
//	}

	public interface Drawable {
		public void draw();
	}
	
	public interface Updatable {
		public void update(TimeUnit elapsed);
	}
	
	

	public static class Camera3f {
		private final Scalarf yaw = new Scalarf();
		private final Scalarf pitch = new Scalarf();
		private final Scalarf distance = new Scalarf();
		private final Vec3f direction = new Vec3f();  //<auto>
		private final Vec3f right = new Vec3f(); //<auto>
		private final Vec3f up = new Vec3f(); //<auto>
		private final Vec3f focus = new Vec3f();
		private final Vec3f position = new Vec3f(); //<auto>
		
		public Camera3f() {
		}
		public void update() {
			yaw.mod(Scalarf.PI2);
			pitch.mod(Scalarf.PI2);
			
			direction.x = (float)(Math.cos(yaw.v) * Math.cos(pitch.v));
			direction.y = (float)(Math.sin(pitch.v));
			direction.z = (float)(Math.sin(yaw.v) * Math.cos(pitch.v));
			direction.norm();
	
			right.set(-direction.z + direction.y, 0, direction.x);
			direction.cross(right, up);
			direction.cross(up, right);
			
			position.set(focus);
			position.add(direction, distance.v);
		}
		public void bind() {
			gluLookAt(position.x, position.y, position.z, position.x + direction.x, 
					position.y + direction.y, position.z + direction.z, up.x, up.y, up.z);
		}
	}
	
	
	public static class Scalarf {
		public static float PI = (float)Math.PI;
		public static float PI2 = (float)(Math.PI * 2.0);
		
		public float v;
		public Scalarf() {
		}
		public Scalarf(float v) {
			this.v = v;
		}
		public void set(float v) {
			this.v = v;
		}
		public void add(float s) {
			v += s;
		}
		public void sub(float s) {
			v -= s;
		}
		public void mul(float s) {
			v *= s;
		}
		public void div(float s) {
			if (s != 0.0) {
				v /= s;
			}
		}
		public void max(float s) {
			v = (v > s ? s : v);
		}
		public void min(float s) {
			v = (v < s ? s : v);
		}
		public void clamp(float max, float min) {
			v = (v < min ? min : (v > max ? max : v));
		}
		public void delta(float start, float end, float delta) {
			v = (end - start) * delta + start;
		}
		public void floor() {
			v = (float)StrictMath.floor(v);
		}
		public void ceil() {
			v = (float)StrictMath.ceil(v);
		}
		public void neg() {
			v = -v;
		}
		public void abs() {
			v = (v < 0 ? -v : v);
		}
		public void mod(float s) {
			v -= s * StrictMath.floor(v / s);
		}
	}
	
	public static class Vec3f {
		public static final Vec3f RIGHT = new Vec3f(1, 0, 0);
		public static final Vec3f LEFT = new Vec3f(-1, 0, 0);
		public static final Vec3f UP = new Vec3f(0, 1, 0);
		public static final Vec3f DOWN = new Vec3f(0, -1, 0);
		public static final Vec3f NEAR = new Vec3f(0, 0, 1);
		public static final Vec3f FAR = new Vec3f(0, 0, -1);
		
		public float x, y, z;
		public Vec3f() {
		}
		public Vec3f(Vec3f v) {
			set(v);
		}
		public Vec3f(float x, float y, float z) {
			set(x, y, z);
		}
		public void set(Vec3f v) {
			x = v.x;
			y = v.y;
			z = v.z;
		}
		public void set(float x, float y, float z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		public void cross(Vec3f with, Vec3f out) {
			out.x = y * with.z - with.y * z;
			out.y = z * with.x - with.z * x;
			out.z = x * with.y - with.x * y;
		}
		public void norm() {
			float sq = (x*x)+ (y*y) + (z*z);
			if (sq != 0.0f && sq != 1.0f) {
				div((float)Math.sqrt(sq));
			}
		}
		public void norm(Vec3f v) {
			set(v); norm();
		}
		public void norm(float x, float y, float z) {
			set(x, y, z); norm();
		}
		public void div(float s) {
			if (s != 0.0) {
				s = 1f / s;
				x *= s;
				y *= s;
				z *= s;
			}
		}
		public void mul(float s) {
			x *= s;
			y *= s;
			z *= s;
		}
		public void add(Vec3f v) {
			x += v.x; y += v.y; z += v.z;
		}
		public void add(float vx, float vy, float vz) {
			x += vx; y += vy; z += vz;
		}
		public void add(Vec3f v, float scale) {
			x += v.x * scale; y += v.y * scale; z += v.z * scale;
		}
		public void sub(Vec3f v) {
			x -= v.x; y -= v.y; z -= v.z;
		}
		public void neg() {
			x = -z; y = -y; z = -z;
		}
		public void length(float length) {
			float sq = (x*x) + (y*y) + (z*z);
			if (sq != 0.0 && sq != length * length) {
				mul(length / (float)Math.sqrt(sq));
			}
		}

		public static Vec3f mul(Vec3f v, float s) {
			return new Vec3f(v.x * s, v.y * s, v.z * s);
		}
	}
	
	public class Quad implements Drawable {
		private final Vec3f center = new Vec3f();
		private final Vec3f up = new Vec3f();
		private final Vec3f right = new Vec3f();
		private final Vec3f normal = new Vec3f();
		private float scale = 0.5f;
		public Quad(Vec3f center, Vec3f normal) {
			setCenter(center);
			setNormal(normal);
		}
		public void setCenter(Vec3f c) {
			center.set(c);
		}
		public void setNormal(Vec3f n) {
			normal.norm(n);
			right.norm(-n.z + n.y, 0, n.x);
			normal.cross(right, up);
			normal.cross(up, right);
			right.length(scale);
			up.length(scale);
		}
		public void setScale(float newScale) {
			up.mul(newScale / scale);
			right.mul(newScale / scale);
			scale = newScale;
		}
		public void draw() {
			final Vec3f n = normal, u = up, r = right, c = center;
			glNormal3f(n.x, n.y, n.z);
			glVertex3f(c.x + u.x + r.x, c.y + u.y + r.y, c.z + u.z + r.z); //0
			glVertex3f(c.x + u.x - r.x, c.y + u.y - r.y, c.z + u.z - r.z); //1
			glVertex3f(c.x +-u.x - r.x, c.y +-u.y - r.y, c.z +-u.z - r.z); //2
			glVertex3f(c.x +-u.x + r.x, c.y +-u.y + r.y, c.z +-u.z + r.z); //3
		}
		public Grid gridify(int radius) {
			return new Grid(this, radius);
		}
	}
	
	public class Grid implements Drawable {
		private final Quad[] quads;
		public Grid(Quad root, int radius) {
			int wide = (radius << 1) - 1;
			float scale = (root.scale * 2) / wide;
			int index = 0;
			this.quads = new Quad[wide * wide];
			for (int y = -radius + 1; y < radius; y++) {
				for (int x = -radius + 1; x < radius; x++) {
					quads[index++] = create(root, x, y, scale);
				}
			}
		}
		public Quad create(Quad root, int ix, int iy, float scale) {
			Vec3f v = new Vec3f();
			Quad q = new Quad(root.center, root.normal);
			v.norm(root.right);
			q.center.add(v, ix * scale);
			v.norm(root.up);
			q.center.add(v, iy * scale);
			q.setScale(scale * 0.5f);
			return q;
		}
		public void draw() {
			for (Quad q : quads) {
				q.draw();	
			}
		}
	}
	
	@Override
	public void onClose() {
		
	}	

	@Override
	public void onKeyUp(KeyEvent e) {
		if (e.key == Keyboard.KEY_ESCAPE) {
			screen.close();
		}
	}

	@Override
	public void onKeyDown(KeyEvent e) {
	}

	@Override
	public void onMouseDown(MouseEvent e) {
	}

	@Override
	public void onMouseUp(MouseEvent e) {
	}

	@Override
	public void onMouseDrag(MouseEvent e) {
		mouse.add(e.dx, e.dy);
	}

	@Override
	public void onMouseEnter(MouseEvent e) {
	}

	@Override
	public void onMouseExit(MouseEvent e) {
	}

	@Override
	public void onMouseHover(MouseEvent e) {
	}

	@Override
	public void onMouseMove(MouseEvent e) {
		mouse.add(e.dx, e.dy);
	}

	@Override
	public void onMouseWheel(MouseEvent e) {
	}


}
