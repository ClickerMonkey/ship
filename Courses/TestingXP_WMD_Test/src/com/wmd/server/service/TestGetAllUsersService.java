package com.wmd.server.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.junit.Test;

import com.wmd.client.msg.Role;
import com.wmd.client.msg.UserMsg;
import com.wmd.server.db.DatabaseTest;


/**
 * A class to test the GetAllUsersService.
 * 
 * 
 * @author Andrew Marx, Chris Koch
 * @since 2010 April 21
 * @see GetAllUsersService
 * @see GetAllUsersServiceAsync
 * @see GetAllUsersServiceImpl
 * 
 */
public class TestGetAllUsersService extends DatabaseTest
{
	/**
	 * Test that we get all the students, using the getAllUsers(Role) method.
	 * 
	 * @see GetAllUsersServiceImpl#getAllUsers(Role)
	 */
	@Test
	public void testGetAllStudents()
	{
		GetAllUsersServiceImpl service = new GetAllUsersServiceImpl();
		ArrayList<UserMsg> users;
		Role role = Role.Student;

		users = service.getAllUsers(role);

		assertNotNull(users);
		assertEquals(4, users.size());
		Collections.sort(users, new UserComparator());

		int i = 0;
		assertEquals(2, users.get(i).getUserId());
		assertEquals("FirstEasy", users.get(i).getFirstName());
		assertEquals("LastEasy", users.get(i).getLastName());
		assertEquals(Role.Student, users.get(i).getRole());

		i++;
		assertEquals(3, users.get(i).getUserId());
		assertEquals("FirstEasy2", users.get(i).getFirstName());
		assertEquals("LastEasy2", users.get(i).getLastName());
		assertEquals(Role.Student, users.get(i).getRole());

		i++;
		assertEquals(4, users.get(i).getUserId());
		assertEquals("FirstMed", users.get(i).getFirstName());
		assertEquals("LastMed", users.get(i).getLastName());
		assertEquals(Role.Student, users.get(i).getRole());

		i++;
		assertEquals(5, users.get(i).getUserId());
		assertEquals("FirstHard", users.get(i).getFirstName());
		assertEquals("LastHard", users.get(i).getLastName());
		assertEquals(Role.Student, users.get(i).getRole());
	}


	/**
	 * Test that we get all the instructors, using the getAllUsers(Role) method.
	 * 
	 * @see GetAllUsersServiceImpl#getAllUsers(Role)
	 */
	@Test
	public void testGetAllInstructors()
	{
		GetAllUsersServiceImpl service = new GetAllUsersServiceImpl();
		ArrayList<UserMsg> users;
		Role role = Role.Instructor;

		users = service.getAllUsers(role);

		assertNotNull(users);
		assertEquals(1, users.size());
		Collections.sort(users, new UserComparator());

		int i = 0;
		assertEquals(6, users.get(i).getUserId());
		assertEquals("FirstInstr", users.get(i).getFirstName());
		assertEquals("lastInstr", users.get(i).getLastName());
		assertEquals(Role.Instructor, users.get(i).getRole());
	}



	/**
	 * Test that we get all the admins, using the getAllUsers(Role) method.
	 * 
	 * @see GetAllUsersServiceImpl#getAllUsers(Role)
	 */
	@Test
	public void testGetAllAdmins()
	{
		GetAllUsersServiceImpl service = new GetAllUsersServiceImpl();
		ArrayList<UserMsg> users;
		Role role = Role.Admin;

		users = service.getAllUsers(role);

		assertNotNull(users);
		assertEquals(1, users.size());
		Collections.sort(users, new UserComparator());

		int i = 0;
		assertEquals(1, users.get(i).getUserId());
		assertEquals("FirstAdmin", users.get(i).getFirstName());
		assertEquals("lastAdmin", users.get(i).getLastName());
		assertEquals(Role.Admin, users.get(i).getRole());
	}



	/**
	 * Test that the getAllStudentsByPeriod method works.
	 * 
	 * @see GetAllUsersServiceImpl#getAllStudentsByPeriod(String)
	 */
	@Test
	public void testGetStudentsByPeriod()
	{
		GetAllUsersServiceImpl service = new GetAllUsersServiceImpl();
		ArrayList<UserMsg> users;
		String period;
		int i;

		// get all students from the first period
		period = "first";
		users = service.getAllStudentsByPeriod(period);

		assertNotNull(users);
		assertEquals(2, users.size());
		Collections.sort(users, new UserComparator());

		i = 0;
		assertEquals(2, users.get(i).getUserId());
		assertEquals("FirstEasy", users.get(i).getFirstName());
		assertEquals("LastEasy", users.get(i).getLastName());
		assertEquals(Role.Student, users.get(i).getRole());
		assertEquals(period, users.get(i).getPeriod());

		i++;
		assertEquals(3, users.get(i).getUserId());
		assertEquals("FirstEasy2", users.get(i).getFirstName());
		assertEquals("LastEasy2", users.get(i).getLastName());
		assertEquals(Role.Student, users.get(i).getRole());
		assertEquals(period, users.get(i).getPeriod());



		// get all students from the second period
		period = "second";
		users = service.getAllStudentsByPeriod(period);

		assertNotNull(users);
		assertEquals(1, users.size());

		i = 0;
		assertEquals(4, users.get(i).getUserId());
		assertEquals("FirstMed", users.get(i).getFirstName());
		assertEquals("LastMed", users.get(i).getLastName());
		assertEquals(Role.Student, users.get(i).getRole());
		assertEquals(period, users.get(i).getPeriod());



		// get all students from the third period
		period = "third";
		users = service.getAllStudentsByPeriod(period);

		assertNotNull(users);
		assertEquals(1, users.size());

		i = 0;
		assertEquals(5, users.get(i).getUserId());
		assertEquals("FirstHard", users.get(i).getFirstName());
		assertEquals("LastHard", users.get(i).getLastName());
		assertEquals(Role.Student, users.get(i).getRole());
		assertEquals(period, users.get(i).getPeriod());
	}



	/**
	 * A comparator used to sort UserMsg objects, according to the user id.
	 * 
	 * @author Andrew Marx, Chris Koch
	 * 
	 */
	public static class UserComparator implements Comparator<UserMsg>
	{
		@Override
		public int compare(UserMsg u1, UserMsg u2)
		{
			return (u1.getUserId() - u2.getUserId());
		}
	}

}
