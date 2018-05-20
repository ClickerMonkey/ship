package com.wmd.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.wmd.client.msg.Level;
import com.wmd.client.msg.UserMsg;
/**
 * Class for retrieving all Students
 * @author Tristan Dalious, Steve Unger
 *
 */
public class StudentList
{
	/**
	 * The connection to the database
	 */
	Connection conn = null;
	/**
	 * Constructor for the StudentList class
	 * @throws SQLException
	 */
	public StudentList() throws SQLException
	{
		this.setConn(Database.get().getConnection());

	}
	
	/**
	 * Method that returns all Students in an ArrayList of UserMsges
	 * @return - All Students as UserMsges
	 * @throws Exception
	 */
	public ArrayList<UserMsg> getAllStudents() throws Exception
	{
		ArrayList<UserMsg> result = new ArrayList<UserMsg>();
		
		String query = "SELECT id FROM user WHERE user.role = 'Student';";
		
		try
		{
			//Prepare the statement
			PreparedStatement stmt = this.getConn().prepareStatement(query);
			
			//Get the result
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				result.add(new Student(rs.getInt(1)).getMessage());
			}
			
			if(result.size()<=0)
			{
				throw new Exception("No Students Found! (getAllStudents())");
			}
			
		} catch (SQLException e)
		{
			GWT.log("Error in SQL: StudentList -> getAllStudents()", e);
			this.getConn().rollback();
			throw e;
		}
		
		return result;
	}
	
	/**
	 * Method that gets all Students based on whether or not they
	 * are enabled or disabled
	 * @param isEnabled True if the student is enabled, false otherwise
	 * @return All students that are enabled or disabled depending on isEnabled
	 * @throws Exception
	 */
	public ArrayList<UserMsg> getStudents(boolean isEnabled) throws Exception
	{
		ArrayList<UserMsg> allStudents = getAllStudents();
		ArrayList<UserMsg> enabledStudents = new ArrayList<UserMsg>();
		
		for(int i = 0; i < allStudents.size(); i++)
			if(allStudents.get(i).isEnabled() == isEnabled)
				enabledStudents.add(allStudents.get(i));
			
		return enabledStudents;
	}
	
	/**
	 * Method that gets all Students based on their period
	 * @param period Period the students are in
	 * @return All students that are in the selected period
	 * @throws Exception
	 */
	public ArrayList<UserMsg> getStudents(String period) throws Exception
	{
		ArrayList<UserMsg> allStudents = getAllStudents();
		ArrayList<UserMsg> periodStudents = new ArrayList<UserMsg>();
		
		for(int i = 0; i < allStudents.size(); i++)
		
			if(allStudents.get(i).getPeriod().equals(period))
				periodStudents.add(allStudents.get(i));
			
		return periodStudents;
	}
	
	/**
	 * Method that gets all Students based on their level
	 * @param lvl Level the students are in
	 * @return All students that are in the selected level
	 * @throws Exception
	 */
	public ArrayList<UserMsg> getStudents(Level lvl) throws Exception
	{
		ArrayList<UserMsg> allStudents = getAllStudents();
		ArrayList<UserMsg> lvlStudents = new ArrayList<UserMsg>();
		
		for(int i = 0; i < allStudents.size(); i++)
			if(allStudents.get(i).getLevel() == lvl)
				lvlStudents.add(allStudents.get(i));
			
		return lvlStudents;
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