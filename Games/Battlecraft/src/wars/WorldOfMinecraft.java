package wars;

import static org.lwjgl.opengl.GL11.*;

import java.nio.DoubleBuffer;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import axe.Blend;
import axe.DrawMode;
import axe.Scalarf;
import axe.TimeUnit;
import axe.g2f.Rect2i;
import axe.g2f.Scene2f;
import axe.g2f.Vec2f;
import axe.g2f.tile.Tilesheet;
import axe.g3f.Camera3f;
import axe.g3f.Drawable3f;
import axe.g3f.Scene3f;
import axe.g3f.Surface3f;
import axe.g3f.Vec3f;
import axe.g3f.geom.Cube;
import axe.input.KeyEvent;
import axe.input.KeyListener;
import axe.input.MouseEvent;
import axe.input.MouseListener;
import axe.texture.SourceTile;
import axe.texture.Texture;
import axe.texture.TextureLoader;
import axe.texture.TextureWrap;
import axe.util.Buffers;
import axe.util.Color4f;
import axe.util.Screen;
import axe.util.ScreenListener;
import axe.util.Vector;



public class WorldOfMinecraft implements ScreenListener, MouseListener, KeyListener
{
	
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	
	public static void main(String[] args) 
	{	
		Screen screen = new Screen(WIDTH, HEIGHT, false, 1);

		WorldOfMinecraft game = new WorldOfMinecraft(screen);

		screen.addScreenListener(game);
		screen.addKeyListener(game);
		screen.addMouseListener(game);
		screen.start();
	}


	private final Screen screen;
	private final Vector mouse;
	private final Scene3f scene3f;
	private final Scene2f scene2f;
//	private Light light0;
	private Heightmap hmap;
	private Surface3f surf;
	private Tilesheet sheet;
	private Texture sheetTexture;
	private int callList = -1;
	private float callTime = 0f;
	
	public WorldOfMinecraft(Screen screen) 
	{
		this.screen = screen;
		this.mouse = new Vector(WIDTH/2, HEIGHT/2);
		this.scene3f = new Scene3f(new Rect2i(0, 0, WIDTH, HEIGHT));
		this.scene2f = new Scene2f(new Rect2i(0, 0, WIDTH, HEIGHT));
	}

