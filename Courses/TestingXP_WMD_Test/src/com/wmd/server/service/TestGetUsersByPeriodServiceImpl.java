package com.wmd.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;


import org.junit.Test;

import com.wmd.client.msg.UserAssignmentStatusMsg;
import com.wmd.client.msg.UserMsg;
import com.wmd.client.service.GetUserAssignmentStatusService;

import com.wmd.server.db.DatabaseTest;

/**
 * @author Olga Zalamea
 *
 */
public class TestGetUsersByPeriodServiceImpl  extends DatabaseTest
{
	
	// The period to grab the list of students from (FIXED).
	private static final String PERIOD= "first";
	
	/**
	 * Test getting students for a given period.
	 * @throws SQLException An error occurred in the database.
	 */
	@Test
	public void testGetAllUserAssignments() throws SQLException
	{
		GetUsersByPeriodServiceImpl service = new GetUsersByPeriodServiceImpl();
		List<UserMsg> msgs;
	
		msgs = service.getUsers(PERIOD);
		
		// Ensure it came back valid
		assertNotNull(msgs);
		
		// Make sure some user messages existed
		assertTrue(msgs.size() > 0);
		
		// Make sure all user messages are non null
		for (UserMsg msg : msgs)
		{
			assertNotNull(msg);
		}
		
		// Make sure all user messages have the right period
		for (UserMsg msg : msgs)
		{
			assertEquals(PERIOD, msg.getPeriod());
		}
	}
	
}
