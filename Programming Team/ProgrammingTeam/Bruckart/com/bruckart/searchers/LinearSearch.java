package com.bruckart.searchers;

import com.bruckart.SearchCallback;


public class LinearSearch extends AbstractSearcher
{
	private char[] base;
	
	@Override
	public char[] getBase() {
		return base;
	}

	@Override
	public void setBase(char[] newBase) {
		base = newBase;
	}

	@Override
	public long getMemoryUsage() {
		return 0;
	}

	@Override
	public int search(char[] text, SearchCallback callback) {
		int totalFound = 0;
		int max = base.length - text.length;
		int matches;
		for (int i = 0; i < max; i++) {
			for (matches = 0; matches < text.length; matches++) {
				if (base[i + matches] != text[matches]) {
					break;
				}
			}
			if (matches == text.length) {
				totalFound++;
				if (!callback.onTextFound(text, totalFound, i, this)) {
					break;
				}
			}
		}
		return totalFound;
	}

}
