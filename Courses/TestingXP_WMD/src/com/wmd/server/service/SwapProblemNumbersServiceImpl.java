package com.wmd.server.service;

import java.sql.SQLException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wmd.client.service.ServiceException;
import com.wmd.client.service.SwapProblemNumbersService;
import com.wmd.server.db.Problem;

/**
 * @author Merlin
 * 
 *         Created: Apr 5, 2010
 */
public class SwapProblemNumbersServiceImpl extends RemoteServiceServlet implements SwapProblemNumbersService
{

	/**
	 * serial id
	 */
	private static final long serialVersionUID = -8743567843853066009L;

	/**
	 * @see com.wmd.client.service.SwapProblemNumbersService#swap(com.wmd.client.msg.ProblemMsg,
	 *      com.wmd.client.msg.ProblemMsg)
	 */
	public void swap(int probId1, int probId2) throws ServiceException
	{
		try
		{
			Problem prob1 = new Problem(probId1);
			Problem prob2 = new Problem(probId2);
			
			if ((prob1.getAssignmentId() != prob2.getAssignmentId())
					|| (prob1.getLevel() != prob2.getLevel()))
			{
				throw new ServiceException(
						"Can only swap problem numbers within one level in one assignment");
			}
			
			int temp = prob1.getProblemOrder();
			prob1.setProblemOrder(prob2.getProblemOrder());
			prob2.setProblemOrder(temp);
			
		} catch (SQLException e)
		{
			GWT.log("ERROR in SwapProblemNumbersService", e);
			e.printStackTrace();
		}

	}

}
