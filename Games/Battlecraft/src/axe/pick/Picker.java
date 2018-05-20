package axe.pick;

import java.nio.IntBuffer;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import axe.DrawMode;
import axe.core.Scene;
import axe.util.Buffers;

public class Picker 
{
	
	private IntBuffer view = Buffers.ints(16);
	private IntBuffer picks = Buffers.ints(64);
	public float width, height, cx, cy;
	
	public Picker(float cx, float cy, float width, float height) 
	{
		this.cx = cx;
		this.cy = cy;
		this.width = width;
		this.height = height;
	}
	
	public <S extends Scene> int pick(Pick<S>[] out, PickList<S> list, S scene) 
	{
		glGetInteger(GL_VIEWPORT, view);
		glSelectBuffer((IntBuffer)picks.clear());
		glRenderMode(GL_SELECT);
		glInitNames();
		glPushName(0);
		glMatrixMode(GL_PROJECTION);
		glPushMatrix();
		glLoadIdentity();
		gluPickMatrix(cx, cy, width, height, view);
		scene.select();
		list.draw(DrawMode.Select, scene);
		glMatrixMode(GL_PROJECTION);
		glPopMatrix();

		for (int i = 0; i < out.length; i++) {
			out[i] = null;
		}

		int names = glRenderMode(GL_RENDER);
		int placed = 0;
		for (int k = 0, j = 0; k < names; k++, j=(k<<2)) {
			Pick<S> p = new Pick<S>(list.get(picks.get(j + 3)));
			p.hits = picks.get(j + 0);
			p.minZ = picks.get(j + 1);
			p.maxZ = picks.get(j + 2);
			boolean inserted = false;
			int imax = Math.min(k, out.length);
			for (int i = 0; i < imax && !inserted; i++)  {
				if (out[i].minZ > p.minZ) {
					int qmax = Math.min(imax + 1, out.length - 1);
					for (int q = qmax; q > i; q--) {
						out[q] = out[q - 1];
					}
					out[i] = p;
					placed++;
					inserted = true;
				}
			}
			if (!inserted && k < out.length) {
				out[k] = p;
				placed++;
			}
		}

		return Math.min(placed, out.length);
	}
	
}
