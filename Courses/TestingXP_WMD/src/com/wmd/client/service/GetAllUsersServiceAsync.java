package com.wmd.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wmd.client.msg.Role;
import com.wmd.client.msg.UserMsg;

/**
 * This service should be used to fetch a list of students from the database.
 * 
 * @author Andrew Marx, Chris Koch
 * @since 2010 April 12
 * @see GetAllUsersService
 */
public interface GetAllUsersServiceAsync
{

	/**
	 * Gets an ArrayList of every user having the specified role (i.e. student,
	 * instructor) in the database.
	 * 
	 * @param role
	 *            The type of user to retrieve (i.e. student, instructor, admin)
	 * @param callback
	 *            The asynchronous call back containing the ArrayList of UserMsg
	 *            objects.
	 */
	void getAllUsers(Role role, AsyncCallback<ArrayList<UserMsg>> callback);



}
