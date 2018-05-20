package com.wmd.server.db;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the Assignment database object class
 * and all its functions and exceptions
 * @author Drew Q, Paul C
 * 
 */
public class TestAssignment extends DatabaseTest
{
	
	/**
	 * Tests getting the ID
	 * @throws Exception 
	 */
	@Test
	public void testGetId() throws Exception
	{
		Assignment test_a = new Assignment(3);
		
		assertEquals(3,test_a.getId());
	}
	
	/**
	 * Tests getting the assignment name
	 * @throws Exception 
	 */
	@Test
	public void testGetAssignmentName() throws Exception
	{
		Assignment test_a = new Assignment(1);
		
		assertEquals("TestAssignment1",test_a.getName());
	}
	
	/**
	 * Tests if the assignment is enabled
	 * @throws Exception 
	 */
	@Test
	public void testIsAssignmentEnabled() throws Exception
	{
		Assignment test_a1 = new Assignment(1);
		Assignment test_a2 = new Assignment(2);
		
		assertTrue(test_a1.isEnabled());
		assertFalse(test_a2.isEnabled());
	}
	
	/**
	 * tests setting the assignment name
	 * @throws Exception 
	 */
	@Test
	public void testSetAssignmentName() throws Exception
	{
		Assignment test_a = new Assignment(1);
		
		test_a.setName("TestNewAssignmentName");
		
		assertEquals("TestNewAssignmentName",test_a.getName());
	}
	
	/**
	 * Tests setting the enabled state of the assignment
	 * @throws Exception 
	 */
	@Test
	public void testSetEnabled() throws Exception
	{
		Assignment test_a = new Assignment(1);
		
		test_a.setEnabled(false);
		
		assertFalse(test_a.isEnabled());
	}
	
	/**
	 * Tests making a new assignment
	 * @throws Exception 
	 */
	@Test
	public void testCreateAssignment() throws Exception
	{
		Assignment new_a = new Assignment("NewAssignment1",false);
		
		assertEquals("NewAssignment1", new_a.getName());
	}
	
	/**
	 * Tests throwing an exception when you try
	 * to get an ID that doesnt exist
	 * @throws Exception 
	 */
	@Test(expected = Exception.class)
	public void testExceptionThrowing() throws Exception
	{
		@SuppressWarnings("unused")
		Assignment fail_a = new Assignment(10);
	}
	
}
