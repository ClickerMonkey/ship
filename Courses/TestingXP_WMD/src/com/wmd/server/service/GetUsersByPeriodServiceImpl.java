package com.wmd.server.service;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wmd.client.msg.UserMsg;
import com.wmd.client.service.GetUsersByPeriodService;
import com.wmd.server.db.StudentList;

/**
 * Service that return a list of students for period.
 * @author Olga Zalamea
 */
public class GetUsersByPeriodServiceImpl extends RemoteServiceServlet implements
	GetUsersByPeriodService
{

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Method that will send the period and get a list of all students in that period
	 * 
	 * @param period
	 *            - String the period
	 * @return List<UserMsg> list of students
	 */
	public List<UserMsg> getUsers(String period)
	{
		try
		{
			StudentList list = new StudentList();
			return list.getStudents(period);
		} catch (Exception e)
		{
			e.printStackTrace();
			GWT.log("Error in gesUSers(String "+ period +")", e);
			return null;
		}
	}
}
