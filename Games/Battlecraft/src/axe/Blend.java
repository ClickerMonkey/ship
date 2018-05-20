package axe;

import static org.lwjgl.opengl.GL11.*;

public enum Blend 
{
	Add		(GL_ONE, GL_ONE),
	Blend 	(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA),
	Minus	(GL_ONE_MINUS_DST_ALPHA, GL_DST_ALPHA),
	Xor		(GL_ONE_MINUS_DST_COLOR, GL_ZERO),
	Filter 	(GL_DST_COLOR, GL_ZERO),
	Modulate(GL_DST_COLOR, GL_ZERO),
	None 	(GL_ZERO, GL_ONE);
	
	public final int sfactor, dfactor;
	
	private Blend(int sfactor, int dfactor) {
		this.sfactor = sfactor;
		this.dfactor = dfactor;
	}
	
	public void bind() {
		glBlendFunc(sfactor, dfactor);
	}
	
	private static boolean enabled = false;
	
	public static void on() {
		if (!enabled) {
			glEnable(GL_BLEND);
			enabled = true;
		}
	}
	
	public static void off() {
		if (enabled) {
			glDisable(GL_BLEND);
			enabled = false;
		}
	}
	
}
