package com.wmd.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wmd.client.msg.UserAssignmentStatusMsg;
/**
 * TODO
 * @author Christopher Eby and William Fisher
 *
 */
public interface GetAllUserAssignmentStatusServiceAsync
{

	void getAllUserAssignmentStatus(int assignmentId,
			AsyncCallback<List<UserAssignmentStatusMsg>> callback);

}
