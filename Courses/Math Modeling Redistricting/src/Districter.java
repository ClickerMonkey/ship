import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

import javax.imageio.ImageIO;


public class Districter {

	public static void main(String[] args) throws Exception {
		Districter d = new Districter();
		d.setDistrictCount(18);
		d.run();
	}
	
	public class City implements Comparable<City> {
		public String name;
		public int population; // people
		public double lng; // x
		public double lat; // y
		public City(String line) {
			String[] parts = line.split(",");
			name = parts[0];
			population = Integer.parseInt(parts[1]);
			lat = Double.parseDouble(parts[2]);
			lng = Double.parseDouble(parts[3]);
		}
		public int compareTo(City o) {
			return population - o.population;
		}
	}
	
	public class Viewport {
		private Box box;
		private int width;
		private int height;
		private Viewport(Box bounds, int width, int height) {
			this.box = bounds;
			this.width = width;
			this.height = height;
		}
		private Point convert(double x, double y) {
			Point p = new Point();
			p.x = (int)((x - box.l) / box.width());
			p.y = (int)((box.t - y) / box.height());
			return p;
		}
//		public static Viewport fromWidth(Box bounds, int width) {
//			return new Viewport(bounds, width, (int)(width * (bounds.height() / bounds.width()())));
//		}
	}
	
	public class Box {
		double r, l, t, b;
		public Box(City center) {
			l = r = center.lng;
			t = b = center.lat;
		}
		public void include(City c) {
			if (c.lat > t) t = c.lat;
			if (c.lat < b) b = c.lat;
			if (c.lng < l) l = c.lng;
			if (c.lng > r) r = c.lng;
		}
		public double width() {
			return (r - l);
		}
		public double height() {
			return (t - b);
		}
	}
	
	public class Point {
		public int x, y, index;
		public City city;
	}
	
	public class PointList extends ArrayList<Point> {
		public PointList(int capacity) {
			super(capacity);
		}
	}
	
	public class District {
		public List<Node> nodes;
		public long tx, ty;
		public long population;
		public int index;
		public District(Node center, int index) {
			this.nodes = new ArrayList<Node>();
			this.index = index;
			this.add(center);
		}
		public void add(Node n) {
			place(n, 1);
			nodes.add(n);
		}
		public void remove(Node n) {
			place(n, -1);
			nodes.remove(n);
		}
		private void place(Node n, int sign) {
			tx += n.x * sign;
			ty += n.y * sign;
			population += n.city.population * sign;
			n.district = index * sign;
		}
		public int cx() {
			return (int)(tx / nodes.size());
		}
		public int cy() {
			return (int)(ty / nodes.size());
		}
	}

	public class Node implements Comparable<Node> {
		public int x, y, index, district;
		public List<Node> neighbors;
		public City city;
		public Node(Point p) {
			x = p.x;
			y = p.y;
			index = p.index;
			city = p.city;
			neighbors = new ArrayList<Node>(4);
			district = -1;
		}
		public int compareTo(Node n) {
			return city.population - n.city.population;
		}
	}
	
	public class Edge implements Comparable<Edge> {
		public int j, k, weight;
		public Edge(int j, int k, int weight) {
			this.j = j;
			this.k = k;
			this.weight = weight;
		}
		public Edge(Node a, Node b) {
			this.j = a.index;
			this.k = b.index;
			this.weight = distanceSq(a.x, a.y, b.x, b.y);
		}
		public Edge(Node a, int x, int y) {
			this.j = this.k = a.index;
			this.weight = distanceSq(a.x, a.y, x, y);
		}
		public int compareTo(Edge o) {
			return weight - o.weight;
		}
	}
	
