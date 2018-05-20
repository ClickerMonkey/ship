package com.wmd.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wmd.client.msg.UserMsg;

public interface GetUsersByPeriodServiceAsync
{

	void getUsers(String period, AsyncCallback<List<UserMsg>> callback);

}
