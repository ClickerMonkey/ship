package com.wmd.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.wmd.client.msg.UserAssignmentStatusMsg;
import com.wmd.client.service.GetAllUserAssignmentStatusService;

import com.wmd.server.db.DatabaseTest;

/**
 * 
 * @author Olga Zalamea
 */
public class TestGetAllUserAssignmentStatusServiceImpl  extends DatabaseTest
{
	
	// The assignment 
	private static final int ASSIGNMENT = 1;
	
	/**
	 * Test getting userAssignment statuses for all students for a given assignment.
	 * 
	 * @throws SQLException An error occurred in the database.
	 */
	@Test
	public void testGetAllUserAssignments() throws SQLException
	{
		GetAllUserAssignmentStatusService service = new GetAllUserAssignmentStatusServiceImpl();
		List<UserAssignmentStatusMsg> msgs;
	
		msgs = service.getAllUserAssignmentStatus(ASSIGNMENT);
		
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
			assertEquals(ASSIGNMENT, msg.getAssignmentId());
		}
	}
	
}
