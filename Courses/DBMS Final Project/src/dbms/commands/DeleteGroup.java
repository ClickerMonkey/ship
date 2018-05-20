package dbms.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dbms.Database;
import dbms.common.InputPrompt;
import dbms.menu.MenuCommand;
import dbms.validator.IntegerValidator;

/**
 * =============================================================================
 * 				Delete a row from a table (with foreign keys).
 * =============================================================================
 * 
 * The MenuCommand which deletes a group.
 * 
 * @author Philip Diffenderfer
 *
 */
public class DeleteGroup implements MenuCommand 
{

	// The SQL query used to delete a group. This will result a cascade of
	// deleting all memberships for every user in this group, and all ownerships
	// for all pages controlled by this group.
	private static final String SQL_COMMAND = 
		"DELETE FROM pd6407.groups " +
		"WHERE id = ?";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() 
	{
		// Prompt for id
		InputPrompt<Integer> idPrompt = new InputPrompt<Integer>(
				"Id: ", new IntegerValidator());

		Integer id = idPrompt.getInput();

		// Get the live connection to the database.
		Connection conn = Database.get().getConnection();
		
		try
		{
			// Create a statement to process the SQL command.
			PreparedStatement query = conn.prepareStatement(SQL_COMMAND);
			query.setInt(1, id);

			// Execute the query and test whether it was a success.
			if (query.executeUpdate() > 0) {
				System.out.println("Successful deletion");
			} else {
				System.out.println("No group was deleted");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
