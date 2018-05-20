package com.wmd.client.msg;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * Tests the UserMsg class.
 * @author Christopher Eby and Olga Zalamea
 *
 */
public class TestUserMsg 
{
	/**
	 * Tests initailzing the UserMsg class.
	 */
	@Test
	public void testInitialization()
	{
		UserMsg userMsg = new UserMsg("username", "password");
		assertEquals("username", userMsg.getUsername());
		assertEquals("password", userMsg.getPassword());
	}
	/**
	 * Tests getting the user Id
	 */
	@Test
	public void testGetUserId()
	{
		UserMsg userMsg = new UserMsg("admin", "admin");
		userMsg.setUserId(2);
		assertEquals(userMsg.getUserId(),2);
	}
	
	/**
	 * Tests getting the user role.
	 */
	@Test
	public void testGetUserRole()
	{
		UserMsg userMsg = new UserMsg("admin", "admin");
		userMsg.setRole(Role.Admin);
		assertEquals(userMsg.getRole().name(), "Admin");
	}

	/**
	 * Tests getting the user level.
	 */
	@Test
	public void testGetUserLevel()
	{
		UserMsg userMsg = new UserMsg("admin", "admin");
		userMsg.setLevel(Level.Hard);
		assertEquals(userMsg.getLevel().name(), "Hard");
	}
	/**
	 * Tests checking is the user is enabled.
	 */
	@Test
	public void testGetUserEnabled()
	{
		UserMsg userMsg = new UserMsg("admin", "admin");
		userMsg.setEnable(true);
		assertTrue(userMsg.isEnabled());
	}
	/**
	 * Tests setting the users username and password.
	 */
	@Test
	public void testSetUsernamePassword()
	{
		UserMsg userMsg = new UserMsg("username", "password");
		userMsg.setUsername("newUsername");
		userMsg.setPassword("newPassword");
		assertEquals("newUsername", userMsg.getUsername());
		assertEquals("newPassword", userMsg.getPassword());
	}
}
