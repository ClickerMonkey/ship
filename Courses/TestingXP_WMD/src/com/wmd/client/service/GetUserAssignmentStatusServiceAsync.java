package com.wmd.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wmd.client.msg.UserAssignmentStatusMsg;

/**
 * Returns the status of all assignments for a given user.
 * 
 * @author Philip Diffenderfer, Paul Cheney
 */
public interface GetUserAssignmentStatusServiceAsync
{

	/**
	 * Given the id of a user return the assigned Assignment statuses. This will
	 * invoke the callback implementation whenever the server responds.
	 * 
	 * @param userId
	 *            The id of the user.
	 * @param callback
	 *            The callback to invoke when the server returns.
	 */
	void getUserAssignments(int userId,
			AsyncCallback<List<UserAssignmentStatusMsg>> callback);

}
