package com.wmd.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MathRocket implements EntryPoint
{

	private Widget currentPanel;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad()
	{
		this.currentPanel = new ApplicationWrapper();

		RootPanel.get("application_html_wrapper").add(this.currentPanel);
	}

}
