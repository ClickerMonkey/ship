package com.wmd.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gwt.core.client.GWT;
import com.wmd.client.msg.Role;
import com.wmd.client.msg.UserMsg;
import com.wmd.client.msg.Level;
/**
 * Class that interacts with the database for a specific student
 * 
 * @author Olga Zalamea and Scotty Rhinehart, Sam Storino
 */

public class Student {

	/**
	 * The assignment Id to be stored
	 */
	int userId;
	
	/**
	 * The link to the connection
	 */
	Connection conn = null;

	
	/**
	 * Constructor for the Student
	 */
	public Student (int userId) throws Exception
	{
		this.setConn(Database.get().getConnection());		
		this.getConn().setAutoCommit(false);
		this.userId=userId;
		String query = "SELECT * FROM user WHERE id = ?";
		
		try
		{
			//Prepare the statement
			PreparedStatement stmt = this.getConn().prepareStatement(query);
			
			//Bind the Id
			stmt.setInt(1, this.userId);
			
			//Store the results
			ResultSet rs = stmt.executeQuery();
			
			//See if we got anything
			if(!rs.next())
			{
				// TODO remove
				System.out.println(userId);
				//Throw exception!
				throw new Exception("No Student At This Id");
			}
		}	
		catch(SQLException e)
		{
			GWT.log("Error in SQL: Student->Constructor(id)", e);
			this.getConn().rollback();
			throw e;
		}
	}
	
	/**
	 * Creates a new Student in the database
	 * and instantiates the Student object.
	 * @param id The id of the student that can
	 *  be operated on by this object.
	 * @throws Exception 
	 */
	public Student(String firstname, String lastname, Role role, String username,
			String password, Level level, String period, boolean enabled) throws Exception
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
				throw new Exception("Cannot create new Student - Username taken");
			
