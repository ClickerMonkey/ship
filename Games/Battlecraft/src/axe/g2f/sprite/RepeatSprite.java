package axe.g2f.sprite;

import static org.lwjgl.opengl.GL11.*;

import axe.DrawMode;
import axe.Repeat;
import axe.g2f.Bound2f;
import axe.g2f.Scene2f;
import axe.g2f.Vec2f;
import axe.texture.SourceTile;

public class RepeatSprite extends Sprite
{
	
	protected Repeat repeat = Repeat.None;
	protected final Vec2f offset = new Vec2f();
	
	public RepeatSprite(float ox, float oy, float w, float h, SourceTile tile) 
	{
		this.position.set(ox, oy);
		this.size.set(w, h);
		this.tile = tile;
	}
	
	public void draw(DrawMode mode, Scene2f scene) 
	{
		final Bound2f cam = scene.camera().bounds();
		float sx, ex, sy, ey;
		sx = ex = actual.x;
		sy = ey = actual.y;

		if (repeat.x) {
			sx = actual.x - (float)Math.ceil((actual.x - cam.l) / size.x) * size.x;
			ex = sx + (float)Math.floor((cam.width() + (cam.l - sx)) / size.x) * size.x;
		}
		if (repeat.y) {
			sy = actual.y - (float)Math.ceil((actual.y - cam.t) / size.y) * size.y;
			ey = sy + (float)Math.ceil((cam.height() + (cam.t - sy)) / size.y) * size.y;
		}

		if (mode == DrawMode.Normal) {
			tile.bind();	
		}
		glBegin(GL_QUADS); 
		for (float y = sy; y <= ey; y += size.y) {
			for (float x = sx; x <= ex; x += size.x) {
				glTexCoord2f(tile.s0, tile.t0);
				glVertex2f(x, y);
				glTexCoord2f(tile.s0, tile.t1);
				glVertex2f(x, y + size.y);
				glTexCoord2f(tile.s1, tile.t1);
				glVertex2f(x + size.x, y + size.y);
				glTexCoord2f(tile.s1, tile.t0);
				glVertex2f(x + size.x, y);
			}
		}
		glEnd();
	}
	
	public void repeat(Repeat repeat) {
		this.repeat = repeat;
	}
	
	public Repeat repeat() {
		return repeat;
	}
	
	public void offset(float x, float y) {
		offset.set(x, y);
	}
	
	public Vec2f offset() {
		return offset;
	}
	
}
