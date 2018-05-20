package com.wmd.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * A class to handle multiple units in the database.
 * 
 * @author Philip Diffenderfer, Kevin Rexroth
 *
 */
public class UnitList
{
	
	// The SQL query to return all units in the database
	private static final String SQL_SELECT = "SELECT name FROM unit ORDER BY name";
	
	/**
	 * Returns a list of all units in the database.
	 * 
	 * @return The list of units in the database, or null if an SQL error occurred.
	 */
	public List<String> getUnits()
	{
		try
		{
			// Get a connection to the current database.
			Connection link = Database.getSingleton().getConnection();
			
			// Create the statement and set the unit to insert. 
			PreparedStatement select = link.prepareStatement(SQL_SELECT);
			
			// Get all units returned
			ResultSet results = select.executeQuery();
			
			// Build an array of units
			ArrayList<String> units = new ArrayList<String>();
			
			// Iterate through entire table.
			while (results.next()) 
			{
				units.add(results.getString(1));
			}
			
			// Return the built list 
			return units;	
		}
		catch (SQLException e)
		{
			// If any error occurred then return null to signal an SQL error.
			return null;
		}
	}
	
}
