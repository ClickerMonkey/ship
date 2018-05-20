package com.wmd.server.db;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.wmd.client.msg.ProblemStatusMsg;

import java.sql.SQLException;
import java.util.*;

/**
 * @class Test Problem Status Handler
 * @author Will F., Drew Q.
 */
public class TestProblemStatusHandler extends DatabaseTest
{
	ProblemStatusHandler psh;
	private static final int USER_ID = 3;
	private static final int ASSIGNMENT_ID = 1;
	private static final int PROBLEM_NUMBER = 2;
	private static final int NEW_PROBLEM_NUMBER = 6;
	@Before
	public void localSetup()
	{
		psh = new ProblemStatusHandler();
	}

	/**
	 * Tests the ability to get a list of problem statuses
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testGetStatusList() throws SQLException
	{
		ArrayList<ProblemStatusMsg> psmList;
//		psh.saveOrUpdateStatus(USER_ID, ASSIGNMENT_ID, PROBLEM_NUMBER, true);
		psmList = psh.getProblemStatusList(USER_ID, ASSIGNMENT_ID);
		assertEquals(5, psmList.size());
	}

	/**
	 * Tests the ability to update a given problem status
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testUpdateStatus() throws SQLException
	{
		int beforeTries = psh.getProblemStatus(USER_ID, ASSIGNMENT_ID, PROBLEM_NUMBER).getTries();
		psh.saveOrUpdateStatus(USER_ID, ASSIGNMENT_ID, PROBLEM_NUMBER, false);
		ProblemStatusMsg problemStatus = psh.getProblemStatus(USER_ID, ASSIGNMENT_ID, PROBLEM_NUMBER);
		assertEquals(false, problemStatus.isCorrect());
		assertEquals(beforeTries+1, problemStatus.getTries());
	}


	/**
	 * Tests the add or update function given an object
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testAddStatusObject() throws SQLException
	{
		ProblemStatusMsg psm = new ProblemStatusMsg(USER_ID, ASSIGNMENT_ID, NEW_PROBLEM_NUMBER, 0, false);
		psh.createNewProblemStatus(psm);
		assertFalse( psh.getProblemStatus(USER_ID, ASSIGNMENT_ID, NEW_PROBLEM_NUMBER).isCorrect());
		//TODO delete when rollback works
		psh.removeStatus(USER_ID, ASSIGNMENT_ID, NEW_PROBLEM_NUMBER);
	
	}
}
