package gerry;

import static gerry.Geometry.cross;
import static gerry.Geometry.distanceSq;

import gerry.anim.Display;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Nodifier 
{
	public static void main(String[] args) throws IOException {
		Loader loader = new Loader();
		List<City> cities = loader.load("pacities.clean.csv");
		
		Nodifier nody = new Nodifier(cities);
		
		Box bounds = nody.getBounds();
		Viewport view = Viewport.fromWidth(bounds, 800);
		
		nody.display = Display.create(view, Color.white);
		nody.nodify();
		
		System.out.println("DONE");
	}
	
	
	private List<City> cities;
	private Display display;
	
	public Nodifier(List<City> cities) {
		this(cities, null);
	}
	
	public Nodifier(List<City> cities, Display display) {
		this.cities = cities;
		this.display = display;
	}
	
	public void setDisplay(Display display) {
		this.display = display;
	}
	
	public NodeGraph nodify()
	{
		List<Node> nodes = createNodes();
		PriorityQueue<Edge> edges = createEdges(nodes);
		
		List<Edge> placed = new ArrayList<Edge>();
		Edge[] last = new Edge[50];
		int p = 0;
		while (!edges.isEmpty()) {
			Edge e = edges.poll();
			// the 20th to last edge placed 
			if (e.weight > 0.3) {
				break;
			}
			boolean place = true;
			for (Edge w : placed) {
				if (cross(w, e)) {
					place = false;
					break;
				}
			}
			if (place) {
				last[p++] = e;
				if (p == last.length) {
					p = 0;
				}
				e.a.neighbors.add(e.b);
				e.b.neighbors.add(e.a);
				placed.add(e);
				if (display != null) {
					display.setLine(e.x0(), e.y0(), e.x1(), e.y1(), Color.black);
				}
			}
		}
		
		return new NodeGraph(nodes);
	}
	
	private List<Node> createNodes() {
		List<Node> nodes = new ArrayList<Node>(cities.size());
		for (City c : cities) {
			nodes.add(new Node(c));
		}
		return nodes;
	}

	private PriorityQueue<Edge> createEdges(List<Node> nodes) {
		int size = nodes.size();
		PriorityQueue<Edge> edges = new PriorityQueue<Edge>(size);
		for (int y = 0; y < size; y++) {
			Node p = nodes.get(y);
			for (int x = 0; x < y; x++) {
				Node q = nodes.get(x);
				edges.offer(new Edge(p, q, distanceSq(p, q)));
			}
		}
		return edges;
	}
	
	public Box getBounds() {
		Box bounds = new Box(cities.get(0));
		for (int i = 1; i < cities.size(); i++) {
			bounds.include(cities.get(i));
		}
		return bounds;
	}
	
}
