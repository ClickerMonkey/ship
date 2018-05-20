package com.wmd.client.editor;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.wmd.client.entity.Entity;
import com.wmd.client.entity.EntityContainer;
import com.wmd.client.entity.Fraction;

/**
 * A token the represents the fraction entity.
 * 
 * @author Philip Diffenderfer
 * 
 */
public class FractionToken extends Token implements EditorListener
{

	// The panel holding the numerator ontop of the denominator.
	private VerticalPanel panel;

	// The numerator editor
	private Editor numerator;

	// The denominator editor
	private Editor denominator;


	/**
	 * Initializes a new FractionToken.
	 * 
	 * @param parent
	 *            The editor which contains this token.
	 */
	public FractionToken(Editor parent)
	{
		this(parent, null, null);
	}
	
	/**
	 * Initializes a new FractionToken.
	 * 
	 * @param parent
	 *            The editor which contains this token.
	 * @param numEntity
	 * 			The entity to populate the numerator editor with.
	 * @param denEntity
	 * 			The entity to populate the denominator editor with.
	 */
	public FractionToken(Editor parent, Entity numEntity, Entity denEntity)
	{
		super(parent);
		panel = new VerticalPanel();

		numerator = new Editor(parent);
		numerator.setWidth("30px");
		numerator.fromEntity(numEntity);
		numerator.getElement().getStyle().setMarginLeft(4.0, Unit.PX);
		numerator.getElement().getStyle().setMarginRight(4.0, Unit.PX);
		numerator.addListener(this);

		Label bar = createBar();

		denominator = new Editor(parent);
		denominator.setWidth("30px");
		denominator.fromEntity(denEntity);
		denominator.getElement().getStyle().setMarginLeft(4.0, Unit.PX);
		denominator.getElement().getStyle().setMarginRight(4.0, Unit.PX);
		denominator.addListener(this);

		panel.add(numerator);
		panel.add(bar);
		panel.add(denominator);
		panel.setSize("30px", "40px");

		panel.setCellHeight(numerator, "100%");
		panel.setCellWidth(numerator, "100%");
		panel.setCellHorizontalAlignment(numerator,
				HasHorizontalAlignment.ALIGN_CENTER);

		panel.setCellWidth(bar, "100%");

		panel.setCellHeight(denominator, "100%");
		panel.setCellWidth(denominator, "100%");
		panel.setCellHorizontalAlignment(denominator,
				HasHorizontalAlignment.ALIGN_CENTER);

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
	 * @return A label 1px tall which represents the bar of the fraction.
	 */
	private Label createBar()
	{
		Label bar = new Label();
		Style s = bar.getElement().getStyle();
		s.setHeight(1.0, Unit.PX);
		s.setBackgroundColor("#000");
		return bar;
	}

	/**
	 * {@inheritDoc}
	 */
	public void onEmpty(Editor source)
	{

	}

	/**
	 * {@inheritDoc}
	 */
	public void onTokenAdded(Editor source, Token token)
	{
		if (source == numerator || source == denominator)
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
		numerator.add(factory);
		denominator.add(factory);
	}

	/**
	 * @return The editor for the numerator.
	 */
	public Editor getNumerator()
	{
		return numerator;
	}

	/**
	 * @return The editor for the denominator.
	 */
	public Editor getDenominator()
	{
		return denominator;
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
		Fraction fraction = new Fraction();
		// Editors will always return an Entity which is really an
		// EntityContainer.
		Entity numContainer = numerator.toEntity();
		Entity denContainer = denominator.toEntity();

		fraction.setNumerator((EntityContainer) numContainer);
		fraction.setDenominator((EntityContainer) denContainer);
		return fraction;
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
			return new FractionToken(parent);
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
