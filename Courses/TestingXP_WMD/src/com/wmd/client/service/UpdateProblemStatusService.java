package com.wmd.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wmd.client.msg.ProblemStatusMsg;

/**
 * Given the id of the user, assignment, and problem as well as if the user was
 * correct, this will update the database and return the new ProblemStatusMsg.
 */
@RemoteServiceRelativePath("update_problem_status")
public interface UpdateProblemStatusService extends RemoteService
{

	/**
	 * Updates the status of the given problem depending on whether the user
	 * answered it correctly.
	 * 
	 * @param oldStatus
	 *            The previous status of the problem to update.
	 * @param correct
	 *            Whether the answer on the client side was correct.
	 * @return The new problem status.
	 */
	ProblemStatusMsg updateStatus(int userId, int problemId, boolean correct);

}