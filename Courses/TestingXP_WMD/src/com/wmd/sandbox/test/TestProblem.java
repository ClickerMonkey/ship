package com.wmd.sandbox.test;

import java.sql.SQLException;

import com.wmd.server.db.Database;

import static com.wmd.sandbox.test.Tables.*;

public class TestProblem 
{
	
	public static void main(String[] args) throws SQLException {
		new TestProblem().test();
	}
	
	public void test() throws SQLException
	{ 
		Problems.setDatabase(Database.get());
		
		Problem p1 = new Problem("Tuple Testing", 1, "<problem><question/><answer/></problem>");
		Problems.insert(p1);
		System.out.println(p1);
		
		long id = p1.getLong(Problem.ID);
		
		Problem p2 = new Problem(id);
		p2.set(Problem.NAME, "Tuple Testing II");
		System.out.println(p2);
		Problems.updateTuple(p2);
		
		Problem p3 = new Problem(id);
		Problems.selectTuple(p3);
		System.out.println(p3);
		
		Problem p4 = new Problem(id);
		Problems.deleteTuple(p4);
		System.out.println(p4);
		

		Problem d1 = new Problem("Un1qu3 N4m3", 1, null);
		Problem d2 = new Problem("Un1qu3 N4m3", 1, null);
		Problem d3 = new Problem("Un1qu3 N4m3", 1, null);
		Problems.insert(d1);
		Problems.insert(d2);
		Problems.insert(d3);
		
		// Delete where name is "Un1qu3 N4m3"
		Problem pred = new Problem(); 
		pred.set(Problem.NAME, "Un1qu3 N4m3");
		System.out.println( Problems.delete(pred) );
	}
	
}