	public class Grid {
		Cell[][] cells;
		int width, height, widePower, highPower;
		public Grid(int width, int height, int widePower, int highPower) {
			this.width = width >> widePower;
			this.height = height >> highPower;
			this.widePower = widePower;
			this.highPower = highPower;
			this.cells = new Cell[height][width];
		}
		public void add(Rect x) {
			int l = Math.max(0, x.l >> widePower);
			int r = Math.min(width, x.r >> widePower);
			int t = Math.max(0, x.t >> highPower);
			int b = Math.min(height, x.b >> highPower);
			for (int xx = l; xx <= r; xx++) {
				for (int yy = t; yy <= b; yy++) {
					cells[yy][xx].rects.add(x);
				}
			}
		}
	}
	
	public class Cell {
		List<Rect> rects = new ArrayList<Rect>();
	}
	
	public class Rect {
		public int l, r, t, b;
		public Edge edge;
		public Rect(Edge e) {
			edge = e;
			l = Math.min(points.get(e.j).x, points.get(e.k).x);
			r = Math.max(points.get(e.j).x, points.get(e.k).x);
			t = Math.min(points.get(e.j).y, points.get(e.k).y);
			b = Math.max(points.get(e.j).y, points.get(e.k).y);
		}
		private boolean intersects(Rect x) {
			return !(x.l > r || x.r < l || x.t > b || x.b < t);
		}
		public boolean crosses(Rect x) {
			return intersects(x) && cross(edge, x.edge);
		}
	}
	
	private final int GRID_SCALE = 15;							// 32768
	
	private int districtCount;
	private long totalPopulation;
	private Box bounds;
	private List<City> cities;
	private PointList points;
	private List<District> districts;
	private List<District> clustered;
	private int[][] distances;
	private PriorityQueue<Edge> edges;
	private List<Node> nodes;
	
	public Districter() {
	}
	
