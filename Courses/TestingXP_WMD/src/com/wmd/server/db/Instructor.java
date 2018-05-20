package com.wmd.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gwt.core.client.GWT;
import com.wmd.client.msg.Role;
import com.wmd.client.msg.UserMsg;

/**
 * The Instructor database object class.
 * Handles creation and manipulation operations
 * for instructor-role users.
 * @author Steve U, Tristan D, Drew Q, Scotty R.
 *
 */
public class Instructor 
{

	/**
	 * The id for this instance
	 */
	int instructor_id;
	
	/**
	 * Creates a new object to handle 
	 * operations for the given
	 * instructor id.
	 * @param id The id of the instructor that can
	 *  be operated on by this object.
	 * @throws Exception 
	 */
	public Instructor(int id) throws Exception
	{
		String query = "SELECT id FROM user WHERE id = ?";
		Connection link = Database.getSingleton().getLink();
		
		try
		{
			//Prepare the statement
			PreparedStatement stmt = link.prepareStatement(query);
			
			//Bind the Id
			stmt.setInt(1, id);
			
			//Store the results
			ResultSet rs = stmt.executeQuery();
			
			//See if we got anything
			if(!rs.next())
			{
				//Throw exception!
				throw new Exception("No Instructor At This Id");
			}
			
			this.instructor_id = id;
		}	
		catch(SQLException e)
		{
			GWT.log("Error in SQL: Instructor->Constructor(id)", e);
			Database.get().rollBack();
		}
	}
	
	/**
	 * Creates a new object to handle 
	 * operations for the given
	 * username.
	 * @param id The id of the instructor that can
	 *  be operated on by this object.
	 * @throws Exception 
	 */
	public Instructor(String username) throws Exception
	{
		String query = "SELECT id FROM user WHERE user.username = ?";
		Connection link = Database.getSingleton().getLink();
		
		if(link == null)
			throw new Exception("Connection Failed!");
		
		try {
			PreparedStatement stmt = link.prepareStatement(query);
			stmt.setString(1, username);
			
			ResultSet result = stmt.executeQuery();
			
			if(result.next())
				this.instructor_id = result.getInt(1);
		} catch (SQLException e) 
		{
			GWT.log("Error in constructor: Instructor(String username)", e);
			Database.get().rollBack();
		}
	}
	
	/**
	 * Creates a new Instructor in the database
	 * and instantiates the Instructor object.
	 * @param id The id of the instructor that can
	 *  be operated on by this object.
	 * @throws Exception 
	 */
	public Instructor(String firstname, String lastname, Role role, String username,
			String password, boolean enabled) throws Exception
	{
		//If the username isn't already in the DB
		String query = "SELECT id FROM user WHERE user.username = ?";
		Connection link = Database.getSingleton().getLink();
		
		if (link == null)
			throw new Exception("Connection Failed!");

		try {
			PreparedStatement statement = link.prepareStatement(query);
			statement.setString(1, username);
			ResultSet result = statement.executeQuery();

			// If there is a result, throw an exception
			if (result.next()) 
				throw new Exception("Cannot create new Instructor - Username taken");
			
			this.instructor_id = createInstructor(firstname, lastname, role, username, password, enabled);
			
		} catch (SQLException e) 
		{
			e.printStackTrace();
			GWT.log("Error in constructor: Instructor(String, String, Role, String, String, Level, String, Boolean)", e);
			Database.get().rollBack();
		}
	}
	
	/**
	 * Gets the ID of the Instructor
	 */
	public int getId()
	{
		return this.instructor_id;
	}
	
	/**
	 * Returns the user message  for a specific problem
	 * @return UserMsg holding the instructor information
	 * @throws Exception 
	 */
	public UserMsg getMessage() throws Exception
	{
		UserMsg msg = new UserMsg();
		msg.setUserId(this.instructor_id);
		msg.setFirstName(this.getFirstName());
		msg.setLastName(this.getLastName());
		msg.setRole(Role.Instructor);
		msg.setUsername(this.getUsername());
		msg.setPassword(this.getPassword());
		msg.setLevel(null);
		msg.setPeriod(null);
		msg.setEnable(this.isEnabled());
		return msg;
	}
	
	/**
	 * Gets the first name of the instructor
	 * @return The First Name of the instructor
	 * @throws Exception 
	 */
	public String getFirstName() throws Exception 
	{
		String query = "SELECT firstname FROM user WHERE user.id = ? AND user.role = 'Instructor'";
		Connection link = Database.getSingleton().getLink();

		String ret_str = "";

		if (link == null)
			throw new Exception("Connection Failed!");

		try {

			PreparedStatement statement = link.prepareStatement(query);
			statement.setInt(1, this.instructor_id);
			ResultSet result = statement.executeQuery();

			// If there is no result from the database then null will be returned
			if (!result.next()) 
			{
				return null;
			}

			// Return the successfully UserMsg.
			ret_str = result.getString(1);
			
		} catch (SQLException e) 
		{
			GWT.log("Error in getInstructorFirstName(" + this.instructor_id + ")", e);
			Database.get().rollBack();
			return null;
		}
		return ret_str;
	}

