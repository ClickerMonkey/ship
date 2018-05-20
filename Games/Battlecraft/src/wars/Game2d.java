package wars;

import static org.lwjgl.opengl.GL11.*;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import axe.Blend;
import axe.DrawMode;
import axe.Order;
import axe.Scalarf;
import axe.TimeUnit;
import axe.g2f.Bound2f;
import axe.g2f.Camera2f;
import axe.g2f.Drawable2f;
import axe.g2f.Entity2f;
import axe.g2f.Rect2i;
import axe.g2f.Scene2f;
import axe.g2f.Updatable2f;
import axe.g2f.Vec2f;
import axe.g2f.tile.Tilesheet;
import axe.g3f.Vec3f;
import axe.pick.PickList;
import axe.texture.SourceTile;
import axe.texture.Texture;
import axe.texture.TextureLoader;
import axe.texture.TextureWrap;
import axe.util.Node;
import axe.util.NodeList;
import axe.util.NodeTraverse;
import axe.util.Screen;
import axe.util.ScreenListener;
import axe.util.Vector;


public class Game2d implements ScreenListener
{
	
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	private static final int CENTER_X = WIDTH >> 1;
	private static final int CENTER_Y = HEIGHT >> 1;
	
	public static void main(String[] args) {
		Screen screen = new Screen(WIDTH, HEIGHT, false, 0);
		Game2d game = new Game2d(screen);
		screen.addScreenListener(game);
		screen.start();
	}
	
	public Game2d(Screen screen) {
		this.screen = screen;
		this.mouse = new Vector(CENTER_X, CENTER_Y);
		this.scene = new Scene2f(new Rect2i(0, 0, WIDTH, HEIGHT));
	}
	

	private final Screen screen;
	private final Scene2f scene;
	private final Vector mouse;
	private Tilesheet sheet;
	private Texture sheetTexture;
	private Tilesheet numbers;
	private Texture numberTexture;
	private Heightmap heightMap;
	private NodeList<Updatable2f> updates = new NodeList<Updatable2f>();
	
