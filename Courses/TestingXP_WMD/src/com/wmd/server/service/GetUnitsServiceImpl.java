package com.wmd.server.service;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wmd.client.service.GetUnitsService;
import com.wmd.server.db.UnitList;

/**
 * Gets the list of units from the database.
 * 
 * @author Philip Diffenderfer, Kevin Rexroth
 */
public class GetUnitsServiceImpl extends RemoteServiceServlet implements GetUnitsService
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3368017108979759400L;

	/**
	 * Returns a list of all of the units in the database.
	 * 
	 * @return A list containing all of the units as strings.
	 */
	@Override
	public List<String> getUnits()
	{
		return new UnitList().getUnits();
	}
	
}
