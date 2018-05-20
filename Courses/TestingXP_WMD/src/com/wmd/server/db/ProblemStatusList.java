package com.wmd.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wmd.client.msg.Level;
import com.wmd.client.msg.ProblemStatusMsg;

/**
 * Manages lists of ProblemStatusMsgs.
 * 
 * @author Philip Diffenderfer, AJ Marx
 *
 */
public class ProblemStatusList
{
	
	/**
	 * Gets a list of all problem statuses a user has for a given assignment.
	 * 
	 * @param userId The user's id.
	 * @param assignmentId The assignment's id.
	 * @return A non-null list of problem status messages.
	 * @throws SQLException Error occurred in the method.
	 */
	public List<ProblemStatusMsg> getStatuses(int userId, int assignmentId) throws SQLException
	{
		// Try to make a connection to the database.
		Database db = Database.get();
		Connection conn = db.getConnection();
		
		// Query to get all statuses for a given assignment
		final String SQL_COMMAND = 
			"SELECT ps.user_id, ps.problem_id, ps.tries, ps.correct " +
			"FROM problem_status AS ps, problem AS p " +
			"WHERE ps.user_id=? AND p.assignment_id=? AND ps.problem_id = p.id";
		
		// Prepare the query
		PreparedStatement query = conn.prepareStatement(SQL_COMMAND);
		query.setInt(1, userId);
		query.setInt(2, assignmentId);
		
		// Execute query and return results
		ResultSet results = query.executeQuery();
		
		// List used to populate with statuses
		List<ProblemStatusMsg> statuses = new ArrayList<ProblemStatusMsg>();
		
		// While statuses exist in the ResultSet...
		while (results.next())
		{
			ProblemStatusMsg msg = new ProblemStatusMsg();
			msg.setUserId(results.getInt(1));
			msg.setProblemId(results.getInt(2));
			msg.setTries(results.getInt(3));
			msg.setCorrect(results.getBoolean(4));
			statuses.add(msg);
		}
		
		// Return the built list of problem status messages.
		return statuses;
	}
	
	/**
	 * Given a user and an assignment this will populate the problem_status
	 * table with the necessary information so the user is now prepared to take
	 * the given assignment.
	 * 
	 * @param userId The user's id.
	 * @param assignmentId The assignment's Id.
	 * @return True if all statuses between the assignment and user could be added.
	 */
	public boolean generateProblemStatuses(int userId, int assignmentId) throws Exception
	{
		// Try to make a connection to the database.
		Database db = Database.get();
		Connection conn = db.getConnection();
		conn.setAutoCommit(false);

		try
		{
			// Given a userId get the level of the user (to get their problem set)
			Student student = new Student(userId);
			Level level = student.getLevel();
			
			// Query to get ids of problems in a given assignment (and user level)
			final String SQL_SELECT = 
				"SELECT id FROM problem WHERE assignment_id=? and level=?";

			// Query to insert a problem status
			final String SQL_INSERT = 
				"INSERT INTO problem_status " +
				"(user_id, problem_id, tries, correct) VALUES " +
				"(?, ?, 0, 0)";
			
			// Prepare the queries (selecting problems, and inserting statuses)
			PreparedStatement query = conn.prepareStatement(SQL_SELECT);
			query.setInt(1, assignmentId);
			query.setString(2, level.toString());
			
			PreparedStatement insert = conn.prepareStatement(SQL_INSERT);
			insert.setInt(1, userId);

			// Execute query and return results
			ResultSet results = query.executeQuery();
			
			// The number of problems at the level in the assignment.
			int problemCount = 0;
			int statusesAdded = 0;
			
			// Iterate through returned id's to insert a problem status for each one.
			while (results.next()) 
			{
				// Set the problem_id in the insertion
				insert.setInt(2, results.getInt(1));
				
				// Perform insertion
				statusesAdded += insert.executeUpdate();
				problemCount++;
			}
			
			// This was successful if for each problem in the given assignment 
			// there was a statuses added for the user
			return (statusesAdded == problemCount);			
		}
		catch (SQLException e)
		{
			conn.rollback();
			e.printStackTrace();
			return false;
		}
	}
	
}
