package com.wmd.client.widget.student;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.wmd.client.msg.ProblemStatusMsg;
import com.wmd.client.msg.UserMsg;

/**
 * Create a list of ProblemButtons representing each problem in a problem set.
 * Sets the button to a certain size, and attaches a unique clickHandler
 * 
 * @author Stephen Jurnack and Kevin Rexroth and Eric C. Abruzzese
 * 
 *         Modified to display all questions at once.
 * @author Stephen Jurnack and Kevin Rexroth
 * 
 */
public class ProblemStatusPanel extends Composite
{

	private final int questionsPerColumn = 10;
	private final ProblemPanel problemPanel;
	private final FlexTable buttonPanel;
	private final ArrayList<ProblemButton> buttons;
	private final UserMsg userMsg;

	/**
	 * Constructs a list of question buttons with unique click handlers.
	 * 
	 * @param problemPanel
	 *            - the ProblemPanel this will be displayed onto.
	 * @param userMsg - The UserMessage object of the current user.
	 */
	public ProblemStatusPanel(ProblemPanel problemPanel, UserMsg userMsg)
	{
		this.problemPanel = problemPanel;
		this.buttonPanel = new FlexTable();
		this.buttons = new ArrayList<ProblemButton>();
		this.userMsg = userMsg;
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

			ProblemButton button = new ProblemButton(status, problemNumber++, this.userMsg,
					this.problemPanel);
			button.setSize(buttonSize + "px", buttonSize + "px");
			this.buttonPanel.setWidget(numberOfRow, numberOfColumn, button);
			this.buttons.add(button);

			numberOfRow++;
		}
	}

	/**
	 * Gets the corresponding button to the problem number.
	 * 
	 * @param problemNumber
	 *            The problem number.
	 * @return The associated button.
	 */
	public ProblemButton getButton(int problemNumber)
	{
		for (ProblemButton button : this.buttons)
		{
			if (button.getProblemNumber() == problemNumber)
			{
				return button;
			}
		}
		return null;
	}

}