package com.wmd.client.view.instructor;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.wmd.client.msg.Level;
import com.wmd.client.msg.ProblemMsg;
import com.wmd.client.service.GetAssignmentProblemsService;
import com.wmd.client.service.GetAssignmentProblemsServiceAsync;
import com.wmd.client.view.View;
import com.wmd.client.widget.ProblemDifficultyLevel;
import com.wmd.client.widget.instructor.ProblemDialog;

/**
 * @author Eric C. Abruzzese
 * 
 */
public class InstructorAssignmentView extends View
{

	private int assignmentId;

	private static final GetAssignmentProblemsServiceAsync getProblemsService = GWT
			.create(GetAssignmentProblemsService.class);

	/**
	 * Populates the necessary fields/tables according to the assignmentId
	 * passed to this constructor.
	 * 
	 * @param assignmentId
	 *            The ID of the assignment to be loaded.
	 */
	public InstructorAssignmentView(int assignmentId)
	{
		this.assignmentId = assignmentId;

		// Create a header for the View
		super.getLayout().setWidget(0, 0,
				new HTML("<h1>Create an Assignment</h1><hr />"));

		this.createDetailFields();
		this.createProblemLevels();

	}

	/**
	 * A parameterless instructor assignment view, for creating new assignments.
	 */
	public InstructorAssignmentView()
	{
		AssignmentNameDialog newAssignmentName = new AssignmentNameDialog();
		newAssignmentName.center();
		newAssignmentName.show();
	}

	/**
	 * Creates the fields at the top for enabling/disabling an assignment
	 * (active), the name of the assignment, and the Add a Problem button.
	 */
	private void createDetailFields()
	{
		// New flextable to hold the name fields and status checkbox
		FlexTable detailFields = new FlexTable();
		detailFields.setWidth("100%");

		// Assignment name field (text)
		TextBox nameField = new TextBox();
		nameField.setText("Assignment Name");
		nameField.setWidth("100%");

		// Active checkbox
		Label enabledLabel = new Label("Active");
		CheckBox enabledCheckbox = new CheckBox();
		FlowPanel enabled = new FlowPanel();
		enabled.add(enabledLabel);
		enabled.add(enabledCheckbox);

		// Add a Problem button
		Button addProblem = new Button();
		addProblem.setText("Add a Problem");
		addProblem.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				ProblemDialog problemEditor = new ProblemDialog(assignmentId);
				problemEditor.show();
			}
		});

		/*
		 * Add the fields to the containing flex table.
		 */
		detailFields.setWidget(0, 0, enabled);
		detailFields.setWidget(0, 1, nameField);
		detailFields.setWidget(0, 2, addProblem);

		// Set alignments and widths for necessary cells.
		detailFields.getFlexCellFormatter().setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		detailFields.getFlexCellFormatter().setWidth(0, 0, (100 / 3) + "%");
		detailFields.getFlexCellFormatter().setHorizontalAlignment(0, 1,
				HasHorizontalAlignment.ALIGN_CENTER);
		detailFields.getFlexCellFormatter().setWidth(0, 1, (100 / 3) + "%");
		detailFields.getFlexCellFormatter().setHorizontalAlignment(0, 2,
				HasHorizontalAlignment.ALIGN_RIGHT);
		detailFields.getFlexCellFormatter().setWidth(0, 2, (100 / 3) + "%");

		// Add the containing flex table to the master layout manager.
		super.getLayout().setWidget(1, 0, detailFields);
	}

	/**
	 * Create the levels of difficulty to display for categorization of problems
	 * by level.
	 */
	private void createProblemLevels()
	{
		FlexTable problemLevels = new FlexTable();
		problemLevels.setWidth("100%");

		final ProblemDifficultyLevel easyList = new ProblemDifficultyLevel(
				Level.Easy);
		final ProblemDifficultyLevel mediumList = new ProblemDifficultyLevel(
				Level.Medium);
		final ProblemDifficultyLevel hardList = new ProblemDifficultyLevel(
				Level.Hard);

		getProblemsService.getAssignmentProblems(this.assignmentId,
				new AsyncCallback<List<ProblemMsg>>()
				{

					@Override
					public void onFailure(Throwable caught)
					{
						Window.alert(caught.getMessage());
					}

					@Override
					public void onSuccess(List<ProblemMsg> result)
					{
						if (result == null)
							return;
						for (int i = 0; i < result.size(); i++)
						{
							ProblemMsg problem = result.get(i);
							ProblemMsg prevProblem = null;
							ProblemMsg nextProblem = null;

							if (i > 0)
							{
								prevProblem = result.get(i - 1);
							}

							if (i < result.size() - 1)
							{
								nextProblem = result.get(i + 1);
							}

							if (problem.getLevel() == Level.Easy)
							{
								easyList.add(problem, prevProblem, nextProblem);
							}
							if (problem.getLevel() == Level.Medium)
							{
								mediumList.add(problem, prevProblem,
										nextProblem);
							}
							if (problem.getLevel() == Level.Hard)
							{
								hardList.add(problem, prevProblem, nextProblem);
							}
						}
					}

				});

		final FlexCellFormatter format = problemLevels.getFlexCellFormatter();

		problemLevels.setWidget(0, 0, easyList);
		format.setAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER,
				HasVerticalAlignment.ALIGN_TOP);
		format.setWidth(0, 0, (100 / 3) + "%");

		problemLevels.setWidget(0, 1, mediumList);
		format.setAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER,
				HasVerticalAlignment.ALIGN_TOP);
		format.setWidth(0, 1, (100 / 3) + "%");

		problemLevels.setWidget(0, 2, hardList);
		format.setAlignment(0, 2, HasHorizontalAlignment.ALIGN_CENTER,
				HasVerticalAlignment.ALIGN_TOP);
		format.setWidth(0, 2, (100 / 3) + "%");

		super.getLayout().setWidget(2, 0, problemLevels);
	}

}
