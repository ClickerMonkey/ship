package gerry;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ClustererGravity extends Clusterer 
{

	@Override
	public List<District> cluster(NodeGraph graph, int districtCount) 
	{
		final int DISTRICTS = districtCount;
		
		List<Node> nodes = graph.getNodes();
		List<District> districts = new ArrayList<District>(districtCount);
		
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
		
		// Build intial
		for (int i = 0; i < districtCount; i++) {
			Vector cog = graph.getCenterOfGravity(null);
			Node center = graph.getClosest(cog.x, cog.y);
			District d = new District(center, districts.size(), false);
			displayNode(center, DISTRICTS, 5);
			while (d.population < goal) {
				Node closest = graph.getClosestBordering(d);
				if (closest == null) {
					break;
				}
				d.add(closest);	
				displayNode(closest, DISTRICTS, 5);
			}
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
