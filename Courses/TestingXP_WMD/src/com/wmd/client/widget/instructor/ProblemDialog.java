package com.wmd.client.widget.instructor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.wmd.client.ApplicationWrapper;
import com.wmd.client.editor.*;
import com.wmd.client.entity.Answer;
import com.wmd.client.entity.EntityContainer;
import com.wmd.client.entity.ProblemStatement;
import com.wmd.client.entity.Question;
import com.wmd.client.entity.Symbol;
import com.wmd.client.msg.Level;
import com.wmd.client.msg.ProblemMsg;
import com.wmd.client.service.GetProblemStatementService;
import com.wmd.client.service.GetProblemStatementServiceAsync;
import com.wmd.client.service.SaveProblemService;
import com.wmd.client.service.SaveProblemServiceAsync;
import com.wmd.client.view.instructor.InstructorAssignmentView;

/**
 * Create a DialogBox to display the problem editor.
 * 
 * @author Eric C. Abruzzese and Steve Jurnack
 * 
 */
public class ProblemDialog extends DialogBox implements EditorListener
{
	/*
	 * Stores the composition widget and the various buttons to accompany it.
	 */
	private FlexTable container = new FlexTable();

	private Editor problemComposer = new Editor();
	private Editor answerComposer = new Editor();
	
	private ListBox levelBox;
	private TextBox nameBox;
	
	private ProblemMsg problem;

	private static final SaveProblemServiceAsync saveProblemService = GWT
			.create(SaveProblemService.class);
	private static final GetProblemStatementServiceAsync getProblemStatementService = GWT
	.create(GetProblemStatementService.class);

	/**
	 * Creates a dialog to edit a problem.
	 */
	public ProblemDialog(int assignmentId, ProblemMsg problem)
	{
		this.problem = problem;
		this.problem.setAssignmentId(assignmentId);
		this.initialize();
		
		this.levelBox.setSelectedIndex(problem.getLevel().ordinal());
		this.nameBox.setText(problem.getName());
		
		getProblemStatementService.getProblemStatement(problem.getProblemId(), new AsyncCallback<ProblemStatement>(){
			@Override
			public void onFailure(Throwable caught)
			{
				Window.alert(caught.getMessage());
			}
			@Override
			public void onSuccess(ProblemStatement result)
			{
				setProblemStatement(result);
			}
		});
	}

	/**
	 * Creates a dialog to create a problem.
	 */
	public ProblemDialog(int assignmentId)
	{
		this.problem = new ProblemMsg();
		this.problem.setProblemId(-1);
		this.problem.setProblemOrder(-1);
		this.problem.setAssignmentId(assignmentId);
		this.initialize();
	}

	/**
	 * Initializes the common attributes of the dialogbox for use 
	 * with the overloaded constructors.
	 */
	private void initialize()
	{
		// Lock the screen with "glass" and turn on animation
		this.setGlassEnabled(true);
		this.setAnimationEnabled(true);

		problemComposer.addListener(this);
		answerComposer.addListener(this);
		
		// Set the dialog box content to the container table
		this.container.setWidth("500px");
		this.container.setHeight("250px");
		this.setWidget(this.container);

		this.setHTML("<h2>Editor</h2>");
		
		this.addComposeWidget();

		// Center the dialogbox on the screen
		this.center();
	}
	
	/**
	 * Sets the problem statement in the editor.
	 * 
	 * @param statement The statement to set.
	 */
	private void setProblemStatement(ProblemStatement statement)
	{
		if (statement != null)
		{
			problemComposer.fromEntity(statement.getQuestion());
			problemComposer.onTokenAdded(problemComposer, null);
			answerComposer.fromEntity(statement.getAnswer());
			answerComposer.onTokenAdded(answerComposer, null);	
		}
	}
	
	/**
	 * Adds the composer widgets to the layout flextable.
	 */
	private void addComposeWidget()
	{
		//Create a textbox for the problem name
		nameBox = new TextBox();
		nameBox.setWidth("98%");
		nameBox.setText("Problem Name");
		this.container.setWidget(0, 0, nameBox);
		
		//Create a level drop down
		levelBox = new ListBox();
		for(Level l : Level.values())
		{
			levelBox.addItem(l.toString(), l.toString());
		}
		
		this.container.setWidget(0, 1, levelBox);
		
		//Add the problem composition
		this.container.setWidget(1, 0, new HTML("<h2>Problem Composer</h2><hr />"));
		this.container.setWidget(2, 0, this.problemComposer);
		
		//Add the answer composition
		this.container.setWidget(3, 0, new HTML("<br /><h2>Answer Composer</h2><hr />"));
		this.container.setWidget(4, 0, this.answerComposer);
		
		//Add the editor toolbar
		this.container.setWidget(1, 1, this.createEditorToolbar());
		this.container.getFlexCellFormatter().setRowSpan(1, 1, 4);
		this.container.getFlexCellFormatter().setWidth(0, 1, "75px");
		
		//Add the save,cancel button
		this.container.setWidget(5, 0, this.saveButton());
		this.container.setWidget(5, 1, this.cancelButton());
	}
	
