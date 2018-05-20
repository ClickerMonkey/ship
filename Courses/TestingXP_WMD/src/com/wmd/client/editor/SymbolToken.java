package com.wmd.client.editor;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.wmd.client.entity.Entity;
import com.wmd.client.entity.Symbol;

/**
 * A token representing a single math symbol.
 * 
 * @author Philip Diffenderfer
 * 
 */
public class SymbolToken extends Token
{

	// The container of the symbol
	private final HTML html;

	/**
	 * Initializes a new SymbolToken.
	 * 
	 * @param parent
	 *            The editor which contains this token.
	 */
	public SymbolToken(Editor parent, String symbol)
	{
		super(parent);
		html = new HTML(symbol);
		initWidget(html);
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
		return new Symbol(html.getText());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String toAppendableString()
	{
		return " ";
	}

	/**
	 * Returns the factory associated with this token.
	 * 
	 * @return This tokens factory.
	 */
	public static TokenFactory getFactory(final String symbol)
	{
		return new TokenFactory() {
			@Override
			public Token createToken(Editor parent)
			{
				return new SymbolToken(parent, symbol);
			}
		};
	}

}
