package com.bruckart.searchers;

import java.util.Arrays;

import com.bruckart.SearchCallback;


public class BruckartSearch extends AbstractSearcher
{

	public static int ALPHABET = 128;
	
	private long footprint;
	private char[] base;
	private int[][] entries;
	
	public BruckartSearch() {
	}
	
	public BruckartSearch(char[] base) {
		this.setBase(base);
	}
	
	public BruckartSearch(String base) {
		this.setBase(base);
	}
	
	@Override
	public long getMemoryUsage() {
		return footprint;
	}
	
	@Override
	public char[] getBase() {
		return base;
	}

	@Override
	public void setBase(char[] newBase) {
		base = newBase;
		
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
	
	@Override
	public int search(char[] text, SearchCallback callback) {
		int totalFound = 0;
		
		if (text.length == 1) {
			int[] indices = entries[text[0]];
			for (int i = 0; i < indices.length; i++) {
				totalFound++;
				if (!callback.onTextFound(text, i, indices[i], this)) {
					break;
				}
			}
			return totalFound;
		}
		
		int start = getMinIndex(text);
		
		if (start < 0) {
			return 0;
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
					totalFound++;
					if (!callback.onTextFound(text, totalFound, roots[r] - start, this)) {
						break;
					}
				}
			}
		}
		
		return totalFound;
	}
	
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