	public void run() throws Exception {
		cities = load();
		System.out.format("Cities loaded [%d]\n", cities.size());
		
		bounds = getBounds();
		System.out.format("Bounds L[%.2f] T[%.2f] R[%.2f] B[%.2f]\n", bounds.l, bounds.t, bounds.r, bounds.b);
		
		districts = prune();
		System.out.format("Outlier districts [%d]\n", districts.size());
		for (District d : districts) {
			City c = d.nodes.get(0).city;
			System.out.format("%s [%d]\n", c.name, c.population);
		}
		
		points = translate();
		System.out.format("Points translated [%d]\n", points.size());
		
		distances = matrix();
		System.out.format("Distances matrix calculated\n");
		
		edges = edges();
		System.out.format("Edges made [%d]\n", edges.size());
		
		nodes = nodify();
		System.out.format("Nodes made [%d]\n", nodes.size());

		/* */
		int size = (1 << GRID_SCALE) >> 5;
		BufferedImage img = new BufferedImage(size << 1, size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gr = img.createGraphics();
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.translate(0, size);
		gr.scale(1, -1);
		gr.setColor(Color.black);
		for (Node n : nodes) {
			int x0 = n.x >> 4;
			int y0 = n.y >> 5;
			for (Node m : n.neighbors) {
				int x1 = m.x >> 4;
				int y1 = m.y >> 5;
				gr.drawLine(x0, y0, x1, y1);
			}
		}
		gr.dispose();
		ImageIO.write(img, "PNG", new File("pacities.web.png"));
		
		if (nodes.size() > 0) {
			System.exit(0);
		}
		/**/
		
		System.out.format("Clustering nodes into %d districts\n", districtCount);
		clustered = cluster1(); // cluster0()
		System.out.format("Complete\n");
		
		for (District d : clustered) {
			System.out.format("d [tx: %d, ty: %d, w: %d, s: %d]\n", d.tx, d.ty, d.population, d.nodes.size());
		}
		
		/* ADD SPACE AFTER NEXT ASTERISK TO COMMENT * /
		int size = (1 << GRID_SCALE) >> 5;
		BufferedImage img = new BufferedImage(size * 2, size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gr = img.createGraphics();
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.translate(0, size);
		gr.scale(1, -1);
		int TOTAL_COLORS = districts.size() + clustered.size();
		float HALF_COLORS = TOTAL_COLORS * 0.5f;
		Color[] COLORS = new Color[TOTAL_COLORS];
		for (int i = 0; i < TOTAL_COLORS; i++) {
			if (i >= HALF_COLORS) {
				COLORS[i] = new Color(Color.HSBtoRGB((i-HALF_COLORS) / HALF_COLORS, 0.5f, 0.5f));	
			}
			else {
				COLORS[i] = new Color(Color.HSBtoRGB(i / HALF_COLORS, 1.0f, 1.0f));	
			}
		}
		final int CITY_RADIUS = 4;
		final int CITY_DIAM = CITY_RADIUS << 1;
		for (Node n : nodes) {
			gr.setColor(COLORS[n.district]);
			gr.fillOval((n.x >> 4)-CITY_RADIUS, (n.y >> 5)-CITY_RADIUS, CITY_DIAM, CITY_DIAM);
		}
		for (District d : clustered) {
			gr.setColor(COLORS[d.index]);
			for (Node n : d.nodes) {
				int x0 = n.x >> 4;
				int y0 = n.y >> 5;
				for (Node m : n.neighbors) {
					if (m.district == n.district) {
						int x1 = m.x >> 4;
						int y1 = m.y >> 5;
						gr.drawLine(x0, y0, x1, y1);
					}
				}
			}
		}
		for (District d : districts) {
			gr.setColor(COLORS[d.index + clustered.size()]);
			gr.fillOval((int)(d.tx >> 4)-CITY_RADIUS, (int)(d.ty >> 5)-CITY_RADIUS, CITY_DIAM, CITY_DIAM);
		}
		gr.dispose();
		ImageIO.write(img, "PNG", new File("pacities.cluster.png"));
		/* END */
	}
	
	private List<City> load() throws Exception
	{
		Scanner input = new Scanner(
				new BufferedInputStream(
						new FileInputStream("pacities.clean.csv")));

		List<City> cities = new ArrayList<City>(2048);
		
		while (input.hasNextLine()) {
			cities.add(new City(input.nextLine()));
		}

		input.close();

		Collections.sort(cities);
		
		return cities;
	}

	private List<District> prune()
	{
		List<District> districts = new ArrayList<District>(districtCount);
		
		totalPopulation = getTotalPopulation();
		
		City largest = null;
		double avg = 0.0;
		int last = -1;
		int scale = (1 << GRID_SCALE) - 1;
		
		do {
			avg = totalPopulation / (double)districtCount;
			last = cities.size() - 1;
			largest = cities.get(last);
			if (largest.population >= avg) 
			{
				cities.remove(last);
				districtCount--;
				totalPopulation -= largest.population;
				districts.add(new District(new Node(convert(largest, bounds, last, scale)), 0));
			}
		} 
		while (largest.population >= avg);
		
		return districts;
	}
	
	private PointList translate() 
	{
		int size = cities.size();
		int scale = (1 << GRID_SCALE) - 1;
		PointList points = new PointList(size);
		for (int i = 0; i < size; i++) {
			points.add(convert(cities.get(i), bounds, i, scale));
		}
		return points;
	}
	
	private int[][] matrix() {
		int size = points.size();
		int[][] d = new int[size][];
		for (int i = 0; i < size; i++) {
			d[i] = new int[i + 1];
		}
		for (int i = 0; i < size; i++) {
			Point p = points.get(i);
			for (int j = 0; j < i; j++) {
				Point q = points.get(j);
				d[i][j] = distanceSq(p.x, p.y, q.x, q.y);
			}
		}
		return d;
	}

	private int distanceSq(int x1, int y1, int x2, int y2) {
		int dx = x1 - x2;
		int dy = y1 - y2;
		return (dx * dx + dy * dy);
	}
	
	private PriorityQueue<Edge> edges()
	{
		int size = points.size();
		PriorityQueue<Edge> edges = new PriorityQueue<Edge>(size);
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < y; x++) {
				edges.offer(new Edge(y, x, distances[y][x]));
			}
		}
		return edges;
	}
	
	private List<Node> nodify() {
		List<Node> nodes = new ArrayList<Node>(); 
		for (Point p : points) {
			nodes.add(new Node(p));
		}
		
		List<Edge> placed = new ArrayList<Edge>();
		Edge[] last = new Edge[40];
		int p = 0;
		while (!edges.isEmpty()) {
			Edge e = edges.poll();
			// the 20th to last edge placed 
			if (e.weight >= 38033570) {
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
				
				Node n1 = nodes.get(e.j);
				Node n2 = nodes.get(e.k);
				n1.neighbors.add(n2);
				n2.neighbors.add(n1);
				placed.add(e);
			}
		}
		
		// Prints out the last 20, to know the cut off point to really cut down
		// computation.
//		int x = p;
//		do {
//			System.out.println(last[x].weight);
//			x = (x + 1) % last.length;
//		} while (p != x);
		
		return nodes;
	}
	
	private List<District> cluster0() 
	{
		// Push the nodes to a map
		Map<Integer, Node> nodeMap = new HashMap<Integer, Node>(nodes.size());
		for (Node n : nodes) {
			nodeMap.put(n.index, n);
		}
		
		// Create districtCount number of seeds
		long basePopulation = 0;
		PriorityQueue<Node> seedQueue = new PriorityQueue<Node>();
		for (Node n : nodes) {
			seedQueue.offer(n);
		}
		while (seedQueue.size() > districtCount) {
			seedQueue.poll();
		}
		List<District> districts = new ArrayList<District>(districtCount);
		for (int i = 0; i < districtCount; i++) {
			Node seed = seedQueue.poll();
			basePopulation += seed.city.population;
			districts.add(new District(seed, districts.size()));
			nodeMap.remove(seed.index);
		}
		                      
		long desired = (totalPopulation - basePopulation) / districtCount;
		boolean placed = false;
		
		// Continue to pick off nodes from the map until its empty
		while (!nodeMap.isEmpty()) {
			// Keep track of the closest node and its district
			Node closest = null;
			int closestWeight = -1;
			int closestDistrict = -1;
			for (District d : districts) {
				if (d.population >= desired && placed == true) {
//					continue;
				}
				int cx = d.cx();
				int cy = d.cy();
				for (Node n : d.nodes) {
					for (Node m : n.neighbors) {
						if (m.district < 0) {
							int weight = distanceSq(m.x, m.y, cx, cy);
							if (closest == null || weight < closestWeight) {
								closest = m;
								closestWeight = weight;
								closestDistrict = d.index;
							}
						}
					}
				}
			}
			placed = (closest != null);
			if (closest != null) {
				districts.get(closestDistrict).add(closest);
				nodeMap.remove(closest.index);
			}
		}
		
		// Continually grab the border cities and find the best to swap
		final int ITERATIONS = 10000;
		PriorityQueue<Node> nodeQueue = new PriorityQueue<Node>();
		for (int i = 0; i < ITERATIONS; i++) {
			nodeQueue.clear();
			// For each district...
			for (District d : districts) {
				// If the district is not large enough to take cities, skip
				// it and goto the next district
				if (d.population < desired) {
					continue;
				}
				
				// For each city in the district...
				for (Node n : d.nodes) {
					int sameDistricts = 0;
					int difDistricts = 0;
					// If any of its neighbors are in a different district..
					for (Node m : n.neighbors) {
						if (m.district != n.district) {
							difDistricts++;
						}
						else {
							sameDistricts++;
						}
					}	
					// Only swap it if more or equal of its neighbors are 
					// different districts other than same district
					if (difDistricts > sameDistricts) {
//					if (difDistricts >= 1) {
//					if (sameDistricts >= 2) {
						nodeQueue.offer(n);
					}
				}
			}
			
			while (!nodeQueue.isEmpty()) {
				Node smallest = nodeQueue.poll();
				District current = districts.get(smallest.district);
				District smaller = null;
				for (Node m : smallest.neighbors) {
					District next = districts.get(m.district);
					if (next != current) {
						if (smaller == null || smaller.population > next.population) {
							smaller = next;
						}	
					}
				}
				if (smaller != null) {
//					if (smaller.population < current.population - smallest.city.population) {
						current.remove(smallest);
						smaller.add(smallest);	
//					}	
				}
			}
		}
		
		return districts;
	}
	
	private List<District> cluster1() {
		
		// Build each district
		final int dx = (1 << GRID_SCALE); //or 0
		final int dy = 0; //(1 << GRID_SCALE); //or 0
		
		long desired = totalPopulation / districtCount;
		
		List<District> districts = new ArrayList<District>();
		
		for (int i = 0; i < districtCount; i++) {
			Node center = getClosest(dx, dy);
			District d = new District(center, i);
			for (;;) {
				Queue<Node> queue = getNext(i, d.cx(), d.cy());
				boolean added = false;
				for (Node best = queue.poll(); best != null; best = queue.poll()) {
					if (/*best.city.population +*/ d.population < desired) {
						d.add(best);
						added = true;
						break;
					}
				}
				if (!added) {
					break;
				}
			}
			districts.add(d);
		}

		final int ITERATIONS = 1000;
		Node lastSwap = null;
		HashSet<Node> exclusion = new HashSet<Node>();
		int loopsDetected = 0;
		for (int i = 0; i < ITERATIONS; i++) {
			District min = getSmallest(districts);
			District max = getLargestNeighbor(min, districts);
			
			int cx = min.cx();
			int cy = min.cy();
			Node closest = null;
			int closestSq = 0;
			for (Node n : max.nodes) {
				int sq = distanceSq(n.x, n.y, cx, cy);
				if ((closest == null || sq < closestSq) && getNeighbors(n, min.index) >= 1 && !exclusion.contains(n) && !isBridge(n)) {
					closest = n;
					closestSq = sq;
				}
			}
			
			if (closest == lastSwap) {
				exclusion.add(closest);
				if (exclusion.size() > 5) {
					exclusion.clear();
				}
				lastSwap = closest;
				loopsDetected++;
			}
			else if (closest != null) {
				max.remove(closest);
				min.add(closest);
				lastSwap = closest;
			}
//			Node smallest = null;
//			for (Node n : max.nodes) {
//				if (getNeighbors(n, min.index) >= 2) {
//					if (smallest == null || smallest.city.population > n.city.population) {
//						if (!isBridge(n)) {
//							smallest = n;	
//						}
//					}
//				}
//			}
//			Node smallest = getClosest(min, min.cx(), min.cy(), districts);
//			if (smallest != null) {
//				max.remove(smallest);
//				min.add(smallest);	
//			}
			else {
				System.out.println("BROKE AT " + i);
				break;
			}
		}
		System.out.println("Loops detected: " + loopsDetected);
		
		int loners = 0;
		for (Node n : nodes) {
			if (getNeighbors(n, n.district) == 0) {
				District from = districts.get(n.district);
				District to = districts.get(n.neighbors.get(0).district);
				from.remove(n);
				to.add(n);
				loners++;
			}
		}
		System.out.println(loners);
		
//		// Get smallest district
//		District smallest = null;
//		for (District d : districts) {
//			if (smallest == null || d.population < smallest.population) {
//				smallest = d;
//			}
//		}
//		
//		if (smallest == null) {
//			throw new NullPointerException();
//		}
//
//		long minPopulation = Long.MAX_VALUE;
//		long maxPopulation = Long.MIN_VALUE;
//		for (District d : districts) {
//			if (d != smallest) {
//				minPopulation = Math.min(minPopulation, d.population);
//				maxPopulation = Math.max(maxPopulation, d.population);
//			}
//		}
//		long dif = maxPopulation - minPopulation;
//		
//		while (smallest.population < desired - dif) {
//			Queue<Node> border = getBordering(smallest.index, smallest.cx(), smallest.cy());
//			boolean added = false;
//			for (;;) {
//				Node next = border.poll();
//				if (next == null) {
//					break;
//				}
//				District nextD = districts.get(next.district);
//				if (nextD.population - next.city.population > desired - dif) {
//					nextD.remove(next);
//					smallest.add(next);
//					added = true;
//					break;
//				}
//			}
//			if (!added) {
//				break;
//			}
//		}
		
		return districts;
	}

	// Gets bordering untaken nodes of the given district and returns the list
	// sorted by closest to center of the district.
	private Queue<Node> getNext(int district, final int cx, final int cy) {
		List<Node> nextList = new ArrayList<Node>();
		for (Node n : nodes) {
			if (n.district != district && n.district < 0 && getNeighbors(n, district) > 0) {
				nextList.add(n);
			}
		}
		Collections.sort(nextList, new Comparator<Node>() {
			public int compare(Node o1, Node o2) {
				int sq1 = distanceSq(o1.x, o1.y, cx, cy);
				int sq2 = distanceSq(o2.x, o2.y, cx, cy);
				return sq1 - sq2;
			}
		});
		Queue<Node> queue = new LinkedList<Node>();
		for (Node n : nextList) {
			queue.offer(n);	
		}
		return queue;
	}
	
	private District getSmallest(List<District> districts) {
		District smallest = null;
		for (District d : districts) {
			if (smallest == null || smallest.population > d.population) {
				smallest = d;
			}
		}
		return smallest;
	}
	
	private District getLargestNeighbor(District around, List<District> districts) {
		District largest = null;
		for (District d : districts) {
			if (d == around) {
				continue;
			}
			if (isNeigbor(around, d)) {
				if (largest == null || largest.population < d.population) {
					largest = d;
				}
			}
		}
		return largest;
	}
	
	private boolean isNeigbor(District a, District b) {
		for (Node n : a.nodes) {
			if (getNeighbors(n, b.index) > 0) {
				return true;
			}
		}
		return false;
	}
	
	private Node getSwap(int district) {
		Node smallest = null;
		for (Node n : nodes) {
			if (n.district != district && getNeighbors(n, district) >= 2) {
				if (!isBridge(n)) {
					if (smallest == null || smallest.city.population < n.city.population) {
						smallest = n;
					}	
				}
			}
		}
		return smallest;
	}
	
	private Queue<Node> getBordering(int district, final int cx, final int cy) {
		List<Node> nextList = new ArrayList<Node>();
		for (Node n : nodes) {
			if (n.district != district && getNeighbors(n, district) > 0) {
				nextList.add(n);
			}
		}
		Collections.sort(nextList, new Comparator<Node>() {
			public int compare(Node o1, Node o2) {
				int sq1 = distanceSq(o1.x, o1.y, cx, cy);
				int sq2 = distanceSq(o2.x, o2.y, cx, cy);
				return sq1 - sq2;
			}
		});
		Queue<Node> queue = new LinkedList<Node>();
		for (Node n : nextList) {
			queue.offer(n);	
		}
		return queue;
	}
	
	private Node getClosest(District toDistrict, int cx, int cy, List<District> districts) {
		Node closest = null;
		int closestSq = 0;
		for (Node n : nodes) {
			District fromDistrict = districts.get(n.district);
			if (toDistrict.population < fromDistrict.population && fromDistrict != toDistrict && getNeighbors(n, toDistrict.index) >= 2) {
				int sq = distanceSq(cx, cy, n.x, n.y);
				if (closest == null || closestSq > sq) {
					closest = n;
					closestSq = sq;
				}
			}
		}
		return closest;
	}
	
	// Whether a neighbor has the district
	private int getNeighbors(Node n, int district) {
		int total = 0;
		for (Node m : n.neighbors) {
			if (m.district == district) {
				total++;
			}
		}
		return total;
	}
	
	// Finds root nodes of districts
	private Node getClosest(int x, int y) {
		Node closest = null;
		int closestSq = 0;
		for (Node n : nodes) {
			if (n.district < 0) {
				int sq = distanceSq(n.x, n.y, x, y);
				if (closest == null || sq < closestSq) {
					closest = n;
					closestSq = sq;
				}
			}
		}
		return closest;
	}
	
	private boolean isBridge(Node n) {
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

	private boolean canReach(Node start, Node ex, Node end) {
		Queue<Node> q = new LinkedList<Node>();
		boolean[] visited = new boolean[nodes.size()];
		visited[start.index] = true;
		q.offer(start);
		while (!q.isEmpty()) {
			Node curr = q.poll();
			for (Node m : curr.neighbors) {
				if (m == end) {
					return true;
				}
				if (m != ex && !visited[m.index] && m.district == start.district) {
					q.offer(m);
					visited[m.index] = true;
				}
			}
		}
		return false;
	}
	
 	private void setDistrictCount(int count) {
		districtCount = count;
	}
	
	private long getTotalPopulation() {
		long total = 0;
		for (City c : cities) {
			total += c.population;
		}
		return total;
	}
	
	private Box getBounds() {
		Box bounds = new Box(cities.get(0));
		for (int i = 1; i < cities.size(); i++) {
			bounds.include(cities.get(i));
		}
		return bounds;
	}
	
	private Point convert(City c, Box bounds, int index, int scale) {
		Point p = new Point();
		p.x = (int)(((c.lng - bounds.l) / bounds.width()) * scale);
		p.y = (int)(((c.lat - bounds.b) / bounds.height()) * scale);
		p.city = c;
		p.index = index;
		return p;
	}
	
	

	private boolean cross(Edge a, Edge b) {
		int x0 = points.get(a.j).x;
		int y0 = points.get(a.j).y;
		int x1 = points.get(a.k).x;
		int y1 = points.get(a.k).y;
		int x2 = points.get(b.j).x;
		int y2 = points.get(b.j).y;
		int x3 = points.get(b.k).x;
		int y3 = points.get(b.k).y;
		
		if (x0 == x2 && y0 == y2) return false;
		if (x1 == x2 && y1 == y2) return false;
		if (x0 == x3 && y0 == y3) return false;
		if (x1 == x3 && y1 == y3) return false;
		
		int x21 = x1 - x0;
		int y21 = y1 - y0;
		int x13 = x0 - x2;
		int y13 = y0 - y2;
		int x43 = x3 - x2;
		int y43 = y3 - y2;
		
		int denom = (y43 * x21) - (x43 * y21);
		if (denom == 0)	// Parallel or coincident
			return false;

		int ds = Integer.signum(denom);
		
		int da = ((x43 * y13) - (y43 * x13)) * ds;
		if (da < 0 || da > denom * ds)	// Intersection point not on a
			return false;
		
		int db = (((x21 * y13) - (y21 * x13))) * ds;
		if (db < 0 || db > denom * ds)	// Intersection point not on b
			return false;
		
		return true;
	}
	
	// DRAW WEBBED
//	int size = (1 << GRID_SCALE) >> 5;
//	BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
//	Graphics2D gr = img.createGraphics();
//	gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//	gr.setColor(Color.black);
//	gr.fillRect(0, 0, size, size);
//	gr.setColor(Color.red);
//	for (Node n : nodes) {
//		int x0 = n.x >> 5;
//		int y0 = n.y >> 5;
//		for (Node m : n.neighbors) {
//			int x1 = m.x >> 5;
//			int y1 = m.y >> 5;
//			gr.drawLine(x0, y0, x1, y1);
//		}
//	}
//	gr.dispose();
//	ImageIO.write(img, "PNG", new File("pacities.web.png"));
	
	
}
