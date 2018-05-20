package com.wmd.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gwt.core.client.GWT;
import com.wmd.client.msg.ProblemStatusMsg;
import com.wmd.client.service.ServiceException;
/**
 * Gets info from the Problem Status table of the DB.
 * @author Christopher Eby and William Fisher
 * @author Refactoring: Drew Q, Steve U, Tristan D
 */
public class ProblemStatus
{
	//User Id
	private int userId;
	//Problem id
	private int problemId;
	/**
	 * Initializes the ProblemStatus class and checks to make sure it exists.
	 * @param userId key 1
	 * @param problemId key 2
	 * @throws SQLException
	 * @throws ServiceException
	 */
	public ProblemStatus(int userId, int problemId) throws SQLException, ServiceException
	{
		this.userId = userId;
		this.problemId = problemId;
		
		Connection con = Database.getSingleton().getConnection();

		PreparedStatement s = con
				.prepareStatement("SELECT tries, correct FROM problem_status" +
						" WHERE user_id = ? AND problem_id = ?");
		s.setInt(1, userId);
		s.setInt(2, problemId);
		ResultSet resultSet = s.executeQuery();
		if (!resultSet.next())
		{
			throw new ServiceException("Attempted to reference a non-existant problem");
		}
	}
	/**
	 * Gets the message for this class.
	 * @return ProblemStatusMsg of user Id
	 * @throws SQLException
	 */
	public ProblemStatusMsg getMessage() throws SQLException
	{
		
		ProblemStatusMsg msg = new ProblemStatusMsg();
		msg.setUserId(userId);
		msg.setProblemId(problemId);
		msg.setTries(this.getTries());
		msg.setCorrect(this.getCorrect());
		return msg;
	}
	/**
	 * Gets the correct status from the DB
	 * @return the correct status s boolean
	 * @throws SQLException
	 */
	private boolean getCorrect() throws SQLException
	{
		Connection con = Database.getSingleton().getConnection();

		PreparedStatement s = con
				.prepareStatement("SELECT correct FROM problem_status" +
						" WHERE user_id = ? AND problem_id = ?");
		s.setInt(1, userId);
		s.setInt(2, problemId);
		ResultSet resultSet = s.executeQuery();
		resultSet.first();
		boolean correct = true;
		if(resultSet.getInt("correct") == 0)
			correct = false;
		return correct;
	}
	/**
	 * Gets the tries from the DB.
	 * @return number of tries
	 * @throws SQLException
	 */
	public int getTries() throws SQLException
	{
		Connection con = Database.getSingleton().getConnection();

		PreparedStatement s = con
				.prepareStatement("SELECT tries FROM problem_status" +
						" WHERE user_id = ? AND problem_id = ?");
		s.setInt(1, userId);
		s.setInt(2, problemId);
		ResultSet resultSet = s.executeQuery();
		resultSet.first();
		return resultSet.getInt("tries");
	}
	
	/**
	 * Sets the correct status of the problem status.
	 * Will not change if the problem is already correct.
	 * @param correct The new correct status
	 */
	public void setCorrect(boolean correct) 
	{
		Connection con = Database.getSingleton().getConnection();
		
		String query = "UPDATE problem_status SET correct = ? WHERE user_id = ? AND problem_id = ?";
		
		try
		{
			//prep
			PreparedStatement stmt = con.prepareStatement(query);
			
			//bind
			stmt.setInt(2, this.userId);
			stmt.setInt(3, this.problemId);
			stmt.setInt(1, (correct ? 1 : 0));
			
			//exec
			stmt.executeUpdate();
		}
		catch(SQLException e)
		{
			GWT.log("Exception in ProblemSTatus->setCorrect", e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates the number of tries the user has attempted
	 * at this problem
	 * @param tries The new number of tries
	 */
	public void setTries(int tries) 
	{
		Connection con = Database.getSingleton().getConnection();
		
		String query = "UPDATE problem_status SET tries = ? WHERE user_id = ? AND problem_id = ? ";
		
		try
		{
			//prep
			PreparedStatement stmt = con.prepareStatement(query);
			
			//bind
			stmt.setInt(2, this.userId);
			stmt.setInt(3, this.problemId);
			stmt.setInt(1, tries);
			
			//exec
			stmt.executeUpdate();
		}
		catch(SQLException e)
		{
			GWT.log("Exception in ProblemSTatus->setTries", e);
			e.printStackTrace();
		}
		
	}

}
