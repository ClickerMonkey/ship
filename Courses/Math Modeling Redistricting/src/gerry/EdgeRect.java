package gerry;

import static gerry.Geometry.*;

public class EdgeRect 
{

	public float l, r, t, b;
	public Edge edge;

	public EdgeRect(Edge e) {
		edge = e;
		l = Math.min(e.a.city.x, e.b.city.x);
		r = Math.max(e.a.city.x, e.b.city.x);
		t = Math.min(e.a.city.y, e.b.city.y);
		b = Math.max(e.a.city.y, e.b.city.y);
	}

	public boolean intersects(EdgeRect x) {
		return !(x.l > r || x.r < l || x.t > b || x.b < t);
	}

	public boolean crosses(EdgeRect x) {
		return intersects(x) && cross(edge, x.edge);
	}
	
}