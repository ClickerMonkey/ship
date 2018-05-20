package com.wmd.client.widget;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;
import com.wmd.client.ApplicationWrapper;
import com.wmd.client.view.LoginView;
/**
 * This class returns or creates the menu bar.
 * 
 * @author Chris Koch, Paul Cheney, Eric Abruzzese
 * 
 */
public class ApplicationMenuBar extends MenuBar
{
	/*
	 * The instance of the ApplicationMenuBar to be used
	 */
	private static ApplicationMenuBar instance = new ApplicationMenuBar();
	Command exitCommand = new Command()
	{
		
		/** This method is the command that does the log out and clean up.
		 * @see com.google.gwt.user.client.Command#execute()
		 */
		@Override
		public void execute()
		{
			ApplicationWrapper aw = ApplicationWrapper.getInstance();
			RootPanel.get("application_html_wrapper").clear();
			aw.setDashboard(new LoginView());
			aw.clearWidget();
			System.out.println("exit");
		}
	};
	private ApplicationMenuBar()
	{
	}
	/**
	 * This method uses the parent method and reads the newly cleared exit menu.
	 */
	public void clearItemsNew()
	{
		this.clearItems();
		this.addItem("Exit", exitCommand);
	}
	/**
	 * 
	 * @return new instance of ApplicationMenuBar or current instance if one has
	 *         already been created
	 */
	public static ApplicationMenuBar getInstance()
	{
		if (instance == null)
		{
			instance = new ApplicationMenuBar();
		}
		return instance;
	}
}
