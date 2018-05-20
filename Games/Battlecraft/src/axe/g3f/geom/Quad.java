package axe.g3f.geom;

import static org.lwjgl.opengl.GL11.*;
import axe.DrawMode;
import axe.g3f.Drawable3f;
import axe.g3f.Scene3f;
import axe.g3f.Vec3f;

public class Quad implements Drawable3f 
{
	public final Vec3f center = new Vec3f();
	public final Vec3f up = new Vec3f();
	public final Vec3f right = new Vec3f();
	public final Vec3f normal = new Vec3f();
	public float scale = 0.5f;
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
	public void draw(DrawMode mode, Scene3f scene) {
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
