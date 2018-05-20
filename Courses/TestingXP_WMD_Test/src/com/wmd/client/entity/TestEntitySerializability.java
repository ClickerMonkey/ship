package com.wmd.client.entity;

import org.junit.Test;

import com.wmd.util.SerializabilityVerifier;

/**
 * Tests classes in com.wmd.client.entity for 'proper' Serializability.
 * 
 * @author Philip Diffenderfer, Steve Unger
 */
public class TestEntitySerializability 
{

	/**
	 * Tests Answer for serializability.
	 */
	@Test
	public void testAnswer()
	{
		new SerializabilityVerifier(Answer.class)
			.verifyStructureForSerializability();
	}

	/**
	 * Tests Fraction for serializability.
	 */
	@Test
	public void testFraction()
	{
		new SerializabilityVerifier(Fraction.class)
			.verifyStructureForSerializability();
	}

	/**
	 * Tests Integer for serializability.
	 */
	@Test
	public void testInteger()
	{
		new SerializabilityVerifier(Integer.class)
			.verifyStructureForSerializability();
	}

	/**
	 * Tests Newline for serializability.
	 */
	@Test
	public void testNewline()
	{
		new SerializabilityVerifier(Newline.class)
			.verifyStructureForSerializability();
	}

	/**
	 * Tests ProblemStatement for serializability.
	 */
	@Test
	public void testProblemStatement()
	{
		new SerializabilityVerifier(ProblemStatement.class)
			.verifyStructureForSerializability();
	}

	/**
	 * Tests Question for serializability.
	 */
	@Test
	public void testQuestion()
	{
		new SerializabilityVerifier(Question.class)
			.verifyStructureForSerializability();
	}

	/**
	 * Tests Text for serializability.
	 */
	@Test
	public void testText()
	{
		new SerializabilityVerifier(Text.class)
			.verifyStructureForSerializability();
	}

	/**
	 * Tests Unit for serializability.
	 */
	@Test
	public void testUnit()
	{
		new SerializabilityVerifier(Unit.class)
			.verifyStructureForSerializability();
	}
	/**
	 * Tests Unit for serializability.
	 */
	@Test
	public void testDecimal()
	{
		new SerializabilityVerifier(Decimal.class)
			.verifyStructureForSerializability();
	}
	/**
	 * Tests Unit for serializability.
	 */
	@Test
	public void testExponent()
	{
		new SerializabilityVerifier(Exponent.class)
			.verifyStructureForSerializability();
	}
}
