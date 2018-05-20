package wars;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import org.lwjgl.util.vector.Vector3f;

import axe.TimeUnit;
import axe.input.KeyEvent;
import axe.input.KeyListener;
import axe.input.MouseEvent;
import axe.input.MouseListener;
import axe.texture.Source;
import axe.texture.Texture;
import axe.texture.TextureLoader;
import axe.texture.TextureWrap;
import axe.util.Screen;
import axe.util.ScreenListener;
import axe.util.Vector;


public class Game implements ScreenListener, MouseListener, KeyListener
{
	public static void main(String[] args) {

		Screen screen = new Screen(640, 480, false, 0);

		Game game = new Game(screen);

		screen.addScreenListener(game);
		screen.addKeyListener(game);
		screen.addMouseListener(game);

		screen.setup3D();
		screen.start();
	}

	public abstract class Node {
		private Node prev, next, child, parent;
		public void remove() {
			Node current = child;
			while (current != null) {
				current.remove();
				current = current.next;
			}
			if (prev == null && parent != null) {
				parent.child = this;
			}
			else {
				prev.next = next;
			}
		}
		protected abstract void onRemove();
	}
	
	public class Tile {
		public Source[] sources;
		public Texture texture;
		public Tile(Texture texture, int left, int top, int right, int bottom) {
			this.texture = texture;
			this.sources = new Source[] {
					texture.getSource(left, top),
					texture.getSource(right, top),
					texture.getSource(right, bottom),
					texture.getSource(left, bottom)
			};
		}
		public void draw(Rect r) {
			texture.bind();
			glBegin(GL_QUADS); {
				sources[0].bind();
				glVertex2f(r.left, r.top);
				sources[1].bind();
				glVertex2f(r.right, r.top);
				sources[2].bind();
				glVertex2f(r.right, r.bottom);
				sources[3].bind();
				glVertex2f(r.left, r.bottom);
			} glEnd();
		}
	}

	public class Rect {
		public float left, top, right, bottom;
		public Rect(float x, float y, float width, float height) {
			this.left = x;
			this.top = y;
			this.right = x + width;
			this.bottom = y - height;
		}
	}

	public class TileSheet {
		public Tile[] tiles;
		public Texture texture;
		public TileSheet(Texture texture, int offsetx, int offsety, int columns, int tileCount, int width, int height) {
			this.texture = texture;
			this.tiles = new Tile[tileCount];
			for (int i = 0; i < tileCount; i++) {
				int xx = offsetx + (i % columns) * width;
				int yy = offsety + (i / columns) * height;
				this.tiles[i] = new Tile(texture, xx, yy, xx + width, yy + height);
			}
		}
	}

	public class Vertex {
		private Vector3f vector;
		private Source source;
		public Vertex(Source source, float x, float y, float z, Vector3f offset) {
			this.source = source;
			this.vector = new Vector3f(x + offset.x, y + offset.y, z + offset.z);
		}
		public void bind() {
			glTexCoord2f(source.s, source.t);
			glVertex3f(vector.x, vector.y, vector.z);
		}
	}

	public class Quad {
		public Vertex[] vertex;
		public Tile tile;
		public Quad(Tile tile, Vector3f up, Vector3f side, Vector3f location) {
			this.tile = tile;
			this.vertex = new Vertex[] {
					new Vertex(tile.sources[0], up.x - side.x, up.y + side.y, up.z + side.z, location),
					new Vertex(tile.sources[1], up.x + side.x, up.y - side.y, up.z + side.z, location),
					new Vertex(tile.sources[2], up.x + side.x, up.y - side.y, up.z + side.z, location),
					new Vertex(tile.sources[3], up.x - side.x, up.y + side.y, up.z + side.z, location),
			};
		}
		public void draw() {
			tile.texture.bind();
			glBegin(GL_QUADS); {
				vertex[0].bind();
				vertex[1].bind();
				vertex[2].bind();
				vertex[3].bind();
			} glEnd();
		}
	}

	public class Scalarf {
		public float v;
	}
	
