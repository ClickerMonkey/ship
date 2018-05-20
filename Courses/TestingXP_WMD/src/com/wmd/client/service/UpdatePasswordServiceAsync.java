package com.wmd.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Async call for UpdatePasswordService
 * @author William Fisher and Chris Eby
 *
 */

public interface UpdatePasswordServiceAsync
{
    /**
     * Async call for the UpdatePassword. Returns true if successful.
     * @param password String Password it is to be changed to.
     * @param userId int The user id of the user thats password is to be changed
     * @param callback boolean Value representing success or failure
     */
    void updateUserPassword(String password, int userId,
            AsyncCallback<Boolean> callback);
}
