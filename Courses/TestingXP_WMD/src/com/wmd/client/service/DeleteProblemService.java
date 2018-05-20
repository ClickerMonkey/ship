package com.wmd.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Deletes a problem from the database.
 * 
 * @author Philip Diffenderfer
 */
@RemoteServiceRelativePath("delete_problem")
public interface DeleteProblemService extends RemoteService
{

	/**
	 * Deletes the given problem.
	 * 
	 * @param problemId The id of the problem to delete.
	 * @return True if problem was deleted, false otherwise.
	 * @throws Exception An exception will be thrown if anything goes wrong.
	 */
	public boolean deleteProblem(int problemId);

}
