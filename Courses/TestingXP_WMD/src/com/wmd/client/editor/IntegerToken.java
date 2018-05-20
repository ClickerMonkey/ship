package com.wmd.client.editor;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.wmd.client.entity.Entity;
import com.wmd.client.entity.Integer;
import com.wmd.client.widget.EntityWidget;

/**
 * A token that is an integer box.
 * 
 * @author Philip Diffenderfer
 * 
 */
public class IntegerToken extends Token
{

	// The TextBox containing the integer.
	public TextBox integerBox;


	/**
	 * Initializes a new IntegerToken.
	 * 
	 * @param parent
	 *            The editor containing this token.
	 */
	public IntegerToken(Editor parent)
	{
		this(parent, "");
	}
	
	/**
	 * Initializes a new IntegerToken.
	 * 
	 * @param parent
	 *            The editor containing this token.
	 * @param intValue
	 * 			The string for the integer text box.
	 */
	public IntegerToken(Editor parent, String intValue)
	{
		super(parent);

		integerBox = new TextBox();
		integerBox.setText(intValue);
		integerBox.addKeyUpHandler(new EntityWidget.AdjustSize(integerBox));

		initWidget(integerBox);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void widgetInitialized(Widget widget, Style style)
	{
		style.setFontWeight(FontWeight.BOLD);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void propagateAdd(TokenFactory factory)
	{
	}

	/**
	 * @return The value of the integer TextBox.
	 */
	public String getValue()
	{
		return integerBox.getText();
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
		return new Integer(integerBox.getText());
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
			return new IntegerToken(parent);
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
