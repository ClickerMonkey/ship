package com.wmd.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Deletes a problem from the database.
 * 
 * @author Philip Diffenderfer
 */
public interface DeleteProblemServiceAsync
{

	/**
	 * Deletes the given problem.
	 * 
	 * @param problemId The id of the problem to delete.
	 * @return True if problem was deleted, false otherwise.
	 * @throws Exception An exception will be thrown if anything goes wrong.
	 */
	public void deleteProblem(int problemId, AsyncCallback<Boolean> callback);
	
}
