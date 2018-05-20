package gerry;

import gerry.anim.Display;
import gerry.draw.DrawFilledDistricts;
import gerry.draw.Drawer;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;


public class Districter 
{

	public static void main(String[] args) throws Exception {
		Districter d = new Districter();
		d.run(18);
	}
	
	public Districter() {
	}
	
	public void run(int districtCount) throws Exception 
	{
		Loader loader = new Loader();
		List<City> cities = loader.load("pacities.clean.csv");
		System.out.format("Cities loaded [%d]\n", cities.size());
		
		Nodifier nodifier = new Nodifier(cities);
		
		Box bounds = nodifier.getBounds();
		System.out.println(bounds);
		
		Display display = Display.create(Viewport.fromWidth(bounds, 800), Color.white);
		nodifier.setDisplay(display);
		
		NodeGraph graph = nodifier.nodify();
		System.out.format("Nodified [%d]\n", graph.size());
		
//		Drawer drawer = new DrawConnectedNodes(graph.getNodes());
//		drawer.setColor(DrawConnectedNodes.NEIGHBOR, Color.black);
//		drawer.setViewport(Viewport.fromWidth(bounds, 1024));
//		drawer.save(new File("pacities.web.png"));
//		System.out.format("%s written\n", "pacities.web.png");
//
//		drawer = new DrawFilledNodes(graph.getNodes());
//		drawer.setViewport(Viewport.fromWidth(bounds, 1024));
//		drawer.save(new File("pacities.fill.png"));
//		System.out.format("%s written\n", "pacities.fill.png");
		

		System.out.format("Clustering nodes into %d districts\n", districtCount);
		Clusterer clus = new ClustererFocused();
//		Clusterer clus = new ClustererGravity();
//		Clusterer clus = new ClustererRandom();
		clus.setDistrictCount(districtCount);
		clus.setGraph(graph);
		clus.setDisplay(display);
		List<District> districts = clus.cluster();
		System.out.format("Complete\n");
		
		System.out.format("Balancing nodes\n");
		Balancer bala = new BalancerDefault();
		bala.setDisplay(display);
		bala.setDistricts(districts);
		bala.setGraph(graph);
		bala.setIterations(1000);
		bala.balance();
		
		for (District d : districts) {
			System.out.format("d [tx: %.4f, ty: %.4f, w: %d, s: %d]\n", d.tx, d.ty, d.population, d.nodes.size());
		}
		
		Drawer drawer = new DrawFilledDistricts(graph.getNodes(), districts);
		drawer.setViewport(Viewport.fromWidth(bounds, 1024));
		drawer.save(new File("pacities.cluster.png"));
		System.out.format("%s written\n", "pacities.cluster.png");
		
		double devAll = getStdDev(districts, true);
		double devOpen = getStdDev(districts, false);

		System.out.format("Std. Dev (all): %.4f\n", devAll);
		System.out.format("Std. Dev (-outliers): %.4f\n", devOpen);
		
		try {
			PrintStream out = new PrintStream(new File("districts.txt"));
			
			for (District d : districts) {
				out.format("District #%d\n", d.index + 1);
				out.format("\tPopulation [%d]\n", d.population);
				out.format("\tOutlier [%s]\n", d.fixed ? "Yes" : "No");
				out.format("\tCenter (%.4f, %.4f)\n", d.cx(), d.cy());
				
				out.format("Cities [%d]\n", d.nodes.size());
				out.format("\tCITY [POPULATION] (LONGITUDE, LATITUDE)\n");
				for (Node n : d.nodes) {
					out.format("\t%s [%d] (%.4f,%.4f)\n", n.city.name, n.city.population, n.city.x, n.city.y);
				}
				out.println();
			}

			out.format("Std. Dev (all) [%.4f]\n", devAll);
			out.format("Std. Dev (-outliers) [%.4f]\n", devOpen);
			out.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public double getStdDev(List<District> districts, boolean includeOutliers) 
	{
		double sum = 0.0;
		int count = 0;
		for (District d : districts) { 
			if (d.isOpen() || includeOutliers) {
				count++;
				sum += d.population;
			}
		}
		double mean = sum / count;
		double variance = 0.0;

		for (District d : districts) { 
			if (d.isOpen() || includeOutliers) {
				variance += (d.population - mean) * (d.population - mean);
			}
		}
		
		return Math.sqrt(variance / count);
	}
	
}
