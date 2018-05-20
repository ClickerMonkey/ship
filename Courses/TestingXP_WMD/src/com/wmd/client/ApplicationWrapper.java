package com.wmd.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.wmd.client.view.LoginView;

/**
 * ApplicationWrapper
 * 
 * The ApplicationWrapper provides a well-formed container to host the various
 * Views. This class is a Singleton, and can only be accessed via getInstance().
 * 
 * An ApplicationWrapper basically hold two widgets. The dashboard, the menubar
 * type thing at the top, as well as the main widget at the bottom. Use
 * setWidget() and/or clearWidget() to change the contents of this page.
 * 
 * @author Eric Abruzzese and Paul Cheny, Andrew Marx, Chris Koch
 * 
 */
public class ApplicationWrapper extends Composite
{
	/*
	 * The instance of the ApplicationWrapper to be used
	 */
	private static ApplicationWrapper instance = new ApplicationWrapper();

	private VerticalPanel currentWidgetPanel = new VerticalPanel();
	private SimplePanel dashboardPanel = new SimplePanel();
	private Widget currentWidget = new LoginView();

	private FlexTable applicationTable = new FlexTable();
	private FlexCellFormatter applicationTableFormatter = this.applicationTable
			.getFlexCellFormatter();

	protected ApplicationWrapper()
	{
		// Table properties
		this.applicationTable.setWidth("100%");
		this.applicationTable.setHeight("100%");
		this.applicationTable.setCellPadding(0);
		this.applicationTable.setCellSpacing(0);

		// Current widget cell
		this.applicationTableFormatter.setHeight(1, 0, "100%");
		this.applicationTableFormatter.setHorizontalAlignment(1, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		this.currentWidgetPanel.setWidth("960px");
		this.currentWidgetPanel.setHeight("100%");
		this.currentWidgetPanel.add(this.currentWidget);

		this.applicationTableFormatter.setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		this.applicationTable.setWidget(0, 0, this.dashboardPanel);
		this.applicationTableFormatter.setHorizontalAlignment(1, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		this.applicationTable.setWidget(1, 0, this.currentWidgetPanel);

		initWidget(this.applicationTable);
	}

	/**
	 * @return the singleton instance of the ApplicationWrapper (there should
	 *         only ever be one)
	 */
	public static ApplicationWrapper getInstance()
	{
		if (instance == null)
		{
			instance = new ApplicationWrapper();
		}
		return instance;
	}

	/**
	 * Sets the view inside of the application wrapper.
	 * 
	 */
	@Override
	public void setWidget(Widget w)
	{
		this.currentWidgetPanel.clear();
		this.currentWidgetPanel.add(w);
		refresh();
	}

	/**
	 * Removes the bottom widget.
	 * 
	 */
	public void clearWidget()
	{
		this.currentWidgetPanel.clear();
		refresh();
	}

	/**
	 * Sets the bottom/main widget.
	 * 
	 * @param w
	 *            A new widget.
	 */
	public void setDashboard(Widget w)
	{
		dashboardPanel.clear();
		dashboardPanel.setWidget(w);
		refresh();
	}

	private void refresh()
	{
		RootPanel.get("application_html_wrapper").add(this);
	}
}
