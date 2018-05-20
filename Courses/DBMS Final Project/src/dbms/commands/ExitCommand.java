package dbms.commands;

import dbms.Database;
import dbms.menu.MenuCommand;

/**
 * The MenuCommand which closes the database connection and exits the app.
 * 
 * @author Philip Diffenderfer
 *
 */
public class ExitCommand implements MenuCommand 
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() 
	{
		// Try disconnecting from the database.
		if (Database.get().disconnect()) {
			System.out.println("Disconnected");
		}
		else {
			System.out.println("Error disconnecting");
		}
		// Kill the application.
		System.exit(0);		
	}
	
}
