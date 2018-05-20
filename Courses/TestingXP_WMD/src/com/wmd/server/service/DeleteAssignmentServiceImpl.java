package com.wmd.server.service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wmd.client.service.DeleteAssignmentService;
import com.wmd.server.db.Assignment;

/**
 * Deletes an assignment from the database based on assignmentId
 * 
 * @author A.J. Marx, Chris Koch
 * 
 * Refactored by
 * @author Drew Q, Paul C
 */
public class DeleteAssignmentServiceImpl extends RemoteServiceServlet implements
		DeleteAssignmentService
{

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param assignmentId
	 *            - the assignment to be deleted
	 * @return - true if at least one assignment was deleted, false otherwise
	 * @throws Exception An exception will be thrown if anything goes wrong
	 */
	public boolean deleteAssignment(int assignmentId)
	{
		
		try
		{
			Assignment a = new Assignment(assignmentId);
			int numRemoved = a.delete();
			if (numRemoved > 0)
			{
				return true;
			}
			return false;
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

}
