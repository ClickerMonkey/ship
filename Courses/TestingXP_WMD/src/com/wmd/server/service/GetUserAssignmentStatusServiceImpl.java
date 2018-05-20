package com.wmd.server.service;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wmd.client.msg.UserAssignmentStatusMsg;
import com.wmd.client.service.GetUserAssignmentStatusService;
import com.wmd.server.db.UserAssignmentList;

/**
 * Returns the status of all assignments for a given user (implementation).
 * 
 * @author Philip Diffenderfer, Paul Cheney
 * @refactor Olga Zalamea
 */
@SuppressWarnings("serial")
public class GetUserAssignmentStatusServiceImpl extends RemoteServiceServlet
		implements GetUserAssignmentStatusService
{


	/**
	 * Given the id of a user return the assigned Assignment statuses.
	 * 
	 * @param userId
	 *            The id of the user.
	 * @return The list of the assignment statuses for the user.
	 */
	public List<UserAssignmentStatusMsg> getUserAssignments(int userId)
	{
		try
		{
			UserAssignmentList list = new UserAssignmentList();
			return list.getAllUserAssignmentStatusByUser(userId);
		} catch (Exception e)
		{
			e.printStackTrace();
			GWT.log("Error in getAllUserAssignmentStatus", e);
			return null;
		}
	}

}
