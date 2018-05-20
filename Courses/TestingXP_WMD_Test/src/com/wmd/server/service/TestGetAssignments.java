package com.wmd.server.service;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import com.wmd.client.msg.AssignmentMsg;
import com.wmd.server.db.DatabaseTest;

/**
 * This class tests that all AssignmentMsg objects pertaining to all the
 * Assignments in the database are retrieved.
 * 
 * @author Andrew Marx, Chris Koch
 * @since 31 March 2010
 * 
 */
public class TestGetAssignments extends DatabaseTest
{

	/**
	 * Tests that all the AssignmentMsg objects are retrieved from the database
	 * using GetAssignmentsServiceImpl.
	 * @throws SQLException 
	 */
	@Test
	public void testGetAllAssignments() throws SQLException
	{
		GetAssignmentsServiceImpl service = new GetAssignmentsServiceImpl();

		assertNotNull(service);
		ArrayList<AssignmentMsg> assignments = service.getAssignments();

		assertNotNull(assignments);
		assertEquals(1, assignments.size());
		
		assertEquals("testAssignment", assignments.get(0).getName());
		assertEquals(1, assignments.get(0).getAssignmentId());
		assertTrue(assignments.get(0).isEnabled());
	}

}
