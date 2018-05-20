package com.wmd.client.msg;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * A status for a user answering a problem in an assignment.
 * 
 * @author Philip Diffenderfer, Paul Cheney
 * @author Refactoring: Drew Q, Tristan D
 * @author Refactoring: Chris E, Bill F
 */
public class ProblemStatusMsg implements IsSerializable
{

	// The number of times the user tried to answer the question.
	private int tries;

	// If the user has the question correct.
	private boolean correct;

	// The id of the user answering the problem on the given assignment.
	private int userId;

	//The id of the problem
	private int problemId;

	/**
	 * Initializes a new ProblemStatusMsg.
	 */
	public ProblemStatusMsg()
	{
		// Initialize with no instance vars
	}

	/**
	 * Initializes a ProblemStatusMsg with the paramters intact
	 * 
	 * @param userId The users ID as int
	 * @param problemId the problem id as int
	 * @param tries the tries as int
	 * @param correct boolean is correct
	 */
	public ProblemStatusMsg(int userId, int problemId,int tries, 
			boolean correct)
	{
		this.setUserId(userId);
		this.setTries(tries);
		this.setCorrect(correct);
		this.setProblemId(problemId);
	}

	/**
	 * @return The number of times the user tried to answer the question.
	 */
	public int getTries()
	{
		return this.tries;
	}

	/**
	 * Sets the number of times the user tried to answer the question.
	 * 
	 * @param tries
	 *            The number of times the user tried to answer the question.
	 */
	public void setTries(int tries)
	{
		this.tries = tries;
	}

	/**
	 * @return If the user has the question correct.
	 */
	public boolean isCorrect()
	{
		return this.correct;
	}

	/**
	 * Sets if the user has the question correct.
	 * 
	 * @param correct
	 *            If the user has the question correct.
	 */
	public void setCorrect(boolean correct)
	{
		this.correct = correct;
	}

	/**
	 * @return The id of the user answering the problem on the given assignment.
	 */
	public int getUserId()
	{
		return this.userId;
	}

	/**
	 * Sets the id of the user answering the problem on the given assignment.
	 * 
	 * @param userId
	 *            The id of the user.
	 */
	public void setUserId(int userId)
	{
		this.userId = userId;
	}
	/**
	 * Set the problem Id 
	 * @param problemId problem id as int
	 */
	public void setProblemId(int problemId)
	{
		this.problemId = problemId;
	}
	/**
	 * Gets the problem id
	 * @return problemId as int
	 */
	public int getProblemId()
	{
		return problemId;
	}

}