package com.wmd.client.view.instructor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.wmd.client.ApplicationWrapper;
import com.wmd.client.msg.AssignmentMsg;
import com.wmd.client.service.AddAssignmentService;
import com.wmd.client.service.AddAssignmentServiceAsync;

/**
 * AssignmentNameDialog
 * 
 * This createss a dialog box for the user to enter a name for the assignment
 * thatt is being created.
 * 
 * @author Eric C. Abruzzese
 * 
 */
public class AssignmentNameDialog extends DialogBox
{

	/*
	 * The panel containing the information.
	 */
	AbsolutePanel infoPanel = new AbsolutePanel();

	/*
	 * The text input and button for the submission of the form.
	 */
	TextBox assignmentNameInput = new TextBox();
	Button setNameButton = new Button();

	/*
	 * Service call handler.
	 */
	private static final AddAssignmentServiceAsync addAssignmentService = GWT
			.create(AddAssignmentService.class);

	/**
	 * Create the dialog box
	 */
	public AssignmentNameDialog()
	{
		// Make it pretty
		this.setGlassEnabled(true);
		this.setAnimationEnabled(true);

		// Add the prompt text
		infoPanel.add(new HTML("<h2>New Assignment</h2>"));
		infoPanel
				.add(new HTML(
						"<br />Please enter a name for your new assignment below.<br /><br />"));

		// Width and height
		infoPanel.setWidth("300px");
		infoPanel.setHeight("200px");

		// Add the text box for the assignment name input
		infoPanel.add(this.assignmentNameInput);

		// Create the submit button
		this.setNameButton.setText("Add Assignment");
		setNameButton.addClickHandler(new ClickHandler()
		{
			//Added 'true' so it would match the refactored arguments
			//of AddAssignmentService
			//-Kevin Rexroth Wednesday 4/21/10 6:46pm
			@Override
			public void onClick(ClickEvent event)
			{
				addAssignmentService.addAssignment(assignmentNameInput
						.getValue(), true, new AsyncCallback<AssignmentMsg>()
				{

					@Override
					public void onFailure(Throwable caught)
					{
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(AssignmentMsg result)
					{
						// Set the view to the assignment, and close the dialog
						// box.
						ApplicationWrapper.getInstance().setWidget(
								new InstructorAssignmentView(result
										.getAssignmentId()));
						hide();
					}

				});
			}

		});
		infoPanel.add(setNameButton);

		// Add the info and form elements to the dialogbox.
		this.setWidget(infoPanel);
	}

}
