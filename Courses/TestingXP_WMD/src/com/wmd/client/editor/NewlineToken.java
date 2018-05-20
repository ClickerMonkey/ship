package com.wmd.client.editor;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.wmd.client.entity.Entity;
import com.wmd.client.entity.Newline;

/**
 * A token that is a newline character.
 * 
 * @author Philip Diffenderfer
 * 
 */
public class NewlineToken extends Token
{

	// The label holding the character.
	private final Label label;

	/**
	 * Initializes a new NewlineToken.
	 * 
	 * @param parent
	 *            The editor containing this token.
	 */
	public NewlineToken(Editor parent)
	{
		super(parent);
		label = new Label("<newline>");
		initWidget(label);
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
		return new Newline();
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
			return new NewlineToken(parent);
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
