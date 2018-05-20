package com.wmd.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wmd.client.msg.UserMsg;

/**
 * Given the username and the password, checks to see if the user is a valid one
 * and if so signs them in
 * 
 * @author Scotty Rhinehart and Sam Storino
 */
@RemoteServiceRelativePath("user_signin")
public interface UserSignInService extends RemoteService
{

	/**
	 * Updates the status of the given problem depending on whether the user
	 * answered it correctly.
	 * 
	 * @param user
	 *            String for username
	 * @param password
	 *            String for password
	 * @return the users userMsg
	 */
	UserMsg signInUser(String user, String password);

}
