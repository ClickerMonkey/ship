package com.wmd.server.service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wmd.client.service.SaveUnitService;
import com.wmd.server.db.Unit;

/**
 * Adds a unit to the database.
 * 
 * @author Philip Diffenderfer, Kevin Rexroth
 */
public class SaveUnitServiceImpl extends RemoteServiceServlet implements SaveUnitService
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3893237586582491848L;

	/**
	 * Saves a unit to the database. If the unit already exists in the database
	 * then this returns false. If the unit has been added to the database then
	 * this returns true.
	 * 
	 * @param unit The unit to save to the database.
	 * @return True if the unit was saved, false if it existed already.
	 */
	@Override
	public boolean saveUnit(String unit)
	{
		return new Unit().save(unit);
	}
	
}
