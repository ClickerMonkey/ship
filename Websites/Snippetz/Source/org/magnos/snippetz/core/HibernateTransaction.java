package org.magnos.snippetz.core;

import java.io.Serializable;
import java.util.List;

import org.hibernate.MappingException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.magnos.snippetz.core.AbstractQuery;


public class HibernateTransaction
{
	private final Session session;
	private Transaction transaction;

	protected HibernateTransaction( Session session, boolean startTransaction )
	{
		this.session = session;
		
		if ( startTransaction )
		{
			begin();
		}
	}
	
	public void begin()
	{
		transaction = session.getTransaction();
		transaction.begin();
	}

	public void commit()
	{
		transaction.commit();
	}

	public void rollback()
	{
		transaction.rollback();
	}
	
	public void close()
	{
		session.close();
	}

	public void save( Object entity )
	{
		session.saveOrUpdate( entity );
	}

	public <T> T load( Class<T> type, Serializable primary )
	{
		return (T) session.load( type, primary );
	}

	public void delete( Object entity )
	{
		session.delete( entity );
	}

	public <T> T select( AbstractQuery<T> query, Object ... input )
	{
		return (T) getQuery( query, input ).uniqueResult();
	}

	public <T> List<T> search( AbstractQuery<T> query, Object ... input )
	{
		return (List<T>) getQuery( query, input ).list();
	}

	public <T> List<T> search( AbstractQuery<T> query, int offset, int count, Object ... input )
	{
		Query q = getQuery( query, input );
		q.setFirstResult( offset );
		q.setFetchSize( count );
		
		return (List<T>) q.list();
	}

	public long count( AbstractQuery<?> query, Object ... input )
	{
		String queryName = query.getName();
		String rowCountQueryName = queryName + ".count";
		
		Query q = getNamedQuery( rowCountQueryName );
		
		if ( q == null ) 
		{
			q = getNamedQuery( queryName );
			
			setParameters( q, input );
			
			return q.list().size();
		}
		else
		{
			setParameters( q, input );
			
			return (Long)q.uniqueResult();
		}
	}

	private <T> Query getQuery( AbstractQuery<T> query, Object[] input )
	{
		Query q = getNamedQuery( query.getName() );
		setParameters( q, input );
		
		return q;
	}

	private void setParameters( Query query, Object[] input )
	{
		for (int i = 0; i < input.length; i++)
		{
			query.setParameter( i, input[i] );
		}
	}

	private Query getNamedQuery( String queryName) 
	{
		try 
		{
			return session.getNamedQuery(queryName);
		} 
		catch (MappingException mappingException) 
		{
			return null;
		}
	}

	public Session getSession()
	{
		return session;
	}

}