			this.userId = createStudent(firstname, lastname, role, username, password, level, period, enabled);
			
		} catch (SQLException e) 
		{
			e.printStackTrace();
			GWT.log("Error in constructor: Student(String, String, Role, String, String, Level, String, Boolean)", e);
			Database.get().rollBack();
		}
	}
	
	/**
	 * Constructor for Student that requires username and password
	 * 
	 * @param username String for username
	 * @param password String for password
	 * @throws Exception when username and password are not in the database
	 */
	public Student(String username, String password)throws Exception
	{
		String SQL_USER = "Select id FROM user WHERE (username =? AND password =?) OR (username=? AND role='Instructor' AND password IS NULL);";
		this.setConn(Database.get().getConnection());		
		this.getConn().setAutoCommit(false);
		
		try
		{
			//Prepare the statement
			PreparedStatement statement = conn.prepareStatement(SQL_USER);
			
			//Bind the username, password
			statement.setString(1, username);
			statement.setString(2, password);
			statement.setString(3, username);
			
			//Store the results
			ResultSet result = statement.executeQuery();
			
			//See if we got anything
			if(!result.next())
			{
				//Throw exception!
				throw new Exception("No User with this username and password");
			}   
			this.userId=result.getInt("id");
		}
		catch(SQLException e)
		{
			GWT.log("Error in SQL: Student->Constructor(username, password)", e);
			this.getConn().rollback();
			throw e;
		}
	}
	/**
	 * Gets the userMsg for the signIn service
	 * @return the userMsg with the information about the student
	 * @throws Exception when username and password are not in the database
	 */
	public UserMsg signinUser() throws Exception
	{
		try
		{
			UserMsg user=new UserMsg(getUsername(), getPassword());
			user=this.getMessage();
			return user;
		}
		catch(Exception e)
		{
			GWT.log("Error in SQL: Student->signinUser(username, password)", e);
			throw e;
		}
	}
	
	/**
	 * Returns the user message for a specific student
	 * @return UserMsg holding the student information
	 * @throws SQLException
	 */
	public UserMsg getMessage() throws Exception
	{
		UserMsg msg = new UserMsg();
		msg.setUserId(this.userId);
		msg.setFirstName(this.getFirstname());
		msg.setLastName(this.getLastname());
		msg.setRole(this.getRole());
		msg.setUsername(this.getUsername());
		msg.setPassword(this.getPassword());
		msg.setLevel(this.getLevel());
		msg.setPeriod(this.getPeriod());
		msg.setEnable(this.isEnabled());
		return msg;
	}

	/**
	 * Gets the users student ID
	 */
	
	public int getId()
	{
		return this.userId;
	}

	/**
	 * Gets the firstname of this Student
	 * @return The firstname of this Student
	 * @throws Exception Exception An exception will be thrown if either
	 *  an Student cannot be found or there is an SQL error.
	 */
	public String getFirstname() throws Exception
	{
		String SQL_USER = "SELECT firstname FROM user WHERE id=?;";
		String returnValue="";	
		try
		{
			PreparedStatement statement = this.getConn().prepareStatement(SQL_USER);
			statement.setInt(1, userId);
			ResultSet result = statement.executeQuery();

			// If this returns true, then the user already exists.
			if (!result.next())
			{
				throw new Exception("No student found! (getFirstname)");
			}

			// Return the successfully user.
			returnValue = result.getString("firstname");
		} catch (Exception e)
		{
			GWT.log("Error in getFirstname()", e);
			this.getConn().rollback();
			throw e;
		}
		return returnValue;
	}
	
	/**
	 * Gets the lastname of this Student
	 * @return The lastname of this Student
	 * @throws Exception Exception An exception will be thrown if either
	 *  an Student cannot be found or there is an SQL error.
	 */
	public String getLastname() throws Exception
	{
		String SQL_USER = "SELECT lastname FROM user WHERE id=?;";		
		String returnValue="";	
		try
		{
			PreparedStatement statement = this.getConn().prepareStatement(SQL_USER);
			statement.setInt(1, userId);
			ResultSet result = statement.executeQuery();

			// If this returns true, then the user already exists.
			if (!result.next())
			{
				throw new Exception("No student found! (getLastname)");
			}

			// Return the successfully user.
			returnValue = result.getString("lastname");
		} catch (Exception e)
		{
			GWT.log("Error in getLastname()", e);
			this.getConn().rollback();
			throw e;
		}
		return returnValue;
	}

	/**
	 * Gets the username of this Student
	 * @return String for username
	 * @throws Exception when any SQLException happens
	 */
	public String getUsername() throws Exception
	{
		String SQL_USER = "SELECT username FROM user WHERE id=?;";
		String returnValue = "";
		try
		{
			PreparedStatement statement = this.getConn().prepareStatement(SQL_USER);
			statement.setInt(1, userId);
			ResultSet result = statement.executeQuery();

			// If this returns true, then the user already exists.
			if (!result.next())
			{
				throw new Exception("No student found! (getUsername)");
			}

			// Return the successfully user.
			returnValue = result.getString("username");
		} catch (SQLException e)
		{
			GWT.log("Error in getUsername()", e);
			this.getConn().rollback();
			throw e;
		}
		return returnValue;
	}

	/**
	 * Gets the password of this Student
	 * @return The password of this Student
	 * @throws Exception Exception An exception will be thrown if either
	 *  an Student cannot be found or there is an SQL error.
	 */
	public String getPassword() throws Exception
	{
		String SQL_USER = "SELECT password FROM user WHERE id=?;";		
		String returnValue = "";
		try
		{
			PreparedStatement statement = this.getConn().prepareStatement(SQL_USER);
			statement.setInt(1, userId);
			ResultSet result = statement.executeQuery();

			// If this returns true, then the user already exists.
			if (!result.next())
			{
				throw new Exception("No student found! (getPassword)");
			}

			// Return the successfully user.
			returnValue = result.getString("password");
		} catch (SQLException e)
		{
			GWT.log("Error in getPassword()", e);
			this.getConn().rollback();
			throw e;
		}
		return returnValue;
	}
	
	/**
	 * Gets the period of this Student
	 * @return The period of this Student
	 * @throws Exception Exception An exception will be thrown if either
	 *  an Student cannot be found or there is an SQL error.
	 */
	public String getPeriod() throws Exception
	{
		String SQL_USER = "SELECT period FROM user WHERE id=?;";		
		String returnValue = "";
		try
		{
			PreparedStatement statement = this.getConn().prepareStatement(SQL_USER);
			statement.setInt(1, userId);
			ResultSet result = statement.executeQuery();

			// If this returns true, then the user already exists.
			if (!result.next())
			{
				throw new Exception("No student found! (getPeriod)");
			}

			// Return the successfully user.
			returnValue = result.getString("period");
		} catch (SQLException e)
		{
			GWT.log("Error in getPeriod()", e);
			this.getConn().rollback();
			throw e;
		}
		return returnValue;
	}
	
	/**
	 * Gets the level of this Student
	 * @return Level Level of user
	 * @throws SQLException when any SQLException happens
	 */
	public Level getLevel() throws Exception
	{
		String SQL_USER = "SELECT level FROM user WHERE id=?;";		

		try
		{
			PreparedStatement statement = this.getConn().prepareStatement(SQL_USER);
			statement.setInt(1, userId);
			ResultSet result = statement.executeQuery();

			if (!result.next())
			{
				throw new Exception("No student found! (getLevel)");
			}
			
			// If this returns true, then the user already exists.
			String levelString = result.getString("level");
			if (levelString == null)
			{
				return null;
			}
			return Level.valueOf(levelString);
		} catch (SQLException e)
		{
			GWT.log("Error in getLevel()", e);
			this.getConn().rollback();
			throw e;
		}
	}
	
	/**
	 * Gets the role of this Student
	 * @return Role role of user
	 * @throws SQLException when any SQLException happens
	 */
	public Role getRole() throws Exception
	{
		String SQL_USER = "SELECT role FROM user WHERE id=?;";		

		try
		{
			PreparedStatement statement = this.getConn().prepareStatement(SQL_USER);
			statement.setInt(1, userId);
			ResultSet result = statement.executeQuery();

			// If this returns true, then the user already exists.
			if (!result.next())
			{
				throw new Exception("No student found! (getRole)");
			}
			return Role.valueOf(result.getString("role"));
		} catch (SQLException e)
		{
			GWT.log("Error in getRole()", e);
			this.getConn().rollback();
			throw e;
		}
	}
	
	/**
	 * Gets if the Student is enabled
	 * @return boolean true if the student is enabled, false if not
	 * @throws SQLException when any SQLException happens
	 */
	public boolean isEnabled() throws Exception
	{
		String SQL_USER = "SELECT enabled FROM user WHERE id=?;";		

		try
		{
			PreparedStatement statement = this.getConn().prepareStatement(SQL_USER);
			statement.setInt(1, userId);
			ResultSet result = statement.executeQuery();

			// If this returns true, then the user already exists.
			if (!result.next())
			{
				throw new Exception("No student found! (getEnabled)");
			}
			if (result.getInt("enabled")==1)
				return true;
			return false;
		} catch (SQLException e)
		{
			GWT.log("Error in getEnabled()", e);
			this.getConn().rollback();
			throw e;
		}
	}

	/**
	 * Sets a new Level for this Student
	 * 
	 * @param Level Level for level to be changed to
	 * @throws SQLException An exception will be thrown if SQL has
	 *  an error or the assignment cannot be updated.
	 */
	public void setLevel(Level level) throws SQLException
	{
		if (level == null)
		{
			return;
		}
		
		String SQL_USER = "UPDATE user SET level =? WHERE id=?;";		
		try
		{
			//Prepare the statement
			PreparedStatement statement = this.getConn().prepareStatement(SQL_USER);

			//Bind the variables
			statement.setString(1, level.name());
			statement.setInt(2, userId);

			//Execute the update
			statement.executeUpdate();

		} catch (SQLException e)
		{
			GWT.log("Error in SQL: Student->setLevel(Level "+ level.name() + ")", e);
			this.getConn().rollback();
			throw e;	
		}
		
		//commit change
		Database.get().commit();
	}
	
	/**
	 * Sets a new firstname for this Student
	 * 
	 * @param firstname String for firstname to be changed to
	 * @throws SQLException An exception will be thrown if SQL has
	 *  an error or the assignment cannot be updated.
	 */
	public void setFirstname(String firstname) throws SQLException
	{
		if (firstname == null)
		{
			return;
		}
		
		String SQL_USER = "UPDATE user SET firstname =? WHERE id=?;";		
		try
		{
			//Prepare the statement
			PreparedStatement statement = this.getConn().prepareStatement(SQL_USER);

			//Bind the variables
			statement.setString(1, firstname);
			statement.setInt(2, userId);

			//Execute the update
			statement.executeUpdate();

		} catch (SQLException e)
		{
			GWT.log("Error in SQL: Student->setFirstname(String "+ firstname + ")", e);
			this.getConn().rollback();
			throw e;	
		}
		
		//commit change
		Database.get().commit();
	}
	
	/**
	 * Sets a new lastname for this Student
	 * 
	 * @param lastname String for lastName to be changed to
	 * @throws SQLException An exception will be thrown if SQL has
	 *  an error or the assignment cannot be updated.
	 */
	public void setLastname(String lastname) throws SQLException
	{
		if (lastname  == null)
		{
			return;
		}
		
		String SQL_USER = "UPDATE user SET lastname =? WHERE id=?;";		
		try
		{
			//Prepare the statement
			PreparedStatement statement = this.getConn().prepareStatement(SQL_USER);

			//Bind the variables
			statement.setString(1, lastname);
			statement.setInt(2, userId);

			//Execute the update
			statement.executeUpdate();

		} catch (SQLException e)
		{
			GWT.log("Error in SQL: Student->setLastname(String "+ lastname + ")", e);
			this.getConn().rollback();
			throw e;	
		}
		
		//commit change
		Database.get().commit();
	}
	
	/**
	 * Sets a new username for this Student
	 * 
	 * @param username String for lastName to be changed to
	 * @throws SQLException An exception will be thrown if SQL has
	 *  an error or the assignment cannot be updated.
	 */
	public void setUsername(String username) throws SQLException
	{
		if (username == null)
		{
			return;
		}
		
		String SQL_USER = "UPDATE user SET username =? WHERE id=?;";		
		try
		{
			//Prepare the statement
			PreparedStatement statement = this.getConn().prepareStatement(SQL_USER);

			//Bind the variables
			statement.setString(1, username);
			statement.setInt(2, userId);

			//Execute the update
			statement.executeUpdate();

		} catch (SQLException e)
		{
			GWT.log("Error in SQL: Student->setUsername(String "+ username+ ")", e);
			this.getConn().rollback();
			throw e;	
		}
		
		//commit change
		Database.get().commit();
	}
	
	/**
	 * Sets a new password for this Student
	 * 
	 * @param password  the new password for this Student
	 * @throws SQLException An exception will be thrown if SQL has
	 *  an error or the assignment cannot be updated.
	 */
	public void setPassword(String password) throws SQLException
	{
		if (password == null)
		{
			return;
		}
		
		String SQL_USER = "UPDATE user SET password =? WHERE id=?;";		
		try
		{
			//Prepare the statement
			PreparedStatement statement = this.getConn().prepareStatement(SQL_USER);

			//Bind the variables
			statement.setString(1, password);
			statement.setInt(2, userId);

			//Execute the update
			statement.executeUpdate();

		} catch (SQLException e)
		{
			GWT.log("Error in SQL: Student->setPassword(String "+ password+ ")", e);
			this.getConn().rollback();
			throw e;	
		}
		
		//commit change
		Database.get().commit();
	}
	

	/**
	 * Sets a new period for this Student
	 * 
	 * @param period  the new period for this Student
	 * @throws SQLException An exception will be thrown if SQL has
	 *  an error or the assignment cannot be updated.
	 */
	public void setPeriod(String period) throws SQLException
	{
		if (period == null)
		{
			return;
		}
		
		String SQL_USER = "UPDATE user SET period =? WHERE id=?;";		
		try
		{
			//Prepare the statement
			PreparedStatement statement = this.getConn().prepareStatement(SQL_USER);

			//Bind the variables
			statement.setString(1, period);
			statement.setInt(2, userId);

			//Execute the update
			statement.executeUpdate();

		} catch (SQLException e)
		{
			GWT.log("Error in SQL: Student->setPeriod(String "+ period+ ")", e);
			this.getConn().rollback();
			throw e;	
		}
		
		//commit change
		Database.get().commit();
	}
	
	/**
	 * Sets if the Student is enabled or not
	 * 
	 * @param isEnabled boolean that specify if the student is enabled
	 * @throws SQLException An exception will be thrown if SQL has
	 *  an error or the assignment cannot be updated.
	 */
	public void setEnabled(boolean isEnable) throws SQLException
	{
		String SQL_USER = "UPDATE user SET enabled=? WHERE id=?;";		
		try
		{
			//Prepare the statement
			PreparedStatement statement = this.getConn().prepareStatement(SQL_USER);

			//Bind the variables
			statement.setInt(1, (isEnable ? 1 : 0));
			statement.setInt(2, userId);

			//Execute the update
			statement.executeUpdate();

		} catch (SQLException e)
		{
			GWT.log("Error in SQL: Student->setEnabled(boolean "+ isEnable+ ")", e);
			this.getConn().rollback();
			throw e;	
		}		
		//commit change
		Database.get().commit();
	}

	/**
	 * Sets the role of the Student as student
	 * 
	 * @throws SQLException An exception will be thrown if SQL has
	 *  an error or the assignment cannot be updated.
	 */
	public void setRole() throws SQLException
	{
		String SQL_USER = "UPDATE user SET role = 'Student' WHERE id=?;";		
		try
		{
			//Prepare the statement
			PreparedStatement statement = this.getConn().prepareStatement(SQL_USER);

			//Bind the variables
			statement.setInt(1, userId);

			//Execute the update
			statement.executeUpdate();

		} catch (SQLException e)
		{
			GWT.log("Error in SQL: Student->setRole()", e);
			this.getConn().rollback();
			throw e;	
		}
		
		//commit change
		Database.get().commit();
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
	 * Deletes the student from the database
	 * @param id The user id of the student
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
			GWT.log("Error in deleteUser()",e);
			link.rollback();
			throw e;
		}
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
	private int createStudent(String firstname, String lastname, Role role, String username,
			String password, Level level, String period, boolean enabled) throws Exception
	{
		String query = "INSERT INTO user " +
				"(firstname,lastname,role,username,password,level,period,enabled) VALUES " +
				"(?,?,?,?,?,?,?,?)";
		Connection link = Database.getSingleton().getLink();
		
		if (link == null)
			throw new Exception("Connection Failed!");
		
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
			stmt.setString(6, level.toString());
			stmt.setString(7, period);
			stmt.setBoolean(8, enabled);
			
			//execute update
			stmt.executeUpdate();
			
			ResultSet resultKey = stmt.getGeneratedKeys();
			if (!resultKey.next())
				throw new Exception("Creating new Student failed - createStudent()");
			return resultKey.getInt(1);
		}
		catch (SQLException e)
		{
			GWT.log("Error in setEnabled",e);
			link.rollback();
			throw e;
		}
	}
}
