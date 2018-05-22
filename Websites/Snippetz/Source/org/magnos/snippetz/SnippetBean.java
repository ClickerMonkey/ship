package org.magnos.snippetz;

import org.apache.solr.client.solrj.SolrServer;
import org.magnos.snippetz.core.AbstractBean;
import org.magnos.snippetz.core.HibernateFactory;
import org.magnos.snippetz.core.HibernateTransaction;


public class SnippetBean extends AbstractBean
{

	private final Snippet selected = new Snippet();
	
	public void doSave()
	{
		try {
			HibernateTransaction tran = HibernateFactory.newTransaction();
			tran.begin();
		
			try {
				
				Snippet insert = selected;
				
				Snippet sameTitle = tran.select( SnippetQuery.ByTitle, selected.getTitle() );
				if ( sameTitle != null ) {
					sameTitle.setDescription( selected.getDescription() );
					sameTitle.setSnippet( selected.getSnippet() );
					sameTitle.setTheme( selected.getTheme() );
					sameTitle.setMode( selected.getMode() );
					insert = sameTitle;
				}
				else {
					insert.setId( 0 );
				}
				
				tran.save( insert );
				
				SolrServer searchServer = SnippetSearchBean.getSearchServer();
				searchServer.addBean( insert );
				searchServer.commit();
	
				tran.commit();
				
				if ( sameTitle == null ) {
					addInfo( "snippetForm", "NEW Snippet saved!" );
				}
				else {
					addInfo( "snippetForm", "Snippet saved!" );
				}
			}
			catch ( Exception e ) {
				tran.rollback();
				throw e;
			}
			finally {
				tran.close();
			}
		}
		catch ( Exception e ) {
			addError( "snippetForm", e.getMessage() );
			e.printStackTrace();
		}
	}
	
	public void doDelete()
	{
		if ( selected.getId() == 0 ) {
			addInfo( "snippetForm", "Cannot delete a snippet that has not been saved!" );
			return;
		}
		
		try {
			HibernateTransaction tran = HibernateFactory.newTransaction();
			tran.begin();
			
			try {
				SolrServer searchServer = SnippetSearchBean.getSearchServer();
				searchServer.deleteById( String.valueOf( selected.getId() ) );
				searchServer.commit();
				
				tran.delete( selected );
				tran.commit();
			}
			catch ( Exception e ) {
				tran.rollback();
				throw e;
			}
			finally {
				tran.close();
			}
			
			addInfo( "snippetForm", "Snippet deleted" );
			
			selected.setId( 0 );
			selected.setTitle( "" );
			selected.setDescription( "" );
			selected.setSnippet( "" );
		}
		catch ( Exception e ) {
			addError( "snippetForm", e.getMessage() );
			e.printStackTrace();
		}
	}
	
	public void doReset()
	{
		selected.setId( 0 );
		selected.setTitle( null );
		selected.setDescription( null );
		selected.setSnippet( null );
	}
	
	public Snippet getSelected()
	{
		return selected;
	}
	
	public void setSelected( Snippet selected )
	{
		this.selected.setId( selected.getId() );
		this.selected.setTitle( selected.getTitle() );
		this.selected.setDescription( selected.getDescription() );
		this.selected.setSnippet( selected.getSnippet() );
		this.selected.setTheme( selected.getTheme() );
		this.selected.setMode( selected.getMode() );
	}
	
}
