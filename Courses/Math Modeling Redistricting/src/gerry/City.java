package gerry;

public class City implements Comparable<City> 
{
	public final String name;
	public final int population; // people
	public final float x; // lng
	public final float y; // lat
	public int index;
	
	public City(String line) {
		String[] parts = line.split(",");
		name = parts[0];
		population = Integer.parseInt(parts[1]);
		y = Float.parseFloat(parts[2]);
		x = Float.parseFloat(parts[3]);
	}
	
	public int compareTo(City o) {
		return o.population - population;
	}
	
}