package com.wmd.server.service;

import static org.junit.Assert.*;

import org.junit.*;

import com.wmd.client.msg.AssignmentMsg;
import com.wmd.server.db.AssignmentHandler;
import com.wmd.server.db.DatabaseTest;

/**
 * This class tests that the DeleteAssignmentService successfully removes an
 * assignment from the database.
 * 
 * @author Andrew Marx, Chris Koch
 * @since 31 March 2010
 * 
 */
public class TestDeleteAssignmentService extends DatabaseTest
{
	AssignmentMsg assignmentMsgForDeletion;

	/**
	 * Test that all the data we expect to exist actually exists in the database
	 * prior to removal.
	 */
	@Test
	public void testThatDataExistsInDatabase()
	{
		// TODO fetch all the assignments
		// TODO make sure everything is there
		// TODO choose an AssignmentMsg for deletion
		assignmentMsgForDeletion = null; // XXX change from -1
	}

	/**
	 * Perform the actual removal. Test that it returns true on success.
	 * @throws Exception 
	 */
	@Test
	public void performGoodDeletion() throws Exception
	{
		AssignmentHandler assign = new AssignmentHandler();
		int id = assign.saveOrUpdate("Perform Good Deletion Assignment", true);
		
		DeleteAssignmentServiceImpl service = new DeleteAssignmentServiceImpl();

		boolean success;
		success = service.deleteAssignment(id);

		assertTrue(success);
	}

	/**
	 * Attempt to remove an assignment id that does not actually exist in the
	 * database. Assert that it returns false.
	 * @throws Exception 
	 */
	@Test(expected = Exception.class)
	public void performBadDeletion() throws Exception
	{
		DeleteAssignmentServiceImpl service = new DeleteAssignmentServiceImpl();

		@SuppressWarnings("unused")
		boolean success = service.deleteAssignment(-972394);

	}

	/**
	 * Test that there is one less assignment in the database.
	 */
	@Test
	public void testThatAssignmentWasRemoved()
	{
		// TODO fetch all the assignments
		// TODO make sure everything is there - 1
	}

	/**
	 * Insert the assignment we removed back in to the database, such that there
	 * is not always one less assignment in the database after running this
	 * test.
	 */
	@Test
	public void addAssignmentBackToDatabase()
	{
		// TODO insert the assignment we removed back in to the database
	}
}
