package com.wmd.server.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wmd.client.msg.ProblemStatusMsg;
import com.wmd.client.service.UpdateProblemStatusService;
import com.wmd.server.db.ProblemStatus;

/**
 * Given the id of the user, assignment, and problem as well as if the user was
 * correct, this will update the database and return the new ProblemStatusMsg.
 * 
 * @author Refactoring: Drew Q, Tristan D
 * @author Refactoring: Chris E. and Bill F.
 * @author Refactoring Again: Drew Q, Steve U, Tristan D
 */
public class UpdateProblemStatusServiceImpl extends RemoteServiceServlet
		implements UpdateProblemStatusService
{

	/**
	 * The serial version ID
	 */
	private static final long serialVersionUID = -5258791340489792737L;

	/**
	 * Updates the status of the given problem depending on whether the user
	 * answered it correctly.
	 * 
	 * @param old
	 *            The previous status of the problem to update.
	 * @param correct
	 *            Whether the answer on the client side was correct.
	 * @return The new problem status.
	 */
	public ProblemStatusMsg updateStatus(int userId, int problemId, boolean correct)
	{
		ProblemStatusMsg newMsg = null;
		try
		{
			ProblemStatus status = new ProblemStatus(userId, problemId);
			// Since the handler call returned without error we can
			// automatically assume this is true.
			status.setTries(status.getTries() + 1);
			status.setCorrect(correct);
			
			newMsg = status.getMessage();
		} catch (Exception e)
		{
			e.printStackTrace();
			GWT.log("Error in updateStatus()", e);
		}
		return newMsg;
	}

}
