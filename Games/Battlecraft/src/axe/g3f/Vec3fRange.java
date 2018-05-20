package axe.g3f;

import java.util.Random;


public class Vec3fRange 
{
	public static final Random rnd = new Random();
	public final Vec3f max = new Vec3f();
	public final Vec3f min = new Vec3f();
	public Vec3f rnd() {
		return Vec3f.inter(min, max, rnd.nextFloat());
	}
}