package axe.g2f.tile;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;
import axe.DrawMode;
import axe.TimeUnit;
import axe.g2f.Bound2f;
import axe.g2f.Entity2f;
import axe.g2f.Scene2f;
import axe.g2f.Vec2f;
import axe.texture.SourceTile;
import axe.texture.Texture;

public class Tilemap implements Entity2f 
{

	private final Vec2f position = new Vec2f();
	private final Vec2f respect = new Vec2f();
	private final Vec2f actual = new Vec2f();
	private final Vec2f size = new Vec2f();
	private final Bound2f bounds = new Bound2f();
	private final int width, height;
	private final short[][] map;
	private final Tileset set;
	
	public Tilemap(int width, int height, Tileset set) {
		this.width = width;
		this.height = height;
		this.set = set;
		this.map = new short[height][width];
	}
	
	public void draw(DrawMode mode, Scene2f scene) 
	{
		Bound2f camera = scene.camera().bounds();
		
		if (camera.intersects(bounds)) 
		{
			float tw = size.x;
			float th = size.y;
			float ox = (-actual.x + camera.l);
			float oy = (-actual.y + camera.t);
			
			int sx = (int)Math.floor(ox / tw);
			int ex = (int)Math.ceil((ox + camera.width()) / tw);
			int sy = (int)Math.floor(oy / th);
			int ey = (int)Math.ceil((oy + camera.height()) / th);
			
			if (sx < 0) sx = 0;
			if (ex >= width) ex = width - 1;
			if (sy < 0) sy = 0;
			if (ey >= height) ey = height - 1;
			
			Texture.on();
			Bound2f tile = new Bound2f();
			glBegin(GL_QUADS); {
				for (int y = sy; y <= ey; y++) {
					for (int x = sx; x <= ex; x++) {
						if (map[y][x] >= 0) {
							tile.rect(x * tw, y * th, tw, th);
							SourceTile source = set.get(map[y][x]);
							source.bind();
							glTexCoord2f(source.s0, source.t0);
							glVertex2f(actual.x + tile.l, actual.y + tile.t);
							glTexCoord2f(source.s0, source.t1);
							glVertex2f(actual.x + tile.l, actual.y + tile.b);
							glTexCoord2f(source.s1, source.t1);
							glVertex2f(actual.x + tile.r, actual.y + tile.b);
							glTexCoord2f(source.s1, source.t0);
							glVertex2f(actual.x + tile.r, actual.y + tile.t);
						}
					}
				}
			} glEnd();
		}
	}
	
	public void update(TimeUnit elapsed, Scene2f scene) 
	{
		final Bound2f cam = scene.camera().bounds();
		
		actual.x = (cam.l * respect.x) + position.x;
		actual.y = (cam.t * respect.y) + position.y;
		
		bounds.rect(actual.x, actual.y, width * size.x, height * size.y);
	}
	
	public void position(float x, float y) {
		position.set(x, y);
	}
	
	public Vec2f position() {
		return position;
	}
	
	public void respect(float x, float y) {
		respect.set(x, y);
	}
	
	public Vec2f respect() {
		return respect;
	}
	
	public Vec2f actual() {
		return actual;
	}
	
	public Bound2f bounds() {
		return bounds;
	}
	
	public void size(float w, float h) {
		size.set(w, h);
	}
	
	public Vec2f size() {
		return size;
	}
	
	public Tileset set() {
		return set;
	}
	
	public short[][] data() {
		return map;
	}
	
}
