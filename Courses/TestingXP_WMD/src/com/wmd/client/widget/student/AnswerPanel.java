package com.wmd.client.widget.student;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.wmd.client.entity.Answer;
import com.wmd.client.entity.Entity;
import com.wmd.client.entity.Fraction;
import com.wmd.client.entity.Integer;
import com.wmd.client.entity.Newline;
import com.wmd.client.entity.Text;
import com.wmd.client.widget.EntityWidget;
import com.wmd.client.widget.FractionWidget;
import com.wmd.client.widget.IntegerWidget;

/**
 * FlowPanel used to go through the answers tokens and display the answer boxes
 * 
 * @author Scotty Rhinehart, William Fisher and Eric C. Abruzzese
 * 
 */
public class AnswerPanel extends FlowPanel
{

	private Answer answer;

	/**
	 * Widgets for entities that hold values.
	 */
	private ArrayList<EntityWidget<?>> widgets;

	/**
	 * Initializes an AnswerPanel for displaying textboxes for input of the
	 * answer.
	 * 
	 * @param answer
	 *            The answer to display.
	 */
	public AnswerPanel(Answer answer)
	{
		this.answer = answer;
		this.widgets = new ArrayList<EntityWidget<?>>();
		this.addStyleName("answer-panel");
		this.load();
	}

	/**
	 * Calls the widget that creates the box for the answer.
	 */
	public void load()
	{
		this.clear();
		widgets.clear();

		// If no answer was specified then we can't add its entities
		if (answer == null)
		{
			return;
		}

		ArrayList<Entity> items = answer.getEntities();

		for (int i = 0; i < items.size(); i++)
		{
			Entity e = items.get(i);
			if (e instanceof Fraction)
			{
				FractionWidget widget = new FractionWidget((Fraction) e);

				widgets.add(widget);
				add(widget);
			} else if (e instanceof Integer)
			{
				IntegerWidget widget = new IntegerWidget((Integer) e);

				widgets.add(widget);
				add(widget);
			} else if (e instanceof Text)
			{
				addText((Text) e);
			} else if (e instanceof Newline)
			{
				addNewline();
			}
		}
	}

	/**
	 * Adds text to the panel.
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
	 * @return the answer
	 */
	public Answer getAnswer()
	{
		return answer;
	}

	/**
	 * @param answer
	 *            the answer to set
	 */
	public void setAnswer(Answer answer)
	{
		this.answer = answer;
		this.load();
	}

	/**
	 * Method that checks the individual widgets to see if the input is correct
	 * 
	 * @return boolean -Value that distinguishes whether the answer is correct
	 *         or not
	 */
	public boolean isCorrect()
	{
		for (EntityWidget<?> entity : widgets)
		{
			if (!entity.isCorrect())
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Sets the widgets in the answer to readonly or read-write.
	 * 
	 * @param readOnly
	 *            If the widgets should be read only.
	 */
	public void setReadOnly(boolean readOnly)
	{
		for (EntityWidget<?> widget : widgets)
		{
			widget.setReadOnly(readOnly);
		}
	}

	/**
	 * Shows the answers for all entity widgets.
	 */
	public void showAnswers()
	{
		for (EntityWidget<?> widget : widgets)
		{
			widget.showAnswer();
		}
	}

}