	/**
	 * Gets the last name of the instructor.
	 * @return The last name of the instructor
	 * @throws Exception 
	 */
	public String getLastName() throws Exception {
		String query = "SELECT lastname FROM user WHERE user.id = ? AND user.role ='Instructor';";
		Connection link = Database.getSingleton().getLink();
		
		String ret_str = "";
		
		if (link == null)
			throw new Exception("Connection Failed!");

		try 
		{

			PreparedStatement statement = link.prepareStatement(query);
			statement.setInt(1, this.instructor_id);
			ResultSet result = statement.executeQuery();

			// If there is o result from the database then null will be returned
			if (!result.next()) {
				return null;
			}

			// Return the successfully UserMsg.
			ret_str = result.getString(1);
		} catch (SQLException e) {
			GWT.log("Error in getInstructorLastName(" + this.instructor_id + ")", e);
		}
		return ret_str;
	}

	/**
	 * Gets the username of this instructor
	 * @return The username of the instructor
	 * @throws Exception 
	 * @throws Exception 
	 */
	public String getUsername() throws Exception
	{
		String query = "SELECT username FROM user WHERE user.id = ? AND user.role ='Instructor';";
		Connection link = Database.getSingleton().getLink();
		
		String ret_str = "";
		
		if (link == null)
			throw new Exception("Connection Failed!");

		try {

			PreparedStatement statement = link.prepareStatement(query);
			statement.setInt(1, this.instructor_id);
			ResultSet result = statement.executeQuery();

			// If there is o result from the database then null will be returned
			if (!result.next()) 
			{
				return null;
			}

			// Return the successfully UserMsg.
			ret_str = result.getString(1);
			
		} catch (SQLException e) {
			GWT.log("Error in getInstructorUsername(" + this.instructor_id + ")", e);
		}
		return ret_str;
	}

	/**
	 * Gets the password of the instructor
	 * @return The password of the instructor
	 * @throws Exception 
	 */
	public String getPassword() throws Exception 
	{
		String query = "SELECT password FROM user WHERE user.id = ? AND user.role ='Instructor';";
		Connection link = Database.getSingleton().getLink();

		String ret_str = "";
		
		if (link == null)
			throw new Exception("Connection Failed!");

		try {

			PreparedStatement statement = link.prepareStatement(query);
			statement.setInt(1, this.instructor_id);
			ResultSet result = statement.executeQuery();

			// If there is o result from the database then null will be returned
			if (!result.next()) {
				return null;
			}

			// Return the successfully UserMsg.
			ret_str = result.getString(1);
			
		} catch (SQLException e) {
			GWT.log("Error in getInstructorPassword(" + this.instructor_id + ")", e);
		}
		return ret_str;
	}

	/**
	 * Returns the enabled status of the instructor
	 * @return True if the instructor is enabled
	 * @throws Exception 
	 */
	public boolean isEnabled() throws Exception {
		String query = "SELECT enabled FROM user WHERE user.id = ? AND user.role ='Instructor';";
		Connection link = Database.getSingleton().getLink();
		
		boolean ret_bool = false;
		
		if (link == null)
			throw new Exception("Connection Failed!");

		try {

			PreparedStatement statement = link.prepareStatement(query);
			statement.setInt(1, this.instructor_id);
			ResultSet result = statement.executeQuery();

			// If there is o result from the database then null will be returned
			if (!result.next()) 
			{
				throw new SQLException("no result for this id, this should never happen");
			}

			// Return the successfully UserMsg.
			ret_bool = result.getBoolean(1);
			
		} catch (SQLException e) {
			GWT.log("Error in getInstructorEnabled(" + this.instructor_id + ")", e);
		}
		return ret_bool;
	}

	/**
	 * Sets the first name of the instructor.
	 * @param firstname The new first name of the instructor
	 * @throws Exception 
	 */
	public void setFirstName(String firstname) throws Exception 
	{
		String query = "UPDATE user SET firstname = ? WHERE user.id = ? AND user.role ='Instructor';";
		Connection link = Database.getSingleton().getLink();
		
		if (link == null)
			throw new Exception("Connection Failed!");

		try
		{
			//create the prep stmt
			PreparedStatement stmt = link.prepareStatement(query);
			
			//bind variables
			stmt.setString(1, firstname);
			stmt.setInt(2, this.instructor_id);
			
			//execute update
			stmt.executeUpdate();
		}
		catch (SQLException e)
		{
			GWT.log("Error in setFirstName",e);
			link.rollback();
			throw e;
		}
		
		return;
	}

	/**
	 * Sets the last name of the instructor
	 * @param lastname The new last name of the instructor
	 * @throws Exception 
	 */
	public void setLastName(String lastname) throws Exception 
	{
		String query = "UPDATE user SET lastname = ? WHERE user.id = ? AND user.role ='Instructor';";
		Connection link = Database.getSingleton().getLink();
		
		if (link == null)
			throw new Exception("Connection Failed!");

		try
		{
			//create the prep stmt
			PreparedStatement stmt = link.prepareStatement(query);
			
			//bind variables
			stmt.setString(1, lastname);
			stmt.setInt(2, this.instructor_id);
			
			//execute update
			stmt.executeUpdate();
		}
		catch (SQLException e)
		{
			GWT.log("Error in setLastName",e);
			link.rollback();
			throw e;
		}
		
		return;
		
	}

