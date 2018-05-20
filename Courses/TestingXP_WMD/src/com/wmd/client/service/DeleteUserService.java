package com.wmd.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * This service should be used to delete a user. There are no checks that the
 * user calling the service has the appropriate permissions to delete users.
 * 
 * @author Andrew Marx, Chris Koch
 * @since 2010 April 12
 * 
 */
@RemoteServiceRelativePath("delete_user")
public interface DeleteUserService extends RemoteService
{
	/**
	 * Delete the user having the specified user id. Returns true on success,
	 * false on failure.
	 * 
	 * @param userId
	 *            The id of the user to be deleted.
	 * @return True on success, false on failure.
	 */
	public boolean deleteUser(int userId);

}
