package com.wmd.client.view.instructor;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.wmd.client.ApplicationWrapper;
import com.wmd.client.msg.Role;
import com.wmd.client.msg.UserMsg;
import com.wmd.client.service.DeleteUserService;
import com.wmd.client.service.DeleteUserServiceAsync;
import com.wmd.client.service.GetAllUsersService;
import com.wmd.client.service.GetAllUsersServiceAsync;
import com.wmd.client.view.View;

/**
 * StudentManagementView is a Widget that displays every student for that
 * instructor each of which has an "Edit" and "Delete" button.
 * 
 * @author Andrew Marx, Chris Koch
 * 
 */
public class StudentManagementView extends View
{

	/**
	 * Create the service used to fetch all the students from the database.
	 */
	private static final GetAllUsersServiceAsync getAllUsersService = GWT
			.create(GetAllUsersService.class);
	private static final DeleteUserServiceAsync deleteUserService = GWT
			.create(DeleteUserService.class);

	List<UserMsg> allStudents;

	/**
	 * Creates a new InstructorManagementView.
	 */
	public StudentManagementView()
	{
		this.buildTable();
	}

	/**
	 * builds the table of instructors with the last name, first name, username,
	 * password, level, period, edit button, and delete button for each row.
	 */
	private void buildTable()
	{
		final FlexTable table = this.getLayout();
		table.getColumnFormatter().setWidth(0, "20%");
		table.getColumnFormatter().setWidth(1, "20%");
		table.getColumnFormatter().setWidth(2, "15%");
		table.getColumnFormatter().setWidth(3, "5%");
		table.getColumnFormatter().setWidth(4, "5%");
		table.getColumnFormatter().setWidth(5, "5%");
		table.getColumnFormatter().setWidth(6, "3%");
		createAddButton(table);
		createTableTitles(table);

		getAllUsersService.getAllUsers(Role.Student, new AsyncCallback<ArrayList<UserMsg>>()
		{
			public void onFailure(Throwable caught)
			{
				allStudents = null;
				table.setWidget(0, 1, new HTML("Error: Could not fetch student list."));
			}

			public void onSuccess(ArrayList<UserMsg> result)
			{
				allStudents = result;
				if(allStudents == null) {
					table.setWidget(0, 1, new HTML("There are no students."));
				} else {
					for (int i = 0; i < allStudents.size(); i++)
					{
						insertRow(table, allStudents.get(i));
					}
				}
			}

		});

		table.setWidth("auto");
		table.setCellSpacing(8);
	}

	private void createTableTitles(FlexTable table)
	{
		table.setWidget(1, 0, new HTML("<b>First Name</b>"));
		table.setWidget(1, 1, new HTML("<b>Last Name</b>"));
		table.setWidget(1, 2, new HTML("<b>Username</b>"));
		table.setWidget(1, 3, new HTML("<b>Period</b>"));
		table.setWidget(1, 4, new HTML("<b>Level</b>"));
		
	}

	private void createAddButton(final FlexTable table)
	{
		Button addStudentButton = new Button("Add Student");
		table.getFlexCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		table.setWidget(0,0, addStudentButton);
		addStudentButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				int newRow = table.insertRow(table.getRowCount());
				UserMsg newUser = new UserMsg();
				newUser.setUserId(-1);
				new EditStudentDialogBox(newUser, table, newRow).center();
			}

		});
	}

	/**
	 * inserts an username, last name, first name, password, level, period, edit
	 * button, and delete button into the table
	 * 
	 * @param table
	 *            - the table in which the row is inserted
	 * @param msg
	 *            - the assignment to insert
	 */
	private void insertRow(final FlexTable table, final UserMsg msg)
	{
		//final int userId = msg.getUserId();
		final int currentRow = table.insertRow(table.getRowCount());
		final String lastName = msg.getLastName();
		final String firstName = msg.getFirstName();
		final String username = msg.getUsername();
		//final String password = msg.getPassword();
		final String level = msg.getLevel().toString();
		final String period = msg.getPeriod();
		final HTML statusLabel = new HTML("");
		statusLabel.setVisible(false);
		Button editButton = new Button("Edit");
		editButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				new EditStudentDialogBox(msg, table, currentRow).center();
			}

		});
		Button deleteButton = createDeleteButtonFor(table, currentRow, msg);

		table.setWidget(currentRow, 0, new HTML(firstName));
		table.setWidget(currentRow, 1, new HTML(lastName));
		table.setWidget(currentRow, 2, new HTML(username));
		table.setWidget(currentRow, 3, new HTML(period));
		table.setWidget(currentRow, 4, new HTML(level));
		table.setWidget(currentRow, 5, editButton);
		table.setWidget(currentRow, 6, deleteButton);
		table.setWidget(currentRow, 7, statusLabel);
	}

	/**
	 * creates the delete button for each student with listeners that will
	 * delete the student from the database when clicked
	 * 
	 * @param msg
	 *            - contains the student information (name)
	 * @return - returns the delete button for that student
	 */
	private Button createDeleteButtonFor(final FlexTable table, final int currentRow, UserMsg msg)
	{
		Button deleteButton = new Button("Delete");
		final String studentName = msg.getFirstName() + " " + msg.getLastName();
		final int userId = msg.getUserId();
		deleteButton.addClickHandler(new ClickHandler()
		{

			public void onClick(ClickEvent event)
			{
				String messageHTML = "Are you sure you want to delete <br /><b>" + studentName+"</b>?";
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
						deleteUserService.deleteUser(userId, new AsyncCallback<Boolean>()
						{
							public void onFailure(Throwable caught)
							{
								table
								.setWidget(
										currentRow,
										6,
										new HTML(
												"<span style=\"color:red;\"><b>Deletion failed</b></span>"));
							}

							public void onSuccess(Boolean result)
							{
								ApplicationWrapper.getInstance().setWidget(
										new StudentManagementView());

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
				buttonPanel.setWidget(0, 0, new HTML("<br/>"));
				buttonPanel.setWidget(1, 0, noButton);
				buttonPanel.setWidget(1, 1, yesButton);
				buttonPanel.setWidth("100%");
				confirmDialog.setHTML(messageHTML);
				confirmDialog.setWidget(buttonPanel);
				confirmDialog.center();
			}
		});
		return deleteButton;
	}

}
