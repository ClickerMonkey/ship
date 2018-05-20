package com.wmd.client.editor;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.wmd.client.entity.Decimal;
import com.wmd.client.entity.Entity;
import com.wmd.client.widget.EntityWidget;

/**
 * A token that represents the decimal entity.
 * 
 * @author Philip Diffenderfer, Chris Eby
 * 
 */
public class DecimalToken extends Token
{

	// The panel holding the whole, dot, and decimal parts.
	private HorizontalPanel panel;

	// The TextBox for the whole part of the decimal
	private TextBox wholeBox;

	// The label holding the period, duh.
	private Label dot;

	// The TextBox for the fraction part of the decimal.
	private TextBox decimalBox;

	/**
	 * Initializes a new DecimalToken.
	 * 
	 * @param parent
	 *            The editor containing this token.
	 */
	public DecimalToken(Editor parent)
	{
		this(parent, "", "");
	}
	
	/**
	 * Initializes a new DecimalToken.
	 * 
	 * @param parent
	 * 			The editor containing this token.
	 * @param whole
	 * 			The string for the whole text box.
	 * @param decimal
	 * 			The string for the decimal text box.
	 */
	public DecimalToken(Editor parent, String whole, String decimal)
	{
		super(parent);

		wholeBox = new TextBox();
		wholeBox.addKeyUpHandler(new EntityWidget.AdjustSize(wholeBox));
		wholeBox.setText(whole);

		dot = new Label(".");

		decimalBox = new TextBox();
		decimalBox.addKeyUpHandler(new EntityWidget.AdjustSize(decimalBox));
		decimalBox.setText(decimal);

		panel = new HorizontalPanel();
		panel.add(wholeBox);
		panel.add(dot);
		panel.add(decimalBox);

		initWidget(panel);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void widgetInitialized(Widget widget, Style style)
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void propagateAdd(TokenFactory factory)
	{
	}

	/**
	 * @return The text in the whole TextBox.
	 */
	public String getWhole()
	{
		return wholeBox.getText();
	}

	/**
	 * @return The text in the decimal TextBox.
	 */
	public String getDecimal()
	{
		return decimalBox.getText();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isAnEntity()
	{
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Entity toEntity()
	{
		return new Decimal(wholeBox.getText(), decimalBox.getText());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String toAppendableString()
	{
		return null;
	}

	/**
	 * Creates the factory used to instantiate tokens of this type.
	 */
	private static TokenFactory factory = new TokenFactory()
	{
		@Override
		public Token createToken(Editor parent)
		{
			return new DecimalToken(parent);
		}
	};

	/**
	 * Returns the factory associated with this token.
	 * 
	 * @return This tokens factory.
	 */
	public static TokenFactory getFactory()
	{
		return factory;
	}

}
