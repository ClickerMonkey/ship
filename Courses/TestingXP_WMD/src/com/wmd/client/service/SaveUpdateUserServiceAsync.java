package com.wmd.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wmd.client.msg.Level;
import com.wmd.client.msg.Role;

/**
 * The Async for SaveUpdateUserService
 * 
 * @author Stephen Jurnack and Sam Storino
 * 
 */
public interface SaveUpdateUserServiceAsync
{

	/**
	 * @param user
	 *            - the UserMsg object to be inserted into the database
	 * @param callback
	 *            - the callback containing the success variable of the
	 *            operation.
	 */
	void saveUpdateUser(int id, String firstName, String lastName,
			Role role, String username, String password, Level level,
			String period, boolean enabled, AsyncCallback<Boolean> callback);

}
