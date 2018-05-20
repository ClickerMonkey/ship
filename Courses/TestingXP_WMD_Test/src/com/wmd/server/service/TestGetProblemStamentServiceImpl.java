package com.wmd.server.service;

import static org.junit.Assert.*;

import org.junit.Test;

import com.wmd.client.entity.ProblemStatement;
import com.wmd.client.msg.Level;
import com.wmd.server.db.Database;
import com.wmd.server.service.GetProblemStatementServiceImpl;

/**
 * Tests the GetProblemServiceImpl. 
 * 
 * @author Philip Diffenderfer, Olga Zalamea
 */
public class TestGetProblemStamentServiceImpl 
{
	// id of a static problem with a non-null statement
	private static final int USE_PROBLEM = 1;
	private static final Level USE_LEVEL = Level.Easy;
	private static final int USE_NUMBER = 1;
	
	/**
	 * Given a problem (5) this will return the statement completely parsed from
	 * the Xml.
	 */
	@Test
	public void testImpl()
	{
		Database.setTesting(true);
		GetProblemStatementServiceImpl impl = new GetProblemStatementServiceImpl();

		// This problem is always expected to be in the database
		ProblemStatement probStat1 = impl.getProblemStatement(USE_PROBLEM, USE_LEVEL, USE_NUMBER);
		
		// Ensure things exist, and were parsed correctly.
		assertNotNull(probStat1);
		assertNotNull(probStat1.getAnswer());
		assertNotNull(probStat1.getQuestion());
		assertTrue(probStat1.getAnswer().getEntities().size() >= 1);
		assertTrue(probStat1.getQuestion().getEntities().size() >= 1);
	}
	
}
