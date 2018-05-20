package com.wmd.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wmd.client.msg.PeriodMsg;


/**
 * Get all the periods in the database.
 * 
 * @author Philip Diffenderfer, Kevin Rexroth
 */
@RemoteServiceRelativePath("get_periods")
public interface GetPeriodsService extends RemoteService
{

	/**
	 * Gets all periods from the database.
	 * 
	 * @return The list of periods.
	 */
	public List<PeriodMsg> getPeriods();

}