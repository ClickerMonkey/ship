package com.wmd.server.db;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.wmd.client.msg.UserAssignmentStatusMsg;

/**
 * 
 * @author Philip Diffenderfer, Kevin Rexroth
 *
 */
public class TestUserAssignmentStatusHandler 
{
	// The user to grab an assignment from (FIXED).
	private static final int USER = 2;
	
	// The assignment to access (FIXED)
	private static final int ASSIGNMENT = 1;
	
	/**
	 * Prepare each test by setting the database to testing.
	 */
	@Before
	public void prepare()
	{
		Database.setTesting(true);
	}
	
	/**
	 * Tests the UserAssignmentStatusHandler.
	 * 
	 * @throws SQLException An error occurred in the database.
	 */
	@Test
	public void testGetSingleAssignment() throws SQLException
	{
		UserAssignmentStatusHandler handler = new UserAssignmentStatusHandler();
		UserAssignmentStatusMsg msg;
		
		// Make the database call
		msg = handler.getUserAssignment(USER, ASSIGNMENT);
		
		// We've gotten a message succesfully
		assertNotNull(msg);
	
		// Ensure we got the right one. This user assignment is fixed and 
		// should always have at least 1 for the problem statistics.
		assertEquals(ASSIGNMENT, msg.getAssignmentId());
		assertEquals(USER, msg.getUserId());
		assertNotNull(msg.getAssignmentName());
		assertTrue(msg.getProblemCount() > 0);
	}
	
	/**
	 * Test getting assignment statuses for all assignments for a given user.
	 * 
	 * @throws SQLException An error occurred in the database.
	 */
	@Test
	public void testGetAllUserAssignments() throws SQLException
	{
		UserAssignmentStatusHandler handler = new UserAssignmentStatusHandler();
		List<UserAssignmentStatusMsg> msgs;
	
		msgs = handler.getUserAssignments(USER);
		
		// Ensure it came back valid
		assertNotNull(msgs);
		
		// Make sure some status messages existed
		assertTrue(msgs.size() > 0);
		
		// Make sure all status messages are non null
		for (UserAssignmentStatusMsg msg : msgs)
		{
			assertNotNull(msg);
		}
		
		// Make sure all status messages have the right userId
		for (UserAssignmentStatusMsg msg : msgs)
		{
			assertEquals(USER, msg.getUserId());
		}
	}
	
}
