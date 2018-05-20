package com.wmd.client.widget.student;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.wmd.client.entity.ProblemStatement;

/**
 * 
 * @author Stephen Jurnack, Philip Diffenderfer and Eric C. Abruzzese
 * 
 */
public class ProblemPanel extends VerticalPanel
{

	private final QuestionPanel questionPanel;
	private final AnswerPanel answerPanel;
	private ProblemButton sourceButton;

	/**
	 * Constructor for a problem panel
	 */
	public ProblemPanel()
	{
		setVerticalAlignment(ALIGN_MIDDLE);

		questionPanel = new QuestionPanel(null);
		answerPanel = new AnswerPanel(null);

		add(questionPanel);
		add(answerPanel);

		setCellHeight(questionPanel, "50%");
		setCellHeight(answerPanel, "50%");

		addStyleName("problem-panel");
	}

	/**
	 * @param source
	 *            - A button that links to a problem
	 * @param statement
	 *            - Holds the question and answer entities of a problem
	 */
	public void setStatement(ProblemButton source, ProblemStatement statement)
	{
		sourceButton = source;
		questionPanel.setQuestion(statement.getQuestion());

		answerPanel.setAnswer(statement.getAnswer());

		boolean correct = source.getStatus().isCorrect();
		answerPanel.setReadOnly(correct);
		if (correct)
		{
			answerPanel.showAnswers();
		}
	}

	/**
	 * @return Returns the question panel
	 */
	public QuestionPanel getQuestionPanel()
	{
		return questionPanel;
	}

	/**
	 * @return Returns the answer panel
	 */
	public AnswerPanel getAnswerPanel()
	{
		return answerPanel;
	}

	/**
	 * @return Returns the source button
	 */
	public ProblemButton getSource()
	{
		return sourceButton;
	}

}
