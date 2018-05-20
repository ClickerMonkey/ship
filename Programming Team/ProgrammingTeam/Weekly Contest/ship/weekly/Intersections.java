package ship.weekly;

import static java.lang.Math.*;

import java.util.Scanner;

public class Intersections
{

	public static void main(String[] args) {
		new Intersections();
	}
	
	
	Intersections() {
		Scanner in = new Scanner(System.in);
		
		int cases = Integer.parseInt(in.nextLine());
		int shapeCount;
		Shape[] shapes;
		
		for (int i = 1; i <= cases; i++) {
			shapeCount = Integer.parseInt(in.nextLine());
			shapes = new Shape[shapeCount];
	
			for (int j = 0; j < shapeCount; j++) {
				String type = in.next();
				if (type.equals("circle")) {
					shapes[j] = new Circle(in.nextDouble(), in.nextDouble(), in.nextDouble());
				}
				else if (type.equals("rectangle")) {
					shapes[j] = new Rect(in.nextDouble(), in.nextDouble(), in.nextDouble(), in.nextDouble());
				}
				else if (type.equals("capsule")) {
					shapes[j] = new Capsule(in.nextDouble(), in.nextDouble(), in.nextDouble(), in.nextDouble());
				}
				in.nextLine();
			}
			
			boolean intersect = true;
			
			// Make sure all shapes bounds intersect
			for (int y = 0; y < shapeCount - 1; y++) {
				for (int x = y + 1; x < shapeCount; x++) {
					if (!shapes[y].bounds().intersects(shapes[x].bounds())) {
						intersect = false;
					}
				}
			}
			
			if (intersect) {
				Rect area = intersection(shapes);
				
				double inc = 0.005;
				double answer = 0.0;
				for (double y = area.top; y >= area.bottom; y -= inc) {
					for (double x = area.left; x <= area.right; x += inc) {
						int inside = 0;
						for (int s = 0; s < shapeCount; s++) {
							if (shapes[s].contains(x, y)) {
								inside++;
							}
						}
						if (inside == shapeCount) {
							answer += inc;
						}
					}
				}
				
				System.out.format("Set%d: %.2f\n", i, answer);
			}
			else {
				System.out.format("Set%d: 0.00\n", i);
			}
		}
	}
	
	public Rect intersection(Shape[] shapes) {
		return null;
	}
	
	interface Shape {
		Rect bounds();
		boolean contains(double x, double y);
	}
	
	class Rect implements Shape {
		double left, top, right, bottom;
		Rect(double x0, double y0, double x1, double y1) {
			left = min(x0, x1);
			right = max(x0, x1);
			top = max(y0, y1);
			bottom = min(y0, y1);
		}
		public boolean contains(double px, double py) {
			return !(px < left || px > right || py > top || py < bottom);
		}
		public Rect bounds() {
			return this;
		}
		public boolean intersects(Rect r) {
			return !(r.left > right || r.right < left || r.top < bottom || r.bottom > top);
		}
	}
	
	class Circle implements Shape {
		double x, y, radiusSq;
		Rect bounds;
		Circle(double cx, double cy, double r) {
			x = cx;
			y = cy;
			radiusSq = r * r;
			bounds = new Rect(x - r, y - r, x + r, y + r);
		}
		public boolean contains(double px, double py) {
			double dx = px - x;
			double dy = py - y ;
			return (dx * dx + dy * dy <= radiusSq);
		}
		public Rect bounds() {
			return bounds;
		}
	}
	
	class Capsule implements Shape {
		Circle end0, end1;
		Rect body, bounds;
		Capsule(double left, double top, double right, double bottom) {
			double width = right - left;
			double height = top - bottom;
			if (width > height) {
				double radius = height * 0.5;
				end0 = new Circle(left + radius, top - radius, radius);
				end1 = new Circle(right - radius, top - radius, radius);
				body = new Rect(left + radius, top, right - radius, bottom);
			}
			else {
				double radius = width * 0.5;
				end0 = new Circle(left + radius, top - radius, radius);
				end1 = new Circle(left + radius, bottom + radius, radius);
				body = new Rect(left, top - radius, right, bottom + radius);
			}
			bounds = new Rect(left, top, right, bottom);
		}
		public boolean contains(double px, double py)	{
			return body.contains(px, py) || end0.contains(px, py) || end1.contains(px, py);
		}
		public Rect bounds() {
			return bounds;
		}
	}
	
}
