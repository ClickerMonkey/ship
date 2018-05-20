package wars;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import axe.DrawMode;
import axe.Scalarf;
import axe.TimeUnit;
import axe.g2f.Rect2i;
import axe.g2f.tile.Tilesheet;
import axe.g3f.Camera3f;
import axe.g3f.Drawable3f;
import axe.g3f.Scene3f;
import axe.g3f.Surface3f;
import axe.g3f.Updatable3f;
import axe.g3f.Vec3f;
import axe.g3f.geom.Cube;
import axe.input.KeyEvent;
import axe.input.KeyListener;
import axe.input.MouseEvent;
import axe.input.MouseListener;
import axe.pick.Pick;
import axe.pick.PickList;
import axe.pick.Picker;
import axe.texture.SourceTile;
import axe.texture.Texture;
import axe.texture.TextureLoader;
import axe.texture.TextureWrap;
import axe.util.Node;
import axe.util.Screen;
import axe.util.ScreenListener;
import axe.util.Vector;



public class WorldEdit implements ScreenListener, MouseListener, KeyListener
{
	
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	private static final int TILE_W = 10;
	private static final int TILE_H = 10;
	private static final float PICK_TIME = 0.25f;

	
	public static void main(String[] args) 
	{
		Screen screen = new Screen(WIDTH, HEIGHT, false, 1);

		WorldEdit game = new WorldEdit(screen);

		screen.addScreenListener(game);
		screen.addKeyListener(game);
		screen.addMouseListener(game);
		screen.start();
	}


	private final Screen screen;
	private final Vector mouse;
	private final Scene3f scene;
	private Texture floor;
	private Surface3f surf;
	private FloorTile[][] tiles;
//	private Light light0;
	private PickList<Scene3f> tileList;
	private Picker picker = new Picker(WIDTH/2, HEIGHT/2, 8, 8);
	private float pickTime = 0f;

	
//	private float delta = 0f;
	
	public WorldEdit(Screen screen) 
	{
		this.screen = screen;
		this.mouse = new Vector(WIDTH/2, HEIGHT/2);
		this.scene = new Scene3f(new Rect2i(0, 0, WIDTH, HEIGHT));
	}

