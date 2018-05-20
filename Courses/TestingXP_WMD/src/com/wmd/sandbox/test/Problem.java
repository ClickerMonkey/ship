package com.wmd.sandbox.test;

import com.wmd.sandbox.Tuple;

public class Problem extends Tuple 
{
	
	public static final int ID = 0;
	public static final int NAME = 1;
	public static final int SET_ID = 2;
	public static final int STATEMENT = 3;

	public Problem() {
		super(Tables.Problems);
	}
	
	public Problem(long id) {
		super(Tables.Problems);
		set(ID, id);
	}
	
	public Problem(String name, long set_id, String statement) {
		super(Tables.Problems);
		set(NAME, name);
		set(SET_ID, set_id);
		set(STATEMENT, statement);
	}
	
}
