package dbms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import oracle.jdbc.OracleDriver;

/**
 * A singleton class to connect to my database containing my tables.
 * 
 * @author Philip Diffenderfer
 *
 */
public final class Database 
{
	
	/*
	 * At the loading of this class ensure that the Oracle driver is
	 * registered with the manager so any connections made will be made to
	 * the given Oracle SQL database. 
	 */
	static 
	{
		try {
			DriverManager.registerDriver(new OracleDriver());
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// The single instance of the database.
	private static final Database instance = 
		new Database("pd6407", "swordfish");
	
	/**
	 * Returns the single instance of this Database.
	 */
	public static Database get() 
	{
		return instance;
	}
	
	// The url to the database.
	private final String url;
	
	// The username to login to
	private final String username;
	
	// The password of the user
	private final String password;
	
	// The current connection to the database.
	private Connection connection;
	
	/**
	 * Instantiats a Database connection to an Oracle Database given a username
	 * and their password.
	 * 
	 * @param username => The id of the user accessing the database.
	 * @param password => The password of the user accessing the database.
	 */
	private Database(String username, String password)
	{
		this.url = "jdbc:oracle:thin:@oracle11g.cs.ship.edu:1521:orcl";
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Attempts to connect to the database (if it isn't connected already) and
	 * returns whether this class is now connected to the database.
	 * 
	 * @return True if we're now connected, otherwise false.
	 */
	public boolean connect()
	{
		try {
			if (connection == null || connection.isClosed()) {
				connection = DriverManager.getConnection(url, username, password);
			}
			return !connection.isClosed();
		}
		catch (SQLException e) {
			return false;
		}
	}
	
	/**
	 * Attempts to disconnect from the database (if it isn't disconnected
	 * already) and returns whether this class is now disconnected from the
	 * database.
	 * 
	 * @return True if we're now disconnected, otherwise false.
	 */
	public boolean disconnect()
	{
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
			return (connection == null ? true : connection.isClosed());
		}
		catch (SQLException e) {
			return false;
		}
	}
	
	/**
	 * Gets the connection to the database. If the connection does not exist
	 * then an attempt is made. If succesfully connected to the database after
	 * the attempt this will return the connection made. If connecting to the
	 * database resulted in error then null is returned.
	 */
	public Connection getConnection() 
	{
		if (connect()) {
			return connection;
		}
		return null;
	}
	
	/**
	 * Returns the URL of the database.
	 */
	public String getURL() 
	{
		return url;
	}
	
	/**
	 * Returns the username of the database.
	 */
	public String getUsername() 
	{
		return username;
	}
	
}
