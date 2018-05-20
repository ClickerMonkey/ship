package com.wmd.server.service;

import static org.junit.Assert.*;

import org.junit.Test;

import com.wmd.client.msg.Level;
import com.wmd.client.msg.Role;
import com.wmd.client.msg.UserMsg;
import com.wmd.server.db.Student;

public class TestSaveUpdateUserService {

	/**
	 * @author Stephen Jurnack and Scotty Rhinehart
	 * 
	 *         Tests SaveUpdateUserService
	 * @throws Exception
	 * 
	 */

	@Test
	public void testUpdateUser() throws Exception
	{
		SaveUpdateUserServiceImpl svc = new SaveUpdateUserServiceImpl();
		
		String username = "testEasy";
		String password = "simple";
		int userId = 2;
		Role role = Role.Student;
		Level level = Level.Easy;
		boolean enabled = true;
		String firstName = "FirstHard";
		String lastName = "LastEasy";
		String period = "first";
		
		Student test = new Student(2);
		
		assertTrue(svc.saveUpdateUser(userId, firstName, lastName, role, username,
					password, level, period, enabled));
		assertEquals(firstName, test.getFirstname());
	}
	
	@Test
	public void testSaveUser() throws Exception
	{
		SaveUpdateUserServiceImpl svc = new SaveUpdateUserServiceImpl();
		
		String username = "dude";
		String password = "sdude";
		int userId = 7;
		Role role = Role.Student;
		Level level = Level.Hard;
		boolean enabled = false;
		String firstName = "Jack.java";
		String lastName = "whatHappened";
		String period = "seventh";
		
		Student test = new Student(userId);
		
		assertTrue(svc.saveUpdateUser(userId, firstName, lastName, role, username,
					password, level, period, enabled));
		assertEquals(firstName, test.getFirstname());
	}
}
