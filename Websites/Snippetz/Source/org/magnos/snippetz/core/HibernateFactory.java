package org.magnos.snippetz.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateFactory extends ThreadLocal<Session> implements ServletContextListener
{

	private static SessionFactory sessionFactory;
	
	public static SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}
	
	public static HibernateTransaction newTransaction( boolean startTransaction )
	{
		return new HibernateTransaction( sessionFactory.openSession(), startTransaction );
	}
	
	public static HibernateTransaction newTransaction()
	{
		return new HibernateTransaction( sessionFactory.openSession(), false );
	}
	
	public Session initialValue()
	{
		return sessionFactory.getCurrentSession();
	}
	
	public void contextInitialized(ServletContextEvent event) 
	{
		if ( sessionFactory == null || sessionFactory.isClosed() )
		{
			sessionFactory = newSessionFactory();
		}
	}

	public void contextDestroyed(ServletContextEvent event) 
	{
		if ( sessionFactory != null && !sessionFactory.isClosed() )
		{
			sessionFactory.close();
			sessionFactory = null;
		}
	}

	@SuppressWarnings ("deprecation" )
	private static SessionFactory newSessionFactory()
	{
		try
		{
			return new Configuration().configure().buildSessionFactory();
		}
		catch (Throwable ex) 
		{
			throw new ExceptionInInitializerError( ex );
		}
	}

}
