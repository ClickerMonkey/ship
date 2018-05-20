package com.wmd.server.db;

import static org.junit.Assert.*;
import org.junit.Test;
import com.wmd.client.msg.UserAssignmentStatusMsg;

/**
 * Tests the Student class
 * 
 * @author Olga Zalamea and Scotty Rhinehart
 */

public class TestUserAssignment extends DatabaseTest
{
	/**
	 * Test initialization of Student
	 */
	@Test
	public void testInitialization() 
	{
		int userId = 2;
		int assignmentId =1;
		try {
			UserAssignment test = new UserAssignment(userId, assignmentId );
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests the getProblemCOunt method
	 * @throws Exception 
	 */
	@Test
	public void testGetProblemCount() throws Exception
	{
		int userId = 2;
		int assignmentId =1;
		UserAssignment test = new UserAssignment(userId, assignmentId );
		
		assertEquals(2, test.getProblemCount());
	}
	
	/**
	 * Tests the getProblemCsCorrect method
	 * @throws Exception 
	 */
	@Test
	public void testGetProblemsCorrect() throws Exception
	{
		int userId = 2;
		int assignmentId =1;
		UserAssignment test = new UserAssignment(userId, assignmentId );
		
		assertEquals(1, test.getProblemsCorrect());
	}
	
	/**
	 * Tests the getProblemsTried method
	 * @throws Exception 
	 */
	@Test
	public void testGetProblemsTried() throws Exception
	{
		int userId = 2;
		int assignmentId =1;
		UserAssignment test = new UserAssignment(userId, assignmentId );
		
		assertEquals(1, test.getProblemsTried());
	}
	
	/**
	 * Tests the getTotalTries method
	 * @throws Exception 
	 */
	@Test
	public void testGetTotalTries() throws Exception
	{
		int userId = 2;
		int assignmentId =1;
		UserAssignment test = new UserAssignment(userId, assignmentId );
		
		assertEquals(4, test.getTotalTries());
	}
	
	/**
	 * Tests the getMessage method
	 * @throws Exception 
	 */
	@Test
	public void testGetMessage() throws Exception
	{
		int userId = 2;
		int assignmentId =1;
		UserAssignment test = new UserAssignment(userId, assignmentId );
		UserAssignmentStatusMsg msg = test.getMessage();
		
		assertEquals(assignmentId, msg.getAssignmentId());
		assertEquals("TestAssignment1", msg.getAssignmentName());
		assertEquals(userId, msg.getUserId());		
		assertEquals("FirstEasy", msg.getFirstName());
		assertEquals("LastEasy", msg.getLastName());
		assertEquals("first", msg.getPeriod());
		assertEquals(2,msg.getProblemCount());
		assertEquals(1,msg.getProblemsCorrect());
		assertEquals(1,msg.getProblemsTried());
		assertEquals(4,msg.getTotalTries());
		assertNotNull(msg.getUser());
	}
	
}
