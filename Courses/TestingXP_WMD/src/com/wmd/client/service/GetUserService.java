package com.wmd.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wmd.client.msg.UserMsg;

/**
 * @author Sam Storino and Steve Jurnack
 * 
 *         Gets a user from the database and sends it out as a user message
 * 
 */
@RemoteServiceRelativePath("get_user")
public interface GetUserService extends RemoteService
{

	/**
	 * @param id
	 *            - used to get the appropriate user message from the database
	 * @return UserMsg
	 */
	public UserMsg getUser(int id);

}
