package axe.g2f;

import java.util.Random;

public class Vec2fRange 
{
	
	public static final Random rnd = new Random();
	
	public final Vec2f max = new Vec2f();
	public final Vec2f min = new Vec2f();
	
	public Vec2f rnd() 
	{
		return Vec2f.inter(min, max, rnd.nextFloat());
	}
	
}