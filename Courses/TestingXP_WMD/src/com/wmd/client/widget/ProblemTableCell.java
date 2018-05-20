package com.wmd.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.wmd.client.ApplicationWrapper;
import com.wmd.client.msg.ProblemMsg;
import com.wmd.client.service.DeleteProblemService;
import com.wmd.client.service.DeleteProblemServiceAsync;
import com.wmd.client.service.SwapProblemNumbersService;
import com.wmd.client.service.SwapProblemNumbersServiceAsync;
import com.wmd.client.view.instructor.InstructorAssignmentView;
import com.wmd.client.widget.instructor.ProblemDialog;

/**
 * A widget that displays a problem in a specific difficulty level.
 * 
 * @author Eric Abruzzese
 */
public class ProblemTableCell extends Composite
{

	private ProblemMsg problem;
	private ProblemMsg prevProblem;
	private ProblemMsg nextProblem;

	private FlexTable cellLayout;

	private static final DeleteProblemServiceAsync deleteProblem = 
		GWT.create(DeleteProblemService.class);
	
	private static final SwapProblemNumbersServiceAsync swapProblemNumbersService = 
		GWT.create(SwapProblemNumbersService.class);

	/**
	 * @param problem
	 *            - The ProblemMsg that is being initialized
	 */
	public ProblemTableCell(ProblemMsg problem, ProblemMsg prevProblem,
			ProblemMsg nextProblem)
	{
		this.problem = problem;

		this.cellLayout = new FlexTable();
		this.cellLayout.setWidth("100%");
		this.cellLayout.setHTML(0, 0, "<h3>#"
				+ this.getProblem().getProblemOrder() + "</h3>");
		this.cellLayout.setHTML(0, 1, "<h3>" + this.getProblem().getName()
				+ "</h3>");
		this.cellLayout.setWidget(0, 2, this.upButton());
		this.cellLayout.setWidget(0, 3, this.downButton());
		this.cellLayout.setWidget(0, 4, this.editButton());
		this.cellLayout.setWidget(0, 5, this.deleteButton());

		initWidget(this.cellLayout);
	}

	/**
	 * @return - Returns the problem
	 */
	public ProblemMsg getProblem()
	{
		return this.problem;
	}

	/**
	 * Creates the "move this problem up one" button
	 * 
	 * @return the "Up" Button
	 */
	private Button upButton()
	{
		Button upButton = new Button("&and;");

		upButton.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				swapProblemNumbersService.swap(problem.getProblemId(),
						prevProblem.getProblemId(), new AsyncCallback<Void>()
						{
							@Override
							public void onFailure(Throwable caught)
							{
							}
							@Override
							public void onSuccess(Void result)
							{
							}
						});
			}
		});

		return upButton;
	}

	/**
	 * Creates the "move this problem down one" button
	 * 
	 * @return the "Down" Button
	 */
	private Button downButton()
	{
		Button downButton = new Button("&or;");

		downButton.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				swapProblemNumbersService.swap(problem.getProblemId(),
						nextProblem.getProblemId(), new AsyncCallback<Void>()
						{
							@Override
							public void onFailure(Throwable caught)
							{
							}
							@Override
							public void onSuccess(Void result)
							{
							}
						});
			}
		});

		return downButton;
	}

	/**
	 * Creates the "Edit this problem" button
	 * 
	 * @return the "Edit" Button
	 */
	private Button editButton()
	{
		// A nice little image
		Button editButton = new Button(
				"<img src=\"/Resources/Images/page_white_edit.png\" height=\"13\" width=\"13\" />");

		editButton.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				ProblemDialog problemEditor = new ProblemDialog(
						problem.getAssignmentId(), problem);
				problemEditor.show();
			}
		});

		return editButton;
	}
	
	private Button deleteButton()
	{
		Button deleteButton = new Button("X");
		deleteButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event)
			{
				if (Window.confirm("Are you sure you want to delete this?")) 
				{
					deleteProblem.deleteProblem(problem.getProblemId(), new AsyncCallback<Boolean>() {
						@Override
						public void onFailure(Throwable caught)
						{
							Window.alert("Error deleting problem");
						}
						@Override
						public void onSuccess(Boolean result)
						{
							if (result)
							{
								ApplicationWrapper.getInstance().setWidget(
										new InstructorAssignmentView(problem.getAssignmentId()));
							}
						}
					});
				}
			}
		});
		return deleteButton;
	}

}
