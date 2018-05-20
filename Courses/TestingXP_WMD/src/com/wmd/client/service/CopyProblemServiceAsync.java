package com.wmd.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wmd.client.msg.Level;

/**
 * The Async for CopyProblemService
 * 
 * @author Sam Storino, Scotty Rhinehart, AJ Marx
 */
public interface CopyProblemServiceAsync
{

	/**
	 * Given the problem message, copies problem to different level specified
	 * 
	 * @author Sam Storino, Scotty Rhinehart, AJ Marx
	 * 
	 * @param msg
	 *            - The message of the problem, containing its details
	 * @param level
	 *            - The destination level to copy the problem to
	 * @param callback
	 *            The callback for the synchronous method.
	 * 
	 */
	void copyProblem(int problemId, Level levelTo,
			AsyncCallback<Integer> callback);

}
