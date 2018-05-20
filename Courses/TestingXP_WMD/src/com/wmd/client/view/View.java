package com.wmd.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

/**
 * The abstract class that contains common components for the views.
 * 
 * @author Eric C. Abruzzese
 * 
 */
public abstract class View extends Composite
{

	private FlexTable layout = new FlexTable();
	private FlexCellFormatter formatter = this.layout.getFlexCellFormatter();

	/**
	 * Initializes the FlexTable as a widget.
	 */
	protected View()
	{
		this.getLayout().setWidth("960px");
		initWidget(layout);
	}

	/**
	 * @return the FlexTable being used for the sub-view.
	 */
	protected FlexTable getLayout()
	{
		return this.layout;
	}

	/**
	 * @return the FlexCellFormatter being used for the sub-view.
	 */
	protected FlexCellFormatter getFormatter()
	{
		return this.formatter;
	}

}
