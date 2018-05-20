package com.wmd.client.editor;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.wmd.client.entity.Entity;

/**
 * A token that is a single character.
 * 
 * @author Philip Diffenderfer
 * 
 */
public class CharToken extends Token
{

	// The label holding the character.
	private final Label label;

	// The character contained in this token.
	private final char character;

	/**
	 * Initializes a new CharToken.
	 * 
	 * @param parent
	 *            The editor containing this token.
	 * @param c
	 *            The character of this token.
	 */
	public CharToken(Editor parent, char c)
	{
		super(parent);
		character = c;
		label = new Label(String.valueOf(c));
		initWidget(label);
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
	 * @return The character in this token.
	 */
	public char getCharacter()
	{
		return character;
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
		return String.valueOf(character);
	}

}
