package com.wmd.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wmd.client.msg.UserMsg;
/**
 * Gets the users by period.
 * @author Chris Eby and Bill Fisher
 */
@RemoteServiceRelativePath("get_users_by_period")
public interface GetUsersByPeriodService extends RemoteService
{
	public List<UserMsg> getUsers(String period);
}
