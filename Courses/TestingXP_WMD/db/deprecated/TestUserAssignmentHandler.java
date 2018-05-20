package com.wmd.server.db;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import com.wmd.client.msg.AssignmentMsg;
import com.wmd.client.msg.UserAssignmentMsg;

/**
 * @author Olga Zalamea and William Fisher
 * @description: Test for the UserAssignmentHandler
 */

public class TestUserAssignmentHandler  extends DatabaseTest
{

	UserAssignmentHandler userAssignment;

	@Before
	public void setup()
	{
		userAssignment = new UserAssignmentHandler();
	}
	
	@Test
	public void testGetUserAssignment() throws SQLException
	{
//		AssignmentHandler assignment= new AssignmentHandler();
//		UserHandler user= new UserHandler();
//		
//		int assignmentId = assignment.saveOrUpdate("newAssignment", true);
//
//		UserMsg userMsg =new UserMsg("newUser", "newPassword");		
//		userMsg = user.createUser(userMsg);
//		userMsg.setEnable(true);
//		userMsg.setRole(Role.Student);
//		userMsg.setLevel(Level.Medium);
// 		TODO: next iteration add the (user,assignment) into the database, delete at the end	
		fail("fix this next iteration");
		UserAssignmentHandler userAssignment = new UserAssignmentHandler();
		UserAssignmentMsg message;
		// For this test we are assuming already exist the assignment # 1
		// and user # 2, since for this iteration we aren't creating user_assigments
		message = userAssignment.getUserAssignment(1, 2);
		assertEquals(1,message.getAssignment().getAssignmentId());
		assertEquals(2,message.getUser().getUserId());
	}
	
	@Test
	public void testgetAllAssignments() throws SQLException
	{
// 		TODO: next iteration add user, assignments, user_asignments
		fail("fix this next iteration");
		UserAssignmentHandler usersAssignments = new UserAssignmentHandler();
		ArrayList<AssignmentMsg> assignments = usersAssignments.getAllUserAssignments(2);
		assertNotNull(assignments);
		// For this test we are assuming already exist the assignment # 1
		// since for this iteration we aren't user_assigments
		assertEquals(1, assignments.get(0).getAssignmentId());
	}

}
