package com.wmd.client.widget;

import com.google.gwt.user.client.ui.HTML;
import com.wmd.client.entity.Newline;

/**
 * This Widget encapsulates a New Line object.
 * 
 * @author AJ Marx, Chris Koch
 */
public class NewlineWidget extends EntityWidget<Newline>
{

	/**
	 * Create a new NewLineWidget object.
	 */
	public NewlineWidget()
	{
		super(new Newline(), new Newline());

		this.initWidget(new HTML("<br/>"));
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isCorrect()
	{
		return true;
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
	public void requestFocus()
	{
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateEntity()
	{
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateWidget()
	{
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setReadOnly(boolean readOnly)
	{
		// do nothing
	}

}
