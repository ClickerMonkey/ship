package com.wmd.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wmd.client.msg.UserAssignmentStatusMsg;
/*
 * TODO
 * @author Christopher Eby and William Fisher
 *
 */
@RemoteServiceRelativePath("get_all_user_assignment_status")
public interface GetAllUserAssignmentStatusService extends RemoteService
{
	List<UserAssignmentStatusMsg> getAllUserAssignmentStatus(int assignmentId);
}
