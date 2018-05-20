package com.wmd.client.editor;

import java.util.ArrayList;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.wmd.client.entity.Entity;
import com.wmd.client.entity.Unit;

/**
 * A token for a units selection dialog.
 * 
 * @author Philip Diffenderfer
 *
 */
public class UnitsToken extends Token implements UnitSelectorListener
{
	
	// The arraylist of options
	private ArrayList<String> units;
	
	// The correct option
	private String correct;
	
	// The listbox holding the options.
	private ListBox unitBox;
	
	/**
	 * Instantiates a new UnitsToken.
	 * 
	 * @param parent
	 *            The editor which contains this token.
	 */
	public UnitsToken(Editor parent)
	{
		this(parent, new ArrayList<String>(), "");
	}
	
	/**
	 * Instantiates a new UnitsToken.
	 * 
	 * @param parent
	 * 			The editor which contains this token.
	 * @param units
	 * 			The list of units to choose from.
	 * @param correct
	 * 			The correct unit.
	 */
	public UnitsToken(Editor parent, ArrayList<String> units, String correct)
	{
		super(parent);
		
		this.units = units;
		this.correct = correct;

		this.unitBox = new ListBox();
		this.unitBox.setVisibleItemCount(1);
		this.unitBox.setEnabled(false);
		this.unitBox.addItem(correct);
		
		this.initWidget(unitBox);
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
	protected boolean isAnEntity()
	{
		return true;
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
	protected String toAppendableString()
	{
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Entity toEntity()
	{
		return new Unit(correct, units);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void unitsSelected(ArrayList<String> units, String correct)
	{
		this.units = units;
		this.correct = correct;
	}
	


	/**
	 * Creates the factory used to instantiate tokens of this type.
	 */
	private static TokenFactory factory = new TokenFactory()
	{
		@Override
		public Token createToken(Editor parent)
		{
			return new UnitsToken(parent);
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
	

	/**
	 * Returns a new TokenFactory for the given units.
	 * 
	 * @param units The list of units.
	 * @param correct The correct option.
	 * @return The created TokenFactory.
	 */
	public static TokenFactory getFactory(final ArrayList<String> units, final String correct) 
	{
		return new TokenFactory() {
			@Override
			public Token createToken(Editor parent)
			{
				return new UnitsToken(parent, units, correct);
			}
		};
	}
	
}
