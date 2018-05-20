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
 * Test class for Admin list class
 *
 */
public class TestAdminList extends DatabaseTest 
{
	AdminList admin_list;
	/**
	 * Sets up the Admin list
	 * @throws SQLException
	 */
	@Before
	public void setUps() throws SQLException
	{
		admin_list = new AdminList();
	}
	/**
	 * Test to see that all Admins can be gotten and data retrieved correctly
	 * @throws Exception
	 */
	@Test
	public void testGetAllAdmins() throws Exception
	{
		ArrayList<UserMsg> all = admin_list.getAllAdmins();
		
		assertEquals(1, all.size());
		assertEquals(1, all.get(0).getUserId());
		assertEquals("FirstAdmin",all.get(0).getFirstName());
		assertEquals("lastAdmin",all.get(0).getLastName());
		assertEquals("admin",all.get(0).getUsername());
		assertEquals("admin",all.get(0).getPassword());
	}
	/**
	 * Test to see that if an Admin is not enabled that it takes that into an account
	 * @throws Exception
	 */
	@Test
	public void getAdminsByEnabled() throws Exception
	{
		ArrayList<UserMsg> all = admin_list.getAdmins(true);
		assertEquals(1, all.size());
		assertEquals(1, all.get(0).getUserId());
		assertEquals("FirstAdmin",all.get(0).getFirstName());
		assertEquals("lastAdmin",all.get(0).getLastName());
		assertEquals("admin",all.get(0).getUsername());
		assertEquals("admin",all.get(0).getPassword());
		assertEquals(true,all.get(0).isEnabled());
		
		// Check to see that it works both way
		ArrayList<UserMsg> not = admin_list.getAdmins(false);
		assertEquals(0, not.size());
		
	}
	
}

