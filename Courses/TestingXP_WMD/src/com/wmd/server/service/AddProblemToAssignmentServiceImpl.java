package com.wmd.server.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wmd.client.service.AddProblemToAssignmentService;
import com.wmd.server.db.Problem;

/**
 * Adds a problem to a given assignment.
 * 
 * This will add a relationship between a pre-existing assignment and problem in
 * the database. This will return the relationship added to the database, or
 * null if the problem could not be added to the assignment (assignment didn't
 * exist, problem didn't exist, relationship already exists).
 */
@SuppressWarnings("serial")
public class AddProblemToAssignmentServiceImpl extends RemoteServiceServlet
		implements AddProblemToAssignmentService
{
	/**
	 * Adds the problem to the given assignment.
	 * 
	 * @param problemId
	 *            The id of the problem.
	 * @param assignmentId
	 *            The id of the assignment.
	 * @param callback
	 *            The callback for the synchronous method.
	 */
	public boolean addProblem(int problemId, int assignmentId)
	{
		// ProblemAssignmentMsg msg = null;
		try
		{
			Problem p = new Problem(problemId);
			
			p.setAssignmentId(assignmentId);
			
		} catch (Exception e)
		{
			e.printStackTrace();
			GWT.log("Error in AddProblemToAssignmentImpl", e);
		}
		// return msg;
		return true;
	}

}
