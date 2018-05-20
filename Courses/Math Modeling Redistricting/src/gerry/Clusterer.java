package gerry;

import gerry.anim.Display;

import java.awt.Color;
import java.awt.geom.GeneralPath;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

// Given a set of nodes this will create a set of clusters
public abstract class Clusterer 
{

	protected final Random rnd = new Random();
	private HashMap<String, Object> properties = new HashMap<String, Object>();
	private NodeGraph graph = null;
	private int districtCount = 0;
	protected Display display;
	
	public Clusterer() {
	}
	
	public Clusterer(NodeGraph graph, int districtCount) {
		this.graph = graph;
		this.districtCount = districtCount;
	}
	
	public List<District> cluster() {
		return cluster(graph, districtCount);
	}
	
	public abstract List<District> cluster(NodeGraph graph, int districtCount);

	public void set(String name, Object value) {
		System.out.format("%s: %s\n", name, value.toString());
		properties.put(name, value);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String name) {
		return (T)properties.get(name);
	}
	
	public HashMap<String, Object> getProperties() {
		return properties;
	}
	
	public NodeGraph getGraph() {
		return graph;
	}

	public void setGraph(NodeGraph graph) {
		this.graph = graph;
	}

	public int getDistrictCount() {
		return districtCount;
	}

	public void setDistrictCount(int districtCount) {
		this.districtCount = districtCount;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}
	
	public Display getDisplay() {
		return display;
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
