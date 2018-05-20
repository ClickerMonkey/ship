package wars;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import axe.TimeUnit;
import axe.input.KeyEvent;
import axe.input.KeyListener;
import axe.input.MouseEvent;
import axe.input.MouseListener;
import axe.texture.SourceTile;
import axe.texture.Texture;
import axe.texture.TextureLoader;
import axe.texture.TextureWrap;
import axe.util.Screen;
import axe.util.ScreenListener;
import axe.util.Vector;


public class Battlecraft implements ScreenListener, MouseListener, KeyListener
{
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	private static final float ASPECT = (float)WIDTH / (float)HEIGHT;
	
	public static void main(String[] args) {
		Screen screen = new Screen(WIDTH, HEIGHT, false, 1);

		Battlecraft game = new Battlecraft(screen);

		screen.addScreenListener(game);
		screen.addKeyListener(game);
		screen.addMouseListener(game);
		screen.start();
	}

	private final Screen screen;
	private final Vector mouse;
	private final Node<Bullet> bullets;
	private Camera3f camera;
	private Skybox skybox;
	private Tween<Scalarf> tween;
	private Tween<Vec3f> toOrigin;
	private Tween<Color4f> topChanger;
	private Sector sector;
	
	private Texture blockTexture;
	private Tilesheet sheet;
	private Block block;
	
	public Battlecraft(Screen screen) {
		this.screen = screen;
		this.mouse = new Vector(WIDTH/2, HEIGHT/2);
		this.bullets = new Node<Bullet>(null);
	}
	
	private class Node<T> {
		public final T t;
		public Node<T> next, prev;
		public Node(T t) {
			this.t = t;
		}
		public void remove() {
			if (next != null) {
				next.prev = prev;
				next = null;
			}
			if (prev != null) {
				prev.next = next;
				prev = null;
			}
		}
		public void insert(Node<T> after) {
			final Node<T> before = after.next;
			if (before != null) {
				before.prev = this;
				next = after.next;
			}
			after.next = this;
			prev = after;
		}
	}

	private class Bullet implements Updatable, Drawable {
		private final Vec3f pos, vel;
		private float life = 0f;
		public Bullet(Vec3f pos, Vec3f vel, float speed) {
			this.pos = new Vec3f(pos);
			this.vel = new Vec3f(vel);
			this.vel.mul(speed);
		}
		public void update(TimeUnit elapsed) {
			life += elapsed.seconds;
			vel.y -= 20 * elapsed.seconds;
			pos.add(vel, elapsed.seconds);
		}
		public void draw() {
			Cube c = new Cube();
			c.size(0.5f, 0.5f, 0.5f);
			c.center(pos.x, pos.y, pos.z);
			c.draw();
		}
	}
	
