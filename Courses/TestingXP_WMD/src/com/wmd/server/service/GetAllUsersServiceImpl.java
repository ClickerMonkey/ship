package com.wmd.server.service;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wmd.client.msg.Role;
import com.wmd.client.msg.UserMsg;
import com.wmd.client.service.GetAllUsersService;
import com.wmd.server.db.AdminList;
import com.wmd.server.db.InstructorList;
import com.wmd.server.db.StudentList;

/**
 * This service should be used to fetch a list of users from the database.
 * 
 * @author Andrew Marx, Chris Koch
 * @since 2010 April 12
 * @see GetAllUsersService
 * 
 */
public class GetAllUsersServiceImpl extends RemoteServiceServlet implements GetAllUsersService
{
	private static final long serialVersionUID = 1L;
	private InstructorList instructorList;
	private StudentList studentList;
	private AdminList adminList; 
	/**
	 * Returns an ArrayList of every user having the specified role (i.e.
	 * student, instructor) in the database.
	 * 
	 * @param role
	 *            The type of user to retrieve (i.e. student, instructor, admin)
	 * @return An ArrayList of UserMsg objects, one for each student in the
	 *         database.
	 */
	public ArrayList<UserMsg> getAllUsers(Role role)
	{
		try
		{
			if (role == Role.Admin)
			{
				adminList = new AdminList();
				return adminList.getAllAdmins();
			}
			else if (role == Role.Instructor)
			{
				instructorList = new InstructorList();
				return instructorList.getAllInstructors();

			} else if (role.equals(Role.Student))
			{
				studentList = new StudentList();
				return studentList.getAllStudents();
			} else
			{
				return null;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}

	}

}
