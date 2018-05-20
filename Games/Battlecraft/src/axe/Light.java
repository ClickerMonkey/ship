package axe;

import java.nio.FloatBuffer;

import axe.g3f.Vec3f;
import axe.util.Buffers;
import axe.util.Color4f;

import static org.lwjgl.opengl.GL11.*;

public class Light 
{
	
	public final int index;
	private FloatBuffer ambient = Buffers.floats(0.2f, 0.2f, 0.2f, 1.0f);
	private FloatBuffer diffuse = Buffers.floats(0.8f, 0.8f, 0.8f, 1.0f);
	private FloatBuffer specular = Buffers.floats(1.0f, 1.0f, 1.0f, 1.0f);
	private FloatBuffer position = Buffers.floats(0.0f, 0.0f, 0.0f, 1.0f);
	private FloatBuffer direction = Buffers.floats(0.0f, -1.0f, 0.0f, 1.0f);
//	private FloatBuffer spotOff = Buffers.floats(60.0f, 0f, 0f, 0f);
//	private FloatBuffer spotExp = Buffers.floats(20.0f, 0f, 0f, 0f);

	
	public Light(int index) 
	{
		this.index = index + GL_LIGHT0;
		this.setup();
	}
	
	private void setup() 
	{
		glLight(index, GL_AMBIENT, ambient);
		glLight(index, GL_DIFFUSE, diffuse);
		glLight(index, GL_SPECULAR, specular);
//		glLight(index, GL_SPOT_EXPONENT, spotExp);
//		glLight(index, GL_SPOT_CUTOFF, spotOff);
	}
	
	public void update() 
	{
		glLight(index, GL_POSITION, position);
		glLight(index, GL_SPOT_DIRECTION, direction);
	}
	
	public void on() 
	{
		glEnable(index);
	}
	
	public void off() 
	{
		glDisable(index);
	}
	
	public void ambient(Color4f color) 
	{
		ambient(color.r, color.g, color.b, color.a);
	}
	
	public void ambient(float r, float g, float b, float a) 
	{
		ambient.put(0, r).put(1, g).put(2, b).put(3, a);
	}
	
	public void diffuse(Color4f color) 
	{
		diffuse(color.r, color.g, color.b, color.a);
	}
	
	public void diffuse(float r, float g, float b, float a) 
	{
		diffuse.put(0, r).put(1, g).put(2, b).put(3, a);
	}
	
	public void specular(Color4f color) 
	{
		specular(color.r, color.g, color.b, color.a);
	}
	
	public void specular(float r, float g, float b, float a) 
	{
		specular.put(0, r).put(1, g).put(2, b).put(3, a);
	}
	
	public void position(Vec3f pos) 
	{
		position(pos.x, pos.y, pos.z);
	}
	
	public void position(float x, float y, float z) 
	{
		position.put(0, x).put(1, y).put(2, z).put(3, 1f);
	}
	
	public void direction(Vec3f pos) 
	{
		direction(pos.x, pos.y, pos.z);
	}
	
	public void direction(float x, float y, float z) 
	{
		direction.put(0, x).put(1, y).put(2, z).put(3, 0f);
	}
	
	public static void init() 
	{
		enable();
		FloatBuffer modelAmbient = Buffers.floats(0.8f, 0.8f, 0.8f, 1.0f);
		FloatBuffer materialAmbient = Buffers.floats(0.2f, 0.2f, 0.2f, 1.0f);
		FloatBuffer materialDiffuse = Buffers.floats(0.8f, 0.8f, 0.8f, 1.0f);
		FloatBuffer materialSpecular = Buffers.floats(0.2f, 0.2f, 0.2f, 1.0f);
		FloatBuffer materialEmission = Buffers.floats(0.0f, 0.0f, 0.0f, 1.0f);
		
		glLightModel(GL_LIGHT_MODEL_AMBIENT, modelAmbient);
		glLightModeli(GL_LIGHT_MODEL_LOCAL_VIEWER, GL_TRUE);
		glLightModeli(GL_LIGHT_MODEL_TWO_SIDE, GL_FALSE);
		glMaterial(GL_FRONT, GL_AMBIENT, materialAmbient);
		glMaterial(GL_FRONT, GL_DIFFUSE, materialDiffuse);
		glMaterial(GL_FRONT, GL_SPECULAR, materialSpecular);
		glMaterial(GL_FRONT, GL_EMISSION, materialEmission);
		glMaterialf(GL_FRONT, GL_SHININESS, 10.0f);
		disable();
	}
	
	public static void enable() 
	{
		glEnable(GL_LIGHTING);
		glEnable(GL_NORMALIZE);
	}
	
	public static void disable() 
	{
		glDisable(GL_LIGHTING);
		glDisable(GL_NORMALIZE);
	}
	
}
