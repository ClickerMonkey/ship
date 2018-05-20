package com.wmd.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wmd.client.msg.PeriodMsg;


/**
 * Get all the periods in the database.
 * 
 * @author Philip Diffenderfer, Kevin Rexroth
 */
public interface GetPeriodsServiceAsync
{

	/**
	 * Gets all periods from the database.
	 */
	void getPeriods(AsyncCallback<List<PeriodMsg>> callback);

}
