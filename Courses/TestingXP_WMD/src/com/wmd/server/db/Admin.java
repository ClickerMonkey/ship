package com.wmd.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gwt.core.client.GWT;
import com.wmd.client.msg.Role;
import com.wmd.client.msg.UserMsg;

/**
 * The Admin database object class.
 * Handles creation and manipulation operations
 * for Admin-role users.
 * @author Steve U, Drew Q
 *
 */
public class Admin 
{

	/**
	 * The id for this instance
	 */
	int admin_id;
	
	/**
	 * Creates a new object to handle 
	 * operations for the given
	 * Admin id.
	 * @param id The id of the Admin that can
	 *  be operated on by this object.
	 */
	public Admin(int id)
	{
		this.admin_id = id;
	}
	
	/**
	 * Returns the user message  for a specific problem
	 * @return UserMsg holding the admin information
	 * @throws SQLException
	 */
	public UserMsg getMessage() throws SQLException
	{
		UserMsg msg = new UserMsg();
		msg.setUserId(this.admin_id);
		msg.setFirstName(this.getFirstName());
		msg.setLastName(this.getLastName());
		msg.setRole(Role.Admin);
		msg.setUsername(this.getUsername());
		msg.setPassword(this.getPassword());
		msg.setLevel(null);
		msg.setPeriod(null);
		msg.setEnable(this.isEnabled());
		return msg;
	}
	
	/**
	 * Gets the first name of the Admin
	 * @return The First Name of the Admin
	 * @throws SQLException An exception will be thrown 
	 *  if anything goes wrong in the SQL connection.
	 */
	public String getFirstName() throws SQLException 
	{
		String query = "SELECT firstname FROM user WHERE user.id = ? AND user.role = 'Admin'";
		Connection link = Database.getSingleton().getLink();

		String ret_str = "";

		if (link == null) 
		{
			return null;
		}

		try {

			PreparedStatement statement = link.prepareStatement(query);
			statement.setInt(1, this.admin_id);
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
			GWT.log("Error in getAdminFirstName(" + this.admin_id + ")", e);
			Database.get().rollBack();
			return null;
		}
		return ret_str;
	}

	/**
	 * Gets the last name of the Admin.
	 * @return The last name of the Admin
	 * @throws SQLException An exception will be thrown 
	 *  if anything goes wrong in the SQL connection.
	 */
	public String getLastName() throws SQLException {
		String query = "SELECT lastname FROM user WHERE user.id = ? AND user.role ='Admin';";
		Connection link = Database.getSingleton().getLink();
		
		String ret_str = "";
		
		if (link == null) 
		{
			return null;
		}

		try 
		{

			PreparedStatement statement = link.prepareStatement(query);
			statement.setInt(1, this.admin_id);
			ResultSet result = statement.executeQuery();

			// If there is o result from the database then null will be returned
			if (!result.next()) {
				return null;
			}

			// Return the successfully UserMsg.
			ret_str = result.getString(1);
		} catch (SQLException e) {
			GWT.log("Error in getAdminLastName(" + this.admin_id + ")", e);
		}
		return ret_str;
	}

	/**
	 * Gets the username of this Admin
	 * @return The username of the Admin
	 * @throws SQLException An exception will be thrown 
	 *  if anything goes wrong in the SQL connection.
	 */
	public String getUsername() throws SQLException 
	{
		String query = "SELECT username FROM user WHERE user.id = ? AND user.role ='Admin';";
		Connection link = Database.getSingleton().getLink();
		
		String ret_str = "";
		
		if (link == null) 
		{
			return null;
		}

		try {

			PreparedStatement statement = link.prepareStatement(query);
			statement.setInt(1, this.admin_id);
			ResultSet result = statement.executeQuery();

			// If there is o result from the database then null will be returned
			if (!result.next()) 
			{
				return null;
			}

			// Return the successfully UserMsg.
			ret_str = result.getString(1);
			
		} catch (SQLException e) {
			GWT.log("Error in getAdminUsername(" + this.admin_id + ")", e);
		}
		return ret_str;
	}

	/**
	 * Gets the password of the Admin
	 * @return The password of the Admin
	 * @throws SQLException An exception will be thrown 
	 *  if anything goes wrong in the SQL connection.
	 */
	public String getPassword() throws SQLException 
	{
		String query = "SELECT password FROM user WHERE user.id = ? AND user.role ='Admin';";
		Connection link = Database.getSingleton().getLink();

		String ret_str = "";
		
		if (link == null) 
		{
			return null;
		}

		try {

			PreparedStatement statement = link.prepareStatement(query);
			statement.setInt(1, this.admin_id);
			ResultSet result = statement.executeQuery();

			// If there is o result from the database then null will be returned
			if (!result.next()) {
				return null;
			}

			// Return the successfully UserMsg.
			ret_str = result.getString(1);
			
		} catch (SQLException e) {
			GWT.log("Error in getAdminPassword(" + this.admin_id + ")", e);
		}
		return ret_str;
	}

