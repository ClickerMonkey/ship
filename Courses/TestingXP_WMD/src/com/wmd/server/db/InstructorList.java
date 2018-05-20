package com.wmd.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.wmd.client.msg.UserMsg;
/**
 * Class for retrieving all instructors
 * @author Steve Unger, Drew Quackenbos
 *
 */
public class InstructorList 
{
	/**
	 * The connection to the database
	 */
	Connection conn = null;
	/**
	 * Constructor for the InstructorList class
	 * @throws SQLException
	 */
	public InstructorList() throws SQLException
	{
		this.setConn(Database.get().getConnection());
		
		this.getConn().setAutoCommit(false);
	}
	
	/**
	 * Method that returns all Instructors in an array list user msg
	 * @return - all instructors
	 * @throws Exception
	 */
	public ArrayList<UserMsg> getAllInstructors() throws Exception
	{
		ArrayList<UserMsg> result = new ArrayList<UserMsg>();
		
		String query = "SELECT id FROM user WHERE user.role = 'Instructor';";
		
		try
		{
			//Prepare the statement
			PreparedStatement stmt = this.getConn().prepareStatement(query);
			
			//Get the result
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				result.add(new Instructor(rs.getInt(1)).getMessage());
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
	 * Method that gets all instructors based on whether or not they
	 * are enabled or disabled
	 * @param enabled - whether the Instructor is teaching or not
	 * @return - all instructors based on input
	 * @throws Exception
	 */
	public ArrayList<UserMsg> getInstructors(boolean enabled) throws Exception
	{
		ArrayList<UserMsg> allAssignments = getAllInstructors();
		ArrayList<UserMsg> selectedInstructors = new ArrayList<UserMsg>();
		
		for(int i = 0; i < allAssignments.size(); i++)
			if(allAssignments.get(i).isEnabled() == enabled)
				selectedInstructors.add(allAssignments.get(i));
			
		return selectedInstructors;
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
