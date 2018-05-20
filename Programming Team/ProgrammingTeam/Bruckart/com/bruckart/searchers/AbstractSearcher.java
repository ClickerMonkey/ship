package com.bruckart.searchers;

import java.util.ArrayList;
import java.util.List;

import com.bruckart.SearchCallback;
import com.bruckart.Searcher;


public abstract class AbstractSearcher implements Searcher
{

	@Override
	public boolean exists(char[] text) {
		return search(text, 1).size() == 1;
	}

	@Override
	public boolean exists(String text) {
		return exists(text.toCharArray());
	}

	@Override
	public int occurrences(char[] text) {
		return search(text).size();
	}

	@Override
	public int occurrences(String text) {
		return occurrences(text.toCharArray());
	}

	@Override
	public List<Integer> search(char[] text) {
		final List<Integer> indices = new ArrayList<Integer>();
		search(text, new SearchCallback(){
			public boolean onTextFound(char[] text, int index, int offset, Searcher searchable) {
				return indices.add(offset);
			}
		});
		return indices;
	}

	@Override
	public List<Integer> search(String text) {
		return search(text.toCharArray());
	}

	@Override
	public List<Integer> search(char[] text, final int max) {
		final List<Integer> indices = new ArrayList<Integer>();
		search(text, new SearchCallback(){
			public boolean onTextFound(char[] text, int index, int offset, Searcher searchable) {
				return indices.add(offset) && (index < max);
			}
		});
		return indices;
	}

	@Override
	public List<Integer> search(String text, final int max) {
		return search(text.toCharArray(), max);
	}

	@Override
	public int search(String text, SearchCallback callback) {
		return search(text.toCharArray(), callback);
	}
	
	@Override
	public void setBase(String base) {
		this.setBase(base.toCharArray());
	}

}
