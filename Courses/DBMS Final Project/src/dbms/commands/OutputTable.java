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
 * The MenuCommand which displays all contents of a given table.
 * 
 * @author Philip Diffenderfer
 *
 */
public class OutputTable implements MenuCommand 
{
	
	// The SQL query used to select all tuples from an appended table. 
	private static final String SQL_COMMAND = 
		"SELECT * FROM pd6407.";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() 
	{
		// Prompt table name
		InputPrompt<String> tablePrompt = new InputPrompt<String>(
				"Table: ", new AliasValidator());
		
		String name = tablePrompt.getInput();

		// Get the live connection to the database.
		Connection conn = Database.get().getConnection();
		
		try
		{
			// Create a statement to process the SQL command.
			PreparedStatement query = conn.prepareStatement(SQL_COMMAND + name);
			
			ResultSet results = query.executeQuery();
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
				System.out.println("Empty table");
			}
		}
		catch (SQLException e) {
			System.out.println("The table does not exist.");
		}
	}

}
