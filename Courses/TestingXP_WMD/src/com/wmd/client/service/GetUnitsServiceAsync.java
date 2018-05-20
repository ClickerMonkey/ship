package com.wmd.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Gets the list of units from the database.
 * 
 * @author Philip Diffenderfer, Kevin Rexroth
 */
public interface GetUnitsServiceAsync
{

	/**
	 * Returns a list of all of the units in the database.
	 * 
	 * @param callback The callback invoked then the service returns with units.
	 * @return A list containing all of the units as strings.
	 */
	void getUnits(AsyncCallback<List<String>> callback);
}
