package com.wmd.client.widget.instructor;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.wmd.client.msg.ProblemStatusMsg;

/**
 * ProblemButton class represents a button attached to a specific Problem.
 * 
 * @author Chris Eby, William Fsher
 * 
 */
public class ReportProblemButton extends Composite
{

	private final AbsolutePanel panel;
	private final AbsolutePanel numberLabel;
	private final AbsolutePanel triesLabel;
	private ProblemStatusMsg problemStatusMsg;
	private final int problemNumber;

	/**
	 * Constructor for ProblemButton.
	 * 
	 * @param problemMsg
	 * @param pPanel
	 */
	public ReportProblemButton(ProblemStatusMsg problemMsg, int problemNumber)
	{
		this.problemNumber = problemNumber;
		this.problemStatusMsg = problemMsg;

		this.triesLabel = new AbsolutePanel();
		this.triesLabel.setStyleName("button-tries");

		this.numberLabel = new AbsolutePanel();
		this.numberLabel.setStyleName("button-number");

		this.panel = new AbsolutePanel();
		this.panel.addStyleName("button-panel");
		this.panel.add(this.triesLabel);
		this.panel.add(this.numberLabel);

		this.updateButton();

		initWidget(this.panel);
	}

	/**
	 * Updates the color of this button based on the problem status.
	 */
	public void updateButton()
	{
		// Update image
		if (this.problemStatusMsg.getTries() == 0)
		{
			this.panel.setStyleName("status-notries");
		} else if (this.problemStatusMsg.isCorrect())
		{
			this.panel.setStyleName("status-right");
		} else
		{
			this.panel.setStyleName("status-wrong");
		}

		// Update labels
		this.triesLabel.clear();
		this.triesLabel.add(new HTML(String.valueOf(this.problemStatusMsg.getTries())));

		this.numberLabel.clear();
		this.numberLabel.add(new HTML("#" + this.problemNumber));
	}

}
