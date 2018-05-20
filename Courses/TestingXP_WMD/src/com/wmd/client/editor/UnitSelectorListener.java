package com.wmd.client.editor;

import java.util.ArrayList;

/**
 * A listener notified when a UnitSelectorDialog is 
 * 
 * @author Philip Diffenderfer, Eric Abruzzese
 *
 */
public interface UnitSelectorListener
{
	
	/**
	 * Method invoked when a user is done editing a list of units.
	 * 
	 * @param units The list of units returned by the dialog.
	 */
	public void unitsSelected(ArrayList<String> units, String correct);
	
}
