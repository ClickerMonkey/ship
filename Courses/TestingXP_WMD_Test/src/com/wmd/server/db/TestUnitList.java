package com.wmd.server.db;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

/**
 * Test the UnitList class.
 * 
 * @author Philip Diffenderfer, Kevin Rexroth
 *
 */
public class TestUnitList extends DatabaseTest 
{

	/**
	 * Tests the getUnits() method for returning correct data.
	 */
	@Test
	public void testGetUnits()
	{
		UnitList unitList = new UnitList();
		
		// Get all of the units from the databse.
		List<String> units = unitList.getUnits();
		
		// Show that all units have been returned
		assertEquals(5, units.size());
		
		// Show that all units were returned and in alphabetical order
		assertEquals("centimeters", units.get(0));
		assertEquals("decimeters", units.get(1));
		assertEquals("kilometers", units.get(2));
		assertEquals("meters", units.get(3));
		assertEquals("millimeters", units.get(4));
	}
	
}
