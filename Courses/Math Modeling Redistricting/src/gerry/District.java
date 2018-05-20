package gerry;

import java.util.ArrayList;
import java.util.List;

public class District 
{
	
	public List<Node> nodes;
	public double tx, ty;
	public long population;
	public int index;
	public final boolean fixed;
	
	public District(Node center, int index, boolean fixed) {
		this.nodes = new ArrayList<Node>();
		this.index = index;
		this.fixed = fixed;
		this.add(center);
	}
	
	public void add(Node n) {
		place(n, 1);
		n.district = this;
		nodes.add(n);
	}
	
	public void remove(Node n) {
		place(n, -1);
		n.district = null;
		nodes.remove(n);
	}
	
	private void place(Node n, int sign) {
		tx += n.city.x * sign;
		ty += n.city.y * sign;
		population += n.city.population * sign;
	}
	
	public float cx() {
		return (float)(tx / nodes.size());
	}
	
	public float cy() {
		return (float)(ty / nodes.size());
	}
	
	public boolean isOpen() {
		return !fixed;
	}
	
}