package com.wmd.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import org.junit.Test;

import com.wmd.client.msg.AssignmentMsg;
import com.wmd.client.service.ServiceException;
import com.wmd.server.db.DatabaseTest;

/**
 * Tests for the service that adds a new assignment
 * 
 * @author Merlin
 * 
 *         Created: Apr 1, 2010
 */
public class TestAddAssignmentServiceImpl extends DatabaseTest
{

	/**
	 * The basic test for when everything works nicely
	 * @throws Exception 
	 */
	@Test
	public void testSimple() throws Exception
	{
		AddAssignmentServiceImpl svc = new AddAssignmentServiceImpl();
		AssignmentMsg msg = svc.addAssignment("My Silly Assignment",true);
		assertEquals("My Silly Assignment", msg.getName());
		assertTrue(msg.isEnabled());
	}

	/**
	 * This should throw a service exception because the test database already
	 * contains an assignment with the name "testAssignment"
	 * @throws Exception 
	 */
	@Test(expected = ServiceException.class)
	public void testDuplicate() throws Exception
	{
		AddAssignmentServiceImpl svc = new AddAssignmentServiceImpl();
		svc.addAssignment("TestAssignment2",true);
	}

}
