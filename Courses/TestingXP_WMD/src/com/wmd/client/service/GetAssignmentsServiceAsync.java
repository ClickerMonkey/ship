package com.wmd.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wmd.client.msg.AssignmentMsg;

/**
 * Returns all assignments in the database.
 * 
 * @author A.J. Marx, Chris Koch
 */
public interface GetAssignmentsServiceAsync
{
	/**
	 * Returns all the assignments in the database.
	 * 
	 * @param callback
	 *            A List of all assignments in the database.
	 */
	void getAssignments(AsyncCallback<List<AssignmentMsg>> callback);
}
