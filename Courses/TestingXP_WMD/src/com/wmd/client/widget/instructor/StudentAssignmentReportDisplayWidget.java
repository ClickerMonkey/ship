package com.wmd.client.widget.instructor;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.wmd.client.msg.ProblemStatusMsg;
import com.wmd.client.msg.UserAssignmentStatusMsg;
import com.wmd.client.service.GetProblemStatusService;
import com.wmd.client.service.GetProblemStatusServiceAsync;
/**
 * Displays the Student Assignment Report for an assignment
 * @author Christopher Eby, Bill Fisher
 *
 */
public class StudentAssignmentReportDisplayWidget extends FlexTable
{
	/**
	 * Create the service used to load the problems for the user and assignment.
	 */
	private static final GetProblemStatusServiceAsync getProblemsService = GWT
			.create(GetProblemStatusService.class);
	/**
	 * Initializes the class
	 */
	public StudentAssignmentReportDisplayWidget()
	{
		//Empty
	}
	/**
	 * Displays the grade and loads a ReportProblemStatusPanel for the assignment.
	 * @param assignment UserAssignmentStatusMsg
	 */
	public void load(UserAssignmentStatusMsg assignment)
	{
		//Display grade
		this.setText(0, 0, assignment.getAssignmentName());
		this.setText(0, 1, "Grade: " + formatPercent(assignment.getProblemsCorrect(), assignment.getProblemCount()));
		//Call to get problem status msg for assignment
		getProblemsService.getProblemStatus(assignment.getUserId(), assignment.getAssignmentId(),
				new AsyncCallback<List<ProblemStatusMsg>>()
				{
					/**
					 * Invoked when an error occurs calling the server to get
					 * the list of problem statuses.
					 */
					public void onFailure(Throwable caught)
					{
						Window.alert(caught.getMessage());
					}

					/**
					 * Invoked when the server returns a list of status messages
					 * for the given user and assignment.
					 */
					public void onSuccess(List<ProblemStatusMsg> result)
					{
						// Only load view if atleast 1 status is returned.
						if (result != null && result.size() > 0)
						{
							ReportProblemStatusPanel reportPanel = new ReportProblemStatusPanel();
							reportPanel.load(result);
							StudentAssignmentReportDisplayWidget.this.setWidget(1, 0, reportPanel);
						}
					}
				});
	}
	/**
	 * This method takes two ints, divides them, and formats them into a string of
	 * precision 2.
	 * @param numerator int
	 * @param denominator int
	 * @return String
	 */
	public String formatPercent(int number1, int number2)
	{
		double value = ((double)number1 / (double)number2)*100;
		NumberFormat.getPercentFormat();
		String percent = NumberFormat.getFormat("00.00").format(value);
		return percent;
	}
}
