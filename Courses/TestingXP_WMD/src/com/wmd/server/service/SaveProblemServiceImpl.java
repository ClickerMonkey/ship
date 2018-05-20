package com.wmd.server.service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wmd.client.entity.ProblemStatement;
import com.wmd.client.msg.Level;
import com.wmd.client.msg.ProblemMsg;
import com.wmd.client.service.SaveProblemService;
import com.wmd.server.db.Problem;

/**
 * Saves the problem to the database. If the problem doesn't exist in the
 * database then it is inserted. If the problem does exist in the database then
 * it is updated with the new information.
 * 
 * @author Scotty Rhinehart, AJ Marx
 * 
 * Refactored by: Sam Storino, Stephen Jurnack and Kevin Rexroth and Steven Unger
 */
public class SaveProblemServiceImpl extends RemoteServiceServlet implements
		SaveProblemService
{
	
	/**
	 * The serial version ID
	 */
	private static final long serialVersionUID = 1L;
	
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
	 * @return the problem message
	 */
	public ProblemMsg saveProblem(int problemId, int assignmentId, Level level, String name, int problemOrder,
			ProblemStatement statement)
	{
		// Creates an instance of Problem
		Problem problemToSave = null;
		
		//Tries adding all the arguments to the Problem class
		try 
		{
			// New Problem?
			if (problemId == -1)
			{
				problemToSave = Problem.create(assignmentId, level, name);
			}
			else
			{
				problemToSave = new Problem(problemId);
				problemToSave.setName(name);
			}
			
			problemToSave.setProblemStatement(statement);
		}
		catch (Exception e1) 
		{
			//prints sql exception errors 
			e1.printStackTrace();
			return null;
		} 

		return problemToSave.getMessage();
	}

}
