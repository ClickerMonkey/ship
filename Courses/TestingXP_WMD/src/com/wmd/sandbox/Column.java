package com.wmd.sandbox;

public class Column implements Comparable<Column> 
{

	private final int index;
	private final String name;
	private final boolean key;
	private final boolean auto;

	public Column(int index, String name) {
		this(index, name, false, false);
	}
	
	public Column(int index, String name, boolean key) {
		this(index, name, key, false);
	}
	
	public Column(int index, String name, boolean key, boolean auto) {
		this.index = index;
		this.name = name;
		this.key = key;
		this.auto = auto;
	}
	
	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	public boolean isKey() {
		return key;
	}

	public boolean isAuto() {
		return auto;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof String) {
			return name.equals(o);
		}
		if (o instanceof Column) {
			return (o == this);
		}
		if (o instanceof Integer) {
			return (index == (Integer)o);
		}
		return false;
	}

	public int compareTo(Column o) {
		return index - o.index;
	}
	
}
