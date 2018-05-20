package com.wmd.client.editor;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.wmd.client.entity.Entity;
import com.wmd.client.entity.EntityContainer;
import com.wmd.client.entity.SquareRoot;

/**
 * A token representing a square root editor.
 * 
 * @author Philip Diffenderfer
 * 
 */
public class SquareRootToken extends Token implements EditorListener
{

	// The container of the symbol
	private final HorizontalPanel panel;
	private final HTML radical;
	private final Editor editor;


	/**
	 * Initializes a new SquareRootToken.
	 * 
	 * @param parent
	 *            The editor which contains this token.
	 */
	public SquareRootToken(Editor parent)
	{
		this(parent, null);
	}
	
	/**
	 * Initializes a new SquareRootToken.
	 * 
	 * @param parent
	 *            The editor which contains this token.
	 * @param rootEntity
	 * 			The entity to populate the root editor with.
	 */
	public SquareRootToken(Editor parent, Entity rootEntity)
	{
		super(parent);
		
		panel = new HorizontalPanel();
		panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		radical = new HTML("<h1>&radic;</h1>");
		panel.add(radical);
		
		editor = new Editor(parent);
		editor.fromEntity(rootEntity);
		editor.getElement().getStyle().setProperty("borderTop", "2px solid #000");
		editor.addListener(this);
		panel.add(editor);
		
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
		editor.propagateAdd(factory);
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
		return new SquareRoot((EntityContainer)editor.toEntity());
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
	 * Returns the factory associated with this token.
	 * 
	 * @return This tokens factory.
	 */
	public static TokenFactory getFactory()
	{
		return new TokenFactory() {
			@Override
			public Token createToken(Editor parent)
			{
				return new SquareRootToken(parent);
			}
		};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onEmpty(Editor source)
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onTokenAdded(Editor source, Token token)
	{
		if (getEditor() != null)
		{
			getEditor().onTokenAdded(getEditor(), source);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onTokenRemoved(Editor source)
	{
		onTokenAdded(source, null);
	}

}
