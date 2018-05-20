package axe.texture;

import static org.lwjgl.opengl.GL11.*;

public class Source {
	public float s, t;
	public Source(float s, float t) {
		set(s, t);
	}
	public void set(float s, float t) {
		this.s = s;
		this.t = t;
	}
	public void bind() {
		glTexCoord2f(s, t);
	}
}