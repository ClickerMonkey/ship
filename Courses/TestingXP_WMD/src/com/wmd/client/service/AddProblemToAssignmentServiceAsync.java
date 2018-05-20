package com.wmd.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Adds a problem to a given assignment.
 * 
 * This will add a relationship between a pre-existing assignment and problem in
 * the database. This will return the relationship added to the database, or
 * null if the problem could not be added to the assignment (assignment didn't
 * exist, problem didn't exist, relationship already exists).
 */
public interface AddProblemToAssignmentServiceAsync
{

	/**
	 * Adds the problem to the given assignment.
	 * 
	 * @param problem
	 *            - The ProblemMsg being added
	 * @param callback
	 *            The callback for the synchronous method.
	 */
	void addProblem(int problemId, int assignmentId, AsyncCallback<Boolean> callback);

}
