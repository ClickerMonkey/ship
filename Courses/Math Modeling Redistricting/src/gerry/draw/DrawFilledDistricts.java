package gerry.draw;

import gerry.District;
import gerry.Node;
import gerry.Pixel;
import gerry.Viewport;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DrawFilledDistricts extends AbstractDrawer
{
	public static final int BACKGROUND = 0;
	
	private List<Node> nodes;
	private List<District> districts;
	
	public DrawFilledDistricts(List<Node> nodes, List<District> districts) {
		super(1);
		this.nodes = nodes;
		this.districts = districts;
	}

	@Override
	public BufferedImage create(Viewport view) {
		BufferedImage img = new BufferedImage(view.width, view.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gr = img.createGraphics();
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (hasColor(BACKGROUND)) {
			gr.setColor(getColor(BACKGROUND));
			gr.fillRect(0, 0, view.width, view.height);
		}
		
		Object[] nodes = this.nodes.toArray();
		Arrays.sort(nodes, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				Node n1 = (Node)o1;
				Node n2 = (Node)o2;
				return n1.district.index - n2.district.index;
			}
		});
		
		int districtMax = 0;
		for (int k = 0; k < nodes.length - 1; k++) {
			Node a = (Node)nodes[k+0];
			Node b = (Node)nodes[k+1];
			if (a.district != b.district) {
				districtMax++;
			}
		}
		
		for (int k = 0; k < nodes.length; k++) 
		{
			Node n = (Node)nodes[k];
			final float cx = view.convertx(n.x());
			final float cy = view.converty(n.y());
			int pointCount = n.neighbors.size();
			float[][] points = new float[pointCount][2];
			// Get locations of all neighbors
			for (int i = 0; i < pointCount; i++) {
				points[i][0] = view.convertx(n.neighbors.get(i).x());
				points[i][1] = view.converty(n.neighbors.get(i).y());
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
			GeneralPath path = new GeneralPath();
			path.moveTo(points2[0][0], points2[0][1]);
			for (int i = 1; i < points2.length - 1; i++) {
				path.lineTo(points2[i][0], points2[i][1]);
			}
			path.closePath();
			
			// Pick the color
			Color filler = getColor(n.district.index, districtMax);
			
			// Fill node polygon
			gr.setColor(filler);
			gr.fill(path);
			gr.setColor(filler.darker());
			gr.draw(path);
		}
		
		gr.setColor(Color.black);
		gr.setFont(new Font("Arial", Font.BOLD, 24));
		for (District d : districts) {
			Pixel p = view.convert(d.cx(), d.cy());
			gr.drawString(String.valueOf(d.index), p.x, p.y);
		}
		
		gr.dispose();
		return img;
	}
	
	private Color getColor(int d, float max) {
		float x = Math.min(d / max, 1f);
		float b = 1.0f;
		if ((d & 1) == 1) {
			x = 1 - x;
			b = 0.5f;
		}
		return new Color(Color.HSBtoRGB(x, 1f, b));
	}
	
}
