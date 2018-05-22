package org.magnos.snippetz;

import org.magnos.snippetz.core.AbstractQuery;

public enum SnippetQuery implements AbstractQuery<Snippet>
{
	All 			("snippet.all"),
	ById			("snippet.byId", Integer.class),
	ByTitle		("snippet.byTitle", String.class);
	
	private final String name;

	private final Class<?>[] types;
	
	private SnippetQuery( String name, Class<?> ... types )
	{
		this.name = name;
		this.types = types;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Class<?>[] getTypes()
	{
		return types;
	}
	
}
