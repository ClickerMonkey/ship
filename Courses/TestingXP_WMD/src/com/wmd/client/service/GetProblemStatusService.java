package com.wmd.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wmd.client.msg.ProblemStatusMsg;

/**
 * Gets the status of all problems for a user taking an assignment.
 * 
 * This is invoked by the client when a user is trying to take an assignment.
 * This should be called on the page load so the user can see what problems are
 * contained in the assignment and what their status is on each.
 * 
 * @author Refactoring: Drew Q, Tristan D
 */
@RemoteServiceRelativePath("get_problem_status")
public interface GetProblemStatusService extends RemoteService
{

	/**
	 * Given the id of a user and the assignment id this will return the status
	 * of all the problems in that assignment for the user.
	 * 
	 * @param userId
	 *            The id of the user.
	 * @param assignmentId
	 *            The id of the assignment.
	 * @return A list of problem statuses.
	 */
	List<ProblemStatusMsg> getProblemStatus(int userId, int assignmentId);

}