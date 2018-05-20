package com.wmd.client.widget.student;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.wmd.client.entity.Answer;
import com.wmd.client.entity.Entity;
import com.wmd.client.entity.Fraction;
import com.wmd.client.entity.Integer;
import com.wmd.client.entity.Text;

/**
 * 
 * This widget creates a panel with appropriate textboxes based on the type of
 * answers the problem requires
 * 
 * @author Chris Koch, AJ Marx and Eric C. Abruzzese
 * 
 * 
 */
public class StudentAnswerWidget extends VerticalPanel
{

	HorizontalPanel inputPanel;

	/**
	 * creates empty panel
	 */
	public StudentAnswerWidget()
	{
		init();
	}

	private void init()
	{
		inputPanel = new HorizontalPanel();
		inputPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		Answer answer = new Answer();
		ArrayList<Entity> answerList = answer.getEntities();

		// checks the list of answer types and creates appropriate text boxes

		for (Object o : answerList)
		{
			if (o instanceof Fraction)
			{
				inputPanel.add(createFractionInput());
			} else if (o instanceof Integer)
			{
				inputPanel.add(createIntegerInput());
			} else if (o instanceof Text)
			{
				inputPanel.add(createStringInput());
			}
		}

		this.add(inputPanel);
	}

	/**
	 * Returns a VerticalPanel with two input boxes separated by a &lt;hr /&gt;.
	 * Make sure that the CSS <code>display</code> property for objects of class
	 * <code>fraction</code> is set to <code>inline</code>, otherwise, any
	 * elements following this Widget will appear on a new line.
	 * 
	 * @return
	 */
	private static VerticalPanel createFractionInput()
	{
		VerticalPanel panel = new VerticalPanel();
		panel.setStylePrimaryName("fraction");
		TextBox num = new TextBox();
		TextBox den = new TextBox();
		num.addKeyPressHandler(new NumbersOnly());
		den.addKeyPressHandler(new NumbersOnly());
		panel.add(num);
		panel.add(new HTML("<hr />"));
		panel.add(den);
		return panel;
	}

	private static VerticalPanel createIntegerInput()
	{
		VerticalPanel panel = new VerticalPanel();
		TextBox integer = new TextBox();
		integer.addKeyPressHandler(new NumbersOnly());
		integer.addKeyUpHandler(new AdjustSize(integer));
		panel.setStylePrimaryName("integer");
		panel.add(integer);
		return panel;
	}

	private static VerticalPanel createStringInput()
	{
		VerticalPanel panel = new VerticalPanel();
		TextBox string = new TextBox();
		string.addKeyUpHandler(new AdjustSize(string));
		string.setSize("10ex", "25px");
		panel.setStylePrimaryName("label");
		panel.add(string);
		return panel;
	}

	/**
	 * This KeyPressHandler listens to key presses, and prevents any
	 * 'non-allowable' key presses to reach the target. Only numbers, tabs,
	 * spaces, and arrow keys are allowable.
	 * 
	 */
	private static class NumbersOnly implements KeyPressHandler
	{
		public static final char allowable[] = new char[]
		{ '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '\t', '\b', 37, 38,
				39, 40 };

		public void onKeyPress(KeyPressEvent event)
		{
			char c = event.getCharCode();
			for (char a : allowable)
				if (c == a)
					return;
			event.preventDefault();
		}
	}

	/**
	 * This key listener listens for key presses, and adjusts the size of the
	 * TextBox that this listener listens to.
	 * 
	 * @author AJ Marx, Chris Koch
	 * 
	 */
	private static class AdjustSize implements KeyUpHandler
	{
		TextBox box;

		public AdjustSize(TextBox field)
		{
			box = field;
		}

		public void onKeyUp(KeyUpEvent event)
		{
			box
					.setSize(Math.max(8, box.getValue().length() + 2) + "ex",
							"25px");
		}
	}
}
