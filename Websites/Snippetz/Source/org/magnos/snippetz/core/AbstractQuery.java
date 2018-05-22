package org.magnos.snippetz.core;


public interface AbstractQuery<T>
{
	
	public String getName();
	
	public Class<?>[] getTypes();
	
}
