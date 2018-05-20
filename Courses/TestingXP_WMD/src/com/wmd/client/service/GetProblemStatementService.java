package com.wmd.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wmd.client.entity.ProblemStatement;

/**
 * Gets a single problem statement from the database given its id.
 * 
 * This is invoked by the client when a user wants to attempt a problem. This
 * will return null if the problem doesn't exit.
 */
@RemoteServiceRelativePath("get_problem_statement")
public interface GetProblemStatementService extends RemoteService
{

	/**
	 * Given the id of a problem return the associated ProblemStatement
	 * instance.
	 * 
	 * @param problemId the ID of the problem
	 * @return The problemStament from the database.
	 */
	public ProblemStatement getProblemStatement(int problemId);

}