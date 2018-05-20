package com.wmd.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.wmd.client.msg.AssignmentMsg;

/**
 * AssignmentList Object for getting assignments
 * @author Tristan D., Paul C.
 *
 */
public class AssignmentList 
{
	/**
	 * The connection to the database
	 */
	Connection conn = null;
	/**
	 * Constructor for AssignmentList class 
	 * @throws SQLException
	 */
	public AssignmentList() throws SQLException
	{
		this.setConn(Database.get().getConnection());
		
		this.getConn().setAutoCommit(false);
	}
	
	/**
	 * Gets all assignments as an array list of assignment objects
	 * @return ArrayList of Assignment objects (all assignments)
	 * @throws Exception 
	 */
	public ArrayList<AssignmentMsg> getAssignments() throws Exception
	{
		ArrayList<AssignmentMsg> result = new ArrayList<AssignmentMsg>();
		AssignmentMsg toAdd  = new AssignmentMsg();
		
		String query = "SELECT * FROM assignment";
		
		try
		{
			//Prepare the statement
			PreparedStatement stmt = this.getConn().prepareStatement(query);
			
			//Get the result
			ResultSet rs = stmt.executeQuery();
			
			
			
			while(rs.next())
			{
				toAdd.setAssignmentId(rs.getInt(1));
				toAdd.setName(rs.getString(2));
				toAdd.setEnabled(rs.getInt(3) == 1 ? true : false);
				result.add(toAdd);
				toAdd = new AssignmentMsg();
			}
			
			if(result.size()<=0)
			{
				throw new Exception("No Assignments Found! (getAssignments())");
			}
			
		} catch (SQLException e)
		{
			GWT.log("Error in SQL: AssignmentList -> getAssignments()", e);
			this.getConn().rollback();
			throw e;
		}
		
		return result;
	}
	
	/**
	 * Gets all assignments that are enabled or disabled
	 * @param enabled Value of enabled to query by
	 * @return All enabled assignments or all disabled assignments
	 * @throws Exception
	 */
	public ArrayList<AssignmentMsg> getAssignments(boolean enabled) throws Exception
	{
		ArrayList<AssignmentMsg> allAssignments = getAssignments();
		ArrayList<AssignmentMsg> selectedAssignments = new ArrayList<AssignmentMsg>();
		
		for(int i = 0; i < allAssignments.size(); i++)
			if(allAssignments.get(i).isEnabled() == enabled)
				selectedAssignments.add(allAssignments.get(i));
			
		return selectedAssignments;
	}
	
	/**
	 * Returns the Connection link being used by this instance
	 * @return The database connection being used
	 */
	public Connection getConn()
	{
		return this.conn;
	}
	
	/**
	 * Sets the database connection to be used for this instance
	 * @param conn The connection to use
	 */
	public void setConn(Connection conn)
	{
		this.conn = conn;
	}
	
}
