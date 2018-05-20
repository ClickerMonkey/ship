package com.wmd.client.view.student;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.wmd.client.msg.ProblemStatusMsg;
import com.wmd.client.msg.UserMsg;
import com.wmd.client.service.GetProblemStatusService;
import com.wmd.client.service.GetProblemStatusServiceAsync;
import com.wmd.client.service.UpdateProblemStatusService;
import com.wmd.client.service.UpdateProblemStatusServiceAsync;
import com.wmd.client.view.View;
import com.wmd.client.widget.LoadingWidget;
import com.wmd.client.widget.student.AnswerPanel;
import com.wmd.client.widget.student.ProblemButton;
import com.wmd.client.widget.student.ProblemPanel;
import com.wmd.client.widget.student.ProblemStatusPanel;

/**
 * StudentView
 * 
 * @author Eric C. Abruzzese, Philip M. Diffenderfer
 * 
 *         The StudentView is simply the structural layout for what the student
 *         sees after logging in. This structure will be a FlexTable with other
 *         custom widgets inside of it.
 * 
 */
public class StudentView extends View implements ClickHandler
{

	/**
	 * Create the service used to load the problems for the user and assignment.
	 */
	private static final GetProblemStatusServiceAsync getStatusService = GWT
			.create(GetProblemStatusService.class);

	/**
	 * Create the service used to update the status of a problem
	 */
	private static final UpdateProblemStatusServiceAsync updateStatusService = GWT
			.create(UpdateProblemStatusService.class);

	// The id of the user taking the assignment.
	private final UserMsg user;

	// The id of the assignment being taken by the user.
	private final int assignment;

	// The panel that holds the problem status buttons.
	private final ProblemStatusPanel problemStatusPanel;

	// The panel containing the problem and question panel.
	private final ProblemPanel problemPanel;

	// The button used to check if their answer is correct.
	private final Button answerButton;

	// The label used to display the assignment status. The assignment status
	// is the number of answers correct over the number of total questions.
	private final HTML statusLabel;

	// The number of correct problems answered.
	private int correctCount;

	// The number of problems in the assignment.
	private int problemCount;

	// A dialog used to block the screen of input and display a message to the
	// user.
	private final LoadingWidget loadingWidget;

	/**
	 * Loads an assignment into the student view.
	 * 
	 * @param user
	 *            The ID of the user taking the assignment.
	 * @param assignment
	 *            The ID of the assignment being loaded.
	 */
	public StudentView(UserMsg user, int assignment)
	{
		this.user = user;
		this.assignment = assignment;

		loadLayout();
		// Creates the problem panel which holds the answer and question panels.
		problemPanel = new ProblemPanel();
		super.getLayout().setWidget(0, 1, problemPanel);

		// Holds problem status
		problemStatusPanel = new ProblemStatusPanel(problemPanel, user);
		problemStatusPanel.setHeight("125px");
		super.getLayout().setWidget(0, 0, problemStatusPanel);

		// Hold assignment status
		statusLabel = new HTML();
		statusLabel.setHTML("<h1>0/0</h1>");
		super.getLayout().setWidget(1, 0, statusLabel);

		// The button to answer a question.
		answerButton = new Button();
		answerButton.setHTML("<h1>Check My Answer</h1>");
		answerButton.addClickHandler(this);
		super.getLayout().setWidget(1, 1, answerButton);

		// The loading dialog
		loadingWidget = new LoadingWidget("Loading...");

		// Call the server to get the problem statuses
		getStatusService.getProblemStatus(user.getUserId(), assignment,
				new AsyncCallback<List<ProblemStatusMsg>>()
				{
					/**
					 * Invoked when an error occurs calling the server to get
					 * the list of problem statuses.
					 */
					public void onFailure(Throwable caught)
					{
						Window.alert(caught.getMessage());
					}

					/**
					 * Invoked when the server returns a list of status messages
					 * for the given user and assignment.
					 */
					public void onSuccess(List<ProblemStatusMsg> result)
					{
						// Only load view if atleast 1 status is returned.
						if (result != null && result.size() > 0)
						{
							loadView(result);
						}
					}
				});
	}

