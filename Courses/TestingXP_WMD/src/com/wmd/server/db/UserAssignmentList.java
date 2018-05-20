package com.wmd.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.wmd.client.msg.UserAssignmentStatusMsg;
/**
 * Class for management of lists of userAssigments
 * @author Olga Zalamea and Scotty Rhinehart
 *
 */
public class UserAssignmentList
{
	/**
	 * The connection to the database
	 */
	Connection conn = null;
	/**
	 * Constructor for the StudentList class
	 * @throws SQLException
	 */
	public UserAssignmentList() throws SQLException
	{
		this.setConn(Database.get().getConnection());

	}
	
	/**
	 * Method that returns all UserAsigments in an ArrayList of UserAsigmentStatusMsg
	 * @return - All userAssigments
	 * @throws Exception
	 */
	public ArrayList<UserAssignmentStatusMsg> getAllUserAssignmentStatus() throws Exception
	{
		ArrayList<UserAssignmentStatusMsg> result = new ArrayList<UserAssignmentStatusMsg>();
		String query = "SELECT * FROM user_assignment";
		
		try
		{
			//Prepare the statement
			PreparedStatement stmt = this.getConn().prepareStatement(query);
			
			//Get the result
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				result.add(new UserAssignment(rs.getInt(1),rs.getInt(2)).getMessage());
			}
			
			if(result.size()<=0)
			{
				throw new Exception("No UserAssignment Found! (getAllUserAssignmentStatus())");
			}
			
			
		} catch (SQLException e)
		{
			GWT.log("Error in SQL: UserAssignmentList -> getAllUserAssignmentStatus()", e);
			this.getConn().rollback();
			throw e;
		}
		
		return result;
	}
	
	
	/**
	 * Method that gets all userAssigments based on the userId
	 * @param userId the students id
	 * @return All userAssigmentStatus that for the specific userId
	 * @throws Exception
	 */
	public ArrayList<UserAssignmentStatusMsg> getAllUserAssignmentStatusByUser(int userId) throws Exception
	{
		ArrayList<UserAssignmentStatusMsg> AllUserAssignment = getAllUserAssignmentStatus();
		ArrayList<UserAssignmentStatusMsg> UserAssignmentByUser= new ArrayList<UserAssignmentStatusMsg>();
		
		for(int i = 0; i < AllUserAssignment.size(); i++)
			if(AllUserAssignment.get(i).getUserId()== userId)
				UserAssignmentByUser.add(AllUserAssignment.get(i));			
		return UserAssignmentByUser;
	}
	
	/**
	 * Method that gets all userAssignments based on the assigmentId
	 * @param assignmentId the assignmentId
	 * @return All userAssigmentStatus that for the specific assignmentId
	 * @throws Exception
	 */
	public ArrayList<UserAssignmentStatusMsg> getAllUserAssignmentStatusByAssignment(int assignmentId) throws Exception
	{
		ArrayList<UserAssignmentStatusMsg> AllUserAssignment = getAllUserAssignmentStatus();
		ArrayList<UserAssignmentStatusMsg> UserAssignmentByAssignment= new ArrayList<UserAssignmentStatusMsg>();
		
		for(int i = 0; i < AllUserAssignment.size(); i++)
			if(AllUserAssignment.get(i).getAssignmentId()== assignmentId)
				UserAssignmentByAssignment.add(AllUserAssignment.get(i));			
		return UserAssignmentByAssignment;
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