import java.io.BufferedInputStream;
import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Geocoder {

	public static void main(String[] args) throws Exception {
		new Geocoder().run();
	}
	
	public Geocoder() {
	}
	
	public void run() throws Exception {
		Scanner input = new Scanner(new File("pacities.csv"));
		PrintStream output = new PrintStream("pacities.out.csv");
		List<String> xmlLines = new ArrayList<String>();
		
		String line = null;
		String format = "http://maps.googleapis.com/maps/api/geocode/xml?address=Pennsylvania+%s&sensor=false";
		while ((line = input.nextLine()) != null) {
			String[] cols = split(line);
			Double lat, lng;
			
			for (;;) {
				URL url = new URL(String.format(format, cols[0].replace(" ", "+")));
				Scanner xml = new Scanner(new BufferedInputStream(url.openStream()));
				
				String xmlLine = null;
				boolean ok = false;
				lat = null;
				lng = null;
				xmlLines.clear();
				
				while (xml.hasNextLine()) {
					xmlLine = xml.nextLine().trim();
					xmlLines.add(xmlLine);
					if (xmlLine.equals("<status>OK</status>")) {
						ok = true;
					}
					if (ok) {
						if (lat == null && xmlLine.startsWith("<lat>")) {
							lat = Double.valueOf(xmlLine.substring("<lat>".length(), xmlLine.indexOf("</lat>") - 1));
						}
						if (lng == null && xmlLine.startsWith("<lng>")) {
							lng = Double.valueOf(xmlLine.substring("<lng>".length(), xmlLine.indexOf("</lng>") - 1));
						}
					}
				}

				
				// Pennsylvania
				// L =  42.52
				// T = -80.57
				// R =  39.69
				// B = -74.7
				if (lat != null && lng != null) {
					boolean latOff = (lat > 43 || lat < 38);
					boolean lngOff = (lng > -73 || lng < -81);
					if (latOff || lngOff) {
						System.out.print("WARNING");
						if (latOff) {
							System.out.print("(lat)");
						}
						if (lngOff) {
							System.out.print("(lng)");
						}
					}
					break;
				}
				Thread.sleep(500);
			}
			
			String format2 = "\"%s\",%s,%.8f,%.8f\n";
			String output2 = String.format(format2, cols[0], cols[1], lat, lng);
			System.out.print(output2);
			output.print(output2);
		}
		
		input.close();
		output.close();
	}
	
	private String[] split(String line) {
		final String[] x = line.split(",");
		for (int i = 0; i < x.length; i++) {
			if (x[i].startsWith("\"")) {
				x[i] = x[i].substring(1);
			}
			if (x[i].endsWith("\"")) {
				x[i] = x[i].substring(0, x[i].length() - 1);
			}
		}
		return x;
	}
	
}