	/**
	 * Loads the layout of the StudentView.
	 */
	public void loadLayout()
	{
		final FlexTable layout = super.getLayout();
		final FlexCellFormatter format = super.getFormatter();

		layout.setWidth("100%");
		layout.setHeight("100%");
		layout.setCellPadding(0);
		layout.setCellSpacing(0);

		// ProblemStatusPanel
		format.setHeight(0, 0, "125px");
		format.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		format.setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);

		// Problem Panel
		format.setWidth(0, 1, "100%");
		format.setHeight(0, 1, "100%");
		format
				.setHorizontalAlignment(0, 1,
						HasHorizontalAlignment.ALIGN_CENTER);
		format.setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_MIDDLE);

		// Status Bar Cell
		layout.setWidget(1, 0, null);
		format.setHeight(1, 0, "70px");
		format.getElement(1, 0).setClassName("statusBar");
		format
				.setHorizontalAlignment(1, 0,
						HasHorizontalAlignment.ALIGN_CENTER);
		format.setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_MIDDLE);

		// Check Answer Button
		format.setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		format.setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		format.getElement(1, 1).setClassName("statusBar");
	}

	/**
	 * Loads the view based on the list of problem statuses returned from the
	 * server.
	 * 
	 * @param result
	 *            The list of problem statuses.
	 */
	public void loadView(List<ProblemStatusMsg> result)
	{
		// Update problem status buttons
		problemStatusPanel.load(result);

		// Update status label
		problemCount = result.size();
		correctCount = 0;
		for (ProblemStatusMsg status : result)
		{
			if (status.isCorrect())
			{
				correctCount++;
			}
		}
		updateAssignmentStatus();
	}

	/**
	 * Updates the assignment status label.
	 */
	public void updateAssignmentStatus()
	{
		statusLabel.setHTML("<h1>" + correctCount + "/" + problemCount
				+ "</h1>");
	}

	/**
	 * Invoked when the answer button is clicked. This checks the answer panel
	 * for correctness.
	 */
	public void onClick(ClickEvent event)
	{
		// If the problem is already answered correct then ignore
		if (problemPanel.getSource().getStatus().isCorrect())
		{
			return;
		}

		AnswerPanel answer = problemPanel.getAnswerPanel();
		if (answer.getAnswer() == null)
		{
			Window.alert("No problem to check");
		} else
		{
			boolean correct = answer.isCorrect();

			// 'Disable' input to user.
			loadingWidget.show();

			// Get the source status
			final ProblemButton source = problemPanel.getSource();
			ProblemStatusMsg oldStatus = source.getStatus();

			// Call the server to update the problem status
			updateStatusService.updateStatus(oldStatus.getUserId(),
					oldStatus.getProblemId(), correct,
					new AsyncCallback<ProblemStatusMsg>()
					{
						/**
						 * Invoked when an error occurs calling the server to
						 * update a problem status
						 */
						public void onFailure(Throwable caught)
						{
							Window.alert(caught.getMessage());
						}

						/**
						 * 
						 * @param result
						 */
						public void onSuccess(ProblemStatusMsg result)
						{
							source.setStatus(result);

							if (result.isCorrect())
							{
								correctCount++;
								updateAssignmentStatus();
							}

							loadingWidget.hide();
						}
					});

			// if (correct) {
			// Window.alert("Correct!");
			// }
			// else {
			// Window.alert("Incorrect");
			// }
		}
	}

	/**
	 * @return the problem status panel.
	 */
	public ProblemStatusPanel getProblemSetPanel()
	{
		return problemStatusPanel;
	}

	/**
	 * @return the problem panel.
	 */
	public ProblemPanel getProblemPanel()
	{
		return problemPanel;
	}

	/**
	 * @return the user ID.
	 */
	public UserMsg getUser()
	{
		return user;
	}

	/**
	 * @return the assignment ID.
	 */
	public int getAssignment()
	{
		return assignment;
	}

	/**
	 * This method should call the widget that load the problems for a given
	 * assignment Added to allowed compilation
	 * 
	 * @param assignmentId
	 *            The id of the assignment
	 */

	public void loadAssignment(int assignmentId)
	{
		// TODO Auto-generated method stub

	}

}
