package com.wmd.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wmd.client.msg.ProblemMsg;

/**
 * Returns all problems of a given assignment.
 * 
 * This is invoked when the instructor wants a list of problems for the given
 * assignment. This will return null of the assignment does not exist.
 */
public interface GetAssignmentProblemsServiceAsync
{

	/**
	 * Given the id of a problem return the associated Problem instance.
	 * 
	 * @param assignmentId
	 *            The id of the assignment to get the problems of.
	 * @param callback
	 *            The callback for the synchronous method.
	 */
	void getAssignmentProblems(int assignmentId,
			AsyncCallback<List<ProblemMsg>> callback);

}
