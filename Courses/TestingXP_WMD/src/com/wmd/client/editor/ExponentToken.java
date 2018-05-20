package com.wmd.client.editor;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.wmd.client.entity.Entity;
import com.wmd.client.entity.EntityContainer;
import com.wmd.client.entity.Exponent;

/**
 * A token that represents the exponent entity.
 * 
 * @author Philip Diffenderfer
 * 
 */
public class ExponentToken extends Token implements EditorListener
{

	// The panel holding the base and the exponentPanel.
	private HorizontalPanel panel;

	// The editor for the exponent base.
	private Editor base;

	// The panel which holds the exponent and filler.
	private VerticalPanel exponentPanel;

	// The exponent (its own little editor)
	private Editor exponent;

	// The filler below the exponent with the equal height.
	private Label filler;

	/**
	 * Initializes a new token representing an exponent.
	 * 
	 * @param parent
	 *            The editor that contains this token.
	 */
	public ExponentToken(Editor parent)
	{
		this(parent, null, null);
	}
	
	/**
	 * Initializes a new token representing an exponent.
	 * 
	 * @param parent
	 *            The editor that contains this token.
	 * @param exponentEntity
	 * 			The entity to populate the exponent editor with.
	 * @param baseEntity
	 * 			The entity to populate the base editor with.
	 */
	public ExponentToken(Editor parent, Entity exponentEntity, Entity baseEntity)
	{
		super(parent);

		base = new Editor(parent);
		base.fromEntity(baseEntity);
		
		exponentPanel = new VerticalPanel();

		exponent = new Editor(parent);
		exponent.fromEntity(exponentEntity);
		exponent.setWidth("30px");
		exponent.getElement().getStyle().setMarginLeft(4.0, Unit.PX);
		exponent.getElement().getStyle().setMarginRight(4.0, Unit.PX);
		exponent.addListener(this);

		filler = new Label();

		exponentPanel.add(exponent);
		exponentPanel.add(filler);
		exponentPanel.setSize("30px", "40px");

		exponentPanel.setCellHeight(exponent, "100%");
		exponentPanel.setCellWidth(exponent, "100%");
		exponentPanel.setCellHorizontalAlignment(exponent,
				HasHorizontalAlignment.ALIGN_CENTER);

		exponentPanel.setCellHeight(filler, "100%");
		exponentPanel.setCellWidth(filler, "100%");
		exponentPanel.setCellHorizontalAlignment(filler,
				HasHorizontalAlignment.ALIGN_CENTER);

		panel = new HorizontalPanel();
		panel.add(base);
		panel.add(exponentPanel);

		panel.setCellVerticalAlignment(base, HasVerticalAlignment.ALIGN_MIDDLE);
		panel.setCellVerticalAlignment(exponentPanel,
				HasVerticalAlignment.ALIGN_MIDDLE);

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
	public void onEmpty(Editor source)
	{
		filler.setHeight(exponent.getElement().getStyle().getHeight());
	}

	/**
	 * {@inheritDoc}
	 */
	public void onTokenAdded(Editor source, Token token)
	{
		filler.setHeight(exponent.getElement().getStyle().getHeight());
		if (source == exponent)
		{
			if (getEditor() != null)
			{
				getEditor().onTokenAdded(getEditor(), source);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void onTokenRemoved(Editor source)
	{
		onTokenAdded(source, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void propagateAdd(TokenFactory factory)
	{
		exponent.add(factory);
	}

	/**
	 * @return The editor for the exponent.
	 */
	public Editor getExponent()
	{
		return exponent;
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
		Exponent exp = new Exponent();
		// Editors will always return an Entity which is really an
		// EntityContainer.
		Entity baseContainer = base.toEntity();
		Entity expContainer = exponent.toEntity();

		exp.setBase((EntityContainer) baseContainer);
		exp.setExponent((EntityContainer) expContainer);
		return exp;
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
			return new ExponentToken(parent);
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
