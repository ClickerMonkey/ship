package com.wmd.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.wmd.client.msg.UserMsg;
/**
 * Class for retrieving all Admins
 * @author Steve Unger, Drew Quackenbos
 *
 */
public class AdminList 
{
	/**
	 * The connection to the database
	 */
	Connection conn = null;
	/**
	 * Constructor for the AdminList class
	 * @throws SQLException
	 */
	public AdminList() throws SQLException
	{
		this.setConn(Database.get().getConnection());
		
		this.getConn().setAutoCommit(false);
	}
	
	/**
	 * Method that returns all Admins in an array list user msg
	 * @return - all Admins
	 * @throws Exception
	 */
	public ArrayList<UserMsg> getAllAdmins() throws Exception
	{
		ArrayList<UserMsg> result = new ArrayList<UserMsg>();
		
		String query = "SELECT id FROM user WHERE user.role = 'Admin';";
		
		try
		{
			//Prepare the statement
			PreparedStatement stmt = this.getConn().prepareStatement(query);
			
			//Get the result
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				result.add(new Admin(rs.getInt(1)).getMessage());
			}
			
			if(result.size()<=0)
			{
				throw new Exception("No Admins Found! (getAllAdmins())");
			}
			
		} catch (SQLException e)
		{
			GWT.log("Error in SQL: AdminList -> getAllAdmins()", e);
			this.getConn().rollback();
			throw e;
		}
		
		return result;
	}
	
	/**
	 * Method that gets all Admins based on whether or not they
	 * are enabled or disabled
	 * @param enabled - whether the Admin is teaching or not
	 * @return - all Admins based on input
	 * @throws Exception
	 */
	public ArrayList<UserMsg> getAdmins(boolean enabled) throws Exception
	{
		ArrayList<UserMsg> allAssignments = getAllAdmins();
		ArrayList<UserMsg> selectedAdmins = new ArrayList<UserMsg>();
		
		for(int i = 0; i < allAssignments.size(); i++)
			if(allAssignments.get(i).isEnabled() == enabled)
				selectedAdmins.add(allAssignments.get(i));
			
		return selectedAdmins;
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
}
