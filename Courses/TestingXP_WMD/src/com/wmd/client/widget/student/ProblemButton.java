package com.wmd.client.widget.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.wmd.client.entity.ProblemStatement;
import com.wmd.client.msg.ProblemStatusMsg;
import com.wmd.client.msg.UserMsg;
import com.wmd.client.service.GetProblemStatementService;
import com.wmd.client.service.GetProblemStatementServiceAsync;

/**
 * ProblemButton class represents a button attached to a specific Problem.
 * 
 * @author Stephen Jurnack, Kevin Rexroth, AJ Marx, and Eric C. Abruzesse
 * 
 */
public class ProblemButton extends Composite implements ClickHandler,
		AsyncCallback<ProblemStatement>
{

	private static final GetProblemStatementServiceAsync service = GWT
			.create(GetProblemStatementService.class);

	private final AbsolutePanel panel;
	private final AbsolutePanel numberLabel;
	private final AbsolutePanel triesLabel;
	private final ProblemPanel problemPanel;
	private ProblemStatusMsg problemMsg;
	private final int problemNumber;

	/**
	 * Constructor for ProblemButton.
	 * 
	 * @param problemMsg
	 * @param pPanel
	 */
	public ProblemButton(ProblemStatusMsg problemMsg, int problemNumber, UserMsg userMsg,
			ProblemPanel pPanel)
	{
		this.problemMsg = problemMsg;
		this.problemNumber = problemNumber;
		this.problemPanel = pPanel;

		triesLabel = new AbsolutePanel();
		triesLabel.setStyleName("button-tries");

		numberLabel = new AbsolutePanel();
		numberLabel.setStyleName("button-number");

		this.panel = new AbsolutePanel();
		this.panel.addStyleName("button-panel");
		this.panel.add(triesLabel);
		this.panel.add(numberLabel);
		this.addClickHandler(this);

		this.updateButton();

		initWidget(this.panel);
	}

	/**
	 * @param handler
	 *            The handler to bind to.
	 * @return a valid handler registration.
	 */
	public HandlerRegistration addClickHandler(ClickHandler handler)
	{
		return addDomHandler(handler, ClickEvent.getType());
	}

	/**
	 * Updates the color of this button based on the problem status.
	 */
	public void updateButton()
	{
		// Update image
		if (problemMsg.getTries() == 0)
		{
			panel.setStyleName("status-notries");
		} else if (problemMsg.isCorrect())
		{
			panel.setStyleName("status-right");
		} else
		{
			panel.setStyleName("status-wrong");
		}

		// Update labels
		triesLabel.clear();
		triesLabel.add(new HTML(String.valueOf(problemMsg.getTries())));

		numberLabel.clear();
		numberLabel.add(new HTML("#" + problemNumber));
	}

	/**
	 * Required method of the ClickHandler interface - called when the button is
	 * clicked.
	 */
	public void onClick(ClickEvent event)
	{
		service.getProblemStatement(this.problemMsg.getProblemId(), this);
	}

	/**
	 * Notifies the user of a communication failure.
	 */
	public void onFailure(Throwable caught)
	{
		Window.alert(caught.getMessage());
	}

	/**
	 * Occurs whenever the server responds to a request for a new problem.
	 */
	public void onSuccess(ProblemStatement result)
	{
		if (result == null)
		{
			Window
					.alert("Null problem statement. Check your internet connection.");
			return;
		}

		problemPanel.setStatement(this, result);
	}

	/**
	 * @return the details (tries, status, etc) of the problem.
	 */
	public ProblemStatusMsg getStatus()
	{
		return problemMsg;
	}

	/**
	 * Sets the status of a problem.
	 * 
	 * @param problemMsg
	 *            The status message of a problem.
	 */
	public void setStatus(ProblemStatusMsg problemMsg)
	{
		this.problemMsg = problemMsg;
		this.updateButton();
	}

	/**
	 * @return The problem number for this button
	 */
	public int getProblemNumber()
	{
		return this.problemNumber;
	}

}
