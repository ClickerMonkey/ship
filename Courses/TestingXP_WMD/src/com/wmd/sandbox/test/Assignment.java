package com.wmd.sandbox.test;

import com.wmd.sandbox.Tuple;

public class Assignment extends Tuple 
{
	
	public static final int ID = 0;
	public static final int NAME = 1;
	public static final int PROBLEM_COUNT = 2;

	public Assignment() {
		super(Tables.Assignments);
	}
	
	public Assignment(long id) {
		super(Tables.Assignments);
		set(ID, id);
	}
	
	public Assignment(String name) {
		super(Tables.Assignments);
		set(NAME, name);
	}
	
}
