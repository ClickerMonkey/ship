package com.wmd.client.view.instructor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.wmd.client.msg.Level;
import com.wmd.client.msg.Role;
import com.wmd.client.msg.UserMsg;
import com.wmd.client.service.SaveUpdateUserService;
import com.wmd.client.service.SaveUpdateUserServiceAsync;

/**
 * A dialog box for editing a student's information. There are fields for first
 * name, last name, user name, password, level, and period, with save and cancel
 * buttons.
 * 
 * @author Andrew Marx, Chris Koch
 * @since 2010 April 19
 * 
 */
public class EditStudentDialogBox extends DialogBox
{
	UserMsg msg;
	FlexTable table;
	int currentRow;
	TextBox lastNameTextBox, firstNameTextBox, usernameTextBox,
			passwordTextBox, periodTextBox;
	ListBox levelListBox;
	CheckBox enabledCheckbox;
	FlexTable panel;


	private static final SaveUpdateUserServiceAsync saveUserService = GWT
			.create(SaveUpdateUserService.class);


	/**
	 * Create a new dialog box for editing a student.
	 * 
	 * @param msg
	 *            The UserMsg for the student to be edited.
	 * @param tab
	 *            A table which contains a row with columns of info for the
	 *            specified student. This may be null. The columns of the table
	 *            should correspond to first name, last name, user name, period,
	 *            and level, in that order. The eighth column (index 7) should
	 *            represent an empty/nonexistent cell, where the result of edit
	 *            operations may be displayed.
	 * @param row
	 *            The row in the provided table corresponding to the student
	 *            specified.
	 */
	public EditStudentDialogBox(UserMsg msg, FlexTable tab, int row)
	{
		this.msg = msg;
		table = tab;
		currentRow = row;

		panel = new FlexTable();
		lastNameTextBox = new TextBox();
		firstNameTextBox = new TextBox();
		usernameTextBox = new TextBox();
		passwordTextBox = new TextBox();
		levelListBox = new ListBox();
		periodTextBox = new TextBox();
		enabledCheckbox = new CheckBox();

		init();
	}


