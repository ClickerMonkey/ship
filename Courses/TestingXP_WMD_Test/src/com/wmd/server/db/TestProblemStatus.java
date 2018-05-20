package com.wmd.server.db;

import static org.junit.Assert.*;
import java.sql.SQLException;
import org.junit.Test;
import com.wmd.client.msg.ProblemStatusMsg;
import com.wmd.client.service.ServiceException;
/**
 * Tests the ProblemStatus
 * @author Christopher Eby and William Fisher
 *
 */
public class TestProblemStatus
{
	private static final int tries = 5;
	/**
	 * Tests to make sure the inputes problems status is not there.
	 * @throws SQLException
	 * @throws ServiceException
	 */
	@Test(expected = ServiceException.class)
	public void notThere() throws SQLException, ServiceException
	{
		new ProblemStatus(10020301,102141440);
	}

	/**
	 * Tests getting a message from the DB.
	 * @throws SQLException
	 * @throws ServiceException
	 */
	@Test
	public void buildMsg() throws SQLException, ServiceException
	{
		ProblemStatus p = new ProblemStatus(2,7);
		ProblemStatusMsg msg = p.getMessage();
		assertEquals(tries, msg.getTries());
		assertFalse(msg.isCorrect());
	}
}
