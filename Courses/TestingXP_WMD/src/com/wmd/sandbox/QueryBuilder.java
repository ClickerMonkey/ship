package com.wmd.sandbox;

import java.util.Arrays;

public class QueryBuilder
{
	
	public static int INITIAL_CAPACITY = 512;
	
	private final Column[] columns;
	private char[] value;
	private int count;
	
	public QueryBuilder(Column[] columns) {
		this.columns = columns;
		this.value = new char[INITIAL_CAPACITY];
		this.count = 0;
	}
	
	public void clear() {
		count = 0;
	}
	
	public void append(String s) 
	{
		int add = s.length();
		int next = count + s.length(); 
		if (next > value.length) {
			int capacity = Math.max(next, value.length * 2);
			value = Arrays.copyOf(value, capacity);
		}
		
		for (int i = 0; i < add; i++) {
			value[count++] = s.charAt(i);
		}
	}
	
	public void appendColumnNames(int[] indices, String delim)
	{
		for (int j = 0; j < indices.length; j++) {
			if (j > 0) append(delim);
			append(columns[indices[j]].getName());
		}
	}
	
	public void appendAssignment(Tuple t, boolean predicate, int[] indices, String delim)
	{
		for (int i = 0; i < indices.length; i++) {
			if (i > 0) append(delim);
			append(columns[indices[i]].getName());
			
			if (t.isForceNull(indices[i])) {
				if (predicate) {
					append(" IS NULL");	
				}
				else {
					append("=NULL");
				}
			}
			else {
				Object value = t.get(indices[i]);
				if (value instanceof Literal) {
					append(((Literal)value).getValue());
				}
				else {
					append("=?");
				}	
			}
		}
	}
	
	public void appendList(Tuple t, int[] indices)
	{
		for (int i = 0; i < indices.length; i++) {
			if (i > 0) append(", ");
			if (t.isForceNull(indices[i])) {
				append("NULL");
			}
			else {
				Object value = t.get(indices[i]); 
				if (value instanceof Literal) {
					append(((Literal)value).getValue());
				}
				else {
					append("?");
				}	
			}
		}
	}

	public String takeString() 
	{
		String query = toString();
		clear();
		return query;
	}
	
	public String toString() 
	{
		return String.valueOf(value, 0, count);
	}
	
}
