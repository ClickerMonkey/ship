package gerry;

import static gerry.Geometry.distanceSq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class NodeGraph {

	private final Random rnd = new Random();
	private List<Node> nodes;
	
	public NodeGraph(List<Node> nodes) {
		this.nodes = nodes;
	}
	
	public int size() {
		return nodes.size();
	}
	
	public List<Node> getNodes() {
		return nodes;
	}
	
	public boolean isConcave(District d) {
		float cx = d.cx();
		float cy = d.cy();
		
		return (cx == cy);
	}
	
	public Vector getCenterOfGravity(District d) {
		double totalWeight = 0.0;
		for (Node n : nodes) {
			if (n.district == d) {
				totalWeight += n.weight();
			}
		}
		Vector center = new Vector();
		for (Node n : nodes) {
			if (n.district == d) {
				center.x += (n.weight() / totalWeight) * n.x();
				center.y += (n.weight() / totalWeight) * n.y();
			}
		}
		return center;
	}

	// To give up the most alone node
	public Node getMostAloneBorder(District d) {
		Node alone = null;
		int aloneNeighbors = Integer.MAX_VALUE;
		for (Node n : d.nodes) {
			int joined = getNeighbors(n, d); 
			if (joined < aloneNeighbors) {
				alone = n;
				aloneNeighbors = joined;
			}
		}
		return alone;
	}

	// To give up the most alone node
	public Node getMostAloneBorder(List<District> districts) {
		Node alone = null;
		int aloneNeighbors = Integer.MAX_VALUE;
		for (District d : districts) {
			if (d.isOpen()) {
				for (Node n : d.nodes) {
					int joined = getNeighbors(n, d); 
					int other = n.neighbors.size() - joined;
//					if (joined < aloneNeighbors && other > 0) {
					if ((alone == null || other > aloneNeighbors) && !isBridge(n)) {
						alone = n;
//						aloneNeighbors = joined;
						aloneNeighbors = other;
					}
				}		
			}
		}
		return alone;
	}
	
	// To give up the furthest away node
	public Node getFarthest(District in) {
		float cx = in.cx();
		float cy = in.cy();
		float farthestSq = -Float.MAX_VALUE;
		Node farthest = null;
		for (Node n : in.nodes) {
			float sq = distanceSq(n.x(), n.y(), cx, cy);
			if ((farthest == null || sq > farthestSq) && !isBridge(n)) {
				farthest = n;
				farthestSq = sq;
			}
		}
		return farthest;
	}
	
	// Returns the smallest neighbor to the given node which is not its own district.
	public District getSmallestNeighbor(Node n) {
		District smallest = null;
//		if (getNeighbors(n, n.district) >= 2) {
			for (Node m : n.neighbors) {
				District next = m.district;
				if (next != n.district && next.isOpen()) {
					if (smallest == null || smallest.population > next.population) {
						smallest = next;
					}
				}
			}
//		}
		return smallest;
	}
	
	// Returns the smallest neighbor to the given node which is not its own district.
	public District getClosestNeighbor(Node n) {
		District closest = null;
		float closestSq = Float.MAX_VALUE;
		for (Node m : n.neighbors) {
			District next = m.district;
			if (next != n.district && next.isOpen()) {
				float sq = distanceSq(m.x(), m.y(), n.x(), n.y());
				if (closest == null || sq < closestSq) {
					closest = next;
					closestSq = sq;
				}
			}
		}
		return closest;
	}
	
	// Returns the node most surrounded but does not exist in the given district
	public Node getSurrounded(District by) {
		float cx = by.cx();
		float cy = by.cy();
		float closestSq = -Float.MAX_VALUE;
		Node closest = null;
		for (Node n : by.nodes) {
			for (Node m : n.neighbors) {
				if (m.district != by) {
					float sq = distanceSq(m.x(), m.y(), cx, cy);
					if (closest == null || sq > closestSq) {
						closest = m;
						closestSq = sq;
					}
				}
			}
		}
		return closest;
	}
	
	
	// Gets the closest node in "from" to "to"
	public Node getClosest(District to, District from, HashSet<Node> exclude) {
		float cx = to.cx();
		float cy = to.cy();
		Node closest = null;
		float closestSq = Float.MAX_VALUE;
		for (Node n : from.nodes) {
			float sq = distanceSq(n.x(), n.y(), cx, cy);
			if ((closest == null || sq < closestSq) && getNeighbors(n, to) >= 2 && !exclude.contains(n) && !isBridge(n)) {
				closest = n;
				closestSq = sq;
			}
		}
		return closest;
	}

	// Gets the closest node in "from" to "to"
	public Node getSmallest(District to, District from, HashSet<Node> exclude) {
		Node closest = null;
		for (Node n : from.nodes) {
			if ((closest == null || closest.weight() > n.weight()) && getNeighbors(n, to) >= 2 && !exclude.contains(n) && !isBridge(n)) {
				closest = n;
			}
		}
		return closest;
	}
	
	// Closest untaken node bordering district
	public Node getClosestBordering(District district) 
	{
		float cx = district.cx();
		float cy = district.cy();
		Node closest = null;
		float closestSq = Float.MAX_VALUE;
		for (Node n : nodes) {
			if (n.district == null && getNeighbors(n, district) > 0) {
				float sq = distanceSq(n.x(), n.y(), cx, cy);
				if (closest == null || sq < closestSq) {
					closest = n;
					closestSq = sq;
				}
			}
		}
		return closest;
	}
	
	// Given an untaken node this will find a surrounding district to add it
	// to which has the lowest population of all the neighbors and return it. If
	// no neighbors exist in a district null will be returned.
	public District getNeighborDistrict(Node n) {
		District smallest = null;
		for (Node m : n.neighbors) {
			if (m.district != null) {
				if (smallest == null || smallest.population > m.district.population) {
					smallest = m.district;
				}
			}
		}
		return smallest;
	}

	// Smallest untaken node bordering district
	public Node getSmallestBordering(District district) 
	{
		Node smallest = null;
		for (Node n : nodes) {
			if (n.district == null && getNeighbors(n, district) > 0) {
				if (smallest == null || n.city.population < smallest.city.population) {
					smallest = n;
				}
			}
		}
		return smallest;
	}
	
	// Gets bordering untaken nodes of the given district and returns the list
	// sorted by closest to center of the district.
	public Queue<Node> getBordering(District district) {
		final float cx = district.cx();
		final float cy = district.cy();
		List<Node> nextList = new ArrayList<Node>();
		for (Node n : nodes) {
			if (n.district == null && getNeighbors(n, district) > 0) {
				nextList.add(n);
			}
		}
		Collections.sort(nextList, new Comparator<Node>() {
			public int compare(Node o1, Node o2) {
				float sq1 = distanceSq(o1.x(), o1.y(), cx, cy);
				float sq2 = distanceSq(o2.x(), o2.y(), cx, cy);
				return (int)StrictMath.signum(sq1 - sq2);
			}
		});
		Queue<Node> queue = new LinkedList<Node>();
		for (Node n : nextList) {
			queue.offer(n);	
		}
		return queue;
	}
	
	public District getSmallestDistrict(List<District> districts) {
		District smallest = null;
		for (District d : districts) {
			if (d.isOpen() && (smallest == null || smallest.population > d.population)) {
				smallest = d;
			}
		}
		return smallest;
	}
	
	public District getLargestDistrict(List<District> districts) {
		District largest = null;
		for (District d : districts) {
			if (d.isOpen() && (largest == null || largest.population < d.population)) {
				largest = d;
			}
		}
		return largest;
	}
	
	public District getRandomDistrict(List<District> districts) {
		District random = null;
		while (random == null) {
			District next = districts.get(rnd.nextInt(districts.size()));
			if (next.isOpen()) {
				random = next;
			}
		}
		return random;
	}
	
	public District getLargestNeighbor(District around, List<District> districts) {
		District largest = null;
		for (District d : districts) {
			if (d != around && d.isOpen() && isNeigbor(around, d)) {
				if (largest == null || largest.population < d.population) {
					largest = d;
				}
			}
		}
		return largest;
	}
	
	public District getSmallestNeighbor(District around, List<District> districts) {
		District smallest = null;
		for (District d : districts) {
			if (d != around && d.isOpen() && isNeigbor(around, d)) {
				if (smallest == null || smallest.population > d.population) {
					smallest = d;
				}
			}
		}
		return smallest;
	}
	
	public District getRandomNeighbor(District around, List<District> districts) {
		List<District> neighbors = new ArrayList<District>();
		for (District d : districts) {
			if (d != around && d.isOpen() && isNeigbor(around, d)) {
				neighbors.add(d);
			}
		}
		return neighbors.get(rnd.nextInt(neighbors.size()));
	}
	
	public boolean isNeigbor(District a, District b) {
		for (Node n : a.nodes) {
			if (getNeighbors(n, b) > 0) {
				return true;
			}
		}
		return false;
	}
	
	// Whether a neighbor has the district
	public int getNeighbors(Node n, District district) {
		int total = 0;
		for (Node m : n.neighbors) {
			if (m.district == district) {
				total++;
			}
		}
		return total;
	}
	
	// Finds root nodes of districts
	public Node getClosest(float x, float y) {
		Node closest = null;
		float closestSq = Float.MAX_VALUE;
		for (Node n : nodes) {
			if (n.district == null) {
				float sq = distanceSq(n.x(), n.y(), x, y);
				if (closest == null || sq < closestSq) {
					closest = n;
					closestSq = sq;
				}
			}
		}
		return closest;
	}
	
	public boolean isBridge(Node n) {
		for (Node x : n.neighbors) {
			for (Node y : n.neighbors) {
				if (x != y && x.district == n.district && y.district == n.district) {
					if (!canReach(x, n, y)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean canReach(Node start, Node ex, Node end) {
		Queue<Node> q = new LinkedList<Node>();
		boolean[] visited = new boolean[nodes.size()];
		visited[start.city.index] = true;
		q.offer(start);
		while (!q.isEmpty()) {
			Node curr = q.poll();
			for (Node m : curr.neighbors) {
				if (m == end) {
					return true;
				}
				if (m != ex && !visited[m.city.index] && m.district == start.district) {
					q.offer(m);
					visited[m.city.index] = true;
				}
			}
		}
		return false;
	}
	
 	public long getTotalPopulation() {
		long total = 0;
		for (Node n : nodes) {
			total += n.weight();
		}
		return total;
	}
	
	public Box getBounds() {
		Box bounds = new Box(nodes.get(0).city);
		for (int i = 1; i < nodes.size(); i++) {
			bounds.include(nodes.get(i).city);
		}
		return bounds;
	}
	
}
