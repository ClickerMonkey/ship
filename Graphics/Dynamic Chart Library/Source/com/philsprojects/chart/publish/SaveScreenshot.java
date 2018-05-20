package com.philsprojects.chart.publish;

import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.philsprojects.chart.publish.Screenshot.Type;

public class SaveScreenshot implements Runnable
{

    // The screen shot to save.
    private final Screenshot shot;

    /**
     * Initializes a Save Screen Shot Utility given the screen shot to save.
     * 
     * @param screenshot => The screen shot to save.
     */
    public SaveScreenshot(Screenshot screenshot)
    {
	shot = screenshot;
    }
    
    public void run()
    {
	// Create the save dialog box with a meaningful title.
	FileDialog dialog = new FileDialog(new Frame(), "Save Screenshot", FileDialog.SAVE);
	
	// Only allow the extension used to create
	dialog.setFile("*." + shot.type.extension);

	// Show the dialog.
	dialog.setVisible(true);
	
	// Wait for the user to select a file, or select 'CANCEL'
	String filename = dialog.getFile();

	
	// If no file was specified notify the user and exit.
	if (filename == null)
	{
	    JOptionPane.showMessageDialog(shot.source, "No screenshot saved.");
	    return;
	}

	// Build the full path of the file.
	String separator = System.getProperty("file.separator");
	String filepath = dialog.getDirectory() + separator + filename;
	
	// Create the file object...
	File file = new File(filepath);
	
	// Attempt to save the screen shot now...
	try
	{
	    boolean written = ImageIO.write(shot.image, shot.type.extension, file);
	    
	    if (!written)
		JOptionPane.showMessageDialog(shot.source, "That image type could not be saved!");
	}
	catch (Exception e)
	{
	    JOptionPane.showMessageDialog(shot.source, e.getMessage());
	}
    }
    
    
    

    public static Thread open(Component component, Type type)
    {
	// Immediately create a mirror image of the component.
	Screenshot screenshot = new Screenshot(component, type);
	
	// Create the save screen shot utility.
	SaveScreenshot saver = new SaveScreenshot(screenshot);

	// Run the saver in a separate thread.
	Thread saveThread = new Thread(saver);

	// Start the thread and return it.
	saveThread.start();
	
	return saveThread;
    }
    
}
