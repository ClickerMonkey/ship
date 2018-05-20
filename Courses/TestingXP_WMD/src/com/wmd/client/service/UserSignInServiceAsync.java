package com.wmd.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wmd.client.msg.UserMsg;

/**
 * Given the username and password for a potential user, checks to see if they
 * correspond to each other in the database.
 * 
 * @author Scotty Rhinehart and Sam Storino
 */
public interface UserSignInServiceAsync
{

	/**
	 * Checks to see if the username and password can be signed in.
	 * 
	 * @param user
	 *            - String for the username
	 * @param password
	 *            - String for the password
	 * @param callback
	 *            The callback for the synchronous method.
	 */
	void signInUser(String user, String password,
			AsyncCallback<UserMsg> callback);

}
