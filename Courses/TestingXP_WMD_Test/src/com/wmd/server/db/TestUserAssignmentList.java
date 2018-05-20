package com.wmd.server.db;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.wmd.client.msg.UserAssignmentStatusMsg;

/**
 * Tests the UserAssignmentList Class
 * @author Olga Zalamea and Scotty Rhinehart
 *
 */
public class TestUserAssignmentList extends DatabaseTest
{
	UserAssignmentList userAssignment_list;
	
	@Before
	public void setUps() throws SQLException
	{
		userAssignment_list = new UserAssignmentList();
	}
	
	/**
	 * Tests getting all UserAssigment in the database
	 * @throws Exception 
	 */
	@Test
	public void testGetAllUserAssignment() throws Exception
	{
		ArrayList<UserAssignmentStatusMsg> all = userAssignment_list.getAllUserAssignmentStatus();
		assertNotNull(all);
		assertTrue(all.size()>0);
		assertEquals(1,all.get(0).getAssignmentId());
		assertEquals(2,all.get(0).getUserId());
	}
	
	/**
	 * Tests getting all UserAssigment in the database by UserId
	 * @throws Exception 
	 */
	@Test
	public void testGetAllUserAssignmentStatusByUser() throws Exception
	{
		ArrayList<UserAssignmentStatusMsg> all = userAssignment_list.getAllUserAssignmentStatusByUser(2);
		assertNotNull(all);
		assertTrue(all.size()>0);
		assertEquals(1,all.get(0).getAssignmentId());
		assertEquals(2,all.get(0).getUserId());
	}

	/**
	 * Tests getting all UserAssigment in the database by AssignmentId
	 * @throws Exception 
	 */
	@Test
	public void testGetAllUserAssignmentStatusByAssignment() throws Exception
	{
		ArrayList<UserAssignmentStatusMsg> all = userAssignment_list.getAllUserAssignmentStatusByAssignment(1);
		assertNotNull(all);
		assertTrue(all.size()>0);
		assertEquals(1,all.get(0).getAssignmentId());
		assertEquals(2,all.get(0).getUserId());
	}
}
