package com.wmd.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wmd.client.msg.UserAssignmentStatusMsg;

/**
 * Returns the status of all assignments for a given user.
 * 
 * @author Philip Diffenderfer, Paul Cheney
 */
@RemoteServiceRelativePath("get_user_assignment_status_service")
public interface GetUserAssignmentStatusService extends RemoteService
{

	/**
	 * Given the id of a user return the assigned Assignment statuses.
	 * 
	 * @param userId
	 *            The id of the user.
	 * @return The list of the assignment statuses for the user.
	 */
	List<UserAssignmentStatusMsg> getUserAssignments(int userId);

}