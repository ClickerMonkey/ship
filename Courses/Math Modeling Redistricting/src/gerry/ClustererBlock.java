package gerry;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ClustererBlock extends Clusterer {

	private float focusx = 1.0f;
	private float focusy = 0.0f;
	
	@Override
	public List<District> cluster(NodeGraph graph, int districtCount) 
	{
		final int DISTRICTS = districtCount;
		
		List<Node> nodes = graph.getNodes();
		List<District> districts = new ArrayList<District>(districtCount);
		Box bounds = graph.getBounds();
		
		float cx = (bounds.r - bounds.l) * focusx + bounds.l;
		float cy = (bounds.t - bounds.b) * focusy + bounds.b;
		
		if (display != null) {
			display.clear(Color.white);
			for (Node n : nodes) {
				displayNode(n, DISTRICTS, 0);
			}
		}
		
		long totalPopulation = graph.getTotalPopulation();
		long avg = totalPopulation / districtCount;
		
		for (int i = 0; i < nodes.size(); i++) {
			Node node = nodes.get(i);
			City city = node.city;
			if (city.population > avg) {
				totalPopulation -= city.population;
				districtCount--;
				avg = totalPopulation / districtCount;
				districts.add(  new District(node, districts.size(), true) );
				displayNode(node, DISTRICTS, 5);
			}
		}
		
		long desired = totalPopulation / districtCount;
		
		// Build intial
		for (int i = 0; i < districtCount; i++) {
			Node center = graph.getClosest(cx, cy);
			District d = new District(center, districts.size(), false);
			displayNode(center, DISTRICTS, 5);
			while (d.population < desired) {
				Node closest = graph.getClosestBordering(d);
				if (closest == null) {
					break;
				}
				d.add(closest);	
				displayNode(closest, DISTRICTS, 5);
			}
			districts.add(d);
		}
		
		int without = 0;
		for (Node n : nodes) {
			if (!n.hasDistrict()) {
				without++;
			}
		}
		System.out.println("Node without district: " + without);
		
		while (without > 0) {
			for (Node n : nodes) {
				if (!n.hasDistrict()) {
					District dst = graph.getNeighborDistrict(n);
					if (dst != null) {
						dst.add(n);
						without--;
						displayNode(n, DISTRICTS, 5);
					}
				}
			}
		}
		
		
		return districts;
	}
	
}
