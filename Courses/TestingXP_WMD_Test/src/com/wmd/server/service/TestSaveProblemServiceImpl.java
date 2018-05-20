package com.wmd.server.service;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.wmd.client.entity.Answer;
import com.wmd.client.entity.ProblemStatement;
import com.wmd.client.entity.Question;
import com.wmd.client.msg.Level;
import com.wmd.client.msg.ProblemMsg;
//import com.wmd.deprecated.GetProblemServiceImpl;
import com.wmd.server.db.DatabaseTest;
import com.wmd.server.service.SaveProblemServiceImpl;

/**
 * 
 * @author AJ Marx, Scotty Rhinehart
 * 
 * Refactored by: Sam Storino and Kevin Rexroth
 * 
 */
public class TestSaveProblemServiceImpl extends DatabaseTest
{

	private static final Level Hard = null;

	/**
	 * 
	 * Tests adding a new ProblemStatement to the database.
	 * @throws SQLException 
	 */
	@Test
	public void testAddNewProblemStatement() throws SQLException 
	{
		SaveProblemServiceImpl impl = new SaveProblemServiceImpl();


		// create a new problem
		Question q1 = new Question();
		Answer a1 = new Answer();
		
		
		ProblemStatement ps_test_u2 = new ProblemStatement(q1, a1);

		ProblemMsg msg = new ProblemMsg();

		msg.setProblemId(1);
		msg.setLevel(Hard);
		msg.setProblemOrder(1);
		msg.setAssignmentId(1);
		msg.setName("testSaveProblem");

		int problemId = msg.getProblemId();
		int assignmentId = msg.getAssignmentId();
		Level level = msg.getLevel();
		String name = msg.getName();
		int problemOrder = msg.getProblemOrder();
	
		// save the problem, if null isn't returned then it must be saved!
		assertNotNull(impl.saveProblem(problemId,assignmentId, level, name, problemOrder, ps_test_u2));


	}
}
