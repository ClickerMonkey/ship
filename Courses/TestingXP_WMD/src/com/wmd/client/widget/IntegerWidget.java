package com.wmd.client.widget;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.wmd.client.entity.Integer;

/**
 * This Widget encapsulates a Integer object.
 * 
 * @author AJ Marx, Chris Koch
 * 
 */
public class IntegerWidget extends EntityWidget<Integer>
{

	final TextBox integerTextBox;

	/**
	 * Create a new IntegerWidget with an empty correct entity.
	 */
	public IntegerWidget()
	{
		this(new Integer());
	}

	/**
	 * Creates a new IntegerWidget with the specified Integer as the correct
	 * answer.
	 * 
	 * @param correct
	 */
	public IntegerWidget(Integer correct)
	{
		super(correct, new Integer(""));

		VerticalPanel panel = new VerticalPanel();
		integerTextBox = new TextBox();
		integerTextBox.addKeyUpHandler(new AdjustSize(integerTextBox));
		integerTextBox.addKeyPressHandler(new NumbersOnly());
		integerTextBox.addKeyDownHandler(new KeyDownHandler()
		{
			public void onKeyDown(KeyDownEvent event)
			{
				IntegerWidget.this.getEntity().setInteger(
						integerTextBox.getText());
			}
		});
		integerTextBox.addStyleName("text-readonly");

		panel.addStyleName("text-readonly");
		panel.add(integerTextBox);
		this.initWidget(panel);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateEntity()
	{
		entity.setInteger(integerTextBox.getText());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateWidget()
	{
		integerTextBox.setText(entity.getInteger());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCorrect()
	{
		return (integerTextBox.getText().equalsIgnoreCase(correctEntity
				.getInteger()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showAnswer()
	{
		integerTextBox.setText(correctEntity.getInteger());
	}

	/**
	 * Puts the focus in the text box.
	 */
	@Override
	public void requestFocus()
	{
		integerTextBox.setFocus(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setReadOnly(boolean readOnly)
	{
		// @author Kevin Rexroth
		integerTextBox.setEnabled(!readOnly);
	}

}
