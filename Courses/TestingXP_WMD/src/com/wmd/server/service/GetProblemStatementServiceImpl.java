package com.wmd.server.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wmd.client.entity.ProblemStatement;
import com.wmd.client.service.GetProblemStatementService;
import com.wmd.server.db.Problem;

/**
 * Gets a single problem statement from the database given its id.
 * 
 * This is invoked by the client when a user wants to attempt a problem. This
 * will return null if the problem doesn't exit.
 */
public class GetProblemStatementServiceImpl extends RemoteServiceServlet
		implements GetProblemStatementService
{
	private static final long serialVersionUID = 6103685107366771098L;

	/**
	 * Given the id of a problem return the associated ProblemStatement
	 * instance.
	 * 
	 * @param problemId
	 *            The problem id of the statement
	 * @return The problemStament from the database.
	 */
	public ProblemStatement getProblemStatement(int problemId)
	{
		ProblemStatement msg = null;
		try
		{
			Problem prob = new Problem(problemId);
		    msg = prob.getProblemStatement();
		} catch (Exception e)
		{
			e.printStackTrace();
			GWT.log("Error in GetProblemStatement class", e);
		}
		return msg;
	}

}
