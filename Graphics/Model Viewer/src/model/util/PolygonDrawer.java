package model.util;

import java.util.ArrayList;
import java.util.Collections;

public class PolygonDrawer {

	private final Canvas canvas;
	private Table table;
	private ArrayList<Edge> active;
	
	public PolygonDrawer(Canvas canvas) {
		this.canvas = canvas;
		this.table = new Table();
		this.active = new ArrayList<Edge>();
	}
	
	public void fillPolygon(int[] x, int[] y) {
		table.load(x, y);
		int scany = table.get(0).y;
		int index = active.size();
		do {
			Bucket b = table.take(scany);
			if (b != null) {
				active.addAll(b);
			}
			
			index = active.size();
			while (--index >= 0) {
				if (active.get(index).ymax == scany) {
					active.remove(index);
				}
			}
			
			Collections.sort(active);
			
			index = 0;
			while (index < active.size()) {
				int x1 = active.get(index++).xmin;
				int x2 = active.get(index++).xmin;
				canvas.drawScanline(scany, x1, x2);
			}
			
			scany++;
			
			for (Edge e : active) {
				e.update();
			}
			
		} while (!table.isEmpty() && !active.isEmpty());
	}

	private class Edge implements Comparable<Edge> {
		public int xmin, ymax, dx, dy, sx, den;
		public int set(int x1, int y1, int x2, int y2) {
			int buckety;
			if (y1 < y2) {
				xmin = x1;
				ymax = y2;
				buckety = y1;
			}
			else {
				xmin = x2;
				ymax = y1;
				buckety = y2;
			}
			int sdx = x1 - x2;
			int sdy = y1 - y2;
			dx = Math.abs(sdx);
			dy = Math.abs(sdy);
			den = 0;
			sx = Integer.signum(sdx * sdy);
			return buckety;
		}
		public void update() {
			if (dx == 0) {
				return;
			}
			den += dx;
			while (den > dy) {
				xmin += sx;
				den -= dy;
			}
		}
		public int compareTo(Edge e) {
			return (xmin - e.xmin);
		}
	}
	
	private class Bucket extends ArrayList<Edge> implements Comparable<Bucket> {
		public int y;
		public int compareTo(Bucket b) {
			return (y - b.y);
		}
	}
	
	private class Table extends ArrayList<Bucket> {
		public void load(int[] x, int[] y) {
			int size = x.length;
			int index = -1;
			int x1, y1, x2, y2;
			x1 = x[size - 1];
			y1 = y[size - 1];
			while (++index < size) {
				x2 = x[index];
				y2 = y[index];
				if (y1 != y2) {
					Edge e = new Edge();
					int buckety = e.set(x1, y1, x2, y2);
					addEdge(e, buckety);
				}
				x1 = x2;
				y1 = y2;
			}
			Collections.sort(this);
		}
		public Bucket take(int y) {
			return (isEmpty() || get(0).y != y ? null : remove(0));
		}
		private void addEdge(Edge e, int buckety) {
			for (Bucket b : this) {
				if (b.y == buckety) {
					b.add(e);
					return;
				}
			}
			Bucket b = new Bucket();
			b.y = buckety;
			b.add(e);
			add(b);
		}
	}
	
}