	/**
	 * Returns the enabled status of the Admin
	 * @return True if the Admin is enabled
	 * @throws SQLException An exception will be thrown 
	 *  if anything goes wrong in the SQL connection.
	 */
	public boolean isEnabled() throws SQLException {
		String query = "SELECT enabled FROM user WHERE user.id = ? AND user.role ='Admin';";
		Connection link = Database.getSingleton().getLink();
		
		boolean ret_bool = false;
		
		if (link == null) 
		{
			throw new SQLException("link failed");
		}

		try {

			PreparedStatement statement = link.prepareStatement(query);
			statement.setInt(1, this.admin_id);
			ResultSet result = statement.executeQuery();

			// If there is o result from the database then null will be returned
			if (!result.next()) 
			{
				throw new SQLException("no result for this id, this should never happen");
			}

			// Return the successfully UserMsg.
			ret_bool = result.getBoolean(1);
			
		} catch (SQLException e) {
			GWT.log("Error in getAdminEnabled(" + this.admin_id + ")", e);
		}
		return ret_bool;
	}

	/**
	 * Sets the first name of the Admin.
	 * @param firstname The new first name of the Admin
	 * @throws SQLException An exception will be thrown 
	 *  if anything goes wrong in the SQL connection.
	 */
	public void setFirstName(String firstname) throws SQLException 
	{
		String query = "UPDATE user SET firstname = ? WHERE user.id = ? AND user.role ='Admin';";
		Connection link = Database.getSingleton().getLink();
		
		if (link == null) 
		{
			throw new SQLException("link failed");
		}

		try
		{
			//create the prep stmt
			PreparedStatement stmt = link.prepareStatement(query);
			
			//bind variables
			stmt.setString(1, firstname);
			stmt.setInt(2, this.admin_id);
			
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
	 * Sets the last name of the Admin
	 * @param lastname The new last name of the Admin
	 * @throws SQLException An exception will be thrown 
	 *  if anything goes wrong in the SQL connection.
	 */
	public void setLastName(String lastname) throws SQLException 
	{
		String query = "UPDATE user SET lastname = ? WHERE user.id = ? AND user.role ='Admin';";
		Connection link = Database.getSingleton().getLink();
		
		if (link == null) 
		{
			throw new SQLException("link failed");
		}

		try
		{
			//create the prep stmt
			PreparedStatement stmt = link.prepareStatement(query);
			
			//bind variables
			stmt.setString(1, lastname);
			stmt.setInt(2, this.admin_id);
			
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
	 * Sets the username of the Admin
	 * @param username The new username of the Admin
	 * @throws SQLException An exception will be thrown 
	 *  if anything goes wrong in the SQL connection.
	 */
	public void setUsername(String username) throws SQLException 
	{
		String query = "UPDATE user SET username = ? WHERE user.id = ? AND user.role ='Admin';";
		Connection link = Database.getSingleton().getLink();
		
		if (link == null) 
		{
			throw new SQLException("link failed");
		}

		try
		{
			//create the prep stmt
			PreparedStatement stmt = link.prepareStatement(query);
			
			//bind variables
			stmt.setString(1, username);
			stmt.setInt(2, this.admin_id);
			
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
	 * Sets the password of the Admin
	 * @param password The new password of the Admin
	 * @throws SQLException An exception will be thrown 
	 *  if anything goes wrong in the SQL connection.
	 */
	public void setPassword(String password) throws SQLException 
	{
		String query = "UPDATE user SET password = ? WHERE user.id = ? AND user.role ='Admin';";
		Connection link = Database.getSingleton().getLink();
		
		if (link == null) 
		{
			throw new SQLException("link failed");
		}

		try
		{
			//create the prep stmt
			PreparedStatement stmt = link.prepareStatement(query);
			
			//bind variables
			stmt.setString(1, password);
			stmt.setInt(2, this.admin_id);
			
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
	 * Sets the enabled status of the Admin
	 * @param enabled The new enabled status of the Admin
	 * @throws SQLException An exception will be thrown 
	 *  if anything goes wrong in the SQL connection.
	 */
	public void setEnabled(boolean enabled) throws SQLException 
	{
		String query = "UPDATE user SET enabled = ? WHERE user.id = ? AND user.role ='Admin';";
		Connection link = Database.getSingleton().getLink();
		
		if (link == null) 
		{
			throw new SQLException("link failed");
		}

		try
		{
			//create the prep stmt
			PreparedStatement stmt = link.prepareStatement(query);
			
			//bind variables
			stmt.setInt(1, (enabled ? 1 : 0));
			stmt.setInt(2, this.admin_id);
			
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
}
