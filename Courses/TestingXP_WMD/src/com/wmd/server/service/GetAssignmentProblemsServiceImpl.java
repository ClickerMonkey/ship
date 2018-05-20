package com.wmd.server.service;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wmd.client.msg.ProblemMsg;
import com.wmd.client.service.GetAssignmentProblemsService;
import com.wmd.server.db.ProblemList;

/**
 * Returns all problems of a given assignment.
 * 
 * This is invoked when the instructor wants a list of problems for the given
 * assignment. This will return null of the assignment does not exist.
 * 
 * @author Philip Diffenderfer, Eric Abruzzese
 * 
 */
public class GetAssignmentProblemsServiceImpl extends RemoteServiceServlet
		implements GetAssignmentProblemsService
{
	private static final long serialVersionUID = 6080404250858595053L;

	/**
	 * Given the id of a problem return the associated Problem instance.
	 * 
	 * @param assignmentId
	 *            The id of the assignment to get the problems of.
	 * @return The list of the problems in the assignment.
	 */
	public List<ProblemMsg> getAssignmentProblems(int assignmentId)
	{
		List<ProblemMsg> list = null;
		
		try
		{
			ProblemList probList = new ProblemList();
			
			list = probList.getProblemsByAssignment(assignmentId);
		} catch (Exception e)
		{
			e.printStackTrace();
			GWT.log("Error in GetAssignmentProblemsServiceImpl", e);
		}
		return list;
	}


}