	/**
	 * Sets the username of the instructor
	 * @param username The new username of the instructor
	 * @throws Exception 
	 */
	public void setUsername(String username) throws Exception 
	{
		String query = "UPDATE user SET username = ? WHERE user.id = ? AND user.role ='Instructor';";
		Connection link = Database.getSingleton().getLink();
		
		if (link == null)
			throw new Exception("Connection Failed!");

		try
		{
			//create the prep stmt
			PreparedStatement stmt = link.prepareStatement(query);
			
			//bind variables
			stmt.setString(1, username);
			stmt.setInt(2, this.instructor_id);
			
			//execute update
			stmt.executeUpdate();
		}
		catch (SQLException e)
		{
			GWT.log("Error in setUserName",e);
			link.rollback();
			throw e;
		}
		
		return;
		
	}

	/**
	 * Sets the password of the instructor
	 * @param password The new password of the instructor
	 * @throws Exception 
	 */
	public void setPassword(String password) throws Exception 
	{
		String query = "UPDATE user SET password = ? WHERE user.id = ? AND user.role ='Instructor';";
		Connection link = Database.getSingleton().getLink();
		
		if (link == null)
			throw new Exception("Connection Failed!");

		try
		{
			//create the prep stmt
			PreparedStatement stmt = link.prepareStatement(query);
			
			//bind variables
			stmt.setString(1, password);
			stmt.setInt(2, this.instructor_id);
			
			//execute update
			stmt.executeUpdate();
		}
		catch (SQLException e)
		{
			GWT.log("Error in setPassword",e);
			link.rollback();
			throw e;
		}
		
		return;
		
	}

	/**
	 * Sets the enabled status of the instructor
	 * @param enabled The new enabled status of the instructor
	 * @throws Exception 
	 */
	public void setEnabled(boolean enabled) throws Exception 
	{
		String query = "UPDATE user SET enabled = ? WHERE user.id = ? AND user.role ='Instructor';";
		Connection link = Database.getSingleton().getLink();
		
		if (link == null)
			throw new Exception("Connection Failed!");
		
		try
		{
			//create the prep stmt
			PreparedStatement stmt = link.prepareStatement(query);
			
			//bind variables
			stmt.setInt(1, (enabled ? 1 : 0));
			stmt.setInt(2, this.instructor_id);
			
			//execute update
			stmt.executeUpdate();
		}
		catch (SQLException e)
		{
			GWT.log("Error in setEnabled",e);
			link.rollback();
			throw e;
		}
		return;
	}

	/**
	 * Creates a new Instructor, and returns its id
	 * @param firstname
	 * @param lastname
	 * @param role
	 * @param username
	 * @param password
	 * @param level
	 * @param period
	 * @param enabled
	 * @return id of the newly created instructor
	 * @throws Exception 
	 */
	private int createInstructor(String firstname, String lastname, Role role, String username,
			String password, boolean enabled) throws Exception
	{
		String query = "INSERT INTO user " +
				"(firstname,lastname,role,username,password,enabled) VALUES " +
				"(?,?,?,?,?,?)";
		Connection link = Database.getSingleton().getLink();
		
		if (link == null)
			throw new Exception("Connection Failed!");
		
		if (password == null)
			password = "";
		
		try
		{
			//create the prep stmt
			PreparedStatement stmt = link.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			//bind variables
			stmt.setString(1, firstname);
			stmt.setString(2, lastname);
			stmt.setString(3, role.toString());
			stmt.setString(4, username);
			stmt.setString(5, password);
			stmt.setBoolean(6, enabled);
			
			//execute update
			stmt.executeUpdate();
			
			ResultSet resultKey = stmt.getGeneratedKeys();
			if (!resultKey.next())
				throw new Exception("Creating new user failed - createInstructor()");
			return resultKey.getInt(1);
		}
		catch (SQLException e)
		{
			GWT.log("Error in setEnabled",e);
			link.rollback();
			throw e;
		}
	}
	
	/**
	 * Deletes the instructor from the database
	 * @param id The user id of the instructor
	 * @throws Exception 
	 */
	public boolean deleteUser(int id) throws Exception 
	{
		String query = "DELETE FROM user WHERE user.id = ?";
		Connection link = Database.getSingleton().getLink();
		
		if (link == null)
			throw new Exception("Connection Failed!");
		
		try
		{
			//create the prep stmt
			PreparedStatement stmt = link.prepareStatement(query);
			
			//bind variables
			stmt.setInt(1, (id));
			
			//execute update
			return (stmt.executeUpdate() == 1);
		}
		catch (SQLException e)
		{
			GWT.log("Error in setEnabled",e);
			link.rollback();
			throw e;
		}
	}
}
