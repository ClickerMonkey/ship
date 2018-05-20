package com.wmd.client.msg;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Class UserAssignentStatusMsg This class transfers the status of the current
 * students problem to the database
 * 
 * @author Paul Cheney, Phil Diffenderfer
 * @refactor Scotty Rhinehart and Olga Zalamea  
 */
public class UserAssignmentStatusMsg implements IsSerializable
{

	// The id of the user.
	private int userId;

	// The id of the assignment.
	private int assignmentId;

	// The name of the assignment
	private String assignmentName;

	// The user that holds the assignment
	private UserMsg user;

	// Whether the user can take this assignment currently
	private boolean assignmentEnabled;

	// How many problems are in the assignment.
	private int problemCount;

	// How many problems the user has attempted
	private int problemsTried;

	// How many problems the user has answered correctly
	private int problemsCorrect;

	// The total number of tries for all problems in the assignment.
	private int totalTries;

	/**
	 * Initializes a new UserAssignmentStatusMsg.
	 */
	public UserAssignmentStatusMsg()
	{
		// Initialize no instance variables
	}

	/**
	 * @return the id of the user
	 */
	public int getUserId()
	{
		return this.userId;
	}

	/**
	 * @return the assignment id
	 */
	public int getAssignmentId()
	{
		return this.assignmentId;
	}

	/**
	 * @return assignment name
	 */
	public String getAssignmentName()
	{
		return this.assignmentName;
	}

	/**
	 * @return UserMsg
	 */
	public UserMsg getUser()
	{
		return this.user;
	}

	/**
	 * @return returns whether the assignment is active
	 */
	public boolean isAssignmentEnabled()
	{
		return this.assignmentEnabled;
	}

	/**
	 * @return The number of problems in the assignment.
	 */
	public int getProblemCount()
	{
		return this.problemCount;
	}

	/**
	 * @return number of problems tried
	 */
	public int getProblemsTried()
	{
		return this.problemsTried;
	}

	/**
	 * @return number of problems correct
	 */
	public int getProblemsCorrect()
	{
		return this.problemsCorrect;
	}

	/**
	 * @return total attempts
	 */
	public int getTotalTries()
	{
		return this.totalTries;
	}

	/**
	 * Sets the user id.
	 * 
	 * @param userId
	 *            the id of the user
	 */
	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	/**
	 * Sets the assignment id.
	 * 
	 * @param assignmentId
	 *            the id of the assignment
	 */
	public void setAssignmentId(int assignmentId)
	{
		this.assignmentId = assignmentId;
	}

	/**
	 * Sets the assignment name.
	 * 
	 * @param assignmentName
	 *            the name of the assignment
	 */
	public void setAssignmentName(String assignmentName)
	{
		this.assignmentName = assignmentName;
	}

	/**
	 * Sets the user.
	 * 
	 * @param user
	 *            the student that holds the assignment
	 */
	public void setUser(UserMsg user)
	{
		this.user = user;
	}

	/**
	 * Sets whether the assignment is enabled.
	 * 
	 * @param assignmentEnabled
	 *            is the current assignment currently enabled
	 */
	public void setAssignmentEnabled(boolean assignmentEnabled)
	{
		this.assignmentEnabled = assignmentEnabled;
	}

	/**
	 * Sets the number of problems in the users assignment.
	 * 
	 * @param problemCount
	 *            The number of problems.
	 */
	public void setProblemCount(int problemCount)
	{
		this.problemCount = problemCount;
	}

	/**
	 * Sets how many problems were tried for this assignment.
	 * 
	 * @param problemsTried
	 *            how many problems have been tried so far
	 */
	public void setProblemsTried(int problemsTried)
	{
		this.problemsTried = problemsTried;
	}

	/**
	 * Sets how many problems were correct for this assignment.
	 * 
	 * @param problemsCorrect
	 *            set how many problems are correct
	 */
	public void setProblemsCorrect(int problemsCorrect)
	{
		this.problemsCorrect = problemsCorrect;
	}

	/**
	 * Sets the total amount of tries for this assignment.
	 * 
	 * @param totalTries
	 *            set how many tries attempted
	 */
	public void setTotalTries(int totalTries)
	{
		this.totalTries = totalTries;
	}

	public String getPeriod()
	{
		return user.getPeriod();
	}

	public String getLastName()
	{
		return user.getLastName();
	}

	public String getFirstName()
	{
		return user.getFirstName();
	}

}