package com.wmd.server.db;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.wmd.client.entity.Answer;
import com.wmd.client.entity.ProblemStatement;
import com.wmd.client.entity.Question;
import com.wmd.client.msg.Level;
import com.wmd.client.msg.ProblemMsg;
import com.wmd.server.entity.EntityParser;

/**
 * @author Drew Quackenbos, Tristan Dalious, Chris Eby
 */
public class TestProblemHandler extends DatabaseTest
{
	private static final int ASSIGNMENT_ID = 1;
	ProblemHandler ph_test;
	//ProblemMsg pm_test;
	ProblemStatement ps_test;
	Question q_test;
	Answer a_test;
	
	/**
	 * Sets up a ProblemHandler and ProblemMsg to be used for testing
	 */
	@Before
	public void localSetUp()
	{
		this.ph_test = new ProblemHandler();
		//this.pm_test = new ProblemMsg(1,Level.Easy,5);
		
		this.q_test = new Question();
		this.a_test = new Answer();
		
		this.ps_test = new ProblemStatement(this.q_test,this.a_test);
		//this.ph_test.saveOrUpdate(this.pm_test);
		
	}

	
	/**
	 * Gets a problem from the database
	 * @throws SQLException
	 */
	@Test
	public void testGetProblemMsg() throws SQLException
	{
		ProblemMsg pm_test2 = this.ph_test.getProblem(ASSIGNMENT_ID,Level.valueOf("Easy"),5);
		
		
		assertEquals(1, pm_test2.getAssignmentId());
		assertEquals("Easy", pm_test2.getLevel().toString());
		assertEquals(5, pm_test2.getProblemNumber());
		assertEquals("Problem5", pm_test2.getName());
	}
	
	/**
	 * Tests saving a problem
	 * @throws SQLException
	 */
	@Test
	public void testSaveProblem() throws SQLException
	{
		ProblemMsg pm_test_return = this.ph_test.getProblem(1,Level.Easy,5);
		
		//Compare the insert
		assertEquals(1,pm_test_return.getAssignmentId());
		assertEquals("Easy",pm_test_return.getLevel().toString());
		assertEquals(5,pm_test_return.getProblemNumber());
	}
	
	/**
	 * Tests updating a problem
	 * @throws SQLException
	 */
	@Test
	public void testUpdateProblem() throws SQLException
	{
		ProblemMsg pm_test2 = this.ph_test.getProblem(1, Level.Easy, 5);
		
		pm_test2.setName("Problem lalala");
		this.ph_test.saveOrUpdate(pm_test2);
		
		ProblemMsg pm_test3 = this.ph_test.getProblem(1,Level.Easy,5);
		
		//Compare the update
		assertEquals("Problem lalala",pm_test3.getName());
	}
	
	/**
	 * Tests the retrieval of a problem statement from the db
	 * @throws SQLException
	 */
	@Test
	public void testGetProblemStatement() throws SQLException
	{
		this.ph_test.saveOrUpdate(this.ph_test.getProblem(1, Level.Easy, 5), this.ps_test);
		
		ProblemStatement ps_ret = this.ph_test.getProblemStatement(1,Level.Easy,5);
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><problem><question/><answer/></problem>";
		
		assertEquals(xml,EntityParser.toString(ps_ret));
	}
	
	/**
	 * Tests the deletion of a problem
	 * @throws SQLException
	 */
	@Test
	public void testDeleteProblem() throws SQLException
	{
		assertEquals(1,this.ph_test.deleteProblem(1,Level.Easy,5));
	}
	
	/**
	 * Tests the retrieval of a list of all problems
	 * @throws SQLException
	 */
	@Test
	public void testGetAllProblems() throws SQLException
	{
		assertEquals(15,this.ph_test.getAllProblems(1).size());
	}
	
	/**
	 * Tests the copying of a problem from one primary key value to another
	 * @throws SQLException
	 */
	@Test
	public void testCopyProblem() throws SQLException
	{
		ProblemHandler handler = new ProblemHandler();
		
		ProblemMsg base = handler.getProblem(1, Level.Easy, 5);
		assertNotNull(base);
		
		//Copy problem 1,Easy,5 to 1,Easy,[next problem_id](6)
		int test_prob_id = handler.copyProblem(base, 1);

		// Show its been copied (!= -1)
		assertEquals(6, test_prob_id);
	}

	/**
	 * test that we can swap the problem numbers of two problems
	 * @throws SQLException
	 */
	@Test
	public void swapNumbers() throws SQLException
	{
		ProblemHandler handler = new ProblemHandler();
		ProblemMsg msg3 = handler.getProblem(ASSIGNMENT_ID, Level.Easy, 3);
		ProblemMsg msg4 = handler.getProblem(ASSIGNMENT_ID, Level.Easy, 4);
		handler.swapNumbers(ASSIGNMENT_ID, Level.Easy, 3, 4);
		
		ProblemMsg afterMsg3 = handler.getProblem(ASSIGNMENT_ID, Level.Easy, 3);
		ProblemMsg afterMsg4 = handler.getProblem(ASSIGNMENT_ID, Level.Easy, 4);
		
		assertEquals(msg4.getName(), afterMsg3.getName());
		assertEquals(msg3.getName(), afterMsg4.getName());
		
	}
}
