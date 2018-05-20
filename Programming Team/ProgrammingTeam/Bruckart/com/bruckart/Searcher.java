package com.bruckart;

import java.util.List;


public interface Searcher
{
	public List<Integer> search(char[] text);
	public List<Integer> search(char[] text, final int max);
	public List<Integer> search(String text);
	public List<Integer> search(String text, final int max);
	public int search(char[] textSequence, SearchCallback callback);
	public int search(String textSequence, SearchCallback callback);
	public int occurrences(char[] text);
	public int occurrences(String text);
	public boolean exists(char[] text);
	public boolean exists(String text);
	public char[] getBase();
	public void setBase(char[] newBase);
	public void setBase(String newBase);
	public long getMemoryUsage();
}
