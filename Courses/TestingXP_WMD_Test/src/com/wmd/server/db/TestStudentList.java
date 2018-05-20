package com.wmd.server.db;

import static org.junit.Assert.assertEquals;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import com.wmd.client.msg.Level;
import com.wmd.client.msg.UserMsg;
/**
 * 
 * @author Tristan D., Steve U.
 *	Test class for studentList
 */
public class TestStudentList extends DatabaseTest
{
	StudentList stu_list;
	/**
	 * Sets up the Students list
	 * @throws SQLException
	 */
	@Before
	public void setUps() throws SQLException
	{
		stu_list = new StudentList();
	}
	/**
	 * Test to see that all Students can be gotten and data retrieved correctly
	 * @throws Exception
	 */
	@Test
	public void testGetAllStudents() throws Exception
	{
		ArrayList<UserMsg> all = stu_list.getAllStudents();
		assertEquals(4, all.size());
		assertEquals(2, all.get(0).getUserId());
		assertEquals("FirstEasy",all.get(0).getFirstName());
		assertEquals("LastEasy",all.get(0).getLastName());
		assertEquals("testEasy",all.get(0).getUsername());
		assertEquals("simple",all.get(0).getPassword());
	}
	/**
	 * Test to see that if an Students is not enabled that it takes that into an account
	 * @throws Exception
	 */
	@Test
	public void getStudentsByEnabled() throws Exception
	{
		ArrayList<UserMsg> all = stu_list.getStudents(true);
		assertEquals(3, all.size());
		assertEquals(2, all.get(0).getUserId());
		assertEquals("FirstEasy",all.get(0).getFirstName());
		assertEquals("LastEasy",all.get(0).getLastName());
		assertEquals("testEasy",all.get(0).getUsername());
		assertEquals("simple",all.get(0).getPassword());
		
		// Check to see that it works both way
		ArrayList<UserMsg> all2 = stu_list.getStudents(false);
		assertEquals(1, all2.size());
		
	}
	/**
	 * Tests to see that students can be gotten by level
	 * @throws Exception
	 */
	@Test
	public void getAllStudentsByLvl() throws Exception
	{
		ArrayList<UserMsg> easy = stu_list.getStudents(Level.Easy);
		ArrayList<UserMsg> med = stu_list.getStudents(Level.Medium);
		ArrayList<UserMsg> hard = stu_list.getStudents(Level.Hard);
		assertEquals(2, easy.size());
		assertEquals(1, med.size());
		assertEquals(1, hard.size());
	}
	/**
	 * Tests to see that students can be gotten by period
	 * @throws Exception
	 */
	@Test
	public void getAllStudentsByPeriod() throws Exception
	{
		ArrayList<UserMsg> first = stu_list.getStudents("first");
		ArrayList<UserMsg> second = stu_list.getStudents("second");
		ArrayList<UserMsg> third = stu_list.getStudents("third");
		assertEquals(2, first.size());
		assertEquals(1, second.size());
		assertEquals(1, third.size());
		
		
	}
}
