package com.wmd.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wmd.client.msg.PeriodMsg;

/**
 * A handler for loading list of PeriodMsgs
 * 
 * @author Kevin Rexroth, Philip Diffenderfer
 *
 */
public class PeriodList
{

	/**
	 * Returns a list of all of the periods.
	 * 
	 * @return A list of periods.
	 * @throws SQLException An error occurred in the database call
	 */
	public List<PeriodMsg> getPeriods() throws SQLException
	{
		// Get the current database and get the connection
		Database db = Database.get();
		Connection conn = db.getConnection();
		
		// The command to select all unique periods from the user database.
		final String SQL_COMMAND = "SELECT DISTINCT period FROM user WHERE period IS NOT NULL";
		
		// Prepare statement for execution
		PreparedStatement stmt = conn.prepareStatement(SQL_COMMAND);
		
		// Execute query and get results
		ResultSet results = stmt.executeQuery();

		// The list to populate with all unique periods
		List<PeriodMsg> periods = new ArrayList<PeriodMsg>();
		
		// While periods exist...
		while (results.next())
		{
			PeriodMsg period = new PeriodMsg();
			period.setPeriodName(results.getString(1));
			periods.add(period);	
		}
		
		// Return the list of unique period names
		return periods;
	}
	
}
