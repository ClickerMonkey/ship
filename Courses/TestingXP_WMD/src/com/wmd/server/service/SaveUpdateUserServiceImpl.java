package com.wmd.server.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wmd.client.msg.Level;
import com.wmd.client.msg.Role;
import com.wmd.client.service.SaveUpdateUserService;
import com.wmd.server.db.Admin;
import com.wmd.server.db.Instructor;
import com.wmd.server.db.Student;

/**
 * @author Stephen Jurnack and Sam Storino and Scotty Rhinehart
 * 
 *         Implementation of the SaveUpdateUserService
 * 
 *         IMPORTANT JAVADOCS. PAY ATTENTION TO THIS: - Data must be passed in
 *         en masse in this order - public boolean saveUpdateUser(int id, String
 *         firstName, String lastName, Role role, String username, String
 *         password, Level level, String period, boolean enabled)
 * 
 *         - If you're updating or creating a user other than a Student, use
 *         null for the the fields that are not applied to that User. For
 *         example, Instructors do not have a level. Pass in null instead of a
 *         Level object.
 */

public class SaveUpdateUserServiceImpl extends RemoteServiceServlet implements
SaveUpdateUserService
{

	/**
	 * Serializable variable
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Updates the user (or saves them if they do not exist in the database).
	 * 
	 * returns a boolean indicating the success of the operation.
	 */
	public boolean saveUpdateUser(int id, String firstName, String lastName,
			Role role, String username, String password, Level level,
			String period, boolean enabled)
	{
		try
		{
			// return true if the user was successfully updated
			if (role == Role.Student)
			{
				// If user needs to be created...
				if (id == -1)
				{
					new Student(firstName, lastName, role, username, password, level, period, enabled);
				}
				else
				{
					// student
					Student student = new Student(id);
					student.setFirstname(firstName);
					student.setLastname(lastName);
					student.setLevel(level);
					student.setPassword(password);
					student.setPeriod(period);
					student.setRole();
					student.setUsername(username);
					student.setEnabled(enabled);	
				}

			}
			if (role == Role.Instructor)
			{
				// If user needs to be created...
				if (id == -1)
				{
					new Instructor(firstName, lastName, role, username, password, enabled);
				}
				else
				{
					// instructor
					Instructor i = new Instructor(id);
					i.setFirstName(firstName);
					i.setFirstName(lastName);
					i.setPassword(password);
					i.setUsername(username);
					i.setEnabled(enabled);
				}
			}
			if (role == Role.Admin)
			{

				// If user needs to be created...
				if (id == -1)
				{
					throw new Exception("Cannot create an admin MOFO!");
				}
				
				// admin
				Admin a = new Admin(id);
				a.setFirstName(firstName);
				a.setLastName(lastName);
				a.setUsername(username);
				a.setPassword(password);
				a.setEnabled(enabled);
			}
			return true;
		} catch (Exception e)
		{
			// Throw an error if saveUpdateUser was not successful, return false
			e.printStackTrace();
			GWT.log("Error in SaveUpdateUser class", e);
			return false;
		}
	}
}