	@Override
	public void onStart() 
	{
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glAlphaFunc(GL_GREATER, 0.1f);
		glEnable(GL_ALPHA_TEST);
		glClearColor(0f, 0f, 0f, 1f);

//		Light.init();
//		light0 = new Light(0);
//		light0.direction(0, -1, 0);
//		light0.position(50, 50, 50);
//		light0.specular(0.2f, 0.8f, 0.2f, 0.5f);
//		light0.on();
		
		final Camera3f cam = scene3f.camera;
		cam.focus.set(0, 0, 0);
		cam.distance.set(7);
		cam.yaw.set(0f);
		cam.pitch.set(Scalarf.PI2 * 0.875f);

		surf = new Surface3f(2f, 2f);
		surf.position.set(0, 0, 0);

		try {
			TextureLoader loader = new TextureLoader();
			sheetTexture = loader.getTexture("wars/assets/terrain.png", TextureWrap.Repeat);
			sheet = new Tilesheet(sheetTexture, 0, 0, 64, 64, 16, 256);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		hmap = new Heightmap();
		hmap.size.set(0.1f, 0.1f);
		hmap.center.set(-45f, 0.25f, -45f);
		hmap.top = sheet.get(0);
		hmap.side = sheet.get(2);
		newLandscape();
		
		glEnable(GL_COLOR_MATERIAL);
		glLightModeli(GL_LIGHT_MODEL_LOCAL_VIEWER, GL_TRUE);
		
	}
	
	public void newLandscape() {
		float[][] map1, map2;
		
		map1 = perlin(101, 10, -20, 20, Interpolate.Cosine);
		map2 = perlin(101, 10, -20, 20, Interpolate.Cosine);
		combine(map1, map2, Function.Add);
		map2 = perlin(101, 10, -10, 10, Interpolate.Cosine);
		combine(map1, map2, Function.Avg);
//		map2 = perlin(101, 10, -5, 5, Interpolate.Cosine);
//		combine(map1, map2, Function.Add);
//		map2 = perlin(101, 10, -5, 2, Interpolate.Cosine);
//		combine(map1, map2, Function.Avg);
		
		map2 = perlin(101, 10, -5, 10, Interpolate.Cosine);
		combine(map1, map2, Function.Min);
		map2 = perlin(101, 10, -10, 5, Interpolate.Cosine);
		combine(map1, map2, Function.Max);

		smooth(map1, map2);
		round(map2, 1f);
		
		hmap.generate(map2);
	}
	
	DoubleBuffer clipTop = Buffers.doubles(0, -1, 0, 0);
	DoubleBuffer clipBot = Buffers.doubles(0, 1, 0, 0);
	
	@Override
	public void onDraw() 
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

//		light0.direction(scene3f.camera.direction);
//		light0.position(scene3f.camera.position);
//		light0.update();
		
		// 3D Rendering
		scene3f.init();
		scene3f.start(); {
			
			glEnable(GL_CULL_FACE);
			glCullFace(GL_BACK);
			
//			light0.update();

			if (callList == -1) {
				callList = glGenLists(2);
				
				glNewList(callList, GL_COMPILE);
				glColor3f(0, 0.2f, 0);
				glClipPlane(GL_CLIP_PLANE0, clipTop);
				glEnable(GL_CLIP_PLANE0);
				hmap.draw(DrawMode.Normal, scene3f);
				glDisable(GL_CLIP_PLANE0);
				glEndList();
				
				glNewList(callList+1, GL_COMPILE);
				glColor3f(0, 0.2f, 0);
				glClipPlane(GL_CLIP_PLANE0, clipBot);
				glEnable(GL_CLIP_PLANE0);
				hmap.draw(DrawMode.Normal, scene3f);
				glDisable(GL_CLIP_PLANE0);
				glEndList();
			}
			
//			Light.enable();
			glCallList(callList);
//			Light.disable();

			glDisable(GL_DEPTH_TEST);
			Blend.on();
			Blend.Add.bind();
			glBegin(GL_QUADS); {
				glColor4f(0f, 0f, 0.5f, 0.25f);
				glNormal3f(0, 1, 0);
				glVertex3f(-50, 0, 50);
				glVertex3f(50, 0, 50);
				glVertex3f(50, 0, -50);
				glVertex3f(-50, 0, -50);
			} glEnd();
			Blend.off();
			glEnable(GL_DEPTH_TEST);

//			Light.enable();
			glCallList(callList+1);
//			Light.disable();
			
			Cube c = new Cube();
			c.size(0.5f, 0.5f, 0.5f);
			c.center(scene3f.camera.focus);
			glBegin(GL_QUADS); {
				glColor4f(1f, 0f, 0f, 1f); glNormal3f( 0, 1, 0); c.top();
				glColor4f(1f, 1f, 0f, 1f); glNormal3f( 0,-1, 0); c.bottom();
				glColor4f(1f, 0f, 1f, 1f); glNormal3f( 1, 0, 0); c.right();
				glColor4f(0f, 1f, 1f, 1f); glNormal3f(-1, 0, 0); c.left();
				glColor4f(0f, 0f, 1f, 1f); glNormal3f( 0, 0, 1); c.front();
				glColor4f(0f, 1f, 0f, 1f); glNormal3f( 0, 0,-1); c.back();
				glColor4f(1f, 1f, 1f, 1f);
			} glEnd();
		
		} scene3f.end();

		// 2D Rendering
		scene2f.init();
		scene2f.start(); {
			
			glPushMatrix(); {
				glTranslatef(mouse.x, mouse.y, 0);
				glScalef(8, 8, 1f);
				glColor4f(1f, 0f, 0f, 1f);
				glBegin(GL_LINE_LOOP); {
					glVertex2f(-0.5f, 0.5f);
					glVertex2f(0.5f, 0.5f);
					glVertex2f(0.5f, -0.5f);
					glVertex2f(-0.5f, -0.5f);
				} glEnd();
			} glPopMatrix();
			
		} scene2f.end();
	}

