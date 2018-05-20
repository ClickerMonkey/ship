package com.wmd.client.widget.student;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.wmd.client.entity.Entity;
import com.wmd.client.entity.Fraction;
import com.wmd.client.entity.Integer;
import com.wmd.client.entity.Newline;
import com.wmd.client.entity.Question;
import com.wmd.client.entity.Text;

/**
 * 
 * @author Phil Diffenderfer, William Fisher and Eric C. Abruzzese Purpose:
 *         FlowPanel used to go through the questions tokens and display the
 *         question
 * 
 */
public class QuestionPanel extends FlowPanel
{

	/**
	 * Question that is to be displayed
	 */
	private Question question;

	/**
	 * Initializes a QuestionPanel given the question to display.
	 * 
	 * @param question
	 *            The question to display.
	 */
	public QuestionPanel(Question question)
	{
		this.question = question;
		this.addStyleName("question-panel");
		this.load();
	}

	/**
	 * Loads the entites from the current question into the panel.
	 */
	public void load()
	{
		clear();

		if (this.question == null)
		{
			return;
		}

		ArrayList<Entity> items = this.question.getEntities();

		for (int i = 0; i < items.size(); i++)
		{
			Entity e = items.get(i);
			if (e instanceof Text)
			{
				addText((Text) e);
			} else if (e instanceof Newline)
			{
				addNewline();
			} else if (e instanceof Fraction)
			{
				addFraction((Fraction) e);
			} else if (e instanceof Integer)
			{
				addInteger((Integer) e);
			}
		}
	}

	/**
	 * Add an integer to the text that is displayed
	 * 
	 * @param e
	 *            The Integer Entity
	 */
	private void addInteger(Integer e)
	{
		Label lbl = new Label(e.getInteger());
		lbl.addStyleName("text-readonly");
		add(lbl);
	}

	/**
	 * Adds text to the panel
	 * 
	 * @param text
	 *            The Text Entity
	 */
	private void addText(Text text)
	{
		Label lbl = new Label(text.getText());
		lbl.addStyleName("text-readonly");
		add(lbl);
	}

	/**
	 * Adds a newline to the question
	 */
	private void addNewline()
	{
		HTML lineBreak = new HTML("<br/>");
		add(lineBreak);
	}

	/**
	 * Stacks the numerator and denominator on to bar to for a fraction and adds
	 * its to the panel
	 * 
	 * @param f
	 *            The Fraction Entity
	 */
	private void addFraction(Fraction f)
	{

		// Label num = new Label(f.getNumerator()));
		// num.addStyleName("fraction-value");

		// Label den = new Label(f.getDenominator());
		// den.addStyleName("fraction-value");

		Label bar = new Label();
		bar.addStyleName("add-bar");

		VerticalPanel panel = new VerticalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		// panel.add(num);
		panel.add(bar);
		// panel.add(den);
		panel.addStyleName("fraction");

		add(panel);
	}

	/**
	 * @return the question
	 */
	public Question getQuestion()
	{
		return this.question;
	}

	/**
	 * @param question
	 *            the question to set
	 */
	public void setQuestion(Question question)
	{
		this.question = question;
		this.load();
	}

}
