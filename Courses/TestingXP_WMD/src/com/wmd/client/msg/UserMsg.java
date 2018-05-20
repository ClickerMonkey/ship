package com.wmd.client.msg;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * This class stores user information.
 * 
 * @author Christopher Eby & Olga Zalamea & Steven Unger & Tristan Dalious
 */
public class UserMsg implements IsSerializable
{
	/**
	 * User's username as a String
	 */
	private String username;
	/**
	 * User's password as a String
	 */
	private String password;
	/**
	 * User's Id as an int
	 */
	private int userId;
	/**
	 * User's role as a Role Enum.
	 */
	private Role role;
	/**
	 * User's level as a Level enum.
	 */
	private Level level;
	/**
	 * True if user is enabled.
	 */
	private boolean enabled;
	/**
	 * User's first name
	 */
	private String firstname;
	/**
	 * User's last name
	 */
	private String lastname;
	/**
	 * User's period
	 */
	private String period;

	/**
	 * Empty constructor for serializability.
	 */
	public UserMsg()
	{
		// Empty
	}

	/**
	 * Constructs user from username and password.
	 * 
	 * @param username
	 *            username to construct user for
	 * @param password
	 *            users password
	 */
	public UserMsg(String username, String password)
	{
		this.username = username;
		this.password = password;
	}

	/**
	 * Gets the username.
	 * 
	 * @return username as a String
	 */
	public String getUsername()
	{
		return this.username;
	}

	/**
	 * Gets the user's password.
	 * 
	 * @return password as a String
	 */
	public String getPassword()
	{
		return this.password;
	}

	/**
	 * Gets the user's id
	 * 
	 * @return user id as an int
	 */
	public int getUserId()
	{
		return this.userId;
	}

	/**
	 * Sets the user Id
	 * 
	 * @param userId
	 *            user id as an int
	 */
	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	/**
	 * Sets the user Role
	 * 
	 * @param role
	 *            user role as Role
	 */
	public void setRole(Role role)
	{
		this.role = role;
	}

	/**
	 * Gets the user's role
	 * 
	 * @return user role as a Role
	 */
	public Role getRole()
	{
		return this.role;
	}

	/**
	 * Sets the user Level
	 * 
	 * @param level
	 *            user level as Level
	 */
	public void setLevel(Level level)
	{
		this.level = level;
	}

	/**
	 * Gets the user's level
	 * 
	 * @return user level as a Level
	 */
	public Level getLevel()
	{
		return this.level;
	}

	/**
	 * Sets if the user is enabled
	 * 
	 * @param enabled
	 *            user enabled as boolean
	 */
	public void setEnable(boolean enabled)
	{
		this.enabled = enabled;
	}

	/**
	 * Gets if the user is enabled
	 * 
	 * @return True if the user is enabled
	 */
	public boolean isEnabled()
	{
		return this.enabled;
	}

	/**
	 * Sets the username
	 * 
	 * @param username
	 *            username as String
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}

	/**
	 * Sets the user password
	 * 
	 * @param password
	 *            user password as String
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}
	/**
	 * Returns the User's last name
	 * @return lastname
	 * 			user's last name
	 */
	public String getLastName()
	{
		return this.lastname;
	}
	/**
	 * Returns the User's first name
	 * @return firstname
	 * 			user's first name
	 */
	public String getFirstName()
	{
		return firstname;
	}
	/**
	 * Returns the user's period
	 * @return period
	 * 			user's first period
	 */
	public String getPeriod()
	{
		return period;
	}
	
	public void setPeriod(String p)
	{
		period =  p;
	}
	/**
	 * Sets the user's first name
	 * @param firstname
	 */
	public void setFirstName(String firstname) 
	{
			this.firstname = firstname;
	}
	/**
	 * Sets the user's last name
	 * @param lastname
	 */
	public void setLastName(String lastname) 
	{
		this.lastname = lastname;
	}
	/**
	 * Sets the user's username
	 * @param username
	 */
	public void setUserName(String username) 
	{
		this.username = username;
	}
}