	float time = 0.0f;
	float update = 0.5f;
	int frames = 0;
	String fps = "0";
	
	@Override
	public void onUpdate(TimeUnit elapsed) 
	{
		time += elapsed.seconds;
		frames++;
		if (time >= update) {
			fps = String.valueOf((int)(frames / time));
			time -= update;
			frames = 0;
			Display.setTitle(fps);
		}
		
		final Camera3f cam = scene3f.camera;
		
		callTime += elapsed.seconds;
		
		if (callTime >= 0.5f && Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			newLandscape();
			callList = -1;
			callTime = 0f;
		}
		
		surf.yaw.add(elapsed.seconds);
		
		int dx = Mouse.getDX();
		int dy = Mouse.getDY();
		if (Mouse.isButtonDown(1)) {
			cam.yaw.add(dx * elapsed.seconds);
			cam.pitch.add(dy * elapsed.seconds);
		}

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
		
		cam.distance.add(Mouse.getDWheel() * -elapsed.seconds);
		
		cam.update(elapsed, scene3f);
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
		mouse.add(e.dx, -e.dy);
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
		mouse.add(e.dx, -e.dy);
		mouse.x = Scalarf.clamp(mouse.x, 0, WIDTH);
		mouse.y = Scalarf.clamp(mouse.y, 0, HEIGHT);	
	}

	@Override
	public void onMouseWheel(MouseEvent e) {
	}
	
	private enum Function {
		Clear {
			public float apply(float prev, float next) {
				return next;
			}
		},
		None {
			public float apply(float prev, float next) {
				return prev;
			}
		},
		Add {
			public float apply(float prev, float next) {
				return prev + next;
			}
		},
		Subtract {
			public float apply(float prev, float next) {
				return prev - next;
			}
		},
		Avg {
			public float apply(float prev, float next) {
				return (prev + next) * 0.5f;
			}
		},
		Min {
			public float apply(float prev, float next) {
				return (prev < next ? prev : next);
			}
		},
		Max {
			public float apply(float prev, float next) {
				return (prev > next ? prev : next);
			}
		};
		
		public abstract float apply(float prev, float next);
	}
	
	private enum Interpolate {
		Linear {
			public float delta(float y0, float y1, float d) {
				return (y1 - y0) * d + y0;
			}
		},
		Cosine {
			public float delta(float y0, float y1, float d) {
				d = (1f - Scalarf.cos(d * Scalarf.PI)) * 0.5f;
				return (y0 * (1 - d)) + (y1 * d);
			}
		};
		public abstract float delta(float y0, float y1, float d);
	}

	private final Random rnd = new Random();
	
	public void combine(float[][] m1, float m2[][], Function func) 
	{
		for (int z = 0; z < m1.length; z++) {
			for (int x = 0; x < m1[z].length; x++) {
				m1[z][x] = func.apply(m1[z][x], m2[z][x]);
			}
		}
	}
	
	public void round(float[][] m, float s) 
	{
		for (int z = 0; z < m.length; z++) {
			for (int x = 0; x < m[z].length; x++) {
				m[z][x] = (float)Math.floor(m[z][x] / s) * s;
			}
		}
	}
	