	public class Camera {
		float x, y, z;
		float yaw, pitch, roll;
		float distance;
		public void bind() {
			pitch = mod(pitch, 360);
			yaw = mod(yaw, 360);
			glLoadIdentity();
//			glTranslatef(0, 0, distance);
			glRotatef(360 - pitch + 180, 1, 0, 0);
			glRotatef(yaw, 0, -1, 0);
			glTranslatef(x, y, z);
//			glTranslatef(distance, distance, distance);
//			Vector3f v = new Vector3f();
//			v.x = (float)(Math.cos(yaw)*Math.sin(pitch)*Math.sin(roll) - Math.sin(yaw)*Math.cos(roll));
//			v.y = (float)(Math.sin(yaw)*Math.sin(pitch)*Math.sin(roll) - Math.cos(yaw)*Math.cos(roll));
//			v.z = (float)(Math.cos(pitch)*Math.sin(roll));
//			v.x = (float)(Math.cos(pitch) * Math.sin(yaw));
//			v.y = (float)(Math.sin(pitch));
//			v.z = (float)(Math.cos(pitch) * Math.cos(yaw));
			
//			gluLookAt(x - distance*v.x, y - distance*v.y, z - distance*v.z, x, y, z, v.x, v.y, v.z);
		}
		public void forwards(float distance) {
			x -= Math.sin(Math.toRadians(yaw)) * distance;
			z -= Math.cos(Math.toRadians(yaw)) * distance;
		}
		public void backwards(float distance) {
			forwards(-distance);
		}
		public void right(float distance) {
			x -= Math.sin(Math.toRadians(yaw + 90f)) * distance;
			z -= Math.cos(Math.toRadians(yaw + 90f)) * distance;
		}
		public void left(float distance) {
			right(-distance);
		}
	}
	
	private float mod(float x, float m) {
		while (x >= m) {
			x -= m;
		}
		while (x < 0) {
			x += m;
		}
		return x;
	}
	
	private final Screen screen;
	private final Vector mouse;
	private Camera camera;
	private Texture numberTexture;
	private Texture grassTexture;
	private TileSheet numberTiles;
	private TileSheet grassTiles;
//	private Quad quad;
	private float angle;

	public Game(Screen screen) {
		this.screen = screen;
		this.mouse = new Vector(320, 240);
	}

	@Override
	public void onStart() {
		TextureLoader loader = new TextureLoader();
		try {
			numberTexture = loader.getTexture("wars/assets/numbers.png", TextureWrap.ToEdge);
			grassTexture = loader.getTexture("wars/assets/ground.png", TextureWrap.ToEdge);
		} catch (IOException e) {
			e.printStackTrace();
		}
		numberTiles = new TileSheet(numberTexture, 0, 0, 10, 10, 22, 32);
		grassTiles = new TileSheet(grassTexture, 0, 0, 2, 4, 512, 512);
//		quad = new Quad(grassTiles.tiles[0], new Vector3f(1,0,0), new Vector3f(0,1,0), new Vector3f());
		
		camera = new Camera();
		camera.pitch = 340f;
		camera.yaw = 0f;
		camera.distance = 0f;
		camera.y = 5f;
		camera.z = 20f;
		camera.x = 0f;
	}

	@Override
	public void onDraw() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glColor3f(1f, 1f, 1f);
		screen.setup2D();
		//		grassTiles.tiles[0].draw(new Rect(0, 480, 80, 80));
		for (int i = 0; i < fps.length; i++) {
			numberTiles.tiles[fps[i] - '0'].draw( new Rect(i * 20 + 10, 460, 22, 32) );
		}

		screen.setup3D();

		camera.bind();
//				glTranslatef(location.x, location.y, location.z);
//		gluLookAt(location.x, location.y, location.z, 0, 0, 0, 0, 1, 0);

		glEnable(GL_TEXTURE_2D);
		glPushMatrix(); {
			Tile tile = grassTiles.tiles[0];
			tile.texture.bind();
			glScalef(10, 1, 10);
			glBegin(GL_QUADS); {
				tile.sources[0].bind();
				glVertex3f(-1, 0, 1);
				tile.sources[1].bind();
				glVertex3f(1, 0, 1);
				tile.sources[2].bind();
				glVertex3f(1, 0, -1);
				tile.sources[3].bind();
				glVertex3f(-1, 0, -1);
			} glEnd();
		} glPopMatrix();

		glPushMatrix(); {
			cubeTextured();
		} glPopMatrix();
		glPushMatrix(); {
			cube();
		} glPopMatrix();