	/**
	 * Creates the editor toolbar to add entities to the editor.
	 * 
	 * @return 
	 */
	private FlexTable createEditorToolbar()
	{
		//Create the entity buttons
		Button integer = new Button();
		integer.setHTML("<h3>1</h3>");
		integer.setWidth("35px");
		integer.setHeight("35px");
		integer.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event)
			{
				Editor.addToLastFocused(IntegerToken.getFactory());
			}
		});
		
		Button decimal = new Button();
		decimal.setHTML("<h3>1.0</h3>");
		decimal.setWidth("35px");
		decimal.setHeight("35px");
		decimal.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event)
			{
				Editor.addToLastFocused(DecimalToken.getFactory());
			}
		});
		
		Button fraction = new Button();
		fraction.setHTML("<h3>&frac12;</h3>");
		fraction.setWidth("35px");
		fraction.setHeight("35px");
		fraction.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event)
			{
				Editor.addToLastFocused(FractionToken.getFactory());
			}
		});
		
		Button exponent = new Button();
		exponent.setHTML("<h3>e<sup>x</sup></h3>");
		exponent.setWidth("35px");
		exponent.setHeight("35px");
		exponent.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event)
			{
				Editor.addToLastFocused(ExponentToken.getFactory());
			}
		});
		
		//Math symbols
		Button addition = new Button();
		addition.setHTML("<h3>+</h3>");
		addition.setWidth("35px");
		addition.setHeight("35px");
		addition.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event)
			{
				Editor.addToLastFocused(SymbolToken.getFactory(Symbol.ADDITION));
			}
		});
		
		Button subtraction = new Button();
		subtraction.setHTML("<h3>-</h3>");
		subtraction.setWidth("35px");
		subtraction.setHeight("35px");
		subtraction.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event)
			{
				Editor.addToLastFocused(SymbolToken.getFactory(Symbol.SUBTRACTION));
			}
		});
		
		Button multiplication = new Button();
		multiplication.setHTML("<h3>&times;</h3>");
		multiplication.setWidth("35px");
		multiplication.setHeight("35px");
		multiplication.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event)
			{
				Editor.addToLastFocused(SymbolToken.getFactory(Symbol.MULTIPLICATION));
			}
		});
		
		Button division = new Button();
		division.setHTML("<h3>&divide;</h3>");
		division.setWidth("35px");
		division.setHeight("35px");
		division.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event)
			{
				Editor.addToLastFocused(SymbolToken.getFactory(Symbol.DIVISION));
			}
		});
		
		Button radical = new Button();
		radical.setHTML("<h3>&radic;</h3>");
		radical.setWidth("35px");
		radical.setHeight("35px");
		radical.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event)
			{
				Editor.addToLastFocused(SquareRootToken.getFactory());
			}
		});
		
		//Unit button
		Button unit = new Button();
		unit.setHTML("<h3>Units</h3>");
		unit.setWidth("35px");
		unit.setHeight("35px");
		unit.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event)
			{
				UnitSelectorDialog unitSelector = new UnitSelectorDialog(Editor.getUnitSelectorListener());
				unitSelector.show();
			}
		});
		
		FlexTable toolbar = new FlexTable();

		toolbar.setWidget(0, 0, integer);
		toolbar.setWidget(1, 0, decimal);
		toolbar.setWidget(2, 0, fraction);
		toolbar.setWidget(3, 0, exponent);
		toolbar.setWidget(0, 1, addition);
		toolbar.setWidget(4, 0, unit);
		toolbar.setWidget(1, 1, subtraction);
		toolbar.setWidget(2, 1, multiplication);
		toolbar.setWidget(3, 1, radical);
		toolbar.setWidget(4, 1, division);
		
		return toolbar;
	}
	
	/**
	 * Adds the buttons to the bottom row of the panel.
	 * @return 
	 */
	private Button saveButton()
	{
		// Save and close button and handler
		Button save = new Button("Save & Close");
		save.addClickHandler(new ClickHandler()
		{

			@Override
			public void onClick(ClickEvent event)
			{
				EntityContainer question = (EntityContainer) problemComposer
						.toEntity();
				EntityContainer answer = (EntityContainer) answerComposer
						.toEntity();

				ProblemStatement statement = new ProblemStatement();
				statement.setQuestion(new Question(question.getEntities()));
				statement.setAnswer(new Answer(answer.getEntities()));

				// Get the previous level (if any), and the current
				Level currLevel = Level.valueOf(levelBox.getItemText(levelBox.getSelectedIndex()));
				
				// Set the new level (if one exists)
				problem.setLevel(currLevel);
				
				// Set the new name. 
				problem.setName(nameBox.getText());
				
				// Call the save problem service
				saveProblemService.saveProblem(problem.getProblemId(), problem.getAssignmentId(), problem.getLevel(), problem.getName(), problem.getProblemOrder(), statement,
						new AsyncCallback<ProblemMsg>()
						{
							@Override
							public void onFailure(Throwable caught)
							{
								Window.alert(caught.getMessage());
							}
							@Override
							public void onSuccess(ProblemMsg result)
							{
								if (result != null)
								{
									hide();
									ApplicationWrapper.getInstance().setWidget(
											new InstructorAssignmentView(result.getAssignmentId()));
								}
								else
								{
									Window.alert("Error saving problem");
								}
							}
						});
			}
		});
		
		return save;
	}

	/**
	 * @return A cancel button.
	 */
	private Button cancelButton()
	{
		Button cancel = new Button("Cancel");
		cancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event)
			{
				hide();
			}
		});
		return cancel;
	}

	@Override
	public void onEmpty(Editor source)
	{
		
	}

	@Override
	public void onTokenAdded(Editor source, Token token)
	{
		this.center();
	}

	@Override
	public void onTokenRemoved(Editor source)
	{
		this.center();
	}

}
