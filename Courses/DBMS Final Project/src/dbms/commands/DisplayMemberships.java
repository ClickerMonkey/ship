package dbms.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;

import dbms.Database;
import dbms.DisplayTable;
import dbms.common.InputPrompt;
import dbms.menu.MenuCommand;
import dbms.validator.TimestampValidator;

/**
 * =============================================================================
 * 						List tuple values (multiple table).
 * =============================================================================
 * 
 * The MenuCommand which displays the memberships (user alias and group name)
 * that have been created between two dates.
 * 
 * @author Philip Diffenderfer
 *
 */
public class DisplayMemberships implements MenuCommand 
{
	
	// The SQL query used to display all users and their groups as well as
	// the date they were added to their respective groups. The user must
	// given two dates to return all memberships between.
	private static final String SQL_COMMAND = 
		"SELECT u.alias, g.name, m.date_created " +
		"FROM pd6407.memberships m, " +
		"     pd6407.users u, " +
		"     pd6407.groups g " +
		"WHERE m.user_alias = u.alias AND " +
		"      m.group_id = g.id AND " +
		"      m.date_created BETWEEN ? AND ?";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() 
	{
		InputPrompt<Timestamp> startPrompt = new InputPrompt<Timestamp>(
				"Start Date (yyyy-mm-dd): ", new TimestampValidator());
		
		InputPrompt<Timestamp> endPrompt = new InputPrompt<Timestamp>(
				"End Date (yyyy-mm-dd): ", new TimestampValidator());

		Timestamp start = startPrompt.getInput();
		Timestamp end = endPrompt.getInput();

		// Get the live connection to the database.
		Connection conn = Database.get().getConnection();
		
		try
		{
			// Create a statement to process the SQL command.
			PreparedStatement query = conn.prepareStatement(SQL_COMMAND);
			query.setTimestamp(1, start);
			query.setTimestamp(2, end);
			
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
				System.out.println("No memberships have been created between the given times.");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
