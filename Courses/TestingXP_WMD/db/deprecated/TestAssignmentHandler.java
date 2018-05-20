package com.wmd.server.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.wmd.client.msg.AssignmentMsg;


/**
 * TestAssignmentHandler - Tests the handler for a list of assignments
 * @author Drew Q, Tristan D
 * 
 */
public class TestAssignmentHandler extends DatabaseTest
{
	AssignmentMsg test_assignment;
	AssignmentHandler assign_hand;
	
	/**
	 * Sets up the tests
	 * @throws Exception 
	 */
	@Before
	public void setUp() throws Exception
	{
		super.setUp();
		test_assignment = new AssignmentMsg("Super Ultra Hard Assignment",true);
		assign_hand = new AssignmentHandler();
	}

	/**
	 * Tests save or update
	 * @throws SQLException
	 */
	@Test
	public void testSave() throws SQLException
	{
		int id = assign_hand.saveOrUpdate("Super Ultra Hard Assignment",true);
		assertTrue(id != -1);
	}
	
	/**
	 * Tests remove
	 * @throws SQLException
	 */
	@Test
	public void testRemove() throws SQLException
	{
		//test delete
		int id = assign_hand.saveOrUpdate(test_assignment);
		assertTrue(id != -1);
		assertEquals(1, assign_hand.remove(id));
	}
	
	/**
	 * Tests getting the assignment
	 * @throws SQLException
	 */
	@Test
	public void getAssignment() throws SQLException
	{
		int id = assign_hand.saveOrUpdate(test_assignment);
		AssignmentMsg test_am = assign_hand.getAssignment(id);
		
		assertEquals("Super Ultra Hard Assignment",test_am.getName());
		assertEquals(true,test_am.isEnabled());

	}
	
	/**
	 * Tests updating an assignment
	 * @throws SQLException
	 */
	@Test
	public void updateAssignment() throws SQLException
	{
		int id = assign_hand.saveOrUpdate(test_assignment);
		AssignmentMsg test_am = assign_hand.getAssignment(id);

		test_am.setName("Not As Hard Assignment");
		test_am.setEnabled(false);

		assertEquals("Not As Hard Assignment",test_am.getName());
		
		id = assign_hand.saveOrUpdate(test_am);
		AssignmentMsg test_am2 = assign_hand.getAssignment(id);

		assertEquals("Not As Hard Assignment",test_am2.getName());
		assertEquals(false,test_am2.isEnabled());

	}
	
	/**
	 * make sure that we can get the ID if the assignment name
	 * exists and that zero is returned if it doesn't
	 */
	@Test
	public void testGetAssignmentID()
	{
		AssignmentHandler ah = new AssignmentHandler();
		assertEquals(1,ah.getAssignmentID("testAssignment"));
		assertEquals(2,ah.getAssignmentID("Assignment2"));
	}
	/**
	 * Tests getting the list of all assignments
	 * @throws SQLException
	 */
	@Test
	public void testGetAllAssignments() throws SQLException
	{
//		fail("NEVER RUIN THE DATABASE IN A TEST!!!!  Whoever wrote this needs to make a real test");
//		String q = "TRUNCATE TABLE assignment";
//		PreparedStatement p = assign_hand.getConn().prepareStatement(q);
//		p.executeQuery();
//		
//		assign_hand.saveOrUpdate("Super Ultra Hard Assignment",true);
//		assign_hand.saveOrUpdate("Super Ultra Hard Assignment 2",false);
//		assign_hand.saveOrUpdate("Super Ultra Hard Assignment 3",true);
//		ArrayList<AssignmentMsg> test_am_list = assign_hand.getAllAssignments(true);
//		ArrayList<AssignmentMsg> test_am_list2 = assign_hand.getAllAssignments();
//		
//		assertEquals(2,test_am_list.size());
//		assertEquals(3,test_am_list2.size());
		assertEquals(2, this.assign_hand.getAllAssignments().size());
	}
}
