package com.wmd.client.widget.student;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.wmd.client.ApplicationWrapper;
import com.wmd.client.msg.UserAssignmentStatusMsg;
import com.wmd.client.msg.UserMsg;
import com.wmd.client.view.student.StudentView;

/**
 * 
 * @author William Fisher And Olga Zalamea Purpose: This widget is used to query
 *         the server and display the available assignments. If the assignment
 *         is active it should open the assignment when clicked.
 * 
 */
public class AssignmentListWidget extends FlexTable
{
	private ArrayList<UserAssignmentStatusMsg> assignments;
	private UserMsg user;

	/**
	 * Constructor for the widget
	 * 
	 * @param theList
	 *            - The list of assignments to be displayed
	 * @param view
	 *            - The StudentView that is being looked at
	 */
	public AssignmentListWidget(ArrayList<UserAssignmentStatusMsg> theList,
			UserMsg user)
	{
		this.assignments = theList;
		this.user = user;
		load();
	}

	/**
	 * Send the messages to the correct method if they are active
	 */
	public void load()
	{
		this.setText(0, 0, "Assignment Name");
		this.setText(0, 1, "Launch Assignment");
		this.setText(0, 2, "Completed");

		for (UserAssignmentStatusMsg uam : assignments)
		{

			if (uam.isAssignmentEnabled())
			{
				createActiveAssignment(uam);
			} else
			{
				createInactiveAssignment(uam);
			}
		}
	}

	/**
	 * Method for loading active assignments
	 * 
	 * @param theAssignment
	 *            the Assignment to be displayed
	 */
	private void createActiveAssignment(
			final UserAssignmentStatusMsg theAssignment)
	{
		int row = this.getRowCount();
		final AssignmentLoadingButton assButton = new AssignmentLoadingButton(
				theAssignment.getAssignmentId());
		assButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				StudentView view = new StudentView(user, theAssignment
						.getAssignmentId());
				ApplicationWrapper.getInstance().setWidget(view);
			}
		});
		this.setText(row, 0, theAssignment.getAssignmentName());
		this.setWidget(row, 1, assButton);
		this.setText(row, 2, theAssignment.getProblemsCorrect() + "/"
				+ theAssignment.getProblemCount());
	}

	/**
	 * Method for loading inactive assignments
	 * 
	 * @param theAssignment
	 *            the Assignment to be displayed
	 */
	private void createInactiveAssignment(UserAssignmentStatusMsg theAssignment)
	{
		int row = this.getRowCount();
		this.setText(row, 0, theAssignment.getAssignmentName());
		this.setText(row, 2, theAssignment.getProblemsCorrect() + "/"
				+ theAssignment.getProblemCount());
	}

}
