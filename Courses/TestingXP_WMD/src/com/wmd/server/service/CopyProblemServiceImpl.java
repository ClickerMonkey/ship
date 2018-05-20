package com.wmd.server.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wmd.client.msg.Level;
import com.wmd.client.service.CopyProblemService;
import com.wmd.server.db.Problem;

/**
 * 
 * @author Sam Storino, Scotty Rhinehart, AJ Marx This is the service
 *         implementation for the client side copy problem. It will take the
 *         problem message that is given and the destination level to copy to,
 *         then send it to the problem handler to be executed and have the
 *         problem copied from one level to another.
 */
@SuppressWarnings("serial")
public class CopyProblemServiceImpl extends RemoteServiceServlet implements
		CopyProblemService
{

	/**
	 * Method that will take the problem id and the destination level and
	 * send them to the handler unless a problem occurs then an error will be
	 * shown.
	 * 
	 * @param problemId
	 *            - the problem to be copied
	 * @param level
	 *            - the destination level to copy the problem to
	 * 
	 * @return The ID of the new problem made from the original or 0 on error.
	 */
	public int copyProblem(int problemId, Level level)
	{
		int newId = 0;
		
		try
		{
			Problem prob = new Problem(problemId);
			newId = prob.copyProblem(level);
			
		} catch (Exception e)
		{
			// Throw an error if copyProblem was not successful, return false
			e.printStackTrace();
			GWT.log("Error in CopyProblemService class", e);
			return 0;
		}
		
		return newId;
	}

}