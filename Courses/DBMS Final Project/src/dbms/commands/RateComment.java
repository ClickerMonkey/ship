package dbms.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dbms.Database;
import dbms.common.InputPrompt;
import dbms.menu.MenuCommand;
import dbms.validator.DecimalValidator;
import dbms.validator.IntegerValidator;

/**
 * =============================================================================
 * 						Update a value in a table.
 * =============================================================================
 * 
 * The MenuCommand which rates a given comment.
 * 
 * @author Philip Diffenderfer
 *
 */
public class RateComment implements MenuCommand 
{

	// The SQL query used to add a rating to a comment.
	private static final String SQL_COMMAND = 
		"UPDATE pd6407.comments " +
		"SET avg_rating = ((avg_rating * ratings) + ?) / (ratings + 1), " +
		"    ratings = ratings + 1 " +
		"WHERE page_id = ? AND " +
		"      ordinal = ?";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() 
	{
		// Prompt page_id, ordinal, rating
		InputPrompt<Integer> pagePrompt = new InputPrompt<Integer>(
				"Page Id: ", new IntegerValidator());
		InputPrompt<Integer> ordinalPrompt = new InputPrompt<Integer>(
				"Ordinal: ", new IntegerValidator());
		InputPrompt<Double> ratePrompt = new InputPrompt<Double>(
				"Rating (0.0-10.0): ", new DecimalValidator(0.0, 10.0));

		Integer pageId = pagePrompt.getInput();
		Integer ordinal = ordinalPrompt.getInput();
		Double rating = ratePrompt.getInput();

		// Get the live connection to the database.
		Connection conn = Database.get().getConnection();
		
		try
		{
			// Create a statement to process the SQL command.
			PreparedStatement query = conn.prepareStatement(SQL_COMMAND);
			query.setDouble(1, rating);
			query.setInt(2, pageId);
			query.setInt(3, ordinal);
			
			// Execute the query and test whether it was a success.
			if (query.executeUpdate() > 0) {
				System.out.println("Successful update");
			} else {	
				System.out.println("No comment updated");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
