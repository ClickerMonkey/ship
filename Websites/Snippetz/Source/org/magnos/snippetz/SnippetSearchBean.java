package org.magnos.snippetz;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.magnos.snippetz.core.AbstractBean;
import org.magnos.snippetz.core.HibernateFactory;
import org.magnos.snippetz.core.HibernateTransaction;


public class SnippetSearchBean extends AbstractBean
{

	private static final SolrServer searchServer = newServer( "http://localhost:8983/solr/" ); 

	public static SolrServer getSearchServer()
	{
		return searchServer;
	}
	
	private String search;
	private List<Snippet> searchResults = new ArrayList<Snippet>();

	public void doSearch()
	{
		searchResults.clear();
		
		SolrQuery query = new SolrQuery();
		query.setQuery( "description:" + search );
		query.setRows( 100 );
		
		try {
			QueryResponse response = searchServer.query( query );	
			
			HibernateTransaction tran = HibernateFactory.newTransaction();
			try {
				for ( SolrDocument result : response.getResults() ) 
				{
					String idString = result.getFieldValue( "id" ).toString();
					Long id = Long.valueOf( idString );
					Snippet snippet = tran.select( SnippetQuery.ById, id );

					searchResults.add( snippet );
				}
			}
			finally {
				tran.close();
			}

			addInfo( "searchForm", searchResults.size() + " found with '" + search + "'" );
		}
		catch ( Exception e ) {
			e.printStackTrace();
			
			addError( "searchForm", e.getMessage() );
		}
	}
	
	public String getSearch()
	{
		return search;
	}
	
	public void setSearch( String search )
	{
		this.search = search;
	}

	public List<Snippet> getSearchResults()
	{
		return searchResults;
	}
	
	public void setSearchResults( List<Snippet> searchResults )
	{
		this.searchResults = searchResults;
	}
	
	private static SolrServer newServer( String serverUrl )
	{
		try
		{
			return new CommonsHttpSolrServer( serverUrl );
		}
		catch ( RuntimeException e )
		{
			throw e;
		}
		catch ( Exception e )
		{
			throw new RuntimeException( e );
		}
	}
	
}
