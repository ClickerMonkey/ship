package com.wmd.server.service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wmd.client.service.DeleteProblemService;
import com.wmd.server.db.Problem;

/**
 * Deletes a problem from the database.
 * 
 * @author Philip Diffenderfer
 */
public class DeleteProblemServiceImpl extends RemoteServiceServlet implements DeleteProblemService
{
	
	/**
	 * Generated Serial ID
	 */
	private static final long serialVersionUID = -4372052207040016605L;

	/**
	 * Deletes the given problem.
	 * 
	 * @param problemId The id of the problem to delete.
	 * @return True if problem was deleted, false otherwise.
	 * @throws Exception An exception will be thrown if anything goes wrong.
	 */
	@Override
	public boolean deleteProblem(int problemId)
	{
		try
		{
			Problem prob = new Problem(problemId);
			prob.delete();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;	
		}
	}
}
