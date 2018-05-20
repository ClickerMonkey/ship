package com.wmd.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.wmd.client.msg.ProblemMsg;
import com.wmd.server.db.DatabaseTest;

/**
 * Tests the GetAssignmentProblemService
 * @author Drew Q., Paul C.
 *
 */
public class TestGetAssignmentProblemService extends DatabaseTest
{
	/**
	 * Tests the GetAssignmentProblemService
	 */
	@Test
	public void testGetAssignmentProblemService()
	{
		GetAssignmentProblemsServiceImpl service = new GetAssignmentProblemsServiceImpl();
		
		assertNotNull(service);
		List<ProblemMsg> problems = service.getAssignmentProblems(1);

		assertNotNull(problems);
		assertEquals(15, problems.size());
		
		assertEquals("Problem1", problems.get(0).getName());
		assertEquals(1, problems.get(0).getProblemNumber());
	}
}
