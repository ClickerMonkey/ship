package com.wmd.client.widget;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.wmd.client.entity.EntityContainer;
import com.wmd.client.entity.Fraction;
import com.wmd.client.entity.Text;

/**
 * This Widget encapsulates a Fraction object.
 * 
 * @author AJ Marx, Chris Koch
 * 
 */
public class FractionWidget extends EntityWidget<Fraction>
{

	private final TextBox numTextBox;
	private final TextBox denTextBox;

	/**
	 * Create a new FractionWidget with an empty correct entity.
	 */
	public FractionWidget()
	{
		// This might not work since fraction changed
		this(new Fraction(new EntityContainer(new Text("")),
				new EntityContainer(new Text(""))));
	}

	/**
	 * Create a new FractionWidget with the specified Fraction as the correct
	 * answer.
	 * 
	 * @param correct
	 */
	public FractionWidget(Fraction correct)
	{
		// super(correct, new EntityContainer(new Text("")),new
		// EntityContainer(new Text("")));
		super(correct, correct);

		VerticalPanel panel = new VerticalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.setStylePrimaryName("fraction");

		this.numTextBox = new TextBox();
		this.numTextBox.addKeyPressHandler(new NumbersOnly());
		this.numTextBox.addKeyUpHandler(new AdjustSize(this.numTextBox));
		this.numTextBox.addStyleName("fraction-box");

		this.denTextBox = new TextBox();
		this.denTextBox.addKeyPressHandler(new NumbersOnly());
		this.denTextBox.addKeyUpHandler(new AdjustSize(this.denTextBox));
		this.denTextBox.addStyleName("fraction-box");

		Label bar = new Label();
		bar.addStyleName("add-bar");

		panel.add(this.numTextBox);
		panel.add(bar);
		panel.add(this.denTextBox);
		this.initWidget(panel);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateEntity()
	{
		// this.entity.setNumerator(numTextBox.getText());
		// this.entity.setDenominator(denTextBox.getText());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateWidget()
	{
		// this.numTextBox.setText(entity.getNumerator());
		// this.denTextBox.setText(entity.getDenominator());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCorrect()
	{
		return false;
		// return
		// (this.numTextBox.getText().equalsIgnoreCase(correctEntity.getNumerator())
		// &&
		// this.denTextBox.getText().equalsIgnoreCase(correctEntity.getDenominator()));
	}

	/**
	 * Puts the focus in the numerator text box.
	 */
	@Override
	public void requestFocus()
	{
		this.numTextBox.setFocus(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showAnswer()
	{
		// this.numTextBox.setText(correctEntity.getNumerator());
		// this.denTextBox.setText(correctEntity.getDenominator());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setReadOnly(boolean readOnly)
	{
		// @author Kevin Rexroth
		this.numTextBox.setEnabled(!readOnly);
		this.denTextBox.setEnabled(!readOnly);
	}

}
