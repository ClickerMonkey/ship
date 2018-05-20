package axe.g3f;

import static org.lwjgl.util.glu.GLU.*;
import axe.Scalarf;
import axe.TimeUnit;

public class Camera3f implements Updatable3f
{
	
	public final Scalarf yaw = new Scalarf();
	public final Scalarf pitch = new Scalarf();
	public final Scalarf roll = new Scalarf();
	public final Scalarf distance = new Scalarf();
	public final Vec3f focus = new Vec3f();
	public final Vec3f direction = new Vec3f();  //<auto>
	public final Vec3f right = new Vec3f(); //<auto>
	public final Vec3f up = new Vec3f(); //<auto>
	public final Vec3f position = new Vec3f(); //<auto>
	public final Vec3f forward = new Vec3f(); //<auto>
	public final Quaternion3f rollq = new Quaternion3f(); // <auto>

	public Camera3f() 
	{
	}
	
	public void init() {
		
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

		direction.norm(siny * cosp, sinp, -cosy * cosp);
		right.set(cosy, 0f, siny);
		forward.set(siny, 0f, -cosy);
		up.cross(right, direction);

		// If there is roll, setup the quaternion using the direction and
		// the requested roll angle, and rotate up and right.
		if (roll.v != 0f) {
			rollq.set(direction, -roll.v);
			rollq.rotate(up);
			rollq.rotate(right);
		}

		// Start at the focus and backout (or forward into) by the given 
		// distance in the current looking direction.
		position.set(focus);
		position.add(direction, -distance.v);
	}
	
	public void bind() 
	{
		final Vec3f p = position, d = direction, u = up;
		gluLookAt(p.x, p.y, p.z, p.x + d.x, p.y + d.y, p.z + d.z, u.x, u.y, u.z);
	}
	
}
