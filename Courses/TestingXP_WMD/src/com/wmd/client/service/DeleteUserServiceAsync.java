package com.wmd.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The service used to delete a user.
 * 
 * @author Andrew Marx, Chris Koch
 * @since 2010 April 12
 * @see DeleteUserService
 * 
 */
public interface DeleteUserServiceAsync
{
	/**
	 * Delete a user with the specified id from the database.
	 * 
	 * @param userId
	 *            The user id of the user to be deleted.
	 * @param callback
	 *            The asyncrhonous callback containing a Boolean, representing
	 *            the success of the deletion operation.
	 */
	void deleteUser(int userId, AsyncCallback<Boolean> callback);

}
