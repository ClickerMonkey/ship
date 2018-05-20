package com.wmd.server.db;

import java.sql.*;

import com.google.gwt.core.client.GWT;
import com.wmd.client.service.ServiceException;

/**
 * The Assignment database object class.  Handles
 * and manipulates all database operations for the
 * assignment loaded in the constructor.
 * 
 * @author Drew Q, Paul C
 *
 */
public class Assignment {

	/**
	 * The assignment Id to be stored
	 */
	int assignment_id = 0;
	
	/**
	 * The link to the connection
	 */
	Connection conn = null;

	/**
	 * Constructs a new assignment
	 * from the id given.  This object
	 * can then manipulate data for the
	 * given id
	 * 
	 * @param id The Id that this Assignment can manipulate
	 * 
	 * @throws Exception An exception will be thrown if there is no Assignment with this Id
	 * @throws SQLException An exception will be thrown if something goes wrong with the SQL call
	 */
	public Assignment(int id) throws Exception
	{
		this.setConn(Database.get().getConnection());
		
		this.getConn().setAutoCommit(false);
		
		this.assignment_id = id;
		
		
		String query = "SELECT * FROM assignment WHERE id = ?";
		
		try
		{
			//Prepare the statement
			PreparedStatement stmt = this.getConn().prepareStatement(query);
			
			//Bind the Id
			stmt.setInt(1, this.assignment_id);
			
			//Store the results
			ResultSet rs = stmt.executeQuery();
			
			//See if we got anything
			if(!rs.next())
			{
				//Throw exception!
				throw new Exception("No Assignment At This Id");
			}
		}
		catch(SQLException e)
		{
			GWT.log("Error in SQL: Assignment->Constructor(id)", e);
			this.getConn().rollback();
			throw e;
		}
		
	}
	
	/**
	 * Creates a new assignment given
	 * a name and a boolean state.  If an assignment
	 * like the one specified exists, tan exception
	 * will be thrown.
	 * 
	 * @param name The name of the assignment.
	 * @param enabled The enabled state of the assignment.
	 *
	 * @throws Exception An exception will be thrown if the insert fails.
	 * @throws SQLException An exception could be thrown on an SQL error.
	 * @throws ServiceException An exception will be thrown if this is
	 *  a duplicate.
	 */
	public Assignment(String name, boolean enabled) throws Exception 
	{
		this.setConn(Database.get().getConnection());

		this.getConn().setAutoCommit(false);
		
		String query = "SELECT id FROM assignment WHERE name = ?";
		
		try
		{
			//Prepare the statement
			PreparedStatement stmt = this.getConn().prepareStatement(query);
			
			//Bind the variables
			stmt.setString(1, name);
			
			//Get the result set
			ResultSet rs = stmt.executeQuery();
			
			//Did I get results
			if(!rs.next())
			{
				this.assignment_id = this.insertAssignment(name,enabled);
			}
			else
			{
				throw new ServiceException("This assignment is a duplicate and cannot be inserted.");
				//this.assignment_id = rs.getInt(1);
			}
		}
		catch(Exception e)
		{
			GWT.log("Error in SQL: Assignment->Constructor(name,enabled)", e);
			this.getConn().rollback();
			throw e;
		}
	}

	/**
	 * Returns the Connection link being used by this instance
	 * @return The database connection being used
	 */
	public Connection getConn()
	{
		return this.conn;
	}
	
	/**
	 * Sets the database connection to be used for this instance
	 * @param conn The connection to use
	 */
	public void setConn(Connection conn)
	{
		this.conn = conn;
	}
	
	/**
	 * Gets the Id of this Assignment
	 * 
	 * @return The Id of this Assignment
	 */
	public int getId() 
	{
		return this.assignment_id;
	}
	
	/**
	 * Gets the name of this Assignment
	 * 
	 * @return The name of this Assignment
	 * 
	 * @throws Exception An exception will be thrown if either
	 *  an Assignment cannot be found or there is an SQL error.
	 */
	public String getName() throws Exception
	{
		String query = "SELECT name FROM assignment WHERE id = ?";
		
		String ret_name = "";
		
		try
		{
			//Prepare the statement
			PreparedStatement stmt = this.getConn().prepareStatement(query);
			
			//Bind the Id
			stmt.setInt(1, this.assignment_id);
			
			//Get the result
			ResultSet rs = stmt.executeQuery();
			
			//Please let us get something
			if(!rs.next())
			{
				throw new Exception("No Assignment Found! (getName)");
			}
			
			//store it
			ret_name = rs.getString(1);
		}
		catch (SQLException e)
		{
			//errawr
			GWT.log("Error in SQL: Assignment->getName()", e);
			this.getConn().rollback();
			throw e;				
		}
		
		return ret_name; 
	}

