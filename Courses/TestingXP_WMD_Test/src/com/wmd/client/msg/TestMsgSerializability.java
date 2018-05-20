package com.wmd.client.msg;

import org.junit.Test;

//import com.wmd.deprecated.ProblemAssignmentMsg;
//import com.wmd.deprecated.ProblemSetMsg;
import com.wmd.util.SerializabilityVerifier;

/**
 * Tests classes in com.wmd.client.msg for 'proper' Serializability.
 * 
 * @author Philip Diffenderfer, Paul Cheney
 */
public class TestMsgSerializability 
{

	/**
	 * Tests AssignmentMsg for serializability.
	 */
	@Test
	public void testAssignment()
	{
		new SerializabilityVerifier(AssignmentMsg.class)
			.verifyStructureForSerializability();
	}

	/**
	 * Tests ProblemAssignmentMsg for serializability.
	 */
	@Test
	public void testProblemAssignment()
	{
//		new SerializabilityVerifier(ProblemAssignmentMsg.class)
//			.verifyStructureForSerializability();
	}

	/**
	 * Tests ProblemMsg for serializability.
	 */
	@Test
	public void testProblem()
	{
		new SerializabilityVerifier(ProblemMsg.class)
			.verifyStructureForSerializability();
	}

	/**
	 * Tests ProblemSetMsg for serializability.
	 */
	@Test
	public void testProblemSet()
	{
//		new SerializabilityVerifier(ProblemSetMsg.class)
//			.verifyStructureForSerializability();
	}

	/**
	 * Tests ProbleStatusMsg for serializability.
	 */
	@Test
	public void testProblemStatus()
	{
		new SerializabilityVerifier(ProblemStatusMsg.class)
			.verifyStructureForSerializability();
	}
	
}
