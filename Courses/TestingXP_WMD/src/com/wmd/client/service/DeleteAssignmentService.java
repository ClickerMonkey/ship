package com.wmd.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Deletes an assignment in the database based on assignmentId
 * 
 * @author A.J. Marx, Chris Koch
 * 
 * Refactored by
 * @author Drew Q, Paul C
 */
@RemoteServiceRelativePath("delete_assignment")
public interface DeleteAssignmentService extends RemoteService
{

	/**
	 * 
	 * @param assignmentId
	 *            - assignment to be deleted
	 * @return - true if assignment was deleted, false otherwise
	 * @throws Exception An exception will be thrown if anything goes wrong
	 */
	boolean deleteAssignment(int assignmentId);

}
