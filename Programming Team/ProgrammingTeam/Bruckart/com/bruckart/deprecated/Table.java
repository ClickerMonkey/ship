package com.bruckart.deprecated;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Table
{
	public static final int ALPHABET = 128;
	
	private final int[][] entries;
	private int footprint;
	
	public Table(String base) {
		this(base.toCharArray());
	}
	
	public Table(char[] base) {
		
		int initial = Math.max(16, base.length / ALPHABET);
		int[][] precompiled = new int[ALPHABET][initial];
		int[] sizes = new int[ALPHABET];
		
		for (int i = 0; i < base.length; i++) {
			int hash = base[i];
			if (sizes[hash] == precompiled[hash].length) {
				precompiled[hash] = Arrays.copyOf(precompiled[hash], sizes[hash] + (sizes[hash] >> 1));
			}
			precompiled[hash][sizes[hash]++] = i;
		}
		
		entries = new int[ALPHABET][];
		for (int i = 0; i < ALPHABET; i++) {
			entries[i] = Arrays.copyOf(precompiled[i], sizes[i]);
		}
		
		System.gc();
		
		footprint = 0;
		for (int i = 0; i < ALPHABET; i++) {
			footprint += entries[i].length;
		}
		footprint <<= 2;
	}
	
	public int getFootprint() {
		return footprint;
	}
	
	public boolean exists(char[] text) {
		return search(text, 1).size() == 1;
	}
	
	public boolean exists(String text) {
		return search(text.toCharArray(), 1).size() == 1;
	}
	
	public List<Integer> search(char[] text) {
		return search(text, Integer.MAX_VALUE);
	}

	public List<Integer> search(String text) {
		return search(text.toCharArray(), Integer.MAX_VALUE);
	}
	
	public List<Integer> search(String text, int maxFinds) {
		return search(text.toCharArray(), maxFinds);
	}
	
	public List<Integer> search(char[] text, int maxFinds) 
	{
		List<Integer> found = new ArrayList<Integer>();
		
		if (text.length == 1) {
			int[] indices = entries[text[0]];
			for (int index : indices) {
				found.add(index);
			}
			return found;
		}
		
		int start = getMinIndex(text);
		
		if (start < 0) {
			return found;
		}
		
		int[] roots = entries[text[start]];
		int left, right, prev, next;
		int min = -1;
		int max = text.length;
		
		
		for (int r = 0; r < roots.length; r++) {
			for (left = start - 1, prev = roots[r] - 1; left > min; left--, prev--) {
				if (!exists(text[left], prev)) {
					break;
				}
			}
			if (left == min) {
				for (right = start + 1, next = roots[r] + 1; right < max; right++, next++) {
					if (!exists(text[right], next)) {
						break;
					}
				}
				if (right == max) {
					found.add(roots[r]);
					if (found.size() >= maxFinds) {
						break;
					}
				}
			}
		}
		
		return found;
	}
	
	/**
	 * Finds the letter in the search string (text) with the least number of
	 * occurrences in the base string.
	 * 
	 * @param text
	 * @return
	 */
	private int getMinIndex(char[] text) {
		int minIndex = -1;
		for (int i = 0; i < text.length; i++) {
			if (minIndex == -1 || entries[text[i]].length < entries[text[minIndex]].length) {
				minIndex = i;
			}
		}
		return minIndex;
	}

	private boolean exists(char x, int index) {
		return (Arrays.binarySearch(entries[x], index) >= 0);
	}
	
}
