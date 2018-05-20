package com.wmd.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wmd.client.entity.ProblemStatement;

/**
 * Gets a single problem statement from the database given its id.
 * 
 * This is invoked by the client when a user wants to attempt a problem. This
 * will return null if the problem doesn't exit.
 */
public interface GetProblemStatementServiceAsync
{

	/**
	 * XXX: Javadoc
	 * @param problemId
	 * @param callback
	 */
	void getProblemStatement(int problemId,
			AsyncCallback<ProblemStatement> callback);

}
