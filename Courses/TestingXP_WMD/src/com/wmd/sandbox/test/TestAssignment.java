package com.wmd.sandbox.test;

import java.sql.SQLException;

import com.wmd.server.db.Database;

import static com.wmd.sandbox.test.Tables.*;

public class TestAssignment 
{

	public static void main(String[] args) throws Exception {
		new TestAssignment().test();
	}
	
	public void test() throws SQLException
	{
		Assignments.setDatabase(Database.get());
		
		Assignment query = new Assignment();
		query.set(Assignment.PROBLEM_COUNT, 3);
		Assignment[] results = Assignments.select(query);
		
		System.out.println("Assignments found with criteria:");
		for (Assignment ass : results) {
			System.out.println("\t" + ass);
		}
	}
	
}
