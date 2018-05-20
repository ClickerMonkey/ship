package axe.texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;

public enum TextureWrap 
{
	
	ToEdge(GL_CLAMP_TO_EDGE),
	ToBorder(GL_CLAMP_TO_BORDER),
	Default(GL_CLAMP),
	Repeat(GL_REPEAT),
	Mirror(GL_MIRRORED_REPEAT);
	
	public final int param;
	
	private TextureWrap(int param) 
	{
		this.param = param;
	}
	
	public void binds(int target) 
	{
		glTexParameteri(target, GL_TEXTURE_WRAP_S, param);
	}
	
	public void bindt(int target) 
	{
		glTexParameteri(target, GL_TEXTURE_WRAP_T, param);
	}
	
}
