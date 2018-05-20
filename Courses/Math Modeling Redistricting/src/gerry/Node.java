package gerry;

import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Node implements Comparable<Node> 
{

	public District district;
	public final List<Node> neighbors;
	public final City city;

	public Node(City c) {
		city = c;
		neighbors = new ArrayList<Node>(4);
		district = null;
	}

	public float x() {
		return city.x;
	}

	public float y() {
		return city.y;
	}

	public int weight() {
		return city.population;
	}

	public boolean hasDistrict() {
		return (district != null);
	}

	public int compareTo(Node n) {
		return city.population - n.city.population;
	}

	public boolean equals(Object o) {
		return (o == this);
	}

	public int hashCode() {
		return city.index;
	}
	
	public int getNeighbors(District d) {
		int total = 0;
		for (Node m : neighbors) {
			if (m.district == d) {
				total++;
			}
		}
		return total;
	}
	
	public boolean isOutlier() {
		return (getNeighbors(district) == 1);
	}
	
	public int getFriends() {
		return getNeighbors(district);
	}
	
	public int getForeigns() {
		return neighbors.size() - getFriends();
	}

	private GeneralPath nodePath;
	private Viewport nodeView;

	public GeneralPath getPath(Viewport view) 
	{
		if (nodePath == null || view != nodeView) {

			final float cx = view.convertx(x());
			final float cy = view.converty(y());
			int pointCount = neighbors.size();
			float[][] points = new float[pointCount][2];
			// Get locations of all neighbors
			for (int i = 0; i < pointCount; i++) {
				points[i][0] = view.convertx(neighbors.get(i).x());
				points[i][1] = view.converty(neighbors.get(i).y());
			}
			// Order counter-clockwise
			Arrays.sort(points, new Comparator<float[]>() {
				public int compare(float[] o1, float[] o2) {
					double a1 = Math.atan2(o1[1] - cy, o1[0] - cx);
					double a2 = Math.atan2(o2[1] - cy, o2[0] - cx);
					return (int)Math.signum(a1 - a2);
				}
			}); 
			// Place in every other position
			float[][] points2 = new float[(pointCount << 1) + 1][2];
			for (int i = 0; i < pointCount; i++) {
				points2[i << 1] = points[i];
			}
			points2[pointCount << 1] = points[0];
			// Fill the inbetween as the average of the center and two adjacent points
			for (int i = 1; i < points2.length; i += 2) {
				float[] p0 = points2[i - 1];
				float[] p1 = points2[i + 1];
				points2[i][0] = (p0[0] + p1[0] + cx) / 3.0f;
				points2[i][1] = (p0[1] + p1[1] + cy) / 3.0f;
			}
			// Change neighbor location to half way between
			for (int i = 0; i < pointCount; i++) {
				points2[i << 1][0] = (points2[i << 1][0] + cx) * 0.5f;
				points2[i << 1][1] = (points2[i << 1][1] + cy) * 0.5f;
			}

			// Create polygon containing points
			nodePath = new GeneralPath();
			nodePath.moveTo(points2[0][0], points2[0][1]);
			for (int i = 1; i < points2.length - 1; i++) {
				nodePath.lineTo(points2[i][0], points2[i][1]);
			}
			nodePath.closePath();

			nodeView = view;
		}
		return nodePath;
	}

}