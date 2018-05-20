package model;

import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;

import model.util.Point;
import model.util.World;

public class Mesh {

	private final ArrayList<Point> points;
	private final ArrayList<Primitive> primitives;
	private final ArrayList<Line> wires;
	private final Color wireColor;
	
	public Mesh(Color wireColor) {
		this.points = new ArrayList<Point>();
		this.primitives = new ArrayList<Primitive>();
		this.wires = new ArrayList<Line>();
		this.wireColor = wireColor;
	}
	
	public int addPoint(double x, double y, double z) {
		int index = points.size();
		points.add(new Point(x, y, z));
		return index;
	}
	
	public void addLine(int start, int end, Color color, float width) {
		primitives.add(new Line(points.get(start), points.get(end), color, width));
		wires.add(new Line(points.get(start), points.get(end), wireColor, 1));
	}
	
	public void addSphere(int center, Color color, float radius) {
		primitives.add(new Sphere(points.get(center), radius, color));
	}
	
	public void addFace(int p1, int p2, int p3, Paint fill) {
		primitives.add(new Face(points.get(p1), points.get(p2), points.get(p3), fill));
		wires.add(new Line(points.get(p1), points.get(p2), wireColor, 1));
		wires.add(new Line(points.get(p2), points.get(p3), wireColor, 1));
		wires.add(new Line(points.get(p3), points.get(p1), wireColor, 1));
	}
	
	public void addQuad(int p1, int p2, int p3, int p4, Paint fill) {
//		primitives.add(new Quad(points.get(p1), points.get(p2), points.get(p3), points.get(p4), fill));
		primitives.add(new Face(points.get(p1), points.get(p2), points.get(p3), fill));
		primitives.add(new Face(points.get(p3), points.get(p4), points.get(p1), fill));
		wires.add(new Line(points.get(p1), points.get(p2), wireColor, 1));
		wires.add(new Line(points.get(p2), points.get(p3), wireColor, 1));
		wires.add(new Line(points.get(p3), points.get(p4), wireColor, 1));
		wires.add(new Line(points.get(p4), points.get(p1), wireColor, 1));
	}
	
	public void addPrimitive(Primitive primitive) {
		primitives.add(primitive);
	}
	
	public void pushModel(World world) {
		for (Primitive p : primitives) {
			world.addPrimitive(p);
		}
	}
	public void popModel(World world) {
		for (Primitive p : primitives) {
			world.removePrimitive(p);
		}
	}
	
	public void pushWireframe(World world) {
		for (Line l : wires) {
			world.addPrimitive(l);
		}
	}
	public void popWireframe(World world) {
		for (Line l : wires) {
			world.removePrimitive(l);
		}
	}

}
