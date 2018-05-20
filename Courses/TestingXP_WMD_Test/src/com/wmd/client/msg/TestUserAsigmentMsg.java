package com.wmd.client.msg;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * 
 * @author Olga Zalamea and William Fisher
 * Purpose: Test used to see that a UserAssignmentMsg's methods 
 * work correctly.
 *
 */

public class TestUserAsigmentMsg {

	/**
	 * Test that checks that the objects can be added and accessed properly.
	 */
	@Test
	public void testUserAssignmentMsg()
	{
		UserAssignmentMsg userAssignmentMsg = new UserAssignmentMsg();
		UserMsg userMsg = new UserMsg("username", "password");

		AssignmentMsg assMsg = new AssignmentMsg();
		assMsg.setAssignmentId(1);
		assMsg.setEnabled(true);
		assMsg.setName("Assignment");
				
		userAssignmentMsg.setAssignment(assMsg);
		userAssignmentMsg.setUser(userMsg);
		
		assertEquals("username", userAssignmentMsg.getUser().getUsername());
		assertEquals("password", userAssignmentMsg.getUser().getPassword());

		assertEquals("Assignment", userAssignmentMsg.getAssignment().getName());
		assertEquals(1, userAssignmentMsg.getAssignment().getAssignmentId());
		assertEquals(true,userAssignmentMsg.getAssignment().isEnabled());
	}
}
