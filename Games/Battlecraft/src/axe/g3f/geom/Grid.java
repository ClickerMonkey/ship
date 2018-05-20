package axe.g3f.geom;

import axe.DrawMode;
import axe.g3f.Drawable3f;
import axe.g3f.Scene3f;
import axe.g3f.Vec3f;

public class Grid implements Drawable3f 
{
	private final Quad[] quads;
	public Grid(Quad root, int radius) {
		int wide = (radius << 1) - 1;
		float scale = (root.scale * 2) / wide;
		int index = 0;
		this.quads = new Quad[wide * wide];
		for (int y = -radius + 1; y < radius; y++) {
			for (int x = -radius + 1; x < radius; x++) {
				quads[index++] = create(root, x, y, scale);
			}
		}
	}
	public Quad create(Quad root, int ix, int iy, float scale) {
		Vec3f v = new Vec3f();
		Quad q = new Quad(root.center, root.normal);
		v.norm(root.right);
		q.center.add(v, ix * scale);
		v.norm(root.up);
		q.center.add(v, iy * scale);
		q.setScale(scale * 0.5f);
		return q;
	}
	public void draw(DrawMode mode, Scene3f scene) {
		for (Quad q : quads) {
			q.draw(mode, scene);	
		}
	}
}
