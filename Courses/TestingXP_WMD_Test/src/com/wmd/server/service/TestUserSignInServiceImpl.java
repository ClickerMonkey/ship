package com.wmd.server.service;

import static org.junit.Assert.*;

import org.junit.Test;

import com.wmd.client.msg.UserMsg;
import com.wmd.server.db.DatabaseTest;

/**
 * Tests to see if the user log in service impl works as expected
 * 
 * @author Scotty Rhinehart and Sam Storino
 * @refactor Olga Zalamea
 */

public class TestUserSignInServiceImpl extends DatabaseTest
{

	//constants for USERname and PASSword
	private static final String USER = "admin";
	private static final String PASS = "admin";
	
	/**
	 * Simple test to make sure someone can log on
	 */
	@Test
	public void testImpl()
	{
		//Creates a UserSignInImpl object to signin a user
		UserSignInServiceImpl usii = new UserSignInServiceImpl();
		
		//Get the handler and use the testing database
		//UserHandler handler = usii.getHandler();
		
		//Verify the user can log in
		assertEquals(USER, usii.signInUser(USER, PASS).getUsername());
		assertEquals(PASS, usii.signInUser(USER, PASS).getPassword());
	}
	
	/**
	 * Test for a user that doesn't exist
	 */
	@Test
	public void testBadUserID()
	{
		UserSignInServiceImpl usii = new UserSignInServiceImpl();
		
		// Returns null if userId doesn't exist.
		UserMsg msg = usii.signInUser("Fake User", "Gizmo");
		
		// Make sure no message was returned.
		assertNull(msg);
	}
	
	/**
	 * Test for wrong password
	 */
	@Test
	public void testBadPassword()
	{
		UserSignInServiceImpl usii = new UserSignInServiceImpl();

		// Test a valid id but invalid password.
		UserMsg msg = usii.signInUser("admin", "admin0");
		
		assertNull(msg);
	}

	/**
	 * Test for signIn of a student
	 */
	@Test
	public void testStudentSignIn()
	{
		UserSignInServiceImpl usii = new UserSignInServiceImpl();
				
		//Verify the user can log in
		assertEquals("testEasy", usii.signInUser("testEasy", "simple").getUsername());
		assertEquals("simple", usii.signInUser("testEasy", "simple").getPassword());
	}
}
