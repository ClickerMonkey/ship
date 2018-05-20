package axe.g3f.geom;

import static org.lwjgl.opengl.GL11.*;
import axe.texture.Texture;


public class Skybox 
{
	public Texture top, right, left, front, back;
	public float x, y, z;
	public float w, d, h;
	public void center(float cx, float cy, float cz) {
		x = cx - (w * 0.5f);
		y = cy - (h * 0.5f);
		z = cz - (d * 0.5f);
	}
	public void size(float width, float height, float depth) {
		w = width;
		h = height;
		d = depth;
	}
	public void draw() {
		top(); right(); left(); front(); back();
	}
	public void top() {
		top.bind();
		glBegin(GL_QUADS); {
			glTexCoord2f(1, 1);
			glVertex3f(x + w, y + h, z + d);
			glTexCoord2f(0, 1);
			glVertex3f(x, y + h, z + d);
			glTexCoord2f(0, 0);
			glVertex3f(x, y + h, z);
			glTexCoord2f(1, 0);
			glVertex3f(x + w, y + h, z);	
		} glEnd();
	}
	public void front() {
		front.bind();
		glBegin(GL_QUADS); {
			glTexCoord2f(1, 1);
			glVertex3f(x + w, y, z + d);
			glTexCoord2f(0, 1);
			glVertex3f(x, y, z + d);
			glTexCoord2f(0, 0);	
			glVertex3f(x, y + h, z + d);
			glTexCoord2f(1, 0);
			glVertex3f(x + w, y + h, z + d);
		} glEnd();
	}
	public void back() {
		back.bind();
		glBegin(GL_QUADS); {
			glTexCoord2f(0, 0);
			glVertex3f(x + w, y + h, z);
			glTexCoord2f(1, 0);
			glVertex3f(x, y + h, z);
			glTexCoord2f(1, 1);
			glVertex3f(x, y, z);
			glTexCoord2f(0, 1);
			glVertex3f(x + w, y, z);
		} glEnd();
	}
	public void left() {
		left.bind();
		glBegin(GL_QUADS); {
			glTexCoord2f(1, 1);
			glVertex3f(x, y, z + d);
			glTexCoord2f(0, 1);
			glVertex3f(x, y, z);
			glTexCoord2f(0, 0);
			glVertex3f(x, y + h, z);
			glTexCoord2f(1, 0);
			glVertex3f(x, y + h, z + d);
		} glEnd();
	}
	public void right() {
		right.bind();
		glBegin(GL_QUADS); {
			glTexCoord2f(1, 1);
			glVertex3f(x + w, y, z);
			glTexCoord2f(0, 1);
			glVertex3f(x + w, y, z + d);
			glTexCoord2f(0, 0);
			glVertex3f(x + w, y + h, z + d);
			glTexCoord2f(1, 0);
			glVertex3f(x + w, y + h, z);
		} glEnd();
	}
}
