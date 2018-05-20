package com.wmd.client.widget.instructor;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.wmd.client.msg.ProblemStatusMsg;

/**
 * Create a list of ProblemButtons representing each problem in a problem set.
 * Sets the button to a certain size, and attaches a unique clickHandler
 * 
 * @author Chris Eby and William Fisher
 * 
 */
public class ReportProblemStatusPanel extends Composite
{

	private final int questionsPerColumn = 10;
	private final FlexTable buttonPanel;
	private final ArrayList<ReportProblemButton> buttons;

	/**
	 * Constructs a list of question buttons with unique click handlers.
	 * 
	 * @param problemPanel
	 *            - the ProblemPanel this will be displayed onto.
	 * @param userMsg - The UserMessage object of the current user.
	 */
	public ReportProblemStatusPanel()
	{
		this.buttonPanel = new FlexTable();
		this.buttons = new ArrayList<ReportProblemButton>();
		this.initWidget(this.buttonPanel);
	}

	/**
	 * Loads the buttons given a list of problem statuses.
	 * 
	 * @param problems
	 *            The list of problem statuses.
	 */
	public void load(List<ProblemStatusMsg> problems)
	{

		this.buttonPanel.clear();
		this.buttons.clear();

		// Counters for which row and column to add buttons to
		int numberOfRow = 0;
		int numberOfColumn = 0;
		int problemNumber = 1;

		// Set the button size based on the window size
		int windowSize = Window.getClientHeight();
		int buttonSize = 0;
		buttonSize = windowSize / 12;

		// Add the question buttons to the correct row and column
		for (ProblemStatusMsg status : problems)
		{
			if (numberOfRow >= this.questionsPerColumn)
			{
				numberOfColumn++;
				numberOfRow = 0;
			}

			ReportProblemButton button = new ReportProblemButton(status, problemNumber++);
			button.setSize(buttonSize + "px", buttonSize + "px");
			this.buttonPanel.setWidget(numberOfColumn,numberOfRow, button);
			this.buttons.add(button);

			numberOfRow++;
		}
	}
}