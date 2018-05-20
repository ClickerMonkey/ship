package com.wmd.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wmd.client.entity.ProblemStatement;
import com.wmd.client.msg.Level;
import com.wmd.client.msg.ProblemMsg;

/**
 * Saves the problem to the database. If the problem doesn't exist in the
 * database then it is inserted. If the problem does exist in the database then
 * it is updated with the new information.
 * 
 * @author Sam Storino and Steven Jurnack
 */
public interface SaveProblemServiceAsync
{

	/**
	 * Saves the given problem in the database.
	 * 
	 * @param problemid - the id of the problem being saved
	 * @param assignmentId - the assignment id of the problem being saved
	 * @param level - the difficulty level of the problem being saved
	 * @param name - the name of the problem being saved
	 * @param problemOrder - the order number of the problem
	 * @param statement - the problem statement of the problem being saved
	 *          
	 */
	void saveProblem(int problemId, int assignmentId, Level level, String name, int problemOrder,
			ProblemStatement statement, AsyncCallback<ProblemMsg> callback);

}
