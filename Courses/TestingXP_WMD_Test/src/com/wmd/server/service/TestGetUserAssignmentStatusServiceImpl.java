package com.wmd.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;


import org.junit.Test;

import com.wmd.client.msg.UserAssignmentStatusMsg;
import com.wmd.client.service.GetUserAssignmentStatusService;

import com.wmd.server.db.DatabaseTest;

/**
 * 
 * @author Philip Diffenderfer, Kevin Rexroth
 * @refactor Olga Zalamea
 */
public class TestGetUserAssignmentStatusServiceImpl  extends DatabaseTest
{
	
	// The user to grab an assignment from (FIXED).
	private static final int USER = 2;
	
	/**
	 * Test getting assignment statuses for all assignments for a given user.
	 * 
	 * @throws SQLException An error occurred in the database.
	 */
	@Test
	public void testGetAllUserAssignments() throws SQLException
	{
		GetUserAssignmentStatusService service = new GetUserAssignmentStatusServiceImpl();
		List<UserAssignmentStatusMsg> msgs;
	
		msgs = service.getUserAssignments(USER);
		
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
