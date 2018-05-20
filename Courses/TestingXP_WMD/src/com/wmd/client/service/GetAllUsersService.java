package com.wmd.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wmd.client.msg.Role;
import com.wmd.client.msg.UserMsg;

/**
 * This service should be used to fetch a list of users from the database.
 * 
 * @author Andrew Marx, Chris Koch
 * @since 2010 April 12
 * 
 */
@RemoteServiceRelativePath("get_all_users")
public interface GetAllUsersService extends RemoteService
{
	/**
	 * Returns an ArrayList of every user having the specified role (i.e.
	 * student, instructor) in the database.
	 * 
	 * @param role
	 *            The type of user to retrieve (i.e. student, instructor, admin)
	 * @return An ArrayList of UserMsg objects, one for each student in the
	 *         database.
	 */
	public ArrayList<UserMsg> getAllUsers(Role role);


}