	public void smooth(float[][] m, float[][] out) 
	{
		int d = m.length;
		int w = m[0].length;
		for (int z = 0; z < d; z++) {
			for (int x = 0; x < w; x++) {
				float sum = m[z][x];
				int total = 1;
				if (z > 0) {
					sum += m[z-1][x]; total++;
				}
				if (z < d - 1) {
					sum += m[z+1][x]; total++;
				}
				if (x > 0) {
					sum += m[z][x-1]; total++;
				}
				if (x < w - 1) {
					sum += m[z][x+1]; total++;
				}

				if (z > 0 && x > 0) {
					sum += m[z-1][x-1]; total++;
				}
				if (z > 0 && x < w - 1) {
					sum += m[z-1][x+1]; total++;
				}
				
				if (z < d - 1 && x > 0) {
					sum += m[z+1][x-1]; total++;
				}
				if (z < d - 1 && x < w - 1) {
					sum += m[z+1][x+1]; total++;
				}
				
				out[z][x] = sum / total;
			}
		}
	}
	
	public float[][] perlin(int size, int freq, float min, float max, Interpolate inter) 
	{
		float[][] map = new float[size][size];
		
		// corners
		for (int z = 0; z < size; z += freq) {
			for (int x = 0; x < size; x += freq) {
				map[z][x] = rnd(min, max);
			}
		}

		// edges x-axis
		for (int z = 0; z < size; z += freq) {
			for (int x = 0; x < size-1; x++) {
				int dx = x % freq;
				int ax = x - dx;
				if (dx != 0) {
					map[z][x] = inter.delta(map[z][ax], map[z][ax+freq], dx/(float)freq);	
				}
			}
		}

		// edges z-axis
		for (int x = 0; x < size; x+=freq) {
			for (int z = 0; z < size-1; z++) {
				int dz = z % freq;
				int az = z - dz;
				if (dz != 0) {
					map[z][x] = inter.delta(map[az][x], map[az+freq][x], dz/(float)freq);	
				}
			}
		}
		
		// in between
		for (int z = 0; z < size-1; z++) {
			int dz = z % freq;
			int az = z - dz;
			for (int x = 0; x < size-1; x++) {
				int dx = x % freq;
				int ax = x - dx;
				if (dx != 0 && dz != 0) {
					float affx = inter.delta(map[z][ax], map[z][ax+freq], dx/(float)freq);
					float affz = inter.delta(map[az][x], map[az+freq][x], dz/(float)freq);
					map[z][x] = (affx + affz) * 0.5f;
				}
			}
		}
		
		return map;
	}
	
	public float rnd(float min, float max) 
	{
		return (max - min) * rnd.nextFloat() + min;
	}
	
	private Vec3f[][] SIDES = {
			/*Top   */{new Vec3f()},
			/*Bottom*/
			/*Right */
			/*Left  */
			/*Front */
			/*Back  */
	};
	
	public class Tile {
		public SourceTile source;
		public Color4f color;
	}
	
	public class Side implements Drawable3f {
		float[] light;
		Tile tile;
		public void draw(DrawMode mode, Scene3f scene) {
		}
	}
	
	public void draw(SourceTile tile, Color4f shade, float[] light, Vec3f[] v, float x, float y, float z) {
		glColor4f(shade.r * light[0], shade.g * light[0], shade.b * light[0], shade.a);
		glTexCoord2f(tile.s0, tile.t0);
		glVertex3f(x+v[0].x, y+v[0].y, z+v[0].z);
		
		glColor4f(shade.r * light[1], shade.g * light[1], shade.b * light[1], shade.a);
		glTexCoord2f(tile.s0, tile.t1);
		glVertex3f(x+v[1].x, y+v[1].y, z+v[1].z);
		
		glColor4f(shade.r * light[2], shade.g * light[2], shade.b * light[2], shade.a);
		glTexCoord2f(tile.s1, tile.t1);
		glVertex3f(x+v[2].x, y+v[2].y, z+v[2].z);
		
		glColor4f(shade.r * light[3], shade.g * light[3], shade.b * light[3], shade.a);
		glTexCoord2f(tile.s1, tile.t0);
		glVertex3f(x+v[3].x, y+v[3].y, z+v[3].z);
	}
	
	public class Block {
		
	}
	

