package com.wmd.client.widget;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.wmd.client.entity.Text;

/**
 * This Widget encapsulates a Text object.
 * 
 * @author AJ Marx, Chris Koch
 * 
 */
public class TextWidget extends EntityWidget<Text>
{

	private final TextBox textBox;

	/**
	 * Create a new IntegerWidget with an empty correct entity.
	 */
	public TextWidget()
	{
		this(new Text());
	}

	/**
	 * Create a new TextWidget object for the specified Text Entity.
	 * 
	 * @param text
	 */
	public TextWidget(Text text)
	{
		super(text, new Text(""));

		entity = new Text();

		Panel panel = new VerticalPanel();
		panel.setStylePrimaryName("fraction");
		textBox = new TextBox();
		textBox.addKeyDownHandler(new KeyDownHandler()
		{
			public void onKeyDown(KeyDownEvent event)
			{
				TextWidget.this.getEntity().setText(textBox.getText());
			}
		});
		textBox.addKeyUpHandler(new AdjustSize(textBox));
		panel.add(textBox);
		this.initWidget(panel);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateWidget()
	{
		textBox.setText(entity.getText());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateEntity()
	{
		entity.setText(textBox.getText());
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isCorrect()
	{
		return true;
	}

	/**
	 * Puts the focus in the textbox in this widget.
	 */
	public void requestFocus()
	{
		textBox.setFocus(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showAnswer()
	{
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setReadOnly(boolean readOnly)
	{
		// @author Kevin Rexroth
		textBox.setEnabled(!readOnly);
	}

}
