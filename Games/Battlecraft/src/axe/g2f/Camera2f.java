package axe.g2f;

import static org.lwjgl.opengl.GL11.*;

import axe.Scalarf;
import axe.TimeUnit;


public class Camera2f implements Updatable2f
{
	
	private final Scalarf roll = new Scalarf(0f);
	private final Vec2f center = new Vec2f();
	private final Vec2f scale = new Vec2f(1f, 1f);
	private final Vec2f size = new Vec2f();
	private final Vec2f up = new Vec2f(); //<auto>
	private final Vec2f right = new Vec2f(); //<auto>
	private final Bound2f bounds = new Bound2f(); //<auto>
	
	public Camera2f() 
	{
	}
	
	public void update(TimeUnit elapsed, Scene2f scene) 
	{
		float cosr = roll.cos();
		float sinr = roll.sin();

		right.set(cosr, -sinr);
		up.set(sinr, cosr);
		bounds.quad(center.x, center.y, size.x, size.y, cosr, sinr);
	}
	
	public void bind() 
	{
		glTranslatef(size.x * 0.5f, size.y * 0.5f, 0f);
		glRotatef(roll.degrees(), 0, 0, 1);
		glScalef(scale.x, scale.y, 0);
		glTranslatef(-center.x, -center.y, 0f);
//		glTranslatef(size.x * 0.5f, size.y * 0.5f, 0f);
	}
	
	public void init()
	{
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, size.x, size.y, 0, -10, 10);
		glMatrixMode(GL_MODELVIEW);
	}
	
	public void center(float x, float y) {
		center.set(x, y);
	}
	
	public Vec2f center() {
		return center;
	}
	
	public Scalarf roll() {
		return roll;
	}
	
	public void roll(float a) {
		roll.set(a);
	}
	
	public Vec2f scale() {
		return scale;
	}
	
	public void scale(float sx, float sy) {
		scale.set(sx, sy);
	}
	
	public void zoom(float factor) {
		scale.mul(factor);
	}
	
	public Vec2f size() {
		return size;
	}
	
	public void size(float w, float h) {
		size.set(w, h);
	}
	
	public Vec2f right() {
		return right;
	}
	
	public Vec2f up() {
		return up;
	}
	
	public Bound2f bounds() {
		return bounds;
	}
	
}
