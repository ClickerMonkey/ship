package com.wmd.server.service;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wmd.client.msg.UserAssignmentStatusMsg;
import com.wmd.client.service.GetAllUserAssignmentStatusService;
import com.wmd.server.db.UserAssignmentList;


/**
 * This service should be used to fetch a list of UserAssigmentStatus from the database.
 * 
 * @author Olga Zalamea and Scotty Rhinehart
 * 
 */
public class GetAllUserAssigmentStatusServiceImpl extends RemoteServiceServlet implements
	GetAllUserAssignmentStatusService
{
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserAssignmentStatusMsg> getAllUserAssignmentStatus(int assignmentId)
	{
		List<UserAssignmentStatusMsg> result = null;
		try
		{
			UserAssignmentList handler = new UserAssignmentList();
			result = handler.getAllUserAssignmentStatusByAssignment(assignmentId);	
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return result;
	}


}
