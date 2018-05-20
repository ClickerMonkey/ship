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
 * 					Delete a row from a table (no foreign keys).
 * =============================================================================
 * 
 * The MenuCommand which deletes a comment from a page.
 * 
 * @author Philip Diffenderfer
 *
 */
public class DeleteComment implements MenuCommand 
{

	// The SQL query used to delete a single comment. This will not result in
	// cascade deletions since the key of comments is not a foriegn key in
	// any other table.
	private static final String SQL_COMMAND = 
		"DELETE FROM pd6407.comments " +
		"WHERE page_id = ? AND " +
		"      ordinal = ?";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() 
	{
		// Prompt for page_id and ordinal
		InputPrompt<Integer> pagePrompt = new InputPrompt<Integer>(
				"Page Id: ", new IntegerValidator());
		InputPrompt<Integer> ordinalPrompt = new InputPrompt<Integer>(
				"Ordinal: ", new IntegerValidator());

		Integer pageId = pagePrompt.getInput();
		Integer ordinal = ordinalPrompt.getInput();

		// Get the live connection to the database.
		Connection conn = Database.get().getConnection();
		
		try
		{
			// Create a statement to process the SQL command.
			PreparedStatement query = conn.prepareStatement(SQL_COMMAND);
			query.setInt(1, pageId);
			query.setInt(2, ordinal);

			// Execute the query and test whether it was a success.
			if (query.executeUpdate() > 0) {
				System.out.println("Successful deletion");
			} else {	
				System.out.println("No comments deleted");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
