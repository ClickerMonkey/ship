package com.wmd.client.view.instructor;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.wmd.client.ApplicationWrapper;
import com.wmd.client.msg.AssignmentMsg;
import com.wmd.client.service.DeleteAssignmentService;
import com.wmd.client.service.DeleteAssignmentServiceAsync;
import com.wmd.client.service.GetAssignmentsService;
import com.wmd.client.service.GetAssignmentsServiceAsync;
import com.wmd.client.view.View;

/**
 * ManageAssignmentsWidget is a Widget which displays a list of all assignments,
 * each of which has an "Edit" and "Delete" button.
 * 
 * @author Andrew Marx, Chris Koch
 * 
 */
public class ManageAssignmentsWidget extends View
{

	/**
	 * Create the service used to fetch all the problems from the database.
	 */
	private static final GetAssignmentsServiceAsync getAssignmentListService = GWT
			.create(GetAssignmentsService.class);

	private static final DeleteAssignmentServiceAsync deleteAssignmentService = GWT
			.create(DeleteAssignmentService.class);

	List<AssignmentMsg> allAssignments;

	/**
	 * Creates a new ViewAllAssignmentWidget.
	 */
	public ManageAssignmentsWidget()
	{
		this.buildTable();
	}

	/**
	 * builds the table of assignments with assignment name, edit button, delete
	 * button
	 */
	private void buildTable()
	{
		final FlexTable table = this.getLayout();

		getAssignmentListService
				.getAssignments(new AsyncCallback<List<AssignmentMsg>>()
				{
					public void onFailure(Throwable caught)
					{
						allAssignments = null;
						table.setWidget(0, 0, new HTML(
								"Error: Could not fetch assignment list."));
					}

					public void onSuccess(List<AssignmentMsg> result)
					{
						allAssignments = result;
						for (int i = 0; i < allAssignments.size(); i++)
						{
							insertRow(table, allAssignments.get(i));
						}
					}

				});

		table.setWidth("auto");
		table.setCellSpacing(8);
	}

	/**
	 * inserts an assignment name, edit button, and delete button into the table
	 * 
	 * @param table
	 *            - the table in which the row is inserted
	 * @param msg
	 *            - the assignment to insert
	 */
	private void insertRow(FlexTable table, AssignmentMsg msg)
	{
		final int assignmentId = msg.getAssignmentId();
		int currentRow = table.insertRow(table.getRowCount());
		final String title = msg.getName();
		Button editButton = new Button("Edit");
		editButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				ApplicationWrapper.getInstance().setWidget(
						new InstructorAssignmentView(assignmentId));
			}
		});
		Button deleteButton = createDeleteButtonFor(msg);

		table.setWidget(currentRow, 0, new HTML(title));
		table.getFlexCellFormatter().setWidth(currentRow, 0, "300px");
		table.setWidget(currentRow, 1, editButton);
		table.setWidget(currentRow, 2, deleteButton);
	}

	/**
	 * creates the delete button for each assignment with listeners that will
	 * delete the assignment from the database when clicked
	 * 
	 * @param msg
	 *            - contains the assignment information (name)
	 * @return - returns the delete button for that assignment
	 */
	private Button createDeleteButtonFor(AssignmentMsg msg)
	{
		Button deleteButton = new Button("Delete");
		final String title = msg.getName();
		final int assignmentId = msg.getAssignmentId();
		deleteButton.addClickHandler(new ClickHandler()
		{

			public void onClick(ClickEvent event)
			{
				String messageHTML = "Really delete this assignment? <br />"
						+ title;
				String yesText = "Yes, Delete";
				String noText = "Cancel";

				final DialogBox confirmDialog = new DialogBox();
				FlexTable buttonPanel = new FlexTable();
				Button yesButton = new Button(yesText);
				yesButton.addClickHandler(new ClickHandler()
				{
					public void onClick(ClickEvent event)
					{
						confirmDialog.hide();
						deleteAssignmentService.deleteAssignment(assignmentId,
								new AsyncCallback<Boolean>()
								{
									public void onFailure(Throwable caught)
									{
										Window.alert("Deletion failed.");
									}

									public void onSuccess(Boolean result)
									{
										ApplicationWrapper
												.getInstance()
												.setWidget(
														new ManageAssignmentsWidget());

									}

								});
					}
				});
				Button noButton = new Button(noText);
				noButton.addClickHandler(new ClickHandler()
				{
					public void onClick(ClickEvent event)
					{
						confirmDialog.hide();
					}
				});
				buttonPanel.setWidget(0, 0, yesButton);
				buttonPanel.setWidget(0, 1, noButton);
				buttonPanel.setWidth("100%");
				confirmDialog.setHTML(messageHTML);
				confirmDialog.setWidget(buttonPanel);
				confirmDialog.center();
			}
		});
		return deleteButton;
	}

}
