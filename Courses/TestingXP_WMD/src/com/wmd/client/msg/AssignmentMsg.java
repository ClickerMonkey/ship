package com.wmd.client.msg;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * A message describing an assignment. An assignment is described by a unique
 * id, a name (short description) and the number of problems in the assignment.
 * 
 * @author Philip Diffenderfer, Paul Cheney, Tristan Dalious
 * 
 * Refactored
 * @author Drew Q, Paul C, Sam S.
 */
public class AssignmentMsg implements IsSerializable
{

	// The id of the assignment.
	private int assignmentId;

	// The name or short description of the problem.
	private String name;

	// A derived value calculated by how many Problem-Assignment relations exist
	private int problemCount;

	// Allows an assignment to be enabled/disabled to allow/disallow access
	private boolean enabled;

	/**
	 * Initializes a new AssignmentMsg. Must have an empty constructor for
	 * serialization.
	 */
	public AssignmentMsg()
	{
		// No construction
	}

	/**
	 * Initializes a new AssignentMsg, with parameters for automatic setting.
	 * 
	 * @param id
	 *            ID of the assignment
	 * @param assignmentName
	 *            Name of the assignment
	 * @param enabled
	 *            If the assignment is active
	 */
	public AssignmentMsg(int id, String assignmentName, boolean enabled)
	{
		this.setAssignmentId(id);
		this.setName(assignmentName);
		this.setEnabled(enabled);
	}

	/**
	 * Initializes a new AssignentMsg, with parameters for automatic setting.
	 * 
	 * @param assignmentName
	 *            Name of the assignment
	 * @param enabled
	 *            If the assignment is active
	 */
	public AssignmentMsg(String assignmentName, boolean enabled)
	{
		this.setName(assignmentName);
		this.setEnabled(enabled);
	}

	/**
	 * Creates a new assignment msg from the given Id
	 * 
	 * @param assignmentId The id of the message
	 */
	public AssignmentMsg(int assignmentId) 
	{
		this.setAssignmentId(assignmentId);
	}

	/**
	 * Gets the id of the assignment.
	 * 
	 * @return the assignmentId
	 */
	public int getAssignmentId()
	{
		return this.assignmentId;
	}

	/**
	 * Sets the id of the assignment. This is only for the server side, changes
	 * on the client side will have no effect.
	 * 
	 * @param assignmentId1
	 *            the assignmentId to set
	 */
	public void setAssignmentId(int assignmentId1)
	{
		this.assignmentId = assignmentId1;
	}

	/**
	 * @return The name (or short description) of this Assignment.
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Sets the name (or short description) of this Assignment.
	 * 
	 * @param name1
	 *            The new name of the assignment.
	 */
	public void setName(String name1)
	{
		this.name = name1;
	}

	/**
	 * @return The number of problems in this assignment.
	 */
	public int getProblemCount()
	{
		return this.problemCount;
	}

	/**
	 * Sets the problem count of the assignment. This is only for the server
	 * side, changes on the client side will have no effect.
	 * 
	 * @param problemCount1
	 *            The new number of problems in the assignment.
	 */
	public void setProblemCount(int problemCount1)
	{
		this.problemCount = problemCount1;
	}

	/**
	 * Sets whether or not the assignment is enabled for the user to take the
	 * assignment
	 * 
	 * @param isEnabled
	 *            The new enabled-status of the assignment
	 */
	public void setEnabled(boolean isEnabled)
	{
		this.enabled = isEnabled;
	}

	/**
	 * Determines whether or not the assignment is enabled.
	 * 
	 * @return enabled The enabled-status of the assignment
	 */
	public boolean isEnabled()
	{
		return this.enabled;
	}
}
