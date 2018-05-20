package com.wmd.server.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.Test;

import com.wmd.client.entity.ProblemStatement;
import com.wmd.client.msg.Level;
import com.wmd.client.msg.ProblemMsg;
import com.wmd.client.service.ServiceException;
import com.wmd.server.entity.EntityParser;

public class TestProblem
{
	private static final int assignmentID = 1;
	private static final Level level = Level.Easy;
	private static final int problemOrder = 1;
	private static final String name = "Problem000";

	@Test(expected = ServiceException.class)
	public void notThere() throws SQLException, ServiceException
	{
		new Problem(20);
	}

	@Test
	public void buildMsg() throws SQLException, ServiceException
	{
		Problem p = new Problem(1);
		ProblemMsg msg = p.getMessage();
		assertEquals(assignmentID, msg.getAssignmentId());
		assertEquals(name, msg.getName());
		assertEquals(level, msg.getLevel());
		assertEquals(problemOrder, msg.getProblemOrder());
	}
	
	/**
	 * Tests the retrieval of a problem statement from the db
	 * @throws SQLException
	 * @throws ServiceException 
	 */
	@Test
	public void testGetProblemStatement() throws SQLException, ServiceException
	{
		
		Problem p = new Problem(1);
		
		ProblemStatement ps_ret = p.getProblemStatement();
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><problem><question><text val=\"What is 3+5?\"/></question><answer><integer int=\"8\"/></answer></problem>";
		
		assertEquals(xml,EntityParser.toString(ps_ret));
	}

}