	private void init()
	{

		Level levs[] = Level.values();
		for (int i = 0; i < levs.length; i++)
		{
			levelListBox.addItem(levs[i].toString());
			if (levs[i] == msg.getLevel())
				levelListBox.setSelectedIndex(i);
		}

		if (msg.getLastName() != null)
			lastNameTextBox.setText(msg.getLastName());
		if (msg.getFirstName() != null)
			firstNameTextBox.setText(msg.getFirstName());
		if (msg.getUsername() != null)
			usernameTextBox.setText(msg.getUsername());
		if (msg.getPassword() != null)
			passwordTextBox.setText(msg.getPassword());
		if (msg.getPeriod() != null)
			periodTextBox.setText(msg.getPeriod());
		enabledCheckbox.setValue(msg.isEnabled());

		final Button updateButton = new Button("Update");
		final Button cancelButton = new Button("Cancel");
		updateButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				// check that no fields are empty
				if (firstNameTextBox.getText().trim().length() == 0
						|| lastNameTextBox.getText().trim().length() == 0
						|| usernameTextBox.getText().trim().length() == 0)
				{
					Window.alert("make sure no fields are empty");
					return;
				}

				msg.setFirstName(firstNameTextBox.getText());
				msg.setLastName(lastNameTextBox.getText());
				msg.setUsername(usernameTextBox.getText());
				msg.setPassword(passwordTextBox.getText());
				msg.setPeriod(periodTextBox.getText());
				msg.setEnable(enabledCheckbox.getValue());
				msg.setRole(Role.Student);
				msg.setLevel(Level.valueOf(levelListBox
						.getItemText(levelListBox.getSelectedIndex())));

				saveUserService.saveUpdateUser(msg.getUserId(), msg
						.getFirstName(), msg.getLastName(), msg.getRole(), msg
						.getUsername(), msg.getPassword(), msg.getLevel(), msg
						.getPeriod(), msg.isEnabled(),
						new AsyncCallback<Boolean>()
						{
							@Override
							public void onFailure(Throwable caught)
							{
								notifyOnFail();
							}


							@Override
							public void onSuccess(Boolean result)
							{
								if (result)
									EditStudentDialogBox.this.updateTable();
								else
									notifyOnFail();
							}
						});
				EditStudentDialogBox.this.hide();
			}
		});
		cancelButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				EditStudentDialogBox.this.hide();
			}

		});

		panel.getFlexCellFormatter().setColSpan(0, 0, 2);
		panel.getFlexCellFormatter().setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_CENTER);

		if (msg.getFirstName() == null)
			panel.setWidget(0, 0, new HTML("<h1>Add Student:</h1>"));
		else
			panel.setWidget(0, 0, new HTML("<h1>Edit Student:<br/>"
					+ firstNameTextBox.getText() + " "
					+ lastNameTextBox.getText() + "</h1>"));

		panel.setWidget(1, 0, new HTML("First Name: "));
		panel.setWidget(2, 0, new HTML("Last Name: "));
		panel.setWidget(3, 0, new HTML("Username: "));
		panel.setWidget(4, 0, new HTML("Password: "));
		panel.setWidget(5, 0, new HTML("Level: "));
		panel.setWidget(6, 0, new HTML("Period: "));
		panel.setWidget(7, 0, new HTML("Enabled: "));

		panel.setWidget(1, 1, firstNameTextBox);
		panel.setWidget(2, 1, lastNameTextBox);
		panel.setWidget(3, 1, usernameTextBox);
		panel.setWidget(4, 1, passwordTextBox);
		panel.setWidget(5, 1, levelListBox);
		panel.setWidget(6, 1, periodTextBox);
		panel.setWidget(7, 1, enabledCheckbox);

		panel.setWidget(8, 0, cancelButton);
		panel.setWidget(8, 1, updateButton);

		panel.getFlexCellFormatter().setHorizontalAlignment(8, 1,
				HasHorizontalAlignment.ALIGN_RIGHT);
		this.setWidget(panel);

		// a handler for when the values in the first/last name text box change
		// it automatically updates the username
		ChangeHandler changeHandler = new ChangeHandler()
		{
			@Override
			public void onChange(ChangeEvent event)
			{
				String text = "";
				// first three characters from first name, to lowercase
				text += firstNameTextBox.getText().substring(0,
						Math.min(3, firstNameTextBox.getText().length()))
						.toLowerCase();

				// first five characters from last name, to lowercase
				text += lastNameTextBox.getText().substring(0,
						Math.min(5, lastNameTextBox.getText().length()))
						.toLowerCase();
				usernameTextBox.setText(text);
			}
		};

		firstNameTextBox.addChangeHandler(changeHandler);
		lastNameTextBox.addChangeHandler(changeHandler);
	}


	/**
	 * This method displays an error message in the table when an operation
	 * fails.
	 */
	void notifyOnFail()
	{
		if (table != null)
			table.setWidget(currentRow, 6, new HTML(
					"<span style=\"color:red;\"><b>Edit failed!</b></span>"));
	}


	/**
	 * This is called after the "save" button is clicked in the edit dialog box.
	 * This method updates the table provided in the constructor with the new
	 * information given.
	 */
	private void updateTable()
	{
		if (table == null)
			return;
		table.setWidget(currentRow, 0, new HTML(firstNameTextBox.getText()));
		table.setWidget(currentRow, 1, new HTML(lastNameTextBox.getText()));
		table.setWidget(currentRow, 2, new HTML(usernameTextBox.getText()));
		table.setWidget(currentRow, 3, new HTML(periodTextBox.getText()));
		table.setWidget(currentRow, 4, new HTML(levelListBox
				.getValue(levelListBox.getSelectedIndex())));
		table
				.setWidget(
						currentRow,
						7,
						new HTML(
								"<span style=\"color:red;\"><b>Successfully edited</b></span>"));
	}


	/**
	 * Overridden so that the appropriate text box automatically requests focus
	 * when this dialog box is shown.
	 */
	public void center()
	{
		super.center();
		firstNameTextBox.setFocus(true);
	}
}
