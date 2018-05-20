package com.wmd.server.db;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.wmd.client.msg.Role;
import com.wmd.client.msg.UserMsg;
/**
 * 
 * @author Steve U, Tristan D, Drew Q
 * Tests the functionality of Instructor data base object
 *
 */
public class TestInstructor extends DatabaseTest
{
	Instructor test_instr;
	UserMsg test_msg;
	/**
	 * Sets up the tests
	 * @throws Exception 
	 */
	@Before
	public void localSetUp() throws Exception
	{
		test_instr = new Instructor(6);
		test_msg = new UserMsg();
		test_msg.setUserId(6);
		test_msg.setFirstName("FirstInstr");
		test_msg.setLastName("lastInstr");
		test_msg.setUsername("teacher");
		test_msg.setPassword("teaches");
		test_msg.setEnable(true);
	}
	/**
	 * Tests the insertion of a new instructor
	 * 
	 */
	public void testInsertion() throws Exception
	{
		Instructor test_instr2 = new Instructor("dr.", "wellington", Role.Instructor, "merlin", "wizard", true);
		Instructor test_instr3 = new Instructor("merlin");
		assertEquals(test_instr3.getId(), test_instr2.getId());
	}
	/**
	 * Tests that the firstname can be retrieved
	 * @throws Exception 
	 */
	@Test
	public void testGetFirstName() throws Exception
	{
		assertEquals("FirstInstr",test_instr.getFirstName());
	}
	/**
	 * Tests that the lastname can be retrieved
	 * @throws Exception 
	 */
	@Test
	public void testGetLastName() throws Exception
	{
		assertEquals("lastInstr",test_instr.getLastName());
	}
	/**
	 * Tests that the username can be retrieved
	 * @throws Exception 
	 */
	@Test
	public void testGetUsername() throws Exception
	{
		assertEquals("teacher",test_instr.getUsername());
	}
	/**
	 * Tests that the password can be retrieved
	 * @throws Exception 
	 */
	@Test
	public void testGetPassword() throws Exception
	{
		assertEquals("teaches",test_instr.getPassword());
	}
	/**
	 * Tests to see if the user is enabled
	 * @throws Exception 
	 */
	@Test
	public void testGetEnabled() throws Exception
	{
		assertEquals(true,test_instr.isEnabled());
	}
	/**
	 * Tests to see if the firstname can be set and then gotten
	 * @throws Exception 
	 */
	@Test
	public void testSetFirstName() throws Exception
	{
		test_instr.setFirstName("TRISTAN!");
		assertEquals("TRISTAN!",test_instr.getFirstName());
	}
	/**
	 * Tests to see if the lastname can be set and then gotten
	 * @throws Exception 
	 */
	@Test
	public void testSetLastName() throws Exception
	{
		test_instr.setLastName("MyLastName");
		assertEquals("MyLastName",test_instr.getLastName());
	}
	/**
	 * Tests to see if the username can be set and then gotten
	 * @throws Exception 
	 */
	@Test
	public void testSetUsername() throws Exception
	{
		test_instr.setUsername("Lullorsox");
		assertEquals("Lullorsox",test_instr.getUsername());
	}
	/**
	 * Tests to see if the password can be set and then gotten
	 * @throws Exception 
	 */
	@Test
	public void testSetPassword() throws Exception
	{
		test_instr.setPassword("password");
		assertEquals("password",test_instr.getPassword());
	}
	/**
	 * Tests to see if the user can be enabled and disabled
	 * @throws Exception 
	 */
	@Test
	public void testSetEnabled() throws Exception
	{
		test_instr.setEnabled(false);
		assertEquals(false,test_instr.isEnabled());
	}
	
	/**
	 * Tests the deleteUser method
	 * @throws Exception 
	 */
	public void testDeleteUser() throws Exception
	{
		Instructor test_instr_rawr = new Instructor("fname234e23", "lname243223er", Role.Instructor, "username2342dw32e", "wizard", true);
		Instructor test_instr_rawr2 = new Instructor("username2342dw32e");
		assertEquals(test_instr_rawr2, test_instr_rawr2);
		
		test_instr_rawr.deleteUser(test_instr_rawr.getId());
		assertEquals(null, test_instr_rawr.getFirstName());
	}
}