	@Override
	public void onStart() 
	{
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glAlphaFunc(GL_GREATER, 0.1f);
		glEnable(GL_ALPHA_TEST);
		glClearColor(0f, 0f, 0f, 1f);

		scene.init();

//		Light.init();
//		light0 = new Light(0);
//		light0.on();

		final Camera3f cam = scene.camera;
		cam.focus.set(5, 0, 5);
		cam.distance.set(7);
		cam.yaw.set(0f);
		cam.pitch.set(Scalarf.PI2 * 0.875f);

		try {
			TextureLoader loader = new TextureLoader();
//			floor = loader.getTexture("wars/assets/ground.png");
			floor = loader.getTexture("wars/assets/terrain.png", TextureWrap.Default);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

//		Tilesheet sheet = new Tilesheet(floor, 0, 0, 512, 512, 2, 1);
		Tilesheet sheet = new Tilesheet(floor, 0, 0, 64, 64, 16, 256);

		tiles = new FloorTile[TILE_H][TILE_W];
		tileList = new PickList<Scene3f>(TILE_H * TILE_W);
		for (int z = 0; z < TILE_H; z++) {
			for (int x = 0; x < TILE_W; x++) {
				tiles[z][x] = new FloorTile(new Vec3f(x, 0, z), 1f, sheet.get(22));
				tileList.add(tiles[z][x]);
			}
		}

		surf = new Surface3f(2f, 2f);
		surf.position.set(-1, 0, 5);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void onDraw() 
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		glColor4f(1f, 1f, 1f, 1f);
		
		picker.cx = mouse.x;
		picker.cy = mouse.y;
		Pick<Scene3f>[] picks = new Pick[4];
		int pickCount = picker.pick(picks, tileList, scene);
		if (pickCount > 0) {
			PickList<Scene3f> grabList = new PickList<Scene3f>(pickCount * 9);
			for (int i = 0; i < pickCount; i++) {
				if ((picks[i].minZ - picks[0].minZ) < 1000) {
					((FloorTile)picks[i].obj).grabify(grabList);
				}
			}
			pickCount = picker.pick(picks, grabList, scene);
			int sign = (Keyboard.isKeyDown(Keyboard.KEY_TAB) ? -1 : 1);
			if (Mouse.isButtonDown(0) && pickTime >= PICK_TIME) {
				for (int i = 0; i < pickCount; i++) {
					Grab grab = (Grab)picks[i].obj;
					for (Vec3f c : grab.corners) {
						c.y += sign * 0.15f;
					}
					grab.tile.update();
				}
				pickTime = 0f;
			}
		}

		scene.start();

//		light0.direction(scene.camera.direction);
//		light0.position(scene.camera.position);
//		light0.update();

		glEnable(GL_TEXTURE_2D);
		floor.bind();
//		Light.enable();
		tileList.draw(DrawMode.Normal, scene);
//		Light.disable();
		glDisable(GL_TEXTURE_2D);

		surf.update(new Vec3f(0f, 1f, 0f));
		surf.draw(DrawMode.Normal, scene);
		
		Cube c = new Cube();
		c.size(0.5f, 0.5f, 0.5f);
		c.center(scene.camera.focus);
		glBegin(GL_QUADS); {
			glColor4f(1f, 0f, 0f, 1f); glNormal3f( 0, 1, 0); c.top();
			glColor4f(1f, 1f, 0f, 1f); glNormal3f( 0,-1, 0); c.bottom();
			glColor4f(1f, 0f, 1f, 1f); glNormal3f( 1, 0, 0); c.right();
			glColor4f(0f, 1f, 1f, 1f); glNormal3f(-1, 0, 0); c.left();
			glColor4f(0f, 0f, 1f, 1f); glNormal3f( 0, 0, 1); c.front();
			glColor4f(0f, 1f, 0f, 1f); glNormal3f( 0, 0,-1); c.back();
			glColor4f(1f, 1f, 1f, 1f);
		} glEnd();
		
		
		glPushMatrix();
		glTranslatef(0, 0.02f, 0);
		glColor4f(1f, 1f, 01f, 1f);
		glBlendFunc(GL_ONE_MINUS_DST_COLOR, GL_ZERO);
		if (pickCount > 0) {
			for (int i = 0; i < pickCount; i++) {
				((Grab)picks[i].obj).draw(DrawMode.Normal, scene);
			}
		}
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glColor4f(1f, 1f, 1f, 1f);
		if (pickCount > 0) {
			for (int i = 0; i < pickCount; i++) {
				FloorTile tile = ((Grab)picks[i].obj).tile;
				glBegin(GL_LINE_LOOP); {
					tile.points[0].bind();
					tile.points[1].bind();
					tile.points[2].bind();
					tile.points[3].bind();
				} glEnd();
			}
		}
		glPopMatrix();

		scene.end();



		init2d(WIDTH, HEIGHT);
		glPushMatrix(); {
			glTranslatef(mouse.x, mouse.y, 0);
			glScalef(picker.width, picker.height, 1f);
			glColor4f(1f, 0f, 0f, 1f);
			glBegin(GL_LINE_LOOP); {
				glVertex2f(-0.5f, 0.5f);
				glVertex2f(0.5f, 0.5f);
				glVertex2f(0.5f, -0.5f);
				glVertex2f(-0.5f, -0.5f);
			} glEnd();
		} glPopMatrix();
	}

	@Override
	public void onUpdate(TimeUnit elapsed) 
	{
		final Camera3f cam = scene.camera;
		
		surf.yaw.add(elapsed.seconds);
		
		pickTime += elapsed.seconds;

		int dx = Mouse.getDX();
		int dy = Mouse.getDY();
		if (Mouse.isButtonDown(1)) {
			cam.yaw.add(dx * elapsed.seconds);
			cam.pitch.add(dy * elapsed.seconds);
		}
//		float dx = mouse.x - WIDTH * 0.5f;
//		float dy = mouse.y - HEIGHT * 0.5f;
//		float angle = (float)Math.atan2(dx, dy);
//		cam.yaw.set(angle);
		
		

		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			cam.roll.sub(elapsed.seconds);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			cam.roll.add(elapsed.seconds);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			cam.roll.set(0);
		}

		float speed = 4 * elapsed.seconds;
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			cam.focus.add(cam.forward, speed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			cam.focus.add(cam.forward, -speed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			cam.focus.add(cam.right, -speed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			cam.focus.add(cam.right, speed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			cam.focus.y -= speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			cam.focus.y += speed;
		}

//		delta = Scalarf.clamp(delta + Mouse.getDWheel() * -elapsed.seconds * 0.2f, 0, 1);
//		if (delta > 0.5f) {
//			float d = (delta - 0.5f) * 2;
//			cam.pitch.set((float)Math.toRadians(d * 45 + 270));
//			cam.distance.set((1f-d) * 4 + 4);	
//		}
//		else {
//			float d = delta * 2;
//			cam.pitch.set((float)Math.toRadians(270));
//			cam.distance.set((1f-d) * 4 + 8);	
//		}
		
		
		//*
		cam.distance.add(Mouse.getDWheel() * -elapsed.seconds);
		//*/
		
		cam.update(elapsed, scene);
	}

	public void init2d(int w, int h) 
	{
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluOrtho2D(0, w, 0, h);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}

	public static class FloorTile implements Drawable3f 
	{
		private final Vec3f[] points = {new Vec3f(), new Vec3f(), new Vec3f(), new Vec3f()};
		private final Vec3f[] normals = {new Vec3f(), new Vec3f(), new Vec3f(), new Vec3f()};
		private final Vec3f center = new Vec3f();
		private final Vec3f normal = new Vec3f();
		private final SourceTile tile;
		public FloorTile(Vec3f corner, float size, SourceTile frame) {
			points[0].set(corner.x, corner.y, corner.z);
			points[1].set(corner.x + size, corner.y, corner.z);
			points[2].set(corner.x + size, corner.y, corner.z - size);
			points[3].set(corner.x, corner.y, corner.z - size);
			tile = frame;
			update();
		}
		public void draw(DrawMode mode, Scene3f scene) {
			glBegin(GL_TRIANGLE_FAN); {
				glNormal3f(normal.x, normal.y, normal.z);
				glTexCoord2f(tile.cs(), tile.ct());
				glVertex3f(center.x, center.y, center.z);

				glNormal3f(normals[0].x, normals[0].y, normals[0].z);
				glTexCoord2f(tile.s0, tile.t0);
				glVertex3f(points[0].x, points[0].y, points[0].z);

				glNormal3f(normals[1].x, normals[1].y, normals[1].z);
				glTexCoord2f(tile.s0, tile.t1);
				glVertex3f(points[1].x, points[1].y, points[1].z);

				glNormal3f(normals[2].x, normals[2].y, normals[2].z);
				glTexCoord2f(tile.s1, tile.t1);
				glVertex3f(points[2].x, points[2].y, points[2].z);

				glNormal3f(normals[3].x, normals[3].y, normals[3].z);
				glTexCoord2f(tile.s1, tile.t0);
				glVertex3f(points[3].x, points[3].y, points[3].z);

				glNormal3f(normals[0].x, normals[0].y, normals[0].z);
				glTexCoord2f(tile.s0, tile.t0);
				glVertex3f(points[0].x, points[0].y, points[0].z);
			} glEnd();
			// NORMALS
			//			glColor4f(1f, 1f, 1f, 1f);
			//			glBegin(GL_LINES); {
			//				for (int i = 0; i < 4; i++) {
			//					glVertex3f(points[i].x, points[i].y, points[i].z);
			//					glVertex3f(points[i].x+normals[i].x, points[i].y+normals[i].y, points[i].z+normals[i].z);
			//				}
			//			} glEnd();
			// EDGES
			//			glColor4f(0f, 0f, 0f, 0.5f);
			//			glBegin(GL_LINE_LOOP); {
			//				points[0].bind();
			//				points[1].bind();
			//				points[2].bind();
			//				points[3].bind();
			//			} glEnd();
			//			glColor4f(1f, 1f, 1f, 1f);
		}
		
		public void update() 
		{
			center.average(points);
			normals[0].cross(points[1], points[0], points[3]);
			normals[1].cross(points[2], points[1], points[0]);
			normals[2].cross(points[3], points[2], points[1]);
			normals[3].cross(points[0], points[3], points[2]);
			normal.average(normals); 
		}
		
		public void grabify(PickList<Scene3f> grabs) 
		{
			// 0---3
			// |   |
			// 1---2
			final Vec3f[] p = points;

			//    0   1
			// 2  3   4  5
			// 6  7   8  9
			//    10  11
			final Vec3f[] v = new Vec3f[12];
			v[0]  = Vec3f.inter(p[0], p[3], 0.25f);
			v[1]  = Vec3f.inter(p[3], p[0], 0.25f);
			v[2]  = Vec3f.inter(p[0], p[1], 0.25f);
			v[3]  = Vec3f.inter(p[0], center, 0.5f);
			v[4]  = Vec3f.inter(p[3], center, 0.5f);
			v[5]  = Vec3f.inter(p[3], p[2], 0.25f);
			v[6]  = Vec3f.inter(p[1], p[0], 0.25f);
			v[7]  = Vec3f.inter(p[1], center, 0.5f);
			v[8]  = Vec3f.inter(p[2], center, 0.5f);
			v[9]  = Vec3f.inter(p[2], p[3], 0.25f);
			v[10] = Vec3f.inter(p[1], p[2], 0.25f);
			v[11] = Vec3f.inter(p[2], p[1], 0.25f);

			// 00 1111 22 
			// 33 4444 55
			// 33 4444 55
			// 66 7777 88
			grabs.add(new Grab(this, GL_QUADS, new Vec3f[] {p[0]}, 
					new Vec3f[] {p[0], v[2], v[3], v[0]}));
			grabs.add(new Grab(this, GL_QUADS, new Vec3f[] {p[0], p[3]}, 
					new Vec3f[] {v[0], v[3], v[4], v[1]}));
			grabs.add(new Grab(this, GL_QUADS, new Vec3f[] {p[3]}, 
					new Vec3f[] {p[3], v[1], v[4], v[5]}));
			grabs.add(new Grab(this, GL_QUADS, new Vec3f[] {p[0], p[1]}, 
					new Vec3f[] {v[2], v[6], v[7], v[3]}));
			grabs.add(new Grab(this, GL_TRIANGLE_FAN, new Vec3f[] {p[3], p[0], p[1], p[2]}, 
					new Vec3f[] {center, v[3], v[7], v[8], v[4], v[3]}));
			grabs.add(new Grab(this, GL_QUADS, new Vec3f[] {p[2], p[3]}, 
					new Vec3f[] {v[4], v[8], v[9], v[5]}));
			grabs.add(new Grab(this, GL_QUADS, new Vec3f[] {p[1]}, 
					new Vec3f[] {p[1], v[10], v[7], v[6]}));
			grabs.add(new Grab(this, GL_QUADS, new Vec3f[] {p[1], p[2]}, 
					new Vec3f[] {v[10], v[11], v[8], v[7]}));
			grabs.add(new Grab(this, GL_QUADS, new Vec3f[] {p[2]}, 
					new Vec3f[] {p[2], v[9], v[8], v[11]})); 
		}
	}

	public static class Grab implements Drawable3f 
	{
		public final FloorTile tile;
		public final Vec3f[] corners;
		private final Vec3f[] quad;
		private final int mode;
		public Grab(FloorTile tile, int type, Vec3f[] corners, Vec3f[] quad) {
			this.tile = tile;
			this.mode = type;
			this.corners = corners;
			this.quad = quad;
		}
		public void draw(DrawMode mode, Scene3f scene) {
			glBegin(this.mode); {
				for (Vec3f v : quad) {
					v.bind();
				}
			} glEnd();
		}
	}

	public class ParticleEmitter {
		
	}
	
	public interface ParticleFactory {
		public Particle create(ParticleEmitter emitter); 
	}
	
	public static class ParticleDefault implements Particle 
	{
		public Vec3f size, grow;
		public Vec3f pos, vel, acc;
		public final float life;
		public float time;
		public ParticleDefault(float life) {
			this.life = life;
		}
		public void update(TimeUnit elapsed, Scene3f scene) {
			time += elapsed.seconds;
			vel.add(acc, elapsed.seconds);
			pos.add(vel, elapsed.seconds);
			size.add(grow, elapsed.seconds);
		}
		public void draw(Vec3f r, Vec3f u, SourceTile tile) {
			glTexCoord2f(tile.s0, tile.t0);
			glVertex3f(pos.x+u.x+r.x, pos.y+u.y+r.y, pos.y+u.y+r.y);
			glTexCoord2f(tile.s0, tile.t1);
			glVertex3f(pos.x-u.x+r.x, pos.y-u.y+r.y, pos.y-u.y+r.y);
			glTexCoord2f(tile.s1, tile.t1);
			glVertex3f(pos.x-u.x-r.x, pos.y-u.y-r.y, pos.y-u.y-r.y);
			glTexCoord2f(tile.s1, tile.t0);
			glVertex3f(pos.x+u.x-r.x, pos.y+u.y-r.y, pos.y+u.y-r.y);
		}
		public boolean isAlive() {
			return (time < life);
		}
		public int getMode() {
			return GL_QUADS;
		}
		public void draw(DrawMode mode, Scene3f scene) {
			
		}
	}
	
	public interface Particle extends Drawable3f, Updatable3f {
		public boolean isAlive();
		public int getMode();
	}
	
	public static class Effect implements Drawable3f, Updatable3f
	{
		
		public final Vec3f right = new Vec3f();
		public final Vec3f up = new Vec3f();
		
		public void update(TimeUnit elapsed, Scene3f scene) {
			
		}
		
		public void draw(DrawMode mode, Scene3f scene) {
			
		}
		
	}
	
 	public static class Mirror implements Drawable3f 
 	{
		private final Surface3f mirror;
		private final Camera3f camera;
		private final Node<Drawable3f> reflectables;
		private final Vec3f focal = new Vec3f();
		
		public Mirror(Camera3f camera, Surface3f mirror) {
			this.camera = camera;
			this.mirror = mirror;
			this.reflectables = new Node<Drawable3f>(null);
		}
		
		public void add(Drawable3f reflectable) {
			new Node<Drawable3f>(reflectable).insert(reflectables);
		}
		
		public void draw(DrawMode mode, Scene3f scene) {
			Vec3f oldDirection = new Vec3f(camera.direction);
			Vec3f oldPosition = new Vec3f(camera.position);
			
			focal.set(mirror.position);
			focal.sub(camera.position);
			
			camera.direction.reflect(focal, mirror.normal);
			camera.position.refract(focal, mirror.normal);
			camera.position.add(mirror.position);
			camera.bind();
			
			Node<Drawable3f> current = reflectables.next();
			while (current != null) {
				current.x.draw(mode, scene);
				current = current.next();
			}
			
			camera.direction.set(oldDirection);
			camera.direction.set(oldPosition);
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
		mouse.x = Scalarf.clamp(mouse.x, 0, WIDTH);
		mouse.y = Scalarf.clamp(mouse.y, 0, HEIGHT);	
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
		mouse.x = Scalarf.clamp(mouse.x, 0, WIDTH);
		mouse.y = Scalarf.clamp(mouse.y, 0, HEIGHT);	
	}

	@Override
	public void onMouseWheel(MouseEvent e) {
	}


}
