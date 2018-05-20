package com.bruckart;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.bruckart.searchers.BruckartSearch;
import com.bruckart.searchers.LinearSearch;

public class SearchApplication
{

	public static void main(String[] args) throws IOException {
		SearchApplication app = new SearchApplication();
		app.setSearchable(new BruckartSearch());
		//app.setSearchable(new LinearSearch());
		app.start();
	}
	
	private Searcher searcher;
	
	public SearchApplication() {
	}
	
	public void setSearchable(Searcher searcher) {
		this.searcher = searcher;
	}
	
	public void start() throws IOException {

		Scanner input = new Scanner(System.in);
		long start;
		
		System.out.print("File: ");
		String file = input.nextLine();
		
		System.out.print("Loading Input...");
		start = System.nanoTime();
		char[] data = load(file);
		System.out.format("done! (%.6f sec)\n", (System.nanoTime() - start) * 0.000000001);
		
		System.out.print("Building Table...");
		start = System.nanoTime();
		searcher.setBase(new String(data));
		System.out.format("done! (%.6f sec)\n", (System.nanoTime() - start) * 0.000000001);
		
		System.out.format("Table is taking up %d bytes.\n", searcher.getMemoryUsage());
		
		while (true) {
			System.out.print("Search: ");
			String text = input.nextLine();

			System.out.print("Searching...");
			start = System.nanoTime();
			List<Integer> found = searcher.search(text);
			System.out.format("done! (%.6f sec)\n", (System.nanoTime() - start) * 0.000000001);
			
			System.out.format("\t'%s' occurred %d times at locations (only showing first 100): %s\n", text, found.size(), toString(found));
		}
	}
	
	private char[] load(String file) throws IOException {
		BufferedInputStream input = new BufferedInputStream(new FileInputStream(new File(file)));
		char[] result = new char[input.available()];
		for (int i = 0; i < result.length; i++) {
			result[i] = (char)input.read();
		}
		input.close();
		return result;
	}
	
	private String toString(List<?> list) {
		StringBuilder sb = new StringBuilder();
		sb.append('{');
		int max = Math.min(100, list.size());
		for(int i = 0; i < max; i++) {
			if (i > 0) {
				sb.append(',');
			}
			sb.append(list.get(i).toString());
		}
		sb.append('}');
		
		return sb.toString();
	}
	
}
