package com.wmd.client.editor;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;

/**
 * Creates a toolbar to attach to an editor.
 * 
 * @author Eric C. Abruzzese and Philip Diffenderfer
 * 
 */
public class EditorToolbar extends Composite
{

	/*
	 * The FlexTable to keep track of cells.
	 */
	private FlexTable buttonBar;

	public EditorToolbar()
	{
		this.buttonBar = new FlexTable();

		initWidget(this.buttonBar);
	}

	/**
	 * Adds a button to the toolbar in the specified cell.
	 * 
	 * @param row
	 *            Row of the target cell
	 * @param column
	 *            Column of the target cell.
	 * @param b
	 *            Button to insert. May be regular button or
	 *            EditorToolbarButton.
	 */
	public void addButton(int row, int column, Button b)
	{
		this.buttonBar.setWidget(row, column, b);
	}

}
