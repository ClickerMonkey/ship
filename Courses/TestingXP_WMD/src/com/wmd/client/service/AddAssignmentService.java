package com.wmd.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wmd.client.msg.AssignmentMsg;

/**
 * Adds an assignment to the database.
 * 
 * This is used when an instructor is trying to create a new assignment. This
 * will create an empty assignment with the given properties and return True if
 * an assignment with the given properties was added.
 */
/**
 * Refactored by
 * @author Drew Q , Paul C
 */
@RemoteServiceRelativePath("add_assignment")
public interface AddAssignmentService extends RemoteService
{

	/**
	 * Adds an assignment to the database.
	 * 
	 * @param msg
	 *            The initial properties of the assignment to add.
	 * @return A msg describing the assignment that was created
	 * @throws ServiceException
	 *             describing any errors that occurred
	 * @throws Exception 
	 */
	AssignmentMsg addAssignment(String name, boolean enabled);

}