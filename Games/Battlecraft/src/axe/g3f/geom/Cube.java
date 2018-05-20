package axe.g3f.geom;

import static org.lwjgl.opengl.GL11.*;
import axe.DrawMode;
import axe.g3f.Drawable3f;
import axe.g3f.Scene3f;
import axe.g3f.Vec3f;

public class Cube implements Drawable3f 
{
	public float x, y, z;
	public float w, d, h;
	public Cube() {
		x = y = z = 0f;
		w = d = h = 1f;
	}
	public void center(Vec3f c) {
		center(c.x, c.y, c.z);
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
	public void draw(DrawMode mode, Scene3f scene) {
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
