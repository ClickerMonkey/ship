package com.wmd.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Adds a unit to the database.
 * 
 * @author Philip Diffenderfer, Kevin Rexroth
 */
public interface SaveUnitServiceAsync
{

	/**
	 * Saves a unit to the database. If the unit already exists in the database
	 * then this returns false. If the unit has been added to the database then
	 * this returns true.
	 * 
	 * @param unit The unit to save to the database.
	 * @param callback The callback invoked when the call returns.
	 */
	void saveUnit(String unit, AsyncCallback<Boolean> callback);
}
