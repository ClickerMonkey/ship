package com.wmd.client.view.instructor;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.wmd.client.msg.AssignmentMsg;
import com.wmd.client.service.GetAssignmentsService;
import com.wmd.client.service.GetAssignmentsServiceAsync;
import com.wmd.client.view.View;

/**
 * ViewAllAssignmentsWidget is a Widget which displays a list of all
 * assignments, each of which is linkified to an "Edit Assignment" page.
 * 
 * @author Andrew Marx, Chris Koch
 * 
 */
public class ViewAllAssignmentsWidget extends View
{

	/**
	 * Create the service used to fetch all the problems from the database.
	 */
	private static final GetAssignmentsServiceAsync getAssignmentListService = GWT
			.create(GetAssignmentsService.class);

	List<AssignmentMsg> allAssignments;

	/**
	 * Creates a new ViewAllAssignmentWidget.
	 */
	public ViewAllAssignmentsWidget()
	{
		this.buildTable();
	}

	private void buildTable()
	{
		FlexTable table = this.getLayout();

		getAssignmentListService
				.getAssignments(new AsyncCallback<List<AssignmentMsg>>()
				{
					public void onFailure(Throwable caught)
					{
						allAssignments = null;
					}

					public void onSuccess(List<AssignmentMsg> result)
					{
						allAssignments = result;
					}

				});

		if (allAssignments != null)
		{
			int currentRow;
			for (int i = 0; i < allAssignments.size(); i++)
			{
				currentRow = table.insertRow(i);
				table.addCell(currentRow);
				table.setWidget(currentRow, 0, new HTML(allAssignments.get(i)
						.getName()));
			}
		} else
		{
			// table.insertRow(0);
			// table.addCell(0);
			table.setWidget(0, 0, new HTML(
					"Error: Could not fetch assignment list."));
		}
	}
}
