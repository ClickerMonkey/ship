package com.wmd.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Swaps the problems numbers of two problems
 * 
 * For two problems that are in the same assignment/level, this can swap their
 * problem numbers
 */
@RemoteServiceRelativePath("swap_problem_number")
public interface SwapProblemNumbersService extends RemoteService
{

	/**
	 * Swaps the problem numbers of two problems in an assignment
	 * 
	 * @param a
	 *            the first problem involved in the swap
	 * @param b
	 *            the second problem involved in the swap
	 * 
	 * @throws ServiceException
	 *             describing any errors that occurred
	 */
	void swap(int probId1,int probId2) throws ServiceException;

}