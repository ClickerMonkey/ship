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
import dbms.validator.IntegerValidator;


/**
 * =============================================================================
 * 						Create a recursive closure query.
 * =============================================================================
 * 
 * The MenuCommand which displays all the child pages of a given page.
 * 
 * @author Philip Diffenderfer
 *
 */
public class DisplayChildren implements MenuCommand 
{
	
	// The SQL query used to return the id, parent_id, name, and title of all
	// pages which are the children to the given id.
	private static final String SQL_COMMAND = 
		"SELECT id, parent_id, name, title " +
		"FROM pd6407.pages " +
		"WHERE parent_id = ?";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() 
	{
		// Prompt for id
		InputPrompt<Integer> idPrompt = new InputPrompt<Integer>(
				"Id: ", new IntegerValidator());
		
		int pid = idPrompt.getInput();
		
		try
		{
			// Get the table consisting of all of the children of the given page
			DisplayTable table = getChildren(pid, null);

			// If data exists in the table, display it.
			if (table.getRows() > 0) {
				table.display();	
			}
			else {
				System.out.println("This page has no children (or doesn't exist)");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * A recursive method which builds a DisplayTable with the children of the
	 * given page. If the given table is null then it is created.
	 * 
	 * @param pid => The id of the given page.
	 * @param out => The table to add output to.
	 * @return The DisplayTable generated.
	 * @throws SQLException An SQL error occurred while recursing.
	 */
	private DisplayTable getChildren(int pid, DisplayTable out) throws SQLException 
	{
		// Get the live connection to the database.
		Connection conn = Database.get().getConnection();
		
		// Create a statement to process the SQL command.
		PreparedStatement query = conn.prepareStatement(SQL_COMMAND);
		query.setInt(1, pid);
		
		// Get all of the children of the current page...
		ResultSet results = query.executeQuery();
		ResultSetMetaData data = results.getMetaData();
		if (out == null) {
			// Build the display table headers from the meta data.
			out = DisplayTable.fromMetaData(data);
		}

		// For each child page of this page...
		while (results.next()) {
			// Add a row into the display table...
			out.addRow();
			// Then populate the row with data.
			for (int i = 0; i < data.getColumnCount(); i++) {
				out.set(i, results.getObject(i + 1));
			}
			
			// Recursively add in the children of this child page.
			getChildren(results.getInt("id"), out);
		}

		// Close the query now
		query.close();
		
		// Returns the display table
		return out;
	}

}
