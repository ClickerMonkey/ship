package com.wmd.client.widget.student;

import com.google.gwt.user.client.ui.Button;

/**
 * 
 * @author William Fisher and Olga Zalamea Purpose: Button used in the
 *         assignmentListWidget that when clicked loads the assignment.
 * 
 */
public class AssignmentLoadingButton extends Button
{

	private int assignmentId;

	/**
	 * Constructor for the Button
	 * 
	 * @param assignmentId
	 *            The Id of the assignment to be loaded
	 */
	public AssignmentLoadingButton(int assignmentId)
	{
		this.assignmentId = assignmentId;
		this.setText("Load Assignment");
	}

	/**
	 * Getter for the assignment
	 * 
	 * @return assignment id
	 */
	public int getAssignmentId()
	{
		return assignmentId;
	}

}
