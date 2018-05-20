package com.wmd.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Adds a problem to a given assignment.
 * 
 * This will add a relationship between a pre-existing assignment and problem in
 * the database. This will return the relationship added to the database, or
 * null if the problem could not be added to the assignment (assignment didn't
 * exist, problem didn't exist, relationship already exists).
 */
@RemoteServiceRelativePath("add_problem_to_assignment")
public interface AddProblemToAssignmentService extends RemoteService
{

	/**
	 * Adds the problem to the given assignment.
	 * 
	 * @param problem
	 *            A problem message to add
	 * @return The problem assignment created by the call. If the service failed
	 *         then this will return null.
	 */
	boolean addProblem(int problemId, int assignmentId);

}