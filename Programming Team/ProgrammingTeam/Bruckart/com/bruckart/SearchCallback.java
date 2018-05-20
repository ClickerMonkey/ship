package com.bruckart;


public interface SearchCallback
{
	public boolean onTextFound(char[] text, int index, int offset, Searcher searchable);
}
