package gerry;

import static gerry.Geometry.*;

public class Edge implements Comparable<Edge> 
{
	
	public final float weight;
	public final Node a, b;
	
	public Edge(Node a, Node b) {
		this(a, b, distanceSq(a.x(), a.y(), b.x(), b.y()));
	}
	
	public Edge(Node a, Node b, float weight) {
		this.a = a;
		this.b = b;
		this.weight = weight;
	}
	
	public float x0() {
		return a.city.x;
	}
	
	public float y0() {
		return a.city.y;
	}
	
	public float x1() {
		return b.city.x;
	}
	
	public float y1() {
		return b.city.y;
	}
	
	public int compareTo(Edge o) {
		return (int)Math.signum(weight - o.weight);
	}
	
}