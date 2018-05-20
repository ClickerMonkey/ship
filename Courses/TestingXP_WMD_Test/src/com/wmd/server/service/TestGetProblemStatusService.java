package com.wmd.server.service;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

import com.wmd.client.msg.ProblemStatusMsg;
import com.wmd.server.service.GetProblemStatusServiceImpl;

/**
 * @Class GetProblemStatusService
 * @author Drew Q., Will F.
 * @Description Test for the GetProblemStatusService class
 */
public class TestGetProblemStatusService {

	
	/**
	 * Tests the service
	 * @throws SQLException 
	 */
	@Test
	public void testGetProblemStatusService() throws SQLException
	{
		GetProblemStatusServiceImpl gpss = new GetProblemStatusServiceImpl();
	
		try
		{	
			gpss.getHandler().saveOrUpdateStatus(2,1,3,true);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		List<ProblemStatusMsg> list = gpss.getProblemStatus(2, 1);
		
		assertNotNull(list);
		assertTrue(list.size() > 0);
		
		// Test all messages returned are non-null.
		for (ProblemStatusMsg msg : list)
		{
			assertNotNull(msg);
		}
		
	}
}
