package gerry;

import gerry.anim.Display;

import java.awt.Color;
import java.awt.geom.GeneralPath;
import java.util.List;
import java.util.Random;

// Given a set of clusters this will balance them and form them
public abstract class Balancer 
{	

	protected final Random rnd = new Random();
	private List<District> districts;
	private NodeGraph graph;
	private int iterations;
	protected Display display;
	
	public final void balance() {
		balance(districts, graph, iterations);
	}
	
	public abstract void balance(List<District> districts, NodeGraph graph, int iterations);
	
	public NodeGraph getGraph() {
		return graph;
	}

	public void setGraph(NodeGraph graph) {
		this.graph = graph;
	}
	
	public List<District> getDistricts() {
		return districts;
	}
	
	public void setDistricts(List<District> districts) {
		this.districts = districts;
	}
	
	public int getIterations() {
		return iterations;
	}
	
	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public Display getDisplay() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	protected void displayNode(Node n, int districts, long pause) {
		if (display != null) {
			Color fill = Color.white;
			if (n.district != null) {
				fill = getColor(n.district.index, districts);
			}
			GeneralPath path = n.getPath(display.getView());
			display.setPath(path, fill, fill.darker());
			if (pause > 0) {
				try {
					Thread.sleep(pause);
				} catch (Exception e) { }
			}
		}
	}
	
	protected Color getColor(int d, float max) {
		float x = Math.min(d / max, 1f);
		float b = 1.0f;
		if ((d & 1) == 1) {
			x = 1 - x;
			b = 0.7f;
		}
		if ((d & 3) == 3) {
			b = 0.4f;
		}
		return new Color(Color.HSBtoRGB(x, 1f, b));
	}
	
}
