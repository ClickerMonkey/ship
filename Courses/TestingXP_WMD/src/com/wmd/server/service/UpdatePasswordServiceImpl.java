package com.wmd.server.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wmd.client.service.UpdatePasswordService;
import com.wmd.server.db.Student;
/**
 * Updates the user password.
 * @author Christopher Eby and Bill Fisher
 *
 */
public class UpdatePasswordServiceImpl extends RemoteServiceServlet implements UpdatePasswordService
{
	/**
	 * The serial version ID
	 */
	private static final long serialVersionUID = 2486204153976981670L;
	
	/**
	 * Updates the user password. Takes a password as a String and a userId
	 * as an int.
	 * @return boolean false if fails, true if passes
	 */
	public boolean updateUserPassword(String password, int userId)
	{
		boolean test = true;
		try
		{
			Student student = new Student(userId);
			student.setPassword(password);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			GWT.log("Error in updateUserPassword()", e);
			test = false;
		}
		return test;
	}
}
