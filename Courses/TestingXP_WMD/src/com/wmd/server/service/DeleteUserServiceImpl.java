package com.wmd.server.service;



import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wmd.client.service.DeleteUserService;
import com.wmd.server.db.Instructor;


/**
 * This service deletes a user from the database.
 * 
 * @author Andrew Marx, Chris Koch
 * @since 2010 April 12
 * @see DeleteUserService
 */
public class DeleteUserServiceImpl extends RemoteServiceServlet implements
		DeleteUserService
{
	private static final long serialVersionUID = 1L;

	/**
	 * Delete a user from the database.
	 * 
	 * @param userId
	 *            The id of the user to be deleted.
	 */
	@Override
	public boolean deleteUser(int userId)
	{
		try
		{
			Instructor handler = new Instructor(userId);
			return handler.deleteUser(userId);
		}
		catch (Exception e) 
		{
			return false;
		}
	}
	

}
