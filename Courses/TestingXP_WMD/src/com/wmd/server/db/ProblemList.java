package com.wmd.server.db;

import java.sql.*;
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.wmd.client.msg.Level;
import com.wmd.client.msg.ProblemMsg;

/**
 * Gets Lists of Problems
 * @author Tristan D., Steve U.
 *
 */
public class ProblemList 
{
	/**
	 * The connection to the database
	 */
	Connection conn = null;
	
	public ProblemList() throws SQLException
	{
		this.setConn(Database.get().getConnection());
		this.getConn().setAutoCommit(false);
	}
	
	/**
	 * Gets all problems in the database as problem messages
	 * @return ArrayList of all problems as Problem Messages 
	 * @throws Exception
	 */
	public ArrayList<ProblemMsg> getAllProblems() throws Exception
	{
		ArrayList<ProblemMsg> result = new ArrayList<ProblemMsg>();
		
		String query = "SELECT id FROM problem";
		
		try
		{
			//Prepare the statement
			PreparedStatement stmt = this.getConn().prepareStatement(query);
			
			//Get the result
			ResultSet rs = stmt.executeQuery();
	
			while(rs.next())
			{
				result.add(new Problem(rs.getInt(1)).getMessage());
			}
			
			if(result.size()<=0)
			{
				throw new Exception("No Problems Found! (getAllProblems())");
			}
			
		} catch (SQLException e)
		{
			GWT.log("Error in SQL: ProblemList -> getAllProblems()", e);
			this.getConn().rollback();
			throw e;
		}
		return result;
	}

	/**
	 * Gets all problems of a certain level as problem messages
	 * @param lvl Level of the list of problems
	 * @return ArrayList of all problems of Level as Problem Messages 
	 * @throws Exception
	 */
	public ArrayList<ProblemMsg> getProblemsByLevel(Level lvl) throws Exception 
	{
		ArrayList<ProblemMsg> result = new ArrayList<ProblemMsg>();
		
		String query = "SELECT id FROM problem WHERE problem.level = ?";
		
		try
		{
			//Prepare the statement
			PreparedStatement stmt = this.getConn().prepareStatement(query);
			if(lvl == Level.Easy)
				stmt.setString(1, "Easy");
			else if(lvl == Level.Medium)
				stmt.setString(1, "Medium");
			else if(lvl == Level.Hard)
				stmt.setString(1, "Hard");
			
			//Get the result
			ResultSet rs = stmt.executeQuery();
	
			while(rs.next())
			{
				result.add(new Problem(rs.getInt(1)).getMessage());
			}
			if(result.size()<=0)
			{
				throw new Exception("No Problems Found! (getProblemByLevel())");
			}
			
		} catch (SQLException e)
		{
			GWT.log("Error in SQL: ProblemList -> getProblemByLevel()", e);
			this.getConn().rollback();
			throw e;
		}
		
		return result;
	}
	
	/**
	 * Gets all problems of a certain assignment as problem messages
	 * @param assignId Assignment Id of the Problems to return
	 * @return ArrayList of all problems in an Assignment as Problem Messages 
	 * @throws Exception
	 */
	public ArrayList<ProblemMsg> getProblemsByAssignment(int assignId) throws Exception 
	{
		ArrayList<ProblemMsg> result = new ArrayList<ProblemMsg>();
		
		String query = "SELECT id FROM problem WHERE problem.assignment_id = ?";
		
		try
		{
			//Prepare the statement
			PreparedStatement stmt = this.getConn().prepareStatement(query);
			stmt.setInt(1, assignId);
			
			//Get the result
			ResultSet rs = stmt.executeQuery();
	
			while(rs.next())
			{
				result.add(new Problem(rs.getInt(1)).getMessage());
			}
			if(result.size()<=0)
			{
				throw new Exception("No Problems Found! (getProblemByAssignment())");
			}
			
		} catch (SQLException e)
		{
			GWT.log("Error in SQL: ProblemList -> getProblemByAssignment()", e);
			this.getConn().rollback();
			throw e;
		}
		
		return result;
	}
	
	/**
	 * Gets all problems of a certain assignment as problem messages
	 * @param assignId Assignment Id of the Problems to return
	 * @return ArrayList of all problems in an Assignment as Problem Messages 
	 * @throws Exception
	 */
	public ArrayList<ProblemMsg> getProblemsByAssignmentLevel(int assignId, Level lvl) throws Exception 
	{
		ArrayList<ProblemMsg> result = new ArrayList<ProblemMsg>();
		
		String query = "SELECT id FROM problem "+
						"WHERE problem.assignment_id = ? AND "+
						"problem.level = ?";
		
		try
		{
			//Prepare the statement
			PreparedStatement stmt = this.getConn().prepareStatement(query);
			stmt.setInt(1, assignId);
			if(lvl == Level.Easy)
				stmt.setString(2, "Easy");
			else if(lvl == Level.Medium)
				stmt.setString(2, "Medium");
			else if(lvl == Level.Hard)
				stmt.setString(2, "Hard");
			
			//Get the result
			ResultSet rs = stmt.executeQuery();
	
			while(rs.next())
			{
				result.add(new Problem(rs.getInt(1)).getMessage());
			}
			if(result.size()<=0)
			{
				throw new Exception("No Problems Found! (getProblemByAssignmentLevel())");
			}
			
		} catch (SQLException e)
		{
			GWT.log("Error in SQL: ProblemList -> getProblemByAssignmentLevel()", e);
			this.getConn().rollback();
			throw e;
		}
		return result;
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
	 * @param connection The connection to use
	 */
	public void setConn(Connection connection)
	{
		this.conn = connection;
	}
}
