package gerry;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Loader 
{

	public static void main(String[] args) throws IOException {
		Loader l = new Loader();
		List<City> cities = l.load("pacities.clean.csv");
		long total = 0;
		for (City c : cities) {
			total += c.population;
		}
		System.out.println(total);
	}
	
	public List<City> load(String filename) throws IOException 
	{
		Scanner input = new Scanner(
				new BufferedInputStream(
						new FileInputStream(filename)));

		List<City> cities = new ArrayList<City>(2048);

		while (input.hasNextLine()) {
			cities.add(new City(input.nextLine()));
		}

		input.close();

		Collections.sort(cities);

		for (int i = 0; i < cities.size(); i++) {
			cities.get(i).index = i;
		}

		return cities;
	}

}
