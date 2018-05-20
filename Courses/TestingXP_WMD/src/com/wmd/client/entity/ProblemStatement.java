package com.wmd.client.entity;

/**
 * Problem class contains a Question and an Answer Entity.
 */
public class ProblemStatement implements Entity
{
	private Answer answer;
	private Question question;

	/**
	 * Blank constructor for Serializable
	 */
	public ProblemStatement()
	{
		// Blank constructor for Serializable
	}

	/**
	 * Constructor for class Problem with question and answer parameters
	 * 
	 * @param question
	 *            - Question entity to be stored
	 * @param answer
	 *            - Answer entity to be stored
	 */
	public ProblemStatement(Question question, Answer answer)
	{
		this.question = question;
		this.answer = answer;
	}

	/**
	 * Sets the stored Answer entity.
	 * 
	 * @return Answer entity
	 */
	public Answer getAnswer()
	{
		return this.answer;
	}

	/**
	 * Gets the stored Question entity.
	 * 
	 * @return Question entity
	 */
	public Question getQuestion()
	{
		return this.question;
	}

	/**
	 * Sets the stored Answer.
	 * 
	 * @param answer
	 *            - Answer entity to be stored
	 */
	public void setAnswer(Answer answer)
	{
		this.answer = answer;
	}

	/**
	 * Sets the stored question
	 * 
	 * @param question
	 *            - Question entity to be stored
	 */
	public void setQuestion(Question question)
	{
		this.question = question;
	}

}
