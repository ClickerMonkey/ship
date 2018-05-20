package com.wmd.server.service;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.wmd.client.msg.ProblemStatusMsg;
import com.wmd.client.service.ServiceException;
import com.wmd.server.db.DatabaseTest;
import com.wmd.server.db.ProblemStatus;
import com.wmd.server.service.UpdateProblemStatusServiceImpl;


/**
 * Tests UpdateProblemStatusServiceImpl
 * @author Philip Diffenderfer, Olga Zalamea
 * @author Refactoring: Drew Q, Tristan D
 * @author Refactoring: Chris E, Bill F
 */
public class TestUpdateProblemStatusServiceImpl extends DatabaseTest
{

	private static final int USER_1 = 2;
	private static final int USER_2 = 4;
	private static final int PROB_1 = 7;
	private static final int PROB_2 = 10;
	private static final int PROB_3 = 2;
	
	/**
	 * Tests the implementation by taking three already existing status' and
	 * updating them. 
	 */
	@Test
	public void testImpl()
	{
		UpdateProblemStatusServiceImpl impl = new UpdateProblemStatusServiceImpl();
	
		// Get the initial status of three problem statuses (the 1st and 2nd are
		// in the same boat, should have same tries and are never corrected).
		ProblemStatusMsg oldMsg1 = null, oldMsg2 = null, oldMsg3 = null;
		try
		{
			ProblemStatus handler1 = new ProblemStatus(USER_1,PROB_1);
			ProblemStatus handler2 = new ProblemStatus(USER_1,PROB_2);
			ProblemStatus handler3 = new ProblemStatus(USER_2,PROB_3);
			oldMsg1 = handler1.getMessage();
			oldMsg2 = handler2.getMessage();
			oldMsg3 = handler3.getMessage();
			// Just check if the exist
			assertNotNull(oldMsg1);
			assertNotNull(oldMsg2);
			assertNotNull(oldMsg3);

			// No perform the testing of the service (only set first as correct)
			ProblemStatusMsg newMsg1 = 
				impl.updateStatus(oldMsg1.getUserId(), oldMsg1.getProblemId(), false);
			ProblemStatusMsg newMsg2 = 
				impl.updateStatus(oldMsg2.getUserId(), oldMsg2.getProblemId(), false);
			ProblemStatusMsg newMsg3 = 
				impl.updateStatus(oldMsg3.getUserId(), oldMsg3.getProblemId(), true);

			// Check that each returned a new status message
			assertNotNull(newMsg1);
			assertNotNull(newMsg2);
			assertNotNull(newMsg3);
			
			// Show that they retained their previous information
			assertEqualStatus(oldMsg1, newMsg1);
			assertEqualStatus(oldMsg2, newMsg2);
			assertEqualStatus(oldMsg3, newMsg3);

			// Show that the first two are still not correct, while the first is
			assertFalse(newMsg1.isCorrect());
			assertTrue(newMsg2.isCorrect());
			assertTrue(newMsg3.isCorrect());

			// All should increment in tries even though the 3rd is already correct
			assertEquals(oldMsg1.getTries() + 1, newMsg1.getTries());
			assertEquals(oldMsg2.getTries() + 1, newMsg2.getTries());
			assertEquals(oldMsg3.getTries() + 1, newMsg3.getTries());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			fail();
		} catch (ServiceException e)
		{
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Verifies that two ProblemStatusMsg have matching users, and
	 * problem Ids
	 * @param oldmsg The first ProblemStatusMsg to compare.
	 * @param newmsg The second ProblemStatusMsg to compare.
	 */
	public void assertEqualStatus(ProblemStatusMsg oldmsg, ProblemStatusMsg newmsg)
	{
		// Can't test isCorrect() or getTries() in here.
		assertEquals(oldmsg.getUserId(), newmsg.getUserId());
		assertEquals(oldmsg.getProblemId(), newmsg.getProblemId());
	}

}
