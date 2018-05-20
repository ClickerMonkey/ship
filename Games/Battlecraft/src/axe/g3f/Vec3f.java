package axe.g3f;

import static org.lwjgl.opengl.GL11.*;
import axe.anim.Delta;
import axe.anim.Motion;

public class Vec3f implements Delta<Vec3f>, Motion<Vec3f> 
{
	
	public static final Vec3f RIGHT = new Vec3f(1, 0, 0);
	public static final Vec3f LEFT = new Vec3f(-1, 0, 0);
	public static final Vec3f UP = new Vec3f(0, 1, 0);
	public static final Vec3f DOWN = new Vec3f(0, -1, 0);
	public static final Vec3f NEAR = new Vec3f(0, 0, 1);
	public static final Vec3f FAR = new Vec3f(0, 0, -1);

	public float x, y, z;
	public Vec3f() {
	}
	public Vec3f(Vec3f v) {
		set(v);
	}
	public Vec3f(float x, float y, float z) {
		set(x, y, z);
	}
	public void set(Vec3f v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}
	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public void cross(Vec3f a, Vec3f b) {
		x = a.y * b.z - b.y * a.z;
		y = a.z * b.x - b.z * a.x;
		z = a.x * b.y - b.x * a.y;
	}
	public void norm() {
		float sq = (x*x)+ (y*y) + (z*z);
		if (sq != 0.0f && sq != 1.0f) {
			div((float)Math.sqrt(sq));
		}
	}
	public void norm(Vec3f v) {
		set(v); norm();
	}
	public void norm(float x, float y, float z) {
		set(x, y, z); norm();
	}
	public void cross(Vec3f r, Vec3f o, Vec3f l) {
		float x0 = r.x-o.x, y0 = r.y-o.y, z0 = r.z-o.z;
		float x1 = l.x-o.x, y1 = l.y-o.y, z1 = l.z-o.z;
		float d0 = (float)(1.0 / Math.sqrt((x0*x0)+(y0*y0)+(z0*z0)));
		float d1 = (float)(1.0 / Math.sqrt((x1*x1)+(y1*y1)+(z1*z1)));
		x0 *= d0; y0 *= d0; z0 *= d0;
		x1 *= d1; y1 *= d1; z0 *= d1;
		x = y0 * z1 - y1 * z0;
		y = z0 * x1 - z1 * x0;
		z = x0 * y1 - x1 * y0;
	}
	public void reflect(Vec3f v, Vec3f n) {
		set(v); add(n, -2f * v.dot(n));
	}
	public void refract(Vec3f v, Vec3f n) {
		reflect(v, n); neg();
	}
	public void div(float s) {
		if (s != 0.0) {
			s = 1f / s;
			x *= s;
			y *= s;
			z *= s;
		}
	}
	public void mul(float s) {
		x *= s;
		y *= s;
		z *= s;
	}
	public void add(Vec3f v) {
		x += v.x; y += v.y; z += v.z;
	}
	public void add(float vx, float vy, float vz) {
		x += vx; y += vy; z += vz;
	}
	public void add(Vec3f v, float scale) {
		x += v.x * scale; y += v.y * scale; z += v.z * scale;
	}
	public void sub(Vec3f v) {
		x -= v.x; y -= v.y; z -= v.z;
	}
	public void neg() {
		x = -x; y = -y; z = -z;
	}
	public void length(float length) {
		float sq = (x*x) + (y*y) + (z*z);
		if (sq != 0.0 && sq != length * length) {
			mul(length / (float)Math.sqrt(sq));
		}
	}
	public float dot() {
		return (x * x) + (y * y) + (z * z);
	}
	public float dot(Vec3f v) {
		return (x * v.x) + (y * v.y) + (z * v.z);
	}
	public float length() {
		return (float)StrictMath.sqrt(dot());
	}
	public float distance(Vec3f v) {
		float dx = v.x - x;
		float dy = v.y - y;
		float dz = v.z - z;
		return (float)Math.sqrt(dx * dx + dy * dy + dz * dz);
	}
	public boolean isZero() {
		return (x == 0f && y == 0f && z == 0f);
	}
	public void average(Vec3f[] v) {
		if (v.length > 0) {
			x = y = z = 0f;
			for (int i = 0; i < v.length; i++) {
				add(v[i]);
			}
			div(v.length);
		}
	}
	public void glTranslate() {
		glTranslatef(x, y, z);
	}
	public void glRotate(float degrees) {
		glRotatef(degrees, x, y, z);
	}
	public void glScale() {
		glScalef(x, y, z);
	}
	public void bind() {
		glVertex3f(x, y, z);
	}
	public void delta(Vec3f start, Vec3f end, float delta) {
		x = (end.x - start.x) * delta + start.x;
		y = (end.y - start.y) * delta + start.y;
		z = (end.z - start.z) * delta + start.z;
	}
	public Vec3f get() {
		return this;
	}
	public void max(Vec3f max) {
		float lengthSq = dot();
		float longestSq = max.dot();
		if (lengthSq > longestSq) {
			float length = (float)Math.sqrt(lengthSq);
			float longest = (float)Math.sqrt(longestSq);
			mul(longest / length);
		}
	}
	public boolean equals(Vec3f v) {
		return (x == v.x && y == v.y && z == v.y);
	}
	public boolean equals(float vx, float vy, float vz) {
		return (x == vx && y == vy && z == vz);
	}

	public static Vec3f mul(Vec3f v, float s) {
		return new Vec3f(v.x * s, v.y * s, v.z * s);
	}
	public static Vec3f inter(Vec3f s, Vec3f e, float delta) {
		return new Vec3f((e.x - s.x) * delta + s.x,
				(e.y - s.y) * delta + s.y, (e.z - s.z) * delta + s.z);
	}
	public static Vec3f sub(Vec3f a, Vec3f b) { 
		return new Vec3f(a.x - b.x, a.y - b.y, a.z - b.z);
	}
	public float distance(Vec3f start, Vec3f end) {
		return start.distance(end);
	}
}