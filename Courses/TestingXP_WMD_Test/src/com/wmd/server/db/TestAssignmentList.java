package com.wmd.server.db;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.wmd.client.msg.AssignmentMsg;

/**
 * Tests AssignmentList
 * @author Tristan D., Paul C.
 *
 */
public class TestAssignmentList extends DatabaseTest 
{
	
	AssignmentList asg;
	AssignmentMsg masg;
	AssignmentMsg masg1;
	
	/**
	 * Sets up the objects for testing
	 * @throws SQLException
	 */
	@Before
	public void setup() throws SQLException
	{
		asg = new AssignmentList();
		masg = new AssignmentMsg();
		masg1 = new AssignmentMsg();
		masg.setAssignmentId(2);
		masg.setName("TestAssignment2");
		masg.setEnabled(false);
		
		masg1.setAssignmentId(1);
		masg1.setName("TestAssignment1");
		masg1.setEnabled(true);
	}
	
	/**
	 * Tests getting all assignments
	 * @throws Exception
	 */
	@Test
	public void testGettingAllAssignments() throws Exception
	{
		ArrayList<AssignmentMsg> res = asg.getAssignments();
		System.out.println(res.get(0).getName());
		assertEquals(3,res.size());
		
		assertEquals(masg.getAssignmentId(),res.get(1).getAssignmentId());
	}
	
	/**
	 * Tests getting all enabled assignments
	 * @throws Exception
	 */
	@Test
	public void testGettingAllAssignmentsEnabled() throws Exception{
		ArrayList<AssignmentMsg> res = asg.getAssignments(true);
		assertEquals(2,res.size());
		assertEquals(masg1.getAssignmentId(),res.get(0).getAssignmentId());
	}
	
	/**
	 * Tests getting all disabled assignments
	 * @throws Exception
	 */
	@Test
	public void testGettingAllAssignmentsDisabled() throws Exception{
		ArrayList<AssignmentMsg> res = asg.getAssignments(false);
		assertEquals(1,res.size());
		assertEquals(masg.getAssignmentId(),res.get(0).getAssignmentId());
	}
}
