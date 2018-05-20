package com.wmd.client.service;

import java.sql.SQLException;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wmd.client.msg.AssignmentMsg;

/**
 * Returns all assignments in the database.
 * 
 * @author A.J. Marx, Chris Koch
 */
@RemoteServiceRelativePath("get_assignments")
public interface GetAssignmentsService extends RemoteService
{

	/**
	 * Returns a list of all the assignments in the database.
	 * 
	 * @return A list of AssignmentMsg objects.
	 * @throws SQLException This throws an SQl exception when a problem happens.
	 */
	List<AssignmentMsg> getAssignments();

}
