package axe.g2f;

import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import axe.anim.Delta;
import axe.g3f.Vec3f;

public class Vec2f implements Delta<Vec2f>
{
	public static final Vec2f UP = new Vec2f(0, 1);
	public static final Vec2f DOWN = new Vec2f(0, -1);
	public static final Vec2f RIGHT = new Vec2f(1, 0);
	public static final Vec2f LEFT = new Vec2f(-1, 0);
	
	
	public float x, y;
	
	public Vec2f() {
	}
	
	public Vec2f(float x, float y) {
		set(x, y);
	}
	
	public Vec2f(Vec2f v) {
		set(v);
	}
	public void set(Vec2f v) {
		x = v.x;
		y = v.y;
	}
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	public void norm() {
		float sq = (x*x)+ (y*y);
		if (sq != 0.0f && sq != 1.0f) {
			div((float)Math.sqrt(sq));
		}
	}
	public void norm(Vec2f v) {
		set(v); norm();
	}
	public void norm(float x, float y) {
		set(x, y); norm();
	}
	public void reflect(Vec2f v, Vec2f n) {
		set(v); add(n, -2f * v.dot(n));
	}
	public void refract(Vec2f v, Vec2f n) {
		reflect(v, n); neg();
	}
	public void div(float s) {
		if (s != 0.0) {
			s = 1f / s;
			x *= s;
			y *= s;
		}
	}
	public void mul(float s) {
		x *= s;
		y *= s;
	}
	public void add(Vec2f v) {
		x += v.x; y += v.y;
	}
	public void add(float vx, float vy, float vz) {
		x += vx; y += vy;
	}
	public void add(Vec2f v, float scale) {
		x += v.x * scale; y += v.y * scale;
	}
	public void sub(Vec2f v) {
		x -= v.x; y -= v.y;
	}
	public void neg() {
		x = -x; y = -y;
	}
	public void length(float length) {
		float sq = (x*x) + (y*y);
		if (sq != 0.0 && sq != length * length) {
			mul(length / (float)Math.sqrt(sq));
		}
	}
	public float dot() {
		return (x * x) + (y * y);
	}
	public float dot(Vec2f v) {
		return (x * v.x) + (y * v.y);
	}
	public float length() {
		return (float)StrictMath.sqrt(dot());
	}
	public float distance(Vec2f v) {
		float dx = v.x - x;
		float dy = v.y - y;
		return (float)Math.sqrt(dx * dx + dy * dy);
	}
	public boolean isZero() {
		return (x == 0f && y == 0f);
	}
	public void average(Vec2f[] v) {
		if (v.length > 0) {
			x = y = 0f;
			for (int i = 0; i < v.length; i++) {
				add(v[i]);
			}
			div(v.length);
		}
	}
	public void glTranslate() {
		glTranslatef(x, y, 0);
	}
	public void glRotate(float degrees) {
		glRotatef(degrees, x, y, 0);
	}
	public void glScale() {
		glScalef(x, y, 0);
	}
	public void bind() {
		glVertex3f(x, y, 0);
	}
	public void delta(Vec2f start, Vec2f end, float delta) {
		x = (end.x - start.x) * delta + start.x;
		y = (end.y - start.y) * delta + start.y;
	}
	public Vec2f get() {
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
	public boolean equals(Vec2f v) {
		return (x == v.x && y == v.y);
	}
	public boolean equals(float vx, float vy) {
		return (x == vx && y == vy);
	}
	
	public String toString() {
		return String.format("{%.2f, %.2f}", x, y);
	}

	public static Vec2f mul(Vec2f v, float s) {
		return new Vec2f(v.x * s, v.y * s);
	}
	public static Vec2f sub(Vec2f a, Vec2f b) { 
		return new Vec2f(a.x - b.x, a.y - b.y);
	}
	
	
	public static Vec2f inter(Vec2f s, Vec2f e, float delta) 
	{
		return new Vec2f((e.x - s.x) * delta + s.x,
				(e.y - s.y) * delta + s.y);
	}
	
}