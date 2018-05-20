package com.wmd.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wmd.client.msg.ProblemMsg;

/**
 * Returns all problems of a given assignment.
 * 
 * This is invoked when the instructor wants a list of problems for the given
 * assignment. This will return null of the assignment does not exist.
 */
@RemoteServiceRelativePath("get_assignment_problems")
public interface GetAssignmentProblemsService extends RemoteService
{

	/**
	 * Given the id of a problem return the associated Problem instance.
	 * 
	 * @param assignmentId
	 *            The id of the assignment to get the problems of.
	 * @return The list of the problems in the assignment.
	 */
	List<ProblemMsg> getAssignmentProblems(int assignmentId);

}