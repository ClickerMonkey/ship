package com.wmd.client.widget.instructor;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.wmd.client.msg.AssignmentMsg;
import com.wmd.client.msg.PeriodMsg;
import com.wmd.client.msg.UserAssignmentStatusMsg;
import com.wmd.client.service.GetAllUserAssignmentStatusService;
import com.wmd.client.service.GetAllUserAssignmentStatusServiceAsync;
import com.wmd.client.service.GetPeriodsService;
import com.wmd.client.service.GetPeriodsServiceAsync;

/**
 * Reports grades by assignments.
 * @author Christopher Eby and William Fisher
 * 
 */
public class AssignmentReportWidget extends FlexTable
{
	// list of all students for the selected assignment
	AssignmentReportDisplayWidget displayWidget;
	// results of querying the database
	ArrayList<UserAssignmentStatusMsg> result;
	static final GetAllUserAssignmentStatusServiceAsync getStatusService = GWT
			.create(GetAllUserAssignmentStatusService.class);

	/**
	 * Constructs widget, needs a list of assignment messages.
	 * 
	 * @param assignments
	 *            List of AssignmentMsg
	 */
	public AssignmentReportWidget(List<AssignmentMsg> assignments)
	{
		load(assignments);
	}

	/**
	 * Loads the assignments and periods after they have been selected.
	 */
	public void load(final List<AssignmentMsg> assignments)
	{
		final ListBox assignmentSelect = new ListBox();
		assignmentSelect.addItem("Select Assignment", new Integer(-1)
				.toString());
		for (AssignmentMsg assignment : assignments)
		{
			assignmentSelect.addItem(assignment.getName(), new Integer(
					assignment.getAssignmentId()).toString());
		}
		this.setText(2, 0, null);
		final ListBox periodSelect = new ListBox();
		periodSelect.addItem("Select Assignment");
		assignmentSelect.addChangeHandler(new ChangeHandler()
		{
			@Override
			public void onChange(ChangeEvent event)
			{
				AssignmentReportWidget.this.clearCell(2, 0);
				int index = assignmentSelect.getSelectedIndex();
				if (index != -1)
				{
					Integer assignId = new Integer(assignmentSelect
							.getValue(index));
					// call service
					getStatusService.getAllUserAssignmentStatus(assignId.intValue(),
							new AsyncCallback<List<UserAssignmentStatusMsg>>()
							{
								/**
								 * Invoked when an error occurs calling the
								 * server to get the list of problem statuses.
								 */
								public void onFailure(Throwable caught)
								{
									Window.alert(caught.getMessage());
								}

								/**
								 * Invoked when the server returns a list of
								 * status messages for the given user and
								 * assignment.
								 */
								public void onSuccess(
										List<UserAssignmentStatusMsg> serviceResult)
								{
									result = (ArrayList<UserAssignmentStatusMsg>) serviceResult;
									// Only load view if atleast 1 status is
									// returned.
									if (result != null && result.size() > 0)
									{
										final GetPeriodsServiceAsync getAllPeriods = GWT.create(GetPeriodsService.class);
										
										
										getAllPeriods.getPeriods(new AsyncCallback<List<PeriodMsg>>()
										{
											@Override
											public void onFailure(Throwable caught)
											{
												//Do nothing						
											}

											@Override
											public void onSuccess(List<PeriodMsg> allPeriods)
											{
												periodSelect.clear();
												List<PeriodMsg> periods = allPeriods;	
												for (PeriodMsg period : periods)
												{
													periodSelect.addItem(period.getPeriodName());
												}
												String periodName = periodSelect
														.getValue(periodSelect
																.getSelectedIndex());

												loadReport(
														AssignmentReportWidget.this.result,
														periodName);
											}					
										});
										
										
									}
								}
							});

				}
			}
		});
		//Handles periods changing
		periodSelect.addChangeHandler(new ChangeHandler()
		{
			@Override
			public void onChange(ChangeEvent event)
			{
				int index = assignmentSelect.getSelectedIndex() - 1;
				if (index != -1)
				{
					String periodName = periodSelect.getValue(periodSelect
							.getSelectedIndex());
					loadReport(AssignmentReportWidget.this.result, periodName);
				}
			}
		});

		this.setWidget(0, 0, assignmentSelect);
		this.setWidget(0, 1, periodSelect);
	}

	/**
	 * Loads the report widget.
	 * 
	 * @param resultList
	 *            List of UserAssignmentStatusMsg
	 * @param periodResult
	 *            String
	 */
	void loadReport(List<UserAssignmentStatusMsg> resultList,
			String periodResult)
	{
		if (this.displayWidget == null)
		{
			this.displayWidget = new AssignmentReportDisplayWidget();
			this.setWidget(2, 0, this.displayWidget);
			this.displayWidget.load(resultList, periodResult);
		} else
		{
			this.clearCell(2, 0);
			this.displayWidget = new AssignmentReportDisplayWidget();
			this.setWidget(2, 0, this.displayWidget);
			this.displayWidget.load(resultList, periodResult);
		}

	}

}
