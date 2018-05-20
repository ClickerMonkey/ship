package com.wmd.server.service;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wmd.client.msg.ProblemStatusMsg;
import com.wmd.client.service.GetProblemStatusService;
import com.wmd.server.db.ProblemStatusList;

/**
 * Gets the status of all problems for a user taking an assignment.
 * 
 * This is invoked by the client when a user is trying to take an assignment.
 * This should be called on the page load so the user can see what problems are
 * contained in the assignment and what their status is on each.
 * 
 * @author Refactoring: Drew Q, Tristan D
 */
public class GetProblemStatusServiceImpl extends RemoteServiceServlet implements
		GetProblemStatusService
{

	/**
	 * The serial version ID
	 */
	private static final long serialVersionUID = -551472503820256681L;


	/**
	 * Given the id of a user and the assignment id this will return the status
	 * of all the problems in that assignment for the user.
	 * 
	 * @param userId
	 *            The id of the user.
	 * @param assignmentId
	 *            The id of the assignment.
	 * @return A list of problem statuses.
	 * 
	 * Refactored to use new ProblemStatusMsg by Bill F and Chris E
	 */
	public List<ProblemStatusMsg> getProblemStatus(int userId, int assignmentId)
	{
		List<ProblemStatusMsg> statuses = null;
		try
		{
			ProblemStatusList handler = new ProblemStatusList();	
			statuses = handler.getStatuses(userId, assignmentId);
		} 
		catch (Exception e)
		{
			GWT.log("Error in GetProblemServiceImpl class", e);
			e.printStackTrace();
		}
		return statuses;
	}


}
