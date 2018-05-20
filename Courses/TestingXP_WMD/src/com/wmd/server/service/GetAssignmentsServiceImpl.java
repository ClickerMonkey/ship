package com.wmd.server.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wmd.client.msg.AssignmentMsg;
import com.wmd.client.service.GetAssignmentsService;
import com.wmd.server.db.AssignmentList;

/**
 * Returns all assignments for an instructor
 * 
 * @author A.J. Marx, Chris Koch
 * @author Drew Q, Tristan D
 */
public class GetAssignmentsServiceImpl extends RemoteServiceServlet implements
		GetAssignmentsService
{

	private static final long serialVersionUID = 1L;

	/**
	 * Given the id of an instructor return the assignments for that instructor
	 * 
	 * @param instructorId
	 *            The id of the instructor to get the assignments for
	 * @return The list of assignments
	 * @throws SQLException This throws an SQl exception when a problem happens.
	 */
	public ArrayList<AssignmentMsg> getAssignments()
	{
		try
		{
			AssignmentList aList = new AssignmentList();
			return aList.getAssignments();
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
