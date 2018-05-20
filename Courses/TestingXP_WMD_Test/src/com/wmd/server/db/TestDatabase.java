package com.wmd.server.db;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

/**
 * Verify the base functionality of the class that manages both databases (
 * the one for testing and the production one)
 * 
 * @author Merlin
 *
 * Created:  Apr 1, 2010
 */
public class TestDatabase
{

	/**
	 * Clear the db singleton
	 */
	@Before
	public void setUp()
	{
		Database.reset();
	}
	/**
	 * Test the connection to the application database. Make sure it can 
	 * retrieve the connection, its connected, and it can be successfully
	 * disconnected.
	 * @throws SQLException 
	 */
	@Test
	public void testApplicationConnection() throws SQLException
	{
		Database db = Database.get();
		
		Connection link = db.getLink();
		
		assertNotNull(link);
	}
	
	/**
	 * Test the connection to the testing database. Make sure it can retrieve
	 * the connection, its connected, and it can be successfully disconnected.
	 * @throws SQLException 
	 */
	@Test
	public void testTestingConnection() throws SQLException
	{
		Database.getSingleton().setTesting();
		Database db = Database.get();
		
		Connection link = db.getLink();
		
		assertNotNull(link);
	}

	/**
	 * Make sure we can connect to the testing database
	 * @throws SQLException
	 */
	@Test
	public void connectToTestVsReal() throws SQLException
	{
		Database dbm = Database.getSingleton();
		Connection real = dbm.getConnection();
		String realurl = real.getMetaData().getURL();
		String expected = "wmd";
		assertEquals(expected,realurl.substring(realurl.length()-expected.length()));
		dbm.setTesting();
		Connection testing = dbm.getConnection();
		String testingurl = testing.getMetaData().getURL();
		expected = "wmdTest";
		assertEquals(expected,testingurl.substring(testingurl.length()-expected.length()));
	}
	
	/**
	 * Make sure that the structure of the test and real databases are the same
	 * @throws SQLException
	 */
	@Test
	public void testAndRealHaveSameStructure() throws SQLException
	{
		Connection real = Database.get().getLink();
		Database.getSingleton().setTesting();
		Connection testing = Database.get().getLink();
		assertNotSame(real, testing);
		DatabaseMetaData realMetaData = real.getMetaData();
		ResultSet realColumns = realMetaData.getColumns(null, null, "%", null);
		DatabaseMetaData testingMetaData = testing.getMetaData();
		ResultSet testingColumns = testingMetaData.getColumns(null, null, "%", null);
		compare(realColumns, testingColumns);
		compare(testingColumns, realColumns);
	}

	private void compare(ResultSet columns1, ResultSet columns2)
			throws SQLException
	{
		columns1.first();
		while (!columns1.isAfterLast())
		{
			String tableName = columns1.getString(3);
			String columnName = columns1.getString(4);
			moveToRowMatching(columns2, tableName, columnName);
			assertEquals("Data type doesn't match: " + tableName + ":" + columnName, columns1.getInt(5), columns2.getInt(5));
			assertEquals("Nullable doesn't match: " + tableName + ":" + columnName, columns1.getInt(11), columns2.getInt(11));
			assertEquals("IsNullable doesn't match: " + tableName + ":" + columnName, columns1.getString(18), columns2.getString(18));
			assertEquals("IsNullable doesn't match: " + tableName + ":" + columnName, columns1.getString(18), columns2.getString(18));
			columns1.next();
		}
	}
		

	private void moveToRowMatching(ResultSet rs, String tableName,
			String columnName) throws SQLException
	{
		rs.first();
		while (!rs.isAfterLast())
		{
			if ((rs.getString(3).equals(tableName) && (rs.getString(4).equals(columnName))))
				return;
			rs.next();
		}
		fail("column not found " + tableName + ":" + columnName );
		
	}

	/**
	 * @throws Exception 
	 * 
	 */
	@Test
	public void testRollBack() throws Exception
	{
		Database dbm = Database.get();
		dbm.setTesting();
		AssignmentList h = new AssignmentList();
		int before = h.getAssignments().size();
		Connection con = dbm.getLink();
		Statement stmt = con.createStatement();
		String sql = "INSERT INTO assignment (name) VALUES ('StupidAssignment');";
		stmt.executeUpdate(sql);
		assertEquals(before+1, h.getAssignments().size());
		dbm.rollBack();
		assertEquals(before, h.getAssignments().size());
	}
}
