package com.wmd.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * A class to handle single units in the database.
 * 
 * @author Philip Diffenderfer, Kevin Rexroth
 *
 */
public class Unit
{
	
	// The SQL query to insert a single unit.
	private static final String SQL_INSERT = "INSERT INTO unit (name) values (?);";
	
	// The SQL query for determining if a given Unit exists.
	private static final String SQL_SELECT = "SELECT name FROM unit WHERE name=?";
	
	/**
	 * Tries to save a unit to the database. If the unit exists already then
	 * false is returned, if the unit has been inserted successfully then true
	 * is returned.
	 * 
	 * @param unit The unit to insert.
	 * @return True if the unit was inserted, false if it already existed.
	 */
	public boolean save(String unit)
	{
		try
		{
			// Get a connection to the current database.
			Connection link = Database.getSingleton().getConnection();
			
			// Create the statement and set the unit to insert. 
			PreparedStatement insert = link.prepareStatement(SQL_INSERT);
			insert.setString(1, unit);
			
			// How many rows affected?
			int inserted = insert.executeUpdate();
			
			// Success if a single row was inserted. 
			return (inserted == 1);	
		}
		catch (SQLException e)
		{
			// If an integrity constraint occurred (a unit with the given name
			// already exists) then return false.
			return false;
		}
	}

	/**
	 * Determines if the given unit is already contained in the database.
	 * 
	 * @param unit The unit to test for existence.
	 * @return True if the unit exists, false if it doesn't exist.
	 */
	public boolean contains(String unit)
	{
		try
		{
			// Get a connection to the current database.
			Connection link = Database.getSingleton().getConnection();
			
			// Create the statement and set the unit to insert. 
			PreparedStatement selection = link.prepareStatement(SQL_SELECT);
			selection.setString(1, unit);
			
			// Get the results from the selection
			ResultSet results = selection.executeQuery();
			
			// Return whether the unit exists.
			return results.next();
		}
		catch (SQLException e)
		{
			return false;
		}
	}
	
}
