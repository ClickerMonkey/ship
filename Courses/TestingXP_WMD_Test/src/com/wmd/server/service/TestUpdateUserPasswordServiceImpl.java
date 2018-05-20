package com.wmd.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Test;
import com.wmd.client.msg.UserMsg;
import com.wmd.server.db.Admin;
import com.wmd.server.db.DatabaseTest;
/**
 * Tests the UpdatePasswordServiceImpl class.
 * @author Christopher Eby and William Fisher
 *
 */
public class TestUpdateUserPasswordServiceImpl extends DatabaseTest
{
	/**
	 * Tests basic functionality.
	 * @throws SQLException 
	 */
	@Test
	public void TestUpdateUserPasswordService() throws SQLException
	{	
		Admin admin = new Admin(1);
		UpdatePasswordServiceImpl svc = new UpdatePasswordServiceImpl();
		assertTrue(svc.updateUserPassword("newPass", 1));
		UserMsg userMsg = admin.getMessage();
		assertEquals("newPass",userMsg.getPassword());
	}
}
