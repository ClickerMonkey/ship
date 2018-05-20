package model;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Ellipse2D;

import model.util.Matrix;
import model.util.Point;

public class Sphere extends Primitive 
{
	public static final Color Transparent = new Color(0, 0, 0, 0);
	
	private Point[] center3d = {
			new Point()
	};
	private Point[] center2d = {
			new Point()
	};
	
	private Paint paint;
	private Paint glare;
	private Paint shadow;
	private Ellipse2D.Float circle;
	private Point normal;

	public Sphere(Point center, float radius, Paint color) {
		this(center.x, center.y, center.z, radius, color);
	}
	
	public Sphere(double x, double y, double z, float radius, Paint color) {
		center3d[0].set(x, y, z);
		paint = color;
		circle = new Ellipse2D.Float();
		normal = new Point();
		setRadius(radius);
	}
	
	@Override
	protected void onDraw(Graphics2D gr, Mode mode) {
		switch (mode) {
		case Fill:
			gr.translate(center2d[0].x, center2d[0].y);
			gr.setPaint(paint);
			gr.fill(circle);
			gr.setPaint(shadow);
			gr.fill(circle);
			gr.setPaint(glare);
			gr.fill(circle);
			gr.translate(-center2d[0].x, -center2d[0].y);
			break;
		case Line:
			gr.translate(center2d[0].x, center2d[0].y);
			gr.setPaint(paint);
			gr.draw(circle);
			gr.translate(-center2d[0].x, -center2d[0].y);
			break;
		case Point:
			break;
		}
		
	}

	@Override
	protected void onUpdate(Matrix world) {
		world.transform(center3d[0], center2d[0]);
	}

	@Override
	protected double calculateDepth() {
		return center2d[0].z;
	}
	
	public void setRadius(float radius) {
		circle.x = -radius;
		circle.y = -radius;
		circle.width = radius * 2;
		circle.height = radius * 2;
		
		shadow = new GradientPaint(
				-radius * 0.5f, -radius * 0.5f, Transparent, 
				radius, radius, Color.black);
		glare = new GradientPaint(
				-radius,-radius, Color.white, 
				0, 0, Transparent);
	}

	@Override
	public boolean isFrontFace() {
		return true;
	}

	@Override
	public Point[] getPoints() {
		return center2d;
	}

	@Override
	public Point getNormal(Matrix world) {
		world.getZ(normal);
		normal.negate();
		return normal;
	}

}
