package com.wmd.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wmd.client.msg.UserMsg;

/**
 * @author Sam Storino and Steve Jurnack
 * 
 *         This service is the Async for GetUserService, and can be used to get
 *         a user from the database
 * 
 */
public interface GetUserServiceAsync
{

	/**
	 * @param id
	 *            - this is the user id that will be passed through when getUser
	 *            is called, and used to get the user message from the database
	 * @param callback
	 *            - Asynchronous callback to get the UserMsg from the database
	 */
	void getUser(int id, AsyncCallback<UserMsg> callback);

}
