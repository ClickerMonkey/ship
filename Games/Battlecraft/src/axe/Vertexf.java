package axe;

import static org.lwjgl.opengl.GL11.*;
import axe.g2f.Coord2f;
import axe.g3f.Vec3f;
import axe.util.Color4f;

public class Vertexf 
{
	public Color4f color;
	public Vec3f position;
	public Vec3f normal;
	public Coord2f coord;
	
	public Vertexf(Vec3f position, Vec3f normal, Color4f color, Coord2f coord) 
	{
		this.position = position;
		this.normal = normal;
		this.color = color;
		this.coord = coord;
	}
	
	public void bind() 
	{
		if (coord != null) {
			glTexCoord2f(coord.s, coord.t);
		}
		if (color != null) {
			glColor4f(color.r, color.g, color.b, color.a);
		}
		if (normal != null) {
			glNormal3f(normal.x, normal.y, normal.z);
		}
		if (position != null) {
			glVertex3f(position.x, position.y, position.z);
		}
	}
	
}