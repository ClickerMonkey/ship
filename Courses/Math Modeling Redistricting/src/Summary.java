import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;


public class Summary {

	public static void main(String[] args) throws Exception {
		new Summary().run();
	}
	
	public static class City implements Comparable<City> {
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
			return o.population - population;
		}
	}
	
	public void run() throws Exception {
		
		Scanner input = new Scanner(
				new BufferedInputStream(
						new FileInputStream("pacities.clean.csv")));
		
		List<City> cities = new ArrayList<City>(2048);
		
		while (input.hasNextLine()) {
			cities.add(new City(input.nextLine()));
		}
		
		input.close();

		Collections.sort(cities);
		
		int n = cities.size();
		
//		int m0 = (n - 1) / 2;
//		int m1 = m0 + ((n ^ 1) & 1);
//		double median = (cities.get(m0).population + cities.get(m1).population) * 0.5;
		
//		int l0 = (n - 1) / 4;
//		int l1 = l0 + ((m0 ^ 1) & 1);
//		double lower = (cities.get(l0).population + cities.get(l1).population) * 0.5;

//		int u0 = (n - 1) * 3 / 4;
//		int u1 = u0 + (((n - m1) ^ 1) & 1);
//		double upper = (cities.get(u0).population + cities.get(u1).population) * 0.5;
		
		int lower = cities.get((n - 1) / 4).population;
		int upper = cities.get((n- 1) * 3 / 4).population;
		
		double iqr = lower - upper;
		
		double lowerMild = lower + (iqr * 1.5);
		double lowerExtr = lower + (iqr * 3.0);
		double upperMild = upper - (iqr * 1.5);
		double upperExtr = upper - (iqr * 3.0);
		
		long totalPopulation = 0;
		int maxPopulation = 0;
		for (City c : cities) {
			totalPopulation += c.population;
			maxPopulation = Math.max(maxPopulation, c.population);
		}
		System.out.println(totalPopulation);
		
		double minLat = +Double.MAX_VALUE;
		double maxLat = -Double.MAX_VALUE;
		double minLng = +Double.MAX_VALUE;
		double maxLng = -Double.MAX_VALUE;
		
		for (City c : cities) {
			minLat = Math.min(minLat, c.lat);
			maxLat = Math.max(maxLat, c.lat);
			minLng = Math.min(minLng, c.lng);
			maxLng = Math.max(maxLng, c.lng);
		}
		System.out.format("lat [%.8f, %.8f]\n", minLat, maxLat);
		System.out.format("lng [%.8f, %.8f]\n", minLng, maxLng);

		if (maxLat != Double.NaN) {
			return;
		}
		
		final double dx = maxLng - minLng;
		final double dy = maxLat - minLat;
		
		System.out.format("dx [%.8f]\n", dx);
		System.out.format("dy [%.8f]\n", dy);
		
		final double scale = 1.0 / dx;
		final int width = 1000;
		final int height = (int)(width * dy * scale);
		
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gr = img.createGraphics();
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setColor(Color.black);
		gr.fillRect(0, 0, width, height);
		
		Ellipse2D.Double ellipse = new Ellipse2D.Double(0, 0, 0, 0);
		for (City c : cities) {
			double x = (c.lng - minLng) / dx * width;
			double y = height - ((c.lat - minLat) / dy * height);
			double d = (c.population - upperMild) / (iqr * 3);
			d = Math.min(1, Math.max(0, d));
			ellipse.x = x - d * 4;
			ellipse.y = y - d * 4;
			ellipse.width = ellipse.height = Math.max(1.0 / 8.0, d) * 8;
		
			boolean outline = true;
			if (c.population > lowerExtr) { // HUGE
				gr.setColor(Color.green);
			}
			else if (c.population > lowerMild) { // BIG
				gr.setColor(Color.cyan);
			}
			else if (c.population < upperExtr) { // TINY
				gr.setColor(Color.white);
			}
			else if (c.population < upperMild) { // SMALL
				gr.setColor(Color.lightGray);
			}
			else {
				outline = false;
				gr.setColor(new Color((int)(d*255), 0, (int)((1-d)*255)));
			}
			
			if (outline) {
				gr.draw(ellipse);
			}
			else {
				gr.fill(ellipse);
			}
		}
		gr.dispose();
		ImageIO.write(img, "png", new File("pacities.png"));
	}
	
	// Pennsylvania
	// L =  42.52
	// T = -80.57
	// R =  39.69
	// B = -74.7
	
	
}
