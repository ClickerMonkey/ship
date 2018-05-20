package com.wmd.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A singleton class which makes connections to either the application database
 * or testing database. This class is configured for connecting to MySql
 * databases.
 * 
 * @author Merlin
 * 
 */
public class Database
{

	private static Database singleton;
	private static boolean testing;

	/**
	 * @return the only one
	 * @throws SQLException
	 */
	public static Database getSingleton()
	{
		if (singleton == null)
		{
			singleton = new Database();
		}
		return singleton;
	}

	private Connection connection;

	// private Savepoint svpt;

	private Database()
	{
		openConnectionTo("jdbc:mysql://txp.cs.ship.edu:3306/wmd");
	}

	/**
	 * 
	 */
	public void commit()
	{
		if (!testing)
		{
			try
			{
				connection.commit();
				// svpt = connection.setSavepoint();
			} catch (SQLException e)
			{
				// TODO Errors?
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return the connection to the db
	 */
	public Connection getConnection()
	{
		return connection;
	}

	private void openConnectionTo(String url)
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}

		try
		{

			connection = DriverManager.getConnection(url, "wmd", "mathRox");
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Roll back the current transaction
	 */
	public void rollBack()
	{
		try
		{

			connection.rollback();
			// connection.commit();

		} catch (SQLException e)
		{
			// TODO Errors?
			e.printStackTrace();
		}
	}

	/**
	 * When we are testing, use a different db
	 */
	public void setTesting()
	{
		try
		{
			openConnectionTo("jdbc:mysql://txp.cs.ship.edu:3306/wmdTest");
			connection.setAutoCommit(false);
			Statement stmt = connection.createStatement();
			stmt.execute("START TRANSACTION");
			// if (svpt != null)
			// {
			// connection.releaseSavepoint(svpt);
			// }
			// svpt = connection.setSavepoint("SAVEPOINT_1");
			testing = true;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * For testing purposes only
	 */
	public static void reset()
	{
		try
		{
			if ((singleton != null) && (singleton.connection != null)
					&& (!singleton.connection.isClosed()))
				singleton.connection.close();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		singleton = null;
		testing = false;
	}

	public static Database get()
	{

		return getSingleton();
	}

	public Connection getLink()
	{
		return getConnection();
	}

	public static void setTesting(boolean b)
	{
		getSingleton();
		if (b)
		{
			singleton.setTesting();
		} else
		{
			reset();
		}
		testing = b;

	}

	public static boolean isTesting()
	{
		return testing;
	}
}