	@Override
	public void onStart() {
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glAlphaFunc(GL_GREATER, 0.1f);
		glEnable(GL_ALPHA_TEST);
		
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		glShadeModel(GL_SMOOTH);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		
//		glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
		
		init(WIDTH, HEIGHT);
		
		camera = new Camera3f();
		camera.focus.set(0, 0, 0);
		camera.distance.set(7);
		camera.yaw.set(0f);
		camera.pitch.set(Scalarf.PI2 * 0.875f);
		camera.update();
		
		tween = new Tween<Scalarf>(camera.distance, new Scalarf(0), new Scalarf(7), 4);
		tween.active = false;
		
		toOrigin = new Tween<Vec3f>(camera.focus, new Vec3f(), new Vec3f(), 3);
		toOrigin.active = false;
		
		topChanger = new Tween<Color4f>(new Color4f(), new Color4f(1, 0, 0), new Color4f(0, 1, 1), 2f);
		topChanger.loops = Integer.MAX_VALUE;
		topChanger.bounce = true;
		
		try {
			skybox = new Skybox();
			skybox.size(100, 100, 100);
			skybox.center(0, 0, 0);
			
			String base = "wars/assets/sky/desert/";
			TextureLoader loader = new TextureLoader();
			skybox.back = loader.getTexture(base+"back.jpg", TextureWrap.ToEdge);
			skybox.front = loader.getTexture(base+"front.jpg", TextureWrap.ToEdge);
			skybox.left = loader.getTexture(base+"left.jpg", TextureWrap.ToEdge);
			skybox.right = loader.getTexture(base+"right.jpg", TextureWrap.ToEdge);
			skybox.top = loader.getTexture(base+"top.jpg", TextureWrap.ToEdge);
			
			blockTexture = loader.getTexture("wars/assets/terrain.png", TextureWrap.ToBorder);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		sector = new Sector(10, 10, 10, 4);
		sector.center(0, -5, 0);
		sector.colors[0] = new Color4f(1f, 1f, 1f, 0f);
		sector.colors[1] = new Color4f(0f, 0.5f, 0f);			// green
		sector.colors[2] = new Color4f(0.5f, 0.25f, 0f);		// brown
		sector.colors[3] = new Color4f(0f, 0.25f, 0.6f, 0.7f); 	// blue
		sector.colors[4] = new Color4f(0.5f, 0.5f, 0.5f); 		// gray
		layer(sector, 0, 4);
		layer(sector, 1, 4);
		layer(sector, 2, 4);
		layer(sector, 3, 2);
		layer(sector, 4, 2);
		layer(sector, 5, 2);
		layer(sector, 6, 2);
		layer(sector, 7, 1);
		layer(sector, 8, 3);
		layer(sector, 9, 3);
		xline(sector, 3, 1, 0, 8);
		
		// GRASS BLOCK
		sheet = new Tilesheet(blockTexture, 0, 0, 64, 64, 16, 256);
		
		BlockType typeGrass = new BlockType();
		typeGrass.set(Block.TOP, 0, new Color4f(0.5f, 1.0f, 0.3f), sheet);
		typeGrass.set(Block.BOTTOM, 2, Color4f.White, sheet);
		typeGrass.set(Block.LEFT, 3, Color4f.White, sheet);
		typeGrass.set(Block.RIGHT, 3, Color4f.White, sheet);
		typeGrass.set(Block.FRONT, 3, Color4f.White, sheet);
		typeGrass.set(Block.BACK, 3, Color4f.White, sheet);
		
		BlockType typeIce = new BlockType();
		typeIce.set(Block.TOP, 24, new Color4f(1f, 1f, 1f, 0.6f), sheet);
		typeIce.set(Block.BOTTOM, 24, new Color4f(1f, 1f, 1f, 0.6f), sheet);
		typeIce.set(Block.LEFT, 24, new Color4f(1f, 1f, 1f, 0.6f), sheet);
		typeIce.set(Block.RIGHT, 24, new Color4f(1f, 1f, 1f, 0.6f), sheet);
		typeIce.set(Block.FRONT, 24, new Color4f(1f, 1f, 1f, 0.6f), sheet);
		typeIce.set(Block.BACK, 24, new Color4f(1f, 1f, 1f, 0.6f), sheet);
		
		
		block = new Block(typeGrass);
	}

	private void layer(Sector sector, int y, int color) {
		byte b = (byte)color;
		for (int z = 0; z < sector.d; z++) {
			for (int x = 0; x < sector.w; x++) {
				sector.blocks[z][y][x] = b;
			}
		}
	}
	private void xline(Sector sector, int x, int y, int z0, int z1) {
		int d = Integer.signum(z1 - z0);
		for (int z = z0; z <= z1; z+=d) {
			sector.blocks[z][y][x] = (byte)0;
		}
	}
	
	@Override
	public void onDraw() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		init3d(WIDTH, HEIGHT);
		camera.update();
		camera.bind();

		glEnable(GL_TEXTURE_2D);
		skybox.draw();
		
		blockTexture.bind();
		glBegin(GL_QUADS); {
			block.draw(camera.focus.x-0.5f, camera.focus.y-0.5f, camera.focus.z-0.5f, Block.ALL_VISIBLE);
		} glEnd();
		
		final SourceTile sf = sheet.get(28);
		final Vec3f rt = camera.right;
		glColor4f(1f, 1f, 1f, 1f);
		glBegin(GL_QUADS); {
			glTexCoord2f(sf.s0, sf.t0);
			glVertex3f(-rt.x*0.5f, 1, -rt.z*0.5f);
			glTexCoord2f(sf.s0, sf.t1);
			glVertex3f(-rt.x*0.5f, 0, -rt.z*0.5f);
			glTexCoord2f(sf.s1, sf.t1);
			glVertex3f(rt.x*0.5f, 0, rt.z*0.5f);
			glTexCoord2f(sf.s1, sf.t0);
			glVertex3f(rt.x*0.5f, 1, rt.z*0.5f);
		} glEnd();
		
		glDisable(GL_TEXTURE_2D);
		
//		glPushMatrix(); {
//			cube(camera.focus.x, camera.focus.y, camera.focus.z, 0f, 1f, 1f);
//		} glPopMatrix();
		
		glColor4f(1f, 0f, 0f, 1f);
		Node<Bullet> current = bullets.next;
		while (current != null) {
			current.t.draw();
			current = current.next;
		}
		glColor4f(1f, 1f, 1f, 1f);
		
		sector.draw();
		
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
	
	@Override
	public void onUpdate(TimeUnit elapsed) {

		camera.yaw.add(Mouse.getDX() * elapsed.seconds);
		camera.pitch.add(Mouse.getDY() * elapsed.seconds);
		
		tween.update(elapsed);
		toOrigin.update(elapsed);
		topChanger.update(elapsed);
		
		Node<Bullet> next, current = bullets.next;
		while (current != null) {
			next = current.next;
			current.t.update(elapsed);
			if (current.t.life >= 5f) {
				current.remove();
			}
			current = next;
		}
		
		if (Mouse.isButtonDown(1)) {
			Bullet t = new Bullet(camera.focus, camera.direction, 50);
			Node<Bullet> node = new Node<Bullet>(t);
			node.insert(bullets);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
			if (!tween.active) {
				tween.swap();
				tween.reset();
			}
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
			if (!toOrigin.active) {
				toOrigin.start.set(camera.focus);
				toOrigin.reset();
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			camera.roll.sub(elapsed.seconds);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			camera.roll.add(elapsed.seconds);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			camera.roll.set(0);
		}
		
		float speed = 4 * elapsed.seconds;
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			camera.focus.add(camera.direction, speed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			camera.focus.add(camera.direction, -speed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			camera.focus.add(camera.right, -speed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			camera.focus.add(camera.right, speed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			camera.focus.add(camera.up, -speed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			camera.focus.add(camera.up, speed);
		}
		
		camera.distance.add(Mouse.getDWheel() * -elapsed.seconds);
	}
	
	public void floor(int w, int h, float y) {
		glColor4f(0.8f, 0.8f, 1f, 1f);
		glBegin(GL_QUADS);
		for (int z = -h; z <= h; z++) {
			for (int x = -w; x <= w; x++) {
				glVertex3f(x-0.45f, y, z+0.45f);
				glVertex3f(x+0.45f, y, z+0.45f);
				glVertex3f(x+0.45f, y, z-0.45f);
				glVertex3f(x-0.45f, y, z-0.45f);
			}
		}
		glEnd();
	}
	
	public void init(int w, int h) {
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glViewport(0, 0, w, h);
		glClearColor(0, 0, 0, 1);
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

	public static class Tilesheet {
		private Texture texture;
		private SourceTile[] frames;
		public Tilesheet(Texture texture, int offsetx, int offsety, int frameWidth, int frameHeight, int columns, int frameCount) {
			this.texture = texture;
			this.frames = new SourceTile[frameCount];
			for (int i = 0; i < frameCount; i++) {
				int x = ((i % columns) * frameWidth) + offsetx;
				int y = ((i / columns) * frameHeight) + offsety;
				frames[i] = texture.getTile(x, y, frameWidth, frameHeight);
			}
		}
		public SourceTile get(int index) {
			return frames[index];
		}
		public void bind() {
			texture.bind();
		}
	}

	private static Vec3f[][] FACE_POINTS = {
		{new Vec3f(0,1,0), new Vec3f(0,1,1), new Vec3f(1,1,1), new Vec3f(1,1,0)}, // TOP
		{new Vec3f(1,0,1), new Vec3f(1,0,0), new Vec3f(0,0,0), new Vec3f(0,0,1)}, // BOTTOM
		{new Vec3f(0,1,0), new Vec3f(0,0,0), new Vec3f(0,0,1), new Vec3f(0,1,1)}, // LEFT
		{new Vec3f(1,1,1), new Vec3f(1,0,1), new Vec3f(1,0,0), new Vec3f(1,1,0)}, // RIGHT
		{new Vec3f(0,1,1), new Vec3f(0,0,1), new Vec3f(1,0,1), new Vec3f(1,1,1)}, // FRONT
		{new Vec3f(1,1,0), new Vec3f(1,0,0), new Vec3f(0,0,0), new Vec3f(0,1,0)}, // BACK
	};
	
	public static class BlockFace {
		public SourceTile frame;
		public Color4f shade;
		public BlockFace(SourceTile frame, Color4f shade) {
			this.frame = frame;
			this.shade = shade;
		}
	}
	
	public static class BlockType {
		public BlockFace[] faces = new BlockFace[6];
		public void set(int face, int tile, Color4f shade, Tilesheet sheet) {
			faces[face] = new BlockFace(sheet.get(tile), shade);
		}
		public void draw(int face, byte brightness, float x, float y, float z) {
			final Vec3f[] p = FACE_POINTS[face];
			final BlockFace f = faces[face];
			final SourceTile s = f.frame;
//			f.shade.bind((brightness & 0xFF) * 0.00392156863f);
			f.shade.bind();
			glTexCoord2f(s.s0, s.t0);
			glVertex3f(x + p[0].x, y + p[0].y, z + p[0].z);
			glTexCoord2f(s.s0, s.t1);
			glVertex3f(x + p[1].x, y + p[1].y, z + p[1].z);
			glTexCoord2f(s.s1, s.t1);
			glVertex3f(x + p[2].x, y + p[2].y, z + p[2].z);
			glTexCoord2f(s.s1, s.t0);
			glVertex3f(x + p[3].x, y + p[3].y, z + p[3].z);
		}
	}
	
	public static class Block {
		public static final int TOP = 0;
		public static final int BOTTOM = 1;
		public static final int LEFT = 2;
		public static final int RIGHT = 3;
		public static final int FRONT = 4;
		public static final int BACK = 5;
		
		public static final boolean[] ALL_VISIBLE = {true, true, true, true, true, true};
		
		public BlockType type;
		public byte brightness[] = {(byte)0xFF, (byte)0xFF, (byte)0xFF, 
				(byte)0xFF, (byte)0xFF, (byte)0xFF};
		
		public Block(BlockType type) {
			this.type = type;
		}
		public void draw(float x, float y, float z, boolean[] visible) {
			for (int i = 0; i < 6; i++) {
				if (visible[i]) {
					type.draw(i, brightness[i], x, y, z);	
				}
			}
		}
	}
	
	public static class Sector implements Drawable {
		private final Cube cube = new Cube();
		private int w, h, d;
		private float x, y, z;
		private Color4f[] colors;
		private byte[][][] blocks;
		public Sector(int width, int height, int depth, int colorCount) {
			w = width;
			h = height;
			d = depth;
			cube.size(1f, 1f, 1f);
			colors = new Color4f[colorCount + 1];
			blocks = new byte[d][h][w];
		}
		public void center(float cx, float cy, float cz) {
			x = cx - (w * 0.5f);
			y = cy - (h * 0.5f);
			z = cz - (d * 0.5f);
		}
		public void draw() {
			glBegin(GL_QUADS);
			cube.z = z;
			for (int z = 0; z < d; z++) {
				cube.y = y;
				for (int y = 0; y < h; y++) {
					cube.x = x;
					for (int x = 0; x < w; x++) {
						int index = block(x, y, z);
						if (index > 0) {
							Color4f color = colors[index];
							color.bind();
							if (alpha(x, y + 1, z) < color.a) {
								cube.top();
							}
							if (alpha(x, y - 1, z) < color.a) {
								cube.bottom();
							}
							if (alpha(x + 1, y, z) < color.a) {
								cube.right();
							}
							if (alpha(x - 1, y, z) < color.a) {
								cube.left();
							}
							if (alpha(x, y, z - 1) < color.a) {
								cube.back();
							}
							if (alpha(x, y, z + 1) < color.a) {
								cube.front();
							}
						}
						cube.x += 1f;
					}
					cube.y += 1f;
				}
				cube.z += 1f;
			}
			glEnd();
		}
		public float alpha(int x, int y, int z) {
			if (x < 0 || y < 0 || z < 0 || x >= w || y >= h || z >= d) {
				return 0f;
			}
			return colors[block(x, y, z)].a;
		}
		public int block(int x, int y, int z) {
			return blocks[z][y][x] & 0xFF;
		}
	}
	
	
	public interface Drawable {
		public void draw();
	}
	
	public interface Updatable {
		public void update(TimeUnit elapsed);
	}
	
	public interface Deltaf<T> {
		public void delta(T start, T end, float delta);
		public T get();
		public void set(T value);
	}
	
	public interface Motionf<T> {
		public void add(T value, float scale);
		public void max(T max);
		public T get();
		public void set(T value);
	}
	
	//                              loops (3)
	//              /=================|=================\
	//              |                 |                 |   
	// +-------+----------+------+----------+------+----------+
	// | delay | duration | rest | duration | rest | duration |
	// +-------+----------+------+----------+------+----------+
	// 00000000000000000001111111111111111112222222222222222223    <- iteration
	//
	// total = delay + (duration + rest) * loops - rest;
	// remaining = total - time;
	// active = iteration < loops
	// 
	public static class Tween<T> implements Updatable {
		private Deltaf<T> start, end, subject;
		private float duration, time/**/, delay, rest;
		private int iteration/**/, loops;
		private boolean active/**/, bounce;
		public Tween(Deltaf<T> subject, Deltaf<T> start, Deltaf<T> end, float duration) {
			this.subject = subject;
			this.start = start;
			this.end = end;
			this.duration = duration;
			this.time = 0f;
			this.active = true;
		}
		public void set(Deltaf<T> start, Deltaf<T> end, float duration) {
			this.start = start;
			this.end = end;
			this.duration = duration;
			this.time = 0f;
			this.active = true;
		}
		public void reset() {
			active = true;
			time = 0f;
			iteration = 0;
		}
		public void swap() {
			Deltaf<T> temp = end;
			end = start;
			start = temp;
		}
		public void reverse() {
			if (iteration > 0) {
				time = duration - time;	
			}
		}
		public void update(TimeUnit elapsed) {
			if (active) {
				if (iteration == 0) {
					if (time > delay) {
						iteration++;
						time -= delay;
					}
				}
				if (iteration > 0) {
					float remain = time - duration;
					if (remain >= 0) {
						subject.set(end.get());
						if (remain >= rest) {
							time -= duration + rest;
							iteration++;
							if (bounce) {
								swap();
							}
							if (iteration > loops) {
								active = false;
							}
						}
					}
					else {
						subject.delta(start.get(), end.get(), time / duration);		
					}
				}
				time += elapsed.seconds;
			}
		}
		public void finish() {
			subject.set(end.get());
			active = false;
		}
		public void stop() {
			active = false;
		}
	}

	public static class Mover<T> implements Updatable {
		private Motionf<T> subject, max, vel, acc;
		public Mover(Motionf<T> subject, Motionf<T> max, Motionf<T> vel, Motionf<T> acc) {
			this.subject = subject;
			this.max = max;
			this.vel = vel;
			this.acc = acc;
		}
		public void update(TimeUnit elapsed) {
			vel.add(acc.get(), elapsed.seconds);
			vel.max(max.get());
			subject.add(vel.get(), elapsed.seconds);
		}
	}
	
	public static class Camera3f {
		private final Scalarf yaw = new Scalarf();
		private final Scalarf pitch = new Scalarf();
		private final Scalarf roll = new Scalarf();
		private final Scalarf distance = new Scalarf();
		private final Vec3f focus = new Vec3f();
		private final Vec3f direction = new Vec3f();  //<auto>
		private final Vec3f right = new Vec3f(); //<auto>
		private final Vec3f up = new Vec3f(); //<auto>
		private final Vec3f position = new Vec3f(); //<auto>
		private final Quaternion3f rollq = new Quaternion3f(); // <auto>
		
		public Camera3f() {
		}
		public void update() 
		{
			// Wrap all angles between 0 and 2PI
			yaw.mod(Scalarf.PI2);
			pitch.mod(Scalarf.PI2);
			roll.mod(Scalarf.PI2);
			
			// Use yaw and pitch to calculate the direction and right vector.
			// The right vector always lies on the x,z plane. All three vectors
			// are unit vectors.
			float cosy = yaw.cos();
			float siny = yaw.sin();
			float cosp = pitch.cos();
			float sinp = pitch.sin();
			
			direction.norm(siny * cosp, sinp, -cosy * cosp);
			right.set(cosy, 0f, siny);
			up.cross(right, direction);

			// If there is roll, setup the quaternion using the direction and
			// the requested roll angle, and rotate up and right.
			if (roll.v != 0f) {
				rollq.set(direction, -roll.v);
				rollq.rotate(up);
				rollq.rotate(right);
			}
			
			// Start at the focus and backout (or forward into) by the given 
			// distance in the current looking direction.
			position.set(focus);
			position.add(direction, -distance.v);
		}
		public void bind() {
			final Vec3f p = position, d = direction, u = up;
			gluLookAt(p.x, p.y, p.z, p.x + d.x, p.y + d.y, p.z + d.z, u.x, u.y, u.z);
		}
	}
	
	public static class Quaternion3f {
		public float x, y, z, w;
		public Quaternion3f() {
		}
		public Quaternion3f(float x, float y, float z, float w) {
			set(x, y, z, w);
		}
		public Quaternion3f(Vec3f v, float angle) {
			set(v, angle);
		}
		public void set(float x, float y, float z, float w) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.w = w;
		}
		public void set(Vec3f v, float angle) {
			float sin = (float)StrictMath.sin(angle * 0.5f) / v.length();
			float cos = (float)StrictMath.cos(angle * 0.5f);
			x = v.x * sin;
			y = v.y * sin;
			z = v.z * sin;
			w = cos;
		}
		public void invert() {
			x = -x;
			y = -y;
			z = -z;
		}
		public void multiply(Quaternion3f a, Quaternion3f b) {
			x = a.w * b.x + a.x * b.w + a.y * b.z - a.z * b.y;
            y = a.w * b.y + a.y * b.w + a.z * b.x - a.x * b.z;
            z = a.w * b.z + a.z * b.w + a.x * b.y - a.y * b.x;
            w = a.w * b.w - a.x * b.x - a.y * b.y - a.z * b.z;
		}
		public void multiply(float ax, float ay, float az, float aw, float bx, float by, float bz, float bw) {
			x = aw * bx + ax * bw + ay * bz - az * by;
            y = aw * by + ay * bw + az * bx - ax * bz;
            z = aw * bz + az * bw + ax * by - ay * bx;
            w = aw * bw - ax * bx - ay * by - az * bz;
		}
		private static final Quaternion3f rot = new Quaternion3f();
		public void rotate(Vec3f v) {
			v.norm();
			rot.multiply(v.x, v.y, v.z, 0f, -x, -y, -z, w);
			rot.multiply(x, y, z, w, rot.x, rot.y, rot.z, rot.w);
			v.set(rot.x, rot.y, rot.z);
		}
		public Quaternion3f copy() {
			return new Quaternion3f(x, y, z, w);
		}
	}
	
	public static class Scalarf implements Deltaf<Scalarf>, Motionf<Scalarf> {
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
		public float cos() {
			return (float)StrictMath.cos(v);
		}
		public float sin() {
			return (float)StrictMath.sin(v);
		}
		public void delta(Scalarf start, Scalarf end, float delta) {
			v = (end.v - start.v) * delta + start.v;
		}
		public Scalarf get() {
			return this;
		}
		public void set(Scalarf value) {
			v = value.v;
		}
		public void add(Scalarf value, float scale) {
			v += value.v * scale;
		}
		public void max(Scalarf max) {
			v = StrictMath.min(v, max.v);
		}
	
		public static float clamp(float v, float min, float max) {
			return (v < min ? min : (v > max ? max : v));
		}
	}
	
	public static class Vec3f implements Deltaf<Vec3f>, Motionf<Vec3f> {
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
		public void cross(Vec3f a, Vec3f b) {
			x = a.y * b.z - b.y * a.z;
			y = a.z * b.x - b.z * a.x;
			z = a.x * b.y - b.x * a.y;
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
		public float dot() {
			return (x * x) + (y * y) + (z * z);
		}
		public float length() {
			return (float)StrictMath.sqrt(dot());
		}
		public boolean isZero() {
			return (x == 0f && y == 0f && z == 0f);
		}
		public void glTranslate() {
			glTranslatef(x, y, z);
		}
		public void glRotate(float degrees) {
			glRotatef(degrees, x, y, z);
		}
		public void glScale() {
			glScalef(x, y, z);
		}
		public void bind() {
			glVertex3f(x, y, z);
		}
		public void delta(Vec3f start, Vec3f end, float delta) {
			x = (end.x - start.x) * delta + start.x;
			y = (end.y - start.y) * delta + start.y;
			z = (end.z - start.z) * delta + start.z;
		}
		public Vec3f get() {
			return this;
		}
		public void max(Vec3f max) {
			float lengthSq = dot();
			float longestSq = max.dot();
			if (lengthSq > longestSq) {
				float length = (float)Math.sqrt(lengthSq);
				float longest = (float)Math.sqrt(longestSq);
				mul(longest / length);
			}
		}
		
		public static Vec3f mul(Vec3f v, float s) {
			return new Vec3f(v.x * s, v.y * s, v.z * s);
		}
	}

	public static class Color4f implements Deltaf<Color4f>, Motionf<Color4f> {
		public static final Color4f White = new Color4f(1f, 1f, 1f, 1f);
		public float r, b, g, a;
		public Color4f() {
			set(1f, 1f, 1f, 1f);
		}
		public Color4f(float red, float green, float blue) {
			set(red, green, blue, 1f);
		}
		public Color4f(float red, float green, float blue, float alpha) {
			set(red, green, blue, alpha);
		}
		public void set(float red, float green, float blue) {
			set(red, green, blue, 1f);
		}
		public void set(float red, float green, float blue, float alpha) {
			r = red;
			g = green;
			b = blue;
			a = alpha;
		}
		public void set(Color4f c, float scale) {
			r = Scalarf.clamp(c.r * scale, 0f, 1f);
			g = Scalarf.clamp(c.g * scale, 0f, 1f);
			b = Scalarf.clamp(c.b * scale, 0f, 1f);
			a = Scalarf.clamp(c.a * scale, 0f, 1f);
		}
		public void bind() {
			glColor4f(r, g, b, a);
		}
		public void bind(float scale) {
			glColor4f(clamp(r * scale), clamp(g * scale), clamp(b * scale), clamp(a * scale));
		}
		private float clamp(float r) {
			return Scalarf.clamp(r, 0, 1);
		}
		public void delta(Color4f start, Color4f end, float delta) {
			r = (end.r - start.r) * delta + start.r;
			g = (end.g - start.g) * delta + start.g;
			b = (end.b - start.b) * delta + start.b;
			a = (end.a - start.a) * delta + start.a;
		}
		public Color4f get() {
			return this;
		}
		public void set(Color4f c) {
			r = c.r;
			g = c.g;
			b = c.b;
			a = c.a;
		}
		public void add(Color4f c, float scale) {
			r += clamp(c.r * scale);
			g += clamp(c.g * scale);
			b += clamp(c.b * scale);
			a += clamp(c.a * scale);
		}
		public void max(Color4f max) {
			r = StrictMath.min(r, max.r);
			g = StrictMath.min(g, max.g);
			b = StrictMath.min(b, max.b);
			a = StrictMath.min(a, max.a);
		}
		public void clamp() {
			r = clamp(r);
			g = clamp(g);
			b = clamp(b);
			a = clamp(a);
		}
	}
	
	public static class Model3f {
//		private static int callListId = 0;
		
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
			up.cross(normal, right);
			right.cross(normal, up);
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
	
	public static class Coord {
		
	}
	
	public static class Cube implements Drawable {
		public float x, y, z;
		public float w, d, h;
		public Cube() {
			x = y = z = 0f;
			w = d = h = 1f;
		}
		public void center(float cx, float cy, float cz) {
			x = cx - (w * 0.5f);
			y = cy - (h * 0.5f);
			z = cz - (d * 0.5f);
		}
		public void size(float width, float height, float depth) {
			w = width;
			h = height;
			d = depth;
		}
		public void draw() {
			glBegin(GL_QUADS); {
				top(); bottom(); left(); right(); front(); back();
			} glEnd();
		}
		public void top() {
			glVertex3f(x + w, y + h, z);
			glVertex3f(x, y + h, z);
			glVertex3f(x, y + h, z + d);
			glVertex3f(x + w, y + h, z + d);
		}
		public void bottom() {
			glVertex3f(x + w, y, z + d);
			glVertex3f(x, y, z + d);
			glVertex3f(x, y, z);
			glVertex3f(x + w, y, z);
		}
		public void front() {
			glVertex3f(x + w, y + h, z + d);
			glVertex3f(x, y + h, z + d);
			glVertex3f(x, y, z + d);
			glVertex3f(x + w, y, z + d);
		}
		public void back() {
			glVertex3f(x + w, y, z);
			glVertex3f(x, y, z);
			glVertex3f(x, y + h, z);
			glVertex3f(x + w, y + h, z);
		}
		public void left() {
			glVertex3f(x, y + h, z + d);
			glVertex3f(x, y + h, z);
			glVertex3f(x, y, z);
			glVertex3f(x, y, z + d);
		}
		public void right() {
			glVertex3f(x + w, y + h, z);
			glVertex3f(x + w, y + h, z + d);
			glVertex3f(x + w, y, z + d);
			glVertex3f(x + w, y, z);
		}
	}
	
	public class Skybox {
		public Texture top, right, left, front, back;
		public float x, y, z;
		public float w, d, h;
		public void center(float cx, float cy, float cz) {
			x = cx - (w * 0.5f);
			y = cy - (h * 0.5f);
			z = cz - (d * 0.5f);
		}
		public void size(float width, float height, float depth) {
			w = width;
			h = height;
			d = depth;
		}
		public void draw() {
			top(); right(); left(); front(); back();
		}
		public void top() {
			top.bind();
			glBegin(GL_QUADS); {
				glTexCoord2f(1, 1);
				glVertex3f(x + w, y + h, z + d);
				glTexCoord2f(0, 1);
				glVertex3f(x, y + h, z + d);
				glTexCoord2f(0, 0);
				glVertex3f(x, y + h, z);
				glTexCoord2f(1, 0);
				glVertex3f(x + w, y + h, z);	
			} glEnd();
		}
		public void front() {
			front.bind();
			glBegin(GL_QUADS); {
				glTexCoord2f(1, 1);
				glVertex3f(x + w, y, z + d);
				glTexCoord2f(0, 1);
				glVertex3f(x, y, z + d);
				glTexCoord2f(0, 0);	
				glVertex3f(x, y + h, z + d);
				glTexCoord2f(1, 0);
				glVertex3f(x + w, y + h, z + d);
			} glEnd();
		}
		public void back() {
			back.bind();
			glBegin(GL_QUADS); {
				glTexCoord2f(0, 0);
				glVertex3f(x + w, y + h, z);
				glTexCoord2f(1, 0);
				glVertex3f(x, y + h, z);
				glTexCoord2f(1, 1);
				glVertex3f(x, y, z);
				glTexCoord2f(0, 1);
				glVertex3f(x + w, y, z);
			} glEnd();
		}
		public void left() {
			left.bind();
			glBegin(GL_QUADS); {
				glTexCoord2f(1, 1);
				glVertex3f(x, y, z + d);
				glTexCoord2f(0, 1);
				glVertex3f(x, y, z);
				glTexCoord2f(0, 0);
				glVertex3f(x, y + h, z);
				glTexCoord2f(1, 0);
				glVertex3f(x, y + h, z + d);
			} glEnd();
		}
		public void right() {
			right.bind();
			glBegin(GL_QUADS); {
				glTexCoord2f(1, 1);
				glVertex3f(x + w, y, z);
				glTexCoord2f(0, 1);
				glVertex3f(x + w, y, z + d);
				glTexCoord2f(0, 0);
				glVertex3f(x + w, y + h, z + d);
				glTexCoord2f(1, 0);
				glVertex3f(x + w, y + h, z);
			} glEnd();
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
