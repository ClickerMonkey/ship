package com.wmd.server.service;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.Test;

import com.wmd.client.msg.Level;
import com.wmd.client.msg.ProblemMsg;
import com.wmd.server.db.DatabaseTest;


/**
 * 
 * @author Sam Storino, Scotty Rhinehart, Phil Diffenderfer
 * Tests the CopyProblemServiceImpl to make sure that an assignment can be copied
 * from one level to another. 
 *
 */
public class TestCopyProblemServiceImpl extends DatabaseTest
{
	/**
	 * Creates constant variables for the test problem message 
	 */
	private static final int ASSIGNMENT = 1;
	private static final int PROBLEM_NUMBER = 1;
	private static final Level PROBLEM_LEVEL_FROM = Level.Medium;
	private static final Level PROBLEM_LEVEL_TO = Level.Easy;
	
	/**
	 * Actual test that connects to the test database and copies
	 * the defined problem message from one level to another. Returns
	 * true if the copyProblem method is a success, otherwise it
	 * returns false meaning there was a problem copying it from one
	 * difficulty level to the other, or the problem doesn't exist.
	 * 
	 * @throws SQLException
	 */
	@Test
	@Ignore
	public void testImpl() throws SQLException
	{
		//Creates a CopyProblemServiceImpl object to copy the problem
		CopyProblemServiceImpl cpsi = new CopyProblemServiceImpl();
		
		// Build the problem message
		ProblemMsg msg = new ProblemMsg(ASSIGNMENT, PROBLEM_LEVEL_FROM, PROBLEM_NUMBER);
		
		// Make sure the problem is copied
		assertTrue( cpsi.copyProblem(msg, PROBLEM_LEVEL_TO) );
	}

}
