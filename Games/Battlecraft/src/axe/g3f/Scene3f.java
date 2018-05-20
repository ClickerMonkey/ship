package axe.g3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;
import axe.Scalarf;
import axe.core.Scene;
import axe.g2f.Rect2i;

public class Scene3f implements Scene
{
	
	public final Camera3f camera = new Camera3f();
	public final Rect2i viewport = new Rect2i();
	public final Scalarf fov = new Scalarf(60f);
	public final Scalarf near = new Scalarf(0.0001f);
	public final Scalarf far = new Scalarf(1000.0f);
	
	public Scene3f(Rect2i view) 
	{
		this.viewport.set(view);
	}
	
	public void init() 
	{
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		glShadeModel(GL_SMOOTH);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glViewport(viewport.x, viewport.y, viewport.w, viewport.h);
		
		camera.init();
	}
	
	public void select() 
	{
		gluPerspective(fov.v, (float)viewport.w / (float)viewport.h, near.v, far.v);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		camera.bind();
	}
	
	public void start() 
	{
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(fov.v, (float)viewport.w / (float)viewport.h, near.v, far.v);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glPushMatrix();
		camera.bind();
	}
	
	public void end() 
	{
		glPopMatrix();
	}



}
