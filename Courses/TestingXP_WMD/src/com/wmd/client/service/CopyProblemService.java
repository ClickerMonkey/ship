package com.wmd.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wmd.client.msg.Level;

/**
 * Copies a problem from one level to another given the problem message and its
 * destination level to copy to.
 * 
 * @author Sam Storino, Scotty Rhinehart, AJ Marx
 */
@RemoteServiceRelativePath("copy_problem")
public interface CopyProblemService extends RemoteService
{

	/**
	 * Given the id of a problem return the associated ProblemStatement
	 * instance.
	 * 
	 * @param msg
	 *            - The problem being copied
	 * @param level
	 *            - The level of the problem being copied
	 * @return true on success and false on failure
	 */
	int copyProblem(int problemId, Level levelTo);

}
