package com.wmd.client.widget.instructor;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;

public class NewAssignmentDialog extends DialogBox
{

	/*
	 * Stores the composition widget and the various buttons to accompany it.
	 */
	private FlexTable container = new FlexTable();

	public NewAssignmentDialog()
	{
		// Lock the screen with "glass"
		this.setGlassEnabled(true);

		// Set the dialog box content to the container table
		this.container.setWidth("500px");
		this.container.setHeight("250px");
		this.setWidget(this.container);

		this.setAnimationEnabled(true);
		this.setTitle("New Assignment");

		// Center the dialogbox on the screen
		this.center();
	}

}
