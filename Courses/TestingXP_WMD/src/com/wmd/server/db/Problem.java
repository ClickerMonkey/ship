package com.wmd.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gwt.core.client.GWT;
import com.wmd.client.entity.ProblemStatement;
import com.wmd.client.msg.Level;
import com.wmd.client.msg.ProblemMsg;
import com.wmd.client.service.ServiceException;
import com.wmd.server.entity.EntityParser;
import com.wmd.server.entity.StatementParser;
import com.wmd.util.XMLUtil;
/**
 * 
 * @author Steve U, Drew Q
 * Creates a problem and has all functions to manipulate it
 * @author Refactoring: Drew Q, Steve U, Paul C
 *
 */
public class Problem
{

	private int problem_id;
	
	/**
	 * Constructor for Problem class
	 * @param problemId - id unique to a specific problem
	 * @throws SQLException
	 * @throws ServiceException
	 */
	public Problem(int problemId) throws ServiceException
	{
		this.problem_id = problemId;

		try
		{
			Connection con = Database.getSingleton().getConnection();

			PreparedStatement s = con
					.prepareStatement("SELECT name FROM problem WHERE id = ?");
			s.setInt(1, this.problem_id);
			ResultSet resultSet = s.executeQuery();
			if (!resultSet.next())
			{
				throw new ServiceException("Attempted to reference a non-existant problem");
			}
		}
		catch(SQLException e)
		{
			GWT.log("Error constructing problem", e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the problem id for a certain problem
	 * @return - id
	 */
	public int getProblemId()
	{
		return this.problem_id;
	}
	
	/**
	 * Returns the problem message  for a specific problem
	 * @return - ProblemMsg
	 * @throws SQLException
	 */
	public ProblemMsg getMessage()
	{
		ProblemMsg msg = new ProblemMsg();
	
		try
		{
			msg.setProblemId(this.problem_id);
			msg.setName(this.getName());
			msg.setLevel(this.getLevel());
			msg.setAssignmentId(this.getAssignmentId());
			msg.setProblemOrder(this.getProblemOrder());
		}
		catch(SQLException e)
		{
			GWT.log("SQL Error in Problem -> getMessage", e);
			e.printStackTrace();
		}
		return msg;
	}
	
	/**
	 * Returns the name for a specific problem
	 * @return - problem name
	 * @throws SQLException
	 */
	public String getName() throws SQLException
	{
		String name = null;
		
		try
		{
			Connection con = Database.getSingleton().getConnection();

			PreparedStatement s = con
					.prepareStatement("SELECT name FROM problem WHERE id = ?");
			s.setInt(1, this.problem_id);
			ResultSet resultSet = s.executeQuery();
			resultSet.first();
			name = resultSet.getString(1);
		}
		catch(SQLException e)
		{
			GWT.log("Error in Problem.getName",e);
			e.printStackTrace();
		}
		
		return name;
	}
	
	/**
	 * Returns the assignment id for a specific problem
	 * @return - assignment id
	 * @throws SQLException
	 */
	public int getAssignmentId() throws SQLException
	{
		int assignmentId = -1;
		try
		{
			Connection con = Database.getSingleton().getConnection();

			PreparedStatement s = con
					.prepareStatement("SELECT assignment_id FROM problem WHERE id = ?");
			s.setInt(1, this.problem_id);
			
			ResultSet resultSet = s.executeQuery();
			resultSet.first();
			assignmentId = resultSet.getInt(1);
		} catch (SQLException e)
		{
			GWT.log("Error in Problem.getAssignmentId",e);
			e.printStackTrace();
		}
		
		return assignmentId;
	}
	
	/**
	 * Returns the level for a specific problem
	 * @return - level
	 * @throws SQLException
	 */
	public Level getLevel() throws SQLException
	{
		Level level = null;
		try
		{
			Connection con = Database.getSingleton().getConnection();

			PreparedStatement s = con
					.prepareStatement("SELECT level FROM problem WHERE id = ?");
			s.setInt(1, this.problem_id);
			
			ResultSet resultSet = s.executeQuery();
			resultSet.first();
			
			String levelName = resultSet.getString(1);
			if (levelName != null)
			{
				level = Level.valueOf(levelName);
			}
		}
		catch(SQLException e)
		{
			GWT.log("Error in Problem.getLevel",e);
			e.printStackTrace();		
		}
		
		return level;
	}
	
	/**
	 * Returns the problem order
	 * @return - int problem order
	 * @throws SQLException
	 */
	public int getProblemOrder() throws SQLException
	{
		Connection con = Database.getSingleton().getConnection();

		PreparedStatement s = con
				.prepareStatement("SELECT problem_order FROM problem WHERE id = ?");
		s.setInt(1, this.problem_id);
		
		ResultSet resultSet = s.executeQuery();
		resultSet.first();
		
		return resultSet.getInt("problem_order");
	}
	
	/**
	 * Returns the problem statement of a problem
	 * @return - ProblemStatement
	 * @throws SQLException
	 */
	public ProblemStatement getProblemStatement() throws SQLException
	{
		Connection con = Database.getSingleton().getConnection();

		PreparedStatement s = con
				.prepareStatement("SELECT statement FROM problem WHERE id = ?");
		s.setInt(1, this.problem_id);
		ResultSet resultSet = s.executeQuery();
		resultSet.first();
		//get the string
		String problemStatement = resultSet.getString(1);
		
		//convert to xml object
		Document doc = XMLUtil.parse(problemStatement);
		Element element = doc.getDocumentElement();
		StatementParser parser = new StatementParser();
		//translate to ProblemStatement
		return parser.fromXML(element);
	}
	
	/**
	 * Sets the name for a problem
	 * @param name - string name
	 * @throws SQLException
	 */
	public void setName(String name) throws SQLException
	{
		if(name != null)
		{
			Connection con = Database.getSingleton().getConnection();
			
			PreparedStatement s = con
					.prepareStatement("UPDATE problem SET name = ? WHERE id = ?");
			s.setString(1, name);
			s.setInt(2, this.problem_id);
			
			s.executeUpdate();
		}
	}
	
	/**
	 * Sets the level of this problem
	 * @param lvl The new level of this problem
	 * @throws SQLException
	 */
	public void setLevel(Level lvl) throws SQLException
	{
		if(lvl != null)
		{
			Connection con = Database.getSingleton().getConnection();
			
			PreparedStatement s = con
					.prepareStatement("UPDATE problem SET level = ? WHERE id = ?");
			s.setString(1, lvl.name());
			s.setInt(2, this.problem_id);
			
			s.executeUpdate();
		}
	}
	
	/**
	 * Sets the assignment id for this problem
	 * @param assignmentId The new assignment ID for this problem
	 * @throws SQLException
	 */
	public void setAssignmentId(int assignmentId) throws SQLException
	{
		if(assignmentId != -1)
		{
			Connection con = Database.getSingleton().getConnection();
			
			PreparedStatement s = con
					.prepareStatement("UPDATE problem SET assignment_id = ? WHERE id = ?");
			s.setInt(1, assignmentId);
			s.setInt(2, this.problem_id);
			
			s.executeUpdate();
		}
	}
	
	/**
	 * Sets the problem order for this problem
	 * @param probOrder The new problem order for this problem
	 * @throws SQLException
	 */
	public void setProblemOrder(int probOrder) throws SQLException
	{
		if(probOrder != -1)
		{
			Connection con = Database.getSingleton().getConnection();
			
			PreparedStatement s = con
					.prepareStatement("UPDATE problem SET problem_order = ? WHERE id = ?");
			s.setInt(1, probOrder);
			s.setInt(2, this.problem_id);
			
			s.executeUpdate();
		}
	}
	
	/**
	 * Sets the problem statement for this ID
	 * @param stmt The new problem statement
	 * @throws SQLException
	 */
	public void setProblemStatement(ProblemStatement stmt) throws SQLException
	{
		if(stmt != null)
		{
			Connection con = Database.getSingleton().getConnection();
			
			PreparedStatement s = con
					.prepareStatement("UPDATE problem SET statement = ? WHERE id = ?");
			s.setString(1, EntityParser.toString(stmt));
			s.setInt(2, this.problem_id);
			
			s.executeUpdate();
		}
	}

	/**
	 * Copies the problem this object controls to a
	 * new level.
	 * @param newLevel The level to copy it to
	 * @return The problem Id of the new problem
	 * @throws SQLException An SQL Exception will be thrown if the database errors.
	 */
	public int copyProblem(Level newLevel) throws SQLException
	{
		int ret_id = 0;
		int highest = 0;
		
		int levelNum = 0;
		
		switch(newLevel)
		{
			case Easy:
				levelNum = 0;
				break;
			case Medium:
				levelNum = 1;
				break;
			case Hard:
				levelNum = 2;
				break;	
		}
		
		Connection con = Database.getSingleton().getConnection();
		
		PreparedStatement s = con
				.prepareStatement("SELECT id FROM problem WHERE assignment_id = ? AND level = ? AND problem_order = ?");
		
		s.setInt(1, this.getAssignmentId());
		s.setString(2, newLevel.name());
		s.setInt(3, this.getProblemOrder());
		
		ResultSet resultSet = s.executeQuery();
		
		if(resultSet.next())
		{
			PreparedStatement s2 = con
					.prepareStatement("SELECT MAX(problem_order) FROM problem WHERE assignment_id = ? AND level = ?");
		
			s2.setInt(1, this.getAssignmentId());
			s2.setString(2, newLevel.name());
			
			ResultSet rs = s2.executeQuery();
			
			if(rs.next())
			{
				highest = rs.getInt(1) + 1;
			}
		}
		
		if(highest == 0)
			highest = this.getProblemOrder();
		
		PreparedStatement ins = con
				.prepareStatement("INSERT INTO problem " +
									"(assignment_id,level,problem_order,name,statement) " +
									"VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
		
		ins.setInt(1, this.getAssignmentId());
		ins.setString(2, newLevel.name());
		ins.setInt(3, highest);
		ins.setString(4, "Problem"+this.getAssignmentId()+levelNum+highest);
		ins.setString(5, EntityParser.toString(this.getProblemStatement()));
		
		ins.executeUpdate();
		
		ResultSet insres = ins.getGeneratedKeys();
		
		if(insres.next())
			ret_id = insres.getInt(1);
		
		return ret_id;
		
	}

	/**
	 * Deletes this problem from the database.
	 */
	public void delete() throws SQLException
	{
		Connection conn = Database.getSingleton().getConnection();
	
		final String SQL_DELETE = "DELETE FROM problem WHERE id= ?";
		String SQL_STATUS_DELETE = "DELETE FROM problem_status WHERE id = ?";
		
		PreparedStatement delete = conn.prepareStatement(SQL_DELETE);
		delete.setInt(1, problem_id);

		delete.executeUpdate();
		
		delete = conn.prepareStatement(SQL_STATUS_DELETE);
		delete.setInt(1, problem_id);
		
		delete.executeUpdate();
	}

	
	/**
	 * Creates a new Problem in the database.
	 * 
	 * @param assignmentId The id of the problems assignment.
	 * @param level The level of the problem.
	 * @param name The name of the problem.
	 * @return The handler associated with the create problem.
	 * @throws Exception Error occurred inserting new problem into database.
	 */
	public static Problem create(int assignmentId, Level level, String name) throws Exception
	{
		// Get the connection to the database.
		Database db = Database.get();
		Connection conn = db.getConnection();
		
		// The query used to get the maximum problem order
		final String SQL_ORDER = "SELECT MAX(problem_order) FROM " +
				"problem WHERE assignment_id=? AND level=?";
		
		// Get the maximum problem order 
		PreparedStatement order = conn.prepareStatement(SQL_ORDER);
		order.setInt(1, assignmentId);
		order.setString(2, level.toString());
		ResultSet orderResult = order.executeQuery();
		
		// If no order was selected...
		if (!orderResult.next()) 
		{
			return null;
		}
		
		// Get the problem order to insert
		int problemOrder = orderResult.getInt(1) + 1;
		
		// The query to insert a problem.
		final String SQL_INSERT = "INSERT INTO problem " +
				"(assignment_id, level, name, problem_order) VALUES (?, ?, ?, ?)";
		
		// Create the statement and specify that the generated keys should return.
		PreparedStatement insert = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
		insert.setInt(1, assignmentId);
		insert.setString(2, level.toString());
		insert.setString(3, name);
		insert.setInt(4, problemOrder);
		
		// Execute the query
		insert.executeUpdate();
		
		// Get the returned keys
		ResultSet keys = insert.getGeneratedKeys();
		
		// If the key wasn't returned we can't finish creating a problem
		if (!keys.next()) 
		{
			return null;
		}
		
		// Get the id of the problem inserted.
		int problemId = keys.getInt(1);
		
		// Return the handler for that problem.
		return new Problem(problemId);
	}

	
}
