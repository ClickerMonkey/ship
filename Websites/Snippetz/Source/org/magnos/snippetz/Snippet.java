package org.magnos.snippetz;

import org.apache.solr.client.solrj.beans.Field;


public class Snippet
{
	
	@Field("id")
	private long id;
	private String title;
	@Field("description")
	private String description;
	private String snippet;
	private String theme;
	private String mode;
	
	public Snippet()
	{
	}
	
	public Snippet( String title, String description, String snippet )
	{
		this.title = title;
		this.description = description;
		this.snippet = snippet;
	}
	
	public long getId()
	{
		return id;
	}
	
	public void setId( long id )
	{
		this.id = id;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle( String title )
	{
		this.title = title;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription( String description )
	{
		this.description = description;
	}
	
	public String getSnippet()
	{
		return snippet;
	}
	
	public void setSnippet( String snippet )
	{
		this.snippet = snippet;
	}
	
	public String getTheme()
	{
		return theme;
	}
	
	public void setTheme( String theme )
	{
		this.theme = theme;
	}
	
	public String getMode()
	{
		return mode;
	}
	
	public void setMode( String mode )
	{
		this.mode = mode;
	}
	
}
