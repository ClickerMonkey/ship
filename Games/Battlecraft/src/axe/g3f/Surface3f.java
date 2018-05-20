package axe.g3f;

import static org.lwjgl.opengl.GL11.*;
import axe.DrawMode;
import axe.Scalarf;
import axe.TimeUnit;

public class Surface3f implements Drawable3f, Updatable3f
{
	
	public final Scalarf yaw = new Scalarf();
	public final Scalarf pitch = new Scalarf();
	public final Scalarf roll = new Scalarf();
	public final Vec3f normal = new Vec3f();  //<auto>
	public final Vec3f right = new Vec3f(); //<auto>
	public final Vec3f up = new Vec3f(); //<auto>
	public final Vec3f position = new Vec3f(); //<auto>
	public final Quaternion3f rollq = new Quaternion3f(); // <auto>
	public final float width, height;
	
	public Surface3f(float width, float height) 
	{
		this.width = width;
		this.height = height; 
	}
	
	public void update(TimeUnit elapsed, Scene3f scene) 
	{
		// Wrap all angles between 0 and 2PI
		yaw.mod(Scalarf.PI2);
		pitch.mod(Scalarf.PI2);
		roll.mod(Scalarf.PI2);

		// Use yaw and pitch to calculate the direction and right vector.
		// The right vector always lies on the x,z plane. All three vectors
		// are unit vectors.
		float cosy = yaw.cos();
		float siny = yaw.sin();
		float cosp = pitch.cos();
		float sinp = pitch.sin();

		normal.norm(siny * cosp, sinp, -cosy * cosp);
		right.set(cosy, 0f, siny);
		up.cross(right, normal);

		// If there is roll, setup the quaternion using the direction and
		// the requested roll angle, and rotate up and right.
		if (roll.v != 0f) {
			rollq.set(normal, -roll.v);
			rollq.rotate(up);
			rollq.rotate(right);
		}
	}
	
	public void update(Vec3f n) 
	{
		normal.norm(n);
		right.set(yaw.cos(), 0f, yaw.sin());
		up.cross(right, normal);

		// If there is roll, setup the quaternion using the direction and
		// the requested roll angle, and rotate up and right.
		if (roll.v != 0f) {
			rollq.set(normal, -roll.v);
			rollq.rotate(up);
			rollq.rotate(right);
		}
	}
	
	public void draw(DrawMode mode, Scene3f scene) 
	{
		final Vec3f p = position, r = right, u = up;
		final float hw = width * 0.5f, hh = height * 0.5f;
		final float rx = r.x * hw, ry = r.y * hw, rz = r.z * hw;
		final float ux = u.x * hh, uy = u.y * hh, uz = u.z * hh;
		glBegin(GL_QUADS); {
			glVertex3f(p.x + rx + ux, p.y + ry + uy, p.z + rz + uz);
			glVertex3f(p.x + rx - ux, p.y + ry - uy, p.z + rz - uz);
			glVertex3f(p.x - rx - ux, p.y - ry - uy, p.z - rz - uz);
			glVertex3f(p.x - rx + ux, p.y - ry + uy, p.z - rz + uz); 
		} glEnd();
	}
	
}