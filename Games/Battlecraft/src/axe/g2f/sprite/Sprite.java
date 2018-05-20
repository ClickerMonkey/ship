package axe.g2f.sprite;

import static org.lwjgl.opengl.GL11.*;
import axe.DrawMode;
import axe.TimeUnit;
import axe.g2f.Bound2f;
import axe.g2f.Entity2f;
import axe.g2f.Scene2f;
import axe.g2f.Vec2f;
import axe.texture.SourceTile;
import axe.texture.Texture;

public abstract class Sprite implements Entity2f
{
	
	protected final Vec2f position = new Vec2f();
	protected final Vec2f size = new Vec2f();
	protected final Vec2f respect = new Vec2f(); //0=fixed, 1=camera, 0.5=slow, 2=fast
	protected final Vec2f actual = new Vec2f(); //<auto>
	protected SourceTile tile = new SourceTile();

	public void draw(DrawMode mode, Scene2f scene) 
	{
		switch (mode) {
		case Normal:
		case Select:
			Texture.on();
			tile.bind();
			glBegin(GL_QUADS); {
				glTexCoord2f(tile.s0, tile.t0);
				glVertex2f(actual.x, actual.y);
				glTexCoord2f(tile.s0, tile.t1);
				glVertex2f(actual.x, actual.y + size.y);
				glTexCoord2f(tile.s1, tile.t1);
				glVertex2f(actual.x + size.x, actual.y + size.y);
				glTexCoord2f(tile.s1, tile.t0);
				glVertex2f(actual.x + size.x, actual.y);
			} glEnd();
			break;
		case Wired:
			glBegin(GL_LINE_LOOP); {
				glVertex2f(actual.x, actual.y);
				glVertex2f(actual.x, actual.y + size.y);
				glVertex2f(actual.x + size.x, actual.y + size.y);
				glVertex2f(actual.x + size.x, actual.y);
			} glEnd();
			break;
		}
	}
	
	public void update(TimeUnit elapsed, Scene2f scene) 
	{
		final Bound2f cam = scene.camera().bounds();
		
		actual.x = (cam.l * respect.x) + position.x;
		actual.y = (cam.t * respect.y) + position.y;
	}
	
	public void bounds(Bound2f b) {
		b.rect(actual.x, actual.y, size.x, size.y);
	}
	
	public void position(float x, float y) {
		position.set(x, y);
	}
	
	public Vec2f position() {
		return position;
	}
	
	public void respect(float rx, float ry) {
		respect.set(rx, ry);
	}
	
	public Vec2f respect() {
		return respect;
	}
	
	public void size(float w, float h) {
		size.set(w, h);
	}
	
	public Vec2f size() {
		return size;
	}
	
	public Vec2f actual() {
		return actual;
	}
	
	public void tile(SourceTile tile) {
		this.tile.set(tile);
	}
	
	public SourceTile tile() {
		return tile;
	}
	
}
