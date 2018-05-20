package com.wmd.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wmd.client.msg.AssignmentMsg;

public interface AddAssignmentServiceAsync
{

	void addAssignment(String name, boolean enabled,
			AsyncCallback<AssignmentMsg> callback);

}