	@Override
	public void onStart() 
	{
		final Camera2f cam = scene.camera();
		
		scene.init();
		
		updates.order(Order.FrontToBack);
		updates.add(cam);
		
		Blend.on();
		Blend.Blend.bind();
		
		glClearColor(0f, 0f, 0f, 1f);
		
		try {
			TextureLoader loader = new TextureLoader();
			sheetTexture = loader.getTexture("wars/assets/terrain.png", TextureWrap.ToEdge);
			numberTexture = loader.getTexture("wars/assets/numbers.png", TextureWrap.ToEdge);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		sheet = new Tilesheet(sheetTexture, 0, 0, 64, 64, 16, 256);
		numbers = new Tilesheet(numberTexture, 0, 0, 22, 32, 10, 10);
		
		heightMap = new Heightmap();
		heightMap.person.set(cam.center().x, cam.center().y, 4f);
		heightMap.top = sheet.get(0);
		heightMap.side = sheet.get(0);
		updates.add(heightMap);
		
		createMap(32, 32);
	}

	final Random rnd = new Random();
	
	private void createMap(int w, int h) {
		int start = rnd.nextInt(4);
		float[] prob = {0.85f, 0.10f, 0.02f, 0.010f, 0.008f, 0.005f, 0.003f, 0.002f, 0.001f, 0.001f};
		int[][] hm = new int[h][w];
		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
		hm[0][0] = start;
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				if (x == 0 && y == 0) {
					continue;
				}
				float p = rnd.nextFloat();
				int change = 0;
				while (p > prob[change]) {
					p -= prob[change++];
				}
				int dir = rnd.nextInt(2) * 2 - 1;
				
				int ref = 0, refc = 0;
				if (x > 0) {
					ref += hm[y][x-1];
					refc++;
				}
				if (y > 0) {
					ref += hm[y-1][x];
					refc++;
				}
				
				hm[y][x] = change * dir + (ref / refc);

				
				min = Math.min(min, hm[y][x]);
				max = Math.max(max, hm[y][x]);
			}
		}
		// normalize
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				hm[y][x] -= min;
			}
		}
		max -= min;
		min = 0;
		
		heightMap.min = max;
		heightMap.width = w;
		heightMap.height = h;
		heightMap.heights = blur(hm, w, h);

		int px = Math.max(0, Math.min(heightMap.tilex(heightMap.person.x), w - 1));
		int py = Math.max(0, Math.min(heightMap.tiley(heightMap.person.y), h - 1));
		heightMap.person.z = heightMap.heights[py][px];
	}
	
	private int[][] blur(int[][] hm, int w, int h) {
		int[][] newhm = new int[h][w];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int sum = hm[y][x], total = 1;
				if (exists(x-1,y,w,h)) {
					sum += hm[y][x-1]; total++;
				}
				if (exists(x+1,y,w,h)) {
					sum += hm[y][x+1]; total++;
				}
				if (exists(x,y-1,w,h)) {
					sum += hm[y-1][x]; total++;
				}
				if (exists(x,y+1,w,h)) {
					sum += hm[y+1][x]; total++;
				}
				if (exists(x-1,y-1,w,h)) {
					sum += hm[y-1][x-1]; total++;
				}
				if (exists(x+1,y-1,w,h)) {
					sum += hm[y-1][x+1]; total++;
				}
				if (exists(x-1,y+1,w,h)) {
					sum += hm[y+1][x-1]; total++;
				}
				if (exists(x+1,y+1,w,h)) {
					sum += hm[y+1][x+1]; total++;
				}
				newhm[y][x] = sum / total;
			}
		}
		return newhm;
	}
	
	public boolean exists(int x, int y, int w, int h) {
		return !(x < 0 || x >= w || y < 0 || y >= h);
	}

	@Override
	public void onDraw() {
		glClear(GL_COLOR_BUFFER_BIT);
		glColor4f(1f, 1f, 1f, 1f);
		
		scene.start(); {
			heightMap.draw(DrawMode.Normal, scene);

			Texture.off();
			glPushMatrix(); {
				
				glColor4f(1f, 1f, 1f, 1f);
				glTranslatef(heightMap.person.x, heightMap.person.y, 0);
				glScalef(15f, 15f, 0f);
				glBegin(GL_QUADS); {
					glVertex2f(-0.5f, -0.5f);
					glVertex2f(-0.5f, 0.5f);
					glVertex2f(0.5f, 0.5f);
					glVertex2f(0.5f, -0.5f);
				} glEnd();
			} glPopMatrix();
			
		} scene.end();

		glPushMatrix(); {
			glTranslatef(mouse.x, mouse.y, 0);
			glScalef(20f, 20f, 0f);
			glColor4f(1f, 1f, 1f, 1f);
			glBegin(GL_LINES); {
				glVertex2f(-0.5f, 0);
				glVertex2f(0.5f, 0);
				glVertex2f(0, 0.5f);
				glVertex2f(0, -0.5f);
			} glEnd();
		} glPopMatrix();
		
		numbers.bind();
		glBegin(GL_QUADS); {
			for (int i = 0; i < fps.length; i++) {
					SourceTile number = numbers.get(fps[i] - '0');
					glTexCoord2f(number.s0, number.t0);
					glVertex2f(i * 20 + 10, 10);
					glTexCoord2f(number.s0, number.t1);
					glVertex2f(i * 20 + 10, 42);
					glTexCoord2f(number.s1, number.t1);
					glVertex2f(i * 20 + 32, 42);
					glTexCoord2f(number.s1, number.t0);
					glVertex2f(i * 20 + 32, 10);
			}
		} glEnd();
	}

	float time = 0.0f;
	float update = 0.5f;
	int frames = 0;
	char[] fps = {'0'};
	
	@Override
	public void onUpdate(final TimeUnit elapsed) 
	{
		time += elapsed.seconds;
		frames++;
		if (time >= update) {
			fps = String.valueOf((int)(frames / time)).toCharArray();
			time -= update;
			frames = 0;
		}
		
		mouse.add(Mouse.getDX(), -Mouse.getDY());
		mouse.x = Scalarf.clamp(mouse.x, 0, WIDTH);
		mouse.y = Scalarf.clamp(mouse.y, 0, HEIGHT);	
		
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			screen.close();
		}
		
		final Camera2f cam = scene.camera();
		
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			cam.roll().sub(elapsed.seconds * 3);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			cam.roll().add(elapsed.seconds * 3);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			cam.roll().set(0);
		}
		
		float speed = 200 * elapsed.seconds;
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			cam.center().add(cam.up(), -speed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			cam.center().add(cam.up(), speed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			cam.center().add(cam.right(), -speed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			cam.center().add(cam.right(), speed);
		}
		
		heightMap.person.x = cam.center().x;
		heightMap.person.y = cam.center().y;
		
		updates.traverse(new NodeTraverse<Updatable2f>() {
			public void onTraverse(Node<Updatable2f> node, Updatable2f x) {
				x.update(elapsed, scene);
			}
		});
		
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			cam.zoom(1 + elapsed.seconds);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			cam.zoom(1 - elapsed.seconds);
		}
		
		regenTime += elapsed.seconds;
		if (regenTime >= 1f && Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			createMap(64, 64);
			regenTime = 0f;
		}
		
		/*
		removeTime += elapsed.seconds;
		if (removeTime >= 0.25f) {
			int dir = 0;
			if (Mouse.isButtonDown(0)) {
				dir = 1;
			}
			if (Mouse.isButtonDown(1)) {
				dir = -1;
			}
			if (dir != 0) {
				PickList<Scene2f> blocks = heightMap.getBlocks(scene);
				Picker picker = new Picker(mouse.x, mouse.y, 1, 1);
				Pick<Scene2f>[] picks = new Pick[1];
				int pickCount = picker.pick(picks, blocks, scene);
				if (pickCount > 0) {
					System.out.println(pickCount);
					HeightTile ht = (HeightTile)picks[0].obj;
					heightMap.heights[ht.y][ht.x] += dir;
				}
				removeTime = 0f;
			}
		}
		*/
	}
	float regenTime = 0f;
	float removeTime = 0f;

	@Override
	public void onClose() {
		
	}	
	
	// Used only for block selection
	public static class HeightTile implements Drawable2f {
		int x, y;
		Vec2f[] v = new Vec2f[4];
		public HeightTile(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public void draw(DrawMode mode, Scene2f scene) {
			glColor3f(1, 1, 1);
			glBegin(GL_QUADS); {
				glVertex2f(v[0].x, v[0].y);
				glVertex2f(v[1].x, v[1].y);
				glVertex2f(v[2].x, v[2].y);
				glVertex2f(v[3].x, v[3].y);
			} glEnd();
		}
	}
	
	public static class Heightmap implements Entity2f {
		private int height = -1;
		private int width = -1;
		private int min = -1;
		private int[][] heights = {{}};
		
		private final Vec2f size = new Vec2f(32, 32);
		private final Vec2f position = new Vec2f();
		private final Vec2f respect = new Vec2f();
		private final Vec2f actual = new Vec2f();
//		private final Vec2f mouse = new Vec2f();
		private final Bound2f bounds = new Bound2f();
		private SourceTile top, side;
		
		private Vec3f person = new Vec3f();
		
		public PickList<Scene2f> getBlocks(Scene2f scene)
		{
			Camera2f cam = scene.camera();
			Bound2f camera = cam.bounds();

			if (!camera.intersects(bounds)) {
				return new PickList<Scene2f>(0);
			}
			float tw = size.x;
			float th = size.y;
			float ox = (-actual.x + camera.l);
			float oy = (-actual.y + camera.t);
			
			int sx = /*0; //*/(int)Math.floor(ox / tw);
			int ex = /*width - 1; //*/(int)Math.ceil((ox + camera.width()) / tw);
			int sy = /*0; //*/(int)Math.floor(oy / th);
			int ey = /*height - 1; //*/(int)Math.ceil((oy + camera.height()) / th);
			
			if (sx < 0) sx = 0;
			if (ex >= width) ex = width - 1;
			if (sy < 0) sy = 0;
			if (ey >= height) ey = height - 1;
			
			PickList<Scene2f> picks = new PickList<Scene2f>((ey - sy + 1) * (ex - sx + 1));
			
			float l, t, r, b;
			int z;
			for (int y = sy; y <= ey; y++) {
				for (int x = sx; x <= ex; x++) {
					r = (l = x * tw) + tw;
					b = (t = y * th) + th;
					z = heights[y][x];
					
					HeightTile ht = new HeightTile(x, y);
					ht.v[0] = to(l, t, z);
					ht.v[0].add(actual);
					ht.v[1] = to(l, b, z);
					ht.v[1].add(actual);
					ht.v[2] = to(r, b, z);
					ht.v[2].add(actual);
					ht.v[3] = to(r, t, z);
					ht.v[3].add(actual);
					picks.add(ht);
				}
			}
			return picks;	
		}
		
		public void draw(DrawMode mode, Scene2f scene) 
		{
			Camera2f cam = scene.camera();
			Bound2f camera = cam.bounds();
			
			if (camera.intersects(bounds)) 
			{
				int px = Math.max(0, Math.min(tilex(person.x), width - 1));
				int py = Math.max(0, Math.min(tiley(person.y), height - 1));
				int pz = heights[py][px];
				
				float tw = size.x;
				float th = size.y;
				float ox = (-actual.x + camera.l);
				float oy = (-actual.y + camera.t);
				
				int sx = /*0; //*/(int)Math.floor(ox / tw);
				int ex = /*width - 1; //*/(int)Math.ceil((ox + camera.width()) / tw);
				int sy = /*0; //*/(int)Math.floor(oy / th);
				int ey = /*height - 1; //*/(int)Math.ceil((oy + camera.height()) / th);
				
				if (sx < 0) sx = 0;
				if (ex >= width) ex = width - 1;
				if (sy < 0) sy = 0;
				if (ey >= height) ey = height - 1;
				
				
				int h, layer = min;
				float dark = 0f, light = 0f;
				while (layer >= 0) {
					dark = Scalarf.clamp((1 - ((layer - person.z) * 0.08f)), 0, 1);
//					dark = 1f;
					if (layer < pz) {
						light = 1f - Scalarf.clamp((1 - (-(layer - person.z) * 0.08f)), 0, 1);
					}
					
					side.bind();
					for (int y = sy; y <= ey; y++) {
						for (int x = sx; x <= ex; x++) {
							if (heights[y][x] <= layer) {
								float l = x * tw;
								float r = l + tw;
								float t = y * th;
								float b = t + th;

								h = height(x + 1, y);
								if (r < person.x && h > layer) { // draw right
									h = layer + 1;
									drawQuad(side, 0.54f*dark, 0.27f*dark, 0.075f*dark, 1f, to(r, t, layer), to(r, b, layer), to(r, b, h), to(r, t, h));
								}
								h = height(x - 1, y);
								if (l > person.x && h > layer) { // draw left
									h = layer + 1;
									drawQuad(side, 0.54f*dark, 0.27f*dark, 0.075f*dark, 1f, to(l, t, h), to(l, b, h), to(l, b, layer), to(l, t, layer));
								}
								h = height(x, y + 1);
								if (b < person.y && h > layer) { // draw bottom
									h = layer + 1;
									drawQuad(side, 0.54f*dark, 0.27f*dark, 0.075f*dark, 1f, to(l, b, layer), to(l, b, h), to(r, b, h), to(r, b, layer));
								}
								h = height(x, y - 1);
								if (t > person.y && h > layer) { // draw top
									h = layer + 1;
									drawQuad(side, 0.54f*dark, 0.27f*dark, 0.075f*dark, 1f, to(r, t, layer), to(r, t, h), to(l, t, h), to(l, t, layer));
								}
							}
						}
					}
					
					top.bind();
					for (int y = sy; y <= ey; y++) {
						for (int x = sx; x <= ex; x++) {
							if (heights[y][x] == layer) {
								float l = x * tw;
								float r = l + tw;
								float t = y * th;
								float b = t + th;
								drawQuad(top, light, dark, light, 1f, to(l, t, layer), to(l, b, layer), to(r, b, layer), to(r, t, layer));
								if (layer == pz) {
									drawQuad(top, 1f, 1f, 1f, 0.1f, to(l, t, layer), to(l, b, layer), to(r, b, layer), to(r, t, layer));
								}
							}
						}
					}
					layer--;
				}
				
			}
		}
		
		private void drawQuad(SourceTile tile, float r, float g, float b, float a, Vec2f tl, Vec2f bl, Vec2f br, Vec2f tr) {
			tl.add(actual);
			bl.add(actual);
			br.add(actual);
			tr.add(actual);
			Texture.on();
			glColor4f(r, g, b, a);
			glBegin(GL_QUADS); {
				glTexCoord2f(tile.s0, tile.t0);
				glVertex2f(tl.x, tl.y);
				glTexCoord2f(tile.s0, tile.t1);
				glVertex2f(bl.x, bl.y);
				glTexCoord2f(tile.s1, tile.t1);
				glVertex2f(br.x, br.y);
				glTexCoord2f(tile.s1, tile.t0);
				glVertex2f(tr.x, tr.y);
			} glEnd();
			if (a == 1f) {
				Texture.off();
				glColor4f(r * 0.6f, g * 0.6f, b * 0.6f, a);
				glBegin(GL_LINE_LOOP); {
					glVertex2f(tl.x, tl.y);
					glVertex2f(bl.x, bl.y);
					glVertex2f(br.x, br.y);
					glVertex2f(tr.x, tr.y);
				} glEnd();	
			}
		}
		
		public Vec2f to(float x, float y, float z) {
			float dz = (person.z - z) * 0.0625f; // 0.0625
			Vec2f out = new Vec2f();
			out.x = x + (x - (person.x - actual.x)) * dz;
			out.y = y + (y - (person.y - actual.y)) * dz;
			return out;
		}
		
		public int tilex(float x) {
			return (int)Math.floor((x - actual.x) / size.x); 
		}
		
		public int tiley(float y) {
			return (int)Math.floor((y - actual.y) / size.y); 
		}
		
		public int height(int x, int y) {
			if (x < 0 || x >= width || y < 0 || y >= height) {
				return min;
			}
			return heights[y][x];
		}
		
		public void update(TimeUnit elapsed, Scene2f scene) 
		{
			final Bound2f cam = scene.camera().bounds();
			
			actual.x = (cam.l * respect.x) + position.x;
			actual.y = (cam.t * respect.y) + position.y;
			
			bounds.rect(actual.x, actual.y, width * size.x, height * size.y);
			
			int tx = Math.max(0, Math.min(tilex(person.x), width - 1));
			int ty = Math.max(0, Math.min(tiley(person.y), height - 1));
//			if (Math.abs(person.z - heights[ty][tx]) < 0.1f ) {
//				person.z = heights[ty][tx];
//			}
//			else {
				person.z += (heights[ty][tx]-person.z) * elapsed.seconds * 10;	
//			}
			
//			Display.setTitle(String.format("%.2f", person.z));
		}
		
	}

}
