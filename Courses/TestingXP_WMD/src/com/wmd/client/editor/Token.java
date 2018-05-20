package com.wmd.client.editor;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.wmd.client.entity.Entity;

/**
 * A single token of some arbitrary item contained in an editor.
 * 
 * @author Philip Diffenderfer
 * 
 */
public abstract class Token extends Composite
{

	/**
	 * The starting font size for the rich text editor.
	 */
	public static final int BASE_FONT_SIZE = 24;

	/**
	 * The decrease in font size each embedded text editor gets.
	 */
	public static final int FONT_SIZE_FACTOR = 4;

	/**
	 * The minimum font size for an editor.
	 */
	public static final int MIN_FONT_SIZE = 8;

	// The editor that holds this token.
	private final Editor editor;

	// The level of this token (0=base editor)
	private int level;

	// The font size of this token (in pixels)
	private int fontSize;

	/**
	 * Initializes a new Token.
	 * 
	 * @param parent
	 *            The parent editor of this token.
	 */
	public Token(Editor parent)
	{
		this.editor = parent;
		this.level = (parent == null ? -1 : parent.getLevel() + 1);
	}

	/**
	 * Implementation should be notified when a token is initialized.
	 * 
	 * @param widget
	 *            The widget that initialized.
	 * @param style
	 *            The style of the initialized widget.
	 */
	protected abstract void widgetInitialized(Widget widget, Style style);

	/**
	 * Should propagate the add token call to child editors. The editors will
	 * add the token if they're focused.
	 */
	protected abstract void propagateAdd(TokenFactory factory);

	/**
	 * This should return true if this token can be represented completely by
	 * itself as its own entity (char, space, and tab tokens cannot).
	 * 
	 * @return True if this token can be its own entity.
	 */
	protected abstract boolean isAnEntity();

	/**
	 * Converts this token to an entity. If this token cannot be converted to an
	 * entity this should return null.
	 * 
	 * @return The converted entity.
	 */
	public abstract Entity toEntity();

	/**
	 * Converts this token to a string. This in the end will build a TextEntity.
	 * If this token returns true for isAnEntity() then this can return null.
	 * 
	 * @return A string representation of this token.
	 */
	protected abstract String toAppendableString();

	/**
	 * Initializes the widget in this token. The font size of this token is
	 * updated based on its level and then notifies the implementation that this
	 * token has been initialized through widgetInitialized(Widget, Style).
	 * 
	 * @param widget
	 *            The widget being placed in this token.
	 */
	protected void initWidget(Widget widget)
	{
		super.initWidget(widget);

		Style style = widget.getElement().getStyle();
		// style.setDisplay(Display.INLINE_BLOCK);

		// style.setBorderWidth(1.0, Unit.PX);
		// style.setBorderColor("#000");
		// style.setBorderStyle(BorderStyle.SOLID);

		updateFontSize();

		widgetInitialized(widget, style);
	}

	/**
	 * @return The total width of this token in pixels.
	 */
	public int getWidth()
	{
		return getWidget().getOffsetWidth();
	}

	/**
	 * @return The total height of this token in pixels.
	 */
	public int getHeight()
	{
		return getWidget().getOffsetHeight();
	}

	/**
	 * @return The editor this token exists in.
	 */
	public Editor getEditor()
	{
		return editor;
	}

	/**
	 * Sets the level of the token and adjusts its font size accordingly.
	 * 
	 * @param level
	 *            The new level.
	 */
	public void setLevel(int level)
	{
		this.level = level;
		this.updateFontSize();
	}

	/**
	 * @return The level of this token.
	 */
	public int getLevel()
	{
		return level;
	}

	/**
	 * @return The font size of this token in pixels.
	 */
	public int getFontSize()
	{
		return fontSize;
	}

	/**
	 * Updates the font size of this token based on the level of this token.
	 */
	private void updateFontSize()
	{
		int sizeFactor = level * FONT_SIZE_FACTOR;
		int sizeDesired = BASE_FONT_SIZE - sizeFactor;
		fontSize = Math.max(MIN_FONT_SIZE, sizeDesired);

		getElement().getStyle().setFontSize(fontSize, Unit.PX);
	}

}
