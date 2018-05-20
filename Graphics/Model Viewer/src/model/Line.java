package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import model.util.Matrix;
import model.util.Point;

public class Line extends Primitive 
{
	private Point[] points3d = { 
		new Point(), new Point()	
	};
	private Point[] points2d = {
		new Point(), new Point()
	};

	private Color color;
	private BasicStroke stroke;
	private Line2D.Double line;
	private Point normal;

	public Line(Point start, Point end, Color color, float width) {
		this(start.x, start.y, start.z, end.x, end.y, end.z, color, width);
	}
	
	public Line(double x1, double y1, double z1, double x2, double y2, double z2, Color color, float width) {
		this.points3d[0].set(x1, y1, z1);
		this.points3d[1].set(x2, y2, z2);
		
		this.color = color;
		this.stroke = new BasicStroke(width);
		this.line = new Line2D.Double();
		this.normal = new Point();
	}
	
	@Override
	protected void onDraw(Graphics2D gr, Mode mode) {
		switch (mode) {
		case Line:
		case Fill:
			gr.setColor(color);
			gr.setStroke(stroke);
			gr.draw(line);	
			break;
		case Point:
			break;
		}
	}

	@Override
	protected void onUpdate(Matrix world) {
		
		world.transform(points3d[0], points2d[0]);
		world.transform(points3d[1], points2d[1]);

		line.x1 = points2d[0].x;
		line.y1 = points2d[0].y;
		line.x2 = points2d[1].x;
		line.y2 = points2d[1].y;
	}
	
	@Override
	protected double calculateDepth() {
		return Math.max(points2d[0].z, points2d[1].z);
	}

	@Override
	public boolean isFrontFace() {
		return true;
	}

	@Override
	public Point[] getPoints() {
		return points2d;
	}

	@Override
	public Point getNormal(Matrix world) {
		world.getZ(normal);
		normal.negate();
		return normal;
	}

}
