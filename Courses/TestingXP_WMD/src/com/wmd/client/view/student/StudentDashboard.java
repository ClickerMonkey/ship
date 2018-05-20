package com.wmd.client.view.student;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Image;
import com.wmd.client.ApplicationWrapper;
import com.wmd.client.msg.UserAssignmentStatusMsg;
import com.wmd.client.msg.UserMsg;
import com.wmd.client.service.GetUserAssignmentStatusService;
import com.wmd.client.service.GetUserAssignmentStatusServiceAsync;
import com.wmd.client.view.View;
import com.wmd.client.widget.ApplicationMenuBar;
import com.wmd.client.widget.student.AssignmentListWidget;

/**
 * Creates student dashboard. Has an ApplicationMenuBar with xxx menus
 * 
 * @author Chris Koch and Paul Cheney 3/17/2010
 * 
 */
public class StudentDashboard extends View
{
	// Create the services
	/**
	 * Create the service used to retrieve the list of problems by student
	 */
	static final GetUserAssignmentStatusServiceAsync getStatusService = GWT
			.create(GetUserAssignmentStatusService.class);

	// The user of the dashboard
	private final UserMsg user;

	// the list of assignments
	ArrayList<UserAssignmentStatusMsg> theList = null;

	/**
	 * Initializes new InstructorDashboard
	 * @param user 
	 */
	public StudentDashboard(final UserMsg user)
	{
		this.user = user;

		final ApplicationMenuBar amb = ApplicationMenuBar.getInstance();
		AbsolutePanel absolutePanel = new AbsolutePanel();
		final FlowPanel displayTable = new FlowPanel();
		// clear the items before adding so don't double add items
		//Edited by Paul 4/7/2010.  Changed to use the new clear method.
		amb.clearItemsNew();

		Command viewCommand = new Command()
		{
			public void execute()
			{
				// database call to get assignment list by student
				getStatusService.getUserAssignments(user.getUserId(),
						new AsyncCallback<List<UserAssignmentStatusMsg>>()
						{
							/**
							 * Invoked when an error occurs calling the server
							 * to get the list of problem statuses.
							 */
							public void onFailure(Throwable caught)
							{
								Window
										.alert("Failed to get the list of assignemnts");
							}

							/**
							 * Invoked when the server returns a list of status
							 * messages for the given user and assignment.
							 */
							public void onSuccess(
									List<UserAssignmentStatusMsg> result)
							{
								// Only load view if atleast 1 status is
								// returned.
								if (result == null)
								{
									Window.alert("A null list was returned!");
									return;
								}
								theList = (ArrayList<UserAssignmentStatusMsg>) result;
								AssignmentListWidget assignmentList = new AssignmentListWidget(
										theList, user);
								displayTable.clear();
								displayTable.add(assignmentList);
								ApplicationWrapper.getInstance().setWidget(
										displayTable);
							}
						});

			}
		};


		// add the items to the menu
		amb.addItem("View Assignments", viewCommand);
		// amb.addItem("Add", addCommand);
		// amb.addItem("Save", saveCommand);
		// display the image
		Image banner = new Image("Resources/Images/MathRocketBanner.png");
		HorizontalPanel bannerAndName = new HorizontalPanel();
		bannerAndName.add(banner);
		FlexTable name = new FlexTable();
		name.setStyleName("big_name");
		name.setText(0, 0, " ");
		name.setText(1, 0, " ");
		name.setText(2, 0, " ");
		name.setText(3, 0, " ");
		name.setText(4, 0, " ");
		name.setText(0, 0, user.getLastName() + ", " + user.getFirstName());
		
		bannerAndName.add(name);
		absolutePanel.add(bannerAndName);
		
		absolutePanel.add(amb);
		super.getLayout().setWidget(0, 0, absolutePanel);
		super.getLayout().setWidget(1, 0, displayTable);
	}

	/**
	 * @return The user of this dashboard.
	 */
	public UserMsg getUser()
	{
		return user;
	}

}