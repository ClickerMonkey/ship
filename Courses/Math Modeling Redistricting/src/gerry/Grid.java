package gerry;

public class Grid 
{
	
	public GridCell[][] cells;
	public Box bounds;
	
	public Grid(Box bounds, int cellsWide, int cellsHigh) {
		this.bounds = bounds;
		this.cells = new GridCell[cellsHigh][cellsWide];
	}
	
	public void add(Edge e) {
		add(new EdgeRect(e));
	}
	
	public void add(EdgeRect x) {
		Pixel s = getStart(x);
		Pixel e = getEnd(x);
		for (int yy = s.y; yy <= e.y; yy++) {
			for (int xx = s.x; xx <= e.x; xx++) {
				cells[yy][xx].rects.add(x);
			}
		}
	}
	
	public boolean crosses(Edge e) {
		return crosses(new EdgeRect(e));
	}
	
	public boolean crosses(EdgeRect x) {
		Pixel s = getStart(x);
		Pixel e = getEnd(x);
		for (int yy = s.y; yy <= e.y; yy++) {
			for (int xx = s.x; xx <= e.x; xx++) {
				for (EdgeRect r : cells[yy][xx].rects) {
					if (r.crosses(x)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private Pixel getStart(EdgeRect x) {
		Pixel p = new Pixel();
		p.x = (int)(Math.max(0f, bounds.dx(x.l)) * cells[0].length); 
		p.y = (int)(Math.max(0f, bounds.dy(x.b)) * cells.length); 
		return p;
	}
	
	private Pixel getEnd(EdgeRect x) {
		Pixel p = new Pixel();
		p.x = (int)(Math.min(1f, bounds.dx(x.r)) * cells[0].length); 
		p.y = (int)(Math.min(1f, bounds.dy(x.b)) * cells.length); 
		return p;
	}
	
	
}