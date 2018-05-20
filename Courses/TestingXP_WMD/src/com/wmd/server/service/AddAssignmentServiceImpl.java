package com.wmd.server.service;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wmd.client.msg.AssignmentMsg;
import com.wmd.client.service.AddAssignmentService;
import com.wmd.client.service.ServiceException;
import com.wmd.server.db.Assignment;

/**
 * Adds an assignment to the database.
 * 
 * This is used when an instructor is trying to create a new assignment. This
 * will create an empty assignment with the given properties and return True if
 * an assignment with the given properties was added.
 */
/**
 * Refactored by 
 * @author Drew Q, Paul C, Steve U
 */
public class AddAssignmentServiceImpl extends RemoteServiceServlet implements
		AddAssignmentService
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Adds a new assignment, and returns
	 * the inserted object as a message.
	 * 
	 * @param name The name of the assignment
	 * @param enabled Whether the assignment is enabled or not
	 * @return An AssignmentMsg holding the data inserted
	 * @throws Exception An exception will be thrown if the insert fails
	 * @throws ServiceException An exception will be thrown if something goes wrong
	 *  in the service.
	 */
	public AssignmentMsg addAssignment(String name, boolean enabled)
	{
		AssignmentMsg ret_msg;
		
		try
		{
			Assignment a = new Assignment(name,enabled);
			ret_msg = new AssignmentMsg(a.getId());
			ret_msg.setName(a.getName());
			ret_msg.setEnabled(a.isEnabled());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		return ret_msg;
	}

}
