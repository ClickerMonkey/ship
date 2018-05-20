package com.philsprojects.chart.publish;

import java.awt.Component;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;

import javax.swing.JOptionPane;

import com.philsprojects.chart.publish.Screenshot.Type;

public class PrintScreenshot implements Runnable
{

    // The screen shot to print.
    private final Screenshot shot;

    /**
     * Initializes a Print Screen Shot Utility given the screen shot to print.
     * 
     * @param screenshot => The screen shot to print.
     */
    public PrintScreenshot(Screenshot screenshot)
    {
	shot = screenshot;
    }

    /**
     * Prompts the user with a page setup dialog and a print dialog. 
     * The screen shot is the only thing printed, and on a single page.
     */
    public void run()
    {
	// Create a printer job.
	PrinterJob job = PrinterJob.getPrinterJob();

	// The name of the print job which shows up to the user.
	job.setJobName("Statistics Screenshot");


	// Show the page setup dialog to get the page format.
	PageFormat format = job.pageDialog(job.defaultPage());

	// Set the screen shot as the printable object using the
	// created page format.
	job.setPrintable(shot, format);

	// Show the print dialog...
	if (job.printDialog()) 
	{
	    // If everything is valid and 'print' was selected then attempt to
	    // print, on failed printing show the error message.
	    try 
	    {
		job.print();
	    }
	    catch (Exception e) 
	    {
		JOptionPane.showMessageDialog(shot.source, e.getMessage());
	    }
	}
    }


    public static Thread open(Component component, Type type)
    {
	// Immediately create a mirror image of the component.
	Screenshot screenshot = new Screenshot(component, type);

	// Create the print screen shot utility.
	PrintScreenshot printer = new PrintScreenshot(screenshot);

	// Run the printer in a separate thread.
	Thread printThread = new Thread(printer);

	// Start the thread and return it.
	printThread.start();

	return printThread;
    }


}
