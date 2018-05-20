package com.wmd.client.view.instructor;

import java.util.ArrayList;
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
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.wmd.client.ApplicationWrapper;
import com.wmd.client.msg.Role;
import com.wmd.client.msg.UserMsg;
import com.wmd.client.service.DeleteUserService;
import com.wmd.client.service.DeleteUserServiceAsync;
import com.wmd.client.service.GetAllUsersService;
import com.wmd.client.service.GetAllUsersServiceAsync;
import com.wmd.client.service.UpdatePasswordService;
import com.wmd.client.service.UpdatePasswordServiceAsync;
import com.wmd.client.view.View;

/**
 * InstructorManagementView is a Widget that displays every instructor each of
 * which has an "Edit" and "Delete" button.
 * 
 * @author Andrew Marx, Chris Koch
 * 
 */
public class InstructorManagementView extends View
{

	/**
	 * Create the service used to fetch all the instructors from the database.
	 */
	private static final GetAllUsersServiceAsync getAllUsersService = GWT
			.create(GetAllUsersService.class);
	private static final DeleteUserServiceAsync deleteUserService = GWT
			.create(DeleteUserService.class);
	private static final UpdatePasswordServiceAsync updatePassword = GWT
			.create(UpdatePasswordService.class);
	
	List<UserMsg> allInstructors;

	/**
	 * Creates a new InstructorManagementView.
	 */
	public InstructorManagementView()
	{
		this.buildTable();
	}

	/**
	 * builds the table of instructors with the last name, first name, username,
	 * reset password button, edit button, and delete button for each row.
	 */
	private void buildTable()
	{
		final FlexTable table = this.getLayout();
		table.getColumnFormatter().setWidth(0, "20%");
		table.getColumnFormatter().setWidth(1, "20%");
		table.getColumnFormatter().setWidth(2, "20%");
		table.getColumnFormatter().setWidth(3, "5%");
		table.getColumnFormatter().setWidth(4, "5%");
		table.getColumnFormatter().setWidth(5, "5%");
		createAddButton(table);
		createTableTitles(table);

		getAllUsersService.getAllUsers(Role.Instructor, new AsyncCallback<ArrayList<UserMsg>>()
		{
			public void onFailure(Throwable caught)
			{
				allInstructors = null;
				table.setWidget(0, 1, new HTML("Error: Could not fetch instructor list."));
			}

			public void onSuccess(ArrayList<UserMsg> result)
			{
				allInstructors = result;
				if (allInstructors == null)
				{
					table.setWidget(0, 1, new HTML("There are no instructors."));
				} else
				{
					for (int i = 0; i < allInstructors.size(); i++)
					{
						insertRow(table, allInstructors.get(i));
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
	}

	/**
	 * inserts an username, last name, first name, reset password button, edit
	 * button, and delete button into the table
	 * 
	 * @param table
	 *            - the table in which the row is inserted
	 * @param msg
	 *            - the assignment to insert
	 */
	private void insertRow(final FlexTable table, final UserMsg msg)
	{
		// final int userId = msg.getUserId();
		final int currentRow = table.insertRow(table.getRowCount());
		final String lastName = msg.getLastName();
		final String firstName = msg.getFirstName();
		final String username = msg.getUsername();
		final HTML statusLabel = new HTML("");
		statusLabel.setVisible(false);
		Button editButton = new Button("Edit");
		editButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				new EditInstructorDialogBox(msg, table, currentRow).center();
				// createEditDialogBox(msg, table, currentRow, statusLabel);
			}
		});
		Button deleteButton = createDeleteButtonFor(msg);
		Button resetPasswordButton = createResetPasswordButtonFor(msg, statusLabel);

		table.setWidget(currentRow, 0, new HTML(firstName));
		table.setWidget(currentRow, 1, new HTML(lastName));
		table.setWidget(currentRow, 2, new HTML(username));
		table.getFlexCellFormatter().setWidth(currentRow, 0, "300px");
		table.setWidget(currentRow, 3, editButton);
		table.setWidget(currentRow, 4, resetPasswordButton);
		table.setWidget(currentRow, 5, deleteButton);
		table.setWidget(currentRow, 6, statusLabel);

	}

	private void createAddButton(final FlexTable table)
	{
		Button addStudentButton = new Button("Add Instructor");
		table.getFlexCellFormatter().setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		table.setWidget(0, 0, addStudentButton);
		addStudentButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				int newRow = table.insertRow(table.getRowCount());
				UserMsg newUser = new UserMsg();
				newUser.setUserId(-1);
				new EditInstructorDialogBox(newUser, table, newRow).center();
			}

		});
	}

	/**
	 * creates the delete button for each instructor with listeners that will
	 * delete the instructor from the database when clicked
	 * 
	 * @param msg
	 *            - contains the instructor information (name)
	 * @return - returns the delete button for that instructor
	 */
	private Button createDeleteButtonFor(UserMsg msg)
	{
		Button deleteButton = new Button("Delete");
		final String instructorName = msg.getFirstName() + " " + msg.getLastName();
		final int userId = msg.getUserId();
		deleteButton.addClickHandler(new ClickHandler()
		{

			public void onClick(ClickEvent event)
			{
				String messageHTML = "Are you sure you want to delete <br /><b>" + instructorName
						+ "</b>?";
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
								Window.alert("Deletion failed.");
							}

							public void onSuccess(Boolean result)
							{
								ApplicationWrapper.getInstance().setWidget(
										new InstructorManagementView());

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

	/**
	 * creates the reset password button for each instructor
	 * 
	 * @param msg
	 *            - contains the instructor information
	 * @param statusLabel
	 *            - the last column in the table, indicates success or failure
	 *            for the instructor
	 * @return - returns the reset password button for that instructor
	 */
	private Button createResetPasswordButtonFor(final UserMsg msg, final HTML statusLabel)
	{
		Button resetPasswordButton = new Button("Reset Password");
		resetPasswordButton.setWidth("130px");
		// final int userId = msg.getUserId();
		final String instructorName = "<b>" + msg.getFirstName() + " " + msg.getLastName() + "</b>";
		resetPasswordButton.addClickHandler(new ClickHandler()
		{

			public void onClick(ClickEvent event)
			{
				String messageHTML = "Are you sure you want to reset<br/><b>" + instructorName
						+ "</b>'s password?";
				String yesText = "Yes, Reset";
				String noText = "Cancel";

				final DialogBox confirmDialog = new DialogBox();
				FlexTable buttonPanel = new FlexTable();
				Button yesButton = new Button(yesText);
				yesButton.addClickHandler(new ClickHandler()
				{
					public void onClick(ClickEvent event)
					{
						msg.setPassword("");
						updatePassword.updateUserPassword("", msg.getUserId(), new AsyncCallback<Boolean>() {
							@Override
							public void onFailure(Throwable caught)
							{
								statusLabel.setHTML("<span style=\"color:red;\"><b>Error reseting password</b></span>");
								statusLabel.setVisible(true);		
							}
							@Override
							public void onSuccess(Boolean result)
							{
								statusLabel.setHTML("<span style=\"color:red;\"><b>Password reset</b></span>");
								statusLabel.setVisible(true);								
							}
						});
						confirmDialog.hide();
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
		return resetPasswordButton;
	}

}
