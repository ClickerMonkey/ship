package com.wmd.server.db;


import org.junit.After;
import org.junit.Before;

public class DatabaseTest
{

	@Before
	public void setUp() throws Exception
	{
		Database.reset();
		Database.setTesting(true);
		
	}

	@After
	public void tearDown() throws Exception
	{
		Database db = Database.get();
		db.rollBack();
	}

}
