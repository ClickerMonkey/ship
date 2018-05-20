package com.wmd.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Gets the list of units from the database.
 * 
 * @author Philip Diffenderfer, Kevin Rexroth
 */
@RemoteServiceRelativePath("get_units")
public interface GetUnitsService extends RemoteService
{

	/**
	 * Returns a list of all of the units in the database.
	 * 
	 * @return A list containing all of the units as strings.
	 */
	List<String> getUnits();

}