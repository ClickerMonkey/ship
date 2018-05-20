package axe.g3f;

import axe.Scalarf;

public class Quaternion3f 
{
	
	public float x, y, z, w;
	
	public Quaternion3f() 
	{
	}
	
	public Quaternion3f(float x, float y, float z, float w) 
	{
		set(x, y, z, w);
	}
	
	public Quaternion3f(Vec3f v, float angle) 
	{
		set(v, angle);
	}
	
	public void set(float x, float y, float z, float w) 
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public void set(Vec3f v, float angle) 
	{
		float half = angle * 0.5f;
		float sin = Scalarf.sin(half) / v.length();
		float cos = Scalarf.cos(half);
		x = v.x * sin;
		y = v.y * sin;
		z = v.z * sin;
		w = cos;
	}
	
	public void invert() 
	{
		x = -x;
		y = -y;
		z = -z;
	}
	
	public void multiply(Quaternion3f a, Quaternion3f b) 
	{
		x = a.w * b.x + a.x * b.w + a.y * b.z - a.z * b.y;
		y = a.w * b.y + a.y * b.w + a.z * b.x - a.x * b.z;
		z = a.w * b.z + a.z * b.w + a.x * b.y - a.y * b.x;
		w = a.w * b.w - a.x * b.x - a.y * b.y - a.z * b.z;
	}
	
	public void multiply(float ax, float ay, float az, float aw, float bx, float by, float bz, float bw) 
	{
		x = aw * bx + ax * bw + ay * bz - az * by;
		y = aw * by + ay * bw + az * bx - ax * bz;
		z = aw * bz + az * bw + ax * by - ay * bx;
		w = aw * bw - ax * bx - ay * by - az * bz;
	}
	
	private static final Quaternion3f rot = new Quaternion3f();
	
	public void rotate(Vec3f v) 
	{
		v.norm();
		rot.multiply(v.x, v.y, v.z, 0f, -x, -y, -z, w);
		rot.multiply(x, y, z, w, rot.x, rot.y, rot.z, rot.w);
		v.set(rot.x, rot.y, rot.z);
	}
	
	public Quaternion3f copy() 
	{
		return new Quaternion3f(x, y, z, w);
	}
	
}