	public class Heightmap implements Drawable3f {
		public SourceTile top, side;
		public final Vec3f center = new Vec3f();
		public final Vec2f size = new Vec2f();
		public Vec3f[][] map;
		public int w, h;
		public void generate(float[][] heights) {
			h = heights.length;
			w = heights[0].length;
			
			map = new Vec3f[h][w];
			
			for (int z = 0; z < h; z++) {
				for (int x = 0; x < w; x++) {
					map[z][x] = new Vec3f(x, heights[z][x], z);
				}
			}
		}
		public void draw(DrawMode mode, Scene3f scene) {
			float width = (w - 1) * size.x;
			float height = (h - 1) * size.y;
			float ox = center.x - (width * 0.5f);
			float oy = center.z - (height * 0.5f);
			
			Texture.on();
			glPushMatrix();
			glTranslatef(ox, center.y, oy);
			glBegin(GL_QUADS);
//			Vec3f normal = new Vec3f();
//			int max = pointsLong - 1;
//			for (int z = 0; z < max; z++) {
//				for (int x = 0; x < max; x++) {
//					normal.cross(map[z][x], map[z][x+1], map[z+1][x+1]);
//					glNormal3f(normal.x, normal.y, normal.z);
//					map[z][x].bind();
//					map[z][x+1].bind();
//					map[z+1][x+1].bind();
//					map[z+1][x].bind();
//				}
//			}
			side.bind();
			glColor3f(1, 1, 1);
//			glColor3f(0.1f, 0.1f, 0.05f);
			for (int z = 0; z < h; z++) {
				for (int x = 0; x < w; x++) {
					edge(x, z, x - 1, z);
					edge(x, z, x + 1, z);
					edge(x, z, x, z + 1);
					edge(x, z, x, z - 1);
				}
			}
			top.bind();
			glColor3f(0, 0.3f, 0);
			Vec3f v = null;
			for (int z = 0; z < h; z++) {
				for (int x = 0; x < w; x++) {
					v = map[z][x];
					glNormal3f(0, 1, 0);
					glTexCoord2f(top.s0, top.t0);
					glVertex3f(v.x-0.5f, v.y, v.z-0.5f);
					glTexCoord2f(top.s0, top.t1);
					glVertex3f(v.x-0.5f, v.y, v.z+0.5f);
					glTexCoord2f(top.s1, top.t1);
					glVertex3f(v.x+0.5f, v.y, v.z+0.5f);
					glTexCoord2f(top.s1, top.t0);
					glVertex3f(v.x+0.5f, v.y, v.z-0.5f);
				}
			}
			glEnd();
			glPopMatrix();
			Texture.off();
		}
		private void edge(int x0, int z0, int x1, int z1) {
			if (exists(x0, z0) && exists(x1, z1)) {
				float y0 = map[z0][x0].y;
				float y1 = map[z1][x1].y;
				float sx = Integer.signum(x0 - x1) * 0.5f;
				float sz = Integer.signum(z0 - z1) * 0.5f;
				float dx = (x0 + x1) * 0.5f;
				float dz = (z0 + z1) * 0.5f;

				glNormal3f(dx-x0, 0, dz-z0);
				
				glColor3f(1, 1, 1);
				glTexCoord2f(side.s0+0.001f, side.t1-0.001f);
				glVertex3f(dx+sz, y1, dz-sx);
				glTexCoord2f(side.s1-0.001f, side.t1-0.001f);
				glVertex3f(dx-sz, y1, dz+sx);

				glColor3f(0.5f, 0.5f, 0.5f);
				glTexCoord2f(side.s1-0.001f, side.t0+0.001f);
				glVertex3f(dx-sz, y0, dz+sx);
				glTexCoord2f(side.s0+0.001f, side.t0+0.001f);
				glVertex3f(dx+sz, y0, dz-sx);

			}
		}
		private boolean exists(int x, int z) {
			return !(x < 0 || x >= w || z < 0 || z >= h);
		}
	}

}
