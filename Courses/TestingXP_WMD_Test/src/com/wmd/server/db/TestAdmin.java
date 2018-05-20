package com.wmd.server.db;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.wmd.client.msg.UserMsg;
/**
 * 
 * @author Steve U, Drew Q
 * Tests the functionality of Admin data base object
 *
 */
public class TestAdmin extends DatabaseTest
{
	Admin test_admin;
	UserMsg test_msg;
	/**
	 * Sets up the tests
	 * @throws SQLException
	 */
	@Before
	public void localSetUp() throws SQLException
	{
		test_admin = new Admin(1);
		test_msg = new UserMsg();
		test_msg.setUserId(1);
		test_msg.setFirstName("FirstAdmin");
		test_msg.setLastName("lastAdmin");
		test_msg.setUsername("admin");
		test_msg.setPassword("admin");
		test_msg.setEnable(true);
	}
	/**
	 * Tests that the firstname can be retrieved
	 * @throws SQLException
	 */
	@Test
	public void testGetFirstName() throws SQLException
	{
		assertEquals("FirstAdmin",test_admin.getFirstName());
	}
	/**
	 * Tests that the lastname can be retrieved
	 * @throws SQLException
	 */
	@Test
	public void testGetLastName() throws SQLException
	{
		assertEquals("lastAdmin",test_admin.getLastName());
	}
	/**
	 * Tests that the username can be retrieved
	 * @throws SQLException
	 */
	@Test
	public void testGetUsername() throws SQLException
	{
		assertEquals("admin",test_admin.getUsername());
	}
	/**
	 * Tests that the password can be retrieved
	 * @throws SQLException
	 */
	@Test
	public void testGetPassword() throws SQLException
	{
		assertEquals("admin",test_admin.getPassword());
	}
	/**
	 * Tests to see if the user is enabled
	 * @throws SQLException
	 */
	@Test
	public void testGetEnabled() throws SQLException
	{
		assertEquals(true,test_admin.isEnabled());
	}
	/**
	 * Tests to see if the firstname can be set and then gotten
	 * @throws SQLException
	 */
	@Test
	public void testSetFirstName() throws SQLException
	{
		test_admin.setFirstName("TRISTAN!");
		assertEquals("TRISTAN!",test_admin.getFirstName());
	}
	/**
	 * Tests to see if the lastname can be set and then gotten
	 * @throws SQLException
	 */
	@Test
	public void testSetLastName() throws SQLException
	{
		test_admin.setLastName("MyLastName");
		assertEquals("MyLastName",test_admin.getLastName());
	}
	/**
	 * Tests to see if the username can be set and then gotten
	 * @throws SQLException
	 */
	@Test
	public void testSetUsername() throws SQLException
	{
		test_admin.setUsername("Lullorsox");
		assertEquals("Lullorsox",test_admin.getUsername());
	}
	/**
	 * Tests to see if the password can be set and then gotten
	 * @throws SQLException
	 */
	@Test
	public void testSetPassword() throws SQLException
	{
		test_admin.setPassword("password");
		assertEquals("password",test_admin.getPassword());
	}
	/**
	 * Tests to see if the user can be enabled and disabled
	 * @throws SQLException
	 */
	@Test
	public void testSetEnabled() throws SQLException
	{
		test_admin.setEnabled(false);
		assertEquals(false,test_admin.isEnabled());
	}
}
