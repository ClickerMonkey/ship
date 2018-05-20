package com.wmd.server.db;
import static org.junit.Assert.*;

/**
 * @author Olga Zalamea and William Fisher
 * @description: Test for the UserHandler
 */

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import com.wmd.client.msg.Level;
import com.wmd.client.msg.Role;
import com.wmd.client.msg.UserMsg;
import com.wmd.server.db.UserHandler;

public class TestUserHandler extends DatabaseTest
{
	UserHandler user;
	
	@Before
	public void setUp()
	{
		user = new UserHandler();
	}
	
	@Test
	public void testGettingUser() throws SQLException
	{
		// get the user (2) from the data base, then verifies it has the information that is supposed to have
		UserMsg userMsg = user.getUser(2);
		assertEquals(2, userMsg.getUserId());
		assertEquals("Easy", userMsg.getLevel().name());
		assertEquals("testEasy", userMsg.getPassword());
		assertEquals("Student", userMsg.getRole().name());		
	}
	
	@Test
	public void testSignIn() throws SQLException
	{
		// proves the user exist in the data base, so it can be used to login into the system
		UserMsg userMsg = user.signinUser("testEasy", "testEasy");
		assertEquals(2, userMsg.getUserId());
		assertEquals("Easy", userMsg.getLevel().name());
		assertEquals("testEasy", userMsg.getPassword());
		assertEquals("Student", userMsg.getRole().name());
		
		// if the user doesn't exist, returns null
		UserMsg userMsg2 = user.signinUser("notIn", "notIn");
		assertNull(userMsg2);

		// if the username and password doesn't match, returns null
		UserMsg userMsg3 = user.signinUser("testEasy", "notIn");
		assertNull(userMsg3);
	}
	
	@Test
	public void testUpdateUser() throws SQLException
	{
		//get the information of the first user
		UserMsg userMsg = user.getUser(1);
		assertTrue(userMsg.isEnabled());
		userMsg.setEnable(false);

		// disable the user; enable == FALSE
		assertTrue(user.updateUser(userMsg));
		
		//get the information of the first user
		UserMsg userMsgUpdated = user.getUser(1);
		
		// proves that the user is disabled
		assertFalse(userMsgUpdated.isEnabled());
	}
	
	@Test
	public void testCreateUser() throws SQLException
	{
		//create a new user
		UserMsg userMsg =new UserMsg("newUser", "newPassword");
		userMsg.setEnable(true);
		userMsg.setRole(Role.Student);
		userMsg.setLevel(Level.Medium);
		
		//save the user in the database
		userMsg = user.createUser(userMsg);
		
		// proves the user was created; userMsg is not null
		assertNotNull(userMsg);
	}
	
}
