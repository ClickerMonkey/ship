package com.wmd.server.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wmd.client.msg.UserMsg;
import com.wmd.client.service.UserSignInService;
import com.wmd.server.db.Student;

/**
 * Service that allows a user to sign in.
 * @author Scotty Rhinehart and Sam Storino
 * @refactor Olga Zalamea
 */
public class UserSignInServiceImpl extends RemoteServiceServlet implements
		UserSignInService
{

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Method that will send the username and the password to the handler to
	 * determine if they correspond to a user in the database of users.
	 * 
	 * @param user
	 *            - String the username
	 * @param password
	 *            - String the password
	 * 
	 * @return UserMsg for the user
	 */
	public UserMsg signInUser(String user, String password)
	{
		try
		{
			Student stu = new Student(user, password);
			UserMsg msg = stu.signinUser();
			return msg;
		} catch (Exception e)
		{
			e.printStackTrace();
			GWT.log("Error in log-in for student", e);
			return null;
		}
	}
}
