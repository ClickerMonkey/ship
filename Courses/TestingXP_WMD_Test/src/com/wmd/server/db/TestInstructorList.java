package com.wmd.server.db;

import static org.junit.Assert.assertEquals;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import com.wmd.client.msg.UserMsg;
/**
 * 
 * @author Tristan D., Steve U.
 * test class for instructorList
 *
 */
public class TestInstructorList extends DatabaseTest
{
	InstructorList instr_list;
	/**
	 * Sets up the Instructor list
	 * @throws SQLException
	 */
	@Before
	public void setUps() throws SQLException
	{
		instr_list = new InstructorList();
	}
	/**
	 * Test to see that all Instructor can be gotten and data retrieved correctly
	 * @throws Exception
	 */
	@Test
	public void testGetAllAdmins() throws Exception
	{
		ArrayList<UserMsg> all = instr_list.getAllInstructors();
		
		assertEquals(1, all.size());
		assertEquals(6, all.get(0).getUserId());
		assertEquals("FirstInstr",all.get(0).getFirstName());
		assertEquals("lastInstr",all.get(0).getLastName());
		assertEquals("teacher",all.get(0).getUsername());
		assertEquals("teaches",all.get(0).getPassword());
	}
	/**
	 * Test to see that if an Instructor is not enabled that it takes that into an account
	 * @throws Exception
	 */
	@Test
	public void getInstructorsByEnabled() throws Exception
	{
		ArrayList<UserMsg> all = instr_list.getInstructors(true);
		
		assertEquals(1, all.size());
		assertEquals(6, all.get(0).getUserId());
		assertEquals("FirstInstr",all.get(0).getFirstName());
		assertEquals("lastInstr",all.get(0).getLastName());
		assertEquals("teacher",all.get(0).getUsername());
		assertEquals("teaches",all.get(0).getPassword());
		
		// Check to see that it works both way
		ArrayList<UserMsg> all2 = instr_list.getInstructors(false);
		assertEquals(0, all2.size());
		
	}
}
