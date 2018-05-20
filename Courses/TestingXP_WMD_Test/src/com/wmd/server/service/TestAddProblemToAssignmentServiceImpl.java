package com.wmd.server.service;

import org.junit.Test;

//import com.wmd.deprecated.ProblemAssignmentMsg;
import com.wmd.server.db.DatabaseTest;
import com.wmd.server.service.AddProblemToAssignmentServiceImpl;

/**
 * Tests the {@link AddProblemToAssignmentServiceImpl} class.
 * 
 * @author Philip Diffenderfer, Olga Zalamea
 *
 */
public class TestAddProblemToAssignmentServiceImpl extends DatabaseTest
{

	// id of a static assignment that can be used to assign/unassign problems to
	private static final int ASSIGNMENT = 1;
	// the ids of the static problems to assign/unassign
	private static final int PROB_1 = 1;
	private static final int PROB_2 = 2;
	private static final int PROB_3 = 3;
	
	/**
	 * Tests the service for correctness by assigning three problems (1,2,3) to
	 * the default assignment (1). Once everything works this will delete those
	 * assignments.
	 */
	@Test
	public void testImpl()
	{
		AddProblemToAssignmentServiceImpl impl = new AddProblemToAssignmentServiceImpl();

		// Create 3 relationships for the first assignment, and the first 3
		//problems (their problem_number is coincidentally their id, not always
		// related).
		// TODO Make it work for iteration 2 (tristan)
//		ProblemAssignmentMsg assignment1 = impl.addProblem(ASSIGNMENT, PROB_1, 1);
//		ProblemAssignmentMsg assignment2 = impl.addProblem(ASSIGNMENT, PROB_2, 2);
//		ProblemAssignmentMsg assignment3 = impl.addProblem(ASSIGNMENT, PROB_3, 3);
		
//		assertNotNull(assignment1);
//		assertEquals(ASSIGNMENT, assignment1.getAssignmentId());
//		assertEquals(PROB_1, assignment1.getProblemId());
//		assertEquals(1, assignment1.getProblemNumber());
//
//		assertNotNull(assignment2);
//		assertEquals(ASSIGNMENT, assignment2.getAssignmentId());
//		assertEquals(PROB_2, assignment2.getProblemId());
//		assertEquals(2, assignment2.getProblemNumber());
//
//		assertNotNull(assignment3);
//		assertEquals(ASSIGNMENT, assignment3.getAssignmentId());
//		assertEquals(PROB_3, assignment3.getProblemId());
//		assertEquals(3, assignment3.getProblemNumber());
		
		// Remove created relationship
//		assertTrue( handler.unassignProblem(ASSIGNMENT, PROB_1) );
//		assertTrue( handler.unassignProblem(ASSIGNMENT, PROB_2) );
//		assertTrue( handler.unassignProblem(ASSIGNMENT, PROB_3) );
	}
	
}
