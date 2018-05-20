package com.wmd.client.widget.instructor;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.wmd.client.msg.PeriodMsg;
import com.wmd.client.msg.Role;
import com.wmd.client.msg.UserAssignmentStatusMsg;
import com.wmd.client.msg.UserMsg;
import com.wmd.client.service.GetAllUsersService;
import com.wmd.client.service.GetAllUsersServiceAsync;
import com.wmd.client.service.GetPeriodsService;
import com.wmd.client.service.GetPeriodsServiceAsync;
import com.wmd.client.service.GetUserAssignmentStatusService;
import com.wmd.client.service.GetUserAssignmentStatusServiceAsync;

/**
 * @author Stephen Jurnack and Sam Storino and Kevin Rexroth
 * 
 *         A widget for displaying a list of students by period and viewing
 *         their cumulative grades.
 * 
 */
public class StudentPeriodReportWidget extends VerticalPanel
{
	double grade = 0.0;
	int correctSum = 0;
	int totalSum = 0;
	/*
	 * Service call handler for getting all students.
	 */
	private static final GetAllUsersServiceAsync getAllUsersService = GWT
			.create(GetAllUsersService.class);

	/*
	 * Service call handler for getting a list of assignment statuses.
	 */
	private static final GetUserAssignmentStatusServiceAsync getAssignmentService = GWT
			.create(GetUserAssignmentStatusService.class);

	/*
	 * Service call handler for getting a list of all the periods
	 */
	private static final GetPeriodsServiceAsync getPeriods = GWT
			.create(GetPeriodsService.class);

	/*
	 * Service call handler for getting assignment status list by userId.
	 */
	// private static final GetAllUsersService getAllUsersService = GWT
	// .create(GetAllUsersService.class);

	FlexTable table = new FlexTable();

	ArrayList<UserMsg> studentList = new ArrayList<UserMsg>();

	ListBox listBox = new ListBox();
	VerticalPanel vPanel = new VerticalPanel();

	/**
	 * Constructor
	 */
	public StudentPeriodReportWidget()
	{
		// get a list of periods
		listBox.addItem("Select a period.");
		getPeriods.getPeriods(new AsyncCallback<List<PeriodMsg>>()
		{

			@Override
			public void onFailure(Throwable caught)
			{
				// TODO Auto-generated method stub
				Window.alert("Error in retrieving the list of periods.");
			}

			@Override
			public void onSuccess(List<PeriodMsg> result)
			{
				populatePeriods(result);
			}
		});
		listBox.addChangeHandler(new ChangeHandler()
		{

			@Override
			public void onChange(ChangeEvent event)
			{
				table.clear();
				// change the displayed students based on the period
				final String currentPeriod = listBox.getItemText(listBox
						.getSelectedIndex());
				if (!currentPeriod.equals("Select a period."))
				{
					table.setWidget(0, 0, new Label(currentPeriod));

					// get a list of students
					getAllUsersService.getAllUsers(Role.Student,
							new AsyncCallback<ArrayList<UserMsg>>()
							{

								@Override
								public void onFailure(Throwable caught)
								{
									// TODO Auto-generated method stub
									Window
											.alert("Error: Problem retrieving student list.");
								}

								@Override
								public void onSuccess(ArrayList<UserMsg> result)
								{
									// got a list of students
									int counter = 0;
									for (UserMsg user : result)
									{
										if (user.getPeriod().equals(
												currentPeriod))
										{
											processUser(user, counter++);

										}
									}
								}

							});
				}
			}

		});
		vPanel.add(listBox);
		vPanel.add(table);
		this.add(vPanel);
	}
	
	private void processUser(final UserMsg user, final int index)
	{
		table.setWidget(index, 0, new Label(user.getLastName() + ", " + user.getFirstName()));
		getAssignmentService.getUserAssignments(user.getUserId(),
				new AsyncCallback<List<UserAssignmentStatusMsg>>()
				{
					@Override
					public void onFailure(Throwable caught)
					{
						Window.alert("No.");
					}

					@Override
					public void onSuccess(List<UserAssignmentStatusMsg> result)
					{
						grade = 0.0;
						correctSum = 0;
						totalSum = 0;
						for (UserAssignmentStatusMsg msg : result)
						{
							correctSum += msg.getProblemsCorrect();
							totalSum += msg.getProblemCount();
						}
						if (totalSum > 0)
						{
							grade = (double)correctSum / totalSum;
						}
						table.setWidget(index, 1, new Label("" + grade*100+"%"));
					}
				});
	}
	
	/**
	 * Adds each period to the listbox.
	 * 
	 * @param periods
	 *            The list of periods in the database
	 */
	public void populatePeriods(List<PeriodMsg> periods)
	{
		for (PeriodMsg msg : periods)
		{
			listBox.addItem(msg.getPeriodName());
		}
	}
}