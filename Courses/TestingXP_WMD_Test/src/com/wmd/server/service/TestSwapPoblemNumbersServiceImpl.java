package com.wmd.server.service;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Test;

import com.wmd.client.msg.Level;
import com.wmd.client.msg.ProblemMsg;
import com.wmd.client.service.ServiceException;
import com.wmd.server.db.DatabaseTest;
import com.wmd.server.db.ProblemHandler;

/**
 * @author Merlin
 * 
 *         Created: Apr 5, 2010
 */
public class TestSwapPoblemNumbersServiceImpl extends DatabaseTest
{
	/**
	 * Make sure it works when the request is valed
	 * @throws ServiceException shouldn't
	 * @throws SQLException shouldn't
	 */
	@Test
	public void testGood() throws ServiceException, SQLException
	{
		SwapProblemNumbersServiceImpl svc = new SwapProblemNumbersServiceImpl();
		ProblemHandler handler = new ProblemHandler();

		ProblemMsg msg1 = handler.getProblem(1, Level.Easy, 3);
		ProblemMsg msg2 = handler.getProblem(1, Level.Easy, 4);
		svc.swap(msg1, msg2);
		ProblemMsg newmsg1 = handler.getProblem(1, Level.Easy, 3);
		ProblemMsg newmsg2 = handler.getProblem(1, Level.Easy, 4);
		assertEquals(msg1.getName(), newmsg2.getName());
		assertEquals(msg2.getName(), newmsg1.getName());

	}

	/**
	 * the assignment numbers of the two problem messages must match
	 * @throws ServiceException expected
	 * @throws SQLException shouldn't
	 */
	@Test(expected = ServiceException.class)
	public void testAssigmentsDontMatch() throws ServiceException, SQLException
	{
		SwapProblemNumbersServiceImpl svc = new SwapProblemNumbersServiceImpl();
		ProblemHandler handler = new ProblemHandler();

		ProblemMsg msg1 = handler.getProblem(1, Level.Easy, 3);
		ProblemMsg msg2 = handler.getProblem(2, Level.Easy, 4);
		svc.swap(msg1, msg2);
	}
	
	/**
	 * the levels of the two problem messages must match
	 * @throws ServiceException expected
	 * @throws SQLException shouldn't
	 */
	@Test(expected = ServiceException.class)
	public void testLevelsDontMatch() throws ServiceException, SQLException
	{
		SwapProblemNumbersServiceImpl svc = new SwapProblemNumbersServiceImpl();
		ProblemHandler handler = new ProblemHandler();

		ProblemMsg msg1 = handler.getProblem(1, Level.Easy, 3);
		ProblemMsg msg2 = handler.getProblem(1, Level.Medium, 4);
		svc.swap(msg1, msg2);
	}
}
