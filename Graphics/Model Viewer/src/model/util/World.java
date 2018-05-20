package model.util;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;

import model.Mode;
import model.Primitive;

public class World {

	private Mode mode;
	private boolean drawBackfaces;
	private Matrix world;
	private ArrayList<Primitive> primitives;
	
	public World() {
		world = new Matrix();
		primitives = new ArrayList<Primitive>();
		mode = Mode.Fill;
		drawBackfaces = true;
	}
	
	public void addPrimitive(Primitive p) {
		primitives.add(p);
	}

	public void removePrimitive(Primitive p) {
		primitives.remove(p);
	}
	
	public void updateWorld() {
		for (Primitive p : primitives) {
			p.update(world);
		}
		Collections.sort(primitives);
	}
	
	public void draw(Graphics2D gr) {
		if (drawBackfaces) {
			for (Primitive p : primitives) {
				p.draw(gr, mode);
			}	
		}
		else {
			for (Primitive p : primitives) {
				if (p.isFrontFace()) {
					p.draw(gr, mode);
				}
			}
		}
	}

	public Matrix getWorld() {
		return world;
	}
	
	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public boolean isDrawBackfaces() {
		return drawBackfaces;
	}

	public void setDrawBackfaces(boolean drawBackfaces) {
		this.drawBackfaces = drawBackfaces;
	}
	
}
