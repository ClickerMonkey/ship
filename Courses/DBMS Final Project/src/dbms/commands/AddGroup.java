package dbms.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dbms.Database;
import dbms.common.InputPrompt;
import dbms.menu.MenuCommand;
import dbms.validator.BooleanValidator;
import dbms.validator.NameValidator;

/**
 * =============================================================================
 * 				Add a row to one or more tables (no foreign keys).
 * =============================================================================
 * 
 * The MenuCommand which adds a group to a database.
 * 
 * @author Philip Diffenderfer
 *
 */
public class AddGroup implements MenuCommand 
{
	
	// The SQL query used to insert a group to the database.
	private static final String SQL_COMMAND = 
		"INSERT INTO pd6407.groups " +
		"(name, modify_root, modify_owner, modify_member) " +
		"VALUES (?, ?, ?, ?)";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() 
	{
		// Prompt for name, modify_root, modify_owner, modify_member
		InputPrompt<String> namePrompt = new InputPrompt<String>(
				"Name: ", new NameValidator());
		InputPrompt<Boolean> rootPrompt = new InputPrompt<Boolean>(
				"Modify Root (t/f): ", new BooleanValidator());
		InputPrompt<Boolean> ownerPrompt = new InputPrompt<Boolean>(
				"Modify Owners (t/f): ", new BooleanValidator());
		InputPrompt<Boolean> memberPrompt = new InputPrompt<Boolean>(
				"Modify Members (t/f): ", new BooleanValidator());

		String name = namePrompt.getInput();
		Boolean root = rootPrompt.getInput();
		Boolean owner = ownerPrompt.getInput();
		Boolean member = memberPrompt.getInput();

		// Get the live connection to the database.
		Connection conn = Database.get().getConnection();
		
		try
		{
			// Create a statement to process the SQL command.
			PreparedStatement query = conn.prepareStatement(SQL_COMMAND);
			query.setString(1, name);
			query.setBoolean(2, root);
			query.setBoolean(3, owner);
			query.setBoolean(4, member);

			// Execute and display success if the group was added.
			if (query.executeUpdate() == 1) {
				System.out.println("Successful insertion");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
