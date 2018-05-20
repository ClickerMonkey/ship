package com.wmd.client.msg;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * A message describing an User_assignment. An user_assignment is described by a
 * user message and a assignment message
 * 
 * @author Olga Zalamea, William Fisher
 */
public class UserAssignmentMsg implements IsSerializable
{

	// The userMsg.
	private UserMsg userMsg;

	// The assignmentMsg.
	private AssignmentMsg assignmentMsg;

	/**
	 * Initializes a new UserAssignmentMsg. Must have an empty constructor for
	 * serialization.
	 */
	public UserAssignmentMsg()
	{
		// Empty
	}

	/**
	 * Gets the userMsg.
	 * 
	 * @return userMsg
	 */
	public UserMsg getUser()
	{
		return this.userMsg;
	}

	/**
	 * Gets the assigmentMsg.
	 * 
	 * @return assignmentMsg
	 */
	public AssignmentMsg getAssignment()
	{
		return this.assignmentMsg;
	}

	/**
	 * Sets the userMsg. This is only for the server side, changes on the client
	 * side will have no effect.
	 * 
	 * @param userMsg
	 *            the user to set
	 */
	public void setUser(UserMsg userMsg)
	{
		this.userMsg = userMsg;
	}

	/**
	 * Sets the AssignmentMsg. This is only for the server side, changes on the
	 * client side will have no effect.
	 * 
	 * @param assignmentMsg
	 *            The assignment to set
	 */
	public void setAssignment(AssignmentMsg assignmentMsg)
	{
		this.assignmentMsg = assignmentMsg;
	}

}
