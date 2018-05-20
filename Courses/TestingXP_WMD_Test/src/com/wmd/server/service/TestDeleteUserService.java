package com.wmd.server.service;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.wmd.client.msg.Role;
import com.wmd.client.msg.UserMsg;
import com.wmd.server.db.DatabaseTest;


/**
 * A test for the DeleteUserService. In this class, there are separate tests for
 * deleting a student, instructor, and admin, each of which are essentially the
 * same. There is no difference deleting a user, regardless of their role.
 * 
 * This class largely depends on the getAllUsersService.
 * 
 * @author Andrew Marx, Chris Koch
 * @see DeleteUserServiceImpl
 */
public class TestDeleteUserService extends DatabaseTest
{
	GetAllUsersServiceImpl getUsersService = new GetAllUsersServiceImpl();
	DeleteUserServiceImpl deleteUserService = new DeleteUserServiceImpl();
	int adminCount = 1, instructorCount = 1, studentCount = 4;


	/**
	 * A Test to go before every test to make sure that the right number of
	 * users are there.
	 */
	@Before
	public void checkEverythingsThere()
	{
		ArrayList<UserMsg> userList;

		userList = getUsersService.getAllUsers(Role.Admin);
		assertEquals(adminCount, userList.size());

		userList = getUsersService.getAllUsers(Role.Instructor);
		assertEquals(instructorCount, userList.size());

		userList = getUsersService.getAllUsers(Role.Student);
		assertEquals(studentCount, userList.size());
	}


	/**
	 * Test deleting a student.
	 */
	@Test
	public void testDeleteStudent()
	{
		// delete the user with user id 2, which is a student
		boolean result = deleteUserService.deleteUser(2);
		assertTrue(result);

		ArrayList<UserMsg> userList;
		userList = getUsersService.getAllUsers(Role.Admin);
		assertEquals(adminCount, userList.size());
		userList = getUsersService.getAllUsers(Role.Instructor);
		assertEquals(instructorCount, userList.size());
		userList = getUsersService.getAllUsers(Role.Student);
		assertEquals(studentCount - 1, userList.size());
	}


	/**
	 * Test deleting an instructor.
	 */
	@Test
	public void testDeleteInstructor()
	{
		// delete the user with user id 6, which is an instructor
		boolean result = deleteUserService.deleteUser(6);
		assertTrue(result);

		ArrayList<UserMsg> userList;
		userList = getUsersService.getAllUsers(Role.Admin);
		assertEquals(adminCount, userList.size());
		userList = getUsersService.getAllUsers(Role.Instructor);
		assertEquals(instructorCount - 1, userList.size());
		userList = getUsersService.getAllUsers(Role.Student);
		assertEquals(studentCount, userList.size());
	}


	/**
	 * Test deleting an admin.
	 */
	@Test
	public void testDeleteAdmin()
	{
		// delete the user with user id 1, which is an admin
		boolean result = deleteUserService.deleteUser(1);
		assertTrue(result);

		ArrayList<UserMsg> userList;
		userList = getUsersService.getAllUsers(Role.Admin);
		assertEquals(adminCount - 1, userList.size());
		userList = getUsersService.getAllUsers(Role.Instructor);
		assertEquals(instructorCount, userList.size());
		userList = getUsersService.getAllUsers(Role.Student);
		assertEquals(studentCount, userList.size());
	}


	/**
	 * Test deleting a user with an id that does not exist.
	 */
	@Test
	public void testDeleteNonExistentUser()
	{
		// delete the user with user id 2, which is a student
		boolean result = deleteUserService.deleteUser(-1);
		assertFalse(result);

		ArrayList<UserMsg> userList;
		userList = getUsersService.getAllUsers(Role.Admin);
		assertEquals(adminCount, userList.size());
		userList = getUsersService.getAllUsers(Role.Instructor);
		assertEquals(instructorCount, userList.size());
		userList = getUsersService.getAllUsers(Role.Student);
		assertEquals(studentCount, userList.size());
	}
}
