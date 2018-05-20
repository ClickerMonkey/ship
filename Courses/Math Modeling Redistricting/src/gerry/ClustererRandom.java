package gerry;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import static gerry.Geometry.*;

public class ClustererRandom extends Clusterer 
{

	@Override
	public List<District> cluster(NodeGraph graph, int districtCount) 
	{
		final int DISTRICTS = districtCount;
		
		List<Node> nodes = graph.getNodes();
		List<District> districts = new ArrayList<District>(districtCount);
		Box bounds = graph.getBounds();
		
		if (display != null) {
			display.clear(Color.white);
			for (Node n : nodes) {
				displayNode(n, DISTRICTS, 0);
			}
		}
		
		long totalPopulation = graph.getTotalPopulation();
		long goal = totalPopulation / districtCount;

		set("Initial Total Population", totalPopulation);
		set("Initial Goal Population", goal);
		
		for (int i = 0; i < nodes.size(); i++) {
			Node node = nodes.get(i);
			City city = node.city;
			if (city.population > goal) {
				totalPopulation -= city.population;
				districtCount--;
				goal = totalPopulation / districtCount;
				districts.add(  new District(node, districts.size(), true) );
				displayNode(node, DISTRICTS, 5);
			}
		}
		
		set("Final Total Population", totalPopulation);
		set("Final Districts", districtCount);
		set("Outliers", districts.size());
		set("Final Goal Population", goal);
		
		float area = bounds.width() * bounds.height();
		float barrier = (area / districtCount) * 0.5f;
		
		// Build intial
		for (int i = 0; i < districtCount; i++) {
			float cx = 0;
			float cy = 0;
			boolean finding = true;
			do {
				cx = (bounds.r - bounds.l) * rnd.nextFloat() + bounds.l;
				cy = (bounds.t - bounds.b) * rnd.nextFloat() + bounds.b;
				finding = false;
				for (District d : districts) {
					if (d.isOpen()) {
						if (distanceSq(d.cx(), d.cy(), cx, cy) < barrier) {
							finding = true;
						}	
					}
				}
			} while (finding);
			
			Node center = graph.getClosest(cx, cy);
			District d = new District(center, districts.size(), false);
			displayNode(center, DISTRICTS, 5);
			districts.add(d);
		}
		
		// Find nodes without districts
		int without = 0;
		for (Node n : nodes) {
			if (!n.hasDistrict()) {
				without++;
			}
		}
		set("Without District", without);
		
		// Add nodes to neighboring districts
		while (without > 0) {
			int last = without;
			for (District d : districts) {
				if (d.isOpen() && d.population < goal) {
					Node closest = graph.getClosestBordering(d);
					if (closest != null) {
						d.add(closest);
						without--;
						displayNode(closest, DISTRICTS, 5);	
					}	
				}
			}
			if (last == without) {
				break;
			}
		}
		
		while (without > 0) {
			for (Node n : nodes) {
				if (!n.hasDistrict()) {
					District d = graph.getSmallestNeighbor(n);
					if (d == null) {
						d = graph.getClosestNeighbor(n);
					}
					if (d != null) {
						d.add(n);
						without--;
						displayNode(n, DISTRICTS, 5);	
					}
				}
			}
		}
		
		return districts;
	}
	
}
