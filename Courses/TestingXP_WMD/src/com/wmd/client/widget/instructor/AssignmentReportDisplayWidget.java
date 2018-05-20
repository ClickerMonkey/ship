package com.wmd.client.widget.instructor;

import java.util.List;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.wmd.client.msg.UserAssignmentStatusMsg;
/**
 * Displays the grades for AssignmentReportWidget.
 * @author Christopher Eby and William Fisher
 *
 */
public class AssignmentReportDisplayWidget extends FlexTable
{
	/**
	 * Empty Constructor.
	 */
	public AssignmentReportDisplayWidget()
	{
		//Empty
	}
	/**
	 * Loads the student info into the widget.
	 * @param usersInfo List of UserAssignmentStatusMsg
	 * @param periodName String
	 */
	public void load(List<UserAssignmentStatusMsg> usersInfo, String periodName)
	{
		int currentRow = 2;
		if(usersInfo.size() != 0)
		{
			String assignTitle = "Assignment: " + usersInfo.get(0).getAssignmentName();
			this.setText(0, 0, assignTitle);
			this.setText(1, 0, "Student");
			this.setText(1, 1, "% Complete");
			this.setText(1, 2, "% Correct");
			this.setText(1, 3, "Grade");
		for(UserAssignmentStatusMsg user : usersInfo)
		{
			if(user.getPeriod().compareTo(periodName) == 0)
			{
				this.setText(currentRow, 0, user.getLastName() + ", " + user.getFirstName());
				this.setText(currentRow, 1, formatPercent(user.getProblemsTried(), user.getProblemCount()));
				this.setText(currentRow, 2, formatPercent(user.getProblemsCorrect(),user.getProblemsTried()));
				this.setText(currentRow, 3, formatPercent(user.getProblemsCorrect(),user.getProblemCount()));
				currentRow++;
			}
		}
		}
	}
	/**
	 * This method takes two ints, divides them, and formats them into a string of
	 * precision 2.
	 * @param numerator int
	 * @param denominator int
	 * @return String
	 */
	public String formatPercent(int numerator, int denominator)
	{
		String percent = "";
		if (denominator != 0)
		{
			double value = ((double)numerator / (double)denominator)*100;
			NumberFormat.getPercentFormat();
			percent = NumberFormat.getFormat("00.00").format(value);
		}
		else
		{
			percent = "00.00";
		}
		return percent;
	}

}
