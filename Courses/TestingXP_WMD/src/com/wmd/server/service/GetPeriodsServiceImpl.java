package com.wmd.server.service;

import java.sql.SQLException;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wmd.client.msg.PeriodMsg;
import com.wmd.client.service.GetPeriodsService;
import com.wmd.server.db.PeriodList;

/**
 * Get all the periods in the database.
 * 
 * @author Philip Diffenderfer, Kevin Rexroth
 */
public class GetPeriodsServiceImpl extends RemoteServiceServlet implements
		GetPeriodsService
{

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1180075982634611675L;

	/**
	 * Gets all periods from the database.
	 * 
	 * @return The list of periods.
	 */
	@Override
	public List<PeriodMsg> getPeriods()
	{
		try
		{
			PeriodList handler = new PeriodList();
			return handler.getPeriods();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
