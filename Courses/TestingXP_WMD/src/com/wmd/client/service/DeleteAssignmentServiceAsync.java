package com.wmd.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Deletes an assignment from the database based on assignmentId
 * 
 * @author A.J. Marx, Chris Koch
 */
public interface DeleteAssignmentServiceAsync
{
	/**
	 * 
	 * @param assignmentId
	 *            - assignment to be deleted
	 * @param callback
	 *            - True if the assignment was deleted, false otherwise
	 */
	void deleteAssignment(int assignmentId, AsyncCallback<Boolean> callback);
}
