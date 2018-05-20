package com.wmd.sandbox.test;

import com.wmd.sandbox.Tuple;

public class AssignmentProblem extends Tuple 
{
	
	public static final int ASSIGNMENT_ID = 0;
	public static final int PROBLEM_ID = 1;
	public static final int PROBLEM_NUMBER = 2;

	public AssignmentProblem() {
		super(Tables.AssignmentProblems);
	}
	
	public AssignmentProblem(long assignment_id, long problem_id) {
		super(Tables.AssignmentProblems);
		set(ASSIGNMENT_ID, assignment_id);
		set(PROBLEM_ID, problem_id);
	}
	
}
