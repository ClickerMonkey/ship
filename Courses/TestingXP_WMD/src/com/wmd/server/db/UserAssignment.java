package com.wmd.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.wmd.client.msg.ProblemMsg;
import com.wmd.client.msg.UserAssignmentStatusMsg;
/**
 * Class that interacts with the database for a specific userAssigment
 * 
 * @author Olga Zalamea and Scotty Rhinehart
 */

public class UserAssignment {

	/**
	 * The id's of an userAssigment
	 */
	int userId;
	int assignmentId;
	
	/**
	 * The link to the connection
	 */
	Connection conn = null;

	
	/**
	 * Constructor for the UserAssigment
	 */
	public UserAssignment (int userId, int assignmetId) throws Exception
	{
		this.setConn(Database.get().getConnection());		
		this.getConn().setAutoCommit(false);
		this.userId=userId;
		this.assignmentId=assignmetId;
		String query = "SELECT * FROM user_assignment WHERE user_id = ? and assignment_id = ? ";		
		try
		{
			//Prepare the statement
			PreparedStatement stmt = this.getConn().prepareStatement(query);
			
			//Bind the Ids
			stmt.setInt(1, this.userId);
			stmt.setInt(2, this.assignmentId);
			
			//Store the results
			ResultSet rs = stmt.executeQuery();
			
			//See if we got anything
			if(!rs.next())
			{
				//Throw exception!
				throw new Exception("No realtionship between this student and the assigment");
			}
		}	
		catch(SQLException e)
		{
			GWT.log("Error in SQL: UserAssigment->Constructor(int userId, int assignmetId)", e);
			this.getConn().rollback();
			throw e;
		}
	}
		
	/**
	 * Returns the userAssigmentStatus message for a specific student and assignment
	 * @return userAssigmentStatusMsg holding the userAssigment information
	 * @throws SQLException
	 */
	public UserAssignmentStatusMsg getMessage() throws Exception
	{
		UserAssignmentStatusMsg msg = new UserAssignmentStatusMsg();

		//getting the information for the assignment
		Assignment assignment= new Assignment(assignmentId);
				
		msg.setUserId(userId);
		msg.setAssignmentId(assignment.getId());			
		msg.setAssignmentName(assignment.getName());
		msg.setAssignmentEnabled(assignment.isEnabled());		
		msg.setProblemCount(getProblemCount());
		msg.setProblemsTried(getProblemsTried());
		msg.setProblemsCorrect(getProblemsCorrect());
		msg.setTotalTries(getTotalTries());

		//getting the information for the student
		Student student = new Student(userId);
		msg.setUser(student.getMessage() );

		return msg;
	}

	/**
	 * Gets the number of problems for the current userAssigment
	 * @return the count of problems
	 * @throws Exception Exception An exception will be thrown if either
	 *  an userAssigment cannot be found or there is an SQL error.
	 */
	public int getProblemCount() throws Exception
	{			
		ProblemList list = new ProblemList();
		ArrayList<ProblemMsg> probList;
		Student student = new Student(userId);
		probList=list.getProblemsByAssignmentLevel(assignmentId, student.getLevel());
		//TODO
		//This is a quick fix, due to the probList adding the wrong amount of assignments,
		//off by one every time.  This needs to be investigated further.
		return probList.size()-1;
	}

	/**
	 * Gets the total of all tries for all problems
	 * @return the count of tries
	 * @throws Exception Exception An exception will be thrown if either
	 *  an userAssigment cannot be found or there is an SQL error.
	 */
	public int getTotalTries() throws Exception
	{			
		String SQL_COUNT = "SELECT SUM(tries) FROM problem, problem_status WHERE problem_id=id AND user_id=? AND assignment_id=?";
		try
		{
			PreparedStatement statement = this.getConn().prepareStatement(SQL_COUNT);
			statement.setInt(1, userId);
			statement.setInt(2, assignmentId);
			ResultSet result = statement.executeQuery();

			// If this returns true, then the user already exists.
			if (!result.next())
			{
				throw new Exception("No userAssignmet found! (getProblemsTried)");
			}
			// Return the count.
			return result.getInt(1);
		} catch (Exception e)
		{
			GWT.log("Error in getProblemsTried()", e);
			this.getConn().rollback();
			throw e;
		}
	}

	/**
	 * Gets the total correct problems
	 * @return the count of correct problems
	 * @throws Exception Exception An exception will be thrown if either
	 *  an userAssigment cannot be found or there is an SQL error.
	 */
	public int getProblemsCorrect() throws Exception
	{			
		String SQL_COUNT = "SELECT SUM(correct) FROM problem, problem_status WHERE problem_id=id AND user_id=? AND assignment_id=?";
		try
		{
			PreparedStatement statement = this.getConn().prepareStatement(SQL_COUNT);
			statement.setInt(1, userId);
			statement.setInt(2, assignmentId);
			ResultSet result = statement.executeQuery();

			// If this returns true, then the user already exists.
			if (!result.next())
			{
				throw new Exception("No userAssignmet found! (getProblemsCorrect)");
			}
			// Return the count.
			return result.getInt(1);
		} catch (Exception e)
		{
			GWT.log("Error in getProblemsCorrect()", e);
			this.getConn().rollback();
			throw e;
		}
	}

	/**
	 * Gets the total of problems the user has attempted
	 * @return the count of total tries
	 * @throws Exception Exception An exception will be thrown if either
	 *  an userAssigment cannot be found or there is an SQL error.
	 */
	public int getProblemsTried() throws Exception
	{			
		String SQL_COUNT = "SELECT COUNT(*) FROM problem, problem_status WHERE problem_id=id AND user_id=? AND assignment_id=? AND tries > 0";
		try
		{
			PreparedStatement statement = this.getConn().prepareStatement(SQL_COUNT);
			statement.setInt(1, userId);
			statement.setInt(2, assignmentId);
			ResultSet result = statement.executeQuery();

			// If this returns true, then the user already exists.
			if (!result.next())
			{
				throw new Exception("No userAssigmet found! (getTotalTries)");
			}
			// Return the count.
			return result.getInt(1);
		} catch (Exception e)
		{
			GWT.log("Error in getTotalTries()", e);
			this.getConn().rollback();
			throw e;
		}
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
