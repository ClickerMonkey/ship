package com.wmd.sandbox;

public class Tuple
{

	private Table<?> table;
	private final Object[] values;
	private final boolean[] clear;
	
	public Tuple(Table<?> table)
	{
		this.table = table;
		this.values = new Object[table.getColumnCount()];
		this.clear = new boolean[table.getColumnCount()];
		this.reset();
	}
	
	public void reset() 
	{
		for (int i = 0; i < values.length; i++) {
			values[i] = null;
			clear[i] = false;
		}
	}
	
	public boolean hasNullKeys()
	{
		return table.hasNullKeys(this);
	}
	
	protected void setTable(Table<?> table) 
	{
		this.table = table;
	}
	
	public Table<?> getTable() 
	{
		return table;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (int index = 0; index < table.getColumnCount(); index++) {
			Column c = table.getColumn(index);
			sb.append(c.getName());
			sb.append("=");
			if (values[index] == null) {
				sb.append("NULL");
			}
			else {
				sb.append("'");
				if (values[index] instanceof byte[]) {
					sb.append(new String((byte[])values[index]));
				}
				else if (values[index] instanceof char[]) {
					sb.append(new String((char[])values[index]));
				}
				else {
					sb.append(values[index]);
				}
				sb.append("'");
			}
			sb.append(" ");
		}
		return sb.toString();
	}
	
	public void set(int index, Object value) {
		values[index] = value;
		clear[index] = (value == null);
	}
	public void set(String name, Object value) {
		Column c = table.getColumn(name);
		if (c == null) {
			return;
		}
		set(c.getIndex(), value);
	}
	
	public Object get(int index) {
		return values[index];
	}
	public Object get(String name) {
		Column c = table.getColumn(name);
		if (c == null) {
			return null;
		}
		return get(c.getIndex());
	}
	
	
	public boolean isWritable(int index) {
		return (clear[index] || values[index] != null);
	}
	public boolean isWritable(String name) {
		Column c = table.getColumn(name);
		if (c == null) {
			return false;
		}
		return isWritable(c.getIndex());
	}
	
	public boolean isForceNull(int index) {
		return clear[index];
	}
	public boolean isForceNull(String name) {
		Column c = table.getColumn(name);
		if (c == null) {
			return false;
		}
		return isForceNull(c.getIndex());
	}
	
	public boolean isNull(int index) {
		return (values[index] == null);
	}
	public boolean isNull(String name) {
		Column c = table.getColumn(name);
		if (c == null) {
			return false;
		}
		return isNull(c.getIndex());
	}
	
	public Integer getInt(int index) {
		return (Integer)values[index];
	}
	public Integer getInt(String name) {
		Column c = table.getColumn(name);
		if (c == null) {
			return null;
		}
		return (Integer)values[c.getIndex()];
	}
	
	public Long getLong(int index) {
		return (Long)values[index];
	}
	public Long getLong(String name) {
		Column c = table.getColumn(name);
		if (c == null) {
			return null;
		}
		return (Long)values[c.getIndex()];
	}

}
