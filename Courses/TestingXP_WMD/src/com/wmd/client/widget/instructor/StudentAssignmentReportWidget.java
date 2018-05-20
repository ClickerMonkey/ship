package com.wmd.client.widget.instructor;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.wmd.client.msg.PeriodMsg;
import com.wmd.client.msg.UserAssignmentStatusMsg;
import com.wmd.client.msg.UserMsg;
import com.wmd.client.service.GetUserAssignmentStatusService;
import com.wmd.client.service.GetUserAssignmentStatusServiceAsync;
import com.wmd.client.service.GetUsersByPeriodService;
import com.wmd.client.service.GetUsersByPeriodServiceAsync;

/**
 * Widget that displays the grades for all assignments for a student.
 * @author Christopher Eby and Bill Fisher
 * 
 */
public class StudentAssignmentReportWidget extends FlexTable
{
	/**
	 * Widget that displays the grades.
	 */
	protected StudentAssignmentReportDisplayWidget displayWidget;
	/**
	 * Service that gets users by their period.
	 */
	static final GetUsersByPeriodServiceAsync getUsersService = GWT
			.create(GetUsersByPeriodService.class);
	/**
	 * Service that gets UserAssignmentStatusMsg's for a user.
	 */
	static final GetUserAssignmentStatusServiceAsync getAssignemntStatusService = GWT
			.create(GetUserAssignmentStatusService.class);

	/**
	 * Constructor that initializes the class. Needs a List of PeriodMsgs.
	 * 
	 * @param periods
	 *            List of all periods
	 */
	public StudentAssignmentReportWidget(List<PeriodMsg> periods)
	{
		load(periods);
	}

	/**
	 * Loads the widget, needs a list of period messages.
	 * 
	 * @param periods
	 *            List<PeriodMsg>
	 */
	public void load(List<PeriodMsg> periods)
	{
		final ListBox periodSelect = new ListBox();
		periodSelect.addItem("Select Period", new Integer(-1).toString());
		// Populate the listbox
		for (PeriodMsg period : periods)
		{
			periodSelect.addItem(period.getPeriodName());
		}
		// Adding a change handler
		periodSelect.addChangeHandler(new ChangeHandler()
		{
			@Override
			public void onChange(ChangeEvent event)
			{
				addStudentSelect(periodSelect.getValue(periodSelect
						.getSelectedIndex()));
			}
		});

		this.setWidget(0, 0, periodSelect);
		this.setText(0, 1, null);
	}

	/**
	 * Adds the student select listbox that lets the user select the students
	 * from the period that is passed in.
	 * 
	 * @param period String
	 */
	void addStudentSelect(String period)
	{
		this.clearCell(0, 1);
		final ListBox studentSelect = new ListBox();
		studentSelect.addItem("Select Student");
		// Make Query for students by period
		getUsersService.getUsers(period, new AsyncCallback<List<UserMsg>>()
		{
			/**
			 * Invoked when an error occurs 
			 */
			public void onFailure(Throwable caught)
			{
				Window.alert(caught.getMessage());
			}

			/**
			 * Invoked when the server returns a list of status messages for the
			 * given period.
			 */
			public void onSuccess(List<UserMsg> serviceResult)
			{
				for (UserMsg student : serviceResult)
				{
					studentSelect.addItem(student.getLastName() + ", "
							+ student.getFirstName(), new Integer(student
							.getUserId()).toString());
				}
				studentSelect.addChangeHandler(new ChangeHandler()
				{
					@Override
					public void onChange(ChangeEvent event)
					{
						getAssignemntStatusService
								.getUserAssignments(
										Integer.parseInt(studentSelect.getValue(studentSelect.getSelectedIndex())),
										new AsyncCallback<List<UserAssignmentStatusMsg>>()
										{
											/**
											 * Invoked when an error occurs.
											 */
											public void onFailure(
													Throwable caught)
											{
												Window.alert(caught
														.getMessage());
											}

											/**
											 * Invoked when the server returns a
											 * list of status messages for the
											 * given user.											 */
											public void onSuccess(
													List<UserAssignmentStatusMsg> serviceResult2)
											{
												loadReport(serviceResult2);
											}
										});
					}
				});
				StudentAssignmentReportWidget.this.setWidget(0, 1,
						studentSelect);
			}
		});
	}

	/**
	 * Method that adds the StudentAssignmentReportDisplayWidget for each
	 * assignment
	 * @param assignments List<UserAssignmentStatusMsg>
	 */
	public void loadReport(List<UserAssignmentStatusMsg> assignments)
	{
		int rows = this.getRowCount() - 1;
		while (rows > 1)
		{
			this.clearCell(rows, 0);
			rows--;
		}
		int rowsAdded = 2;
		for (UserAssignmentStatusMsg assignment : assignments)
		{
			if (this.displayWidget == null)
			{
				this.displayWidget = new StudentAssignmentReportDisplayWidget();
				this.setWidget(rowsAdded, 0, this.displayWidget);
				this.displayWidget.load(assignment);
			} else
			{
				this.displayWidget = new StudentAssignmentReportDisplayWidget();
				this.setWidget(rowsAdded, 0, this.displayWidget);
				this.displayWidget.load(assignment);
			}
			rowsAdded++;
		}
	}
}
