package com.wmd.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wmd.client.msg.Level;
import com.wmd.client.msg.Role;

/**
 * Given a user's state (in a UserMsg object), updates that user's entry in the
 * database.
 * 
 * @author Stephen Jurnack and Sam Storino
 * 
 */
@RemoteServiceRelativePath("save_update_user")
public interface SaveUpdateUserService extends RemoteService
{
	/**
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param role
	 * @param username
	 * @param password
	 * @param level
	 * @param period
	 * @param enabled
	 *            - All these parameters are pulled out of a UserMsg object and
	 *            should be sent in as null if they do not apply to the user
	 *            type (Level = null for instructors, etc)
	 * @return returns a boolean object depending on the operation's success.
	 */
	boolean saveUpdateUser(int id, String firstName, String lastName,
			Role role, String username, String password, Level level,
			String period, boolean enabled);
}
