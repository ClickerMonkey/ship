package gerry;

import java.util.HashSet;
import java.util.List;

public class BalancerDefault extends Balancer 
{
	private List<District> districts;
	private NodeGraph graph;
	private int iterations;
	private HashSet<Node> traded;
	private Node lastSwap;
	private District lastSwapDistrict;
	private int loopsDetected;
	private int swaps;
	
	@Override
	public void balance(List<District> theDistricts, NodeGraph theGraph, int theIterations) 
	{
		this.districts = theDistricts;
		this.graph = theGraph;
		this.iterations = theIterations;
		this.traded = new HashSet<Node>();
		this.lastSwap = null;
		this.lastSwapDistrict = null;
		this.loopsDetected = 0;
		this.swaps = 0;
		
		for (int i = 0; i < iterations; i++) 
		{
			District src = null;
			District dst = null;
			if (rnd.nextDouble() < 0.7) {
				dst = graph.getSmallestDistrict(districts);
				if (rnd.nextDouble() < 0.9) {
					src = graph.getLargestNeighbor(dst, districts);	
				}
				else { 
					src = graph.getRandomNeighbor(dst, districts);
				}
			}
			else {
				src = graph.getLargestDistrict(districts);
				if (rnd.nextDouble() < 0.9) {
					dst = graph.getSmallestNeighbor(src, districts);
				}
				else { 
					dst = graph.getRandomNeighbor(src, districts);
				}
			}
			
			if (dst == null || src == null) {
				continue;
			}
			
			Node swapping = null;
			if (rnd.nextDouble() < 0.5) {
				swapping = graph.getClosest(dst, src, traded);
			} 
			else {
				swapping = graph.getSmallest(dst, src, traded);
			}
			
			
			if (swapping == lastSwap) {
				traded.clear();
				loopsDetected++;
			}
			else if (swapping != null) {
				src.remove(swapping);
				dst.add(swapping);
				traded.add(swapping);
				displayNode(swapping, districts.size(), 10);
				swaps++;
				lastSwapDistrict = src;
			}
			lastSwap = swapping;
			
			/*=*/
			if (Math.random() < 0.4) {
//				District from = graph.getRandomDistrict(districts);
//				Node alone = graph.getMostAloneBorder(from);
				Node alone = graph.getMostAloneBorder(districts);
				District from = alone.district;
				District to = graph.getSmallestNeighbor(alone);
				if (to != null) {
					from.remove(alone);
					to.add(alone);
					displayNode(alone, districts.size(), 10);	
				}
			}
			/*=*/
			
			/*=* /
			if (i % 8 == 0) {
				for (District d : districts) {
					if (d.isOpen()) {
						Node inside = graph.getSurrounded(d);
						inside.district.remove(inside);
						d.add(inside);	
						displayNode(inside, DISTRICTS, 10);
					}
				}
			}
			/*=*/
			
			/*=*/
			if (Math.random() < 0.05) {
				for (District d : districts) {
					if (d.isOpen()) {
						Node farthest = graph.getFarthest(d);
						District closest = graph.getClosestNeighbor(farthest);
//						District smallest = graph.getSmallestNeighbor(farthest);
						if (closest != null) {
							d.remove(farthest);
							closest.add(farthest);
							displayNode(farthest, districts.size(), 10);	
						}
					}
				}
			}
			/*=*/
		}
		System.out.println("Loops detected: " + loopsDetected);
		System.out.println("Swaps: " + swaps);
		
	}
	
	public void undo() 
	{
		if (lastSwap != null && lastSwapDistrict != null) {
			District from = lastSwap.district;
			District to = lastSwapDistrict;
			from.remove(lastSwap);
			to.add(lastSwap);	
		}
	}
	
	public void to_SmallestDistrict_from_LargestNeighbor() {
		
	}
	public void to_SmallestDistrict_from_ClosestNeighbor() {
		
	}
	public void to_SmallestDistrict_from_RandomNeighbor() {
		
	}
	public void from_LargestDistrict_to_SmallestNeighbor() {
		
	}
	public void from_LargestDistrict_to_ClosestNeighbor() {
		
	}
	public void from_LargestDistrict_to_RandomNeighbor() {
		
	}
	public void find_MostAlone_to_SmallestNeighbor() {
		
	}
	public void find_Farthest_to_ClosestNeighbor() {
		
	}
	public void find_Farthest_to_SmallestNeighbor() {
		
	}
	

}
