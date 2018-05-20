package axe.g2f;

import axe.Scalarf;

public class Bound2f 
{
	public float l, r, t, b;

	public Bound2f() {
	}
	
	public Bound2f(float x, float y) {
		clear(x, y);
	}
	
	public Bound2f(float left, float top, float right, float bottom) {
		set(left, top, right, bottom);
	}
	
	public Bound2f(Bound2f x) {
		set(x);
	}
	
	public void include(float x, float y) {
		if (x < l) l = x;
		if (x > r) r = x;
		if (y < t) t = y;
		if (y > b) b = y;
	}
	
	public void clear(float x, float y) {
		l = r = x;
		t = b = y;
	}
	
	public void clear() {
		l = r = t = b = 0f;
	}
	
	public void set(Bound2f x) {
		set(x.l, x.t, x.r, x.b);
	}
	
	public void set(float left, float top, float right, float bottom) {
		l = left;
		r = right;
		t = top;
		b = bottom;
	}
	
	public void rect(float x, float y, float width, float height) {
		set(x, y, x + width, y + height);
	}
	
	public void line(float x0, float y0, float x1, float y1) {
		l = Math.min(x0, x1);
		r = Math.max(x0, x1);
		t = Math.min(y0, y1);
		b = Math.min(y0, y1);
	}
	
	public void ellipse(float cx, float cy, float rw, float rh) {
		rw = Math.abs(rw);
		rh = Math.abs(rh);
		l = cx - rw;
		r = cx + rw;
		t = cy - rh;
		b = cy + rh;
	}
	
	public void quad(float cx, float cy, float w, float h, float angle) {
		quad(cx, cy, w, h, Scalarf.cos(angle), Scalarf.sin(angle));
	}
	
	public void quad(float cx, float cy, float w, float h, float vx, float vy) {
		vx = Math.abs(vx);
		vy = Math.abs(vy);
		float hw = (vx * w + vy * h) * 0.5f;
		float hh = (vx * h + vy * w) * 0.5f;
		ellipse(cx, cy, hw, hh);
	}
	
	public void center(float x, float y) {
		float w = width() * 0.5f;
		float h = height() * 0.5f;
		l = x - w;
		r = x + w;
		t = y - h;
		b = y + h;
	}
	
	public void move(float dx, float dy) {
		l += dx; r += dx;
		t += dy; b += dy;
	}
	
	public void zoom(float sx, float sy) {
		float hw = width() * 0.5f * Math.abs(sx);
		float hh = height() * 0.5f * Math.abs(sy);
		ellipse(cx(), cy(), hw, hh);
	}
	
	public float width() {
		return (r - l);
	}
	
	public float height() {
		return (b - t);
	}
	
	public float cx() {
		return (l + r) * 0.5f;
	}
	
	public float cy() {
		return (t + b) * 0.5f;
	}
	
	public void intersection(Bound2f x, Bound2f y) {
		l = Math.max(x.l, y.l);
		r = Math.min(x.r, y.r);
		t = Math.max(x.t, y.t);
		b = Math.min(x.b, y.b);
	}
	
	public void union(Bound2f x, Bound2f y) {
		l = Math.min(x.l, y.l);
		r = Math.max(x.r, y.r);
		t = Math.min(x.t, y.t);
		b = Math.max(x.b, y.b);
	}
	
	public boolean intersects(Bound2f x) {
		return !(x.l >= r || x.r <= l || x.t >= b || x.b <= t);
	}
	
	public boolean touches(Bound2f x) { 
		return !(x.l > r || x.r < l || x.t > b || x.b < t);
	}
	
	public boolean contains(Bound2f x) {
		return !(x.l < l || x.r > r || x.t < t || x.b > b);
	}
	
	public boolean inside(Bound2f x) {
		return !(x.l <= l || x.r >= r || x.t <= t || x.b >= b);
	}
	
	public String toString() {
		return String.format("{%.2f, %.2f, %.2f, %.2f}", l, t, width(), height());
	}
	
	/**
		glPushMatrix(); {
			glColor3f(1, 0, 0);
			glTranslatef(CENTER_X, CENTER_Y, 0);
			glRotatef((float)Math.toDegrees(angle), 0, 0, 1);
			glScalef(20f, 30f, 0f);
			glBegin(GL_LINE_LOOP); {
				glVertex2f(-0.5f, 0.5f);
				glVertex2f(-0.5f,-0.5f);
				glVertex2f( 0.5f,-0.5f);
				glVertex2f( 0.5f, 0.5f);
			} glEnd();
		} glPopMatrix();
		
		Bound2f b = new Bound2f();
		b.quad(CENTER_X, CENTER_Y, 20, 30, angle);
		glColor3f(0, 0, 1);
		glBegin(GL_LINE_LOOP); {
			glVertex2f(b.l, b.t);
			glVertex2f(b.l, b.b);
			glVertex2f(b.r, b.b);
			glVertex2f(b.r, b.t);
		} glEnd();
	 */
	
}

