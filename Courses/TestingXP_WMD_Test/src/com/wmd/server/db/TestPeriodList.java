package com.wmd.server.db;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.wmd.client.msg.PeriodMsg;

/**
 * Tests the PeriodList class.
 * 
 * @author Philip Diffenderfer, AJ Marx
 *
 */
public class TestPeriodList extends DatabaseTest 
{

	/**
	 * Tests the getPeriods method in PeriodList.
	 * 
	 * @throws SQLException Error occurred retreiving periods.
	 */
	@Test
	public void testGetPeriods() throws SQLException
	{
		PeriodList handler = new PeriodList();
		
		List<PeriodMsg> periods = handler.getPeriods();

		assertEquals(3, periods.size());
		assertTrue(periods.contains(new PeriodMsg("first")));
		assertTrue(periods.contains(new PeriodMsg("second")));
		assertTrue(periods.contains(new PeriodMsg("third")));
	}
	
}
