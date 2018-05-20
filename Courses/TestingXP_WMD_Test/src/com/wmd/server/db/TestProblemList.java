package com.wmd.server.db;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.wmd.client.msg.Level;
import com.wmd.client.msg.ProblemMsg;

/**
 * Tests the ProblemList Class
 * @author Tristan D., Steve U. 
 *
 */
public class TestProblemList extends DatabaseTest
{
	ProblemList prob_list;
	
	@Before
	public void setUps() throws SQLException
	{
		prob_list = new ProblemList();
	}
	
	/**
	 * Tests getting all problems in the database
	 * @throws Exception 
	 */
	@Test
	public void testGetAllProblems() throws Exception
	{
		ArrayList<ProblemMsg> all = prob_list.getAllProblems();
		
		assertEquals(18, all.size());
		assertEquals(1, all.get(0).getProblemId());
		assertEquals(18, all.get(17).getProblemId());
	}
	
	/**
	 * Tests getting all problems by an assignment Id
	 * @throws Exception 
	 */
	@Test
	public void testGetProblemsByAssignment() throws Exception
	{
		ArrayList<ProblemMsg> allByAssign = prob_list.getProblemsByAssignment(1);
		
		assertEquals(6, allByAssign.size());
		assertEquals(1, allByAssign.get(0).getProblemId());
		assertEquals(12, allByAssign.get(5).getProblemId());
	}
	
	/**
	 * Tests getting all assignments by a Level
	 * @throws Exception 
	 */
	@Test
	public void testGetProblemsByLevel() throws Exception
	{
		ArrayList<ProblemMsg> allByLevel = prob_list.getProblemsByLevel(Level.Easy);
		
		assertEquals(6, allByLevel.size());
		assertEquals(1, allByLevel.get(0).getProblemId());
		assertEquals(16, allByLevel.get(5).getProblemId());
	}
	
	/**
	 * Tests getting all assignments by an Assignment Id, and a Level
	 * @throws Exception 
	 */
	@Test
	public void testGetProblemsByAssignmentLevel() throws Exception
	{
		ArrayList<ProblemMsg> allByLevel = prob_list.getProblemsByAssignmentLevel(2, Level.Easy);
		
		assertEquals(2, allByLevel.size());
		assertEquals(4, allByLevel.get(0).getProblemId());
		assertEquals(13, allByLevel.get(1).getProblemId());
	}
}