	/**
	 * Sets a new name for this Assignment
	 * 
	 * @param name The new name for this Assignment
	 * 
	 * @throws SQLException An exception will be thrown if SQL has
	 *  an error or the assignment cannot be updated.
	 */
	public void setName(String name) throws SQLException 
	{
		String query = "UPDATE assignment SET name = ? WHERE id = ?";
		
		try
		{
			//Prepare the statement
			PreparedStatement stmt = this.getConn().prepareStatement(query);
			
			//Bind the variables
			stmt.setString(1, name);
			stmt.setInt(2, this.assignment_id);
			
			//Execute the update
			stmt.executeUpdate();
		}
		catch(SQLException e)
		{
			//errawr
			GWT.log("Error in SQL: Assignment->setName(name)", e);
			this.getConn().rollback();
			throw e;	
		}
		
		//commit change
		Database.get().commit();
	}
	
	/**
	 * Returns whether the Assignment is enabled
	 * or not.
	 * 
	 * @return True if enabled
	 * 
	 * @throws Exception Throws an exception if something in the SQL
	 *  goes awry.
	 */
	public boolean isEnabled() throws Exception
	{
		String query = "SELECT enabled FROM assignment WHERE id = ?";
		
		boolean enabled = false;
		
		try
		{
			//Prepare the statement
			PreparedStatement stmt = this.getConn().prepareStatement(query);
			
			//Bind the Id
			stmt.setInt(1, this.assignment_id);
			
			//Get the result
			ResultSet rs = stmt.executeQuery();
			
			//Please let us get something
			if(!rs.next())
			{
				throw new Exception("No Assignment Found! (getName)");
			}
			
			//store it
			enabled = rs.getBoolean(1);
		}
		catch (SQLException e)
		{
			//errawr
			GWT.log("Error in SQL: Assignment->isEnabled()", e);
			this.getConn().rollback();
			throw e;				
		}
		
		return enabled; 
	}

	/**
	 * Sets whether this Assignment is enabled or not
	 * 
	 * @param enabled The new enabled state of this Assignment.\
	 * 
	 * @throws SQLException Throws an Exception if the SQL has an error
	 */
	public void setEnabled(boolean enabled) throws SQLException 
	{

		String query = "UPDATE assignment SET enabled = ? WHERE id = ?";
		
		try
		{
			//Prepare the statement
			PreparedStatement stmt = this.getConn().prepareStatement(query);
			
			//Bind the variables
			stmt.setInt(1, (enabled ? 1 : 0));
			stmt.setInt(2, this.assignment_id);
			
			//Execute the update
			stmt.executeUpdate();
		}
		catch(SQLException e)
		{
			//errawr
			GWT.log("Error in SQL: Assignment->setEnable(enabled)", e);
			this.getConn().rollback();
			throw e;	
		}
		
		//commit change
		Database.get().commit();
		
	}

	/**
	 * Inserts this assignment into the database
	 * 
	 * @param name The name of the assignment
	 * @param enabled Whether the assignment is enabled or not
	 * 
	 * @return The Id of the new assignment
	 * 
	 * @throws SQLException An SQL Exception may be thrown if something goes wrong
	 *  with the SQL.
	 */
	private int insertAssignment(String name, boolean enabled) throws Exception
	{
		int ret_id = 0;

		String query = "INSERT INTO assignment (name, enabled) VALUES (?, ?)";
		
		try
		{
			//Prepare statement and set it to return keys
			PreparedStatement stmt = this.getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			//Bind the data
			stmt.setString(1, name);
			stmt.setInt(2, (enabled ? 1 : 0));
			
			//Insert it and get the data
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			
			if(!rs.next())
			{
				throw new Exception("Error: No keys generated by insertAssignment");
			}
			
			ret_id = rs.getInt(1);
		}
		catch(SQLException e)
		{
			GWT.log("Error in SQL: Assignment->insertAssignment(name,enabled)", e);
			this.getConn().rollback();
			throw e;	
		} 
		
		//commit the change
		Database.get().commit();
		
		//return the generated id
		return ret_id;
	}
	
	/**
	 * Deletes this Assignment from the database.
	 * 
	 * @return The number of rows deleted (should be 1)
	 * 
	 * @throws SQLException An SQLException will be thrown
	 *  if something goes wrong.
	 */
	public int delete() throws SQLException
	{
		int ret_int = 0;
		
		String query = "DELETE FROM assignment WHERE id = ?";
		
		try
		{
			//Prepare the statement
			PreparedStatement stmt = this.getConn().prepareStatement(query);
			
			//Bind the variables
			stmt.setInt(1, this.assignment_id);
			
			//Execute the update
			ret_int = stmt.executeUpdate();
		}
		catch(SQLException e)
		{
			//errawr
			GWT.log("Error in SQL: Assignment->delete()", e);
			this.getConn().rollback();
			throw e;
		}
		
		//commit change
		Database.get().commit();
		return ret_int;
	}
	
}
