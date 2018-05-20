package axe.g2f.sprite;

import static org.lwjgl.opengl.GL11.*;

import axe.DrawMode;
import axe.Scalarf;
import axe.g2f.Bound2f;
import axe.g2f.Scene2f;
import axe.g2f.Vec2f;
import axe.texture.Texture;

public class SimpleSprite extends Sprite 
{

	private final Scalarf angle = new Scalarf();
	private final Vec2f anchor = new Vec2f(0.5f, 0.5f);

	public void draw(DrawMode mode, Scene2f scene) 
	{
		if (angle.v == 0f) {
			super.draw(mode, scene);
		}
		else {
			float cos = angle.cos();
			float sin = angle.sin();
			float l = anchor.x * size.x;
			float r = size.x - l;
			float t = anchor.y * size.y;
			float b = size.y - t;
			float lcos = l * cos, lsin = l * sin;
			float rcos = r * cos, rsin = r * sin;
			float tcos = t * cos, tsin = t * sin;
			float bcos = b * cos, bsin = b * sin;
			
			Texture.on();
			tile.bind();
			glBegin(GL_QUADS); {
				glTexCoord2f(tile.s0, tile.t0);
				glVertex2f(actual.x - lcos - tsin, actual.y - tcos + lsin);
				glTexCoord2f(tile.s0, tile.t1);
				glVertex2f(actual.x - lcos + bsin, actual.y + bcos + lsin);
				glTexCoord2f(tile.s1, tile.t1);
				glVertex2f(actual.x + rcos + bsin, actual.y + bcos - rsin);
				glTexCoord2f(tile.s1, tile.t0);
				glVertex2f(actual.x + rcos - tsin, actual.y - tcos - rsin);
			} glEnd();
		}
	}
	
	public void bounds(Bound2f b) {
		float cx = actual.x + (size.x * 0.5f);
		float cy = actual.y + (size.y * 0.5f);
		b.quad(cx, cy, size.x, size.y, angle.v);
	}
	
	public Scalarf angle() {
		return angle;
	}
	
	public void angle(float radians) {
		angle.set(radians);
	}
	
	public Vec2f anchor() {
		return anchor;
	}
	
	public void anchor(float x, float y) {
		anchor.set(x, y);
	}
	
}
