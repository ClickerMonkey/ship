package com.wmd.sandbox.test;

import com.wmd.sandbox.Column;
import com.wmd.sandbox.ForeignKey;
import com.wmd.sandbox.Table;

public class Tables 
{
	
	/**
	 * A table containing Problems.
	 */
	public static final Table<Problem> Problems =
		// Creates the problem table 
		new Table<Problem>("problem", Problem.class,
			// The automatically generated problem id key
			new Column(Problem.ID, "id", true, true),
			// The name (short description) of the problem
			new Column(Problem.NAME, "name"),
			// The set the problem belongs to
			new Column(Problem.SET_ID, "set_id"),
			// The statement (Xml) of the problem
			new Column(Problem.STATEMENT, "statement"));
	
	/**
	 * A table containing Assignments.
	 */
	public static final Table<Assignment> Assignments =
		// Creates the assignment table
		new Table<Assignment>("assignment", Assignment.class,
			// The automatically generated assignment id key
			new Column(Assignment.ID, "id", true, true),
			// The name (short description) of the assignment
			new Column(Assignment.NAME, "name"),
			// The number of problems in the assignment (derived value)
			new Column(Assignment.PROBLEM_COUNT, "problem_count"));

	/**
	 * A table of problems 'assigned' to an assignment.
	 */
	public static final Table<AssignmentProblem> AssignmentProblems =
		// Creates the assignment_problem table
		new Table<AssignmentProblem>("assignment_problem", AssignmentProblem.class,
			// A foreign key to the assignment table
			new ForeignKey(AssignmentProblem.ASSIGNMENT_ID, "assignment_id", Assignments, Assignment.ID),
			// A foriegn key to the problem table
			new ForeignKey(AssignmentProblem.PROBLEM_ID, "problem_id", Problems, Problem.ID),
			// The problem's number in the assignment
			new Column(AssignmentProblem.PROBLEM_NUMBER, "problem_number"));
	
}
