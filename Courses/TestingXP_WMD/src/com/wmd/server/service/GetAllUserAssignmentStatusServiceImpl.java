package com.wmd.server.service;

import java.util.List;

import com.google.gwt.core.client.GWT;
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
public class GetAllUserAssignmentStatusServiceImpl extends RemoteServiceServlet implements
	GetAllUserAssignmentStatusService
{
	private static final long serialVersionUID = 1L;


	/**
	 * Returns an ArrayList of UserAssigmentStatusMsg for the specific assignment
	 * @return An ArrayList of UserAssigmentStatusMsg objects
	 */
	public List<UserAssignmentStatusMsg> getAllUserAssignmentStatus(int assignmentId)
	{		
		try
		{
			UserAssignmentList list = new UserAssignmentList();
			return list.getAllUserAssignmentStatusByAssignment(assignmentId);
		} catch (Exception e)
		{
			e.printStackTrace();
			GWT.log("Error in getAllUserAssignmentStatus", e);
			return null;
		}
	}


}
