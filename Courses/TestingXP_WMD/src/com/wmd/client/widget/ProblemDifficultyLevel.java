package com.wmd.client.widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.wmd.client.msg.Level;
import com.wmd.client.msg.ProblemMsg;

/**
 * This widget is a flextable that contains ProblemTableCell widgets which act
 * as buttons to interact with problems in the assignment.
 * 
 * @author Eric C. Abruzzese
 * 
 */
public class ProblemDifficultyLevel extends Composite
{

	private Level level;

	private AbsolutePanel problemDifficultyContainer = new AbsolutePanel();
	private FlexTable problemDifficultyTable = new FlexTable();

	private ArrayList<ProblemMsg> problemList;

	/**
	 * Creates the flex table widget to be displayed.
	 * 
	 * @param The
	 *            level constant of the problem (difficulty)
	 */
	public ProblemDifficultyLevel(Level level)
	{
		this.level = level;

		this.problemList = new ArrayList<ProblemMsg>();

		this.problemDifficultyContainer.add(this.problemDifficultyTable);
		this.problemDifficultyContainer.setWidth("100%");

		this.getProblemTable().setWidget(0, 0,
				new HTML("<h2>" + this.getLevel() + "</h2>"));
		this.getProblemTable().getFlexCellFormatter().addStyleName(0, 0,
				"level" + level);
		this.getProblemTable().setWidth("100%");

		initWidget(this.problemDifficultyContainer);
	}

	/**
	 * @return the name of the problem difficulty (e.g. Easy, Medium, Hard)
	 */
	public Level getLevel()
	{
		return this.level;
	}

	/**
	 * @return the table containing the ProblemTableCells
	 */
	private FlexTable getProblemTable()
	{
		return this.problemDifficultyTable;
	}

	/**
	 * @param problem
	 */
	public void add(ProblemMsg problem, ProblemMsg prevProblem,
			ProblemMsg nextProblem)
	{
		this.problemList.add(problem);

		Comparator<ProblemMsg> order = new Comparator<ProblemMsg>()
		{
			public int compare(ProblemMsg p1, ProblemMsg p2)
			{
				return (p1.getProblemOrder() - p2.getProblemOrder());
			}
		};

		Collections.sort(this.problemList, order);

		this.getProblemTable().setWidget(
				this.getProblemTable().getRowCount() + 1, 0,
				new ProblemTableCell(problem, prevProblem, nextProblem));
	}
	
}
