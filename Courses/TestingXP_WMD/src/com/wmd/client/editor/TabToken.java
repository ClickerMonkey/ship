package com.wmd.client.editor;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.wmd.client.entity.Entity;

/**
 * A token representing a single tab.
 * 
 * @author Philip Diffenderfer
 * 
 */
public class TabToken extends Token
{

	// The container of the tab
	private HTML html;

	/**
	 * Initializes a new TabToken.
	 * 
	 * @param parent
	 *            The editor which contains this token.
	 */
	public TabToken(Editor parent)
	{
		super(parent);
		html = new HTML();
		initWidget(html);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void widgetInitialized(Widget widget, Style style)
	{
		style.setWidth(getFontSize() * 2.0, Unit.PX);
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
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Entity toEntity()
	{
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String toAppendableString()
	{
		return "\t";
	}

	/**
	 * Creates the factory used to instantiate tokens of this type.
	 */
	private static TokenFactory factory = new TokenFactory()
	{
		@Override
		public Token createToken(Editor parent)
		{
			return new TabToken(parent);
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
