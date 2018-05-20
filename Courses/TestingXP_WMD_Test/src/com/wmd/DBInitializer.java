package com.wmd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Initializes the Test DB to a knows state
 * 
 * @author Merlin
 * 
 *         Created: Apr 12, 2010
 */
public class DBInitializer
{

	private static String[] levels =
	{ "Easy", "Medium", "Hard" };

	private static String[] problems =
	{
			"<problem><question><text val=\"What is 3+5?\" /></question><answer><integer int=\"8\"  /></answer></problem>",
			"<answer><text val=\"What is \" /><integer int=\"4\" />"
					+ "<fraction><num><integer int=\"2\" /></num><den>"
					+ "<integer int=\"5\" /></den></fraction><text val=\"?\" /></answer>",
			"<fraction><num><integer int=\"1\" /></num><den><integer int=\"5\" />"
					+ "<exponent><base><text val=\"x\" /></base><pow><fraction><num>"
					+ "<integer int=\"1\" /></num><den><integer int=\"2\" /></den></fraction>"
					+ "</pow></exponent></den></fraction>" };

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		String url = "jdbc:mysql://txp.cs.ship.edu:3306/wmdTest";

		try
		{
			Connection con = DriverManager.getConnection(url, "wmd", "mathRox");
			con.setAutoCommit(false);
			initializeUsersTable(con);
			initializeAssignmentTable(con);
			initializeProblemTable(con);
			initializeUserAssignmentTable(con);
			initializeUnitsTable(con);
			initializeProblemStatusTable(con);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	private static void initializeProblemStatusTable(Connection con) throws SQLException
	{
		Statement stmt = con.createStatement();
		stmt.executeUpdate("DROP TABLE problem_status");
		StringBuffer sql = new StringBuffer("CREATE TABLE problem_status(");
		sql.append("user_id int NOT NULL,");
		sql.append("problem_id int NOT NULL,");
		sql.append("tries SMALLINT(5)  DEFAULT 0,");
		sql.append("correct SMALLINT(1) DEFAULT 0,");
		sql.append("FOREIGN KEY (user_id) references user(id),");
		sql.append("FOREIGN key (problem_id) references problem(id), ");
		sql.append("PRIMARY KEY( user_id, problem_id));");

		System.out.println(sql);
		stmt.executeUpdate(new String(sql));
		stmt.executeUpdate("ALTER TABLE problem_status ENGINE = INNODB");
		
		// user 2 has two assignments and there are problem status entries for 3 of the four total problems (one is missing on purpose)
		// there is one problem with 0 tries and not correct, one with 4 tries that is correct and one with 5 tries that is not correct
		stmt.executeUpdate("Insert into problem_status (user_id, problem_id) values (2,1)");
		stmt.executeUpdate("Insert into problem_status (user_id, problem_id,tries,correct) values (2,10,4,1)");
		stmt.executeUpdate("Insert into problem_status (user_id, problem_id,tries,correct) values (2,7,5,0)");
		
		// user 4 has only one assignment and problem status for only one problem in that assignment
		stmt.executeUpdate("Insert into problem_status (user_id, problem_id,tries,correct) values (4,2,4,1)");
		
		con.commit();
	}

	static String[] units =
	{ "centimeters", "decimeters", "kilometers", "meters", "millimeters" };

	private static void initializeUnitsTable(Connection con) throws SQLException
	{
		Statement stmt = con.createStatement();
		stmt.executeUpdate("DROP TABLE unit");
		StringBuffer sql = new StringBuffer("CREATE TABLE unit(");
		sql.append("name VARCHAR(24) NOT NULL);");
		stmt.executeUpdate(new String(sql));
		stmt.executeUpdate("ALTER TABLE unit ENGINE = INNODB");
		for (String unit : units)
		{
			stmt.executeUpdate("INSERT into unit (name) values ('" + unit + "'); ");
		}
		con.commit();
	}

	private static void initializeUserAssignmentTable(Connection con) throws SQLException
	{
		Statement stmt = con.createStatement();
		stmt.executeUpdate("DROP TABLE user_assignment");
		StringBuffer sql = new StringBuffer("CREATE TABLE user_assignment(");
		sql.append("user_id int NOT NULL,");
		sql.append("assignment_id int NOT NULL,");
		sql.append("FOREIGN KEY (user_id) references user(id),");
		sql.append("FOREIGN key (assignment_id) references assignment(id), ");
		sql.append("PRIMARY KEY( user_id, assignment_id));");

		System.out.println(sql);
		stmt.executeUpdate(new String(sql));
		stmt.executeUpdate("ALTER TABLE user_assignment ENGINE = INNODB");

		stmt.executeUpdate("INSERT into user_assignment (user_id, assignment_id) values (2, 1); ");
		stmt.executeUpdate("INSERT into user_assignment (user_id, assignment_id) values (2, 3); ");
		stmt.executeUpdate("INSERT into user_assignment (user_id, assignment_id) values (4, 1); ");

		con.commit();

	}

	private static void initializeProblemTable(Connection con) throws SQLException
	{
		Statement stmt = con.createStatement();
		stmt.executeUpdate("DROP TABLE problem");
		StringBuffer sql = new StringBuffer("CREATE TABLE problem(");
		sql.append("id int NOT NULL AUTO_INCREMENT, ");
		sql.append("assignment_id int NOT NULL,");
		sql.append("level ENUM ('Easy', 'Medium', 'Hard'),");
		sql.append("problem_order int NOT NULL,");
		sql.append("name VARCHAR(128) NOT NULL,");
		sql.append("statement BLOB,");
		sql.append("PRIMARY KEY (id),");
		sql.append("FOREIGN KEY (assignment_id) references assignment(id));");

		System.out.println(sql);
		stmt.executeUpdate(new String(sql));
		stmt.executeUpdate("ALTER TABLE assignment ENGINE = INNODB");

		for (int k=0;k<2;k++)
		{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < levels.length; j++)
			{
				String sql2 = "INSERT INTO problem (assignment_id, level, problem_order, name, statement) values ("
						+ (i + 1)
						+ ", '"
						+ levels[j]
						+ "',"
						+ (k*3+j + 1)
						+ ",'Problem"
						+ i
						+ j + k
						+ "', '" + problems[j] + "');";
				System.out.println(sql2);
				stmt.executeUpdate(sql2);
			}

		}
		}

	}

	private static void initializeAssignmentTable(Connection con) throws SQLException
	{
		Statement stmt = con.createStatement();
		stmt.executeUpdate("DROP TABLE assignment");
		StringBuffer sql = new StringBuffer("CREATE TABLE assignment(");
		sql.append("id int NOT NULL AUTO_INCREMENT, ");
		sql.append("name VARCHAR(128) NOT NULL,");
		sql.append("enabled TINYINT(1),");
		sql.append("PRIMARY KEY (id));");

		System.out.println(sql);
		stmt.executeUpdate(new String(sql));
		stmt.executeUpdate("ALTER TABLE assignment ENGINE = INNODB");
		stmt.executeUpdate("INSERT INTO assignment (name, enabled) VALUES ("
				+ "'TestAssignment1', 1);");
		stmt.executeUpdate("INSERT INTO assignment (name, enabled) VALUES ("
				+ "'TestAssignment2', 0);");
		stmt.executeUpdate("INSERT INTO assignment (name, enabled) VALUES ("
				+ "'TestAssignment3', 1);");

		con.commit();
	}

	private static void initializeUsersTable(Connection con) throws SQLException
	{
		Statement stmt = con.createStatement();
		stmt.executeUpdate("DROP TABLE user");
		StringBuffer sql = new StringBuffer("CREATE TABLE user(");
		sql.append("id int NOT NULL AUTO_INCREMENT, ");
		sql.append("firstname VARCHAR(24) NOT NULL,");
		sql.append("lastname VARCHAR(24) NOT NULL,");
		sql.append("role ENUM ('Admin', 'Instructor', 'Student'),");
		sql.append("username VARCHAR(32) NOT NULL,");
		sql.append("password VARCHAR(32) NOT NULL, ");
		sql.append("level ENUM ('Easy', 'Medium', 'Hard'),");
		sql.append("period VARCHAR(8),");
		sql.append("enabled TINYINT(1),");
		sql.append("PRIMARY KEY (id));");

		System.out.println(sql);
		stmt.executeUpdate(new String(sql));
		stmt.executeUpdate("ALTER TABLE user ENGINE = INNODB");
		stmt.executeUpdate("alter table user add unique (username)");
		stmt
				.executeUpdate("INSERT INTO user (firstname, lastname, role, username, password, enabled) VALUES ("
						+ "'FirstAdmin', 'lastAdmin', 'Admin', 'admin', 'admin', 1);");
		stmt
				.executeUpdate("INSERT INTO user (firstname, lastname, role, username, password, level, period, enabled) VALUES ("
						+ "'FirstEasy', 'LastEasy', 'Student', 'testEasy', 'simple', 'Easy', 'first', 1);");
		stmt
				.executeUpdate("INSERT INTO user (firstname, lastname, role, username, password, level, period, enabled) VALUES ("
						+ "'FirstEasy2', 'LastEasy2', 'Student', 'testEasy2', 'simple', 'Easy', 'first', 0);");
		stmt
				.executeUpdate("INSERT INTO user (firstname, lastname, role, username, password, level, period, enabled) VALUES ("
						+ "'FirstMed', 'LastMed', 'Student', 'testMed', 'more', 'Medium', 'second', 1);");
		stmt
				.executeUpdate("INSERT INTO user (firstname, lastname, role, username, password, level, period, enabled) VALUES ("
						+ "'FirstHard', 'LastHard', 'Student', 'testHard', 'tough', 'Hard', 'third', 1);");
		stmt
				.executeUpdate("INSERT INTO user (firstname, lastname, role, username, password, enabled) VALUES ("
						+ "'FirstInstr', 'lastInstr', 'Instructor', 'teacher', 'teaches', 1);");

		con.commit();
	}

}
