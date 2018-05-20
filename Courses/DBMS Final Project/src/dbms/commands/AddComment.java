package dbms.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dbms.Database;
import dbms.common.InputPrompt;
import dbms.menu.MenuCommand;
import dbms.validator.AliasValidator;
import dbms.validator.IntegerValidator;
import dbms.validator.NoValidator;

/**
 * =============================================================================
 * 				Add a row to one or more tables (with foreign keys).
 * =============================================================================
 * 
 * The MenuCommand which adds a comment to a page.
 * 
 * @author Philip Diffenderfer
 *
 */
public class AddComment implements MenuCommand 
{

	// The SQL query used to insert a comment into the database.
	private static final String SQL_COMMAND = 
		"INSERT INTO pd6407.comments " +
		"(page_id, user_alias, content) " +
		"VALUES (?, ?, ?)";
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() 
	{
		// Prompt for page_id, user_alias, and content.
		InputPrompt<Integer> pagePrompt = new InputPrompt<Integer>(
				"Page Id: ", new IntegerValidator());
		InputPrompt<String> aliasPrompt = new InputPrompt<String>(
				"User Alias: ", new AliasValidator());
		InputPrompt<String> contentPrompt = new InputPrompt<String>(
				"Content: ", new NoValidator());

		Integer pageId = pagePrompt.getInput();
		String alias = aliasPrompt.getInput();
		String content = contentPrompt.getInput();

		// Get the live connection to the database.
		Connection conn = Database.get().getConnection();
		
		try
		{
			// Create a statement to process the SQL command.
			PreparedStatement query = conn.prepareStatement(SQL_COMMAND);
			query.setInt(1, pageId);
			query.setString(2, alias);
			query.setString(3, content);

			// Continually try to insert comment until valid keys are given.
			boolean validKey = false;
			while (!validKey) {
				try {
					if (query.executeUpdate() == 1) {
						System.out.println("Successful insertion");
					}
					validKey = true;
				} 
				catch (SQLException ie) {
					// An error occurred, prompt for a new page_id and alias.
					System.out.println("Invalid Page Id or User Alias");
					pageId = pagePrompt.getInput();
					alias = aliasPrompt.getInput();

					query.setInt(1, pageId);
					query.setString(2, alias);
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
