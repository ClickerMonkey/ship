package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;

import model.util.Matrix;
import model.util.Point;

public class Face extends Primitive 
{

	private Point[] points3d = {
		new Point(), new Point(), new Point()	
	};
	private Point[] points2d = {
		new Point(), new Point(), new Point()	
	};
	
	private Paint fill;
	private Point normal;
	
	public Face(Point p1, Point p2, Point p3, Paint color) {
		points3d[0].set(p1);
		points3d[1].set(p2);
		points3d[2].set(p3);
		fill = color;
		normal = new Point();
	}

	private int polyx[] = new int[3];
	private int polyy[] = new int[3];
	
	@Override
	protected void onDraw(Graphics2D gr, Mode mode) {

		polyx[0] = (int)points2d[0].x;
		polyy[0] = (int)points2d[0].y;
		polyx[1] = (int)points2d[1].x;
		polyy[1] = (int)points2d[1].y;
		polyx[2] = (int)points2d[2].x;
		polyy[2] = (int)points2d[2].y;
		
		switch (mode) {
		case Fill:
		
			if (fill instanceof Color) {
				normal.normal(points2d[0], points2d[1], points2d[2]);
				double norm = Math.abs(normal.z);
				double delta = norm + (1 - norm) * 0.2;
				
				Color fillColor = (Color)fill;
				int r = clamp(fillColor.getRed() * delta, 0, 255);
				int g = clamp(fillColor.getGreen() * delta, 0, 255);
				int b = clamp(fillColor.getBlue() * delta, 0, 255);
				Color shade = new Color(r, g, b, fillColor.getAlpha());
				
				gr.setPaint(shade);
			}
			else {
				gr.setPaint(fill);			
			}
			gr.fillPolygon(polyx, polyy, 3);
			
			break;
		case Line:
			gr.drawPolygon(polyx, polyy, 3);
			break;
		case Point:
			break;
		}
		
	}

	@Override
	protected void onUpdate(Matrix world) {
		world.transform(points3d[0], points2d[0]);
		world.transform(points3d[1], points2d[1]);
		world.transform(points3d[2], points2d[2]);
		
		normal.normal(points2d[0], points2d[1], points2d[2]);
	}
	
	@Override
	protected double calculateDepth() {
		return Math.min(points2d[0].z, Math.min(points2d[1].z, points2d[2].z));
	}

	@Override
	public boolean isFrontFace() {
		return Point.area(points2d[0], points2d[1], points2d[2]) > 0;
	}

	@Override
	public Point[] getPoints() {
		return points2d;
	}

	@Override
	public Point getNormal(Matrix world) {
		return normal;
	}

}
