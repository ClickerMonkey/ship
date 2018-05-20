package dbms.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import dbms.Database;
import dbms.DisplayTable;
import dbms.common.InputPrompt;
import dbms.menu.MenuCommand;
import dbms.validator.AliasValidator;

/**
 * =============================================================================
 * 				List tuple values (single table, multiple rows).
 * =============================================================================
 * 
 * The MenuCommand which displays the comments for a given user.
 * 
 * @author Philip Diffenderfer
 *
 */
public class DisplayUserComments implements MenuCommand 
{

	// The SQL query used to select all comments of a given user 
	private static final String SQL_COMMAND = 
		"SELECT page_id, ordinal, content, avg_rating, time_created " +
		"FROM pd6407.comments " +
		"WHERE user_alias = ? " +
		"ORDER BY page_id, ordinal";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() 
	{
		// Prompt alias
		InputPrompt<String> aliasPrompt = new InputPrompt<String>(
				"Alias: ", new AliasValidator());
		
		String alias = aliasPrompt.getInput();

		// Get the live connection to the database.
		Connection conn = Database.get().getConnection();
		
		try
		{
			// Create a statement to process the SQL command.
			PreparedStatement select = conn.prepareStatement(SQL_COMMAND);
			select.setString(1, alias);
			
			ResultSet results = select.executeQuery();
			ResultSetMetaData data = results.getMetaData();

			// Build the display table headers from the meta data.
			DisplayTable table = DisplayTable.fromMetaData(data);

			// Execute the query and test whether it was a success.
			while (results.next()) {
				// Add a row into the display table...
				table.addRow();

				// Then populate the row with data.
				for (int i = 0; i < data.getColumnCount(); i++) {
					table.set(i, results.getObject(i + 1));
				}
			}

			// If data exists in the table, display it.
			if (table.getRows() > 0) {
				table.display();	
			}
			else {
				System.out.println("The given user does not have any comments.");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