		//		glEnable(GL_TEXTURE_2D);
		//		glLoadIdentity(); 
		//		glTranslatef(-1.5f,0.0f,-6.0f);             // Move Right 1.5 Units And Into The Screen 6.0
		//		glRotatef(angle,0f,1f,1f);           // Rotate The Quad On The X axis ( NEW )
		//		glColor3f(1f,1f,1f); 
		//		quad.draw();
	}

	float time = 0.0f;
	float update = 0.5f;
	int frames = 0;
	char[] fps = {'0'};

	@Override
	public void onUpdate(TimeUnit elapsed) {
		time += elapsed.seconds;
		frames++;
		if (time >= update) {
			fps = String.valueOf((int)(frames / time)).toCharArray();
			time -= update;
			frames = 0;
		}

		angle += 60 * elapsed.seconds;

		camera.pitch += Mouse.getDY() * elapsed.seconds * 20;
		camera.yaw += Mouse.getDX() * elapsed.seconds * 20;
//		
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			camera.right(elapsed.seconds * 20);
//			camera.x -= elapsed.seconds * 20;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			camera.left(elapsed.seconds * 20);
//			camera.x += elapsed.seconds * 20;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			camera.forwards(elapsed.seconds * 20);
//			camera.z += elapsed.seconds * 20;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			camera.backwards(elapsed.seconds * 20);
//			camera.z -= elapsed.seconds * 20;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			System.out.format("x: %.2f, y: %.2f, z: %.2f\n", camera.x, camera.y, camera.z);
			System.out.format("yaw: %.2f, pitch, %.2f\n", camera.yaw, camera.pitch);
		}
	}

	private void cube() {
		glDisable(GL_TEXTURE_2D);
		//		glLoadIdentity();                          // Reset The Current Modelview Matrix
		glTranslatef(1.5f,0.0f,-7.0f);             // Move Right 1.5 Units And Into The Screen 6.0
		glRotatef(angle,1.0f,1.0f,1.0f);           // Rotate The Quad On The X axis ( NEW )
		glColor3f(0.5f,0.5f,1.0f);                 // Set The Color To Blue One Time Only
		glBegin(GL_QUADS); {                       // Draw A Quad 
			glColor3f(0.0f,1.0f,0.0f);             // Set The Color To Green
			glVertex3f( 1.0f, 1.0f,-1.0f);         // Top Right Of The Quad (Top)
			glVertex3f(-1.0f, 1.0f,-1.0f);         // Top Left Of The Quad (Top)
			glVertex3f(-1.0f, 1.0f, 1.0f);         // Bottom Left Of The Quad (Top)
			glVertex3f( 1.0f, 1.0f, 1.0f);         // Bottom Right Of The Quad (Top)
			glColor3f(1.0f,0.5f,0.0f);             // Set The Color To Orange
			glVertex3f( 1.0f,-1.0f, 1.0f);         // Top Right Of The Quad (Bottom)
			glVertex3f(-1.0f,-1.0f, 1.0f);         // Top Left Of The Quad (Bottom)
			glVertex3f(-1.0f,-1.0f,-1.0f);         // Bottom Left Of The Quad (Bottom)
			glVertex3f( 1.0f,-1.0f,-1.0f);         // Bottom Right Of The Quad (Bottom)
			glColor3f(1.0f,0.0f,0.0f);             // Set The Color To Red
			glVertex3f( 1.0f, 1.0f, 1.0f);         // Top Right Of The Quad (Front)
			glVertex3f(-1.0f, 1.0f, 1.0f);         // Top Left Of The Quad (Front)
			glVertex3f(-1.0f,-1.0f, 1.0f);         // Bottom Left Of The Quad (Front)
			glVertex3f( 1.0f,-1.0f, 1.0f);         // Bottom Right Of The Quad (Front)
			glColor3f(1.0f,1.0f,0.0f);             // Set The Color To Yellow
			glVertex3f( 1.0f,-1.0f,-1.0f);         // Bottom Left Of The Quad (Back)
			glVertex3f(-1.0f,-1.0f,-1.0f);         // Bottom Right Of The Quad (Back)
			glVertex3f(-1.0f, 1.0f,-1.0f);         // Top Right Of The Quad (Back)
			glVertex3f( 1.0f, 1.0f,-1.0f);         // Top Left Of The Quad (Back)
			glColor3f(0.0f,0.0f,1.0f);             // Set The Color To Blue
			glVertex3f(-1.0f, 1.0f, 1.0f);         // Top Right Of The Quad (Left)
			glVertex3f(-1.0f, 1.0f,-1.0f);         // Top Left Of The Quad (Left)
			glVertex3f(-1.0f,-1.0f,-1.0f);         // Bottom Left Of The Quad (Left)
			glVertex3f(-1.0f,-1.0f, 1.0f);         // Bottom Right Of The Quad (Left)
			glColor3f(1.0f,0.0f,1.0f);             // Set The Color To Violet
			glVertex3f( 1.0f, 1.0f,-1.0f);         // Top Right Of The Quad (Right)
			glVertex3f( 1.0f, 1.0f, 1.0f);         // Top Left Of The Quad (Right)
			glVertex3f( 1.0f,-1.0f, 1.0f);         // Bottom Left Of The Quad (Right)
			glVertex3f( 1.0f,-1.0f,-1.0f);         // Bottom Right Of The Quad (Right)
		} glEnd();                                 // Done Drawing The Quad
	}

	private void cubeTextured() {
		glEnable(GL_TEXTURE_2D);
		Tile tile = grassTiles.tiles[0];
		tile.texture.bind();
		//		glLoadIdentity();                          // Reset The Current Modelview Matrix
		glTranslatef(-1.5f,0.0f,-7.0f);             // Move Right 1.5 Units And Into The Screen 6.0
		glRotatef(angle,1.0f,1.0f,1.0f);           // Rotate The Quad On The X axis ( NEW )
		glColor3f(1f,1f,1f);                 // Set The Color To Blue One Time Only
		glBegin(GL_QUADS); {                       // Draw A Quad 
			//			glColor3f(0.0f,1.0f,0.0f);             // Set The Color To Green
			tile.sources[1].bind();
			glVertex3f( 1.0f, 1.0f,-1.0f);         // Top Right Of The Quad (Top)
			tile.sources[0].bind();
			glVertex3f(-1.0f, 1.0f,-1.0f);         // Top Left Of The Quad (Top)
			tile.sources[3].bind();
			glVertex3f(-1.0f, 1.0f, 1.0f);         // Bottom Left Of The Quad (Top)
			tile.sources[2].bind();
			glVertex3f( 1.0f, 1.0f, 1.0f);         // Bottom Right Of The Quad (Top)
			//			glColor3f(1.0f,0.5f,0.0f);             // Set The Color To Orange
			tile.sources[1].bind();
			glVertex3f( 1.0f,-1.0f, 1.0f);         // Top Right Of The Quad (Bottom)
			tile.sources[0].bind();
			glVertex3f(-1.0f,-1.0f, 1.0f);         // Top Left Of The Quad (Bottom)
			tile.sources[3].bind();
			glVertex3f(-1.0f,-1.0f,-1.0f);         // Bottom Left Of The Quad (Bottom)
			tile.sources[2].bind();
			glVertex3f( 1.0f,-1.0f,-1.0f);         // Bottom Right Of The Quad (Bottom)
			//			glColor3f(1.0f,0.0f,0.0f);             // Set The Color To Red
			tile.sources[1].bind();
			glVertex3f( 1.0f, 1.0f, 1.0f);         // Top Right Of The Quad (Front)
			tile.sources[0].bind();
			glVertex3f(-1.0f, 1.0f, 1.0f);         // Top Left Of The Quad (Front)
			tile.sources[3].bind();
			glVertex3f(-1.0f,-1.0f, 1.0f);         // Bottom Left Of The Quad (Front)
			tile.sources[2].bind();
			glVertex3f( 1.0f,-1.0f, 1.0f);         // Bottom Right Of The Quad (Front)
			//			glColor3f(1.0f,1.0f,0.0f);             // Set The Color To Yellow
			tile.sources[1].bind();
			glVertex3f( 1.0f,-1.0f,-1.0f);         // Bottom Left Of The Quad (Back)
			tile.sources[0].bind();
			glVertex3f(-1.0f,-1.0f,-1.0f);         // Bottom Right Of The Quad (Back)
			tile.sources[3].bind();
			glVertex3f(-1.0f, 1.0f,-1.0f);         // Top Right Of The Quad (Back)
			tile.sources[2].bind();
			glVertex3f( 1.0f, 1.0f,-1.0f);         // Top Left Of The Quad (Back)
			//			glColor3f(0.0f,0.0f,1.0f);             // Set The Color To Blue
			tile.sources[1].bind();
			glVertex3f(-1.0f, 1.0f, 1.0f);         // Top Right Of The Quad (Left)
			tile.sources[0].bind();
			glVertex3f(-1.0f, 1.0f,-1.0f);         // Top Left Of The Quad (Left)
			tile.sources[3].bind();
			glVertex3f(-1.0f,-1.0f,-1.0f);         // Bottom Left Of The Quad (Left)
			tile.sources[2].bind();
			glVertex3f(-1.0f,-1.0f, 1.0f);         // Bottom Right Of The Quad (Left)
			//			glColor3f(1.0f,0.0f,1.0f);             // Set The Color To Violet
			tile.sources[1].bind();
			glVertex3f( 1.0f, 1.0f,-1.0f);         // Top Right Of The Quad (Right)
			tile.sources[0].bind();
			glVertex3f( 1.0f, 1.0f, 1.0f);         // Top Left Of The Quad (Right)
			tile.sources[3].bind();
			glVertex3f( 1.0f,-1.0f, 1.0f);         // Bottom Left Of The Quad (Right)
			tile.sources[2].bind();
			glVertex3f( 1.0f,-1.0f,-1.0f);         // Bottom Right Of The Quad (Right)
		} glEnd();                                 // Done Drawing The Quad
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
