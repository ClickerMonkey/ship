package com.wmd.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wmd.client.msg.ProblemStatusMsg;

/**
 * Given the id of the user, assignment, and problem as well as if the user was
 * correct, this will update the database and return the new ProblemStatusMsg.
 */
public interface UpdateProblemStatusServiceAsync
{

	/**
	 * Updates the status of the given problem depending on whether the user
	 * answered it correctly.
	 * 
	 * @param oldStatus
	 *            The previous status of the problem to update.
	 * @param correct
	 *            Whether the answer on the client side was correct.
	 * @param callback
	 *            The callback for the synchronous method.
	 */
	void updateStatus(int userId, int problemId, boolean correct,
			AsyncCallback<ProblemStatusMsg> callback);

}
