package model;

import java.awt.Graphics2D;

import model.util.Matrix;
import model.util.Point;

public abstract class Primitive implements Comparable<Primitive>
{

	protected boolean visible = true;
	protected double depth = 0.0;
	
	protected abstract void onUpdate(Matrix world);
	protected abstract double calculateDepth();
	public void update(Matrix world) {
		onUpdate(world);
		depth = calculateDepth();
	}
	
	protected abstract void onDraw(Graphics2D gr, Mode mode);
	public final void draw(Graphics2D gr, Mode mode) {
		if (visible) {
			onDraw(gr, mode);
		}
	}
	
	public final boolean isVisible() {
		return visible;
	}
	
	public final void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	protected int clamp(double value, int min, int max) {
		return (value < min ? min : (value > max ? max : (int)value));
	}
	
	public final int compareTo(Primitive p) {
		return (int)Math.signum(p.depth - depth);
	}
	
	public final double getDepth() {
		return depth;
	}
	
	public abstract boolean isFrontFace();
	
	public abstract Point[] getPoints();
	
	public abstract Point getNormal(Matrix world);
	
}
