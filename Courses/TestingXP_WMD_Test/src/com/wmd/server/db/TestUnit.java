package com.wmd.server.db;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * A test for the Unit class.
 * 
 * @author Philip Diffenderfer, Kevin Rexroth
 * 
 */
public class TestUnit extends DatabaseTest
{
	/**
	 * if we try to retrieve a unit that is in the db, contains should return true
	 */
	@Test
	public void canRetrieveExisting()
	{
		Unit units = new Unit();

		assertTrue(units.contains("meters"));
	}

	/**
	 * if we try to retrieve a unit that isn't in the db, contains should return false
	 */
	@Test
	public void cantRetrieveNonExisting()
	{
		Unit units = new Unit();

		assertFalse(units.contains("feet"));
	}

	/**
	 * if we add a unit using the save method, we should be able to see that it
	 * is there with the contains method
	 */
	@Test
	public void canAddNonExisting()
	{
		Unit units = new Unit();
		assertTrue(units.save("feet"));
		assertTrue(units.contains("feet"));
	}

	/**
	 * If we try to add an existing unit, save should return false, but it
	 * should still be in the db
	 */
	@Test
	public void cantAddExisting()
	{
		Unit units = new Unit();
		assertFalse(units.save("meters"));
		assertTrue(units.contains("meters"));
	}
}
