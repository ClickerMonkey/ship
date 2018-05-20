package com.wmd.client.msg;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * A message used to return the id of its assignment, its level, its name (short
 * description), and its number in its assignment.
 * 
 * @author Philip Diffenderfer, Paul Cheney
 * @author Refactoring: Drew Q, Tristan D
 */
public class ProblemMsg implements IsSerializable
{

	//the id of the problem
	private int problem_id;
	
	// The id of this assignment this problem exists in.
	private int assignmentId;

	// The level of this problem.
	private Level level;

	// The number of this problem in the assignment (at the level)
	private int problemOrder;

	// The name (short description) of this problem.
	private String name;

	// The previous level, for database overwrite checking (save or update)
	private Level previousLevel;

	/**
	 * Initializes a new ProblemMsg.
	 */
	public ProblemMsg()
	{
		// No instance vars to construct
	}

	/**
	 * Constructor for pre-defined Id handling i.e. from a database handler
	 * 
	 * @param assignmentId
	 *            The id of the assignment
	 * @param level
	 *            The level of this problem
	 * @param problemNumber
	 *            The number of this problem in its assignment.
	 */
	public ProblemMsg(int assignmentId, Level level, int problemNumber)
	{
		this.assignmentId = assignmentId;
		this.level = level;
		this.previousLevel = level;
		this.problemOrder = problemNumber;
		this.name = "";
	}

	/**
	 * Constructor for pre-defined Id handling i.e. from a database handler
	 * 
	 * @param assignmentId
	 *            The id of the assignment
	 * @param level
	 *            The level of this problem
	 * @param problemNumber
	 *            The number of this problem in its assignment.
	 * @param name
	 *            The name of the problem
	 */
	public ProblemMsg(int assignmentId, Level level, int problemNumber,
			String name)
	{
		this.assignmentId = assignmentId;
		this.level = level;
		this.previousLevel = level;
		this.problemOrder = problemNumber;
		this.name = name;
	}

	/**
	 * @return The id of the Problem
	 */
	public int getProblemId()
	{
		return this.problem_id;
	}

	/**
	 * Sets the ID of the problem
	 */
	public void setProblemId(int id)
	{
		this.problem_id = id; 
	}
	
	/**
	 * @return The id of the assignment.
	 */
	public int getAssignmentId()
	{
		return this.assignmentId;
	}
	
	/**
	 * Sets the id of the assignment.
	 * 
	 * @param id
	 *            The id of the assignment.
	 */
	public void setAssignmentId(int id)
	{
		this.assignmentId = id;
	}

	/**
	 * @return The name (short description) of the problem.
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Sets the name (short description) of the problem.
	 * 
	 * @param name
	 *            The name of the problem.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return The id of the set this problem belongs to.
	 */
	public Level getLevel()
	{
		return this.level;
	}

	/**
	 * Sets the level of the problem.
	 * 
	 * @param level
	 *            The level of the problem.
	 */
	public void setLevel(Level level)
	{
		this.previousLevel = this.level;
		this.level = level;
	}

	/**
	 * @return The number this problem is in its respective assignment.
	 */
	public int getProblemOrder()
	{
		return this.problemOrder;
	}

	/**
	 * Sets the problem number.
	 * 
	 * @param problemNumber
	 *            The new problem number.
	 */
	public void setProblemOrder(int problemNumber)
	{
		this.problemOrder = problemNumber;
	}

	/**
	 * @return the previous level
	 */
	public Level getPreviousLevel()
	{
		return this.previousLevel;
	}
	
	/**
	 * Change the previous level
	 * 
	 * @param previousLevel
	 *            the value we want the previous level to have
	 */
	public void setPreviousLevel(Level previousLevel)
	{
		this.previousLevel = previousLevel;
	}

	@Override
	public String toString()
	{
		return this.assignmentId + "\nLevel: " + this.level
				+ "\n Previous Level: " + this.previousLevel
				+ "\nProblem Number: " + this.problemOrder + "\n Name: "
				+ this.name;
	}
}
