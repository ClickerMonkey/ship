package com.wmd.client.widget;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DialogBox;

/**
 * 
 * @author Philip Diffenderfer, Eric Abruzzese
 * 
 */
public class LoadingWidget
{

	/**
	 * The minimum time in milliseconds to display the LoadingWidget.
	 */
	public static final long SLEEP_TIME = 1000;

	// The dialog to display.
	private final DialogBox dialog;

	// The time (in milliseconds) when the dialog was displayed.
	private long startTime;

	/**
	 * Initializes a new LoadingWidget given its text.
	 * 
	 * @param text
	 *            The text in the widget.
	 */
	public LoadingWidget(String text)
	{
		dialog = new DialogBox();
		dialog.setGlassEnabled(true);
		dialog.setText(text);

		AbsolutePanel panel = new AbsolutePanel();
		panel.setSize("240px", "100px");
		panel.addStyleName("ajax-loader");

		dialog.add(panel);
	}

	/**
	 * Shows the loading widget and keeps track of the time called.
	 */
	public void show()
	{
		dialog.center();
		startTime = System.currentTimeMillis();
	}

	/**
	 * Hides the dialog after SLEEP_TIME has elapsed since the show.
	 */
	public void hide()
	{
		long current = System.currentTimeMillis();

		// Remaining millis to sleep.
		int sleep = (int) (SLEEP_TIME - (current - startTime));

		// If it's already surpassed SLEEP_TIME then just hide the widget.
		if (sleep < 0)
		{
			dialog.hide();
		} else
		{
			// Set a timer to go off in the remaining time to hide the widget.
			Timer hideTimer = new Timer()
			{
				@Override
				public void run()
				{
					dialog.hide();
				}
			};
			hideTimer.schedule(sleep);
		}
	}

}
