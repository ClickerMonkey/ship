package com.wmd.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
/**
 * Service call for UpdatePasswordService
 * @author William Fisher and Chris Eby
 *
 */
@RemoteServiceRelativePath("update_password")
public interface UpdatePasswordService extends RemoteService
{
    /**
     * Service call for the UpdatePassword. Returns true if successful.
     * @param password String Password it is to be changed to.
     * @param userId int The user id of the user thats password is to be changed
     */
    boolean updateUserPassword(String password, int userId);
}
