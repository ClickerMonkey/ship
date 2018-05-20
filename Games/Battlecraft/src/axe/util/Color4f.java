package axe.util;

import static org.lwjgl.opengl.GL11.*;
import axe.Scalarf;
import axe.anim.Delta;
import axe.anim.Motion;

public class Color4f implements Delta<Color4f>, Motion<Color4f> 
{
	public static final Color4f White = new Color4f(1f, 1f, 1f, 1f);
	
	public float r, b, g, a;
	
	public Color4f() 
	{
		set(1f, 1f, 1f, 1f);
	}
	
	public Color4f(float red, float green, float blue) 
	{
		set(red, green, blue, 1f);
	}
	
	public Color4f(float red, float green, float blue, float alpha) 
	{
		set(red, green, blue, alpha);
	}
	
	public Color4f(Color4f c) {
		set(c);
	}
	
	public void set(Color4f c) {
		set(c.r, c.g, c.b, c.a);
	}
	
	public void set(float red, float green, float blue) 
	{
		set(red, green, blue, 1f);
	}
	
	public void set(float red, float green, float blue, float alpha) 
	{
		r = red;
		g = green;
		b = blue;
		a = alpha;
	}
	
	public void set(Color4f c, float scale) 
	{
		r = Scalarf.clamp(c.r * scale, 0f, 1f);
		g = Scalarf.clamp(c.g * scale, 0f, 1f);
		b = Scalarf.clamp(c.b * scale, 0f, 1f);
		a = Scalarf.clamp(c.a * scale, 0f, 1f);
	}
	
	public void bind() 
	{
		glColor4f(r, g, b, a);
	}
	
	public void bind(float scale) 
	{
		glColor4f(clamp(r * scale), clamp(g * scale), clamp(b * scale), clamp(a * scale));
	}
	
	private float clamp(float r) 
	{
		return Scalarf.clamp(r, 0, 1);
	}
	
	public void delta(Color4f start, Color4f end, float delta) 
	{
		r = (end.r - start.r) * delta + start.r;
		g = (end.g - start.g) * delta + start.g;
		b = (end.b - start.b) * delta + start.b;
		a = (end.a - start.a) * delta + start.a;
	}
	
	public Color4f get() 
	{
		return this;
	}
	
	public void add(Color4f c, float scale) 
	{
		r += clamp(c.r * scale);
		g += clamp(c.g * scale);
		b += clamp(c.b * scale);
		a += clamp(c.a * scale);
	}
	
	public void max(Color4f max) 
	{
		r = StrictMath.min(r, max.r);
		g = StrictMath.min(g, max.g);
		b = StrictMath.min(b, max.b);
		a = StrictMath.min(a, max.a);
	}
	
	public void clamp() 
	{
		r = clamp(r);
		g = clamp(g);
		b = clamp(b);
		a = clamp(a);
	}

	public float distance(Color4f to) 
	{
		return Math.abs(r - to.r) + Math.abs(g - to.g) + Math.abs(b - to.b) + Math.abs(a - to.a);
	}
	
}