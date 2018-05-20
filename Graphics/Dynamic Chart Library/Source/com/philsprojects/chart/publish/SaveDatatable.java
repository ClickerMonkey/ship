package com.philsprojects.chart.publish;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import javax.swing.JOptionPane;

import com.philsprojects.chart.Legend;
import com.philsprojects.chart.LegendDefinition;
import com.philsprojects.chart.data.Dataset;
import com.philsprojects.chart.data.Datatable;

public class SaveDatatable implements Runnable
{

    // The DataTable to save.
    private final Datatable table;

    // The names/labels of each data list.
    private final String[] names;

    /**
     * Initializes a Save DataTable Utility given the DataTable to save.
     * 
     * @param datatable => The DataTable to save.
     */
    public SaveDatatable(Datatable datatable, String[] listNames)
    {
	table = datatable;
	names = listNames;
    }


    public void run()
    {
	// Create the save dialog box with a meaningful title.
	FileDialog dialog = new FileDialog(new Frame(), "Save Datatable", FileDialog.SAVE);

	// Only allow the extension used to create
	dialog.setFile("*.csv");

	// Show the dialog.
	dialog.setVisible(true);

	// Wait for the user to select a file, or select 'CANCEL'
	String filename = dialog.getFile();

	// If no file was specified notify the user and exit.
	if (filename == null)
	{
	    JOptionPane.showMessageDialog(null, "No screenshot saved.");
	    return;
	}

	// Build the full path of the file.
	String separator = System.getProperty("file.separator");
	String filepath = dialog.getDirectory() + separator + filename;

	// Create the file object...
	File file = new File(filepath);

	// Create the Output stream
	PrintStream output = null;
	try
	{
	    output = new PrintStream(file);
	}
	catch (FileNotFoundException e)
	{
	    JOptionPane.showMessageDialog(null, "Error saving data to file: \n" + e.getMessage());
	    return;
	}

	// Unload the DataTable.
	saveTo(output);

	// Close everything up.
	output.flush();
	output.close();
    }

    private void saveTo(PrintStream out)
    {
	int dims = table.getDimension();
	int lists = table.getListCount();
	int sets = table.getSize();

	// Write the header
	out.print(',');
	for (int i = 0; i < lists; i++)
	{
	    out.print(names[i]);

	    for (int j = 0; j < dims; j++)
		out.print(',');
	}
	out.println();

	// Write the data sets.
	Dataset set;
	for (int i = 0; i < sets; i++)
	{
	    set = table.get(i);

	    // Write the dataset's name.
	    out.print(convert(set.getName()));

	    // Write out each list, and their dimensions.
	    for (int l = 0; l < lists; l++)
	    {
		for (int d = 0; d < dims; d++)
		{
		    out.print(',');
		    out.print(set.getData(l, d));
		}
	    }
	    out.println();
	}
    }

    private String convert(String s)
    {
	StringBuilder sb = new StringBuilder(s.length());

	char[] chars = s.toCharArray();
	for (char c : chars)
	{
	    if (c == '"')
		sb.append('"');

	    sb.append(c);
	}

	return sb.toString();
    }


    public static Thread open(Datatable table, Legend legend)
    {
	LegendDefinition[] defs = legend.getDefinitions();

	String[] names = new String[defs.length];

	for (int i = 0; i < defs.length; i++)
	    names[i] = defs[i].getLabel();

	return open(table, names);
    }

    public static Thread open(Datatable table, String[] listNames)
    {
	// Create the save screen shot utility.
	SaveDatatable saver = new SaveDatatable(table, listNames);

	// Run the saver in a separate thread.
	Thread saveThread = new Thread(saver);

	// Start the thread and return it.
	saveThread.start();

	return saveThread;
    }

}
