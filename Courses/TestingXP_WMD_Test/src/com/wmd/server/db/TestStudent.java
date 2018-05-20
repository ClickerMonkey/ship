package com.wmd.server.db;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.wmd.client.msg.Level;
import com.wmd.client.msg.Role;
import com.wmd.client.msg.UserMsg;

/**
 * Tests the Student class
 * 
 * @author Olga Zalamea and Scotty Rhinehart, Sam Storino
 */

public class TestStudent extends DatabaseTest
{
	/**
	 * Test initialization of Student
	 */
	@Test
	public void testInitialization() 
	{
		
		
		String user = "testEasy";
		String pass = "simple";
		Student test;
		try {
			test = new Student(user, pass);
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Student fail;
		try {
			fail = new Student("failure", "superFailure");
		} catch (Exception e) {
			assertTrue(true);
			e.printStackTrace();
		}		
	}
	
	/**
	 * Test Singin Student
	 */
	@Test
	public void testSigninUser() 
	{
		String user = "testEasy";
		String pass = "simple";
		Student test;
		try {
			test=new Student(user, pass);
			UserMsg student= test.signinUser();
			assertEquals(user, student.getUsername());
			assertEquals(pass, student.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Tests the getMessage method
	 * @throws Exception 
	 */
	@Test
	public void testGetMessage() throws Exception
	{
		Student test = new Student(2);
		UserMsg msg = test.getMessage();
		assertEquals("FirstEasy", msg.getFirstName());
		assertEquals("LastEasy", msg.getLastName());
		assertEquals("Easy", msg.getLevel().name());
		assertEquals("Student", msg.getRole().name());
		assertEquals("testEasy", msg.getUsername());
		assertEquals("simple", msg.getPassword());
		assertEquals("first", msg.getPeriod());
		assertTrue(msg.isEnabled());
	}
	
	/**
	 * Tests getting the student firstname
	 * @throws Exception 
	 */
	@Test
	public void testGetFirstname() throws Exception
	{
		Student test = new Student(2);
		assertEquals("FirstEasy",test.getFirstname());
	}

	/**
	 * Tests getting the lastName
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetLastname() throws Exception
	{
		Student test = new Student(2);
		assertEquals("LastEasy",test.getLastname());
	}

	/**
	 * Tests getting the username
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetUsername() throws Exception
	{
		Student test = new Student(2);
		assertEquals("testEasy",test.getUsername());		
	}
	
	/**
	 * Tests getting the password
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetPassword() throws Exception
	{
		Student test = new Student(2);
		assertEquals("simple",test.getPassword());	
	}
	
	/**
	 * Tests getting the period
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetPeriod() throws Exception
	{
		Student test = new Student(2);
		assertEquals("first",test.getPeriod());	
	}
	
	/**
	 * Tests getting the Level
	 * @throws Exception
	 */
	@Test
	public void testGetLevel() throws Exception
	{
		Student test = new Student(2);
		assertEquals(Level.Easy, test.getLevel());
	}
	
	/**
	 * Tests getting the Role
	 * @throws Exception
	 */
	@Test
	public void testGetRole() throws Exception
	{
		Student test = new Student(2);
		assertEquals(Role.Student, test.getRole());
	}
	
	/**
	 * Tests setting the Firstname
	 * @throws SQLException
	 */
	@Test
	public void testSetFirstname() throws Exception
	{
		Student test = new Student(2);
		String testName = "Dude";
		test.setFirstname(testName);
		assertEquals(testName, test.getFirstname());
	}

	/**
	 * Tests setting the Lastname
	 * @throws Exception
	 */
	@Test
	public void testSetLastname() throws Exception
	{
		Student test = new Student(2);
		String testLastname = "Dude";
		test.setLastname (testLastname );
		assertEquals(testLastname , test.getLastname());
	}

	/**
	 * Tests setting the username
	 * @throws Exception
	 */
	@Test
	public void testSetUsername() throws Exception
	{
		Student test = new Student(2);
		String testUsername = "Dude";
		test.setLastname (testUsername );
		assertEquals(testUsername, test.getLastname());
	}

	/**
	 * Tests setting the password
	 * @throws Exception
	 */
	@Test
	public void testSetPassword() throws Exception
	{
		Student test = new Student(2);
		String testPassword = "Dude";
		test.setPassword (testPassword );
		assertEquals(testPassword, test.getPassword());
	}

	/**
	 * Tests setting the period
	 * @throws Exception
	 */
	@Test
	public void testSetPeriod() throws Exception
	{
		Student test = new Student(2);
		String testPeriod = "Dude";
		test.setPeriod(testPeriod);
		assertEquals(testPeriod, test.getPeriod());
	}

	/**
	 * Tests getEnabled method
	 * @throws Exception 
	 */
	@Test
	public void testGetEnabled() throws Exception
	{
		Student test = new Student(2);
		assertTrue(test.isEnabled());
	}
	
	/**
	 * Tests setEnabled method
	 * @throws Exception 
	 */
	@Test
	public void testSetEnabled() throws Exception
	{
		Student test = new Student(2);
		test.setEnabled(false);
		assertFalse(test.isEnabled());
	}

	/**
	 * Tests setLevel method
	 * @throws Exception 
	 */
	@Test
	public void testSetLevel() throws Exception
	{
		Student test = new Student(2);
		test.setLevel(Level.Hard);
		assertEquals(Level.Hard,test.getLevel());
	}

	/**
	 * Tests setRole method
	 * @throws Exception 
	 */
	@Test
	public void testSetRole() throws Exception
	{
		Student test = new Student(1);
		test.setRole();
		assertEquals(Role.Student, test.getRole());
	}
	
	/**
	 * Tests insertion of user
	 * @throws Exception
	 */
	public void testInsertion() throws Exception
	{
		Student test_instr2 = new Student("dr.", "wellington", Role.Instructor, "merlin", "wizard", Level.Hard, "first", true);
		Student test_instr3 = new Student("merlin", "wizard");
		assertEquals(test_instr3.getId(), test_instr2.getId());
	}
	
	/**
	 * Tests the deleteUser method
	 * @throws Exception 
	 */
	public void testDeleteUser() throws Exception
	{
		Student test_instr_rawr = new Student("fname234e23", "lname243223er", Role.Instructor, "username2342dw32e", "wizard", Level.Hard, "first", true);
		Student test_instr_rawr2 = new Student("username2342dw32e", "wizard");
		assertEquals(test_instr_rawr2, test_instr_rawr2);
		
		test_instr_rawr.deleteUser(test_instr_rawr.getId());
		assertEquals(null, test_instr_rawr.getFirstname());
	}
}
