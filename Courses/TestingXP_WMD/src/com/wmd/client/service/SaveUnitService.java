package com.wmd.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Adds a unit to the database.
 * 
 * @author Philip Diffenderfer, Kevin Rexroth
 */
@RemoteServiceRelativePath("save_unit")
public interface SaveUnitService extends RemoteService
{

	/**
	 * Saves a unit to the database. If the unit already exists in the database
	 * then this returns false. If the unit has been added to the database then
	 * this returns true.
	 * 
	 * @param unit The unit to save to the database.
	 * @return True if the unit was saved, false if it existed already.
	 */
	boolean saveUnit(String unit);

}