package axe.g2f;

import axe.core.Scene;

import static org.lwjgl.opengl.GL11.*;

public class Scene2f implements Scene
{
	
	private final Rect2i viewport = new Rect2i();
	private final Camera2f camera = new Camera2f();
	
	public Scene2f(Rect2i view) 
	{
		this.viewport.set(view);
		this.camera.size(viewport.w, viewport.h);
		this.camera.center(viewport.w >> 1, viewport.h >> 1);
	}
	
	public void init() 
	{
		glDisable(GL_DEPTH_TEST);
		glShadeModel(GL_SMOOTH);
		glViewport(viewport.x, viewport.y, viewport.w, viewport.h);
		
		camera.init();
	}
	
	public void select() 
	{
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		camera.bind();
	}
	
	public void start() 
	{
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glPushMatrix();
		camera.bind();
	}
	
	public void end() 
	{
		glPopMatrix();
	}
	
	public Camera2f camera() {
		return camera;
	}
	
	public Rect2i viewport() {
		return viewport;
	}
	
	public void viewport(int x, int y, int w, int h) {
		viewport.set(x, y, w, h);
	}
	
}
